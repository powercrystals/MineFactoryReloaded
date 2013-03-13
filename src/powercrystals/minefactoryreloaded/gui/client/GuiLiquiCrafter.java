package powercrystals.minefactoryreloaded.gui.client;

import org.lwjgl.opengl.GL11;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.container.ContainerLiquiCrafter;
import powercrystals.minefactoryreloaded.processing.TileEntityLiquiCrafter;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidStack;

public class GuiLiquiCrafter extends GuiFactoryInventory
{
	private TileEntityLiquiCrafter _crafter;

	public GuiLiquiCrafter(ContainerLiquiCrafter container, TileEntityLiquiCrafter router)
	{
		super(container, router);
		_crafter = router;
		xSize = 231;
		ySize = 214;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		fontRenderer.drawString("Template", 67, 27, 4210752);
		fontRenderer.drawString("Output", 128, 26, 4210752);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		for(int i = 0; i < 9; i++)
		{
			LiquidStack l = _crafter.getTanks(ForgeDirection.UNKNOWN)[i].getLiquid();
			if(l != null)
			{
				drawTank(-50 + (i % 3 * 18), 43 + (i / 3 * 35),  l.itemID, l.itemMeta, l.amount * 33 / _crafter.getTanks(ForgeDirection.UNKNOWN)[i].getCapacity());
			}
		}
		
		int texture = mc.renderEngine.getTexture(MineFactoryReloadedCore.guiFolder + "liquicrafter.png");
		this.mc.renderEngine.bindTexture(texture);
		for(int i = 0; i < 8; i++)
		{
			this.drawTexturedModalRect(-50 + (i % 3 * 18), 10 + (i / 3 * 35), 232, 0, 16, 33);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float gameTicks, int mouseX, int mouseY)
	{
		int texture = mc.renderEngine.getTexture(MineFactoryReloadedCore.guiFolder + _tileEntity.getGuiBackground());

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2 - 56;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawTank(int xOffset, int yOffset, int liquidId, int liquidMeta, int level)
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
	
			drawTexturedModalRect(xOffset, yOffset - x - vertOffset, liquidTexX * 16, liquidTexY * 16 + (16 - x), 16, 16 - (16 - x));
			vertOffset = vertOffset + 16;
		}
	}
}
