package powercrystals.minefactoryreloaded.modhelpers.thaumcraft;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableTreeLeaves;

public class HarvestableThaumcraftLeaves extends HarvestableTreeLeaves
{
	private int _plantId;
	
	public HarvestableThaumcraftLeaves(int id, int plantId)
	{
		super(id);
		_plantId = plantId;
	}
	
	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		if(harvesterSettings.get("silkTouch") != null && harvesterSettings.get("silkTouch"))
		{
			ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
			drops.add(new ItemStack(getPlantId(), 1, world.getBlockMetadata(x, y, z)));
			return drops;
		}
		else
		{
			int leafMeta = world.getBlockMetadata(x, y, z) & 0x01;
			List<ItemStack> drops = new LinkedList<ItemStack>();
			if(rand.nextInt(leafMeta == 0 ? 300 : 500) == 0)
			{
				drops.add(new ItemStack(_plantId, 1, leafMeta));
			}
			return drops;
		}
	}
}
