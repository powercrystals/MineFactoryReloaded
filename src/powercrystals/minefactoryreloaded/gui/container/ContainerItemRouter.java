package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityItemRouter;

public class ContainerItemRouter extends ContainerFactoryInventory
{
	public ContainerItemRouter(TileEntityItemRouter router, InventoryPlayer inventoryPlayer)
	{
		super(router, inventoryPlayer);
	}
	
	@Override
	protected void addSlots()
	{
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(_te, j + i * 9, 8 + j * 18, 20 + i * 18));
			}
		}
	}
	
	@Override
	protected int getPlayerInventoryVerticalOffset()
	{
		return 124;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		return null;
	}
}
