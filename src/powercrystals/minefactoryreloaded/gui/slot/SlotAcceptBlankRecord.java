package powercrystals.minefactoryreloaded.gui.slot;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAcceptBlankRecord extends Slot
{
	public SlotAcceptBlankRecord(IInventory inv, int index, int x, int y)
	{
		super(inv, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return stack != null && stack.itemID == MineFactoryReloadedCore.blankRecordItem.itemID;
	}
}
