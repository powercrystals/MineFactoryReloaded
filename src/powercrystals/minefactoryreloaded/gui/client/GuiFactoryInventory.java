package powercrystals.minefactoryreloaded.gui.client;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryInventory;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;

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
	protected void drawGuiContainerBackgroundLayer(float gameTicks, int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.func_98187_b(MineFactoryReloadedCore.guiFolder + _tileEntity.getGuiBackground());
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float gameTicks)
	{
		super.drawScreen(mouseX, mouseY, gameTicks);
		
		drawTooltips(mouseX, mouseY);
	}
	
	protected void drawTooltips(int mouseX, int mouseY)
	{
		if(isPointInRegion(122, 15, 16, 60, mouseX, mouseY) && _tileEntity.getTank() != null && _tileEntity.getTank().getLiquid().amount > 0)
		{
			drawBarTooltip(_tileEntity.getTank().getLiquid().asItemStack().getDisplayName(),
					"mB", _tileEntity.getTank().getLiquid().amount, _tileEntity.getTank().getCapacity(), mouseX, mouseY);
		}
	}
	
	protected void drawBar(int xOffset, int yOffset, int max, int current, int color)
	{
		int size = max > 0 ? current * _barSizeMax / max : 0;
		drawRect(xOffset, yOffset - size, xOffset + 8, yOffset, color);
	}
	
	protected void drawTank(int xOffset, int yOffset, int liquidId, int liquidMeta, int level)
	{
		Icon liquidTexture;
		ItemStack tempStack = new ItemStack(liquidId, 1, liquidMeta);

		if(liquidId <= 0)
		{
			return;
		}
		if(liquidId < Block.blocksList.length && Block.blocksList[liquidId] != null)
		{
			liquidTexture = Block.blocksList[liquidId].getBlockTextureFromSide(0);
		}
		else if	(Item.itemsList[liquidId] != null)
		{
			liquidTexture = Item.itemsList[liquidId].getIconFromDamage(liquidMeta);
		}
		else
		{
			return;
		}

		int vertOffset = 0;

		while(level > 0)
		{
			int texHeight = 0;
	
			if (level > 16)
			{
				texHeight = 16;
				level -= 16;
			}
			else
			{
				texHeight = level;
				level = 0;
			}
			
			if(tempStack.func_94608_d() == 0)
			{
				this.mc.renderEngine.func_98187_b("/terrain.png");
			}
			else
			{
				this.mc.renderEngine.func_98187_b("/gui/items.png");
			}
			
			this.func_94065_a(xOffset, yOffset - texHeight - vertOffset, liquidTexture, 16, texHeight);
			vertOffset = vertOffset + 16;
		}

		this.mc.renderEngine.func_98187_b(MineFactoryReloadedCore.guiFolder + _tileEntity.getGuiBackground());
		this.drawTexturedModalRect(xOffset, yOffset - 60, 176, 0, 16, 60);
	}

	protected void drawBarTooltip(String name, String unit, int value, int max, int x, int y)
	{
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glDisable(GL11.GL_LIGHTING);
		
		List<String> stringList = new LinkedList<String>();
		stringList.add(name);
		stringList.add(value + " / " + max + " " + unit);

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

		xStart = x + 12;
		yStart = y - 12;
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
		itemRenderer.zLevel = 300.0F;
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
		
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_DEPTH_TEST);

		this.zLevel = 0.0F;
		itemRenderer.zLevel = 0.0F;
	}
}
