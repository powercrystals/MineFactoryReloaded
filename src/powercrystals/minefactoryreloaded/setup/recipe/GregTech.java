package powercrystals.minefactoryreloaded.setup.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import ic2.api.Items;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.setup.Machine;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class GregTech extends Vanilla
{
	@Override
	protected void registerMachines()
	{
		if(!Loader.isModLoaded("GregTech_Addon") || !Loader.isModLoaded("IC2"))
		{
			return;
		}
		try
		{
			ItemStack generator = Items.getItem("generator");
			
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
							'F', "craftingRawMachineTier00",
							'O', "plateCopper",
							'C', "basicCircuit",
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
							'F', "craftingRawMachineTier01",
							'O', "plateIron",
							'C', "advancedCircuit"
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
							'F', "craftingRawMachineTier00",
							'O', "plateGold",
							'C', "basicCircuit"
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
							'T', "craftingPump",
							'S', Item.shears,
							'F', "craftingRawMachineTier01",
							'O', "plateTin",
							'C', "advancedCircuit"
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
							'F', "craftingRawMachineTier01",
							'O', "plateSilver",
							'C', "advancedCircuit"
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
							'F', "craftingRawMachineTier01",
							'O', "plateCopper",
							'C', "advancedCircuit"
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
							'F', "craftingRawMachineTier01",
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
							'T', "craftingItemValve",
							'S', Item.pickaxeIron,
							'F', "craftingRawMachineTier01",
							'O', "plateIron",
							'C', "advancedCircuit"
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
							'F', "craftingRawMachineTier01",
							'O', "plateTin",
							'C', "advancedCircuit"
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
							'F', "craftingRawMachineTier01",
							'O', "plateIron",
							'C', "advancedCircuit"
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
							'F', "craftingRawMachineTier01",
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
							'F', "craftingRawMachineTier01",
							'O', Item.brick,
							'C', "advancedCircuit"
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
							'F', "craftingRawMachineTier01",
							'O', new ItemStack(Item.dyePowder, 1, 5),
							'C', "advancedCircuit"
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
							'T', "plateAlloyIridium",
							'S', "craftingGrinder",
							'F', "craftingRawMachineTier02",
							'O', Item.book,
							'C', "advancedCircuit"
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
							'T', "plateAlloyIridium",
							'S', Item.book,
							'F', "craftingRawMachineTier03",
							'O', "eliteCircuit",
							'C', Block.obsidian
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
							'T', "gemEmerald",
							'F', "craftingRawMachineTier02",
							'O', new ItemStack(Item.dyePowder, 1, 5),
							'C', "eliteCircuit"
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
							'T', "craftingPump",
							'F', "craftingRawMachineTier00",
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
							'F', "craftingRawMachineTier01"
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
							'T', "craftingPump",
							'S', Item.redstoneRepeater,
							'F', "craftingRawMachineTier01"
						} ));
			}
			
			if(Machine.DeepStorageUnit.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 4, 3), new Object[]
						{					
							"PEP",
							"CFC",
							"PEP",
							'P', "sheetPlastic",
							'C', "eliteCircuit",
							'E', Item.eyeOfEnder,
							'F', "craftingRawMachineTier03"
						} ));
				
				if(MineFactoryReloadedCore.enableCheapDSU.getBoolean(false))
				{
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 3), new Object[]
							{					
								"PCP",
								"CFC",
								"PCP",
								'P', "sheetPlastic",
								'C', Block.chest,
								'F', "craftingRawMachineTier01"
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
							'S', "craftingPump",
							'F', "craftingRawMachineTier01",
							'O', Item.book,
							'C', "craftingLiquidMeter"
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
							'F', "craftingRawMachineTier02",
							'O', Item.blazeRod,
							'C', "advancedCircuit"
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
							'F', "craftingRawMachineTier02",
							'O', Block.obsidian,
							'C', "advancedCircuit"
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
							'F', "craftingRawMachineTier01"
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
							'T', "plateCopper",
							'S', "plateSilver",
							'F', "craftingRawMachineTier01",
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
							'T', "plateAlloyIridium",
							'S', Item.magmaCream,
							'F', "craftingRawMachineTier02",
							'O', Item.emerald,
							'C', "advancedCircuit"
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
							'F', "craftingRawMachineTier02",
							'O', "craftingItemValve",
							'C', "craftingPump"
						} ));
			}
			
			if(Machine.BioFuelGenerator.getIsRecipeEnabled())
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, 11), new Object[]
						{
							"PCP",
							"SFS",
							"OCO",
							'P', "sheetPlastic",
							'S', "plateIron",
							'F', generator,
							'O', Item.blazeRod,
							'C', "advancedCircuit"
						} ));
			}
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
}