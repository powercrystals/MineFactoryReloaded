package powercrystals.minefactoryreloaded.render.block;

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
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		BlockFactoryGlassPane pane = (BlockFactoryGlassPane)block;
		
		int worldHeight = blockAccess.getHeight();
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(pane.getMixedBrightnessForBlock(blockAccess, x, y, z));
		int color = block.colorMultiplier(blockAccess, x, y, z);
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
		
		tessellator.setColorOpaque_F(red, green, blue);
		Icon iconFront, iconSide, iconOverlaySouth, iconOverlayWest;
		
		if(renderer.hasOverrideBlockTexture())
		{
			iconFront = renderer.overrideBlockTexture;
			iconSide = renderer.overrideBlockTexture;
			iconOverlaySouth = renderer.overrideBlockTexture;
			iconOverlayWest = renderer.overrideBlockTexture;
		}
		else
		{
			int metadata = blockAccess.getBlockMetadata(x, y, z);
			iconFront = renderer.getBlockIconFromSideAndMetadata(pane, 0, metadata);
			iconSide = pane.getSideTextureIndex();
			iconOverlaySouth = pane.getBlockOverlayTexture(blockAccess, x, y, z, 2);
			iconOverlayWest = pane.getBlockOverlayTexture(blockAccess, x, y, z, 5);
		}
		
		double minXGlass = iconFront.getMinU();
		double midXGlass = iconFront.getInterpolatedU(8.0D);
		double maxXGlass = iconFront.getMaxU();
		double minYGlass = iconFront.getMinV();
		double maxYGlass = iconFront.getMaxV();
		
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
		
		double negSideZOffset = x + 0.5D - 0.0625D;
		double posSideZOffset = x + 0.5D + 0.0625D;
		double negSideXOffset = z + 0.5D - 0.0625D;
		double posSideXOffset = z + 0.5D + 0.0625D;
		
		boolean connectedNegZ = pane.canThisFactoryPaneConnectToThisBlockID(blockAccess.getBlockId(x, y, z - 1));
		boolean connectedPosZ = pane.canThisFactoryPaneConnectToThisBlockID(blockAccess.getBlockId(x, y, z + 1));
		boolean connectedNegX = pane.canThisFactoryPaneConnectToThisBlockID(blockAccess.getBlockId(x - 1, y, z));
		boolean connectedPosX = pane.canThisFactoryPaneConnectToThisBlockID(blockAccess.getBlockId(x + 1, y, z));
		
		boolean renderTop = y >= worldHeight || pane.shouldSideBeRendered(blockAccess, x, y + 1, z, 1);
		boolean renderBottom = y <= 0 || pane.shouldSideBeRendered(blockAccess, x, y - 1, z, 0);
		
		if ((!connectedNegX || !connectedPosX) && (connectedNegX || connectedPosX || connectedNegZ || connectedPosZ))
		{
			if (connectedNegX && !connectedPosX)
			{
				tessellator.addVertexWithUV(xMin, yMax, zMid, minXGlass, minYGlass);
				tessellator.addVertexWithUV(xMin, yMin, zMid, minXGlass, maxYGlass);
				tessellator.addVertexWithUV(xMid, yMin, zMid, midXGlass, maxYGlass);
				tessellator.addVertexWithUV(xMid, yMax, zMid, midXGlass, minYGlass);
				
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
				tessellator.addVertexWithUV(xMid, yMax, zMid, midXGlass, minYGlass);
				tessellator.addVertexWithUV(xMid, yMin, zMid, midXGlass, maxYGlass);
				tessellator.addVertexWithUV(xMax, yMin, zMid, maxXGlass, maxYGlass);
				tessellator.addVertexWithUV(xMax, yMax, zMid, maxXGlass, minYGlass);
				
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
			tessellator.addVertexWithUV(xMin, yMax, zMid, minXGlass, minYGlass);
			tessellator.addVertexWithUV(xMin, yMin, zMid, minXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMax, yMin, zMid, maxXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMax, yMax, zMid, maxXGlass, minYGlass);
			
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
				tessellator.addVertexWithUV(xMid, yMax, zMin, minXGlass, minYGlass);
				tessellator.addVertexWithUV(xMid, yMin, zMin, minXGlass, maxYGlass);
				tessellator.addVertexWithUV(xMid, yMin, zMid, midXGlass, maxYGlass);
				tessellator.addVertexWithUV(xMid, yMax, zMid, midXGlass, minYGlass);
				
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
				tessellator.addVertexWithUV(xMid, yMax, zMid, midXGlass, minYGlass);
				tessellator.addVertexWithUV(xMid, yMin, zMid, midXGlass, maxYGlass);
				tessellator.addVertexWithUV(xMid, yMin, zMax, maxXGlass, maxYGlass);
				tessellator.addVertexWithUV(xMid, yMax, zMax, maxXGlass, minYGlass);
				
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
			tessellator.addVertexWithUV(xMid, yMax, zMin, minXGlass, minYGlass);
			tessellator.addVertexWithUV(xMid, yMin, zMin, minXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMid, yMin, zMax, maxXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMid, yMax, zMax, maxXGlass, minYGlass);
			
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
