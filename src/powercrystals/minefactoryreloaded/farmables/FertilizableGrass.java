package powercrystals.minefactoryreloaded.farmables;

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
		return fertilizerType == FertilizerType.GrowPlant;
	}

	@Override
	public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
	{
		if (!world.isRemote)
		{
			label133:

			for (int var12 = 0; var12 < 128; ++var12)
			{
				int xTemp = x;
				int yTemp = y + 1;
				int zTemp = z;

				for (int var16 = 0; var16 < var12 / 16; ++var16)
				{
					xTemp += rand.nextInt(3) - 1;
					yTemp += (rand.nextInt(3) - 1) * rand.nextInt(3) / 2;
					zTemp += rand.nextInt(3) - 1;

					if (world.getBlockId(xTemp, yTemp - 1, zTemp) != Block.grass.blockID || world.isBlockNormalCube(xTemp, yTemp, zTemp))
					{
						continue label133;
					}
				}

				if (world.getBlockId(xTemp, yTemp, zTemp) == 0)
				{
					if (rand.nextInt(10) != 0)
					{
						if (Block.tallGrass.canBlockStay(world, xTemp, yTemp, zTemp))
						{
							world.setBlockAndMetadataWithNotify(xTemp, yTemp, zTemp, Block.tallGrass.blockID, 1);
						}
					}
					else
					{
						ForgeHooks.plantGrass(world, xTemp, yTemp, zTemp);
					}
				}
			}
		}

		return true;
	}

	@Override
	public int getFertilizableBlockId()
	{
		return Block.grass.blockID;
	}
}
