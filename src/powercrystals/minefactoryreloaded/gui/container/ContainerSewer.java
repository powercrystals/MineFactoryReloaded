package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryInventory;
import powercrystals.minefactoryreloaded.gui.slot.SlotAcceptUpgrade;

public class ContainerSewer extends ContainerFactoryInventory
{
	public ContainerSewer(TileEntityFactoryInventory te, InventoryPlayer inv)
	{
		super(te, inv);
	}
	
	@Override
	protected void addSlots()
	{
		addSlotToContainer(new SlotAcceptUpgrade(_te, 0, 152, 79));
	}
	
	@Override
	protected int getPlayerInventoryVerticalOffset()
	{
		return 99;
	}
}
