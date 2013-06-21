package powercrystals.minefactoryreloaded.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.block.BlockFactoryGlass;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.common.FMLLog;

public class FactoryGlassRenderer implements ISimpleBlockRenderingHandler
{
	@Override
	public void renderInventoryBlock(Block tile, int metadata, int modelID, RenderBlocks renderer)
	{
		BlockFactoryGlass block = (BlockFactoryGlass)tile;
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(15);
		float f = 1.0F;
		int i1 = 0xFFFFFF;
		float f1 = (i1 >> 16 & 255) / 255.0F;
		float f2 = (i1 >> 8 & 255) / 255.0F;
		float f3 = (i1 & 255) / 255.0F;
		
		if (EntityRenderer.anaglyphEnable)
		{
			float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
			float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
			float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
			f1 = f4;
			f2 = f5;
			f3 = f6;
		}
		
		tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
		Icon iconGlass, iconOverlay;
		
		if(renderer.hasOverrideBlockTexture())
		{
			iconGlass = renderer.overrideBlockTexture;
			iconOverlay = renderer.overrideBlockTexture;
		}
		else
		{
			iconGlass = renderer.getBlockIconFromSideAndMetadata(block, 0, metadata);
			iconOverlay = block.getBlockOverlayTexture();
		}
		
		double minXGlass = iconGlass.getMinU();
		double maxXGlass = iconGlass.getMaxU();
		double minYGlass = iconGlass.getMinV();
		double maxYGlass = iconGlass.getMaxV();

		double minXOverlay = iconOverlay.getMinU();
		double maxXOverlay = iconOverlay.getMaxU();
		double minYOverlay = iconOverlay.getMinV();
		double maxYOverlay = iconOverlay.getMaxV();
		

		double d10 = 0, d11 = 16;
		double d12 = 0, d13 = 16;
		double d14 = 0, d15 = 16;

		tessellator.addVertexWithUV(d10, d13, d14, minXGlass, minYGlass);
		tessellator.addVertexWithUV(d10, d13, d15, minXGlass, maxYGlass);
		tessellator.addVertexWithUV(d11, d13, d15, maxXGlass, maxYGlass);
		tessellator.addVertexWithUV(d11, d13, d14, maxXGlass, minYGlass);
		
		tessellator.addVertexWithUV(d10, d13, d14, minXOverlay, minYOverlay);
		tessellator.addVertexWithUV(d10, d13, d15, minXOverlay, maxYOverlay);
		tessellator.addVertexWithUV(d11, d13, d15, maxXOverlay, maxYOverlay);
		tessellator.addVertexWithUV(d11, d13, d14, maxXOverlay, minYOverlay);
		
		tessellator.addVertexWithUV(d10, d13, d14, minXGlass, minYGlass);
		tessellator.addVertexWithUV(d10, d12, d14, minXGlass, maxYGlass);
		tessellator.addVertexWithUV(d11, d12, d14, maxXGlass, maxYGlass);
		tessellator.addVertexWithUV(d11, d13, d14, maxXGlass, minYGlass);
		
		tessellator.addVertexWithUV(d10, d13, d14, minXOverlay, minYOverlay);
		tessellator.addVertexWithUV(d10, d12, d14, minXOverlay, maxYOverlay);
		tessellator.addVertexWithUV(d11, d12, d14, maxXOverlay, maxYOverlay);
		tessellator.addVertexWithUV(d11, d13, d14, maxXOverlay, minYOverlay);
		
		tessellator.addVertexWithUV(d10, d13, d15, minXGlass, minYGlass);
		tessellator.addVertexWithUV(d10, d12, d15, minXGlass, maxYGlass);
		tessellator.addVertexWithUV(d11, d12, d15, maxXGlass, maxYGlass);
		tessellator.addVertexWithUV(d11, d13, d15, maxXGlass, minYGlass);
		
		tessellator.addVertexWithUV(d10, d13, d15, minXOverlay, minYOverlay);
		tessellator.addVertexWithUV(d10, d12, d15, minXOverlay, maxYOverlay);
		tessellator.addVertexWithUV(d11, d12, d15, maxXOverlay, maxYOverlay);
		tessellator.addVertexWithUV(d11, d13, d15, maxXOverlay, minYOverlay);
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block tile, int modelId, RenderBlocks renderer)
	{
		BlockFactoryGlass block = (BlockFactoryGlass)tile;
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, x, y, z));
		float f = 1.0F;
		int i1 = block.colorMultiplier(blockAccess, x, y, z);
		float f1 = (i1 >> 16 & 255) / 255.0F;
		float f2 = (i1 >> 8 & 255) / 255.0F;
		float f3 = (i1 & 255) / 255.0F;
		
		if (EntityRenderer.anaglyphEnable)
		{
			float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
			float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
			float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
			f1 = f4;
			f2 = f5;
			f3 = f6;
		}
		
		tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
		Icon iconGlass, iconOverlayTop, iconOverlaySouth, iconOverlayWest;
		
		if(renderer.hasOverrideBlockTexture())
		{
			iconGlass = renderer.overrideBlockTexture;
			iconOverlayTop = renderer.overrideBlockTexture;
			iconOverlaySouth = renderer.overrideBlockTexture;
			iconOverlayWest = renderer.overrideBlockTexture;
		}
		else
		{
			int metadata = blockAccess.getBlockMetadata(x, y, z);
			iconGlass = renderer.getBlockIconFromSideAndMetadata(block, 0, metadata);
			iconOverlayTop = block.getBlockOverlayTexture(blockAccess, x, y, z, 1);
			iconOverlaySouth = block.getBlockOverlayTexture(blockAccess, x, y, z, 2);
			iconOverlayWest = block.getBlockOverlayTexture(blockAccess, x, y, z, 5);
		}
		
		double minXGlass = iconGlass.getMinU();
		double maxXGlass = iconGlass.getMaxU();
		double minYGlass = iconGlass.getMinV();
		double maxYGlass = iconGlass.getMaxV();

		double minXSouth = iconOverlaySouth.getMinU();
		double maxXSouth = iconOverlaySouth.getMaxU();
		double minYSouth = iconOverlaySouth.getMinV();
		double maxYSouth = iconOverlaySouth.getMaxV();

		double minXWest = iconOverlayWest.getMinU();
		double maxXWest = iconOverlayWest.getMaxU();
		double minYWest = iconOverlayWest.getMinV();
		double maxYWest = iconOverlayWest.getMaxV();

		double minXTop = iconOverlayTop.getMinU();
		double maxXTop = iconOverlayTop.getMaxU();
		double minYTop = iconOverlayTop.getMinV();
		double maxYTop = iconOverlayTop.getMaxV();
		
		//int width = iconOverlayTop.getSheetWidth();
		//int height = iconOverlayTop.getSheetHeight();
		
		//FMLLog.severe("X: %s, %s; Y: %s, %s", minXTop * width, maxXTop * width, minYTop * height, maxYTop * height);
		
		double xMin = x, xMax = x + 1;
		double yMin = y, yMax = y + 1;
		double zMin = z, zMax = z + 1;
		
		boolean[] renderSide = {
				renderer.renderAllFaces || y == 0 || block.shouldSideBeRendered(blockAccess, x, y - 1, z, 0),
				renderer.renderAllFaces || y >= 255 || block.shouldSideBeRendered(blockAccess, x, y + 1, z, 1),
				renderer.renderAllFaces || block.shouldSideBeRendered(blockAccess, x, y, z - 1, 2),
				renderer.renderAllFaces || block.shouldSideBeRendered(blockAccess, x, y, z + 1, 3),
				renderer.renderAllFaces || block.shouldSideBeRendered(blockAccess, x - 1, y, z, 4),
				renderer.renderAllFaces || block.shouldSideBeRendered(blockAccess, x + 1, y, z, 5),
			};

		if (renderSide[0])
		{
			tessellator.addVertexWithUV(xMin, yMin, zMin, minXGlass, minYGlass);
			tessellator.addVertexWithUV(xMin, yMin, zMax, minXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMax, yMin, zMax, maxXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMax, yMin, zMin, maxXGlass, minYGlass);
			
			tessellator.addVertexWithUV(xMin, yMin, zMin, minXTop, minYTop);
			tessellator.addVertexWithUV(xMin, yMin, zMax, minXTop, maxYTop);
			tessellator.addVertexWithUV(xMax, yMin, zMax, maxXTop, maxYTop);
			tessellator.addVertexWithUV(xMax, yMin, zMin, maxXTop, minYTop);
		}

		if (renderSide[1])
		{
			tessellator.addVertexWithUV(xMin, yMax, zMin, minXGlass, minYGlass);
			tessellator.addVertexWithUV(xMin, yMax, zMax, minXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMax, yMax, zMax, maxXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMax, yMax, zMin, maxXGlass, minYGlass);
			
			tessellator.addVertexWithUV(xMin, yMax, zMin, minXTop, minYTop);
			tessellator.addVertexWithUV(xMin, yMax, zMax, minXTop, maxYTop);
			tessellator.addVertexWithUV(xMax, yMax, zMax, maxXTop, maxYTop);
			tessellator.addVertexWithUV(xMax, yMax, zMin, maxXTop, minYTop);
		}
		
		if (renderSide[2])
		{
			tessellator.addVertexWithUV(xMin, yMax, zMin, minXGlass, minYGlass);
			tessellator.addVertexWithUV(xMin, yMin, zMin, minXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMax, yMin, zMin, maxXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMax, yMax, zMin, maxXGlass, minYGlass);
			
			tessellator.addVertexWithUV(xMin, yMax, zMin, minXSouth, minYSouth);
			tessellator.addVertexWithUV(xMin, yMin, zMin, minXSouth, maxYSouth);
			tessellator.addVertexWithUV(xMax, yMin, zMin, maxXSouth, maxYSouth);
			tessellator.addVertexWithUV(xMax, yMax, zMin, maxXSouth, minYSouth);
		}

		if (renderSide[3])
		{
			tessellator.addVertexWithUV(xMin, yMax, zMax, minXGlass, minYGlass);
			tessellator.addVertexWithUV(xMin, yMin, zMax, minXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMax, yMin, zMax, maxXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMax, yMax, zMax, maxXGlass, minYGlass);
			
			tessellator.addVertexWithUV(xMin, yMax, zMax, minXSouth, minYSouth);
			tessellator.addVertexWithUV(xMin, yMin, zMax, minXSouth, maxYSouth);
			tessellator.addVertexWithUV(xMax, yMin, zMax, maxXSouth, maxYSouth);
			tessellator.addVertexWithUV(xMax, yMax, zMax, maxXSouth, minYSouth);
		}
		
		if (renderSide[4])
		{
			tessellator.addVertexWithUV(xMin, yMax, zMin, minXGlass, minYGlass);
			tessellator.addVertexWithUV(xMin, yMin, zMin, minXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMin, yMin, zMax, maxXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMin, yMax, zMax, maxXGlass, minYGlass);
			
			tessellator.addVertexWithUV(xMin, yMax, zMin, minXWest, minYWest);
			tessellator.addVertexWithUV(xMin, yMin, zMin, minXWest, maxYWest);
			tessellator.addVertexWithUV(xMin, yMin, zMax, maxXWest, maxYWest);
			tessellator.addVertexWithUV(xMin, yMax, zMax, maxXWest, minYWest);
		}

		if (renderSide[5])
		{
			tessellator.addVertexWithUV(xMax, yMax, zMin, minXGlass, minYGlass);
			tessellator.addVertexWithUV(xMax, yMin, zMin, minXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMax, yMin, zMax, maxXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMax, yMax, zMax, maxXGlass, minYGlass);
			
			tessellator.addVertexWithUV(xMax, yMax, zMin, minXWest, minYWest);
			tessellator.addVertexWithUV(xMax, yMin, zMin, minXWest, maxYWest);
			tessellator.addVertexWithUV(xMax, yMin, zMax, maxXWest, maxYWest);
			tessellator.addVertexWithUV(xMax, yMax, zMax, maxXWest, minYWest);
		}
		
		return true;
	}
	
	@Override
	public boolean shouldRender3DInInventory()
	{
		return true;
	}
	
	@Override
	public int getRenderId()
	{
		return MineFactoryReloadedCore.renderIdFactoryGlass;
	}
}
