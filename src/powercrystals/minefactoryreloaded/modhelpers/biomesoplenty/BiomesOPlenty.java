package powercrystals.minefactoryreloaded.modhelpers.biomesoplenty;

import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.MobDrop;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizableSapling;
import powercrystals.minefactoryreloaded.farmables.grindables.GrindableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableStandard;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
			String[] bopLogs = {"acacia", "cherry", "dark", "dead", "fir", "holy", "magic", "mangrove", "palm", "redwood", "willow"};
			String[] bopSaplings = {"acacia", "apple", "bamboo", "brown", "dark", "fir", "holy", "magic", "mangrove", "orange", "origin", "palm", "pink", "red", "redwood", "willow", "yellow"};
			
			String[] bopMiscTrunks = {"giantFlowerStem", "bamboo"};
			String[] bopMiscLeaves = {"appleLeavesFruitless", "treeMoss", "giantFlowerRed", "giantFlowerYellow"}; 
			String[] bopMiscStandardHarvestables = {"deadGrass", "desertGrass", "whiteFlower", "blueFlower", "purpleFlower", "orangeFlower", "tinyFlower", "glowFlower", "cattail", "willow", "thorn", "toadstool", "shortGrass", "bush", "originGrass", "barley", "tinyCactus", "deathbloom", "hydrangea", "violet", "mediumGrass", "duneGrass", "desertSprouts", "holyGrass", "holyTallGrass", "moss", "algae", "smolderingGrass"};   
			String[] bopLeaveBottom = {"highGrassBottom", "highGrassTop"};
		
			Class BOPBlocks = Class.forName("tdwp_ftw.biomesop.configuration.BOPBlocks");
			if(BOPBlocks != null)
			{
				for(String leaves : bopLeaves)
				{
					MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(
							((Block)BOPBlocks.getField(leaves + "Leaves").get(null)).blockID
							));
				}
				
				for(String log : bopLogs)
				{
					MFRRegistry.registerHarvestable(new HarvestableStandard(((Block)BOPBlocks.getField(log + "Wood").get(null)).blockID, HarvestType.Tree));
				}
				
				for(String sapling : bopSaplings)
				{
					MFRRegistry.registerPlantable(new PlantableStandard(((Block)BOPBlocks.getField(sapling + "Sapling").get(null)).blockID, ((Block)BOPBlocks.getField(sapling + "Sapling").get(null)).blockID));
					MFRRegistry.registerFertilizable(new FertilizableSapling(((Block)BOPBlocks.getField(sapling + "Sapling").get(null)).blockID));
				}
				
				for(String trunk : bopMiscTrunks)
				{
					MFRRegistry.registerHarvestable(new HarvestableStandard(((Block)BOPBlocks.getField(trunk).get(null)).blockID, HarvestType.Tree));
				}
				
				for(String leaves : bopMiscLeaves)
				{
					MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(((Block)BOPBlocks.getField(leaves).get(null)).blockID));
				}
				
				for(String harvestable : bopMiscStandardHarvestables)
				{
					MFRRegistry.registerHarvestable(new HarvestableStandard(((Block)BOPBlocks.getField(harvestable).get(null)).blockID, HarvestType.Normal));
				}
				
				for(String harvestable : bopLeaveBottom)
				{
					MFRRegistry.registerHarvestable(new HarvestableStandard(((Block)BOPBlocks.getField(harvestable).get(null)).blockID, HarvestType.LeaveBottom));
				}
				
				MFRRegistry.registerSludgeDrop(25, new ItemStack((Block)BOPBlocks.getField("mud").get(null)));
				MFRRegistry.registerSludgeDrop(15, new ItemStack((Block)BOPBlocks.getField("ash").get(null)));
				MFRRegistry.registerSludgeDrop(15, new ItemStack((Block)BOPBlocks.getField("driedDirt").get(null)));
				MFRRegistry.registerSludgeDrop(15, new ItemStack((Block)BOPBlocks.getField("hardSand").get(null)));
				MFRRegistry.registerSludgeDrop(15, new ItemStack((Block)BOPBlocks.getField("hardDirt").get(null)));
				MFRRegistry.registerSludgeDrop(15, new ItemStack((Block)BOPBlocks.getField("quicksand").get(null)));
			}
			
			Class bopJungleSpider = Class.forName("tdwp_ftw.biomesop.mobs.EntityJungleSpider");
			Class bopRosester = Class.forName("tdwp_ftw.biomesop.mobs.EntityRosester");
			
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
}
