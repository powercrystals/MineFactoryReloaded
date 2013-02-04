package powercrystals.minefactoryreloaded.gui;

import org.lwjgl.opengl.GL11;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.ForgeHooksClient;

public class GuiFactoryPowered extends GuiContainer
{
	protected TileEntityFactoryPowered _tePowered;
	
	private static final int _barSizeMax = 60;
	private static final int _barColorEnergy = (0) | (0) | (255 << 16) | (255 << 24);
	private static final int _barColorWork =   (0) | (255 << 8) | (0 << 16) | (255 << 24);
	private static final int _barColorIdle =   (255) | (0 << 8) | (0 << 16) | (255 << 24);
	
	public GuiFactoryPowered(ContainerFactoryPowered container, TileEntityFactoryPowered te)
	{
		super(container);
		_tePowered = te;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		fontRenderer.drawString(_tePowered.getInvName(), 8, 6, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		
		drawBars();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float gameTicks, int mouseX, int mouseY)
	{
		int texture;
		if(_tePowered.getTank() == null)
		{
			texture = mc.renderEngine.getTexture(MineFactoryReloadedCore.guiFolder + "noinv.png");
		}
		else
		{
			texture = mc.renderEngine.getTexture(MineFactoryReloadedCore.guiFolder + "noinvtank.png");
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
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
