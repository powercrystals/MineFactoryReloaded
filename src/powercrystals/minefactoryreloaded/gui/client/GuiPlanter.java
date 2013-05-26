package powercrystals.minefactoryreloaded.gui.client;

import powercrystals.minefactoryreloaded.gui.container.ContainerUpgradable;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

public class GuiPlanter extends GuiUpgradable
{

	public GuiPlanter(ContainerUpgradable container, TileEntityFactoryPowered te)
	{
		super(container, te);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		fontRenderer.drawString("Filter", 8, 24, 4210752);
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
}
