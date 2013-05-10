package powercrystals.minefactoryreloaded.modhelpers.extrabiomes;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableStandard;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MineFactoryReloaded|CompatExtraBiomes", name = "MFR Compat: ExtraBiomes", version = MineFactoryReloadedCore.version, dependencies = "after:MineFactoryReloaded;after:ExtrabiomesXL")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class ExtraBiomes
{
	private static Map<String, HarvestType> _harvestRegistries;
	
	@Init
	public static void load(FMLInitializationEvent ev)
	{
		if(!Loader.isModLoaded("ExtrabiomesXL"))
		{
			FMLLog.warning("ExtraBiomesXL missing - MFR ExtraBiomesXL Compat not loading");
			return;
		}
		
		_harvestRegistries = new HashMap<String, HarvestType>();
		_harvestRegistries.put("CATTAIL", HarvestType.Normal);
		_harvestRegistries.put("FLOWER", HarvestType.Normal);
		_harvestRegistries.put("GRASS", HarvestType.Normal);
		_harvestRegistries.put("LEAFPILE", HarvestType.Normal);
		_harvestRegistries.put("CUSTOMLOG", HarvestType.Tree);
		_harvestRegistries.put("QUARTERLOG0", HarvestType.Tree);
		_harvestRegistries.put("QUARTERLOG1", HarvestType.Tree);
		_harvestRegistries.put("QUARTERLOG2", HarvestType.Tree);
		_harvestRegistries.put("QUARTERLOG3", HarvestType.Tree);
		
		try
		{
			Class<?> xbbs = Class.forName("extrabiomes.lib.BlockSettings");
			
			for(String s : new String[] { "AUTUMNLEAVES", "GREENLEAVES" })
			{
				Object o = xbbs.getField(s).get(null);
				Integer blockID = (Integer)xbbs.getMethod("getID").invoke(o);
				MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(blockID));
			}
			
			for(Entry<String, HarvestType> e : _harvestRegistries.entrySet())
			{
				Object o = xbbs.getField(e.getKey()).get(null);
				Integer blockID = (Integer)xbbs.getMethod("getID").invoke(o);
				MFRRegistry.registerHarvestable(new HarvestableStandard(blockID, e.getValue()));
			}
			
			for(String s : new String[] { "SAPLING", "CATTAIL" })
			{
				Object o = xbbs.getField(s).get(null);
				Integer blockID = (Integer)xbbs.getMethod("getID").invoke(o);
				MFRRegistry.registerPlantable(new PlantableStandard(blockID, blockID));
			}
			
			
			Class<?> xbs = Class.forName("extrabiomes.blocks.BlockCustomSapling");
			Method fert = xbs.getMethod("growTree", World.class, int.class, int.class, int.class, Random.class);
			
			Object o = xbbs.getField("SAPLING").get(null);
			int saplingBlockID = (Integer)xbbs.getMethod("getID").invoke(o);
			
			MFRRegistry.registerFertilizable(new FertilizableExtraBiomesTree(saplingBlockID, fert));
			
			MFRRegistry.registerSludgeDrop(15, new ItemStack((Integer)xbbs.getMethod("getID").invoke(xbbs.getField("QUICKSAND").get(null)), 1, 0));
			MFRRegistry.registerSludgeDrop(15, new ItemStack((Integer)xbbs.getMethod("getID").invoke(xbbs.getField("CRACKEDSAND").get(null)), 1, 0));
			
			MFRRegistry.registerRubberTreeBiome("Autumn Woods");
			MFRRegistry.registerRubberTreeBiome("Birch Forest");
			MFRRegistry.registerRubberTreeBiome("Extreme Jungle");
			MFRRegistry.registerRubberTreeBiome("Forested Hills");
			MFRRegistry.registerRubberTreeBiome("Forested Island");
			MFRRegistry.registerRubberTreeBiome("Green Hills");
			MFRRegistry.registerRubberTreeBiome("Green Swamplands");
			MFRRegistry.registerRubberTreeBiome("Mini Jungle");
			MFRRegistry.registerRubberTreeBiome("Mountain Taiga");
			MFRRegistry.registerRubberTreeBiome("Pine Forest");
			MFRRegistry.registerRubberTreeBiome("Rainforest");
			MFRRegistry.registerRubberTreeBiome("Redwood Forest");
			MFRRegistry.registerRubberTreeBiome("Lush Redwoods");
			MFRRegistry.registerRubberTreeBiome("Snow Forest");
			MFRRegistry.registerRubberTreeBiome("Snowy Rainforest");
			MFRRegistry.registerRubberTreeBiome("Temperate Rainforest");
			MFRRegistry.registerRubberTreeBiome("Woodlands");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
