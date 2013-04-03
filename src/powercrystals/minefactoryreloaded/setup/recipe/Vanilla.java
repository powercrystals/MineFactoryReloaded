package powercrystals.minefactoryreloaded.setup.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.setup.Machine;
import cpw.mods.fml.common.registry.GameRegistry;

public class Vanilla
{
	public static void registerRecipes()
	{	
		FurnaceRecipes.smelting().addSmelting(MineFactoryReloadedCore.rawRubberItem.itemID, 0, new ItemStack(MineFactoryReloadedCore.rubberBarItem), 0.1F);
		FurnaceRecipes.smelting().addSmelting(MineFactoryReloadedCore.rubberWoodBlock.blockID, 0, new ItemStack(Item.coal, 1, 1), 0.1F);
		
		GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 3, 3), new ItemStack(MineFactoryReloadedCore.rubberWoodBlock));
		
		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 4), new Object[]
				{
					"R",
					"S",
					Character.valueOf('R'), MineFactoryReloadedCore.rawRubberItem,
					Character.valueOf('S'), Item.stick
				} );
		
		GameRegistry.addRecipe(new ItemStack(Block.pistonStickyBase), new Object[]
				{
					"R",
					"P",
					Character.valueOf('R'), MineFactoryReloadedCore.rawRubberItem,
					Character.valueOf('P'), Block.pistonBase
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.plasticSheetItem, 4), new Object[]
				{
					"##",
					"##",
					Character.valueOf('#'), MineFactoryReloadedCore.rawPlasticItem,
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBaseItem, 3), new Object[]
				{
					"PPP",
					"SSS",
					Character.valueOf('P'), MineFactoryReloadedCore.plasticSheetItem,
					Character.valueOf('S'), Block.stone,
				} );
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.syringeEmptyItem, 1), new Object[]
				{
					"PRP",
					"P P",
					" I ",
					Character.valueOf('P'), MineFactoryReloadedCore.plasticSheetItem,
					Character.valueOf('R'), "itemRubber",
					Character.valueOf('I'), Item.ingotIron,
				} ));
		
		GameRegistry.addShapelessRecipe(new ItemStack(MineFactoryReloadedCore.syringeHealthItem), new Object[] { MineFactoryReloadedCore.syringeEmptyItem, Item.appleRed });
		GameRegistry.addShapelessRecipe(new ItemStack(MineFactoryReloadedCore.syringeGrowthItem), new Object[] { MineFactoryReloadedCore.syringeEmptyItem, Item.goldenCarrot });
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.syringeZombieItem, 1), new Object[]
				{
					"FFF",
					"FSF",
					"FFF",
					Character.valueOf('F'), Item.rottenFlesh,
					Character.valueOf('S'), MineFactoryReloadedCore.syringeEmptyItem,
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.syringeSlimeItem, 1), new Object[]
				{
					"   ",
					" S ",
					"BLB",
					Character.valueOf('B'), Item.slimeBall,
					Character.valueOf('L'), new ItemStack(Item.dyePowder, 1, 4),
					Character.valueOf('S'), MineFactoryReloadedCore.syringeEmptyItem,
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.fertilizerItem, 16), new Object[]
				{
					"WBW",
					"STS",
					"WBW",
					Character.valueOf('W'), Item.wheat,
					Character.valueOf('B'), new ItemStack(Item.dyePowder, 1, 15),
					Character.valueOf('S'), Item.silk,
					Character.valueOf('T'), Item.stick,
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.safariNetItem, 1), new Object[]
				{
					" E ",
					"EGE",
					" E ",
					Character.valueOf('E'), Item.enderPearl,
					Character.valueOf('G'), Item.ghastTear,
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.safariNetSingleItem, 1), new Object[]
				{
					"SLS",
					" B ",
					"S S",
					Character.valueOf('S'), Item.silk,
					Character.valueOf('L'), Item.leather,
					Character.valueOf('B'), Item.slimeBall,
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.safariNetLauncherItem, 1), new Object[]
				{
					"PGP",
					"LGL",
					"IRI",
					Character.valueOf('P'), MineFactoryReloadedCore.plasticSheetItem,
					Character.valueOf('L'), Item.lightStoneDust,
					Character.valueOf('G'), Item.gunpowder,
					Character.valueOf('I'), Item.ingotIron,
					Character.valueOf('R'), Item.redstone,
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.factoryHammerItem, 1), new Object[]
				{
					"PPP",
					" S ",
					" S ",
					Character.valueOf('P'), MineFactoryReloadedCore.plasticSheetItem,
					Character.valueOf('S'), Item.stick,
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.factoryRoadBlock, 16), new Object[]
				{
					"BBB",
					"BPB",
					"BBB",
					Character.valueOf('P'), MineFactoryReloadedCore.plasticSheetItem,
					Character.valueOf('B'), new ItemStack(Block.stoneBrick, 1, 0),
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.factoryRoadBlock, 4, 1), new Object[]
				{
					"R R",
					" G ",
					"R R",
					Character.valueOf('R'), new ItemStack(MineFactoryReloadedCore.factoryRoadBlock, 1, 0),
					Character.valueOf('G'), Block.redstoneLampIdle,
				} );
		
		GameRegistry.addShapelessRecipe(new ItemStack(MineFactoryReloadedCore.factoryRoadBlock, 1, 4), new ItemStack(MineFactoryReloadedCore.factoryRoadBlock, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(MineFactoryReloadedCore.factoryRoadBlock, 1, 1), new ItemStack(MineFactoryReloadedCore.factoryRoadBlock, 1, 4));
		
		if(MineFactoryReloadedCore.vanillaOverrideIce.getBoolean(true))
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Block.ice, 1, 1), new ItemStack(Block.ice, 1, 0), new ItemStack(MineFactoryReloadedCore.rawPlasticItem, 1));
		}
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.blankRecordItem, 1), new Object[]
				{
					"RRR",
					"RPR",
					"RRR",
					Character.valueOf('R'), MineFactoryReloadedCore.rawPlasticItem,
					Character.valueOf('P'), Item.paper,
				} );
		
		if(Machine.Planter.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 0), new Object[]
					{
						"GGG",
						"CPC",
						" M ",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('P'), Block.pistonBase,
						Character.valueOf('C'), Item.flowerPot,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.Fisher.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 1), new Object[]
					{
						"GGG",
						"RRR",
						"BMB",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('R'), Item.fishingRod,
						Character.valueOf('B'), Item.bucketEmpty,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.Harvester.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 2), new Object[]
					{
						"GGG",
						"SXS",
						" M ",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('X'), Item.axeGold,
						Character.valueOf('S'), Item.shears,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.Rancher.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 3), new Object[]
					{
						"GGG",
						"SBS",
						" M ",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('B'), Item.bucketEmpty,
						Character.valueOf('S'), Item.shears,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.Fertilizer.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 4), new Object[]
					{
						"GGG",
						"LBL",
						" M ",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('L'), Item.leather,
						Character.valueOf('B'), Item.glassBottle,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.Vet.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 5), new Object[]
					{
						"GGG",
						"SSS",
						"EME",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('E'), Item.spiderEye,
						Character.valueOf('S'), MineFactoryReloadedCore.syringeEmptyItem,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.ItemCollector.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 8, 6), new Object[]
					{
						"GGG",
						" C ",
						" M ",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('C'), Block.chest,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.BlockBreaker.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 7), new Object[]
					{
						"GGG",
						"PHS",
						" M ",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('P'), Item.pickaxeGold,
						Character.valueOf('H'), MineFactoryReloadedCore.factoryHammerItem,
						Character.valueOf('S'), Item.shovelGold,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.WeatherCollector.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 8), new Object[]
					{
						"GGG",
						"BBB",
						"UMU",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('B'), Block.fenceIron,
						Character.valueOf('U'), Item.bucketEmpty,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.SludgeBoiler.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 9), new Object[]
					{
						"GGG",
						"FFF",
						" M ",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('F'), Block.furnaceIdle,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.Sewer.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 4, 10), new Object[]
					{
						"GGG",
						"BUB",
						"BMB",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('B'), Item.brick,
						Character.valueOf('U'), Item.bucketEmpty,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.Composter.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 11), new Object[]
					{
						"GGG",
						"PFP",
						" M ",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('P'), Block.pistonBase,
						Character.valueOf('F'), Block.furnaceIdle,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.Breeder.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 12), new Object[]
					{
						"GGG",
						"CAC",
						"PMP",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('P'), new ItemStack(Item.dyePowder, 1, 5),
						Character.valueOf('C'), Item.goldenCarrot,
						Character.valueOf('A'), Item.appleGold,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.Grinder.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 13), new Object[]
					{
						"GGG",
						"BSP",
						" M ",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('P'), Block.pistonBase,
						Character.valueOf('B'), Item.book,
						Character.valueOf('S'), Item.swordGold,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.AutoEnchanter.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 14), new Object[]
					{
						"GGG",
						"BBB",
						"DMD",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('B'), Item.book,
						Character.valueOf('D'), Item.diamond,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.Chronotyper.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 15), new Object[]
					{
						"GGG",
						"EEE",
						"PMP",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('E'), Item.emerald,
						Character.valueOf('P'), new ItemStack(Item.dyePowder, 1, 5),
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.Ejector.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 8, 0), new Object[]
					{
						"GGG",
						" D ",
						"RMR",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('D'), Block.dispenser,
						Character.valueOf('R'), Item.redstone,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.ItemRouter.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 8, 1), new Object[]
					{
						"GGG",
						"RCR",
						" M ",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('C'), Block.chest,
						Character.valueOf('R'), Item.redstoneRepeater,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.LiquidRouter.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 8, 2), new Object[]
					{
						"GGG",
						"RBR",
						"BMB",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('B'), Item.bucketEmpty,
						Character.valueOf('R'), Item.redstoneRepeater,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.DeepStorageUnit.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 4, 3), new Object[]
					{
						"GGG",
						"PPP",
						"EME",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('P'), Item.enderPearl,
						Character.valueOf('E'), Item.eyeOfEnder,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
			
			if(MineFactoryReloadedCore.enableCheapDSU.getBoolean(false))
			{
				GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 3), new Object[]
						{
							"GGG",
							"CCC",
							"CMC",
							Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
							Character.valueOf('C'), Block.chest,
							Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
						} );
			}
		}
		
		if(Machine.LiquiCrafter.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 4), new Object[]
					{
						"GGG",
						"BWB",
						"FMF",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('B'), Item.bucketEmpty,
						Character.valueOf('W'), Block.workbench,
						Character.valueOf('F'), Item.itemFrame,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.LavaFabricator.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 5), new Object[]
					{
						"GGG",
						"OBO",
						"CMC",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('O'), Block.obsidian,
						Character.valueOf('B'), Item.blazeRod,
						Character.valueOf('C'), Item.magmaCream,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.OilFabricator.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 6), new Object[]
					{
						"GGG",
						"OTO",
						"OMO",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('O'), Block.obsidian,
						Character.valueOf('T'), Block.tnt,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.AutoJukebox.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 7), new Object[]
					{
						"GGG",
						" J ",
						" M ",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('J'), Block.jukebox,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.Unifier.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 8), new Object[]
					{
						"GGG",
						"PBP",
						" M ",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('B'), Item.book,
						Character.valueOf('P'), Block.pumpkin,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.AutoSpawner.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 9), new Object[]
					{
						"GGG",
						"ECE",
						"NMS",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('E'), Item.emerald,
						Character.valueOf('C'), Item.magmaCream,
						Character.valueOf('N'), Item.netherStalkSeeds,
						Character.valueOf('S'), Item.sugar,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.BioReactor.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 10), new Object[]
					{
						"GGG",
						"UEU",
						"SMS",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('U'), Item.sugar,
						Character.valueOf('E'), Item.fermentedSpiderEye,
						Character.valueOf('S'), Item.slimeBall,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		if(Machine.BioFuelGenerator.getIsRecipeEnabled())
		{
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 11), new Object[]
					{
						"GGG",
						"PFP",
						"RMR",
						Character.valueOf('G'), MineFactoryReloadedCore.plasticSheetItem,
						Character.valueOf('F'), Block.furnaceIdle,
						Character.valueOf('P'), Block.pistonBase,
						Character.valueOf('R'), Item.blazeRod,
						Character.valueOf('M'), MineFactoryReloadedCore.machineBaseItem,
					} );
		}
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 0), new Object[]
				{
					"III",
					"PPP",
					"RGR",
					Character.valueOf('I'), "dyeBlue",
					Character.valueOf('P'), MineFactoryReloadedCore.rawPlasticItem,
					Character.valueOf('R'), Item.redstone,
					Character.valueOf('G'), Item.goldNugget,
				} ));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 1), new Object[]
				{
					"III",
					"PPP",
					"RGR",
					Character.valueOf('I'), Item.ingotIron,
					Character.valueOf('P'), MineFactoryReloadedCore.rawPlasticItem,
					Character.valueOf('R'), Item.redstone,
					Character.valueOf('G'), Item.goldNugget,
				} ));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 2), new Object[]
				{
					"III",
					"PPP",
					"RGR",
					Character.valueOf('I'), "ingotTin",
					Character.valueOf('P'), MineFactoryReloadedCore.rawPlasticItem,
					Character.valueOf('R'), Item.redstone,
					Character.valueOf('G'), Item.goldNugget,
				} ));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 3), new Object[]
				{
					"III",
					"PPP",
					"RGR",
					Character.valueOf('I'), "ingotCopper",
					Character.valueOf('P'), MineFactoryReloadedCore.rawPlasticItem,
					Character.valueOf('R'), Item.redstone,
					Character.valueOf('G'), Item.goldNugget,
				} ));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 4), new Object[]
				{
					"III",
					"PPP",
					"RGR",
					Character.valueOf('I'), "ingotBronze",
					Character.valueOf('P'), MineFactoryReloadedCore.rawPlasticItem,
					Character.valueOf('R'), Item.redstone,
					Character.valueOf('G'), Item.goldNugget,
				} ));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 5), new Object[]
				{
					"III",
					"PPP",
					"RGR",
					Character.valueOf('I'), "ingotSilver",
					Character.valueOf('P'), MineFactoryReloadedCore.rawPlasticItem,
					Character.valueOf('R'), Item.redstone,
					Character.valueOf('G'), Item.goldNugget,
				} ));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 6), new Object[]
				{
					"III",
					"PPP",
					"RGR",
					Character.valueOf('I'), Item.ingotGold,
					Character.valueOf('P'), MineFactoryReloadedCore.rawPlasticItem,
					Character.valueOf('R'), Item.redstone,
					Character.valueOf('G'), Item.goldNugget,
				} ));
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 7), new Object[]
				{
					"III",
					"PPP",
					"RGR",
					Character.valueOf('I'), Item.field_94583_ca,
					Character.valueOf('P'), MineFactoryReloadedCore.rawPlasticItem,
					Character.valueOf('R'), Item.redstone,
					Character.valueOf('G'), Item.goldNugget,
				} );
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 8), new Object[]
				{
					"III",
					"PPP",
					"RGR",
					Character.valueOf('I'), Item.diamond,
					Character.valueOf('P'), MineFactoryReloadedCore.rawPlasticItem,
					Character.valueOf('R'), Item.redstone,
					Character.valueOf('G'), Item.goldNugget,
				} ));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 9), new Object[]
				{
					"III",
					"PPP",
					"RGR",
					Character.valueOf('I'), "ingotPlatinum",
					Character.valueOf('P'), MineFactoryReloadedCore.rawPlasticItem,
					Character.valueOf('R'), Item.redstone,
					Character.valueOf('G'), Item.goldNugget,
				} ));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 10), new Object[]
				{
					"III",
					"PPP",
					"RGR",
					Character.valueOf('I'), Item.emerald,
					Character.valueOf('P'), MineFactoryReloadedCore.rawPlasticItem,
					Character.valueOf('R'), Item.redstone,
					Character.valueOf('G'), Item.goldNugget,
				} ));
		
		
		
		
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.conveyorBlock, 16, 16), new Object[]
				{
					"UUU",
					"RIR",
					Character.valueOf('U'), "itemRubber",
					Character.valueOf('R'), Item.redstone,
					Character.valueOf('I'), Item.ingotIron,
				} ));
		

		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.railPickupCargoBlock, 2), new Object[]
				{
					" C ",
					"SDS",
					"SSS",
					Character.valueOf('C'), Block.chest,
					Character.valueOf('S'), MineFactoryReloadedCore.plasticSheetItem,
					Character.valueOf('D'), Block.railDetector
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.railDropoffCargoBlock, 2), new Object[]
				{
					"SSS",
					"SDS",
					" C ",
					Character.valueOf('C'), Block.chest,
					Character.valueOf('S'), MineFactoryReloadedCore.plasticSheetItem,
					Character.valueOf('D'), Block.railDetector
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.railPickupPassengerBlock, 3), new Object[]
				{
					" L ",
					"SDS",
					"SSS",
					Character.valueOf('L'), Block.blockLapis,
					Character.valueOf('S'), MineFactoryReloadedCore.plasticSheetItem,
					Character.valueOf('D'), Block.railDetector
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.railDropoffPassengerBlock, 3), new Object[]
				{
					"SSS",
					"SDS",
					" L ",
					Character.valueOf('L'), Block.blockLapis,
					Character.valueOf('S'), MineFactoryReloadedCore.plasticSheetItem,
					Character.valueOf('D'), Block.railDetector
				} );
		
		for(int i = 0; i < 16; i++)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(MineFactoryReloadedCore.ceramicDyeItem, 4, i), new ItemStack(Item.clay), new ItemStack(Item.dyePowder, 1, 15 - i));
			GameRegistry.addShapelessRecipe(new ItemStack(MineFactoryReloadedCore.factoryGlassBlock, 1, i), new ItemStack(Block.glass), new ItemStack(MineFactoryReloadedCore.ceramicDyeItem, 1, i));
			GameRegistry.addShapelessRecipe(new ItemStack(MineFactoryReloadedCore.factoryGlassPaneBlock, 1, i), new ItemStack(Block.thinGlass), new ItemStack(MineFactoryReloadedCore.ceramicDyeItem, 1, i));
			GameRegistry.addShapelessRecipe(new ItemStack(MineFactoryReloadedCore.conveyorBlock, 1, i), new ItemStack(MineFactoryReloadedCore.conveyorBlock, 1, 16), new ItemStack(MineFactoryReloadedCore.ceramicDyeItem, 1, i));
			
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.factoryGlassPaneBlock, 16, i), new Object[]
					{
						"GGG",
						"GGG",
						Character.valueOf('G'), new ItemStack(MineFactoryReloadedCore.factoryGlassBlock, 1, i)
					} );
		}
		

		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.factoryDecorativeBrickBlock, 8, 0), new Object[]
				{
					"M M",
					" B ",
					"M M",
					Character.valueOf('B'), Block.brick,
					Character.valueOf('M'), Block.glowStone,
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.factoryDecorativeBrickBlock, 8, 1), new Object[]
				{
					"M M",
					" B ",
					"M M",
					Character.valueOf('B'), Block.brick,
					Character.valueOf('M'), Block.ice,
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.factoryDecorativeBrickBlock, 8, 2), new Object[]
				{
					"M M",
					" B ",
					"M M",
					Character.valueOf('B'), Block.brick,
					Character.valueOf('M'), Block.blockLapis,
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.factoryDecorativeBrickBlock, 8, 3), new Object[]
				{
					"M M",
					" B ",
					"M M",
					Character.valueOf('B'), Block.brick,
					Character.valueOf('M'), Block.obsidian,
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.factoryDecorativeBrickBlock, 8, 4), new Object[]
				{
					"M M",
					" B ",
					"M M",
					Character.valueOf('B'), Block.brick,
					Character.valueOf('M'), new ItemStack(Block.stoneSingleSlab, 1, 0),
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.factoryDecorativeBrickBlock, 8, 5), new Object[]
				{
					"M M",
					" B ",
					"M M",
					Character.valueOf('B'), Block.brick,
					Character.valueOf('M'), Block.blockSnow,
				} );
		
		if(MineFactoryReloadedCore.enableMossyCobbleRecipe.getBoolean(true))
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestoneMossy), new Object[]
					{
						Block.cobblestone,
						Item.bucketWater,
						Item.wheat
					} );
		}
		
		GameRegistry.addShapelessRecipe(new ItemStack(MineFactoryReloadedCore.milkBottleItem), new Object[]
					{
						Item.bucketMilk,
						Item.glassBottle
					} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.spyglassItem), new Object[]
				{
					"GLG",
					"PLP",
					" S ",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('L'), Block.glass,
					Character.valueOf('P'), MineFactoryReloadedCore.plasticSheetItem,
					Character.valueOf('S'), Item.stick
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.portaSpawnerItem), new Object[]
				{
					"GLG",
					"DND",
					"GLG",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('L'), Block.glass,
					Character.valueOf('D'), Item.diamond,
					Character.valueOf('N'), Item.netherStar
				} );
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.strawItem), new Object[]
				{
					"PP",
					"P ",
					"P ",
					Character.valueOf('P'), MineFactoryReloadedCore.plasticSheetItem,
				} );
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.xpExtractorItem), new Object[]
				{
					"PLP",
					"PLP",
					"RPR",
					Character.valueOf('R'), "itemRubber",
					Character.valueOf('L'), Block.glass,
					Character.valueOf('P'), MineFactoryReloadedCore.plasticSheetItem,
				} ));
		
		GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.redstoneCableBlock, 8), new Object[]
				{
					"PPP",
					"RRR",
					"PPP",
					Character.valueOf('R'), Item.redstone,
					Character.valueOf('P'), MineFactoryReloadedCore.plasticSheetItem,
				} );
	}
}
