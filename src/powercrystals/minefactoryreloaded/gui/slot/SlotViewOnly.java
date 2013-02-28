package powercrystals.minefactoryreloaded.gui.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotViewOnly extends Slot
{
	public SlotViewOnly(IInventory inv, int index, int x, int y)
	{
		super(inv, index, x, y);
	}
	
	@Override
	public void putStack(ItemStack stack)
	{
	}
	
	@Override
	public ItemStack decrStackSize(int par1)
	{
		return null;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}
}
