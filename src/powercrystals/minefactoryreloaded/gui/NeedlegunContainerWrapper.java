package powercrystals.minefactoryreloaded.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NeedlegunContainerWrapper implements IInventory
{
	private ItemStack _stack;
	
	public NeedlegunContainerWrapper(ItemStack stack)
	{
		_stack = stack;
	}
	
	public ItemStack getStack()
	{
		return _stack;
	}

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		if(_stack.getTagCompound().getCompoundTag("ammo") == null || _stack.getTagCompound().getCompoundTag("ammo").hasNoTags())
		{
			return null;
		}
		else
		{
			ItemStack s = new ItemStack(0, 0, 0);
			s.readFromNBT(_stack.getTagCompound().getCompoundTag("ammo"));
			return s;
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		ItemStack s = new ItemStack(0, 0, 0);
		s.readFromNBT(_stack.getTagCompound().getCompoundTag("ammo"));
		s.stackSize -= j;
		if(s.stackSize <= 0)
		{
			_stack.getTagCompound().setCompoundTag("ammo", new NBTTagCompound());
			s = null;
		}
		else
		{
			NBTTagCompound t = new NBTTagCompound();
			s.writeToNBT(t);
			_stack.getTagCompound().setCompoundTag("ammo", t);
		}
		return s;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		if(itemstack == null)
		{
			_stack.getTagCompound().setCompoundTag("ammo", new NBTTagCompound());
		}
		else
		{
			NBTTagCompound t = new NBTTagCompound();
			itemstack.writeToNBT(t);
			_stack.getTagCompound().setCompoundTag("ammo", t);
		}
	}

	@Override
	public String getInvName()
	{
		return "Needlegun";
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public void onInventoryChanged()
	{
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
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
	public boolean isStackValidForSlot(int i, ItemStack itemstack)
	{
		return true;
	}
}
