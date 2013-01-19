package powercrystals.minefactoryreloaded.modhelpers.pam;

import net.minecraft.world.World;

public class HarvestablePamsPerennial extends HarvestablePams
{
	public HarvestablePamsPerennial(int sourceId)
	{
		super(sourceId);
	}

	@Override
	public void postHarvest(World world, int x, int y, int z)
	{
		world.setBlockAndMetadataWithNotify(x, y, z, getSourceId(), 0);
	}
}