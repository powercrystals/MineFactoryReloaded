package powercrystals.minefactoryreloaded.gui.client;

import powercrystals.minefactoryreloaded.gui.container.ContainerFruitPicker;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityFruitPicker;

public class GuiFruitPicker extends GuiFactoryPowered
{
	public GuiFruitPicker(ContainerFruitPicker container, TileEntityFruitPicker te)
	{
		super(container, te);
		ySize = 180;
	}
}
