package powercrystals.minefactoryreloaded.farmables.fertilizables;

import java.util.Random;

import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import powercrystals.minefactoryreloaded.block.BlockRubberSapling;

public class FertilizableRubberSapling implements IFactoryFertilizable
{
	@Override
	public int getFertilizableBlockId()
	{
		return MineFactoryReloadedCore.rubberSaplingBlock.blockID;
	}
	
	@Override
	public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType)
	{
		return fertilizerType == FertilizerType.GrowPlant;
	}
	
	@Override
	public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
	{
		((BlockRubberSapling)MineFactoryReloadedCore.rubberSaplingBlock).growTree(world, x, y, z, world.rand);
		return world.getBlockId(x, y, z) != MineFactoryReloadedCore.rubberSaplingBlock.blockID;
	}
}
