package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;

public class ContainerUpgradable extends ContainerFactoryPowered
{
	public ContainerUpgradable(TileEntityFactoryPowered te, InventoryPlayer inv)
	{
		super(te, inv);
	}
	
	@Override
	protected void addSlots()
	{
		super.addSlots();
		
		addSlotToContainer(new Slot(_te, 9, 152, 79));
	}
	
	@Override
	protected int getPlayerInventoryVerticalOffset()
	{
		return 99;
	}
}
