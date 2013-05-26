package powercrystals.minefactoryreloaded.setup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import cpw.mods.fml.common.IWorldGenerator;

public class MineFactoryReloadedWorldGen implements IWorldGenerator
{
	private static List<Integer> _blacklistedDimensions;
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		if(_blacklistedDimensions == null)
		{
			_blacklistedDimensions = buildBlacklistedDimensions();
		}
		
		if(_blacklistedDimensions.contains(world.provider.dimensionId))
		{
			return;
		}
		
		int x = chunkX * 16 + random.nextInt(16);
		int z = chunkZ * 16 + random.nextInt(16);
		
		BiomeGenBase b = world.getBiomeGenForCoords(x, z);
		
		if(MFRConfig.rubberTreeWorldGen.getBoolean(true))
		{
			if(MFRRegistry.getRubberTreeBiomes().contains(b.biomeName))
			{
				if(random.nextInt(100) < 40)
				{
					new WorldGenRubberTree().generate(world, random, x, random.nextInt(3) + 4, z);
				}
			}
		}
		
		if(MFRConfig.mfrLakeWorldGen.getBoolean(true) && world.provider.canRespawnHere())
		{
			if(random.nextInt(MFRConfig.mfrLakeSludgeRarity.getInt()) == 0)
			{
				int lakeX = x - 8 + random.nextInt(16);
				int lakeY = random.nextInt(128);
				int lakeZ = z - 8 + random.nextInt(16);
				new WorldGenLakesMeta(MineFactoryReloadedCore.sludgeLiquid.blockID, 7).generate(world, random, lakeX, lakeY, lakeZ);
			}
			
			if(random.nextInt(MFRConfig.mfrLakeSewageRarity.getInt()) == 0)
			{
				int lakeX = x - 8 + random.nextInt(16);
				int lakeY = random.nextInt(128);
				int lakeZ = z - 8 + random.nextInt(16);
				if(b.biomeName.toLowerCase().contains("mushroom"))
				{
					new WorldGenLakesMeta(MineFactoryReloadedCore.mushroomSoupLiquid.blockID, 7).generate(world, random, lakeX, lakeY, lakeZ);
				}
				else
				{
					new WorldGenLakesMeta(MineFactoryReloadedCore.sewageLiquid.blockID, 7).generate(world, random, lakeX, lakeY, lakeZ);
				}
			}
		}
	}
	
	private static List<Integer> buildBlacklistedDimensions()
	{
		String blacklist = MFRConfig.worldGenDimensionBlacklist.getString();
		List<Integer> dims = new ArrayList<Integer>();
		
		if(blacklist == null)
		{
			return dims;
		}
		blacklist = blacklist.trim();
		
		for(String dim : blacklist.split(","))
		{
			try
			{
				Integer dimId = Integer.parseInt(dim);
				dims.add(dimId);
			}
			catch(Exception x)
			{
			}
		}
		
		return dims;
	}
}
