package powercrystals.minefactoryreloaded.modhelpers.sufficientbiomes;

import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MFReloaded|CompatSufficientBiomes", name = "MFR Compat: Sufficient Biomes", version = MineFactoryReloadedCore.version, dependencies = "after:MFReloaded;after:EmasherWorldGen")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class SufficientBiomes
{

	@Init
	public static void load(FMLInitializationEvent ev)
	{
		if(!Loader.isModLoaded("EmasherWorldGen"))
		{
			FMLLog.warning("Sufficient Biomes missing - MFR Sufficient Biomes Compat not loading");
			return;
		}
		
		MFRRegistry.registerRubberTreeBiome("Hollow");
		MFRRegistry.registerRubberTreeBiome("Marsh");
		MFRRegistry.registerRubberTreeBiome("Foot Hills");
		MFRRegistry.registerRubberTreeBiome("Sand Forest");
		MFRRegistry.registerRubberTreeBiome("Prairie Forest");
		
	}
	
}