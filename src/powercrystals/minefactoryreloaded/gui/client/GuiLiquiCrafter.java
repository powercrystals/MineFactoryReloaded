package powercrystals.minefactoryreloaded.gui.client;

import org.lwjgl.opengl.GL11;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.container.ContainerLiquiCrafter;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityLiquiCrafter;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidDictionary;
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
		
		this.mc.renderEngine.bindTexture(MineFactoryReloadedCore.guiFolder + "liquicrafter.png");
		for(int i = 0; i < 8; i++)
		{
			this.drawTexturedModalRect(-50 + (i % 3 * 18), 10 + (i / 3 * 35), 232, 0, 16, 33);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float gameTicks, int mouseX, int mouseY)
	{

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(MineFactoryReloadedCore.guiFolder + _tileEntity.getGuiBackground());
		int x = (width - xSize) / 2 - 56;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawTank(int xOffset, int yOffset, int liquidId, int liquidMeta, int level)
	{
		LiquidStack stack = LiquidDictionary.getCanonicalLiquid(new LiquidStack(liquidId, 1, liquidMeta));

		if(liquidId <= 0 || stack == null)
		{
			return;
		}

		int vertOffset = 0;

		while(level > 0)
		{
			int texHeight = 0;
	
			if(level > 16)
			{
				texHeight = 16;
				level -= 16;
			}
			else
			{
				texHeight = level;
				level = 0;
			}
			
			mc.renderEngine.bindTexture(stack.getTextureSheet());
			drawTexturedModelRectFromIcon(xOffset, yOffset - texHeight - vertOffset, stack.getRenderingIcon(), 16, texHeight);
			vertOffset = vertOffset + 16;
		}

		this.mc.renderEngine.bindTexture(MineFactoryReloadedCore.guiFolder + _tileEntity.getGuiBackground());
		this.drawTexturedModalRect(xOffset, yOffset - 33, 232, 0, 16, 33);
	}
}
