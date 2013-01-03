package powercrystals.minefactoryreloaded.farmables;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableCropPlant implements IFactoryFertilizable
{
	private final int blockID;
	
	public FertilizableCropPlant(int blockID)
	{
		this.blockID = blockID;
	}
	
	@Override
	public int getFertilizableBlockId()
	{
		return blockID;
	}

	@Override
	public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType)
	{
		return fertilizerType == FertilizerType.GrowPlant && world.getBlockMetadata(x, y, z) < 7;
	}

	@Override
	public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
	{
		((BlockCrops)Block.crops).fertilize(world, x, y, z);
		return true;
	}
}
