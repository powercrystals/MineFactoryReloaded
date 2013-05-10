package powercrystals.minefactoryreloaded.modhelpers.biomesoplenty;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.MobDrop;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizableSapling;
import powercrystals.minefactoryreloaded.farmables.grindables.GrindableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableStandard;

import com.google.common.base.Optional;

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
	private static Class<?> _BOPBlocks;
	
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
			
			String[] bopLeaves = { "leaves1", "leaves2" };
			String[] bopLogs = { "logs1", "logs2", "logs3" };
			String[] bopSaplings = { "saplings", "colorizedSaplings" };
			
			String[] bopMiscTrunks = { "flowers", "bamboo"};
			String[] bopMiscLeaves = { "treeMoss" };
			String[] bopMiscStandardHarvestables = { "plants", "foliage" };
			
			_BOPBlocks = Class.forName("biomesoplenty.api.Blocks");
			if(_BOPBlocks != null)
			{
				for(String leaves : bopLeaves)
				{
					MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(getBOPBlock(leaves).blockID));
				}
				
				for(String log : bopLogs)
				{
					MFRRegistry.registerHarvestable(new HarvestableStandard(getBOPBlock(log).blockID, HarvestType.Tree));
				}
				
				for(String sapling : bopSaplings)
				{
					MFRRegistry.registerPlantable(new PlantableStandard(getBOPBlock(sapling).blockID, getBOPBlock(sapling).blockID));
					MFRRegistry.registerFertilizable(new FertilizableSapling(getBOPBlock(sapling).blockID));
				}
				
				for(String trunk : bopMiscTrunks)
				{
					MFRRegistry.registerHarvestable(new HarvestableStandard(getBOPBlock(trunk).blockID, HarvestType.Tree));
				}
				
				for(String leaves : bopMiscLeaves)
				{
					MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(getBOPBlock(leaves).blockID));
				}
				
				for(String harvestable : bopMiscStandardHarvestables)
				{
					MFRRegistry.registerHarvestable(new HarvestableStandard(getBOPBlock(harvestable).blockID, HarvestType.Normal));
				}
				
				MFRRegistry.registerSludgeDrop(25, new ItemStack(getBOPBlock("mud")));
				MFRRegistry.registerSludgeDrop(15, new ItemStack(getBOPBlock("ash")));
				MFRRegistry.registerSludgeDrop(15, new ItemStack(getBOPBlock("driedDirt")));
				MFRRegistry.registerSludgeDrop(15, new ItemStack(getBOPBlock("hardSand")));
				MFRRegistry.registerSludgeDrop(15, new ItemStack(getBOPBlock("hardDirt")));
				MFRRegistry.registerSludgeDrop(25, new ItemStack(getBOPBlock("mud"), 1, 1));
			}
			
			Class bopJungleSpider = Class.forName("biomesoplenty.mobs.EntityJungleSpider");
			Class bopRosester = Class.forName("biomesoplenty.mobs.EntityRosester");
			
			MFRRegistry.registerGrindable(new GrindableStandard(bopJungleSpider, new MobDrop[]
					{
					new MobDrop(3, new ItemStack(Item.silk)),
					new MobDrop(1, new ItemStack(Item.spiderEye))
					}));
			MFRRegistry.registerGrindable(new GrindableStandard(bopRosester, new MobDrop[]
					{
					new MobDrop(1, new ItemStack(Item.chickenRaw)),
					new MobDrop(1, new ItemStack(Item.dyePowder, 1, 1))
					}));
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static Block getBOPBlock(String field) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
	{
		return ((Block)((Optional<?>)_BOPBlocks.getField("mud").get(null)).get());
	}
}
