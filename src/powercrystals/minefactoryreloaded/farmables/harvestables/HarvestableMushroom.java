package powercrystals.minefactoryreloaded.farmables.harvestables;

import java.util.Map;

import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.HarvestType;

public class HarvestableMushroom extends HarvestableStandard
{
	public HarvestableMushroom(int sourceId)
	{
		super(sourceId, HarvestType.Normal);
	}
	
	@Override
	public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		if(harvesterSettings.get("harvestSmallMushrooms") == null) return false;
		return harvesterSettings.get("harvestSmallMushrooms");
	}
}
