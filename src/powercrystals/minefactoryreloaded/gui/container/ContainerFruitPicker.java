package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import powercrystals.minefactoryreloaded.gui.slot.SlotAcceptUpgrade;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityFruitPicker;

public class ContainerFruitPicker extends ContainerUpgradable
{
	public ContainerFruitPicker(TileEntityFruitPicker te, InventoryPlayer inv)
	{
		super(te, inv);
	}
	
	@Override
	protected void addSlots()
	{
		addSlotToContainer(new SlotAcceptUpgrade(_te, 0, 152, 79));
	}
}
