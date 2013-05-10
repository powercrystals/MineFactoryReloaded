package powercrystals.minefactoryreloaded.farmables.fertilizables;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableCocoa implements IFactoryFertilizable
{
	@Override
	public int getFertilizableBlockId()
	{
		return Block.cocoaPlant.blockID;
	}
	
	@Override
	public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType)
	{
		return fertilizerType == FertilizerType.GrowPlant && world.getBlockMetadata(x, y, z) < 8;
	}
	
	@Override
	public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
	{
		world.setBlockMetadataWithNotify(x, y, z, 8 | BlockDirectional.getDirection(world.getBlockMetadata(x, y, z)), 2);
		return true;
	}
}
