package powercrystals.minefactoryreloaded;

import java.util.Random;

import powercrystals.minefactoryreloaded.plants.WorldGenRubberTree;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class MineFactoryReloadedWorldGen implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		String[] allowedBiomes = {"Swamplands", "Forest", "Taiga", "TaigaHills", "Jungle", "JungleHills"};
		BiomeGenBase b = world.getBiomeGenForCoords(chunkX, chunkZ);
		for (String s : allowedBiomes)
		{
			if(b.biomeName.equals(s))
			{
				if(random.nextInt(100) < 40)
				{
					new WorldGenRubberTree().generate(world, random, chunkX * 16 + random.nextInt(16), random.nextInt(3) + 4, chunkZ * 16 + random.nextInt(16));
				}
			}
		}
	}
}
