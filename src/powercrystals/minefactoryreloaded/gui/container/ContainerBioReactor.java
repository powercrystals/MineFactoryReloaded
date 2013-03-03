package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import powercrystals.minefactoryreloaded.gui.slot.SlotRemoveOnly;
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
			addSlotToContainer(new SlotRemoveOnly(_te, 9 + i, 8 + 18 * i, 83));
		}
	}
	
	@Override
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
					addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 113 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 171));
		}
	}
}
