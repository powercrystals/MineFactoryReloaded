package powercrystals.minefactoryreloaded.processing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import powercrystals.minefactoryreloaded.core.TileEntityFactory;

public class TileEntityDeepStorageUnit extends TileEntityFactory implements IInventory, ISidedInventory
{
	// in, in, out
	private ItemStack[] _inventory = new ItemStack[3];
	private boolean[] _isSideOutput = new boolean[] { false, false, true, true, true, true };
	
	private int _storedQuantity;
	private int _storedId;
	private int _storedMeta;
	
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
	
	public void setQuantity(int quantity)
	{
		_storedQuantity = quantity;
	}
	
	public int getId()
	{
		return _storedId;
	}
	
	public void setId(int id)
	{
		_storedId = id;
	}
	
	public int getMeta()
	{
		return _storedMeta;
	}
	
	public void setMeta(int meta)
	{
		_storedMeta = meta;
	}
	
	@Override
	public boolean canRotate()
	{
		return false;
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
	}
	
	private void checkInput(int slot)
	{
		if(_inventory[slot] != null)
		{
			if(_storedQuantity == 0)
			{
				_storedId = _inventory[slot].itemID;
				_storedMeta = _inventory[slot].getItemDamage();
				_storedQuantity = _inventory[slot].stackSize;
				_inventory[slot] = null;
			}
			else if(_inventory[slot].itemID == _storedId && _inventory[slot].getItemDamage() == _storedMeta && _inventory[slot].getTagCompound() == null && Integer.MAX_VALUE - _inventory[slot].stackSize > _storedQuantity)
			{
				if(_inventory[slot].getMaxStackSize() > 1)
				{
					_storedQuantity += (_inventory[slot].stackSize - 1);
					_inventory[slot].stackSize = 1;
				}
				else
				{
					_storedQuantity += _inventory[slot].stackSize;
					_inventory[slot] = null;
				}
			}
		}
		
		if(_inventory[slot] == null && _storedQuantity > 1 && _inventory[2] != null && _inventory[2].getMaxStackSize() > 1)
		{
			_inventory[slot] = new ItemStack(_storedId, 1, _storedMeta);
			_storedQuantity--;
		}
	}
	
	@Override
	public int getSizeInventory()
	{
		return 3;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return _inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int size)
	{
		if(_inventory[slot] != null)
		{
			if(_inventory[slot].stackSize <= size)
			{
				ItemStack itemstack = _inventory[slot];
				_inventory[slot] = null;
				return itemstack;
			}
			ItemStack itemstack1 = _inventory[slot].splitStack(size);
			if(_inventory[slot].stackSize == 0)
			{
				_inventory[slot] = null;
			}
			return itemstack1;
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		_inventory[slot] = stack;
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
	public void openChest()
	{
	}

	@Override
	public void closeChest()
	{
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
		
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < _inventory.length; i++)
		{
			if(_inventory[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				_inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
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
		
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 0xff;
			if(j >= 0 && j < _inventory.length)
			{
				ItemStack s = new ItemStack(0, 0, 0);
				s.readFromNBT(nbttagcompound1);
				_inventory[j] = s;
			}
		}
	}
}
