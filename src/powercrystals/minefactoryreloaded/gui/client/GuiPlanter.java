package powercrystals.minefactoryreloaded.gui.client;

import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

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
		fontRenderer.drawString("Filter", 8, 22, 4210752);
		
		//GuiFactoryPowered
		drawBar(140, 75, _tePowered.getEnergyStoredMax(), _tePowered.getEnergyStored(), _barColorEnergy);
		drawBar(150, 75, _tePowered.getWorkMax(), _tePowered.getWorkDone(), _barColorWork);
		drawBar(160, 75, _tePowered.getIdleTicksMax(), _tePowered.getIdleTicks(), _barColorIdle);
		
		//GuiFactoryInventory, tweaked slightly
		fontRenderer.drawString(_tileEntity.getInvName(), 8, 6, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 4, 4210752);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if(_tileEntity.getTank() != null && _tileEntity.getTank().getLiquid() != null)
		{
			int tankSize = _tileEntity.getTank().getLiquid().amount * _tankSizeMax / _tileEntity.getTank().getCapacity();
			drawTank(122, 75, _tileEntity.getTank().getLiquid().itemID, _tileEntity.getTank().getLiquid().itemMeta, tankSize);
		}
	}
}
