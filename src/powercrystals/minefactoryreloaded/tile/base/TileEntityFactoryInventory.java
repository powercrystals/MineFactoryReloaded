package powercrystals.minefactoryreloaded.tile.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import powercrystals.minefactoryreloaded.core.MFRLiquidMover;
import buildcraft.core.IMachine;

public abstract class TileEntityFactoryInventory extends TileEntityFactory implements IInventory, ISidedInventory, IMachine
{
	protected TileEntityFactoryInventory()
	{
		_inventory = new ItemStack[getSizeInventory()];
	}
	
	public ILiquidTank getTank()
	{
		return null;
	}
	
	protected boolean shouldPumpLiquid()
	{
		return false;
	}
	
	public boolean allowBucketFill()
	{
		return false;
	}
	
	public boolean allowBucketDrain()
	{
		return false;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if(!worldObj.isRemote && shouldPumpLiquid())
		{
			MFRLiquidMover.pumpLiquid(getTank(), this);
		}
	}
	
	protected ItemStack[] _inventory;
	
	@Override
	public ItemStack getStackInSlot(int i)
	{
		return _inventory[i];
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
	public ItemStack decrStackSize(int slot, int size)
	{
		if(_inventory[slot] != null)
		{
			if(_inventory[slot].stackSize <= size)
			{
				ItemStack itemstack = _inventory[slot];
				_inventory[slot] = null;
				onFactoryInventoryChanged();
				return itemstack;
			}
			ItemStack itemstack1 = _inventory[slot].splitStack(size);
			if(_inventory[slot].stackSize == 0)
			{
				_inventory[slot] = null;
			}
			onFactoryInventoryChanged();
			return itemstack1;
		}
		else
		{
			onFactoryInventoryChanged();
			return null;
		}
	}
	
	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		_inventory[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}
		onFactoryInventoryChanged();
	}
	
	protected void onFactoryInventoryChanged()
	{
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}
	
	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack)
	{
		return true;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		if(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
		{
			return false;
		}
		return entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64D;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		_inventory = new ItemStack[getSizeInventory()];
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
		onFactoryInventoryChanged();
		
		int tankItemId = nbttagcompound.getInteger("tankItemId");
		int tankItemMeta = nbttagcompound.getInteger("tankItemMeta");
		int tankAmount = nbttagcompound.getInteger("tankAmount");
		
		if(getTank() != null && Item.itemsList[tankItemId] != null && LiquidContainerRegistry.isLiquid(new ItemStack(tankItemId, 1, tankItemMeta)))
		{
			((LiquidTank)getTank()).setLiquid(new LiquidStack(tankItemId, tankAmount, tankItemMeta));
			
			if(getTank().getLiquid() != null && getTank().getLiquid().amount > getTank().getCapacity())
			{
				getTank().getLiquid().amount = getTank().getCapacity();
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
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
		if(getTank() != null && getTank().getLiquid() != null)
		{
			nbttagcompound.setInteger("tankAmount", getTank().getLiquid().amount);
			nbttagcompound.setInteger("tankItemId", getTank().getLiquid().itemID);
			nbttagcompound.setInteger("tankMeta", getTank().getLiquid().itemMeta);
		}
		
		nbttagcompound.setTag("Items", nbttaglist);
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;
	}
	
	public boolean shouldDropSlotWhenBroken(int slot)
	{
		return true;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		int start = getStartInventorySide(ForgeDirection.getOrientation(side));
		int size = getSizeInventorySide(ForgeDirection.getOrientation(side));
		
		int[] slots = new int[size];
		for(int i = 0; i < size; i++)
		{
			slots[i] = i + start;
		}
		return slots;
	}
	
	public int getStartInventorySide(ForgeDirection side)
	{
		return 0;
	}
	
	public int getSizeInventorySide(ForgeDirection side)
	{
		return getSizeInventory();
	}
	
	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j)
	{
		return this.isStackValidForSlot(i, itemstack);
	}
	
	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j)
	{
		return true;
	}
	
	@Override
	public boolean isActive()
	{
		return false;
	}
	
	@Override
	public boolean manageLiquids()
	{
		return false;
	}
	
	@Override
	public boolean manageSolids()
	{
		return false;
	}
	
	@Override
	public boolean allowActions()
	{
		return false;
	}
}
