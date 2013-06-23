package powercrystals.minefactoryreloaded.modhelpers.forestry;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.FarmingRegistry;
import powercrystals.minefactoryreloaded.modhelpers.forestry.fertilizer.FertilizerForestry;
import powercrystals.minefactoryreloaded.modhelpers.forestry.leaves.FertilizableForestryLeaves;
import powercrystals.minefactoryreloaded.modhelpers.forestry.leaves.FruitForestry;
import powercrystals.minefactoryreloaded.modhelpers.forestry.trees.HarvestableForestryTree;
import powercrystals.minefactoryreloaded.modhelpers.forestry.utils.ForestryUtils;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import powercrystals.minefactoryreloaded.modhelpers.forestry.pods.FertilizableForestryPods;
import powercrystals.minefactoryreloaded.modhelpers.forestry.pods.FruitForestryPod;

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
			Class<?> forestryItems = Class.forName("forestry.core.config.ForestryItem");
			if(forestryItems != null)
			{
				Item peat = (Item)forestryItems.getField("peat").get(null);
				MFRRegistry.registerSludgeDrop(5, new ItemStack(peat));
			}
			
			for(Field f : Class.forName("forestry.core.config.ForestryBlock").getDeclaredFields())
			{
				if(f.getName().contains("log"))
				{
					Block log = ((Block)f.get(null));
					if(log != null)
					{
						FarmingRegistry.registerHarvestable(new HarvestableForestryTree(log.blockID));
						FarmingRegistry.registerFruitLogBlockId(log.blockID);
					}
				}
				else if(f.getName().contains("leaves"))
				{
					Block leaves = ((Block)f.get(null));
					if(leaves != null)
					{
						FarmingRegistry.registerFruit(new FruitForestry(leaves.blockID));
						FarmingRegistry.registerFertilizable(new FertilizableForestryLeaves(leaves.blockID));
					}
				}
				else if(f.getName().contains("pods"))
				{
					Block pods = ((Block)f.get(null));
					if(pods != null)
					{
						FarmingRegistry.registerFruit(new FruitForestryPod(pods.blockID));
						FarmingRegistry.registerFertilizable(new FertilizableForestryPods(pods.blockID));
					}
				}
			}
			FarmingRegistry.registerFertilizer(new FertilizerForestry(ForestryUtils.getItem("fertilizerCompound")));
		}
		catch(Exception x)
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
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("milk", new LiquidStack(
				MineFactoryReloadedCore.milkLiquid, LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(Item.bucketMilk), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("sludge", new LiquidStack(
				MineFactoryReloadedCore.sludgeLiquid, LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(MineFactoryReloadedCore.sludgeBucketItem),
				new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("sewage", new LiquidStack(
				MineFactoryReloadedCore.sewageLiquid, LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(MineFactoryReloadedCore.sewageBucketItem),
				new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("mobEssence", new LiquidStack(
				MineFactoryReloadedCore.essenceLiquid, LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(MineFactoryReloadedCore.mobEssenceBucketItem),
				new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("biofuel", new LiquidStack(
				MineFactoryReloadedCore.biofuelLiquid, LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(MineFactoryReloadedCore.bioFuelBucketItem),
				new ItemStack(Item.bucketEmpty)));
		
		MineFactoryReloadedCore.proxy.onPostTextureStitch(null);
	}
}
