package powercrystals.minefactoryreloaded.farmables.fertilizables;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockMushroom;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableGiantMushroom implements IFactoryFertilizable
{
	private int fertilizableId;
	
	public FertilizableGiantMushroom(int fertilizableId)
	{
		this.fertilizableId = fertilizableId;
	}
	
	@Override
	public int getFertilizableBlockId()
	{
		return fertilizableId;
	}
	
	@Override
	public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType)
	{
		return fertilizerType == FertilizerType.GrowPlant;
	}
	
	@Override
	public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
	{
		int blockId = world.getBlockId(x, y, z);
		return ((BlockMushroom)Block.blocksList[blockId]).fertilizeMushroom(world, x, y, z, world.rand);
	}
}
