package powercrystals.minefactoryreloaded.gui.client;

import powercrystals.minefactoryreloaded.gui.container.ContainerUpgradable;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

public class GuiUpgradable extends GuiFactoryPowered
{
	public GuiUpgradable(ContainerUpgradable container, TileEntityFactoryPowered te)
	{
		super(container, te);
		ySize = 180;
	}
}
