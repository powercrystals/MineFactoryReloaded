package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import powercrystals.minefactoryreloaded.processing.TileEntityBioReactor;

public class ContainerBioReactor extends ContainerFactoryInventory
{
	public ContainerBioReactor(TileEntityBioReactor tileentity, InventoryPlayer inv)
	{
		super(tileentity, inv);
	}
	
	@Override
	protected void addSlots()
	{
		super.addSlots();
		
		for(int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(_te, 9 + i, 8 + 18 * i, 83));
		}
	}
}
