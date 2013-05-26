package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import powercrystals.minefactoryreloaded.gui.slot.SlotAcceptUpgrade;
import powercrystals.minefactoryreloaded.gui.slot.SlotFake;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

public class ContainerPlanter extends ContainerUpgradable
{
	public ContainerPlanter(TileEntityFactoryPowered te, InventoryPlayer inv)
	{
		super(te, inv);
	}
	
	@Override
	protected void addSlots()
	{
		//area control slots
		addSlotToContainer(new SlotFake(_te, 0, 8, 33));
		addSlotToContainer(new SlotFake(_te, 1, 26, 33));
		addSlotToContainer(new SlotFake(_te, 2, 44, 33));
		addSlotToContainer(new SlotFake(_te, 3, 8, 51));
		addSlotToContainer(new SlotFake(_te, 4, 26, 51));
		addSlotToContainer(new SlotFake(_te, 5, 44, 51));
		addSlotToContainer(new SlotFake(_te, 6, 8, 69));
		addSlotToContainer(new SlotFake(_te, 7, 26, 69));
		addSlotToContainer(new SlotFake(_te, 8, 44, 69));
		
		//upgrade slot
		addSlotToContainer(new SlotAcceptUpgrade(_te, 9, 152, 79));
		
		//resource slots
		int xStart = 65;
		int yStart = 15;
		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				addSlotToContainer(new Slot(_te, 10 + i*4 + j, xStart + 18 * j, yStart + 18*i));
			}
		}
	}
}
