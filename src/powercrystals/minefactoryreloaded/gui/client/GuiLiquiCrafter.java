package powercrystals.minefactoryreloaded.gui.client;

import org.lwjgl.opengl.GL11;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryInventory;
import powercrystals.minefactoryreloaded.processing.TileEntityLiquiCrafter;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidStack;

public class GuiLiquiCrafter extends GuiFactoryInventory
{
	private TileEntityLiquiCrafter _crafter;

	public GuiLiquiCrafter(ContainerFactoryInventory container, TileEntityLiquiCrafter router)
	{
		super(container, router);
		_crafter = router;
		ySize = 256;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		fontRenderer.drawString(_crafter.getInvName(), 8, 6, 4210752);
		fontRenderer.drawString("Template", 67, 27, 4210752);
		fontRenderer.drawString("Output", 128, 26, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 137 + 2, 4210752);
		
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
		for(int i=0; i<8; i++)
		{
			switch (i)
			{
				case 0:
					this.drawTexturedModalRect(6 - 56, 10, 232, 0, 16, 33);
				case 1:
					this.drawTexturedModalRect(24 - 56, 10, 232, 0, 16, 33);
				case 2:
					this.drawTexturedModalRect(42 - 56, 10, 232, 0, 16, 33);
				case 3:
					this.drawTexturedModalRect(6 - 56, 45, 232, 0, 16, 33);
				case 4:
					this.drawTexturedModalRect(24 - 56, 45, 232, 0, 16, 33);
				case 5:
					this.drawTexturedModalRect(42 - 56, 45, 232, 0, 16, 33);
				case 6:
					this.drawTexturedModalRect(6 - 56, 80, 232, 0, 16, 33);
				case 7:
					this.drawTexturedModalRect(24 - 56, 80, 232, 0, 16, 33);
				case 8:
					this.drawTexturedModalRect(42 - 56, 80, 232, 0, 16, 33);
			}
		}
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
			int xIndex = 0;
			int yCoord = 0;
			if(tankIndex < 3)
			{
				xIndex = tankIndex;
				yCoord = 43;
			}
			else if (tankIndex >= 3 && tankIndex <= 5)
			{
				xIndex = tankIndex - 3;
				yCoord = 78;
			}
			else
			{
				xIndex = tankIndex - 6;
				yCoord = 113;
			}
			drawTexturedModalRect(8 - 58 + 18 * xIndex, yCoord - x - vertOffset, liquidTexX * 16, liquidTexY * 16 + (16 - x), 16, 16 - (16 - x));
			vertOffset = vertOffset + 16;
		}
	}
}
