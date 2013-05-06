package powercrystals.minefactoryreloaded.render.block;

import org.lwjgl.opengl.GL11;

import powercrystals.minefactoryreloaded.render.RenderBlocksInverted;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class VineScaffoldRenderer implements ISimpleBlockRenderingHandler
{
	private RenderBlocksInverted _invertedRenderer = new RenderBlocksInverted();
	
	@Override
	public void renderInventoryBlock(Block par1Block, int par2, int modelID, RenderBlocks renderer)
	{
		Tessellator tessellator = Tessellator.instance;
		
        par1Block.setBlockBoundsForItemRender();
        renderer.setRenderBoundsFromBlock(par1Block);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(par1Block, 0, par2));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderTopFace(par1Block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(par1Block, 1, par2));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(par1Block, 2, par2));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(par1Block, 3, par2));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(par1Block, 4, par2));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(par1Block, 5, par2));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if(renderer.renderStandardBlock(block, x, y, z))
		{
			_invertedRenderer.renderStandardBlock(renderer, block, x, y, z);
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
		return 0;
	}
}
