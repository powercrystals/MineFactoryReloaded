package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.api.IDeepStorageUnit;
import powercrystals.minefactoryreloaded.api.IDeepStorageUnit27;
import powercrystals.minefactoryreloaded.gui.client.GuiDeepStorageUnit;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerDeepStorageUnit;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityDeepStorageUnit extends TileEntityFactoryInventory implements IDeepStorageUnit, IDeepStorageUnit27
{
	public TileEntityDeepStorageUnit()
	{
		super(Machine.DeepStorageUnit);
	}

	private boolean[] _isSideOutput = new boolean[] { false, false, true, true, true, true };
	
	private ItemStack _storedStack;
	
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
		return _storedStack != null ? _storedStack.stackSize : 0;
	}
	
	public int getQuantityAdjusted()
	{
		int quantity = 0;
		if(_storedStack != null)
		{
			quantity = _storedStack.stackSize;
		}
		
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(_inventory[i] != null && (_storedStack == null || UtilInventory.stacksEqual(_storedStack, _inventory[i])))
			{
				quantity += _inventory[i].stackSize;				
			}
		}
		
		return quantity;
	}
	
	public void setQuantity(int quantity)
	{
		if(_storedStack != null)
		{
			_storedStack.stackSize = quantity;
		}
		else if(worldObj.isRemote)
		{
			_storedStack = new ItemStack(0, quantity, 0);
		}
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
		if(_storedStack != null)
		{
			return _storedStack.itemID;
		}
		else if(_inventory[2] != null)
		{
			return _inventory[2].itemID;
		}
		return 0;
	}
	
	public int getMeta()
	{
		if(_storedStack != null)
		{
			return _storedStack.getItemDamage();
		}
		else if(_inventory[2] != null)
		{
			return _inventory[2].getItemDamage();
		}
		return 0;
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
		if(_inventory[2] == null && _storedStack != null)
		{
			_inventory[2] = _storedStack.copy();
			_inventory[2].stackSize = Math.min(_storedStack.stackSize, _storedStack.getMaxStackSize());
			_storedStack.stackSize -= _inventory[2].stackSize;
		}
		else if(_inventory[2] != null && _inventory[2].stackSize < _inventory[2].getMaxStackSize() && UtilInventory.stacksEqual(_storedStack, _inventory[2]))
		{
			int amount = Math.min(_inventory[2].getMaxStackSize() - _inventory[2].stackSize, _storedStack.stackSize);
			_inventory[2].stackSize += amount;
			_storedStack.stackSize -= amount;
		}
		checkInput(0);
		checkInput(1);
		
		if(_inventory[0] == null && _inventory[1] == null)
		{
			_canUpdate = false;
		}
		
		if(_storedStack != null && _storedStack.stackSize == 0)
		{
			_storedStack = null;
		}
	}
	
	private void checkInput(int slot)
	{
		if(_inventory[slot] != null)
		{
			if(_storedStack == null &&
					(_inventory[2] == null ||
					UtilInventory.stacksEqual(_inventory[2], _inventory[slot])))
			{
				_storedStack = _inventory[slot].copy();
				_inventory[slot] = null;
			}
			else if(UtilInventory.stacksEqual(_inventory[slot], _storedStack) && (Integer.MAX_VALUE - 64) - _inventory[slot].stackSize > _storedStack.stackSize)
			{
				_storedStack.stackSize += _inventory[slot].stackSize;
				_inventory[slot] = null;
			}
			// boot improperly typed items from the input slots
			else if(!UtilInventory.stacksEqual(_inventory[slot], _storedStack))
			{
				//move doDrop from TEPowered to TEInventory and use it for this? Or just remove this?
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
			ItemStack stored = (_storedStack == null && _inventory[2] != null) ? _inventory[2] : _storedStack;
			return UtilInventory.stacksEqual(stored, stack);
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
		
		if(_storedStack != null)
		{
			NBTTagCompound storedstacktag = new NBTTagCompound();
			_storedStack.writeToNBT(storedstacktag);
			nbttagcompound.setTag("storedStack", storedstacktag);
			nbttagcompound.setInteger("storedQuantity", _storedStack.stackSize);
		}
		
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
		
		if(nbttagcompound.hasKey("storedStack"))
		{
			_storedStack = new ItemStack(0, 0, 0);
			_storedStack = ItemStack.loadItemStackFromNBT((NBTTagCompound)nbttagcompound.getTag("storedStack"));
			_storedStack.stackSize = nbttagcompound.getInteger("storedQuantity");
		}
		else
		{
			int _storedId = nbttagcompound.getInteger("storedId");
			int _storedMeta = nbttagcompound.getInteger("storedMeta");
			int _storedQuantity = nbttagcompound.getInteger("storedQuantity");
			if(_storedId != 0 && _storedQuantity != 0)
			{
				_storedStack = new ItemStack(_storedId, _storedQuantity, _storedMeta);
			}
		}
		
		_isSideOutput[0] = nbttagcompound.getBoolean("side0output");
		_isSideOutput[1] = nbttagcompound.getBoolean("side1output");
		_isSideOutput[2] = nbttagcompound.getBoolean("side2output");
		_isSideOutput[3] = nbttagcompound.getBoolean("side3output");
		_isSideOutput[4] = nbttagcompound.getBoolean("side4output");
		_isSideOutput[5] = nbttagcompound.getBoolean("side5output");
		
		// not entirely sure this check is still helpful, with the switch to internal stack-based storage
		if(_storedStack != null && (Item.itemsList[_storedStack.itemID] == null || _storedStack.stackSize <= 0))
		{
			_storedStack = null;
		}
	}
	
	@Override
	public ItemStack getStoredItemType()
	{
		ItemStack returnstack = null;
		if(_storedStack != null)
		{
			returnstack = _storedStack.copy();
			if(UtilInventory.stacksEqual(_storedStack, _inventory[2]))
			{
				returnstack.stackSize += _inventory[2].stackSize;
			}
		}
		else if(_inventory[2] != null)
		{
			returnstack = _inventory[2].copy();
		}
		return returnstack;
	}
	
	@Override
	public void setStoredItemCount(int amount)
	{
		if(_storedStack == null) return;
		
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(_inventory[i] != null && UtilInventory.stacksEqual(_storedStack, _inventory[i]))
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
		_storedStack.stackSize = amount;
		_canUpdate = true;
	}
	
	@Override
	@Deprecated
	public void setStoredItemType(int itemID, int meta, int Count)
	{
		clearSlots();
		_storedStack = new ItemStack(itemID, Count, meta);
		_canUpdate = true;
	}
	
	@Override
	public void setStoredItemType(ItemStack storedStack)
	{
		clearSlots();
		_storedStack = storedStack.copy();
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
