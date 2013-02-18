package powercrystals.minefactoryreloaded.modhelpers.ic2;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStandard;

public class HarvestableIC2RubberWood extends HarvestableStandard
{
	private int _resinId;
	
	public HarvestableIC2RubberWood(int sourceId, HarvestType harvestType, int resinId)
	{
		super(sourceId, harvestType);
		_resinId = resinId;
	}
	
	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		List<ItemStack> drops = super.getDrops(world, rand, harvesterSettings, x, y, z);
		int md = world.getBlockMetadata(x, y, z);
		if(md >= 2 && md <= 5)
		{
			drops.add(new ItemStack(_resinId, 1, 0));
		}
		
		return drops;
	}
}