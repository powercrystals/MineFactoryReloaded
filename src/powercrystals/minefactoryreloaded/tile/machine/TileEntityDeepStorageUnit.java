package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.api.IDeepStorageUnit;
import powercrystals.minefactoryreloaded.gui.client.GuiDeepStorageUnit;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerDeepStorageUnit;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityDeepStorageUnit extends TileEntityFactoryInventory implements IDeepStorageUnit
{
	public TileEntityDeepStorageUnit()
	{
		super(Machine.DeepStorageUnit);
	}

	private boolean[] _isSideOutput = new boolean[] { false, false, true, true, true, true };
	
	private int _storedQuantity;
	private int _storedId;
	private int _storedMeta;
	
	private boolean _canUpdate = true;
	
	@Override
	public String getGuiBackground()
	{
		return "dsu.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiDeepStorageUnit(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerDeepStorageUnit getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerDeepStorageUnit(this, inventoryPlayer);
	}
	
	public boolean getIsSideOutput(int side)
	{
		return _isSideOutput[side];
	}
	
	public void setSideIsOutput(int side, boolean isOutput)
	{
		_isSideOutput[side] = isOutput;
	}
	
	public int getQuantity()
	{
		return _storedQuantity;
	}
	
	public int getQuantityAdjusted()
	{
		int quantity = _storedQuantity;
		
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(_inventory[i] != null && _storedQuantity == 0)
			{
				quantity += _inventory[i].stackSize;				
			}
			else if(_inventory[i] != null && _inventory[i].itemID == _storedId && _inventory[i].getItemDamage() == _storedMeta)
			{
				quantity += _inventory[i].stackSize;
			}
		}
		
		return quantity;
	}
	
	public void setQuantity(int quantity)
	{
		_storedQuantity = quantity;
	}
	
	public void clearSlots()
	{
		for(int i = 0; i < getSizeInventory(); i++)
		{
			_inventory[i] = null;
		}
	}
	
	public int getId()
	{
		if(_storedQuantity == 0 && _inventory[2] != null)
		{
			return _inventory[2].itemID;
		}
		else
		{
			return _storedId;			
		}
	}
	
	public void setId(int id)
	{
		_storedId = id;
	}
	
	public int getMeta()
	{
		if(_storedQuantity == 0 && _inventory[2] != null)
		{
			return _inventory[2].getItemDamage();
		}
		else
		{
			return _storedMeta;			
		}
	}
	
	public void setMeta(int meta)
	{
		_storedMeta = meta;
	}
	
	@Override
	public ForgeDirection getDropDirection()
	{
		return ForgeDirection.UP;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(worldObj.isRemote)
		{
			return;
		}
		if((_inventory[2] == null) && _storedQuantity > 0)
		{
			_inventory[2] = new ItemStack(_storedId, Math.min(_storedQuantity, new ItemStack(_storedId, 1, _storedMeta).getMaxStackSize()), _storedMeta);
			_storedQuantity -= _inventory[2].stackSize;
		}
		else if(_inventory[2] != null && _inventory[2].stackSize < _inventory[2].getMaxStackSize() && _inventory[2].itemID == _storedId && _inventory[2].getItemDamage() == _storedMeta  && _storedQuantity > 0)
		{
			int amount = Math.min(_inventory[2].getMaxStackSize() - _inventory[2].stackSize, _storedQuantity);
			_inventory[2].stackSize += amount;
			_storedQuantity -= amount;
		}
		checkInput(0);
		checkInput(1);
		
		if(_inventory[0] == null && _inventory[1] == null)
		{
			_canUpdate = false;
		}
	}
	
	private void checkInput(int slot)
	{
		if(_inventory[slot] != null)
		{
			if(_storedQuantity == 0 &&
					(_inventory[2] == null ||
					(_inventory[2].itemID == _inventory[slot].itemID &&	_inventory[2].getItemDamage() == _inventory[slot].getItemDamage()))
					&& _inventory[slot].getTagCompound() == null)
			{
				_storedId = _inventory[slot].itemID;
				_storedMeta = _inventory[slot].getItemDamage();
				_storedQuantity = _inventory[slot].stackSize;
				_inventory[slot] = null;
			}
			else if(_inventory[slot].itemID == _storedId && _inventory[slot].getItemDamage() == _storedMeta && _inventory[slot].getTagCompound() == null && (Integer.MAX_VALUE - 66) - _inventory[slot].stackSize > _storedQuantity)
			{
				_storedQuantity += _inventory[slot].stackSize;
				_inventory[slot] = null;
			}
			// boot improperly typed items from the input slots
			else if(_inventory[slot].itemID != _storedId || _inventory[slot].getItemDamage() != _storedMeta || _inventory[slot].getTagCompound() != null)
			{
				_inventory[slot] = UtilInventory.dropStack(this, _inventory[slot], this.getDropDirection());
			}
		}
	}
	
	@Override
	public int getSizeInventory()
	{
		return 3;
	}
	
	@Override
	public String getInvName()
	{
		return "Deep Storage Unit";
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return player.getDistanceSq(xCoord, yCoord, zCoord) <= 64D;
	}
	
	@Override
	public int getStartInventorySide(ForgeDirection side)
	{
		if(side.ordinal() > 5) return 0;
		return _isSideOutput[side.ordinal()] ? 2 : 0;
	}
	
	@Override
	public int getSizeInventorySide(ForgeDirection side)
	{
		if(side.ordinal() > 5) return 0;
		return _isSideOutput[side.ordinal()] ? 1 : 2;
	}
	
	/*
	 * Should only allow matching items to be inserted in the "in" slot. Nothing goes in the "out" slot.
	 */
	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int sideordinal)
	{
		if(sideordinal > 5) return false;
		if(!_isSideOutput[sideordinal])
		{
			ItemStack stored = getStoredItemType();
			if(stored == null && _inventory[2] != null)
			{
				stored = _inventory[2];
			}
			return (!stack.hasTagCompound() && (stored == null || stack.isItemEqual(stored)));
		}
		return false;
	}
	
	/*
	 * Should only allow removal from the output slot.
	 */
	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int sideordinal)
	{
		if(sideordinal > 5) return false;
		return _isSideOutput[sideordinal];
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		
		nbttagcompound.setInteger("storedId", _storedId);
		nbttagcompound.setInteger("storedMeta", _storedMeta);
		nbttagcompound.setInteger("storedQuantity", _storedQuantity);
		
		nbttagcompound.setBoolean("side0output", _isSideOutput[0]);
		nbttagcompound.setBoolean("side1output", _isSideOutput[1]);
		nbttagcompound.setBoolean("side2output", _isSideOutput[2]);
		nbttagcompound.setBoolean("side3output", _isSideOutput[3]);
		nbttagcompound.setBoolean("side4output", _isSideOutput[4]);
		nbttagcompound.setBoolean("side5output", _isSideOutput[5]);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		
		_storedId = nbttagcompound.getInteger("storedId");
		_storedMeta = nbttagcompound.getInteger("storedMeta");
		_storedQuantity = nbttagcompound.getInteger("storedQuantity");
		
		_isSideOutput[0] = nbttagcompound.getBoolean("side0output");
		_isSideOutput[1] = nbttagcompound.getBoolean("side1output");
		_isSideOutput[2] = nbttagcompound.getBoolean("side2output");
		_isSideOutput[3] = nbttagcompound.getBoolean("side3output");
		_isSideOutput[4] = nbttagcompound.getBoolean("side4output");
		_isSideOutput[5] = nbttagcompound.getBoolean("side5output");
		
		if(Item.itemsList[_storedId] == null && _storedQuantity > 0)
		{
			_storedQuantity = 0;
		}
	}
	
	@Override
	public ItemStack getStoredItemType()
	{
		if(_storedQuantity > 0 || _inventory[2] != null)
		{
			return new ItemStack(_storedId, getQuantityAdjusted(), _storedMeta);
		}
		return null;
	}
	
	@Override
	public void setStoredItemCount(int amount)
	{
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(_inventory[i] != null && _inventory[i].itemID == _storedId && _inventory[i].getItemDamage() == _storedMeta)
			{
				if(amount == 0)
				{
					_inventory[i] = null;
				}
				else if(amount >= _inventory[i].stackSize)
				{
					amount -= _inventory[i].stackSize;					
				}
				else if(amount < _inventory[i].stackSize)
				{
					_inventory[i].stackSize = amount;
					amount = 0;
				}
			}
		}
		_storedQuantity = amount;
		_canUpdate = true;
	}
	
	@Override
	public void setStoredItemType(int itemID, int meta, int Count)
	{
		clearSlots();
		_storedId = itemID;
		_storedMeta = meta;
		_storedQuantity = Count;
		_canUpdate = true;
	}
	
	@Override
	public int getMaxStoredCount()
	{
		return Integer.MAX_VALUE;
	}
	
	@Override
	public boolean canUpdate()
	{
		return _canUpdate;
	}
	
	@Override
	protected void onFactoryInventoryChanged()
	{
		_canUpdate = true;
	}
}
