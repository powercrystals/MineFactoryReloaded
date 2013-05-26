package powercrystals.minefactoryreloaded.gui.client;

import powercrystals.minefactoryreloaded.gui.container.ContainerUnifier;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityUnifier;

public class GuiUnifier extends GuiFactoryInventory
{
	public GuiUnifier(ContainerUnifier container, TileEntityUnifier te)
	{
		super(container, te);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		fontRenderer.drawString("Preferences", 48, 14, 4210752);
	}
}
