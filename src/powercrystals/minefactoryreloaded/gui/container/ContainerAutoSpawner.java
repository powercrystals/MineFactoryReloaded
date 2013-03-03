package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import powercrystals.minefactoryreloaded.animals.TileEntityAutoSpawner;

public class ContainerAutoSpawner extends ContainerFactoryInventory
{
	public ContainerAutoSpawner(TileEntityAutoSpawner spawner, InventoryPlayer inv)
	{
		super(spawner, inv);
	}
	
	@Override
	protected void addSlots()
	{
		addSlotToContainer(new Slot((TileEntityAutoSpawner)_te, 0, 8, 24));
	}
}
