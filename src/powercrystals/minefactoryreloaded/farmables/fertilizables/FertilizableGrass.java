package powercrystals.minefactoryreloaded.farmables.fertilizables;

import java.util.Random;

import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class FertilizableGrass implements IFactoryFertilizable
{
	@Override
	public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType)
	{
		return fertilizerType == FertilizerType.GrowPlant && world.getBlockId(x, y + 1, z) == 0;
	}

	@Override
	public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
	{
		if(rand.nextInt(6) != 0)
		{
			if(Block.tallGrass.canBlockStay(world, x, y + 1, z))
			{
				world.setBlock(x, y + 1, z, Block.tallGrass.blockID, 1, 2);
			}
		}
		else
		{
			ForgeHooks.plantGrass(world, x, y + 1, z);
		}

		return world.getBlockId(x, y + 1, z) != 0;
	}

	@Override
	public int getFertilizableBlockId()
	{
		return Block.grass.blockID;
	}
}
