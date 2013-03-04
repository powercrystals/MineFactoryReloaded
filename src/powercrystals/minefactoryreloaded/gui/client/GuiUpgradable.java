package powercrystals.minefactoryreloaded.gui.client;

import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;
import powercrystals.minefactoryreloaded.gui.container.ContainerUpgradable;

public class GuiUpgradable extends GuiFactoryPowered
{
	public GuiUpgradable(ContainerUpgradable container, TileEntityFactoryPowered te)
	{
		super(container, te);
		ySize = 180;
	}
}
