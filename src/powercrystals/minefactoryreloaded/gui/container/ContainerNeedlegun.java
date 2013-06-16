package powercrystals.minefactoryreloaded.gui.container;

import powercrystals.minefactoryreloaded.gui.NeedlegunContainerWrapper;
import powercrystals.minefactoryreloaded.gui.slot.SlotAcceptNeedlegunAmmo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNeedlegun extends Container
{
	private NeedlegunContainerWrapper _ncw;
	
	public ContainerNeedlegun(NeedlegunContainerWrapper ncw, InventoryPlayer inv)
	{
		_ncw = ncw;
		addSlotToContainer(new SlotAcceptNeedlegunAmmo(_ncw, 0, 80, 30));
		bindPlayerInventory(inv);
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 84 + 58));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		return null;
	}
	
	@Override
	public void onCraftGuiClosed(EntityPlayer player)
	{
		super.onCraftGuiClosed(player);
		player.inventory.mainInventory[player.inventory.currentItem] = _ncw.getStack();
	}
}
