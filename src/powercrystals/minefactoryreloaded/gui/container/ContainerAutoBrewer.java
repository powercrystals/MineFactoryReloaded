package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import powercrystals.minefactoryreloaded.gui.slot.SlotFake;
import powercrystals.minefactoryreloaded.gui.slot.SlotPotionIngredient;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

public class ContainerAutoBrewer extends ContainerFactoryPowered
{
	public ContainerAutoBrewer(TileEntityFactoryPowered te, InventoryPlayer inv)
	{
		super(te, inv);
	}
	
	@Override
	protected void addSlots()
	{
		for(int row = 0; row < 6; row++)
		{
			addSlotToContainer(new Slot(_te, row * 5, 8, 34 + row * 18));
			addSlotToContainer(new SlotFake(_te, row * 5 + 1, 44, 34 + row * 18));
			addSlotToContainer(new SlotPotionIngredient(_te, row * 5 + 2, 80, 34 + row * 18));
			addSlotToContainer(new SlotPotionIngredient(_te, row * 5 + 3, 98, 34 + row * 18));
			addSlotToContainer(new SlotPotionIngredient(_te, row * 5 + 4, 116, 34 + row * 18));
		}
		addSlotToContainer(new Slot(_te, 30, 8, 142));
	}
	
	@Override
	protected int getPlayerInventoryVerticalOffset()
	{
		return 174;
	}
}
