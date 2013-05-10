package powercrystals.minefactoryreloaded.gui.client;

import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import powercrystals.minefactoryreloaded.gui.container.ContainerBioReactor;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityBioReactor;

public class GuiBioReactor extends GuiFactoryInventory
{
	private static final int _barColorBurn =  (79)  | (44 << 8)  | (63 << 16)  | (255 << 24);
	private static final int _barColorValue = (55)  | (182 << 8) | (211 << 16) | (255 << 24);
	
	public GuiBioReactor(ContainerBioReactor container, TileEntityBioReactor tileentity)
	{
		super(container, tileentity);
		ySize = 194;
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
			drawTank(132, 75, _tileEntity.getTank().getLiquid().itemID, _tileEntity.getTank().getLiquid().itemMeta, tankSize);
		}
		
		drawBar(150, 75, ((TileEntityBioReactor)_tileEntity).getOutputValueMax(), ((TileEntityBioReactor)_tileEntity).getOutputValue(), _barColorValue);
		drawBar(160, 75, ((TileEntityBioReactor)_tileEntity).getBurnTimeMax(), ((TileEntityBioReactor)_tileEntity).getBurnTime(), _barColorBurn);
	}
	
	@Override
	protected void drawTooltips(int mouseX, int mouseY)
	{
		if(isPointInRegion(132, 15, 16, 60, mouseX, mouseY) && _tileEntity.getTank() != null && _tileEntity.getTank().getLiquid().amount > 0)
		{
			drawBarTooltip(_tileEntity.getTank().getLiquid().asItemStack().getDisplayName(),
					"mB", _tileEntity.getTank().getLiquid().amount, _tileEntity.getTank().getCapacity(), mouseX, mouseY);
		}
		
		else if(isPointInRegion(151, 15, 8, 60, mouseX, mouseY))
		{
			drawBarTooltip("Efficiency", "", ((TileEntityBioReactor)_tileEntity).getOutputValue(), ((TileEntityBioReactor)_tileEntity).getOutputValueMax(), mouseX, mouseY);
		}
		else if(isPointInRegion(161, 15, 8, 60, mouseX, mouseY))
		{
			drawBarTooltip("Buffer", "", ((TileEntityBioReactor)_tileEntity).getBurnTime(), ((TileEntityBioReactor)_tileEntity).getBurnTimeMax(), mouseX, mouseY);
		}
	}
}
