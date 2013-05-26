package powercrystals.minefactoryreloaded.render.tileentity;

import java.util.Arrays;
import java.util.Collections;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.render.model.RedNetHistorianModel;
import powercrystals.minefactoryreloaded.tile.rednet.TileEntityRedNetHistorian;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;

public class RedNetHistorianRenderer extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
	private RedNetHistorianModel _model;
	
	public RedNetHistorianRenderer()
	{
		_model = new RedNetHistorianModel();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTicks)
	{
		RenderEngine renderengine = Minecraft.getMinecraft().renderEngine;
		TileEntityRedNetHistorian historian = (TileEntityRedNetHistorian)tileentity;
		
		if(renderengine != null)
		{
			renderengine.bindTexture(MineFactoryReloadedCore.tileEntityFolder + "historian.png");
		}
		
		GL11.glPushMatrix();

		GL11.glTranslatef((float)x, (float)y, (float)z);
		
		if(historian.getDirectionFacing() == ForgeDirection.EAST)
		{
			GL11.glTranslatef(1, 0, 0);
			GL11.glRotatef(270, 0, 1, 0);
		}
		else if(historian.getDirectionFacing() == ForgeDirection.SOUTH)
		{
			GL11.glTranslatef(1, 0, 1);
			GL11.glRotatef(180, 0, 1, 0);
		}
		else if(historian.getDirectionFacing() == ForgeDirection.WEST)
		{
			GL11.glTranslatef(0, 0, 1);
			GL11.glRotatef(90, 0, 1, 0);
		}	
		
		_model.render((TileEntityRedNetHistorian)tileentity);
		
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		RenderHelper.disableStandardItemLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		Tessellator t = Tessellator.instance;
		t.startDrawing(GL11.GL_LINES);
		GL11.glLineWidth(2.0F);
		
		Integer[] values = historian.getValues();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		int yMin = Collections.min(Arrays.asList(values));
		int yMax = Collections.max(Arrays.asList(values));
		if(yMax < 15 && yMin < 15 && yMin >= 0)
		{
			yMin = 0;
			yMax = 15;
		}
		
		for(int i = 1; i < values.length; i++)
		{
			double x1 = (14.0/16.0)/values.length * (i - 1) + (1.0/16.0);
			double x2 = (14.0/16.0)/values.length * (i) + (1.0/16.0);
			double y1 = (14.0/16.0)/yMax * values[i - 1] + (1.0/16.0) - yMin;
			double y2 = (14.0/16.0)/yMax * values[i] + (1.0/16.0) - yMin;
			
			t.addVertex(x1, y1, 0.253);
			t.addVertex(x2, y2, 0.253);
		}
		
		t.draw();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
		
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		bindTextureByName(MineFactoryReloadedCore.tileEntityFolder + "historian.png");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0.12, 0, 0);
		_model.render(null);
		GL11.glPopMatrix();
		
		return;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory()
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return MineFactoryReloadedCore.renderIdRedNetPanel;
	}
}
