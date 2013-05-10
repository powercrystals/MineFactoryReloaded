package powercrystals.minefactoryreloaded.farmables.fertilizables;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableSapling implements IFactoryFertilizable
{
	private int _blockId;
	
	public FertilizableSapling(int blockId)
	{
		_blockId = blockId;
	}
	
	@Override
	public int getFertilizableBlockId()
	{
		return _blockId;
	}
	
	@Override
	public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType)
	{
		return fertilizerType == FertilizerType.GrowPlant;
	}
	
	@Override
	public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
	{
		((BlockSapling)Block.blocksList[world.getBlockId(x, y, z)]).growTree(world, x, y, z, world.rand);
		return world.getBlockId(x, y, z) != _blockId;
	}
}
