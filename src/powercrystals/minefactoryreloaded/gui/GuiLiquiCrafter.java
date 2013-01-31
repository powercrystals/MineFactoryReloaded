package powercrystals.minefactoryreloaded.gui;

import org.lwjgl.opengl.GL11;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.processing.TileEntityLiquiCrafter;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidStack;

public class GuiLiquiCrafter extends GuiContainer
{
	private TileEntityLiquiCrafter _crafter;

	public GuiLiquiCrafter(Container container, TileEntityLiquiCrafter router)
	{
		super(container);
		_crafter = router;
		ySize = 256;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		fontRenderer.drawString(_crafter.getInvName(), 8, 6, 4210752);
		fontRenderer.drawString("Template", 67, 27, 4210752);
		fontRenderer.drawString("Output", 128, 26, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 95 + 2, 4210752);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		for(int i = 0; i < 9; i++)
		{
			LiquidStack l = _crafter.getTanks(ForgeDirection.UNKNOWN)[i].getLiquid();
			if(l == null)
			{
				drawTank(0, 0, 0, i);
			}
			else
			{
				drawTank(l.itemID, l.itemMeta, l.amount * 33 / _crafter.getTanks(ForgeDirection.UNKNOWN)[i].getCapacity(), i);
			}
		}
		
		int texture = mc.renderEngine.getTexture(MineFactoryReloadedCore.guiFolder + "liquicrafter.png");
		this.mc.renderEngine.bindTexture(texture);	
		for (int i=0; i<8; i++) {
			int x = (8 + (i * 18));
			this.drawTexturedModalRect(x, 128, 176, 0, 16, 33);
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		int texture = mc.renderEngine.getTexture(MineFactoryReloadedCore.guiFolder + "liquicrafter.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	private void drawTank(int liquidId, int liquidMeta, int level, int tankIndex)
	{
		int liquidTexture = 0;

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
	
			drawTexturedModalRect(8 + 18 * tankIndex, 161 - x - vertOffset, liquidTexX * 16, liquidTexY * 16 + (16 - x), 16, 16 - (16 - x));
			vertOffset = vertOffset + 16;
		}
	}
}
