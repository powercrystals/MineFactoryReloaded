package powercrystals.minefactoryreloaded.gui.container;

import powercrystals.minefactoryreloaded.tile.TileEntityRedNetLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerRedNetLogic extends Container
{
	public ContainerRedNetLogic(TileEntityRedNetLogic logic)
	{
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}
}
