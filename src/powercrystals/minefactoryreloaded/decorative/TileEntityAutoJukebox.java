package powercrystals.minefactoryreloaded.decorative;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import powercrystals.core.util.Util;
import powercrystals.minefactoryreloaded.core.TileEntityFactory;

public class TileEntityAutoJukebox extends TileEntityFactory implements IInventory
{
	private ItemStack[] _inventory = new ItemStack[1];
	private boolean _lastRedstoneState;
	
	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if(worldObj.isRemote)
		{
			return;
		}
		
		boolean redstoneState = Util.isRedstonePowered(this);
		if(redstoneState && !_lastRedstoneState)
		{
			worldObj.playAuxSFX(1005, xCoord, yCoord, zCoord, 0);
			if(_inventory[0] != null && _inventory[0].getItem() instanceof ItemRecord)
			{
				worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1005, xCoord, yCoord, zCoord, _inventory[0].itemID);
			}
		}
		
		_lastRedstoneState = redstoneState;
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
		return "Item Router";
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
}
