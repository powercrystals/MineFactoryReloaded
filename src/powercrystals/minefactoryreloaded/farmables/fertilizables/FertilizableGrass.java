package powercrystals.minefactoryreloaded.farmables.fertilizables;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableGrass implements IFactoryFertilizable
{
	@Override
	public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType)
	{
		return (fertilizerType == FertilizerType.GrowPlant || fertilizerType == FertilizerType.Grass) && world.getBlockId(x, y + 1, z) == 0;
	}
	
	@Override
	public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
	{
		for(int xOffset = -1; xOffset <= 1; xOffset++)
		{
			for(int zOffset = -1; zOffset <= 1; zOffset++)
			{
				if(rand.nextInt(6) != 0)
				{
					if(Block.tallGrass.canBlockStay(world, x + xOffset, y + 1, z + zOffset))
					{
						world.setBlock(x + xOffset, y + 1, z + zOffset, Block.tallGrass.blockID, 1, 2);
					}
				}
				else
				{
					ForgeHooks.plantGrass(world, x + xOffset, y + 1, z + zOffset);
				}
			}
		}
		
		return world.getBlockId(x, y + 1, z) != 0;
	}
	
	@Override
	public int getFertilizableBlockId()
	{
		return Block.grass.blockID;
	}
}
