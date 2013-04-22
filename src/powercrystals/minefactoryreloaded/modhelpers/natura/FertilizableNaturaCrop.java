package powercrystals.minefactoryreloaded.modhelpers.natura;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableNaturaCrop implements IFactoryFertilizable
{
	private int _blockId;

	public FertilizableNaturaCrop(int blockId)
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
		return fertilizerType == FertilizerType.GrowPlant && world.getBlockMetadata(x, y, z) != 3 && world.getBlockMetadata(x, y, z) != 8;
	}

	@Override
	public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
	{
		((BlockCrops)Block.crops).fertilize(world, x, y, z);
		return true;
	}
}