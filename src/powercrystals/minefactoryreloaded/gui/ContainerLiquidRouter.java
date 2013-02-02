package powercrystals.minefactoryreloaded.gui;

import powercrystals.minefactoryreloaded.transport.TileEntityLiquidRouter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLiquidRouter extends Container
{
	private TileEntityLiquidRouter _router;

	public ContainerLiquidRouter(TileEntityLiquidRouter router, InventoryPlayer inventoryPlayer)
	{
		super();
		bindPlayerInventory(inventoryPlayer);
		_router = router;
		for(int i = 0; i < 6; i++)
		{
			addSlotToContainer(new Slot(_router, i, 8 + i * 18, 20));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return _router.isUseableByPlayer(entityplayer);
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
					addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 52 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 110));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		return null;
	}
}
