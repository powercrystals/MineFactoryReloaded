package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import powercrystals.minefactoryreloaded.animals.TileEntityAutoSpawner;
import powercrystals.minefactoryreloaded.processing.TileEntityAutoEnchanter;

public class ContainerAutoSpawner extends ContainerFactoryPowered
{
	public ContainerAutoSpawner(TileEntityAutoSpawner te, InventoryPlayer inv)
	{
		super(te, inv);
	}
	
	@Override
	protected void addSlots()
	{
		addSlotToContainer(new Slot((TileEntityAutoEnchanter)_te, 0, 8, 24));
	}
}
