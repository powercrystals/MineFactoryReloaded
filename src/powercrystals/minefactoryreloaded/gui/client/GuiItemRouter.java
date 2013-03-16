package powercrystals.minefactoryreloaded.gui.client;

import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryInventory;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityItemRouter;

public class GuiItemRouter extends GuiFactoryInventory
{
	public GuiItemRouter(ContainerFactoryInventory container, TileEntityItemRouter router)
	{
		super(container, router);
		ySize = 205;
	}
}
