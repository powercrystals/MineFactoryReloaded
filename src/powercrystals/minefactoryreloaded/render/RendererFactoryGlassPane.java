package powercrystals.minefactoryreloaded.render;

import powercrystals.minefactoryreloaded.MineFactoryReloadedClient;
import powercrystals.minefactoryreloaded.decorative.BlockFactoryGlassPane;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RendererFactoryGlassPane implements ISimpleBlockRenderingHandler
{
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		BlockFactoryGlassPane pane = (BlockFactoryGlassPane)block;
		
        int var5 = world.getHeight();
        Tessellator var6 = Tessellator.instance;
        var6.setBrightness(pane.getMixedBrightnessForBlock(world, x, y, z));
        float var7 = 1.0F;
        int var8 = pane.colorMultiplier(world, x, y, z);
        float var9 = (float)(var8 >> 16 & 255) / 255.0F;
        float var10 = (float)(var8 >> 8 & 255) / 255.0F;
        float var11 = (float)(var8 & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
            float var13 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
            float var14 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
            var9 = var12;
            var10 = var13;
            var11 = var14;
        }

        var6.setColorOpaque_F(var7 * var9, var7 * var10, var7 * var11);
        int var68;
        int var65;
        int var66;

        var68 = world.getBlockMetadata(x, y, z);
        var65 = pane.getBlockTextureFromSideAndMetadata(0, var68);
        var66 = pane.getBlockSideTextureFromMetadata(var68);

        var68 = (var65 & 15) << 4;
        int var15 = var65 & 240;
        double var16 = (double)((float)var68 / 256.0F);
        double var18 = (double)(((float)var68 + 7.99F) / 256.0F);
        double var20 = (double)(((float)var68 + 15.99F) / 256.0F);
        double var22 = (double)((float)var15 / 256.0F);
        double var24 = (double)(((float)var15 + 15.99F) / 256.0F);
        int var26 = (var66 & 15) << 4;
        int var27 = var66 & 240;
        double var28 = (double)((float)(var26 + 7) / 256.0F);
        double var30 = (double)(((float)var26 + 8.99F) / 256.0F);
        double var32 = (double)((float)var27 / 256.0F);
        double var34 = (double)((float)(var27 + 8) / 256.0F);
        double var36 = (double)(((float)var27 + 15.99F) / 256.0F);
        double var38 = (double)x;
        double var40 = (double)x + 0.5D;
        double var42 = (double)(x + 1);
        double var44 = (double)z;
        double var46 = (double)z + 0.5D;
        double var48 = (double)(z + 1);
        double var50 = (double)x + 0.5D - 0.0625D;
        double var52 = (double)x + 0.5D + 0.0625D;
        double var54 = (double)z + 0.5D - 0.0625D;
        double var56 = (double)z + 0.5D + 0.0625D;
        boolean var58 = pane.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x, y, z - 1));
        boolean var59 = pane.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x, y, z + 1));
        boolean var60 = pane.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x - 1, y, z));
        boolean var61 = pane.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x + 1, y, z));
        boolean var62 = pane.shouldSideBeRendered(world, x, y + 1, z, 1);
        boolean var63 = pane.shouldSideBeRendered(world, x, y - 1, z, 0);

        if ((!var60 || !var61) && (var60 || var61 || var58 || var59))
        {
            if (var60 && !var61)
            {
                var6.addVertexWithUV(var38, (double)(y + 1), var46, var16, var22);
                var6.addVertexWithUV(var38, (double)(y + 0), var46, var16, var24);
                var6.addVertexWithUV(var40, (double)(y + 0), var46, var18, var24);
                var6.addVertexWithUV(var40, (double)(y + 1), var46, var18, var22);
                var6.addVertexWithUV(var40, (double)(y + 1), var46, var16, var22);
                var6.addVertexWithUV(var40, (double)(y + 0), var46, var16, var24);
                var6.addVertexWithUV(var38, (double)(y + 0), var46, var18, var24);
                var6.addVertexWithUV(var38, (double)(y + 1), var46, var18, var22);

                if (!var59 && !var58)
                {
                    var6.addVertexWithUV(var40, (double)(y + 1), var56, var28, var32);
                    var6.addVertexWithUV(var40, (double)(y + 0), var56, var28, var36);
                    var6.addVertexWithUV(var40, (double)(y + 0), var54, var30, var36);
                    var6.addVertexWithUV(var40, (double)(y + 1), var54, var30, var32);
                    var6.addVertexWithUV(var40, (double)(y + 1), var54, var28, var32);
                    var6.addVertexWithUV(var40, (double)(y + 0), var54, var28, var36);
                    var6.addVertexWithUV(var40, (double)(y + 0), var56, var30, var36);
                    var6.addVertexWithUV(var40, (double)(y + 1), var56, var30, var32);
                }

                if (var62 || y < var5 - 1 && world.isAirBlock(x - 1, y + 1, z))
                {
                    var6.addVertexWithUV(var38, (double)(y + 1) + 0.01D, var56, var30, var34);
                    var6.addVertexWithUV(var40, (double)(y + 1) + 0.01D, var56, var30, var36);
                    var6.addVertexWithUV(var40, (double)(y + 1) + 0.01D, var54, var28, var36);
                    var6.addVertexWithUV(var38, (double)(y + 1) + 0.01D, var54, var28, var34);
                    var6.addVertexWithUV(var40, (double)(y + 1) + 0.01D, var56, var30, var34);
                    var6.addVertexWithUV(var38, (double)(y + 1) + 0.01D, var56, var30, var36);
                    var6.addVertexWithUV(var38, (double)(y + 1) + 0.01D, var54, var28, var36);
                    var6.addVertexWithUV(var40, (double)(y + 1) + 0.01D, var54, var28, var34);
                }

                if (var63 || y > 1 && world.isAirBlock(x - 1, y - 1, z))
                {
                    var6.addVertexWithUV(var38, (double)y - 0.01D, var56, var30, var34);
                    var6.addVertexWithUV(var40, (double)y - 0.01D, var56, var30, var36);
                    var6.addVertexWithUV(var40, (double)y - 0.01D, var54, var28, var36);
                    var6.addVertexWithUV(var38, (double)y - 0.01D, var54, var28, var34);
                    var6.addVertexWithUV(var40, (double)y - 0.01D, var56, var30, var34);
                    var6.addVertexWithUV(var38, (double)y - 0.01D, var56, var30, var36);
                    var6.addVertexWithUV(var38, (double)y - 0.01D, var54, var28, var36);
                    var6.addVertexWithUV(var40, (double)y - 0.01D, var54, var28, var34);
                }
            }
            else if (!var60 && var61)
            {
                var6.addVertexWithUV(var40, (double)(y + 1), var46, var18, var22);
                var6.addVertexWithUV(var40, (double)(y + 0), var46, var18, var24);
                var6.addVertexWithUV(var42, (double)(y + 0), var46, var20, var24);
                var6.addVertexWithUV(var42, (double)(y + 1), var46, var20, var22);
                var6.addVertexWithUV(var42, (double)(y + 1), var46, var18, var22);
                var6.addVertexWithUV(var42, (double)(y + 0), var46, var18, var24);
                var6.addVertexWithUV(var40, (double)(y + 0), var46, var20, var24);
                var6.addVertexWithUV(var40, (double)(y + 1), var46, var20, var22);

                if (!var59 && !var58)
                {
                    var6.addVertexWithUV(var40, (double)(y + 1), var54, var28, var32);
                    var6.addVertexWithUV(var40, (double)(y + 0), var54, var28, var36);
                    var6.addVertexWithUV(var40, (double)(y + 0), var56, var30, var36);
                    var6.addVertexWithUV(var40, (double)(y + 1), var56, var30, var32);
                    var6.addVertexWithUV(var40, (double)(y + 1), var56, var28, var32);
                    var6.addVertexWithUV(var40, (double)(y + 0), var56, var28, var36);
                    var6.addVertexWithUV(var40, (double)(y + 0), var54, var30, var36);
                    var6.addVertexWithUV(var40, (double)(y + 1), var54, var30, var32);
                }

                if (var62 || y < var5 - 1 && world.isAirBlock(x + 1, y + 1, z))
                {
                    var6.addVertexWithUV(var40, (double)(y + 1) + 0.01D, var56, var30, var32);
                    var6.addVertexWithUV(var42, (double)(y + 1) + 0.01D, var56, var30, var34);
                    var6.addVertexWithUV(var42, (double)(y + 1) + 0.01D, var54, var28, var34);
                    var6.addVertexWithUV(var40, (double)(y + 1) + 0.01D, var54, var28, var32);
                    var6.addVertexWithUV(var42, (double)(y + 1) + 0.01D, var56, var30, var32);
                    var6.addVertexWithUV(var40, (double)(y + 1) + 0.01D, var56, var30, var34);
                    var6.addVertexWithUV(var40, (double)(y + 1) + 0.01D, var54, var28, var34);
                    var6.addVertexWithUV(var42, (double)(y + 1) + 0.01D, var54, var28, var32);
                }

                if (var63 || y > 1 && world.isAirBlock(x + 1, y - 1, z))
                {
                    var6.addVertexWithUV(var40, (double)y - 0.01D, var56, var30, var32);
                    var6.addVertexWithUV(var42, (double)y - 0.01D, var56, var30, var34);
                    var6.addVertexWithUV(var42, (double)y - 0.01D, var54, var28, var34);
                    var6.addVertexWithUV(var40, (double)y - 0.01D, var54, var28, var32);
                    var6.addVertexWithUV(var42, (double)y - 0.01D, var56, var30, var32);
                    var6.addVertexWithUV(var40, (double)y - 0.01D, var56, var30, var34);
                    var6.addVertexWithUV(var40, (double)y - 0.01D, var54, var28, var34);
                    var6.addVertexWithUV(var42, (double)y - 0.01D, var54, var28, var32);
                }
            }
        }
        else
        {
            var6.addVertexWithUV(var38, (double)(y + 1), var46, var16, var22);
            var6.addVertexWithUV(var38, (double)(y + 0), var46, var16, var24);
            var6.addVertexWithUV(var42, (double)(y + 0), var46, var20, var24);
            var6.addVertexWithUV(var42, (double)(y + 1), var46, var20, var22);
            var6.addVertexWithUV(var42, (double)(y + 1), var46, var16, var22);
            var6.addVertexWithUV(var42, (double)(y + 0), var46, var16, var24);
            var6.addVertexWithUV(var38, (double)(y + 0), var46, var20, var24);
            var6.addVertexWithUV(var38, (double)(y + 1), var46, var20, var22);

            if (var62)
            {
                var6.addVertexWithUV(var38, (double)(y + 1) + 0.01D, var56, var30, var36);
                var6.addVertexWithUV(var42, (double)(y + 1) + 0.01D, var56, var30, var32);
                var6.addVertexWithUV(var42, (double)(y + 1) + 0.01D, var54, var28, var32);
                var6.addVertexWithUV(var38, (double)(y + 1) + 0.01D, var54, var28, var36);
                var6.addVertexWithUV(var42, (double)(y + 1) + 0.01D, var56, var30, var36);
                var6.addVertexWithUV(var38, (double)(y + 1) + 0.01D, var56, var30, var32);
                var6.addVertexWithUV(var38, (double)(y + 1) + 0.01D, var54, var28, var32);
                var6.addVertexWithUV(var42, (double)(y + 1) + 0.01D, var54, var28, var36);
            }
            else
            {
                if (y < var5 - 1 && world.isAirBlock(x - 1, y + 1, z))
                {
                    var6.addVertexWithUV(var38, (double)(y + 1) + 0.01D, var56, var30, var34);
                    var6.addVertexWithUV(var40, (double)(y + 1) + 0.01D, var56, var30, var36);
                    var6.addVertexWithUV(var40, (double)(y + 1) + 0.01D, var54, var28, var36);
                    var6.addVertexWithUV(var38, (double)(y + 1) + 0.01D, var54, var28, var34);
                    var6.addVertexWithUV(var40, (double)(y + 1) + 0.01D, var56, var30, var34);
                    var6.addVertexWithUV(var38, (double)(y + 1) + 0.01D, var56, var30, var36);
                    var6.addVertexWithUV(var38, (double)(y + 1) + 0.01D, var54, var28, var36);
                    var6.addVertexWithUV(var40, (double)(y + 1) + 0.01D, var54, var28, var34);
                }

                if (y < var5 - 1 && world.isAirBlock(x + 1, y + 1, z))
                {
                    var6.addVertexWithUV(var40, (double)(y + 1) + 0.01D, var56, var30, var32);
                    var6.addVertexWithUV(var42, (double)(y + 1) + 0.01D, var56, var30, var34);
                    var6.addVertexWithUV(var42, (double)(y + 1) + 0.01D, var54, var28, var34);
                    var6.addVertexWithUV(var40, (double)(y + 1) + 0.01D, var54, var28, var32);
                    var6.addVertexWithUV(var42, (double)(y + 1) + 0.01D, var56, var30, var32);
                    var6.addVertexWithUV(var40, (double)(y + 1) + 0.01D, var56, var30, var34);
                    var6.addVertexWithUV(var40, (double)(y + 1) + 0.01D, var54, var28, var34);
                    var6.addVertexWithUV(var42, (double)(y + 1) + 0.01D, var54, var28, var32);
                }
            }

            if (var63)
            {
                var6.addVertexWithUV(var38, (double)y - 0.01D, var56, var30, var36);
                var6.addVertexWithUV(var42, (double)y - 0.01D, var56, var30, var32);
                var6.addVertexWithUV(var42, (double)y - 0.01D, var54, var28, var32);
                var6.addVertexWithUV(var38, (double)y - 0.01D, var54, var28, var36);
                var6.addVertexWithUV(var42, (double)y - 0.01D, var56, var30, var36);
                var6.addVertexWithUV(var38, (double)y - 0.01D, var56, var30, var32);
                var6.addVertexWithUV(var38, (double)y - 0.01D, var54, var28, var32);
                var6.addVertexWithUV(var42, (double)y - 0.01D, var54, var28, var36);
            }
            else
            {
                if (y > 1 && world.isAirBlock(x - 1, y - 1, z))
                {
                    var6.addVertexWithUV(var38, (double)y - 0.01D, var56, var30, var34);
                    var6.addVertexWithUV(var40, (double)y - 0.01D, var56, var30, var36);
                    var6.addVertexWithUV(var40, (double)y - 0.01D, var54, var28, var36);
                    var6.addVertexWithUV(var38, (double)y - 0.01D, var54, var28, var34);
                    var6.addVertexWithUV(var40, (double)y - 0.01D, var56, var30, var34);
                    var6.addVertexWithUV(var38, (double)y - 0.01D, var56, var30, var36);
                    var6.addVertexWithUV(var38, (double)y - 0.01D, var54, var28, var36);
                    var6.addVertexWithUV(var40, (double)y - 0.01D, var54, var28, var34);
                }

                if (y > 1 && world.isAirBlock(x + 1, y - 1, z))
                {
                    var6.addVertexWithUV(var40, (double)y - 0.01D, var56, var30, var32);
                    var6.addVertexWithUV(var42, (double)y - 0.01D, var56, var30, var34);
                    var6.addVertexWithUV(var42, (double)y - 0.01D, var54, var28, var34);
                    var6.addVertexWithUV(var40, (double)y - 0.01D, var54, var28, var32);
                    var6.addVertexWithUV(var42, (double)y - 0.01D, var56, var30, var32);
                    var6.addVertexWithUV(var40, (double)y - 0.01D, var56, var30, var34);
                    var6.addVertexWithUV(var40, (double)y - 0.01D, var54, var28, var34);
                    var6.addVertexWithUV(var42, (double)y - 0.01D, var54, var28, var32);
                }
            }
        }

        if ((!var58 || !var59) && (var60 || var61 || var58 || var59))
        {
            if (var58 && !var59)
            {
                var6.addVertexWithUV(var40, (double)(y + 1), var44, var16, var22);
                var6.addVertexWithUV(var40, (double)(y + 0), var44, var16, var24);
                var6.addVertexWithUV(var40, (double)(y + 0), var46, var18, var24);
                var6.addVertexWithUV(var40, (double)(y + 1), var46, var18, var22);
                var6.addVertexWithUV(var40, (double)(y + 1), var46, var16, var22);
                var6.addVertexWithUV(var40, (double)(y + 0), var46, var16, var24);
                var6.addVertexWithUV(var40, (double)(y + 0), var44, var18, var24);
                var6.addVertexWithUV(var40, (double)(y + 1), var44, var18, var22);

                if (!var61 && !var60)
                {
                    var6.addVertexWithUV(var50, (double)(y + 1), var46, var28, var32);
                    var6.addVertexWithUV(var50, (double)(y + 0), var46, var28, var36);
                    var6.addVertexWithUV(var52, (double)(y + 0), var46, var30, var36);
                    var6.addVertexWithUV(var52, (double)(y + 1), var46, var30, var32);
                    var6.addVertexWithUV(var52, (double)(y + 1), var46, var28, var32);
                    var6.addVertexWithUV(var52, (double)(y + 0), var46, var28, var36);
                    var6.addVertexWithUV(var50, (double)(y + 0), var46, var30, var36);
                    var6.addVertexWithUV(var50, (double)(y + 1), var46, var30, var32);
                }

                if (var62 || y < var5 - 1 && world.isAirBlock(x, y + 1, z - 1))
                {
                    var6.addVertexWithUV(var50, (double)(y + 1), var44, var30, var32);
                    var6.addVertexWithUV(var50, (double)(y + 1), var46, var30, var34);
                    var6.addVertexWithUV(var52, (double)(y + 1), var46, var28, var34);
                    var6.addVertexWithUV(var52, (double)(y + 1), var44, var28, var32);
                    var6.addVertexWithUV(var50, (double)(y + 1), var46, var30, var32);
                    var6.addVertexWithUV(var50, (double)(y + 1), var44, var30, var34);
                    var6.addVertexWithUV(var52, (double)(y + 1), var44, var28, var34);
                    var6.addVertexWithUV(var52, (double)(y + 1), var46, var28, var32);
                }

                if (var63 || y > 1 && world.isAirBlock(x, y - 1, z - 1))
                {
                    var6.addVertexWithUV(var50, (double)y, var44, var30, var32);
                    var6.addVertexWithUV(var50, (double)y, var46, var30, var34);
                    var6.addVertexWithUV(var52, (double)y, var46, var28, var34);
                    var6.addVertexWithUV(var52, (double)y, var44, var28, var32);
                    var6.addVertexWithUV(var50, (double)y, var46, var30, var32);
                    var6.addVertexWithUV(var50, (double)y, var44, var30, var34);
                    var6.addVertexWithUV(var52, (double)y, var44, var28, var34);
                    var6.addVertexWithUV(var52, (double)y, var46, var28, var32);
                }
            }
            else if (!var58 && var59)
            {
                var6.addVertexWithUV(var40, (double)(y + 1), var46, var18, var22);
                var6.addVertexWithUV(var40, (double)(y + 0), var46, var18, var24);
                var6.addVertexWithUV(var40, (double)(y + 0), var48, var20, var24);
                var6.addVertexWithUV(var40, (double)(y + 1), var48, var20, var22);
                var6.addVertexWithUV(var40, (double)(y + 1), var48, var18, var22);
                var6.addVertexWithUV(var40, (double)(y + 0), var48, var18, var24);
                var6.addVertexWithUV(var40, (double)(y + 0), var46, var20, var24);
                var6.addVertexWithUV(var40, (double)(y + 1), var46, var20, var22);

                if (!var61 && !var60)
                {
                    var6.addVertexWithUV(var52, (double)(y + 1), var46, var28, var32);
                    var6.addVertexWithUV(var52, (double)(y + 0), var46, var28, var36);
                    var6.addVertexWithUV(var50, (double)(y + 0), var46, var30, var36);
                    var6.addVertexWithUV(var50, (double)(y + 1), var46, var30, var32);
                    var6.addVertexWithUV(var50, (double)(y + 1), var46, var28, var32);
                    var6.addVertexWithUV(var50, (double)(y + 0), var46, var28, var36);
                    var6.addVertexWithUV(var52, (double)(y + 0), var46, var30, var36);
                    var6.addVertexWithUV(var52, (double)(y + 1), var46, var30, var32);
                }

                if (var62 || y < var5 - 1 && world.isAirBlock(x, y + 1, z + 1))
                {
                    var6.addVertexWithUV(var50, (double)(y + 1), var46, var28, var34);
                    var6.addVertexWithUV(var50, (double)(y + 1), var48, var28, var36);
                    var6.addVertexWithUV(var52, (double)(y + 1), var48, var30, var36);
                    var6.addVertexWithUV(var52, (double)(y + 1), var46, var30, var34);
                    var6.addVertexWithUV(var50, (double)(y + 1), var48, var28, var34);
                    var6.addVertexWithUV(var50, (double)(y + 1), var46, var28, var36);
                    var6.addVertexWithUV(var52, (double)(y + 1), var46, var30, var36);
                    var6.addVertexWithUV(var52, (double)(y + 1), var48, var30, var34);
                }

                if (var63 || y > 1 && world.isAirBlock(x, y - 1, z + 1))
                {
                    var6.addVertexWithUV(var50, (double)y, var46, var28, var34);
                    var6.addVertexWithUV(var50, (double)y, var48, var28, var36);
                    var6.addVertexWithUV(var52, (double)y, var48, var30, var36);
                    var6.addVertexWithUV(var52, (double)y, var46, var30, var34);
                    var6.addVertexWithUV(var50, (double)y, var48, var28, var34);
                    var6.addVertexWithUV(var50, (double)y, var46, var28, var36);
                    var6.addVertexWithUV(var52, (double)y, var46, var30, var36);
                    var6.addVertexWithUV(var52, (double)y, var48, var30, var34);
                }
            }
        }
        else
        {
            var6.addVertexWithUV(var40, (double)(y + 1), var48, var16, var22);
            var6.addVertexWithUV(var40, (double)(y + 0), var48, var16, var24);
            var6.addVertexWithUV(var40, (double)(y + 0), var44, var20, var24);
            var6.addVertexWithUV(var40, (double)(y + 1), var44, var20, var22);
            var6.addVertexWithUV(var40, (double)(y + 1), var44, var16, var22);
            var6.addVertexWithUV(var40, (double)(y + 0), var44, var16, var24);
            var6.addVertexWithUV(var40, (double)(y + 0), var48, var20, var24);
            var6.addVertexWithUV(var40, (double)(y + 1), var48, var20, var22);

            if (var62)
            {
                var6.addVertexWithUV(var52, (double)(y + 1), var48, var30, var36);
                var6.addVertexWithUV(var52, (double)(y + 1), var44, var30, var32);
                var6.addVertexWithUV(var50, (double)(y + 1), var44, var28, var32);
                var6.addVertexWithUV(var50, (double)(y + 1), var48, var28, var36);
                var6.addVertexWithUV(var52, (double)(y + 1), var44, var30, var36);
                var6.addVertexWithUV(var52, (double)(y + 1), var48, var30, var32);
                var6.addVertexWithUV(var50, (double)(y + 1), var48, var28, var32);
                var6.addVertexWithUV(var50, (double)(y + 1), var44, var28, var36);
            }
            else
            {
                if (y < var5 - 1 && world.isAirBlock(x, y + 1, z - 1))
                {
                    var6.addVertexWithUV(var50, (double)(y + 1), var44, var30, var32);
                    var6.addVertexWithUV(var50, (double)(y + 1), var46, var30, var34);
                    var6.addVertexWithUV(var52, (double)(y + 1), var46, var28, var34);
                    var6.addVertexWithUV(var52, (double)(y + 1), var44, var28, var32);
                    var6.addVertexWithUV(var50, (double)(y + 1), var46, var30, var32);
                    var6.addVertexWithUV(var50, (double)(y + 1), var44, var30, var34);
                    var6.addVertexWithUV(var52, (double)(y + 1), var44, var28, var34);
                    var6.addVertexWithUV(var52, (double)(y + 1), var46, var28, var32);
                }

                if (y < var5 - 1 && world.isAirBlock(x, y + 1, z + 1))
                {
                    var6.addVertexWithUV(var50, (double)(y + 1), var46, var28, var34);
                    var6.addVertexWithUV(var50, (double)(y + 1), var48, var28, var36);
                    var6.addVertexWithUV(var52, (double)(y + 1), var48, var30, var36);
                    var6.addVertexWithUV(var52, (double)(y + 1), var46, var30, var34);
                    var6.addVertexWithUV(var50, (double)(y + 1), var48, var28, var34);
                    var6.addVertexWithUV(var50, (double)(y + 1), var46, var28, var36);
                    var6.addVertexWithUV(var52, (double)(y + 1), var46, var30, var36);
                    var6.addVertexWithUV(var52, (double)(y + 1), var48, var30, var34);
                }
            }

            if (var63)
            {
                var6.addVertexWithUV(var52, (double)y, var48, var30, var36);
                var6.addVertexWithUV(var52, (double)y, var44, var30, var32);
                var6.addVertexWithUV(var50, (double)y, var44, var28, var32);
                var6.addVertexWithUV(var50, (double)y, var48, var28, var36);
                var6.addVertexWithUV(var52, (double)y, var44, var30, var36);
                var6.addVertexWithUV(var52, (double)y, var48, var30, var32);
                var6.addVertexWithUV(var50, (double)y, var48, var28, var32);
                var6.addVertexWithUV(var50, (double)y, var44, var28, var36);
            }
            else
            {
                if (y > 1 && world.isAirBlock(x, y - 1, z - 1))
                {
                    var6.addVertexWithUV(var50, (double)y, var44, var30, var32);
                    var6.addVertexWithUV(var50, (double)y, var46, var30, var34);
                    var6.addVertexWithUV(var52, (double)y, var46, var28, var34);
                    var6.addVertexWithUV(var52, (double)y, var44, var28, var32);
                    var6.addVertexWithUV(var50, (double)y, var46, var30, var32);
                    var6.addVertexWithUV(var50, (double)y, var44, var30, var34);
                    var6.addVertexWithUV(var52, (double)y, var44, var28, var34);
                    var6.addVertexWithUV(var52, (double)y, var46, var28, var32);
                }

                if (y > 1 && world.isAirBlock(x, y - 1, z + 1))
                {
                    var6.addVertexWithUV(var50, (double)y, var46, var28, var34);
                    var6.addVertexWithUV(var50, (double)y, var48, var28, var36);
                    var6.addVertexWithUV(var52, (double)y, var48, var30, var36);
                    var6.addVertexWithUV(var52, (double)y, var46, var30, var34);
                    var6.addVertexWithUV(var50, (double)y, var48, var28, var34);
                    var6.addVertexWithUV(var50, (double)y, var46, var28, var36);
                    var6.addVertexWithUV(var52, (double)y, var46, var30, var36);
                    var6.addVertexWithUV(var52, (double)y, var48, var30, var34);
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
		return MineFactoryReloadedClient.renderIdFactoryGlassPane;
	}
}
