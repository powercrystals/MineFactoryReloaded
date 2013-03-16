package powercrystals.minefactoryreloaded.gui.client;

import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryInventory;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityBioFuelGenerator;

public class GuiBioFuelGenerator extends GuiFactoryInventory
{
	private static final int _barColorEnergy = (0)   | (0 << 8)   | (255 << 16) | (255 << 24);
	
	public GuiBioFuelGenerator(ContainerFactoryInventory container, TileEntityBioFuelGenerator tileentity)
	{
		super(container, tileentity);
		ySize = 165;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		fontRenderer.drawString(_tileEntity.getInvName(), 8, 6, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if(_tileEntity.getTank() != null && _tileEntity.getTank().getLiquid() != null)
		{
			int tankSize = _tileEntity.getTank().getLiquid().amount * _tankSizeMax / _tileEntity.getTank().getCapacity();
			drawTank(142, 75, _tileEntity.getTank().getLiquid().itemID, _tileEntity.getTank().getLiquid().itemMeta, tankSize);
		}

		drawBar(160, 75, ((TileEntityBioFuelGenerator)_tileEntity).getBufferMax(), ((TileEntityBioFuelGenerator)_tileEntity).getBuffer(), _barColorEnergy);
	}
	
	@Override
	protected void drawTooltips(int mouseX, int mouseY)
	{
		if(isPointInRegion(142, 15, 16, 60, mouseX, mouseY) && _tileEntity.getTank() != null && _tileEntity.getTank().getLiquid().amount > 0)
		{
			drawBarTooltip(_tileEntity.getTank().getLiquid().asItemStack().getDisplayName(),
					"mB", _tileEntity.getTank().getLiquid().amount, _tileEntity.getTank().getCapacity(), mouseX, mouseY);
		}

		else if(isPointInRegion(161, 15, 8, 60, mouseX, mouseY))
		{
			drawBarTooltip("Energy", "MJ", ((TileEntityBioFuelGenerator)_tileEntity).getBuffer(), ((TileEntityBioFuelGenerator)_tileEntity).getBufferMax(), mouseX, mouseY);
		}
	}
}
