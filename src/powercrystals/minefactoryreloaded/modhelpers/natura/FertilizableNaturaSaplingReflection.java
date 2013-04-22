package powercrystals.minefactoryreloaded.modhelpers.natura;

import java.lang.reflect.Method;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableNaturaSaplingReflection implements IFactoryFertilizable
{
	private Method _fertilize;
	private int _blockId;
	
	public FertilizableNaturaSaplingReflection(int blockId, Method fertilize)
	{
		_blockId = blockId;
		_fertilize = fertilize;
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
		try
		{
			_fertilize.invoke(Block.blocksList[_blockId], world, x, y, z, rand);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return world.getBlockId(x, y, z) != _blockId;
	}
}
