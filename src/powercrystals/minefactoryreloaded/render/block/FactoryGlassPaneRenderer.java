package powercrystals.minefactoryreloaded.render.block;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.block.BlockFactoryGlassPane;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class FactoryGlassPaneRenderer implements ISimpleBlockRenderingHandler
{
	@Override
	public void renderInventoryBlock(Block tile, int metadata, int modelID, RenderBlocks renderer)
	{
		BlockFactoryGlassPane block = (BlockFactoryGlassPane)tile;

		Tessellator tessellator = Tessellator.instance;
		int color = block.getRenderColor(metadata);
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float anaglyphRed = (red * 30.0F + green * 59.0F + blue * 11.0F) / 100.0F;
			float anaglyphGreen = (red * 30.0F + green * 70.0F) / 100.0F;
			float anaglyphBlue = (red * 30.0F + blue * 70.0F) / 100.0F;
			red = anaglyphRed;
			green = anaglyphGreen;
			blue = anaglyphBlue;
		}
		Icon iconGlass, iconStreaks, iconSide, iconOverlay;

		iconGlass = block.getIcon(0, metadata);
		iconStreaks = block.getIcon(0, 16 | metadata);
		iconSide = block.getSideTextureIndex();
		iconOverlay = block.getBlockOverlayTexture();

		double minXGlass = iconGlass.getMinU();
		double maxXGlass = iconGlass.getMaxU();
		double minYGlass = iconGlass.getMinV();
		double maxYGlass = iconGlass.getMaxV();

		double minXStreaks = iconStreaks.getMinU();
		double maxXStreaks = iconStreaks.getMaxU();
		double minYStreaks = iconStreaks.getMinV();
		double maxYStreaks = iconStreaks.getMaxV();

		double minXSide = iconSide.getInterpolatedU(7.0D);
		double maxXSide = iconSide.getInterpolatedU(9.0D);
		double minYSide = iconSide.getMinV();
		double maxYSide = iconSide.getMaxV();

		double minXOverlay = iconOverlay.getMinU();
		double maxXOverlay = iconOverlay.getMaxU();
		double minYOverlay = iconOverlay.getMinV();
		double maxYOverlay = iconOverlay.getMaxV();

		double offset = 0.001D;

		double xMin = 0, xMax = 1;
		double yMin = 0, yMax = 1;
		double zMid = 0.5;

		double negSideXOffset = zMid - 0.0625D;
		double posSideXOffset = zMid + 0.0625D;
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(red, green, blue);
		tessellator.addVertexWithUV(xMin, yMax, zMid, minXGlass, minYGlass);
		tessellator.addVertexWithUV(xMin, yMin, zMid, minXGlass, maxYGlass);
		tessellator.addVertexWithUV(xMax, yMin, zMid, maxXGlass, maxYGlass);
		tessellator.addVertexWithUV(xMax, yMax, zMid, maxXGlass, minYGlass);

		tessellator.setColorOpaque_F(1, 1, 1);
		tessellator.addVertexWithUV(xMin, yMax, zMid, minXStreaks, minYStreaks);
		tessellator.addVertexWithUV(xMin, yMin, zMid, minXStreaks, maxYStreaks);
		tessellator.addVertexWithUV(xMax, yMin, zMid, maxXStreaks, maxYStreaks);
		tessellator.addVertexWithUV(xMax, yMax, zMid, maxXStreaks, minYStreaks);

		tessellator.addVertexWithUV(xMin, yMax, zMid, minXOverlay, minYOverlay);
		tessellator.addVertexWithUV(xMin, yMin, zMid, minXOverlay, maxYOverlay);
		tessellator.addVertexWithUV(xMax, yMin, zMid, maxXOverlay, maxYOverlay);
		tessellator.addVertexWithUV(xMax, yMax, zMid, maxXOverlay, minYOverlay);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(1, 1, 1);
		tessellator.addVertexWithUV(xMin, yMax, negSideXOffset, minXSide, minYSide);
		tessellator.addVertexWithUV(xMin, yMin, negSideXOffset, minXSide, maxYSide);
		tessellator.addVertexWithUV(xMin, yMin, posSideXOffset, maxXSide, maxYSide);
		tessellator.addVertexWithUV(xMin, yMax, posSideXOffset, maxXSide, minYSide);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(1, 1, 1);
		tessellator.addVertexWithUV(xMax, yMax, negSideXOffset, minXSide, minYSide);
		tessellator.addVertexWithUV(xMax, yMin, negSideXOffset, minXSide, maxYSide);
		tessellator.addVertexWithUV(xMax, yMin, posSideXOffset, maxXSide, maxYSide);
		tessellator.addVertexWithUV(xMax, yMax, posSideXOffset, maxXSide, minYSide);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(1, 1, 1);
		tessellator.addVertexWithUV(xMin, yMax + offset, posSideXOffset, maxXSide, maxYSide);
		tessellator.addVertexWithUV(xMax, yMax + offset, posSideXOffset, maxXSide, minYSide);
		tessellator.addVertexWithUV(xMax, yMax + offset, negSideXOffset, minXSide, minYSide);
		tessellator.addVertexWithUV(xMin, yMax + offset, negSideXOffset, minXSide, maxYSide);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(1, 1, 1);
		tessellator.addVertexWithUV(xMin, yMin - offset, posSideXOffset, maxXSide, maxYSide);
		tessellator.addVertexWithUV(xMax, yMin - offset, posSideXOffset, maxXSide, minYSide);
		tessellator.addVertexWithUV(xMax, yMin - offset, negSideXOffset, minXSide, minYSide);
		tessellator.addVertexWithUV(xMin, yMin - offset, negSideXOffset, minXSide, maxYSide);
		tessellator.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z,
			Block tile, int modelId, RenderBlocks renderer)
	{
		BlockFactoryGlassPane block = (BlockFactoryGlassPane)tile;

		int worldHeight = blockAccess.getHeight();
		int metadata = blockAccess.getBlockMetadata(x, y, z);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(tile.getMixedBrightnessForBlock(blockAccess, x, y, z));
		int color = block.getRenderColor(metadata);
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float anaglyphRed = (red * 30.0F + green * 59.0F + blue * 11.0F) / 100.0F;
			float anaglyphGreen = (red * 30.0F + green * 70.0F) / 100.0F;
			float anaglyphBlue = (red * 30.0F + blue * 70.0F) / 100.0F;
			red = anaglyphRed;
			green = anaglyphGreen;
			blue = anaglyphBlue;
		}

		Icon iconGlass, iconStreaks, iconSide, iconOverlaySouth, iconOverlayWest;

		if (renderer.hasOverrideBlockTexture())
		{
			iconGlass = iconStreaks = iconSide = 
			iconOverlaySouth = iconOverlayWest =
					renderer.overrideBlockTexture;
		}
		else
		{
			iconGlass = block.getIcon(0, metadata);
			iconStreaks = block.getIcon(0, 16 | metadata);
			iconSide = block.getSideTextureIndex();
			iconOverlaySouth = block.getBlockOverlayTexture(blockAccess, x, y, z, 2);
			iconOverlayWest = block.getBlockOverlayTexture(blockAccess, x, y, z, 5);
		}

		double minXGlass = iconGlass.getMinU();
		double midXGlass = iconGlass.getInterpolatedU(8.0D);
		double maxXGlass = iconGlass.getMaxU();
		double minYGlass = iconGlass.getMinV();
		double maxYGlass = iconGlass.getMaxV();

		double minXStreaks = iconStreaks.getMinU();
		double midXStreaks = iconStreaks.getInterpolatedU(8.0D);
		double maxXStreaks = iconStreaks.getMaxU();
		double minYStreaks = iconStreaks.getMinV();
		double maxYStreaks = iconStreaks.getMaxV();

		double minXSide = iconSide.getInterpolatedU(7.0D);
		double maxXSide = iconSide.getInterpolatedU(9.0D);
		double minYSide = iconSide.getMinV();
		double midYSide = iconSide.getInterpolatedV(8.0D);
		double maxYSide = iconSide.getMaxV();

		double minXSouth = iconOverlaySouth.getMinU();
		double midXSouth = iconOverlaySouth.getInterpolatedU(8.0D);
		double maxXSouth = iconOverlaySouth.getMaxU();
		double minYSouth = iconOverlaySouth.getMinV();
		double maxYSouth = iconOverlaySouth.getMaxV();

		double minXWest = iconOverlayWest.getMinU();
		double midXWest = iconOverlayWest.getInterpolatedU(8.0D);
		double maxXWest = iconOverlayWest.getMaxU();
		double minYWest = iconOverlayWest.getMinV();
		double maxYWest = iconOverlayWest.getMaxV();

		double xMin = x;
		double xMid = x + 0.5D;
		double xMax = x + 1;

		double zMin = z;
		double zMid = z + 0.5D;
		double zMax = z + 1;

		double yMin = y;
		double yMax = y + 1;

		double vertSideZOffset = 0.001D;
		double vertSideXOffset = 0.002D;

		double negSideZOffset = xMid - 0.0625D;
		double posSideZOffset = xMid + 0.0625D;
		double negSideXOffset = zMid - 0.0625D;
		double posSideXOffset = zMid + 0.0625D;

		boolean connectedNegZ = block.canThisFactoryPaneConnectToThisBlockID(blockAccess.getBlockId(x, y, z - 1));
		boolean connectedPosZ = block.canThisFactoryPaneConnectToThisBlockID(blockAccess.getBlockId(x, y, z + 1));
		boolean connectedNegX = block.canThisFactoryPaneConnectToThisBlockID(blockAccess.getBlockId(x - 1, y, z));
		boolean connectedPosX = block.canThisFactoryPaneConnectToThisBlockID(blockAccess.getBlockId(x + 1, y, z));

		boolean renderTop = y >= worldHeight || block.shouldSideBeRendered(blockAccess, x, y + 1, z, 1);
		boolean renderBottom = y <= 0 || block.shouldSideBeRendered(blockAccess, x, y - 1, z, 0);

		if ((!connectedNegX || !connectedPosX) && (connectedNegX || connectedPosX || connectedNegZ || connectedPosZ))
		{
			if (connectedNegX && !connectedPosX)
			{
				tessellator.setColorOpaque_F(red, green, blue);
				tessellator.addVertexWithUV(xMin, yMax, zMid, minXGlass, minYGlass);
				tessellator.addVertexWithUV(xMin, yMin, zMid, minXGlass, maxYGlass);
				tessellator.addVertexWithUV(xMid, yMin, zMid, midXGlass, maxYGlass);
				tessellator.addVertexWithUV(xMid, yMax, zMid, midXGlass, minYGlass);

				tessellator.setColorOpaque_F(1, 1, 1);
				tessellator.addVertexWithUV(xMin, yMax, zMid, minXStreaks, minYStreaks);
				tessellator.addVertexWithUV(xMin, yMin, zMid, minXStreaks, maxYStreaks);
				tessellator.addVertexWithUV(xMid, yMin, zMid, midXStreaks, maxYStreaks);
				tessellator.addVertexWithUV(xMid, yMax, zMid, midXStreaks, minYStreaks);

				tessellator.addVertexWithUV(xMin, yMax, zMid, minXSouth, minYSouth);
				tessellator.addVertexWithUV(xMin, yMin, zMid, minXSouth, maxYSouth);
				tessellator.addVertexWithUV(xMid, yMin, zMid, midXSouth, maxYSouth);
				tessellator.addVertexWithUV(xMid, yMax, zMid, midXSouth, minYSouth);

				if (!connectedPosZ && !connectedNegZ)
				{
					tessellator.addVertexWithUV(xMid, yMax, posSideXOffset, minXSide, minYSide);
					tessellator.addVertexWithUV(xMid, yMin, posSideXOffset, minXSide, maxYSide);
					tessellator.addVertexWithUV(xMid, yMin, negSideXOffset, maxXSide, maxYSide);
					tessellator.addVertexWithUV(xMid, yMax, negSideXOffset, maxXSide, minYSide); 
				}

				if (renderTop)
				{
					tessellator.addVertexWithUV(xMin, yMax + vertSideXOffset, posSideXOffset, maxXSide, midYSide);
					tessellator.addVertexWithUV(xMid, yMax + vertSideXOffset, posSideXOffset, maxXSide, maxYSide);
					tessellator.addVertexWithUV(xMid, yMax + vertSideXOffset, negSideXOffset, minXSide, maxYSide);
					tessellator.addVertexWithUV(xMin, yMax + vertSideXOffset, negSideXOffset, minXSide, midYSide);
				}

				if (renderBottom)
				{
					tessellator.addVertexWithUV(xMin, yMin - vertSideXOffset, posSideXOffset, maxXSide, midYSide);
					tessellator.addVertexWithUV(xMid, yMin - vertSideXOffset, posSideXOffset, maxXSide, maxYSide);
					tessellator.addVertexWithUV(xMid, yMin - vertSideXOffset, negSideXOffset, minXSide, maxYSide);
					tessellator.addVertexWithUV(xMin, yMin - vertSideXOffset, negSideXOffset, minXSide, midYSide);
				}
			}
			else if (!connectedNegX && connectedPosX)
			{
				tessellator.setColorOpaque_F(red, green, blue);
				tessellator.addVertexWithUV(xMid, yMax, zMid, midXGlass, minYGlass);
				tessellator.addVertexWithUV(xMid, yMin, zMid, midXGlass, maxYGlass);
				tessellator.addVertexWithUV(xMax, yMin, zMid, maxXGlass, maxYGlass);
				tessellator.addVertexWithUV(xMax, yMax, zMid, maxXGlass, minYGlass);

				tessellator.setColorOpaque_F(1, 1, 1);
				tessellator.addVertexWithUV(xMid, yMax, zMid, midXStreaks, minYStreaks);
				tessellator.addVertexWithUV(xMid, yMin, zMid, midXStreaks, maxYStreaks);
				tessellator.addVertexWithUV(xMax, yMin, zMid, maxXStreaks, maxYStreaks);
				tessellator.addVertexWithUV(xMax, yMax, zMid, maxXStreaks, minYStreaks);

				tessellator.addVertexWithUV(xMid, yMax, zMid, midXSouth, minYSouth);
				tessellator.addVertexWithUV(xMid, yMin, zMid, midXSouth, maxYSouth);
				tessellator.addVertexWithUV(xMax, yMin, zMid, maxXSouth, maxYSouth);
				tessellator.addVertexWithUV(xMax, yMax, zMid, maxXSouth, minYSouth);

				if (!connectedPosZ && !connectedNegZ)
				{
					tessellator.addVertexWithUV(xMid, yMax, negSideXOffset, minXSide, minYSide);
					tessellator.addVertexWithUV(xMid, yMin, negSideXOffset, minXSide, maxYSide);
					tessellator.addVertexWithUV(xMid, yMin, posSideXOffset, maxXSide, maxYSide);
					tessellator.addVertexWithUV(xMid, yMax, posSideXOffset, maxXSide, minYSide);
				}

				if (renderTop)
				{
					tessellator.addVertexWithUV(xMid, yMax + vertSideXOffset, posSideXOffset, maxXSide, minYSide);
					tessellator.addVertexWithUV(xMax, yMax + vertSideXOffset, posSideXOffset, maxXSide, midYSide);
					tessellator.addVertexWithUV(xMax, yMax + vertSideXOffset, negSideXOffset, minXSide, midYSide);
					tessellator.addVertexWithUV(xMid, yMax + vertSideXOffset, negSideXOffset, minXSide, minYSide);
				}

				if (renderBottom)
				{
					tessellator.addVertexWithUV(xMid, yMin - vertSideXOffset, posSideXOffset, maxXSide, minYSide);
					tessellator.addVertexWithUV(xMax, yMin - vertSideXOffset, posSideXOffset, maxXSide, midYSide);
					tessellator.addVertexWithUV(xMax, yMin - vertSideXOffset, negSideXOffset, minXSide, midYSide);
					tessellator.addVertexWithUV(xMid, yMin - vertSideXOffset, negSideXOffset, minXSide, minYSide);
				}
			}
		}
		else
		{
			tessellator.setColorOpaque_F(red, green, blue);
			tessellator.addVertexWithUV(xMin, yMax, zMid, minXGlass, minYGlass);
			tessellator.addVertexWithUV(xMin, yMin, zMid, minXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMax, yMin, zMid, maxXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMax, yMax, zMid, maxXGlass, minYGlass);

			tessellator.setColorOpaque_F(1, 1, 1);
			tessellator.addVertexWithUV(xMin, yMax, zMid, minXStreaks, minYStreaks);
			tessellator.addVertexWithUV(xMin, yMin, zMid, minXStreaks, maxYStreaks);
			tessellator.addVertexWithUV(xMax, yMin, zMid, maxXStreaks, maxYStreaks);
			tessellator.addVertexWithUV(xMax, yMax, zMid, maxXStreaks, minYStreaks);

			tessellator.addVertexWithUV(xMin, yMax, zMid, minXSouth, minYSouth);
			tessellator.addVertexWithUV(xMin, yMin, zMid, minXSouth, maxYSouth);
			tessellator.addVertexWithUV(xMax, yMin, zMid, maxXSouth, maxYSouth);
			tessellator.addVertexWithUV(xMax, yMax, zMid, maxXSouth, minYSouth);

			if (!connectedPosX && !connectedNegX)
			{
				tessellator.addVertexWithUV(xMin, yMax, negSideXOffset, minXSide, minYSide);
				tessellator.addVertexWithUV(xMin, yMin, negSideXOffset, minXSide, maxYSide);
				tessellator.addVertexWithUV(xMin, yMin, posSideXOffset, maxXSide, maxYSide);
				tessellator.addVertexWithUV(xMin, yMax, posSideXOffset, maxXSide, minYSide);

				tessellator.addVertexWithUV(xMax, yMax, negSideXOffset, minXSide, minYSide);
				tessellator.addVertexWithUV(xMax, yMin, negSideXOffset, minXSide, maxYSide);
				tessellator.addVertexWithUV(xMax, yMin, posSideXOffset, maxXSide, maxYSide);
				tessellator.addVertexWithUV(xMax, yMax, posSideXOffset, maxXSide, minYSide);
			}

			if (renderTop)
			{
				tessellator.addVertexWithUV(xMin, yMax + vertSideXOffset, posSideXOffset, maxXSide, maxYSide);
				tessellator.addVertexWithUV(xMax, yMax + vertSideXOffset, posSideXOffset, maxXSide, minYSide);
				tessellator.addVertexWithUV(xMax, yMax + vertSideXOffset, negSideXOffset, minXSide, minYSide);
				tessellator.addVertexWithUV(xMin, yMax + vertSideXOffset, negSideXOffset, minXSide, maxYSide);
			}

			if (renderBottom)
			{
				tessellator.addVertexWithUV(xMin, yMin - vertSideXOffset, posSideXOffset, maxXSide, maxYSide);
				tessellator.addVertexWithUV(xMax, yMin - vertSideXOffset, posSideXOffset, maxXSide, minYSide);
				tessellator.addVertexWithUV(xMax, yMin - vertSideXOffset, negSideXOffset, minXSide, minYSide);
				tessellator.addVertexWithUV(xMin, yMin - vertSideXOffset, negSideXOffset, minXSide, maxYSide);
			}
		}

		if ((!connectedNegZ || !connectedPosZ) && (connectedNegX || connectedPosX || connectedNegZ || connectedPosZ))
		{
			if (connectedNegZ && !connectedPosZ)
			{
				tessellator.setColorOpaque_F(red, green, blue);
				tessellator.addVertexWithUV(xMid, yMax, zMin, minXGlass, minYGlass);
				tessellator.addVertexWithUV(xMid, yMin, zMin, minXGlass, maxYGlass);
				tessellator.addVertexWithUV(xMid, yMin, zMid, midXGlass, maxYGlass);
				tessellator.addVertexWithUV(xMid, yMax, zMid, midXGlass, minYGlass);

				tessellator.setColorOpaque_F(1, 1, 1);
				tessellator.addVertexWithUV(xMid, yMax, zMin, minXStreaks, minYStreaks);
				tessellator.addVertexWithUV(xMid, yMin, zMin, minXStreaks, maxYStreaks);
				tessellator.addVertexWithUV(xMid, yMin, zMid, midXStreaks, maxYStreaks);
				tessellator.addVertexWithUV(xMid, yMax, zMid, midXStreaks, minYStreaks);

				tessellator.addVertexWithUV(xMid, yMax, zMin, minXWest, minYWest);
				tessellator.addVertexWithUV(xMid, yMin, zMin, minXWest, maxYWest);
				tessellator.addVertexWithUV(xMid, yMin, zMid, midXWest, maxYWest);
				tessellator.addVertexWithUV(xMid, yMax, zMid, midXWest, minYWest);

				if (!connectedPosX && !connectedNegX)
				{
					tessellator.addVertexWithUV(negSideZOffset, yMax, zMid, minXSide, minYSide);
					tessellator.addVertexWithUV(negSideZOffset, yMin, zMid, minXSide, maxYSide);
					tessellator.addVertexWithUV(posSideZOffset, yMin, zMid, maxXSide, maxYSide);
					tessellator.addVertexWithUV(posSideZOffset, yMax, zMid, maxXSide, minYSide);
				}

				if (renderTop)
				{
					tessellator.addVertexWithUV(negSideZOffset, yMax + vertSideZOffset, zMin, maxXSide, minYSide);
					tessellator.addVertexWithUV(negSideZOffset, yMax + vertSideZOffset, zMid, maxXSide, midYSide);
					tessellator.addVertexWithUV(posSideZOffset, yMax + vertSideZOffset, zMid, minXSide, midYSide);
					tessellator.addVertexWithUV(posSideZOffset, yMax + vertSideZOffset, zMin, minXSide, minYSide);
				}

				if (renderBottom)
				{
					tessellator.addVertexWithUV(negSideZOffset, yMin - vertSideZOffset, zMin, maxXSide, minYSide);
					tessellator.addVertexWithUV(negSideZOffset, yMin - vertSideZOffset, zMid, maxXSide, midYSide);
					tessellator.addVertexWithUV(posSideZOffset, yMin - vertSideZOffset, zMid, minXSide, midYSide);
					tessellator.addVertexWithUV(posSideZOffset, yMin - vertSideZOffset, zMin, minXSide, minYSide);
				}
			}
			else if (!connectedNegZ && connectedPosZ)
			{
				tessellator.setColorOpaque_F(red, green, blue);
				tessellator.addVertexWithUV(xMid, yMax, zMid, midXGlass, minYGlass);
				tessellator.addVertexWithUV(xMid, yMin, zMid, midXGlass, maxYGlass);
				tessellator.addVertexWithUV(xMid, yMin, zMax, maxXGlass, maxYGlass);
				tessellator.addVertexWithUV(xMid, yMax, zMax, maxXGlass, minYGlass);

				tessellator.setColorOpaque_F(1, 1, 1);
				tessellator.addVertexWithUV(xMid, yMax, zMid, midXStreaks, minYStreaks);
				tessellator.addVertexWithUV(xMid, yMin, zMid, midXStreaks, maxYStreaks);
				tessellator.addVertexWithUV(xMid, yMin, zMax, maxXStreaks, maxYStreaks);
				tessellator.addVertexWithUV(xMid, yMax, zMax, maxXStreaks, minYStreaks);

				tessellator.addVertexWithUV(xMid, yMax, zMid, midXWest, minYWest);
				tessellator.addVertexWithUV(xMid, yMin, zMid, midXWest, maxYWest);
				tessellator.addVertexWithUV(xMid, yMin, zMax, maxXWest, maxYWest);
				tessellator.addVertexWithUV(xMid, yMax, zMax, maxXWest, minYWest);

				if (!connectedPosX && !connectedNegX)
				{
					tessellator.addVertexWithUV(posSideZOffset, yMax, zMid, minXSide, minYSide);
					tessellator.addVertexWithUV(posSideZOffset, yMin, zMid, minXSide, maxYSide);
					tessellator.addVertexWithUV(negSideZOffset, yMin, zMid, maxXSide, maxYSide);
					tessellator.addVertexWithUV(negSideZOffset, yMax, zMid, maxXSide, minYSide);
				}

				if (renderTop)
				{
					tessellator.addVertexWithUV(negSideZOffset, yMax + vertSideZOffset, zMid, minXSide, midYSide);
					tessellator.addVertexWithUV(negSideZOffset, yMax + vertSideZOffset, zMax, minXSide, maxYSide);
					tessellator.addVertexWithUV(posSideZOffset, yMax + vertSideZOffset, zMax, maxXSide, maxYSide);
					tessellator.addVertexWithUV(posSideZOffset, yMax + vertSideZOffset, zMid, maxXSide, midYSide);
				}

				if (renderBottom)
				{
					tessellator.addVertexWithUV(negSideZOffset, yMin - vertSideZOffset, zMid, minXSide, midYSide);
					tessellator.addVertexWithUV(negSideZOffset, yMin - vertSideZOffset, zMax, minXSide, maxYSide);
					tessellator.addVertexWithUV(posSideZOffset, yMin - vertSideZOffset, zMax, maxXSide, maxYSide);
					tessellator.addVertexWithUV(posSideZOffset, yMin - vertSideZOffset, zMid, maxXSide, midYSide);
				}
			}
		}
		else
		{
			tessellator.setColorOpaque_F(red, green, blue);
			tessellator.addVertexWithUV(xMid, yMax, zMin, minXGlass, minYGlass);
			tessellator.addVertexWithUV(xMid, yMin, zMin, minXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMid, yMin, zMax, maxXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMid, yMax, zMax, maxXGlass, minYGlass);

			tessellator.setColorOpaque_F(1, 1, 1);
			tessellator.addVertexWithUV(xMid, yMax, zMin, minXStreaks, minYStreaks);
			tessellator.addVertexWithUV(xMid, yMin, zMin, minXStreaks, maxYStreaks);
			tessellator.addVertexWithUV(xMid, yMin, zMax, maxXStreaks, maxYStreaks);
			tessellator.addVertexWithUV(xMid, yMax, zMax, maxXStreaks, minYStreaks);

			tessellator.addVertexWithUV(xMid, yMax, zMin, minXWest, minYWest);
			tessellator.addVertexWithUV(xMid, yMin, zMin, minXWest, maxYWest);
			tessellator.addVertexWithUV(xMid, yMin, zMax, maxXWest, maxYWest);
			tessellator.addVertexWithUV(xMid, yMax, zMax, maxXWest, minYWest);

			if (!connectedPosZ && !connectedNegZ)
			{
				tessellator.addVertexWithUV(negSideZOffset, yMax, zMin, minXSide, minYSide);
				tessellator.addVertexWithUV(negSideZOffset, yMin, zMin, minXSide, maxYSide);
				tessellator.addVertexWithUV(posSideZOffset, yMin, zMin, maxXSide, maxYSide);
				tessellator.addVertexWithUV(posSideZOffset, yMax, zMin, maxXSide, minYSide);

				tessellator.addVertexWithUV(posSideZOffset, yMax, zMax, minXSide, minYSide);
				tessellator.addVertexWithUV(posSideZOffset, yMin, zMax, minXSide, maxYSide);
				tessellator.addVertexWithUV(negSideZOffset, yMin, zMax, maxXSide, maxYSide);
				tessellator.addVertexWithUV(negSideZOffset, yMax, zMax, maxXSide, minYSide);
			}

			if (renderTop)
			{
				tessellator.addVertexWithUV(posSideZOffset, yMax + vertSideZOffset, zMax, maxXSide, maxYSide);
				tessellator.addVertexWithUV(posSideZOffset, yMax + vertSideZOffset, zMin, maxXSide, minYSide);
				tessellator.addVertexWithUV(negSideZOffset, yMax + vertSideZOffset, zMin, minXSide, minYSide);
				tessellator.addVertexWithUV(negSideZOffset, yMax + vertSideZOffset, zMax, minXSide, maxYSide);
			}
			// TODO: fix slightly awkward rendering when pane on top/bottom doesn't connect on both sides
			if (renderBottom)
			{
				tessellator.addVertexWithUV(posSideZOffset, yMin - vertSideZOffset, zMax, maxXSide, maxYSide);
				tessellator.addVertexWithUV(posSideZOffset, yMin - vertSideZOffset, zMin, maxXSide, minYSide);
				tessellator.addVertexWithUV(negSideZOffset, yMin - vertSideZOffset, zMin, minXSide, minYSide);
				tessellator.addVertexWithUV(negSideZOffset, yMin - vertSideZOffset, zMax, minXSide, maxYSide);
			}
		}

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory()
	{
		return false;
	}

	@Override
	public int getRenderId()
	{
		return MineFactoryReloadedCore.renderIdFactoryGlassPane;
	}
}
