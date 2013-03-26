package powercrystals.minefactoryreloaded.setup;

import java.util.Random;

import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;


import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenLakes;
import cpw.mods.fml.common.IWorldGenerator;

public class MineFactoryReloadedWorldGen implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		int x = chunkX * 16 + random.nextInt(16);
		int z = chunkZ * 16 + random.nextInt(16);
		
		if(MineFactoryReloadedCore.rubberTreeWorldGen.getBoolean(true))
		{
			BiomeGenBase b = world.getBiomeGenForCoords(x, z);
			
			if(MFRRegistry.getRubberTreeBiomes().contains(b.biomeName))
			{
				if(random.nextInt(100) < 40)
				{
					new WorldGenRubberTree().generate(world, random, x, random.nextInt(3) + 4, z);
				}
			}
		}
		
		if(MineFactoryReloadedCore.mfrLakeWorldGen.getBoolean(true))
		{
			if(random.nextInt(8) == 0)
			{
				int lakeX = x - 8 + random.nextInt(16);
				int lakeY = random.nextInt(128);
				int lakeZ = z - 8 + random.nextInt(16);
				new WorldGenLakes(MineFactoryReloadedCore.sludgeStill.blockID).generate(world, random, lakeX, lakeY, lakeZ);
			}
			
			if(random.nextInt(8) == 0)
			{
				int lakeX = x - 8 + random.nextInt(16);
				int lakeY = random.nextInt(128);
				int lakeZ = z - 8 + random.nextInt(16);
				new WorldGenLakes(MineFactoryReloadedCore.sewageStill.blockID).generate(world, random, lakeX, lakeY, lakeZ);
			}
		}
	}
}
