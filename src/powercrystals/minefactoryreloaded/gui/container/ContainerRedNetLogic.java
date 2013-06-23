package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.tile.rednet.TileEntityRedNetLogic;

public class ContainerRedNetLogic extends Container
{
	private TileEntityRedNetLogic logic;
	
	public ContainerRedNetLogic(TileEntityRedNetLogic logic)
	{
		this.logic = logic;
		logic.crafters++;
	}
	
	@Override
	public void onCraftGuiClosed(EntityPlayer entityplayer)
	{
		super.onCraftGuiClosed(entityplayer);
		logic.crafters--;
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
