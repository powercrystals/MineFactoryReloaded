package powercrystals.minefactoryreloaded.modhelpers.biomesoplenty;

import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizableSapling;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableStandard;
import net.minecraft.block.Block;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MineFactoryReloaded|CompatBiomesOPlenty", name = "MFR Compat: Biomes O' Plenty", version = MineFactoryReloadedCore.version, dependencies = "after:MineFactoryReloaded;after:BiomesOPlenty")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class BiomesOPlenty
{

	@SuppressWarnings("rawtypes")
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
			// Biomes
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
			
			String[] bopLeaves = {"acacia", "apple", "autumn", "bamboo", "dark", "dead", "blue", "fir", "holy", "mangrove", "orange", "origin", "palm", "pink", "red", "redwood", "white", "willow"};
			String[] bopLogs = {"acacia", "bamboo", "cherry", "dark", "dead", "fir", "holy", "magic", "mangrove", "palm", "redwood", "willow"};
			String[] bopSaplings = {"acacia", "apple", "brown", "dark", "fir", "holy", "magic", "mangrove", "orange", "origin", "palm", "pink", "red", "redwood", "willow", "yellow"};
		
			Class BOPBlocks = Class.forName("tdwp_ftw.biomesop.declarations.BOPBlocks");
			if(BOPBlocks != null)
			{
				for(String leaves : bopLeaves)
				{
					MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(
							((Block)BOPBlocks.getField(leaves + "Leaves").get(null)).blockID
							));
				}
				MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(((Block)BOPBlocks.getField("appleLeavesFruitless").get(null)).blockID));
				
				for(String log : bopLogs)
				{
					MFRRegistry.registerHarvestable(new HarvestableStandard(((Block)BOPBlocks.getField(log + "Wood").get(null)).blockID, HarvestType.Tree));
				}
				
				for(String sapling : bopSaplings)
				{
					MFRRegistry.registerPlantable(new PlantableStandard(((Block)BOPBlocks.getField(sapling + "Sapling").get(null)).blockID, ((Block)BOPBlocks.getField(sapling + "Sapling").get(null)).blockID));
					MFRRegistry.registerFertilizable(new FertilizableSapling(((Block)BOPBlocks.getField(sapling + "Sapling").get(null)).blockID));
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
