package powercrystals.minefactoryreloaded.farmables.fertilizables;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableStemPlants implements IFactoryFertilizable
{
	private int fertilizableId;
	
	public FertilizableStemPlants(int fertilizableId)
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
		return fertilizerType == FertilizerType.GrowPlant && world.getBlockMetadata(x, y, z) < 7;
	}
	
	@Override
	public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
	{
		int blockId = world.getBlockId(x, y, z);
		((BlockStem)Block.blocksList[blockId]).fertilizeStem(world, x, y, z);
		return world.getBlockMetadata(x, y, z) == 7;
	}
	
}
