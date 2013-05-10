package powercrystals.minefactoryreloaded.render.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.render.model.RedstoneCableModel;
import powercrystals.minefactoryreloaded.tile.TileRedstoneCable;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

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
		
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		_model.render(cable,0.0625f);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		bindTextureByName(MineFactoryReloadedCore.tileEntityFolder + "cable.png");
		
		GL11.glPushMatrix();
		GL11.glRotatef(90, 0, 1, 0);
		GL11.glTranslated(0.12, 0, 0);
		GL11.glScalef(1.28f, 1.28f, 1.28f);
		
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		_model.render(0.0625f);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
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