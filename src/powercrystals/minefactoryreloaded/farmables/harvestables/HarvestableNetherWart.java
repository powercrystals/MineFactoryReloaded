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
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

public class HarvestableNetherWart implements IFactoryHarvestable
{
	@Override
	public int getPlantId()
	{
		return Block.netherStalk.blockID;
	}
	
	@Override
	public HarvestType getHarvestType()
	{
		return HarvestType.Normal;
	}
	
	@Override
	public boolean breakBlock()
	{
		return true;
	}
	
	@Override
	public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z) >= 3;
	}
	
	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		int numDrops = 1;
		if(world.getBlockMetadata(x, y, z) >= 3)
		{
			numDrops = 2 + world.rand.nextInt(3);
		}
		for(int k1 = 0; k1 < numDrops; k1++)
		{
			drops.add(new ItemStack(Item.netherStalkSeeds));
		}
		return drops;
	}
	
	@Override
	public void preHarvest(World world, int x, int y, int z)
	{
	}
	
	@Override
	public void postHarvest(World world, int x, int y, int z)
	{
	}
	
}
