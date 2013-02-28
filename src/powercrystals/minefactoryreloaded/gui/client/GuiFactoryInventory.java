package powercrystals.minefactoryreloaded.gui.client;

import org.lwjgl.opengl.GL11;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryInventory;

public class GuiFactoryInventory extends GuiFactoryPowered
{
	public GuiFactoryInventory(ContainerFactoryInventory container, TileEntityFactoryInventory tileentity)
	{
		super(container, tileentity);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float mouseX, int mouseY, int gameTicks)
	{
		int texture;
		if(_tePowered.getTank() == null)
		{
			texture = mc.renderEngine.getTexture(MineFactoryReloadedCore.guiFolder + "inv.png");
		}
		else
		{
			texture = mc.renderEngine.getTexture(MineFactoryReloadedCore.guiFolder + "invtank.png");
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
