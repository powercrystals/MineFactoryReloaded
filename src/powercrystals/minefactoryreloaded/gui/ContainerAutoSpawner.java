package powercrystals.minefactoryreloaded.gui;

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
	protected void addSlots(InventoryPlayer inv)
	{
		addSlotToContainer(new Slot((TileEntityAutoSpawner)_te, 0, 8, 24));
		
		bindPlayerInventory(inv);
	}
}
