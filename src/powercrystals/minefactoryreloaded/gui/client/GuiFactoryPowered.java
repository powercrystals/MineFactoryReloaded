package powercrystals.minefactoryreloaded.gui.client;

import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;

public class GuiFactoryPowered extends GuiFactoryInventory
{
	protected TileEntityFactoryPowered _tePowered;
	
	private static final int _barColorEnergy = (0) | (0) | (255 << 16) | (255 << 24);
	private static final int _barColorWork =   (0) | (255 << 8) | (0 << 16) | (255 << 24);
	private static final int _barColorIdle =   (255) | (0 << 8) | (0 << 16) | (255 << 24);
	
	public GuiFactoryPowered(ContainerFactoryPowered container, TileEntityFactoryPowered te)
	{
		super(container, te);
		_tePowered = te;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		drawBar(142, 75, _tePowered.getEnergyStoredMax(), _tePowered.getEnergyStored(), _barColorEnergy);
		drawBar(152, 75, _tePowered.getWorkMax(), _tePowered.getWorkDone(), _barColorWork);
		drawBar(162, 75, _tePowered.getIdleTicksMax(), _tePowered.getIdleTicks(), _barColorIdle);
	}
}
