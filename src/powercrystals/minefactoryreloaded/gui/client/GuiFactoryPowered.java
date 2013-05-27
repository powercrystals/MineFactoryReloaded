package powercrystals.minefactoryreloaded.gui.client;

import java.util.ArrayList;
import java.util.List;

import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

public class GuiFactoryPowered extends GuiFactoryInventory
{
	protected TileEntityFactoryPowered _tePowered;
	
	protected static final int _barColorEnergy = (0)   | (0 << 8)   | (255 << 16) | (255 << 24);
	protected static final int _barColorWork =   (0)   | (255 << 8) | (0 << 16)   | (255 << 24);
	protected static final int _barColorIdle =   (255) | (0 << 8)   | (0 << 16)   | (255 << 24);
	
	public GuiFactoryPowered(ContainerFactoryPowered container, TileEntityFactoryPowered te)
	{
		super(container, te);
		_tePowered = te;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		drawBar(140, 75, _tePowered.getEnergyStoredMax(), _tePowered.getEnergyStored(), _barColorEnergy);
		drawBar(150, 75, _tePowered.getWorkMax(), _tePowered.getWorkDone(), _barColorWork);
		drawBar(160, 75, _tePowered.getIdleTicksMax(), _tePowered.getIdleTicks(), _barColorIdle);
		
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
	
	@Override
	protected void drawTooltips(int mouseX, int mouseY)
	{
		super.drawTooltips(mouseX, mouseY);
		
		if(isPointInRegion(141, 15, 8, 60, mouseX, mouseY))
		{
			List<String> lines = new ArrayList<String>();
			lines.add("Energy");
			lines.add(_tePowered.getEnergyStored() / TileEntityFactoryPowered.energyPerMJ + " / " + _tePowered.getEnergyStoredMax() / TileEntityFactoryPowered.energyPerMJ + " " + "MJ");
			lines.add(_tePowered.getEnergyStored() / TileEntityFactoryPowered.energyPerEU + " / " + _tePowered.getEnergyStoredMax() / TileEntityFactoryPowered.energyPerEU + " " + "EU");
			lines.add(_tePowered.getEnergyStored() * TileEntityFactoryPowered.wPerEnergy / 1000 + " / " +
					_tePowered.getEnergyStoredMax() * TileEntityFactoryPowered.wPerEnergy / 1000 + " " + "KJ");
			drawTooltip(lines, mouseX, mouseY);
		}
		else if(isPointInRegion(151, 15, 8, 60, mouseX, mouseY))
		{
			drawBarTooltip("Work", "Wk", _tePowered.getWorkDone(), _tePowered.getWorkMax(), mouseX, mouseY);
		}
		else if(isPointInRegion(161, 15, 8, 60, mouseX, mouseY))
		{
			drawBarTooltip("Idle", "t", _tePowered.getIdleTicks(), _tePowered.getIdleTicksMax(), mouseX, mouseY);
		}
	}
}
