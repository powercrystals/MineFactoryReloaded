package powercrystals.minefactoryreloaded.gui.slot;

import powercrystals.minefactoryreloaded.api.INeedleAmmo;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAcceptNeedlegunAmmo extends Slot
{
	public SlotAcceptNeedlegunAmmo(IInventory inv, int index, int x, int y)
	{
		super(inv, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return stack != null && stack.getItem() instanceof INeedleAmmo;
	}
}
