package powercrystals.minefactoryreloaded.farmables.fertilizables;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableCropPlant implements IFactoryFertilizable
{
	private final int blockID;
	private int _targetMeta;
	
	public FertilizableCropPlant(int blockID, int targetMeta)
	{
		this.blockID = blockID;
		this._targetMeta = targetMeta;
	}
	
	@Override
	public int getFertilizableBlockId()
	{
		return blockID;
	}
	
	@Override
	public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType)
	{
		return fertilizerType == FertilizerType.GrowPlant && world.getBlockMetadata(x, y, z) < _targetMeta;
	}
	
	@Override
	public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
	{
		((BlockCrops)Block.crops).fertilize(world, x, y, z);
		return true;
	}
}
