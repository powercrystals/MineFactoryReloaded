package powercrystals.minefactoryreloaded.modhelpers.forestry;

import java.lang.reflect.Field;

import buildcraft.api.recipes.RefineryRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizerStandard;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizableSapling;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableStandard;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MineFactoryReloaded|CompatForestry", name = "MFR Compat: Forestry", version = MineFactoryReloadedCore.version, dependencies = "after:MineFactoryReloaded;after:Forestry")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class Forestry
{
	@Init
	public static void load(FMLInitializationEvent e)
	{
		if(!Loader.isModLoaded("Forestry"))
		{
			FMLLog.warning("Forestry missing - MFR Forestry Compat not loading");
			return;
		}
		try
		{
			Item fertilizer = (Item)Class.forName("forestry.core.config.ForestryItem").getField("fertilizerCompound").get(null);
			if(fertilizer != null)
			{
				MFRRegistry.registerFertilizer(new FertilizerStandard(fertilizer.itemID, 0));
			}
			Class<?> forestryBlocks = Class.forName("forestry.core.config.ForestryBlock");
			if(forestryBlocks != null)
			{
				MFRRegistry.registerHarvestable(new HarvestableStandard(((Block)forestryBlocks.getField("log1").get(null)).blockID, HarvestType.Tree));
				MFRRegistry.registerHarvestable(new HarvestableStandard(((Block)forestryBlocks.getField("log2").get(null)).blockID, HarvestType.Tree));
				MFRRegistry.registerHarvestable(new HarvestableStandard(((Block)forestryBlocks.getField("log3").get(null)).blockID, HarvestType.Tree));
				MFRRegistry.registerHarvestable(new HarvestableStandard(((Block)forestryBlocks.getField("log4").get(null)).blockID, HarvestType.Tree));
				MFRRegistry.registerHarvestable(new HarvestableStandard(((Block)forestryBlocks.getField("log5").get(null)).blockID, HarvestType.Tree));
				MFRRegistry.registerHarvestable(new HarvestableStandard(((Block)forestryBlocks.getField("log6").get(null)).blockID, HarvestType.Tree));
				MFRRegistry.registerHarvestable(new HarvestableStandard(((Block)forestryBlocks.getField("log7").get(null)).blockID, HarvestType.Tree));
				
				MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(((Block)forestryBlocks.getField("leaves").get(null)).blockID));
				
				MFRRegistry.registerPlantable(new PlantableStandard(((Block)forestryBlocks.getField("saplingGE").get(null)).blockID, ((Block)forestryBlocks.getField("saplingGE").get(null)).blockID));
				
				MFRRegistry.registerFertilizable(new FertilizableSapling(((Block)forestryBlocks.getField("saplingGE").get(null)).blockID));
			}
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
	
	@PostInit
	public static void postInit(FMLPostInitializationEvent e)
	{
		if(!Loader.isModLoaded("Forestry"))
		{
			return;
		}
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("milk", new LiquidStack(MineFactoryReloadedCore.milkLiquid,  LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(Item.bucketMilk), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("sludge", new LiquidStack(MineFactoryReloadedCore.sludgeLiquid,  LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(MineFactoryReloadedCore.sludgeBucketItem), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("sewage", new LiquidStack(MineFactoryReloadedCore.sewageLiquid,  LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(MineFactoryReloadedCore.sewageBucketItem), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("mobEssence", new LiquidStack(MineFactoryReloadedCore.essenceLiquid,  LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(MineFactoryReloadedCore.mobEssenceBucketItem), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("biofuel", new LiquidStack(MineFactoryReloadedCore.biofuelLiquid,  LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(MineFactoryReloadedCore.bioFuelBucketItem), new ItemStack(Item.bucketEmpty)));
		
		try
		{
			Class<?> forestryItems = Class.forName("forestry.core.config.ForestryItem");
			int forestryBiofuelId = ((Item)forestryItems.getField("liquidBiofuel").get(null)).itemID;
			
			for(RefineryRecipe rr : RefineryRecipe.getRecipes())
			{
				if(rr.result.itemID == forestryBiofuelId)
				{
					LiquidStack newResult = new LiquidStack(LiquidDictionary.getCanonicalLiquid("biofuel").itemID, rr.result.amount, LiquidDictionary.getCanonicalLiquid("biofuel").itemMeta);
					Field result = RefineryRecipe.class.getField("result");
					result.setAccessible(true);
					result.set(rr, newResult);
				}
			}
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
	}
}
