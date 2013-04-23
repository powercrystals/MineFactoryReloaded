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
	
	@Override
	protected void registerMachineUpgrades()
	{
		if(!Loader.isModLoaded("GregTech_Addon") || !Loader.isModLoaded("IC2"))
		{
			return;
		}
		try
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 0), new Object[]
					{
						"III",
						"PPP",
						"RGR",
						'I', "dyeBlue",
						'P', "dustPlastic",
						'R', "copperWire",
						'G', "basicCircuit",
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 1), new Object[]
					{
						"III",
						"PPP",
						"RGR",
						'I', Item.ingotIron,
						'P', "dustPlastic",
						'R', "copperWire",
						'G', "basicCircuit",
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 2), new Object[]
					{
						"III",
						"PPP",
						"RGR",
						'I', "ingotTin",
						'P', "dustPlastic",
						'R', "copperWire",
						'G', "basicCircuit",
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 3), new Object[]
					{
						"III",
						"PPP",
						"RGR",
						'I', "ingotCopper",
						'P', "dustPlastic",
						'R', "copperWire",
						'G', "advancedCircuit",
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 4), new Object[]
					{
						"III",
						"PPP",
						"RGR",
						'I', "ingotBronze",
						'P', "dustPlastic",
						'R', "copperWire",
						'G', "advancedCircuit",
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 5), new Object[]
					{
						"III",
						"PPP",
						"RGR",
						'I', "ingotSilver",
						'P', "dustPlastic",
						'R', "copperWire",
						'G', "advancedCircuit",
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 6), new Object[]
					{
						"III",
						"PPP",
						"RGR",
						'I', Item.ingotGold,
						'P', "dustPlastic",
						'R', "copperWire",
						'G', "advancedCircuit",
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 7), new Object[]
					{
						"III",
						"PPP",
						"RGR",
						'I', Item.netherQuartz,
						'P', "dustPlastic",
						'R', "copperWire",
						'G', "eliteCircuit",
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 8), new Object[]
					{
						"III",
						"PPP",
						"RGR",
						'I', Item.diamond,
						'P', "dustPlastic",
						'R', "copperWire",
						'G', "eliteCircuit",
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 9), new Object[]
					{
						"III",
						"PPP",
						"RGR",
						'I', "ingotPlatinum",
						'P', "dustPlastic",
						'R', "copperWire",
						'G', "eliteCircuit",
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 10), new Object[]
					{
						"III",
						"PPP",
						"RGR",
						'I', Item.emerald,
						'P', "dustPlastic",
						'R', "copperWire",
						'G', "eliteCircuit",
					} ));
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
	
	@Override
	protected void registerConveyors()
	{
		if(!Loader.isModLoaded("GregTech_Addon") || !Loader.isModLoaded("IC2"))
		{
			return;
		}
		try
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.conveyorBlock, 16, 16), new Object[]
					{
						"UUU",
						"RIR",
						'U', "itemRubber",
						'R', Item.redstone,
						'I', "plateIron",
					} ));
			
			for(int i = 0; i < 16; i++)
			{
				GameRegistry.addShapelessRecipe(new ItemStack(MineFactoryReloadedCore.conveyorBlock, 1, i), new ItemStack(MineFactoryReloadedCore.conveyorBlock, 1, 16), new ItemStack(MineFactoryReloadedCore.ceramicDyeItem, 1, i));
			}
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
	
	@Override
	protected void registerSyringes()
	{
		if(!Loader.isModLoaded("GregTech_Addon") || !Loader.isModLoaded("IC2"))
		{
			return;
		}
		try
		{
			ItemStack cell = Items.getItem("cell");
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.syringeEmptyItem, 1), new Object[]
					{
						"PRP",
						"PCP",
						" I ",
						'P', "sheetPlastic",
						'R', "itemRubber",
						'I', Item.ingotIron,
						'C', cell
					} ));
			
			GameRegistry.addShapelessRecipe(new ItemStack(MineFactoryReloadedCore.syringeHealthItem), new Object[] { MineFactoryReloadedCore.syringeEmptyItem, Item.appleRed });
			GameRegistry.addShapelessRecipe(new ItemStack(MineFactoryReloadedCore.syringeGrowthItem), new Object[] { MineFactoryReloadedCore.syringeEmptyItem, Item.goldenCarrot });
			
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.syringeZombieItem, 1), new Object[]
					{
						"FFF",
						"FSF",
						"FFF",
						'F', Item.rottenFlesh,
						'S', MineFactoryReloadedCore.syringeEmptyItem,
					} );
			
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.syringeSlimeItem, 1), new Object[]
					{
						"   ",
						" S ",
						"BLB",
						'B', Item.slimeBall,
						'L', new ItemStack(Item.dyePowder, 1, 4),
						'S', MineFactoryReloadedCore.syringeEmptyItem,
					} );
			
			GameRegistry.addShapelessRecipe(new ItemStack(MineFactoryReloadedCore.syringeCureItem), new Object[] { MineFactoryReloadedCore.syringeEmptyItem, Item.appleGold });
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
	
	@Override
	protected void registerMiscItems()
	{
		if(!Loader.isModLoaded("GregTech_Addon") || !Loader.isModLoaded("IC2"))
		{
			return;
		}
		try
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.plasticSheetItem, 4), new Object[]
					{
						"##",
						"##",
						'#', "dustPlastic",
					} ));
	
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.fertilizerItem, 16), new Object[]
					{
						"WBW",
						"STS",
						"WBW",
						'W', Item.wheat,
						'B', new ItemStack(Item.dyePowder, 1, 15),
						'S', Item.silk,
						'T', Item.stick,
					} );
			
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.safariNetItem, 1), new Object[]
					{
						" E ",
						"CGC",
						" E ",
						'E', Item.enderPearl,
						'G', Item.ghastTear,
						'C', "advancedCircuit"
					} );
			
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.safariNetSingleItem, 1), new Object[]
					{
						"SLS",
						"CBC",
						"S S",
						'S', Item.silk,
						'L', Item.leather,
						'B', Item.slimeBall,
						'C', "basicCircuit"
					} );
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.safariNetLauncherItem, 1), new Object[]
					{
						"PGP",
						"LGL",
						"IRI",
						'P', "sheetPlastic",
						'L', Item.lightStoneDust,
						'G', Item.gunpowder,
						'I', "plateIron",
						'R', "craftingItemValve"
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.factoryHammerItem, 1), new Object[]
					{
						"PPP",
						" S ",
						" S ",
						'P', "sheetPlastic",
						'S', Item.stick,
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.blankRecordItem, 1), new Object[]
					{
						"RRR",
						"RPR",
						"RRR",
						'R', "plasticDust",
						'P', Item.paper,
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.spyglassItem), new Object[]
					{
						"GLG",
						"PLP",
						" S ",
						'G', Item.ingotGold,
						'L', Block.glass,
						'P', "sheetPlastic",
						'S', Item.stick
					} ));
			
			GameRegistry.addRecipe(new ItemStack(MineFactoryReloadedCore.portaSpawnerItem), new Object[]
					{
						"GLG",
						"DND",
						"GLG",
						'G', "plateGold",
						'L', "plateAlloyIridium",
						'D', Item.diamond,
						'N', Item.netherStar
					} );
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.strawItem), new Object[]
					{
						"PP",
						"P ",
						"P ",
						'P', "sheetPlastic",
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.xpExtractorItem), new Object[]
					{
						"PLP",
						"PLP",
						"RPR",
						'R', "itemRubber",
						'L', Block.glass,
						'P', "sheetPlastic",
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.rulerItem), new Object[]
					{
						"P",
						"A",
						"P",
						'P', "sheetPlastic",
						'A', Item.paper,
					} ));
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
	
	@Override
	protected void registerRails()
	{
		if(!Loader.isModLoaded("GregTech_Addon") || !Loader.isModLoaded("IC2"))
		{
			return;
		}
		try
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.railPickupCargoBlock, 2), new Object[]
					{
						" C ",
						"SDS",
						"SSS",
						'C', "craftingItemValve",
						'S', "sheetPlastic",
						'D', Block.railDetector
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.railDropoffCargoBlock, 2), new Object[]
					{
						"SSS",
						"SDS",
						" C ",
						'C', "craftingItemValve",
						'S', "sheetPlastic",
						'D', Block.railDetector
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.railPickupPassengerBlock, 3), new Object[]
					{
						" L ",
						"SDS",
						"SSS",
						'L', Block.blockLapis,
						'S', "sheetPlastic",
						'D', Block.railDetector
					} ));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MineFactoryReloadedCore.railDropoffPassengerBlock, 3), new Object[]
					{
						"SSS",
						"SDS",
						" L ",
						'L', Block.blockLapis,
						'S', "sheetPlastic",
						'D', Block.railDetector
					} ));
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
}