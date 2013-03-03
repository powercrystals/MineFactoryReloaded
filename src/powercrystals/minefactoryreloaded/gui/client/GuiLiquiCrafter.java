package powercrystals.minefactoryreloaded.gui.client;

import org.lwjgl.opengl.GL11;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryInventory;
import powercrystals.minefactoryreloaded.processing.TileEntityLiquiCrafter;
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
}
