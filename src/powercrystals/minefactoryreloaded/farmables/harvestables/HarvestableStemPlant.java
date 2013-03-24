package powercrystals.minefactoryreloaded.farmables.harvestables;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.HarvestType;

public class HarvestableStemPlant extends HarvestableStandard
{
	public HarvestableStemPlant(int sourceId, HarvestType harvestType)
	{
		super(sourceId, harvestType);
	}
	
	@Override
	public void postHarvest(World world, int x, int y, int z)
	{
		int blockId = world.getBlockId(x, y, z);
		int groundId = world.getBlockId(x, y - 1, z);
		if(blockId == 0 && (groundId == Block.dirt.blockID || groundId == Block.grass.blockID))
		{
			world.setBlock(x, y - 1, z, Block.tilledField.blockID);
		}
	}
}
