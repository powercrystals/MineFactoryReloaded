package powercrystals.minefactoryreloaded.setup.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.setup.MFRConfig;
import powercrystals.minefactoryreloaded.setup.Machine;
import thermalexpansion.api.item.ItemRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class ThermalExpansion extends Vanilla
{
	@Override
	protected void registerMachines()
	{
		if(!Loader.isModLoaded("ThermalExpansion"))
		{
			return;
		}
		try
		{
			ItemStack conduitLiquid = ItemRegistry.getItem("conduitLiquid", 1);
			ItemStack lamp = ItemRegistry.getItem("lamp", 1);
			ItemStack hardenedGlass = ItemRegistry.getItem("hardenedGlass", 1);
			ItemStack machineFrame = ItemRegistry.getItem("machineFrame", 1);
			ItemStack pneumaticServo = ItemRegistry.getItem("pneumaticServo", 1);
			ItemStack powerCoilElectrum = ItemRegistry.getItem("powerCoilElectrum", 1);
			ItemStack powerCoilGold = ItemRegistry.getItem("powerCoilGold", 1);
			ItemStack powerCoilSilver = ItemRegistry.getItem("powerCoilSilver", 1);
			ItemStack tesseractFrameFull = ItemRegistry.getItem("tesseractFrameFull", 1);
			
			if(Machine.Planter.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 0), new Object[]
						{
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', Item.flowerPot,
					'S', Block.pistonBase,
					'F', machineFrame,
					'O', "ingotCopper",
					'C', powerCoilGold,
						} ));
			}
			
			if(Machine.Fisher.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 1), new Object[]
						{
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', Item.fishingRod,
					'S', Item.bucketEmpty,
					'F', machineFrame,
					'O', "ingotIron",
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.Harvester.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 2), new Object[]
						{
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', Item.axeIron,
					'S', Item.shears,
					'F', machineFrame,
					'O', "ingotGold",
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.Rancher.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 3), new Object[]
						{
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', conduitLiquid,
					'S', Item.shears,
					'F', machineFrame,
					'O', "ingotTin",
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.Fertilizer.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 4), new Object[]
						{
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', Item.glassBottle,
					'S', Item.leather,
					'F', machineFrame,
					'O', "ingotSilver",
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.Vet.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 5), new Object[]
						{
					"PTP",
					"TFT",
					"OCO",
					'P', "sheetPlastic",
					'T', MineFactoryReloadedCore.syringeEmptyItem,
					'F', machineFrame,
					'O', "ingotCopper",
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.ItemCollector.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 8, 6), new Object[]
						{
					"P P",
					" F ",
					"PCP",
					'P', "sheetPlastic",
					'F', machineFrame,
					'C', Block.chest
						} ));
			}
			
			if(Machine.BlockBreaker.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 7), new Object[]
						{
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', "gearInvar",
					'S', Item.pickaxeIron,
					'F', machineFrame,
					'O', "ingotIron",
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.WeatherCollector.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 8), new Object[]
						{
					"PTP",
					" F ",
					"OCO",
					'P', "sheetPlastic",
					'T', Item.bucketEmpty,
					'F', machineFrame,
					'O', "ingotTin",
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.SludgeBoiler.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 9), new Object[]
						{
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', Item.bucketEmpty,
					'S', Block.furnaceIdle,
					'F', machineFrame,
					'O', "ingotIron",
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.Sewer.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 4, 10), new Object[]
						{
					"PTP",
					"SFS",
					"SSS",
					'P', "sheetPlastic",
					'T', Item.bucketEmpty,
					'S', Item.brick,
					'F', machineFrame,
						} ));
			}
			
			if(Machine.Composter.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 11), new Object[]
						{
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', Block.furnaceIdle,
					'S', Block.pistonBase,
					'F', machineFrame,
					'O', Item.brick,
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.Breeder.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 12), new Object[]
						{
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', Item.appleGold,
					'S', Item.goldenCarrot,
					'F', machineFrame,
					'O', new ItemStack(Item.dyePowder, 1, 5),
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.Grinder.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 13), new Object[]
						{
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', Item.swordGold,
					'S', "gearTin",
					'F', machineFrame,
					'O', Item.book,
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.AutoEnchanter.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 14), new Object[]
						{
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', Block.obsidian,
					'S', Item.book,
					'F', machineFrame,
					'O', Item.diamond,
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.Chronotyper.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, 15), new Object[]
						{
					"PTP",
					"TFT",
					"OCO",
					'P', "sheetPlastic",
					'T', Item.emerald,
					'F', machineFrame,
					'O', new ItemStack(Item.dyePowder, 1, 5),
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.Ejector.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 8, 0), new Object[]
						{							
					"PTP",
					" F ",
					"OOO",
					'P', "sheetPlastic",
					'T', pneumaticServo,
					'F', machineFrame,
					'O', Item.redstone
						} ));
			}
			
			if(Machine.ItemRouter.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 8, 1), new Object[]
						{					
					"PTP",
					"SFS",
					"PSP",
					'P', "sheetPlastic",
					'T', Block.chest,
					'S', Item.redstoneRepeater,
					'F', machineFrame
						} ));
			}
			
			if(Machine.LiquidRouter.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 8, 2), new Object[]
						{					
					"PTP",
					"SFS",
					"PSP",
					'P', "sheetPlastic",
					'T', conduitLiquid,
					'S', Item.redstoneRepeater,
					'F', machineFrame
						} ));
			}
			
			if(Machine.DeepStorageUnit.getIsRecipeEnabled())
			{
				int dsuCount = MFRConfig.craftSingleDSU.getBoolean(false) ? 1 : 4;
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), dsuCount, 3), new Object[]
						{					
					"PCP",
					"CFC",
					"PCP",
					'P', "sheetPlastic",
					'C', Block.chest,
					'F', tesseractFrameFull
						} ));
				
				if(MFRConfig.enableCheapDSU.getBoolean(false))
				{
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 3), new Object[]
							{					
						"PCP",
						"CFC",
						"PCP",
						'P', "sheetPlastic",
						'C', Block.chest,
						'F', machineFrame
							} ));
				}
			}
			
			if(Machine.LiquiCrafter.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 4), new Object[]
						{						
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', Block.workbench,
					'S', conduitLiquid,
					'F', machineFrame,
					'O', Item.book,
					'C', pneumaticServo
						} ));
			}
			
			if(Machine.LavaFabricator.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 5), new Object[]
						{
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', Block.obsidian,
					'S', Item.magmaCream,
					'F', machineFrame,
					'O', Item.blazeRod,
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.OilFabricator.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 6), new Object[]
						{
					"PTP",
					"OFO",
					"OCO",
					'P', "sheetPlastic",
					'T', Block.tnt,
					'F', machineFrame,
					'O', Block.obsidian,
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.AutoJukebox.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 7), new Object[]
						{
					"PJP",
					" F ",
					" P ",
					'P', "sheetPlastic",
					'J', Block.jukebox,
					'F', machineFrame
						} ));
			}
			
			if(Machine.Unifier.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 8), new Object[]
						{
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', "ingotCopper",
					'S', "ingotSilver",
					'F', machineFrame,
					'O', Item.comparator,
					'C', Item.book
						} ));
			}
			
			if(Machine.AutoSpawner.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 9), new Object[]
						{
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', Item.netherStalkSeeds,
					'S', Item.magmaCream,
					'F', machineFrame,
					'O', Item.emerald,
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.BioReactor.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 10), new Object[]
						{
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', Item.fermentedSpiderEye,
					'S', Item.slimeBall,
					'F', machineFrame,
					'O', Item.brick,
					'C', Item.sugar
						} ));
			}
			
			if(Machine.BioFuelGenerator.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 11), new Object[]
						{
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', Block.furnaceIdle,
					'S', Block.pistonBase,
					'F', machineFrame,
					'O', Item.blazeRod,
					'C', powerCoilSilver
						} ));
			}
			
			if(Machine.AutoDisenchanter.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 12), new Object[]
						{
					"PTP",
					"SFS",
					"OCO",
					'P', "sheetPlastic",
					'T', Block.netherBrick,
					'S', Item.book,
					'F', machineFrame,
					'O', Item.diamond,
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.Slaughterhouse.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 13), new Object[]
						{
					"GIG",
					"SFS",
					"XCX",
					'G', "sheetPlastic",
					'S', Item.swordGold,
					'X', Item.axeGold,
					'I', "gearInvar",
					'F', machineFrame,
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.MeatPacker.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 14), new Object[]
						{
					"GSG",
					"BFB",
					"BCB",
					'G', "sheetPlastic",
					'B', Block.brick,
					'S', Item.flintAndSteel,
					'F', machineFrame,
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.EnchantmentRouter.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 15), new Object[]
						{					
					"PBP",
					"SFS",
					"PSP",
					'P', "sheetPlastic",
					'B', Item.book,
					'S', Item.redstoneRepeater,
					'F', machineFrame
						} ));
			}
			
			if(Machine.LaserDrill.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(2), 1, 0), new Object[]
						{
					"GFG",
					"CFC",
					"DHD",
					'G', "sheetPlastic",
					'D', Item.diamond,
					'H', hardenedGlass,
					'F', lamp,
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.LaserDrillPrecharger.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(2), 1, 1), new Object[]
						{
					"GSG",
					"HFH",
					"DCD",
					'G', "sheetPlastic",
					'D', Item.diamond,
					'S', MineFactoryReloadedCore.pinkSlimeballItem,
					'H', hardenedGlass,
					'F', lamp,
					'C', powerCoilElectrum
						} ));
			}
			
			if(Machine.AutoAnvil.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(2), 1, 2), new Object[]
						{
					"GAG",
					"AFA",
					" C ",
					'G', "sheetPlastic",
					'A', Block.anvil,
					'F', machineFrame,
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.BlockSmasher.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(2), 1, 3), new Object[]
						{
					"GPG",
					"HFH",
					"BCB",
					'G', "sheetPlastic",
					'P', Block.pistonBase,
					'H', MineFactoryReloadedCore.factoryHammerItem,
					'B', Item.book,
					'F', machineFrame,
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.RedNote.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(2), 1, 4), new Object[]
						{
					"GNG",
					"CFC",
					'G', "sheetPlastic",
					'C', MineFactoryReloadedCore.rednetCableBlock,
					'N', Block.music,
					'F', machineFrame
						} ));
			}
			
			if(Machine.AutoBrewer.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(2), 1, 5), new Object[]
						{
					"GBG",
					"CFC",
					"RCR",
					'G', "sheetPlastic",
					'C', conduitLiquid,
					'B', Item.brewingStand,
					'R', Item.redstoneRepeater,
					'F', machineFrame,
					'C', powerCoilGold
						} ));
			}
			
			if(Machine.FruitPicker.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(2), 1, 6), new Object[]
						{
					"GXG",
					"SFS",
					"SCS",
					'G', "sheetPlastic",
					'X', Item.axeGold,
					'S', Item.shears,
					'F', machineFrame,
					'C', powerCoilGold
						} ));
			}
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
}