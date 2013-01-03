package powercrystals.minefactoryreloaded.farmables;

import java.util.Map;

import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

public class HarvestableCropPlant extends HarvestableStandard implements IFactoryHarvestable
{
	public HarvestableCropPlant(int blockID)
	{
		super(blockID, HarvestType.Normal);
	}
	
	@Override
	public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z) >= 7;
	}
}
