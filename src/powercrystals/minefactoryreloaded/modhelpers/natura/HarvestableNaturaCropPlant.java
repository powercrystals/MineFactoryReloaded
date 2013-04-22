package powercrystals.minefactoryreloaded.modhelpers.natura;

import java.util.Map;

import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStandard;

public class HarvestableNaturaCropPlant extends HarvestableStandard
{	
	public HarvestableNaturaCropPlant(int sourceId)
	{
		super(sourceId, HarvestType.Normal);
	}
	
	@Override
	public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z) % 4 >= 3 || world.getBlockMetadata(x, y, z) == 8;
	}
}
