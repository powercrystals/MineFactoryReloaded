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

public class FactoryGlassRenderer implements ISimpleBlockRenderingHandler
{
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
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
		int metadata;
		
		if(renderer.hasOverrideBlockTexture())
		{
			iconGlass = renderer.overrideBlockTexture;
			iconOverlayTop = renderer.overrideBlockTexture;
			iconOverlaySouth = renderer.overrideBlockTexture;
			iconOverlayWest = renderer.overrideBlockTexture;
		}
		else
		{
			metadata = blockAccess.getBlockMetadata(x, y, z);
			iconGlass = renderer.getBlockIconFromSideAndMetadata(block, 0, metadata);
			iconOverlayTop = block.getBlockOverlayTexture(blockAccess, x, y, z, 0);
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
		
		double d10 = x, d11 = x + 1;
		double d12 = y, d13 = y + 1;
		double d14 = z, d15 = z + 1;
		
		boolean[] renderSide = {
				renderer.renderAllFaces || block.shouldSideBeRendered(blockAccess, x, y - 1, z, 0),
				renderer.renderAllFaces || block.shouldSideBeRendered(blockAccess, x, y + 1, z, 1),
				renderer.renderAllFaces || block.shouldSideBeRendered(blockAccess, x, y, z - 1, 2),
				renderer.renderAllFaces || block.shouldSideBeRendered(blockAccess, x, y, z + 1, 3),
				renderer.renderAllFaces || block.shouldSideBeRendered(blockAccess, x - 1, y, z, 4),
				renderer.renderAllFaces || block.shouldSideBeRendered(blockAccess, x + 1, y, z, 5),
			};

		if (renderSide[0])
		{
			tessellator.addVertexWithUV(d10, d12, d14, minXGlass, minYGlass);
			tessellator.addVertexWithUV(d10, d12, d15, minXGlass, maxYGlass);
			tessellator.addVertexWithUV(d11, d12, d15, maxXGlass, maxYGlass);
			tessellator.addVertexWithUV(d11, d12, d14, maxXGlass, minYGlass);
			
			tessellator.addVertexWithUV(d10, d12, d14, minXTop, minYTop);
			tessellator.addVertexWithUV(d10, d12, d15, minXTop, maxYTop);
			tessellator.addVertexWithUV(d11, d12, d15, maxXTop, maxYTop);
			tessellator.addVertexWithUV(d11, d12, d14, maxXTop, minYTop);
		}

		if (renderSide[1])
		{
			tessellator.addVertexWithUV(d10, d13, d14, minXGlass, minYGlass);
			tessellator.addVertexWithUV(d10, d13, d15, minXGlass, maxYGlass);
			tessellator.addVertexWithUV(d11, d13, d15, maxXGlass, maxYGlass);
			tessellator.addVertexWithUV(d11, d13, d14, maxXGlass, minYGlass);
			
			tessellator.addVertexWithUV(d10, d13, d14, minXTop, minYTop);
			tessellator.addVertexWithUV(d10, d13, d15, minXTop, maxYTop);
			tessellator.addVertexWithUV(d11, d13, d15, maxXTop, maxYTop);
			tessellator.addVertexWithUV(d11, d13, d14, maxXTop, minYTop);
		}
		
		if (renderSide[2])
		{
			tessellator.addVertexWithUV(d10, d13, d14, minXGlass, minYGlass);
			tessellator.addVertexWithUV(d10, d12, d14, minXGlass, maxYGlass);
			tessellator.addVertexWithUV(d11, d12, d14, maxXGlass, maxYGlass);
			tessellator.addVertexWithUV(d11, d13, d14, maxXGlass, minYGlass);
			
			tessellator.addVertexWithUV(d10, d13, d14, minXSouth, minYSouth);
			tessellator.addVertexWithUV(d10, d12, d14, minXSouth, maxYSouth);
			tessellator.addVertexWithUV(d11, d12, d14, maxXSouth, maxYSouth);
			tessellator.addVertexWithUV(d11, d13, d14, maxXSouth, minYSouth);
		}

		if (renderSide[3])
		{
			tessellator.addVertexWithUV(d10, d13, d15, minXGlass, minYGlass);
			tessellator.addVertexWithUV(d10, d12, d15, minXGlass, maxYGlass);
			tessellator.addVertexWithUV(d11, d12, d15, maxXGlass, maxYGlass);
			tessellator.addVertexWithUV(d11, d13, d15, maxXGlass, minYGlass);
			
			tessellator.addVertexWithUV(d10, d13, d15, minXSouth, minYSouth);
			tessellator.addVertexWithUV(d10, d12, d15, minXSouth, maxYSouth);
			tessellator.addVertexWithUV(d11, d12, d15, maxXSouth, maxYSouth);
			tessellator.addVertexWithUV(d11, d13, d15, maxXSouth, minYSouth);
		}
		
		if (renderSide[4])
		{
			tessellator.addVertexWithUV(d10, d13, d14, minXGlass, minYGlass);
			tessellator.addVertexWithUV(d10, d12, d14, minXGlass, maxYGlass);
			tessellator.addVertexWithUV(d10, d12, d15, maxXGlass, maxYGlass);
			tessellator.addVertexWithUV(d10, d13, d15, maxXGlass, minYGlass);
			
			tessellator.addVertexWithUV(d10, d13, d14, minXWest, minYWest);
			tessellator.addVertexWithUV(d10, d12, d14, minXWest, maxYWest);
			tessellator.addVertexWithUV(d10, d12, d15, maxXWest, maxYWest);
			tessellator.addVertexWithUV(d10, d13, d15, maxXWest, minYWest);
		}

		if (renderSide[5])
		{
			tessellator.addVertexWithUV(d11, d13, d14, minXGlass, minYGlass);
			tessellator.addVertexWithUV(d11, d12, d14, minXGlass, maxYGlass);
			tessellator.addVertexWithUV(d11, d12, d15, maxXGlass, maxYGlass);
			tessellator.addVertexWithUV(d11, d13, d15, maxXGlass, minYGlass);
			
			tessellator.addVertexWithUV(d11, d13, d14, minXWest, minYWest);
			tessellator.addVertexWithUV(d11, d12, d14, minXWest, maxYWest);
			tessellator.addVertexWithUV(d11, d12, d15, maxXWest, maxYWest);
			tessellator.addVertexWithUV(d11, d13, d15, maxXWest, minYWest);
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
		return MineFactoryReloadedCore.renderIdFactoryGlass;
	}
}
