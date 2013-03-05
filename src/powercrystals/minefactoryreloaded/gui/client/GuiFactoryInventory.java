package powercrystals.minefactoryreloaded.gui.client;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.ForgeHooksClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryInventory;

public class GuiFactoryInventory extends GuiContainer
{
	protected TileEntityFactoryInventory _tileEntity;
	protected int _barSizeMax = 60;
	protected int _tankSizeMax = 61;
	
	public GuiFactoryInventory(ContainerFactoryInventory container, TileEntityFactoryInventory tileentity)
	{
		super(container);
		_tileEntity = tileentity;
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
			drawTank(122, 75, _tileEntity.getTank().getLiquid().itemID, _tileEntity.getTank().getLiquid().itemMeta, tankSize);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float mouseX, int mouseY, int gameTicks)
	{
		int texture = mc.renderEngine.getTexture(MineFactoryReloadedCore.guiFolder + _tileEntity.getGuiBackground());

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	protected void drawBar(int xOffset, int yOffset, int max, int current, int color)
	{
		int size = max > 0 ? current * _barSizeMax / max : 0;
		drawRect(xOffset, yOffset - size, xOffset + 8, yOffset, color);
	}
	
	protected void drawTank(int xOffset, int yOffset, int liquidId, int liquidMeta, int level)
	{
		int liquidTexture = 0;
		int gaugeTexture = mc.renderEngine.getTexture(MineFactoryReloadedCore.guiFolder + _tileEntity.getGuiBackground());

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
	
			drawTexturedModalRect(xOffset, yOffset - x - vertOffset, liquidTexX * 16, liquidTexY * 16 + (16 - x), 16, 16 - (16 - x));
			vertOffset = vertOffset + 16;
		}
		
		this.mc.renderEngine.bindTexture(gaugeTexture);
		this.drawTexturedModalRect(xOffset, yOffset - 60, 176, 0, 16, 60);
	}

	protected void drawBarTooltip(String name, String unit, int value, int max, int x, int y)
	{
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		List<String> stringList = new LinkedList<String>();
		stringList.add(name);
		stringList.add(value + " / " + max);

		int tooltipWidth = 0;
		int tempWidth;
		int xStart;
		int yStart;

		for(int i = 0; i < stringList.size(); i++)
		{
			tempWidth = this.fontRenderer.getStringWidth(stringList.get(i));

			if(tempWidth > tooltipWidth)
			{
				tooltipWidth = tempWidth;
			}
		}

		xStart = x + 12 - this.guiLeft;
		yStart = y - 12 - this.guiTop;
		int tooltipHeight = 8;

		if(stringList.size() > 1)
		{
			tooltipHeight += 2 + (stringList.size() - 1) * 10;
		}

		if(this.guiTop + yStart + tooltipHeight + 6 > this.height)
		{
			yStart = this.height - tooltipHeight - this.guiTop - 6;
		}

		this.zLevel = 300.0F;
		int color1 = -267386864;
		this.drawGradientRect(xStart - 3, yStart - 4, xStart + tooltipWidth + 3, yStart - 3, color1, color1);
		this.drawGradientRect(xStart - 3, yStart + tooltipHeight + 3, xStart + tooltipWidth + 3, yStart + tooltipHeight + 4, color1, color1);
		this.drawGradientRect(xStart - 3, yStart - 3, xStart + tooltipWidth + 3, yStart + tooltipHeight + 3, color1, color1);
		this.drawGradientRect(xStart - 4, yStart - 3, xStart - 3, yStart + tooltipHeight + 3, color1, color1);
		this.drawGradientRect(xStart + tooltipWidth + 3, yStart - 3, xStart + tooltipWidth + 4, yStart + tooltipHeight + 3, color1, color1);
		int color2 = 1347420415;
		int color3 = (color2 & 16711422) >> 1 | color2 & -16777216;
		this.drawGradientRect(xStart - 3, yStart - 3 + 1, xStart - 3 + 1, yStart + tooltipHeight + 3 - 1, color2, color3);
		this.drawGradientRect(xStart + tooltipWidth + 2, yStart - 3 + 1, xStart + tooltipWidth + 3, yStart + tooltipHeight + 3 - 1, color2, color3);
		this.drawGradientRect(xStart - 3, yStart - 3, xStart + tooltipWidth + 3, yStart - 3 + 1, color2, color2);
		this.drawGradientRect(xStart - 3, yStart + tooltipHeight + 2, xStart + tooltipWidth + 3, yStart + tooltipHeight + 3, color3, color3);

		for(int stringIndex = 0; stringIndex < stringList.size(); ++stringIndex)
		{
			String line = stringList.get(stringIndex);

			if(stringIndex == 0)
			{
				line = "\u00a7" + Integer.toHexString(15) + line;
			}
			else
			{
				line = "\u00a77" + line;
			}

			this.fontRenderer.drawStringWithShadow(line, xStart, yStart, -1);

			if(stringIndex == 0)
			{
				yStart += 2;
			}

			yStart += 10;
		}

		this.zLevel = 0.0F;
	}
}
