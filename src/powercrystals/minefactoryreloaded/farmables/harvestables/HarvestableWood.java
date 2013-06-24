package powercrystals.minefactoryreloaded.farmables.harvestables;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.HarvestType;

public class HarvestableWood extends HarvestableStandard
{
	public HarvestableWood()
	{
		super(Block.wood.blockID, HarvestType.Tree);
	}
	
	@Override
	public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		if(BlockLog.limitToValidMetadata(world.getBlockMetadata(x, y, z)) != 3)
		{
			return true;
		}
		
		if(harvesterSettings.get("harvestJungleWood") == null) return false;
		return harvesterSettings.get("harvestJungleWood");
	}
}
