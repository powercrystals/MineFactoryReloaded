package powercrystals.minefactoryreloaded.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFilter extends Slot
{
	public SlotFilter(IInventory inv, int index, int x, int y)
	{
		super(inv, index, x, y);
	}
	
	@Override
	public void putStack(ItemStack stack)
	{
		if(stack != null)
		{
			ItemStack filterStack = stack.copy();
			filterStack.stackSize = 1;
			inventory.setInventorySlotContents(getSlotIndex(), filterStack);
	        this.onSlotChanged();
		}
		else
		{
			inventory.setInventorySlotContents(getSlotIndex(), null);
	        this.onSlotChanged();
		}
	}
	
	@Override
	public ItemStack decrStackSize(int size)
	{
		inventory.setInventorySlotContents(getSlotIndex(), null);
		return null;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return true;
	}
}
