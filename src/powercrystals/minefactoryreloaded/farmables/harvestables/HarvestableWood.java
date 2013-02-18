package powercrystals.minefactoryreloaded.farmables.harvestables;

import java.util.Map;

import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.world.World;

public class HarvestableWood extends HarvestableStandard implements IFactoryHarvestable
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
