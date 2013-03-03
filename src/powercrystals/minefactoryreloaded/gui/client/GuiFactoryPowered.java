package powercrystals.minefactoryreloaded.gui.client;

import org.lwjgl.opengl.GL11;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;

public class GuiFactoryPowered extends GuiFactoryInventory
{
	protected TileEntityFactoryPowered _tePowered;
	
	private static final int _barSizeMax = 60;
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
		
		drawBars();
	}
	
	protected void drawBars()
	{
		int energySize = _tePowered.getEnergyStoredMax() > 0 ? _tePowered.getEnergyStored() * _barSizeMax / _tePowered.getEnergyStoredMax() : 0;
		int workSize = _tePowered.getWorkMax() > 0 ? _tePowered.getWorkDone() * _barSizeMax / _tePowered.getWorkMax() : 0;
		int idleSize = _tePowered.getIdleTicksMax() > 0 ? _tePowered.getIdleTicks() * _barSizeMax / _tePowered.getIdleTicksMax() : 0;
		
		drawRect(142, 75 - energySize, 150, 75, _barColorEnergy);
		drawRect(152, 75 - workSize, 160, 75, _barColorWork);
		drawRect(162, 75 - idleSize, 170, 75, _barColorIdle);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if(_tePowered.getTank() != null && _tePowered.getTank().getLiquid() != null)
		{
			int tankSize = _tePowered.getTank().getLiquid().amount * _barSizeMax / _tePowered.getTank().getCapacity();
			drawTank(_tePowered.getTank().getLiquid().itemID, _tePowered.getTank().getLiquid().itemMeta, tankSize);
		}
	}
	
	private void drawTank(int liquidId, int liquidMeta, int level)
	{
		int liquidTexture = 0;
		int gaugeTexture = mc.renderEngine.getTexture(MineFactoryReloadedCore.guiFolder + "noinvtank.png");

		if (liquidId <= 0)
		{
			return;
		}
		if (liquidId < Block.blocksList.length && Block.blocksList[liquidId] != null)
		{
			ForgeHooksClient.bindTexture(Block.blocksList[liquidId].getTextureFile(), 0);
			liquidTexture = Block.blocksList[liquidId].blockIndexInTexture;
		}
		else if	(Item.itemsList[liquidId] != null)
		{
			ForgeHooksClient.bindTexture(Item.itemsList[liquidId].getTextureFile(), 0);
			liquidTexture = Item.itemsList[liquidId].getIconFromDamage(liquidMeta);
		}
		else
		{
			return;
		}

		int liquidTexY = liquidTexture / 16;
		int liquidTexX = liquidTexture - liquidTexY * 16;

		int vertOffset = 0;

		while(level > 0)
		{
			int x = 0;
	
			if (level > 16)
			{
				x = 16;
				level -= 16;
			}
			else
			{
				x = level;
				level = 0;
			}
	
			drawTexturedModalRect(124, 75 - x - vertOffset, liquidTexX * 16, liquidTexY * 16 + (16 - x), 16, 16 - (16 - x));
			vertOffset = vertOffset + 16;
		}
		
		this.mc.renderEngine.bindTexture(gaugeTexture);
		this.drawTexturedModalRect(124, 15, 176, 0, 16, 60);
	}
}
