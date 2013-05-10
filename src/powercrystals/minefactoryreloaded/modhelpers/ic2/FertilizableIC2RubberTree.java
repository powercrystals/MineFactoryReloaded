package powercrystals.minefactoryreloaded.modhelpers.ic2;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableIC2RubberTree implements IFactoryFertilizable
{
	private int _saplingId;
	
	public FertilizableIC2RubberTree(int blockId)
	{
		_saplingId = blockId;
	}
	
	@Override
	public int getFertilizableBlockId()
	{
		return _saplingId;
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
			((BlockSapling)Block.blocksList[_saplingId]).growTree(world, x, y, z, rand);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return world.getBlockId(x, y, z) != _saplingId;
	}
	
}