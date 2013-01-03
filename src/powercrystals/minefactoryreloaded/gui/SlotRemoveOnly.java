package powercrystals.minefactoryreloaded.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotRemoveOnly extends Slot
{
	public SlotRemoveOnly(IInventory inv, int i, int j, int k)
	{
		super(inv, i, j, k);
	}
	
	@Override
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		return false;
	}
}
