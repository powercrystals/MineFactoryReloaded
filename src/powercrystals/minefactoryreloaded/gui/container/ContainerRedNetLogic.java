package powercrystals.minefactoryreloaded.gui.container;

import powercrystals.minefactoryreloaded.tile.TileEntityRedNetLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerRedNetLogic extends Container
{
	private TileEntityRedNetLogic _logic;
	
	public ContainerRedNetLogic(TileEntityRedNetLogic logic)
	{
		_logic = logic;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		// TODO
		return true;
	}
}
