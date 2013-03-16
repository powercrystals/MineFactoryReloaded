package powercrystals.minefactoryreloaded.render;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.block.BlockFactoryGlassPane;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

public class RendererFactoryGlassPane implements ISimpleBlockRenderingHandler
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
		float f1 = (float)(i1 >> 16 & 255) / 255.0F;
		float f2 = (float)(i1 >> 8 & 255) / 255.0F;
		float f3 = (float)(i1 & 255) / 255.0F;

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
		Icon iconFront;
		Icon iconSide;
		int metadata;

		if (renderer.func_94167_b())
		{
			iconFront = renderer.overrideBlockTexture;
			iconSide = renderer.overrideBlockTexture;
		}
		else
		{
			metadata = blockAccess.getBlockMetadata(x, y, z);
			iconFront = renderer.func_94165_a(pane, 0, metadata);
			iconSide = pane.getBlockSideTextureFromMetadata(metadata);
		}

		double d0 = (double)iconFront.func_94209_e();
		double d1 = (double)iconFront.func_94214_a(8.0D);
		double d2 = (double)iconFront.func_94212_f();
		double d3 = (double)iconFront.func_94206_g();
		double d4 = (double)iconFront.func_94210_h();
		
		double d5 = (double)iconSide.func_94214_a(7.0D);
		double d6 = (double)iconSide.func_94214_a(9.0D);
		double d7 = (double)iconSide.func_94206_g();
		double d8 = (double)iconSide.func_94207_b(8.0D);
		double d9 = (double)iconSide.func_94210_h();
		double d10 = (double)x;
		double d11 = (double)x + 0.5D;
		double d12 = (double)(x + 1);
		double d13 = (double)z;
		double d14 = (double)z + 0.5D;
		double d15 = (double)(z + 1);
		double d16 = (double)x + 0.5D - 0.0625D;
		double d17 = (double)x + 0.5D + 0.0625D;
		double d18 = (double)z + 0.5D - 0.0625D;
		double d19 = (double)z + 0.5D + 0.0625D;
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
				tessellator.addVertexWithUV(d10, (double)(y + 1), d14, d0, d3);
				tessellator.addVertexWithUV(d10, (double)(y + 0), d14, d0, d4);
				tessellator.addVertexWithUV(d11, (double)(y + 0), d14, d1, d4);
				tessellator.addVertexWithUV(d11, (double)(y + 1), d14, d1, d3);
				tessellator.addVertexWithUV(d11, (double)(y + 1), d14, d0, d3);
				tessellator.addVertexWithUV(d11, (double)(y + 0), d14, d0, d4);
				tessellator.addVertexWithUV(d10, (double)(y + 0), d14, d1, d4);
				tessellator.addVertexWithUV(d10, (double)(y + 1), d14, d1, d3);

				if (!connectedPosZ && !connectedNegZ)
				{
					tessellator.addVertexWithUV(d11, (double)(y + 1), d19, d5, d7);
					tessellator.addVertexWithUV(d11, (double)(y + 0), d19, d5, d9);
					tessellator.addVertexWithUV(d11, (double)(y + 0), d18, d6, d9);
					tessellator.addVertexWithUV(d11, (double)(y + 1), d18, d6, d7);
					tessellator.addVertexWithUV(d11, (double)(y + 1), d18, d5, d7);
					tessellator.addVertexWithUV(d11, (double)(y + 0), d18, d5, d9);
					tessellator.addVertexWithUV(d11, (double)(y + 0), d19, d6, d9);
					tessellator.addVertexWithUV(d11, (double)(y + 1), d19, d6, d7);
				}

				if (renderTop || y < l - 1 && blockAccess.isAirBlock(x - 1, y + 1, z))
				{
					tessellator.addVertexWithUV(d10, (double)(y + 1) + 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d11, (double)(y + 1) + 0.01D, d19, d6, d9);
					tessellator.addVertexWithUV(d11, (double)(y + 1) + 0.01D, d18, d5, d9);
					tessellator.addVertexWithUV(d10, (double)(y + 1) + 0.01D, d18, d5, d8);
					tessellator.addVertexWithUV(d11, (double)(y + 1) + 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d10, (double)(y + 1) + 0.01D, d19, d6, d9);
					tessellator.addVertexWithUV(d10, (double)(y + 1) + 0.01D, d18, d5, d9);
					tessellator.addVertexWithUV(d11, (double)(y + 1) + 0.01D, d18, d5, d8);
				}

				if (renderBottom || y > 1 && blockAccess.isAirBlock(x - 1, y - 1, z))
				{
					tessellator.addVertexWithUV(d10, (double)y - 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d11, (double)y - 0.01D, d19, d6, d9);
					tessellator.addVertexWithUV(d11, (double)y - 0.01D, d18, d5, d9);
					tessellator.addVertexWithUV(d10, (double)y - 0.01D, d18, d5, d8);
					tessellator.addVertexWithUV(d11, (double)y - 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d10, (double)y - 0.01D, d19, d6, d9);
					tessellator.addVertexWithUV(d10, (double)y - 0.01D, d18, d5, d9);
					tessellator.addVertexWithUV(d11, (double)y - 0.01D, d18, d5, d8);
				}
			}
			else if (!connectedNegX && connectedPosX)
			{
				tessellator.addVertexWithUV(d11, (double)(y + 1), d14, d1, d3);
				tessellator.addVertexWithUV(d11, (double)(y + 0), d14, d1, d4);
				tessellator.addVertexWithUV(d12, (double)(y + 0), d14, d2, d4);
				tessellator.addVertexWithUV(d12, (double)(y + 1), d14, d2, d3);
				tessellator.addVertexWithUV(d12, (double)(y + 1), d14, d1, d3);
				tessellator.addVertexWithUV(d12, (double)(y + 0), d14, d1, d4);
				tessellator.addVertexWithUV(d11, (double)(y + 0), d14, d2, d4);
				tessellator.addVertexWithUV(d11, (double)(y + 1), d14, d2, d3);

				if (!connectedPosZ && !connectedNegZ)
				{
					tessellator.addVertexWithUV(d11, (double)(y + 1), d18, d5, d7);
					tessellator.addVertexWithUV(d11, (double)(y + 0), d18, d5, d9);
					tessellator.addVertexWithUV(d11, (double)(y + 0), d19, d6, d9);
					tessellator.addVertexWithUV(d11, (double)(y + 1), d19, d6, d7);
					tessellator.addVertexWithUV(d11, (double)(y + 1), d19, d5, d7);
					tessellator.addVertexWithUV(d11, (double)(y + 0), d19, d5, d9);
					tessellator.addVertexWithUV(d11, (double)(y + 0), d18, d6, d9);
					tessellator.addVertexWithUV(d11, (double)(y + 1), d18, d6, d7);
				}

				if (renderTop || y < l - 1 && blockAccess.isAirBlock(x + 1, y + 1, z))
				{
					tessellator.addVertexWithUV(d11, (double)(y + 1) + 0.01D, d19, d6, d7);
					tessellator.addVertexWithUV(d12, (double)(y + 1) + 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d12, (double)(y + 1) + 0.01D, d18, d5, d8);
					tessellator.addVertexWithUV(d11, (double)(y + 1) + 0.01D, d18, d5, d7);
					tessellator.addVertexWithUV(d12, (double)(y + 1) + 0.01D, d19, d6, d7);
					tessellator.addVertexWithUV(d11, (double)(y + 1) + 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d11, (double)(y + 1) + 0.01D, d18, d5, d8);
					tessellator.addVertexWithUV(d12, (double)(y + 1) + 0.01D, d18, d5, d7);
				}

				if (renderBottom || y > 1 && blockAccess.isAirBlock(x + 1, y - 1, z))
				{
					tessellator.addVertexWithUV(d11, (double)y - 0.01D, d19, d6, d7);
					tessellator.addVertexWithUV(d12, (double)y - 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d12, (double)y - 0.01D, d18, d5, d8);
					tessellator.addVertexWithUV(d11, (double)y - 0.01D, d18, d5, d7);
					tessellator.addVertexWithUV(d12, (double)y - 0.01D, d19, d6, d7);
					tessellator.addVertexWithUV(d11, (double)y - 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d11, (double)y - 0.01D, d18, d5, d8);
					tessellator.addVertexWithUV(d12, (double)y - 0.01D, d18, d5, d7);
				}
			}
		}
		else
		{
			tessellator.addVertexWithUV(d10, (double)(y + 1), d14, d0, d3);
			tessellator.addVertexWithUV(d10, (double)(y + 0), d14, d0, d4);
			tessellator.addVertexWithUV(d12, (double)(y + 0), d14, d2, d4);
			tessellator.addVertexWithUV(d12, (double)(y + 1), d14, d2, d3);
			tessellator.addVertexWithUV(d12, (double)(y + 1), d14, d0, d3);
			tessellator.addVertexWithUV(d12, (double)(y + 0), d14, d0, d4);
			tessellator.addVertexWithUV(d10, (double)(y + 0), d14, d2, d4);
			tessellator.addVertexWithUV(d10, (double)(y + 1), d14, d2, d3);

			if (renderTop)
			{
				tessellator.addVertexWithUV(d10, (double)(y + 1) + 0.01D, d19, d6, d9);
				tessellator.addVertexWithUV(d12, (double)(y + 1) + 0.01D, d19, d6, d7);
				tessellator.addVertexWithUV(d12, (double)(y + 1) + 0.01D, d18, d5, d7);
				tessellator.addVertexWithUV(d10, (double)(y + 1) + 0.01D, d18, d5, d9);
				tessellator.addVertexWithUV(d12, (double)(y + 1) + 0.01D, d19, d6, d9);
				tessellator.addVertexWithUV(d10, (double)(y + 1) + 0.01D, d19, d6, d7);
				tessellator.addVertexWithUV(d10, (double)(y + 1) + 0.01D, d18, d5, d7);
				tessellator.addVertexWithUV(d12, (double)(y + 1) + 0.01D, d18, d5, d9);
			}
			else
			{
				if (y < l - 1 && blockAccess.isAirBlock(x - 1, y + 1, z))
				{
					tessellator.addVertexWithUV(d10, (double)(y + 1) + 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d11, (double)(y + 1) + 0.01D, d19, d6, d9);
					tessellator.addVertexWithUV(d11, (double)(y + 1) + 0.01D, d18, d5, d9);
					tessellator.addVertexWithUV(d10, (double)(y + 1) + 0.01D, d18, d5, d8);
					tessellator.addVertexWithUV(d11, (double)(y + 1) + 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d10, (double)(y + 1) + 0.01D, d19, d6, d9);
					tessellator.addVertexWithUV(d10, (double)(y + 1) + 0.01D, d18, d5, d9);
					tessellator.addVertexWithUV(d11, (double)(y + 1) + 0.01D, d18, d5, d8);
				}

				if (y < l - 1 && blockAccess.isAirBlock(x + 1, y + 1, z))
				{
					tessellator.addVertexWithUV(d11, (double)(y + 1) + 0.01D, d19, d6, d7);
					tessellator.addVertexWithUV(d12, (double)(y + 1) + 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d12, (double)(y + 1) + 0.01D, d18, d5, d8);
					tessellator.addVertexWithUV(d11, (double)(y + 1) + 0.01D, d18, d5, d7);
					tessellator.addVertexWithUV(d12, (double)(y + 1) + 0.01D, d19, d6, d7);
					tessellator.addVertexWithUV(d11, (double)(y + 1) + 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d11, (double)(y + 1) + 0.01D, d18, d5, d8);
					tessellator.addVertexWithUV(d12, (double)(y + 1) + 0.01D, d18, d5, d7);
				}
			}

			if (renderBottom)
			{
				tessellator.addVertexWithUV(d10, (double)y - 0.01D, d19, d6, d9);
				tessellator.addVertexWithUV(d12, (double)y - 0.01D, d19, d6, d7);
				tessellator.addVertexWithUV(d12, (double)y - 0.01D, d18, d5, d7);
				tessellator.addVertexWithUV(d10, (double)y - 0.01D, d18, d5, d9);
				tessellator.addVertexWithUV(d12, (double)y - 0.01D, d19, d6, d9);
				tessellator.addVertexWithUV(d10, (double)y - 0.01D, d19, d6, d7);
				tessellator.addVertexWithUV(d10, (double)y - 0.01D, d18, d5, d7);
				tessellator.addVertexWithUV(d12, (double)y - 0.01D, d18, d5, d9);
			}
			else
			{
				if (y > 1 && blockAccess.isAirBlock(x - 1, y - 1, z))
				{
					tessellator.addVertexWithUV(d10, (double)y - 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d11, (double)y - 0.01D, d19, d6, d9);
					tessellator.addVertexWithUV(d11, (double)y - 0.01D, d18, d5, d9);
					tessellator.addVertexWithUV(d10, (double)y - 0.01D, d18, d5, d8);
					tessellator.addVertexWithUV(d11, (double)y - 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d10, (double)y - 0.01D, d19, d6, d9);
					tessellator.addVertexWithUV(d10, (double)y - 0.01D, d18, d5, d9);
					tessellator.addVertexWithUV(d11, (double)y - 0.01D, d18, d5, d8);
				}

				if (y > 1 && blockAccess.isAirBlock(x + 1, y - 1, z))
				{
					tessellator.addVertexWithUV(d11, (double)y - 0.01D, d19, d6, d7);
					tessellator.addVertexWithUV(d12, (double)y - 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d12, (double)y - 0.01D, d18, d5, d8);
					tessellator.addVertexWithUV(d11, (double)y - 0.01D, d18, d5, d7);
					tessellator.addVertexWithUV(d12, (double)y - 0.01D, d19, d6, d7);
					tessellator.addVertexWithUV(d11, (double)y - 0.01D, d19, d6, d8);
					tessellator.addVertexWithUV(d11, (double)y - 0.01D, d18, d5, d8);
					tessellator.addVertexWithUV(d12, (double)y - 0.01D, d18, d5, d7);
				}
			}
		}

		if ((!connectedNegZ || !connectedPosZ) && (connectedNegX || connectedPosX || connectedNegZ || connectedPosZ))
		{
			if (connectedNegZ && !connectedPosZ)
			{
				tessellator.addVertexWithUV(d11, (double)(y + 1), d13, d0, d3);
				tessellator.addVertexWithUV(d11, (double)(y + 0), d13, d0, d4);
				tessellator.addVertexWithUV(d11, (double)(y + 0), d14, d1, d4);
				tessellator.addVertexWithUV(d11, (double)(y + 1), d14, d1, d3);
				tessellator.addVertexWithUV(d11, (double)(y + 1), d14, d0, d3);
				tessellator.addVertexWithUV(d11, (double)(y + 0), d14, d0, d4);
				tessellator.addVertexWithUV(d11, (double)(y + 0), d13, d1, d4);
				tessellator.addVertexWithUV(d11, (double)(y + 1), d13, d1, d3);

				if (!connectedPosX && !connectedNegX)
				{
					tessellator.addVertexWithUV(d16, (double)(y + 1), d14, d5, d7);
					tessellator.addVertexWithUV(d16, (double)(y + 0), d14, d5, d9);
					tessellator.addVertexWithUV(d17, (double)(y + 0), d14, d6, d9);
					tessellator.addVertexWithUV(d17, (double)(y + 1), d14, d6, d7);
					tessellator.addVertexWithUV(d17, (double)(y + 1), d14, d5, d7);
					tessellator.addVertexWithUV(d17, (double)(y + 0), d14, d5, d9);
					tessellator.addVertexWithUV(d16, (double)(y + 0), d14, d6, d9);
					tessellator.addVertexWithUV(d16, (double)(y + 1), d14, d6, d7);
				}

				if (renderTop || y < l - 1 && blockAccess.isAirBlock(x, y + 1, z - 1))
				{
					tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d13, d6, d7);
					tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d14, d6, d8);
					tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d14, d5, d8);
					tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d13, d5, d7);
					tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d14, d6, d7);
					tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d13, d6, d8);
					tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d13, d5, d8);
					tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d14, d5, d7);
				}

				if (renderBottom || y > 1 && blockAccess.isAirBlock(x, y - 1, z - 1))
				{
					tessellator.addVertexWithUV(d16, (double)y - 0.005D, d13, d6, d7);
					tessellator.addVertexWithUV(d16, (double)y - 0.005D, d14, d6, d8);
					tessellator.addVertexWithUV(d17, (double)y - 0.005D, d14, d5, d8);
					tessellator.addVertexWithUV(d17, (double)y - 0.005D, d13, d5, d7);
					tessellator.addVertexWithUV(d16, (double)y - 0.005D, d14, d6, d7);
					tessellator.addVertexWithUV(d16, (double)y - 0.005D, d13, d6, d8);
					tessellator.addVertexWithUV(d17, (double)y - 0.005D, d13, d5, d8);
					tessellator.addVertexWithUV(d17, (double)y - 0.005D, d14, d5, d7);
				}
			}
			else if (!connectedNegZ && connectedPosZ)
			{
				tessellator.addVertexWithUV(d11, (double)(y + 1), d14, d1, d3);
				tessellator.addVertexWithUV(d11, (double)(y + 0), d14, d1, d4);
				tessellator.addVertexWithUV(d11, (double)(y + 0), d15, d2, d4);
				tessellator.addVertexWithUV(d11, (double)(y + 1), d15, d2, d3);
				tessellator.addVertexWithUV(d11, (double)(y + 1), d15, d1, d3);
				tessellator.addVertexWithUV(d11, (double)(y + 0), d15, d1, d4);
				tessellator.addVertexWithUV(d11, (double)(y + 0), d14, d2, d4);
				tessellator.addVertexWithUV(d11, (double)(y + 1), d14, d2, d3);

				if (!connectedPosX && !connectedNegX)
				{
					tessellator.addVertexWithUV(d17, (double)(y + 1), d14, d5, d7);
					tessellator.addVertexWithUV(d17, (double)(y + 0), d14, d5, d9);
					tessellator.addVertexWithUV(d16, (double)(y + 0), d14, d6, d9);
					tessellator.addVertexWithUV(d16, (double)(y + 1), d14, d6, d7);
					tessellator.addVertexWithUV(d16, (double)(y + 1), d14, d5, d7);
					tessellator.addVertexWithUV(d16, (double)(y + 0), d14, d5, d9);
					tessellator.addVertexWithUV(d17, (double)(y + 0), d14, d6, d9);
					tessellator.addVertexWithUV(d17, (double)(y + 1), d14, d6, d7);
				}

				if (renderTop || y < l - 1 && blockAccess.isAirBlock(x, y + 1, z + 1))
				{
					tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d14, d5, d8);
					tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d15, d5, d9);
					tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d15, d6, d9);
					tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d14, d6, d8);
					tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d15, d5, d8);
					tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d14, d5, d9);
					tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d14, d6, d9);
					tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d15, d6, d8);
				}

				if (renderBottom || y > 1 && blockAccess.isAirBlock(x, y - 1, z + 1))
				{
					tessellator.addVertexWithUV(d16, (double)y - 0.005D, d14, d5, d8);
					tessellator.addVertexWithUV(d16, (double)y - 0.005D, d15, d5, d9);
					tessellator.addVertexWithUV(d17, (double)y - 0.005D, d15, d6, d9);
					tessellator.addVertexWithUV(d17, (double)y - 0.005D, d14, d6, d8);
					tessellator.addVertexWithUV(d16, (double)y - 0.005D, d15, d5, d8);
					tessellator.addVertexWithUV(d16, (double)y - 0.005D, d14, d5, d9);
					tessellator.addVertexWithUV(d17, (double)y - 0.005D, d14, d6, d9);
					tessellator.addVertexWithUV(d17, (double)y - 0.005D, d15, d6, d8);
				}
			}
		}
		else
		{
			tessellator.addVertexWithUV(d11, (double)(y + 1), d15, d0, d3);
			tessellator.addVertexWithUV(d11, (double)(y + 0), d15, d0, d4);
			tessellator.addVertexWithUV(d11, (double)(y + 0), d13, d2, d4);
			tessellator.addVertexWithUV(d11, (double)(y + 1), d13, d2, d3);
			tessellator.addVertexWithUV(d11, (double)(y + 1), d13, d0, d3);
			tessellator.addVertexWithUV(d11, (double)(y + 0), d13, d0, d4);
			tessellator.addVertexWithUV(d11, (double)(y + 0), d15, d2, d4);
			tessellator.addVertexWithUV(d11, (double)(y + 1), d15, d2, d3);

			if (renderTop)
			{
				tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d15, d6, d9);
				tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d13, d6, d7);
				tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d13, d5, d7);
				tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d15, d5, d9);
				tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d13, d6, d9);
				tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d15, d6, d7);
				tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d15, d5, d7);
				tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d13, d5, d9);
			}
			else
			{
				if (y < l - 1 && blockAccess.isAirBlock(x, y + 1, z - 1))
				{
					tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d13, d6, d7);
					tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d14, d6, d8);
					tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d14, d5, d8);
					tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d13, d5, d7);
					tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d14, d6, d7);
					tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d13, d6, d8);
					tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d13, d5, d8);
					tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d14, d5, d7);
				}

				if (y < l - 1 && blockAccess.isAirBlock(x, y + 1, z + 1))
				{
					tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d14, d5, d8);
					tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d15, d5, d9);
					tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d15, d6, d9);
					tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d14, d6, d8);
					tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d15, d5, d8);
					tessellator.addVertexWithUV(d16, (double)(y + 1) + 0.005D, d14, d5, d9);
					tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d14, d6, d9);
					tessellator.addVertexWithUV(d17, (double)(y + 1) + 0.005D, d15, d6, d8);
				}
			}

			if (renderBottom)
			{
				tessellator.addVertexWithUV(d17, (double)y - 0.005D, d15, d6, d9);
				tessellator.addVertexWithUV(d17, (double)y - 0.005D, d13, d6, d7);
				tessellator.addVertexWithUV(d16, (double)y - 0.005D, d13, d5, d7);
				tessellator.addVertexWithUV(d16, (double)y - 0.005D, d15, d5, d9);
				tessellator.addVertexWithUV(d17, (double)y - 0.005D, d13, d6, d9);
				tessellator.addVertexWithUV(d17, (double)y - 0.005D, d15, d6, d7);
				tessellator.addVertexWithUV(d16, (double)y - 0.005D, d15, d5, d7);
				tessellator.addVertexWithUV(d16, (double)y - 0.005D, d13, d5, d9);
			}
			else
			{
				if (y > 1 && blockAccess.isAirBlock(x, y - 1, z - 1))
				{
					tessellator.addVertexWithUV(d16, (double)y - 0.005D, d13, d6, d7);
					tessellator.addVertexWithUV(d16, (double)y - 0.005D, d14, d6, d8);
					tessellator.addVertexWithUV(d17, (double)y - 0.005D, d14, d5, d8);
					tessellator.addVertexWithUV(d17, (double)y - 0.005D, d13, d5, d7);
					tessellator.addVertexWithUV(d16, (double)y - 0.005D, d14, d6, d7);
					tessellator.addVertexWithUV(d16, (double)y - 0.005D, d13, d6, d8);
					tessellator.addVertexWithUV(d17, (double)y - 0.005D, d13, d5, d8);
					tessellator.addVertexWithUV(d17, (double)y - 0.005D, d14, d5, d7);
				}

				if (y > 1 && blockAccess.isAirBlock(x, y - 1, z + 1))
				{
					tessellator.addVertexWithUV(d16, (double)y - 0.005D, d14, d5, d8);
					tessellator.addVertexWithUV(d16, (double)y - 0.005D, d15, d5, d9);
					tessellator.addVertexWithUV(d17, (double)y - 0.005D, d15, d6, d9);
					tessellator.addVertexWithUV(d17, (double)y - 0.005D, d14, d6, d8);
					tessellator.addVertexWithUV(d16, (double)y - 0.005D, d15, d5, d8);
					tessellator.addVertexWithUV(d16, (double)y - 0.005D, d14, d5, d9);
					tessellator.addVertexWithUV(d17, (double)y - 0.005D, d14, d6, d9);
					tessellator.addVertexWithUV(d17, (double)y - 0.005D, d15, d6, d8);
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
