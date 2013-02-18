package powercrystals.minefactoryreloaded.farmables.harvestables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.HarvestType;

public class HarvestableTreeLeaves extends HarvestableStandard
{
	public HarvestableTreeLeaves(int id)
	{
		super(id, HarvestType.TreeLeaf);
	}

	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		if(harvesterSettings.get("silkTouch") != null && harvesterSettings.get("silkTouch"))
		{
			ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
			drops.add(new ItemStack(getSourceId(), 1, world.getBlockMetadata(x, y, z)));
			return drops;
		}
		else if(getSourceId() == Block.leaves.blockID && (world.getBlockMetadata(x, y, z) & 3) == 0)
		{
			ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
			if(rand.nextInt(20) == 0) drops.add(new ItemStack(Block.sapling));
			if(rand.nextInt(200) == 0) drops.add(new ItemStack(Item.appleRed));
			return drops;
		}
		else
		{
			return Block.blocksList[getSourceId()].getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		}
	}
}
