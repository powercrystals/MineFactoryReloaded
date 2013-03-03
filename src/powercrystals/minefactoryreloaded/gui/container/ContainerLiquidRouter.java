package powercrystals.minefactoryreloaded.gui.container;

import powercrystals.minefactoryreloaded.transport.TileEntityLiquidRouter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLiquidRouter extends ContainerFactoryInventory
{
	private TileEntityLiquidRouter _router;

	public ContainerLiquidRouter(TileEntityLiquidRouter router, InventoryPlayer inventoryPlayer)
	{
		super(router, inventoryPlayer);
		_router = router;
	}
	
	@Override
	protected void addSlots()
	{
		for(int i = 0; i < 6; i++)
		{
			addSlotToContainer(new Slot(_router, i, 8 + i * 18, 20));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		return null;
	}
}
