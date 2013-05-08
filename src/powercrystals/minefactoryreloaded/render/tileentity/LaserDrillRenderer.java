package powercrystals.minefactoryreloaded.render.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import powercrystals.minefactoryreloaded.tile.machine.TileEntityLaserDrill;

@SideOnly(Side.CLIENT)
public class LaserDrillRenderer extends TileEntitySpecialRenderer
{
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks)
	{
		TileEntityLaserDrill laserDrill = (TileEntityLaserDrill)tileEntity;
		if(laserDrill.shouldDrawBeam())
		{
			this.bindTextureByName("/misc/beam.png");
			LaserRendererBase.renderLaser(laserDrill, x, y, z, laserDrill.getBeamHeight(), ForgeDirection.DOWN, partialTicks);
		}
	}
}