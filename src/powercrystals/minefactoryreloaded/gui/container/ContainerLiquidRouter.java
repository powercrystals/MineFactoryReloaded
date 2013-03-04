package powercrystals.minefactoryreloaded.gui.container;

import powercrystals.minefactoryreloaded.transport.TileEntityLiquidRouter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLiquidRouter extends ContainerFactoryInventory
{
	public ContainerLiquidRouter(TileEntityLiquidRouter router, InventoryPlayer inventoryPlayer)
	{
		super(router, inventoryPlayer);
	}
	
	@Override
	protected void addSlots()
	{
		for(int i = 0; i < 6; i++)
		{
			addSlotToContainer(new Slot(_te, i, 8 + i * 18, 20));
		}
	}
	
	@Override
	protected int getPlayerInventoryVerticalOffset()
	{
		return 52;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		return null;
	}
}
