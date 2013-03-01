package powercrystals.minefactoryreloaded.world;

import java.util.Random;

import powercrystals.minefactoryreloaded.MFRRegistry;


import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class MineFactoryReloadedWorldGen implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		int x = chunkX * 16 + random.nextInt(16);
		int z = chunkZ * 16 + random.nextInt(16);
		BiomeGenBase b = world.getBiomeGenForCoords(x, z);
		
		System.out.println("Our biome: " + b.biomeName);
		System.out.println("Allowed biomes: ");
		for(String s : MFRRegistry.getRubberTreeBiomes())
		{
			System.out.print(s + ";");
		}
		System.out.println();
		
		if(MFRRegistry.getRubberTreeBiomes().contains(b.biomeName))
		{
			System.out.println("Biome matched");
			if(random.nextInt(100) < 40)
			{
				new WorldGenRubberTree().generate(world, random, x, random.nextInt(3) + 4, z);
			}
		}
	}
}
