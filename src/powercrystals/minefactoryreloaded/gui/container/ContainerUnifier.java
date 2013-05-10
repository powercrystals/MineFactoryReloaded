package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import powercrystals.minefactoryreloaded.gui.slot.SlotRemoveOnly;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;

public class ContainerUnifier extends ContainerFactoryInventory
{
	public ContainerUnifier(TileEntityFactoryInventory tileentity, InventoryPlayer inv)
	{
		super(tileentity, inv);
	}
	
	
	@Override
	protected void addSlots()
	{
		addSlotToContainer(new Slot(_te, 0, 8, 24));
		addSlotToContainer(new SlotRemoveOnly(_te, 1, 8, 54));
	}
}
