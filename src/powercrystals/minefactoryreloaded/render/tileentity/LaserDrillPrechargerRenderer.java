package powercrystals.minefactoryreloaded.render.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityLaserDrillPrecharger;

public class LaserDrillPrechargerRenderer extends TileEntitySpecialRenderer
{
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks)
	{
		TileEntityLaserDrillPrecharger laserDrillPrecharger = (TileEntityLaserDrillPrecharger)tileEntity;
		if(laserDrillPrecharger.shouldDrawBeam())
		{
			this.bindTextureByName("/misc/beam.png");
			LaserRendererBase.renderLaser(laserDrillPrecharger, x, y, z, 1, laserDrillPrecharger.getDirectionFacing(), partialTicks);
		}
	}
}
