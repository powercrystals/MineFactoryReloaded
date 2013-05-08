package powercrystals.minefactoryreloaded.render.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import powercrystals.minefactoryreloaded.tile.machine.TileEntityLaserDrill;

@SideOnly(Side.CLIENT)
public class LaserDrillRenderer extends TileEntitySpecialRenderer
{
	public void renderLaserDrillAt(TileEntityLaserDrill laserDrill, double x, double y, double z, float partialTicks)
	{
		if(laserDrill.shouldDrawBeam())
		{
			Tessellator tessellator = Tessellator.instance;
			this.bindTextureByName("/misc/beam.png");
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			RenderHelper.disableStandardItemLighting();
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDepthMask(true);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			float ticks = (float)laserDrill.getWorldObj().getTotalWorldTime() + partialTicks;
			float f3 = -ticks * 0.2F - (float)MathHelper.floor_float(-ticks * 0.1F);
			double d3 = (double)ticks * 0.025D * (1.0D - 2.5D);
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA(255, 255, 255, 32);
			double d4 = 0.2D;
			double d5 = 0.5D + Math.cos(d3 + 2.356194490192345D) * d4;
			double d6 = 0.5D + Math.sin(d3 + 2.356194490192345D) * d4;
			double d7 = 0.5D + Math.cos(d3 + (Math.PI / 4D)) * d4;
			double d8 = 0.5D + Math.sin(d3 + (Math.PI / 4D)) * d4;
			double d9 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * d4;
			double d10 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * d4;
			double d11 = 0.5D + Math.cos(d3 + 5.497787143782138D) * d4;
			double d12 = 0.5D + Math.sin(d3 + 5.497787143782138D) * d4;
			double yStart = -laserDrill.yCoord + y + 1;
			double height = (double)laserDrill.getBeamHeight();
			double uStart = 0.0D;
			double uEnd = 1.0D;
			double vStart = (double)(-1.0F + f3);
			double vEnd = (double)(256.0F) * (0.5D / d4) + vStart;
			tessellator.addVertexWithUV(x + d5, yStart + height, z + d6, uEnd, vEnd);
			tessellator.addVertexWithUV(x + d5, yStart, z + d6, uEnd, vStart);
			tessellator.addVertexWithUV(x + d7, yStart, z + d8, uStart, vStart);
			tessellator.addVertexWithUV(x + d7, yStart + height, z + d8, uStart, vEnd);
			tessellator.addVertexWithUV(x + d11, yStart + height, z + d12, uEnd, vEnd);
			tessellator.addVertexWithUV(x + d11, yStart, z + d12, uEnd, vStart);
			tessellator.addVertexWithUV(x + d9, yStart, z + d10, uStart, vStart);
			tessellator.addVertexWithUV(x + d9, yStart + height, z + d10, uStart, vEnd);
			tessellator.addVertexWithUV(x + d7, yStart + height, z + d8, uEnd, vEnd);
			tessellator.addVertexWithUV(x + d7, yStart, z + d8, uEnd, vStart);
			tessellator.addVertexWithUV(x + d11, yStart, z + d12, uStart, vStart);
			tessellator.addVertexWithUV(x + d11, yStart + height, z + d12, uStart, vEnd);
			tessellator.addVertexWithUV(x + d9, yStart + height, z + d10, uEnd, vEnd);
			tessellator.addVertexWithUV(x + d9, yStart, z + d10, uEnd, vStart);
			tessellator.addVertexWithUV(x + d5, yStart, z + d6, uStart, vStart);
			tessellator.addVertexWithUV(x + d5, yStart + height, z + d6, uStart, vEnd);
			tessellator.draw();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDepthMask(false);
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA(255, 255, 255, 32);
			double d18 = 0.2D;
			double d19 = 0.2D;
			double d20 = 0.8D;
			double d21 = 0.2D;
			double d22 = 0.2D;
			double d23 = 0.8D;
			double d24 = 0.8D;
			double d25 = 0.8D;
			double d29 = (double)(-1.0F + f3);
			double d30 = (double)(256.0F) + d29;
			tessellator.addVertexWithUV(x + d18, yStart + height, z + d19, uEnd, d30);
			tessellator.addVertexWithUV(x + d18, yStart, z + d19, uEnd, d29);
			tessellator.addVertexWithUV(x + d20, yStart, z + d21, uStart, d29);
			tessellator.addVertexWithUV(x + d20, yStart + height, z + d21, uStart, d30);
			tessellator.addVertexWithUV(x + d24, yStart + height, z + d25, uEnd, d30);
			tessellator.addVertexWithUV(x + d24, yStart, z + d25, uEnd, d29);
			tessellator.addVertexWithUV(x + d22, yStart, z + d23, uStart, d29);
			tessellator.addVertexWithUV(x + d22, yStart + height, z + d23, uStart, d30);
			tessellator.addVertexWithUV(x + d20, yStart + height, z + d21, uEnd, d30);
			tessellator.addVertexWithUV(x + d20, yStart, z + d21, uEnd, d29);
			tessellator.addVertexWithUV(x + d24, yStart, z + d25, uStart, d29);
			tessellator.addVertexWithUV(x + d24, yStart + height, z + d25, uStart, d30);
			tessellator.addVertexWithUV(x + d22, yStart + height, z + d23, uEnd, d30);
			tessellator.addVertexWithUV(x + d22, yStart, z + d23, uEnd, d29);
			tessellator.addVertexWithUV(x + d18, yStart, z + d19, uStart, d29);
			tessellator.addVertexWithUV(x + d18, yStart + height, z + d19, uStart, d30);
			tessellator.draw();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(true);
			GL11.glPopAttrib();
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks)
	{
		this.renderLaserDrillAt((TileEntityLaserDrill)tileEntity, x, y, z, partialTicks);
	}
}