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
		
		int l = blockAccess.getHeight();
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(pane.getMixedBrightnessForBlock(blockAccess, x, y, z));
		float f = 1.0F;
		int i1 = pane.colorMultiplier(blockAccess, x, y, z);
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
		Icon iconFront, iconSide, iconOverlaySouth, iconOverlayWest;
		int metadata;
		
		if(renderer.hasOverrideBlockTexture())
		{
			iconFront = renderer.overrideBlockTexture;
			iconSide = renderer.overrideBlockTexture;
			iconOverlaySouth = renderer.overrideBlockTexture;
			iconOverlayWest = renderer.overrideBlockTexture;
		}
		else
		{
			metadata = blockAccess.getBlockMetadata(x, y, z);
			iconFront = renderer.getBlockIconFromSideAndMetadata(pane, 0, metadata);
			iconSide = pane.getBlockSideTextureFromMetadata(metadata);
			iconOverlaySouth = pane.getBlockOverlayTexture(blockAccess, x, y, z, 2);
			iconOverlayWest = pane.getBlockOverlayTexture(blockAccess, x, y, z, 5);
		}
		
		double d0 = iconFront.getMinU();
		double d1 = iconFront.getInterpolatedU(8.0D);
		double d2 = iconFront.getMaxU();
		double d3 = iconFront.getMinV();
		double d4 = iconFront.getMaxV();
		
		double d5 = iconSide.getInterpolatedU(7.0D);
		double d6 = iconSide.getInterpolatedU(9.0D);
		double d7 = iconSide.getMinV();
		double d8 = iconSide.getInterpolatedV(8.0D);
		double d9 = iconSide.getMaxV();

		double s0 = iconOverlaySouth.getMinU();
		double s1 = iconOverlaySouth.getInterpolatedU(8.0D);
		double s2 = iconOverlaySouth.getMaxU();
		double s3 = iconOverlaySouth.getMinV();
		double s4 = iconOverlaySouth.getMaxV();

		double w0 = iconOverlayWest.getMinU();
		double w1 = iconOverlayWest.getInterpolatedU(8.0D);
		double w2 = iconOverlayWest.getMaxU();
		double w3 = iconOverlayWest.getMinV();
		double w4 = iconOverlayWest.getMaxV();
		
		double d10 = x;
		double d11 = x + 0.5D;
		double d12 = x + 1;
		double d13 = z;
		double d14 = z + 0.5D;
		double d15 = z + 1;
		double d16 = x + 0.5D - 0.0625D;
		double d17 = x + 0.5D + 0.0625D;
		double d18 = z + 0.5D - 0.0625D;
		double d19 = z + 0.5D + 0.0625D;
		boolean connectedNegZ = pane.canThisFactoryPaneConnectToThisBlockID(blockAccess.getBlockId(x, y, z - 1));
		boolean connectedPosZ = pane.canThisFactoryPaneConnectToThisBlockID(blockAccess.getBlockId(x, y, z + 1));
		boolean connectedNegX = pane.canThisFactoryPaneConnectToThisBlockID(blockAccess.getBlockId(x - 1, y, z));
		boolean connectedPosX = pane.canThisFactoryPaneConnectToThisBlockID(blockAccess.getBlockId(x + 1, y, z));
		boolean renderTop = pane.shouldSideBeRendered(blockAccess, x, y + 1, z, 1);
		boolean renderBottom = pane.shouldSideBeRendered(blockAccess, x, y - 1, z, 0);
		
		if ((!connectedNegX || !connectedPosX) && (connectedNegX || connectedPosX || connectedNegZ || connectedPosZ))
		{
			if (connectedNegX && !connectedPosX)
			{
				tessellator.addVertexWithUV(d10, y + 1, d14, d0, d3);
				tessellator.addVertexWithUV(d10, y + 0, d14, d0, d4);
				tessellator.addVertexWithUV(d11, y + 0, d14, d1, d4);
				tessellator.addVertexWithUV(d11, y + 1, d14, d1, d3);
				
				tessellator.addVertexWithUV(d10, y + 1, d14, s0, s3);
				tessellator.addVertexWithUV(d10, y + 0, d14, s0, s4);
				tessellator.addVertexWithUV(d11, y + 0, d14, s1, s4);
				tessellator.addVertexWithUV(d11, y + 1, d14, s1, s3);
				
				if (!connectedPosZ && !connectedNegZ)
				{
					tessellator.addVertexWithUV(d11, y + 1, d19, d5, d7);
					tessellator.addVertexWithUV(d11, y + 0, d19, d5, d9);
					tessellator.addVertexWithUV(d11, y + 0, d18, d6, d9);
					tessellator.addVertexWithUV(d11, y + 1, d18, d6, d7); 
				}
				
				if (renderTop || y < l - 1 && blockAccess.isAirBlock(x - 1, y + 1, z))
				{
					tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d19, d6, d9);
					tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d9);
					tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d18, d5, d8);
				}
				
				if (renderBottom || y > 1 && blockAccess.isAirBlock(x - 1, y - 1, z))
				{
					tessellator.addVertexWithUV(d10, y - 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d11, y - 0.01D, d19, d6, d9);
					tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d9);
					tessellator.addVertexWithUV(d10, y - 0.01D, d18, d5, d8);
				}
			}
			else if (!connectedNegX && connectedPosX)
			{
				tessellator.addVertexWithUV(d11, y + 1, d14, d1, d3);
				tessellator.addVertexWithUV(d11, y + 0, d14, d1, d4);
				tessellator.addVertexWithUV(d12, y + 0, d14, d2, d4);
				tessellator.addVertexWithUV(d12, y + 1, d14, d2, d3);
				
				tessellator.addVertexWithUV(d11, y + 1, d14, s1, s3);
				tessellator.addVertexWithUV(d11, y + 0, d14, s1, s4);
				tessellator.addVertexWithUV(d12, y + 0, d14, s2, s4);
				tessellator.addVertexWithUV(d12, y + 1, d14, s2, s3);
				
				if (!connectedPosZ && !connectedNegZ)
				{
					tessellator.addVertexWithUV(d11, y + 1, d18, d5, d7);
					tessellator.addVertexWithUV(d11, y + 0, d18, d5, d9);
					tessellator.addVertexWithUV(d11, y + 0, d19, d6, d9);
					tessellator.addVertexWithUV(d11, y + 1, d19, d6, d7);
				}
				
				if (renderTop || y < l - 1 && blockAccess.isAirBlock(x + 1, y + 1, z))
				{
					tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d19, d6, d7);
					tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d18, d5, d8);
					tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d7);
				}
				
				if (renderBottom || y > 1 && blockAccess.isAirBlock(x + 1, y - 1, z))
				{
					tessellator.addVertexWithUV(d11, y - 0.01D, d19, d6, d7);
					tessellator.addVertexWithUV(d12, y - 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d12, y - 0.01D, d18, d5, d8);
					tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d7);
				}
			}
		}
		else
		{
			tessellator.addVertexWithUV(d10, y + 1, d14, d0, d3);
			tessellator.addVertexWithUV(d10, y + 0, d14, d0, d4);
			tessellator.addVertexWithUV(d12, y + 0, d14, d2, d4);
			tessellator.addVertexWithUV(d12, y + 1, d14, d2, d3);
			
			tessellator.addVertexWithUV(d10, y + 1, d14, s0, s3);
			tessellator.addVertexWithUV(d10, y + 0, d14, s0, s4);
			tessellator.addVertexWithUV(d12, y + 0, d14, s2, s4);
			tessellator.addVertexWithUV(d12, y + 1, d14, s2, s3);
			
			if (renderTop)
			{
				tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d19, d6, d9);
				tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d19, d6, d7);
				tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d18, d5, d7);
				tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d18, d5, d9);
			}
			else
			{
				if (y < l - 1 && blockAccess.isAirBlock(x - 1, y + 1, z))
				{
					tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d19, d6, d9);
					tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d9);
					tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d18, d5, d8);
				}
				
				if (y < l - 1 && blockAccess.isAirBlock(x + 1, y + 1, z))
				{
					tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d19, d6, d7);
					tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d12, y + 1 + 0.01D, d18, d5, d8);
					tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d7);
				}
			}
			
			if (renderBottom)
			{
				tessellator.addVertexWithUV(d10, y - 0.01D, d19, d6, d9);
				tessellator.addVertexWithUV(d12, y - 0.01D, d19, d6, d7);
				tessellator.addVertexWithUV(d12, y - 0.01D, d18, d5, d7);
				tessellator.addVertexWithUV(d10, y - 0.01D, d18, d5, d9);
			}
			else
			{
				if (y > 1 && blockAccess.isAirBlock(x - 1, y - 1, z))
				{
					tessellator.addVertexWithUV(d10, y - 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d11, y - 0.01D, d19, d6, d9);
					tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d9);
					tessellator.addVertexWithUV(d10, y - 0.01D, d18, d5, d8);
				}
				
				if (y > 1 && blockAccess.isAirBlock(x + 1, y - 1, z))
				{
					tessellator.addVertexWithUV(d11, y - 0.01D, d19, d6, d7);
					tessellator.addVertexWithUV(d12, y - 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d12, y - 0.01D, d18, d5, d8);
					tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d7);
				}
			}
		}
		
		if ((!connectedNegZ || !connectedPosZ) && (connectedNegX || connectedPosX || connectedNegZ || connectedPosZ))
		{
			if (connectedNegZ && !connectedPosZ)
			{
				tessellator.addVertexWithUV(d11, y + 1, d13, d0, d3);
				tessellator.addVertexWithUV(d11, y + 0, d13, d0, d4);
				tessellator.addVertexWithUV(d11, y + 0, d14, d1, d4);
				tessellator.addVertexWithUV(d11, y + 1, d14, d1, d3);
				
				tessellator.addVertexWithUV(d11, y + 1, d13, w0, w3);
				tessellator.addVertexWithUV(d11, y + 0, d13, w0, w4);
				tessellator.addVertexWithUV(d11, y + 0, d14, w1, w4);
				tessellator.addVertexWithUV(d11, y + 1, d14, w1, w3);
				
				if (!connectedPosX && !connectedNegX)
				{
					tessellator.addVertexWithUV(d16, y + 1, d14, d5, d7);
					tessellator.addVertexWithUV(d16, y + 0, d14, d5, d9);
					tessellator.addVertexWithUV(d17, y + 0, d14, d6, d9);
					tessellator.addVertexWithUV(d17, y + 1, d14, d6, d7);
				}
				
				if (renderTop || y < l - 1 && blockAccess.isAirBlock(x, y + 1, z - 1))
				{
					tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d13, d6, d7);
					tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d6, d8);
					tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d14, d5, d8);
					tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d13, d5, d7);
				}
				
				if (renderBottom || y > 1 && blockAccess.isAirBlock(x, y - 1, z - 1))
				{
					tessellator.addVertexWithUV(d16, y - 0.005D, d13, d6, d7);
					tessellator.addVertexWithUV(d16, y - 0.005D, d14, d6, d8);
					tessellator.addVertexWithUV(d17, y - 0.005D, d14, d5, d8);
					tessellator.addVertexWithUV(d17, y - 0.005D, d13, d5, d7);
				}
			}
			else if (!connectedNegZ && connectedPosZ)
			{
				tessellator.addVertexWithUV(d11, y + 1, d14, d1, d3);
				tessellator.addVertexWithUV(d11, y + 0, d14, d1, d4);
				tessellator.addVertexWithUV(d11, y + 0, d15, d2, d4);
				tessellator.addVertexWithUV(d11, y + 1, d15, d2, d3);
				
				tessellator.addVertexWithUV(d11, y + 1, d14, w1, w3);
				tessellator.addVertexWithUV(d11, y + 0, d14, w1, w4);
				tessellator.addVertexWithUV(d11, y + 0, d15, w2, w4);
				tessellator.addVertexWithUV(d11, y + 1, d15, w2, w3);
				
				if (!connectedPosX && !connectedNegX)
				{
					tessellator.addVertexWithUV(d17, y + 1, d14, d5, d7);
					tessellator.addVertexWithUV(d17, y + 0, d14, d5, d9);
					tessellator.addVertexWithUV(d16, y + 0, d14, d6, d9);
					tessellator.addVertexWithUV(d16, y + 1, d14, d6, d7);
				}
				
				if (renderTop || y < l - 1 && blockAccess.isAirBlock(x, y + 1, z + 1))
				{
					tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d5, d8);
					tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d15, d5, d9);
					tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d15, d6, d9);
					tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d14, d6, d8);
				}
				
				if (renderBottom || y > 1 && blockAccess.isAirBlock(x, y - 1, z + 1))
				{
					tessellator.addVertexWithUV(d16, y - 0.005D, d14, d5, d8);
					tessellator.addVertexWithUV(d16, y - 0.005D, d15, d5, d9);
					tessellator.addVertexWithUV(d17, y - 0.005D, d15, d6, d9);
					tessellator.addVertexWithUV(d17, y - 0.005D, d14, d6, d8);
				}
			}
		}
		else
		{
			tessellator.addVertexWithUV(d11, y + 1, d15, d0, d3);
			tessellator.addVertexWithUV(d11, y + 0, d15, d0, d4);
			tessellator.addVertexWithUV(d11, y + 0, d13, d2, d4);
			tessellator.addVertexWithUV(d11, y + 1, d13, d2, d3);
			
			tessellator.addVertexWithUV(d11, y + 1, d15, w0, w3);
			tessellator.addVertexWithUV(d11, y + 0, d15, w0, w4);
			tessellator.addVertexWithUV(d11, y + 0, d13, w2, w4);
			tessellator.addVertexWithUV(d11, y + 1, d13, w2, w3);
			
			if (renderTop)
			{
				tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d15, d6, d9);
				tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d13, d6, d7);
				tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d13, d5, d7);
				tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d15, d5, d9);
			}
			else
			{
				if (y < l - 1 && blockAccess.isAirBlock(x, y + 1, z - 1))
				{
					tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d13, d6, d7);
					tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d6, d8);
					tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d14, d5, d8);
					tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d13, d5, d7);
				}
				
				if (y < l - 1 && blockAccess.isAirBlock(x, y + 1, z + 1))
				{
					tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d5, d8);
					tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d15, d5, d9);
					tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d15, d6, d9);
					tessellator.addVertexWithUV(d17, y + 1 + 0.005D, d14, d6, d8);
				}
			}
			
			if (renderBottom)
			{
				tessellator.addVertexWithUV(d17, y - 0.005D, d15, d6, d9);
				tessellator.addVertexWithUV(d17, y - 0.005D, d13, d6, d7);
				tessellator.addVertexWithUV(d16, y - 0.005D, d13, d5, d7);
				tessellator.addVertexWithUV(d16, y - 0.005D, d15, d5, d9);
			}
			else
			{
				if (y > 1 && blockAccess.isAirBlock(x, y - 1, z - 1))
				{
					tessellator.addVertexWithUV(d16, y - 0.005D, d13, d6, d7);
					tessellator.addVertexWithUV(d16, y - 0.005D, d14, d6, d8);
					tessellator.addVertexWithUV(d17, y - 0.005D, d14, d5, d8);
					tessellator.addVertexWithUV(d17, y - 0.005D, d13, d5, d7);
				}
				
				if (y > 1 && blockAccess.isAirBlock(x, y - 1, z + 1))
				{
					tessellator.addVertexWithUV(d16, y - 0.005D, d14, d5, d8);
					tessellator.addVertexWithUV(d16, y - 0.005D, d15, d5, d9);
					tessellator.addVertexWithUV(d17, y - 0.005D, d15, d6, d9);
					tessellator.addVertexWithUV(d17, y - 0.005D, d14, d6, d8);
				}
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
