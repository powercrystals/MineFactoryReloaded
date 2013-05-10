package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.tile.TileEntityRedNetLogic;

public class ContainerRedNetLogic extends Container
{
	public ContainerRedNetLogic(TileEntityRedNetLogic logic)
	{
	}
	
	@Override
	public void putStackInSlot(int par1, ItemStack par2ItemStack)
	{
		return;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}
}
