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

public class HarvestableCocoa extends HarvestableStandard
{
	public HarvestableCocoa()
	{
		super(Block.cocoaPlant.blockID, HarvestType.Normal);
	}
	
	@Override
	public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		int blockMetadata = world.getBlockMetadata(x, y, z);
		return ((blockMetadata & 12) >> 2) >= 2;
	}
	
	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		ArrayList<ItemStack> result = new ArrayList<ItemStack>();
		result.add(new ItemStack(Item.dyePowder, 3, 3));
		return result;
	}
}
