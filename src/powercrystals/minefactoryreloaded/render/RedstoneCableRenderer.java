package powercrystals.minefactoryreloaded.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.tile.TileRedstoneCable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class RedstoneCableRenderer extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
	private RedstoneCableModel _model;
	
	public RedstoneCableRenderer()
	{
		_model = new RedstoneCableModel();
	}	
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float scale)
	{
		TileRedstoneCable cable = (TileRedstoneCable)tileentity;
		bindTextureByName(MineFactoryReloadedCore.tileEntityFolder + "cable.png");
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5f, (float) z + 0.5F);
		
		_model.render(cable,0.0625f);

		GL11.glPopMatrix();
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		bindTextureByName(MineFactoryReloadedCore.tileEntityFolder + "cable.png");
		
		GL11.glPushMatrix();
		GL11.glTranslated(-0.5, -0.5 + -1.0 / 16.0, -0.5);
		_model.render(0.0625f);
		GL11.glPopMatrix();
		
		return;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
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
		return MineFactoryReloadedCore.renderIdRedstoneCable;
	}
}
