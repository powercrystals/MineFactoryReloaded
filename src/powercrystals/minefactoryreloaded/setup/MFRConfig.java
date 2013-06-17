package powercrystals.minefactoryreloaded.setup;

import java.io.File;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class MFRConfig
{
	// client config
	public static Property spyglassRange;
	
	// common config
	public static Property machineBlock0Id;
	public static Property machineBlock1Id;
	public static Property machineBlock2Id;
	
	public static Property conveyorBlockId;
	
	public static Property factoryGlassBlockId;
	public static Property factoryGlassPaneBlockId;
	public static Property factoryRoadBlockId;
	public static Property factoryDecorativeBrickBlockId;
	public static Property factoryDecorativeStoneBlockId;
	
	public static Property rubberWoodBlockId;
	public static Property rubberLeavesBlockId;
	public static Property rubberSaplingBlockId;
	
	public static Property railPickupCargoBlockId;
	public static Property railDropoffCargoBlockId;
	public static Property railPickupPassengerBlockId;
	public static Property railDropoffPassengerBlockId;
	
	public static Property rednetCableBlockId;
	public static Property rednetLogicBlockId;
	public static Property rednetPanelBlockId;
	
	public static Property fakeLaserBlockId;
	
	public static Property vineScaffoldBlockId;
	
	public static Property milkStillBlockId;
	public static Property sludgeStillBlockId;
	public static Property sewageStillBlockId;
	public static Property essenceStillBlockId;
	public static Property biofuelStillBlockId;
	public static Property meatStillBlockId;
	public static Property pinkslimeStillBlockId;
	public static Property chocolateMilkStillBlockId;
	public static Property mushroomSoupStillBlockId;
	
	public static Property hammerItemId;
	public static Property milkItemId;
	public static Property sludgeItemId;
	public static Property sewageItemId;
	public static Property mobEssenceItemId;
	public static Property fertilizerItemId;
	public static Property plasticSheetItemId;
	public static Property rawPlasticItemId;
	public static Property rubberBarItemId;
	public static Property sewageBucketItemId;
	public static Property sludgeBucketItemId;
	public static Property mobEssenceBucketItemId;
	public static Property syringeEmptyItemId;
	public static Property syringeHealthItemId;
	public static Property syringeGrowthItemId;
	public static Property rawRubberItemId;
	public static Property machineBaseItemId;
	public static Property safariNetItemId;
	public static Property ceramicDyeItemId;
	public static Property blankRecordId;
	public static Property syringeZombieId;
	public static Property safariNetSingleItemId;
	public static Property bioFuelItemId;
	public static Property bioFuelBucketItemId;
	public static Property upgradeItemId;
	public static Property safariNetLauncherItemId;
	public static Property sugarCharcoalItemId;
	public static Property milkBottleItemId;
	public static Property spyglassItemId;
	public static Property portaSpawnerItemId;
	public static Property strawItemId;
	public static Property xpExtractorItemId;
	public static Property syringeSlimeItemId;
	public static Property syringeCureItemId;
	public static Property logicCardItemId;
	public static Property rednetMeterItemId;
	public static Property rednetMemoryCardItemId;
	public static Property rulerItemId;
	public static Property meatIngotRawItemId;
	public static Property meatIngotCookedItemId;
	public static Property meatNuggetRawItemId;
	public static Property meatNuggetCookedItemId;
	public static Property meatBucketItemId;
	public static Property pinkSlimeBucketItemId;
	public static Property pinkSlimeballItemId;
	public static Property safariNetJailerItemId;
	public static Property laserFocusItemId;
	public static Property chocolateMilkBucketItemId;
	public static Property mushroomSoupBucketItemId;
	public static Property needlegunItemId;
	public static Property needlegunAmmoEmptyItemId;
	public static Property needlegunAmmoStandardItemId;
	public static Property needlegunAmmoLavaItemId;
	public static Property needlegunAmmoSludgeItemId;
	public static Property needlegunAmmoSewageItemId;
	public static Property needlegunAmmoFireItemId;
	public static Property needlegunAmmoAnvilItemId;
	
	public static Property plasticCupItemId;
	
	public static Property zoolologistEntityId;
	
	public static Property colorblindMode;
	public static Property treeSearchMaxVertical;
	public static Property treeSearchMaxHorizontal;
	public static Property verticalHarvestSearchMaxVertical;
	public static Property enableBonemealFertilizing;
	public static Property enableCheapDSU;
	public static Property craftSingleDSU;
	public static Property enableMossyCobbleRecipe;
	public static Property conveyorCaptureNonItems;
	public static Property conveyorNeverCapturesPlayers;
	public static Property conveyorNeverCapturesTCGolems;
	public static Property playSounds;
	public static Property fruitTreeSearchMaxVertical;
	public static Property fruitTreeSearchMaxHorizontal;
	public static Property breederShutdownThreshold;
	public static Property autospawnerCostStandard;
	public static Property autospawnerCostExact;
	public static Property laserdrillCost;
	public static Property meatSaturation;
	
	public static Property vanillaOverrideGlassPane;
	public static Property vanillaOverrideIce;
	public static Property vanillaOverrideMilkBucket;
	
	public static Property enableCompatibleAutoEnchanter;
	public static Property enableSlipperyRoads;
	
	public static Property redNetConnectionBlacklist;
	
	public static Property redNetDebug;
	
	public static Property rubberTreeWorldGen;
	
	public static Property mfrLakeWorldGen;
	public static Property mfrLakeSewageRarity;
	public static Property mfrLakeSludgeRarity;
	public static Property rubberTreeBiomeWhitelist;
	public static Property rubberTreeBiomeBlacklist;
	public static Property worldGenDimensionBlacklist;
	
	public static Property passengerRailSearchMaxHorizontal;
	public static Property passengerRailSearchMaxVertical;
	
	// recipes config
	public static Property vanillaRecipes;
	public static Property thermalExpansionRecipes;
	public static Property gregTechRecipes;
	
	public static void loadClientConfig(File configFile)
	{
		Configuration c = new Configuration(configFile);
		
		spyglassRange = c.get(Configuration.CATEGORY_GENERAL, "SpyglassRange", 200);
		spyglassRange.comment = "The maximum number of blocks the spyglass and ruler can look to find something. This calculation is performed only on the client side.";
		
		c.save();
	}
	
	public static void loadCommonConfig(File configFile)
	{
		Configuration c = new Configuration(configFile);
		c.load();
		machineBlock0Id = c.getBlock("ID.MachineBlock", 3120);
		conveyorBlockId = c.getBlock("ID.ConveyorBlock", 3121);
		rubberWoodBlockId = c.getBlock("ID.RubberWood", 3122);
		rubberLeavesBlockId = c.getBlock("ID.RubberLeaves", 3123);
		rubberSaplingBlockId = c.getBlock("ID.RubberSapling", 3124);
		railDropoffCargoBlockId = c.getBlock("ID.CargoRailDropoffBlock", 3125);
		railPickupCargoBlockId = c.getBlock("ID.CargoRailPickupBlock", 3126);
		railDropoffPassengerBlockId = c.getBlock("ID.PassengerRailDropoffBlock", 3127);
		railPickupPassengerBlockId = c.getBlock("ID.PassengerRailPickupBlock", 3128);
		factoryGlassBlockId = c.getBlock("ID.StainedGlass", 3129);
		factoryGlassPaneBlockId = c.getBlock("ID.StainedGlassPane", 3130);
		machineBlock1Id = c.getBlock("ID.MachineBlock1", 3131);
		factoryRoadBlockId = c.getBlock("ID.Road", 3132);
		factoryDecorativeBrickBlockId = c.getBlock("ID.Bricks", 3133);
		factoryDecorativeStoneBlockId = c.getBlock("ID.Stone", 3134);
		milkStillBlockId = c.getBlock("ID.Milk.Still", 3135);
		meatStillBlockId = c.getBlock("ID.Meat.Still", 3136);
		sludgeStillBlockId = c.getBlock("ID.Sludge.Still", 3137);
		pinkslimeStillBlockId = c.getBlock("ID.PinkSlime.Still", 3138);
		sewageStillBlockId = c.getBlock("ID.Sewage.Still", 3139);
		chocolateMilkStillBlockId = c.getBlock("ID.ChocolateMilk.Still", 3140);
		essenceStillBlockId = c.getBlock("ID.MobEssence.Still", 3141);
		mushroomSoupStillBlockId = c.getBlock("ID.MushroomSoup.Still", 3142);
		biofuelStillBlockId = c.getBlock("ID.BioFuel.Still", 3143);
		rednetCableBlockId = c.getBlock("ID.RedNet.Cable", 3144);
		rednetLogicBlockId = c.getBlock("ID.RedNet.Logic", 3145);
		machineBlock2Id = c.getBlock("ID.MachineBlock2", 3146);
		fakeLaserBlockId = c.getBlock("ID.FakeLaser", 3147);
		vineScaffoldBlockId = c.getBlock("ID.VineScaffold", 3148);
		rednetPanelBlockId = c.getBlock("ID.RedNet.Panel", 3149);
		
		hammerItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Hammer", 11987);
		milkItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Milk", 11988);
		sludgeItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Sludge", 11989);
		sewageItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Sewage", 11990);
		mobEssenceItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.MobEssence", 11991);
		fertilizerItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.FertilizerItem", 11992);
		plasticSheetItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.PlasticSheet", 11993);
		rawPlasticItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.RawPlastic", 11994);
		rubberBarItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.RubberBar", 11995);
		sewageBucketItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SewageBucket", 11996);
		sludgeBucketItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SludgeBucket", 11997);
		mobEssenceBucketItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.MobEssenceBucket", 11998);
		syringeEmptyItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SyringeEmpty", 11999);
		syringeHealthItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SyringeHealth", 12000);
		syringeGrowthItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SyringeGrowth", 12001);
		rawRubberItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.RawRubber", 12002);
		machineBaseItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.MachineBlock", 12003);
		safariNetItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SafariNet", 12004);
		ceramicDyeItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.CeramicDye", 12005);
		blankRecordId = c.getItem(Configuration.CATEGORY_ITEM, "ID.BlankRecord", 12006);
		syringeZombieId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SyringeZombie", 12007);
		safariNetSingleItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SafariNetSingleUse", 12008);
		bioFuelItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.BioFuel", 12009);
		bioFuelBucketItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.BioFuelBucket", 12010);
		upgradeItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Upgrade", 12011);
		safariNetLauncherItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SafariNetLauncher", 12012);
		sugarCharcoalItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SugarCharcoal", 12013);
		milkBottleItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.MilkBottle", 12014);
		spyglassItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Spyglass", 12015);
		portaSpawnerItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.PortaSpawner", 12016);
		strawItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Straw", 12017);
		xpExtractorItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.XPExtractor", 12018);
		syringeSlimeItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SyringeSlime", 12019);
		syringeCureItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SyringeCure", 12020);
		logicCardItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Upgrade.PRC", 12021);
		rednetMeterItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.RedNet.Meter", 12022);
		rednetMemoryCardItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.RedNet.MemoryCard", 12023);
		rulerItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Ruler", 12024);
		meatIngotRawItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.MeatIngotRaw", 12025);
		meatIngotCookedItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.MeatIngotCooked", 12026);
		meatNuggetRawItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.MeatNuggetRaw", 12027);
		meatNuggetCookedItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.MeatNuggetCooked", 12028);
		meatBucketItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.MeatBucket", 12029);
		pinkSlimeBucketItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.PinkSlimeBucket", 12030);
		pinkSlimeballItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.PinkSlimeball", 12031);
		safariNetJailerItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SafariNetJailer", 12032);
		laserFocusItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.LaserFocus", 12033);
		chocolateMilkBucketItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.ChocolateMilkBucket", 12034);
		mushroomSoupBucketItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.MushroomSoupBucket", 12035);
		needlegunItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.NeedleGun", 12036);
		needlegunAmmoEmptyItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.NeedleGun.Ammo.Empty", 12037);
		needlegunAmmoStandardItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.NeedleGun.Ammo.Standard", 12038);
		needlegunAmmoLavaItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.NeedleGun.Ammo.Lava", 12039);
		needlegunAmmoSludgeItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.NeedleGun.Ammo.Sludge", 12040);
		needlegunAmmoSewageItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.NeedleGun.Ammo.Sewage", 12041);
		needlegunAmmoFireItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.NeedleGun.Ammo.Fire", 12042);
		needlegunAmmoAnvilItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.NeedleGun.Ammo.Anvil", 12043);
		plasticCupItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.PlasticCup", 12044);
		
		zoolologistEntityId = c.get("Entity", "ID.Zoologist", 330);
		
		colorblindMode = c.get(Configuration.CATEGORY_GENERAL, "RedNet.EnableColorblindMode", false);
		colorblindMode.comment = "Set to true to enable the RedNet GUI's colorblind mode.";
		treeSearchMaxHorizontal = c.get(Configuration.CATEGORY_GENERAL, "SearchDistance.TreeMaxHoriztonal", 8);
		treeSearchMaxHorizontal.comment = "When searching for parts of a tree, how far out to the sides (radius) to search";
		treeSearchMaxVertical = c.get(Configuration.CATEGORY_GENERAL, "SearchDistance.TreeMaxVertical", 40);
		treeSearchMaxVertical.comment = "When searching for parts of a tree, how far up to search";
		verticalHarvestSearchMaxVertical = c.get(Configuration.CATEGORY_GENERAL, "SearchDistance.StackingBlockMaxVertical", 3);
		verticalHarvestSearchMaxVertical.comment = "How far upward to search for members of \"stacking\" blocks, like cactus and sugarcane";
		passengerRailSearchMaxVertical = c.get(Configuration.CATEGORY_GENERAL, "SearchDistance.PassengerRailMaxVertical", 2);
		passengerRailSearchMaxVertical.comment = "When searching for players or dropoff locations, how far up to search";
		passengerRailSearchMaxHorizontal = c.get(Configuration.CATEGORY_GENERAL, "SearchDistance.PassengerRailMaxHorizontal", 3);
		passengerRailSearchMaxHorizontal.comment = "When searching for players or dropoff locations, how far out to the sides (radius) to search";
		rubberTreeWorldGen = c.get(Configuration.CATEGORY_GENERAL, "WorldGen.RubberTree", true);
		rubberTreeWorldGen.comment = "Whether or not to generate rubber trees during map generation";
		mfrLakeWorldGen = c.get(Configuration.CATEGORY_GENERAL, "WorldGen.MFRLakes", true);
		mfrLakeWorldGen.comment = "Whether or not to generate MFR lakes during map generation";
		enableBonemealFertilizing = c.get(Configuration.CATEGORY_GENERAL, "Fertilizer.EnableBonemeal", false);
		enableBonemealFertilizing.comment = "If true, the fertilizer will use bonemeal as well as MFR fertilizer. Provided for those who want a less work-intensive farm.";
		enableCheapDSU = c.get(Configuration.CATEGORY_GENERAL, "DSU.EnableCheaperRecipe", false);
		enableCheapDSU.comment = "If true, DSU can be built out of chests instead of ender pearls. Does nothing if the DSU recipe is disabled.";
		craftSingleDSU = c.get(Configuration.CATEGORY_GENERAL, "DSU.CraftSingle", false);
		craftSingleDSU.comment = "DSU recipes will always craft one DSU. Does nothing for recipes that already only craft one DSU (cheap mode, GT recipes, etc).";
		enableMossyCobbleRecipe = c.get(Configuration.CATEGORY_GENERAL, "EnableMossyCobbleRecipe", true);
		enableMossyCobbleRecipe.comment = "If true, mossy cobble can be crafted.";
		conveyorCaptureNonItems = c.get(Configuration.CATEGORY_GENERAL, "Conveyor.CaptureNonItems", true);
		conveyorCaptureNonItems.comment = "If false, conveyors will not grab non-item entities. Breaks conveyor mob grinders but makes them safe for golems, etc.";
		conveyorNeverCapturesPlayers = c.get(Configuration.CATEGORY_GENERAL, "Conveyor.NeverCapturePlayers", false);
		conveyorNeverCapturesPlayers.comment = "If true, conveyors will NEVER capture players regardless of other settings.";
		conveyorNeverCapturesTCGolems = c.get(Configuration.CATEGORY_GENERAL, "Conveyor.NeverCaptureTCGolems", false);
		conveyorNeverCapturesTCGolems.comment = "If true, conveyors will NEVER capture Thaumcraft golems regardless of other settings.";
		playSounds = c.get(Configuration.CATEGORY_GENERAL, "PlaySounds", true);
		playSounds.comment = "Set to false to disable the harvester's sound when a block is harvested.";
		enableSlipperyRoads = c.get(Configuration.CATEGORY_GENERAL, "Road.Slippery", true);
		enableSlipperyRoads.comment = "If true, roads will be slippery like ice.";
		fruitTreeSearchMaxHorizontal = c.get(Configuration.CATEGORY_GENERAL, "SearchDistance.FruitTreeMaxHoriztonal", 5);
		fruitTreeSearchMaxHorizontal.comment = "When searching for parts of a fruit tree, how far out to the sides (radius) to search";
		fruitTreeSearchMaxVertical = c.get(Configuration.CATEGORY_GENERAL, "SearchDistance.FruitTreeMaxVertical", 20);
		fruitTreeSearchMaxVertical.comment = "When searching for parts of a fruit tree, how far up to search";
		breederShutdownThreshold = c.get(Configuration.CATEGORY_GENERAL, "Breeder.ShutdownThreshold", 50);
		breederShutdownThreshold.comment = "If the number of entities in the breeder's target area exceeds this value, the breeder will cease operating. This is provided to control server lag.";
		autospawnerCostExact = c.get(Configuration.CATEGORY_GENERAL, "AutoSpawner.Cost.Exact", 50);
		autospawnerCostExact.comment = "The work required to generate a mob in exact mode.";
		autospawnerCostStandard = c.get(Configuration.CATEGORY_GENERAL, "AutoSpawner.Cost.Standard", 15);
		autospawnerCostStandard.comment = "The work required to generate a mob in standard (non-exact) mode.";
		laserdrillCost = c.get(Configuration.CATEGORY_GENERAL, "LaserDrill.Cost", 300);
		laserdrillCost.comment = "The work required by the drill to generate a single ore.";
		meatSaturation = c.get(Configuration.CATEGORY_GENERAL, "Meat.IncreasedSaturation", false);
		meatSaturation.comment = "If true, meat will be worth steak saturation instead of cookie saturation.";
		
		vanillaOverrideGlassPane = c.get(Configuration.CATEGORY_GENERAL, "VanillaOverride.GlassPanes", true);
		vanillaOverrideGlassPane.comment = "If true, allows vanilla glass panes to connect to MFR stained glass panes.";
		vanillaOverrideIce = c.get(Configuration.CATEGORY_GENERAL, "VanillaOverride.Ice", true);
		vanillaOverrideIce.comment = "If true, enables MFR unmelting ice as well as vanilla ice.";
		vanillaOverrideMilkBucket = c.get(Configuration.CATEGORY_GENERAL, "VanillaOverride.MilkBucket", true);
		vanillaOverrideMilkBucket.comment = "If true, replaces the vanilla milk bucket so milk can be placed in the world.";
		
		enableCompatibleAutoEnchanter = c.get(Configuration.CATEGORY_GENERAL, "AutoEnchanter.EnableSafeMode", false);
		enableCompatibleAutoEnchanter.comment = "This was provided to workaround a BuildCraft issue in 1.4 and no longer has any effect.";
		
		redNetDebug = c.get(Configuration.CATEGORY_GENERAL, "RedNet.Debug", false);
		redNetDebug.comment = "If true, RedNet cables will dump a massive amount of data to the log file. You should probably only use this if PC tells you to.";
		
		rubberTreeBiomeWhitelist = c.get(Configuration.CATEGORY_GENERAL, "WorldGen.RubberTreeBiomeWhitelist", "");
		rubberTreeBiomeWhitelist.comment = "A comma-separated list of biomes to allow rubber trees to spawn in. Does nothing if rubber tree worldgen is disabled.";
		rubberTreeBiomeBlacklist = c.get(Configuration.CATEGORY_GENERAL, "WorldGen.RubberTreeBiomeBlacklist", "");
		rubberTreeBiomeBlacklist.comment = "A comma-separated list of biomes to disallow rubber trees to spawn in. Overrides any other biomes added.";
		redNetConnectionBlacklist = c.get(Configuration.CATEGORY_GENERAL, "RedNet.ConnectionBlackList", "");
		redNetConnectionBlacklist.comment = "A comma-separated list of block IDs to prevent RedNet cables from connecting to.";
		worldGenDimensionBlacklist = c.get(Configuration.CATEGORY_GENERAL, "WorldGen.DimensionBlacklist", "");
		worldGenDimensionBlacklist.comment = "A comma-separated list of dimension IDs to disable MFR worldgen in. By default, MFR will not attempt worldgen in dimensions where the player cannot respawn.";
		mfrLakeSludgeRarity = c.get(Configuration.CATEGORY_GENERAL, "WorldGen.LakeRarity.Sludge", 32);
		mfrLakeSludgeRarity.comment = "Higher numbers make sludge lakes rarer. A value of one will be approximately one per chunk.";
		mfrLakeSewageRarity = c.get(Configuration.CATEGORY_GENERAL, "WorldGen.LakeRarity.Sewage", 32);
		mfrLakeSewageRarity.comment = "Higher numbers make sewage lakes rarer. A value of one will be approximately one per chunk.";
		
		vanillaRecipes = c.get("RecipeSets", "EnableVanillaRecipes", true);
		vanillaRecipes.comment = "If true, MFR will register its standard (vanilla-item-only) recipes.";
		thermalExpansionRecipes = c.get("RecipeSets", "EnableThermalExpansionRecipes", false);
		thermalExpansionRecipes.comment = "If true, MFR will register its Thermal Expansion-based recipes.";
		gregTechRecipes = c.get("RecipeSets", "EnableGregTechRecipes", false);
		gregTechRecipes.comment = "If true, MFR will register its GregTech-based recipes.";
		
		for(Machine machine : Machine.values())
		{
			machine.load(c);
		}
		
		c.save();
	}
	
}