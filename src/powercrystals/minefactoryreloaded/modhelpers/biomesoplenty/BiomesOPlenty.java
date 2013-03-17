package powercrystals.minefactoryreloaded.modhelpers.biomesoplenty;

import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MFReloaded|CompatBiomesOPlenty", name = "MFR Compat: Biomes O' Plenty", version = MineFactoryReloadedCore.version, dependencies = "after:MFReloaded;after:BiomesOPlenty")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class BiomesOPlenty
{

	@Init
	public static void load(FMLInitializationEvent ev)
	{
		if(!Loader.isModLoaded("BiomesOPlenty"))
		{
			FMLLog.warning("Biomes O' Plenty missing - MFR Biomes O' Plenty Compat not loading");
			return;
		}
		try
		{
			MFRRegistry.registerRubberTreeBiome("Bayou");
			MFRRegistry.registerRubberTreeBiome("Birch Forest");
			MFRRegistry.registerRubberTreeBiome("Bog");
			MFRRegistry.registerRubberTreeBiome("Boreal Forest");
			MFRRegistry.registerRubberTreeBiome("Deciduous Forest");
			MFRRegistry.registerRubberTreeBiome("Forest");
			MFRRegistry.registerRubberTreeBiome("Forest New");
			MFRRegistry.registerRubberTreeBiome("ForestHills");
			MFRRegistry.registerRubberTreeBiome("Grove");
			MFRRegistry.registerRubberTreeBiome("Highland");
			MFRRegistry.registerRubberTreeBiome("Jungle");
			MFRRegistry.registerRubberTreeBiome("JungleHills");
			MFRRegistry.registerRubberTreeBiome("Lush Swamp");
			MFRRegistry.registerRubberTreeBiome("Maple Woods");
			MFRRegistry.registerRubberTreeBiome("Marsh");
			MFRRegistry.registerRubberTreeBiome("Moor");
			MFRRegistry.registerRubberTreeBiome("Rainforest");
			MFRRegistry.registerRubberTreeBiome("Seasonal Forest");
			MFRRegistry.registerRubberTreeBiome("Shield");
			MFRRegistry.registerRubberTreeBiome("Swampland");
			MFRRegistry.registerRubberTreeBiome("Swampwoods");
			MFRRegistry.registerRubberTreeBiome("Temperate Rainforest");
			MFRRegistry.registerRubberTreeBiome("Thicket");
			MFRRegistry.registerRubberTreeBiome("Tropical Rainforest");
			MFRRegistry.registerRubberTreeBiome("Woodland");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
