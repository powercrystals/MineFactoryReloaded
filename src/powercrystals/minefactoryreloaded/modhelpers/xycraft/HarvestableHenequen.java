package powercrystals.minefactoryreloaded.modhelpers.xycraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

public class HarvestableHenequen implements IFactoryHarvestable
{
	private int _sourceId;
	
	public HarvestableHenequen(int sourceId)
	{
		_sourceId = sourceId;
	}
	
	@Override
	public int getPlantId()
	{
		return _sourceId;
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
		return world.getBlockId(x, y + 1, z) == _sourceId;
	}
	
	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		List<ItemStack> drops = new ArrayList<ItemStack>();
		drops.addAll(Block.blocksList[_sourceId].getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0));
		drops.addAll(Block.blocksList[_sourceId].getBlockDropped(world, x, y + 1, z, world.getBlockMetadata(x, y + 1, z), 0));
		return drops;
	}
	
	@Override
	public void preHarvest(World world, int x, int y, int z)
	{
		world.setBlockToAir(x, y + 1, z);
	}
	
	@Override
	public void postHarvest(World world, int x, int y, int z)
	{
	}
}