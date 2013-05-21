package powercrystals.minefactoryreloaded.gui.client;

import powercrystals.minefactoryreloaded.gui.container.ContainerFruitPicker;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityFruitPicker;

public class GuiFactoryFruitPicker extends GuiFactoryPowered
{
	public GuiFactoryFruitPicker(ContainerFruitPicker container, TileEntityFruitPicker te)
	{
		super(container, te);
		ySize = 180;
	}
}
