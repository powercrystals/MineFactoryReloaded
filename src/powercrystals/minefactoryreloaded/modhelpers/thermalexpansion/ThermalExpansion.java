package powercrystals.minefactoryreloaded.modhelpers.thermalexpansion;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

import thermalexpansion.api.crafting.CraftingManagers;

@Mod(modid = "MineFactoryReloaded|CompatThermalExpansion", name = "MFR Compat: ThermalExpansion", version = MineFactoryReloadedCore.version, dependencies = "after:MineFactoryReloaded;after:ThermalExpansion")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class ThermalExpansion
{
	@PostInit
	public static void postInit(FMLPostInitializationEvent e)
	{
		if(!Loader.isModLoaded("ThermalExpansion"))
		{
			FMLLog.warning("ThermalExpansion missing - Thermal Expansion compat not loading");
			return;
		}
		try
		{
			CraftingManagers.transposerManager.addFillRecipe(80, new ItemStack(Item.bucketEmpty), new ItemStack(Item.bucketMilk), LiquidDictionary.getLiquid("milk", LiquidContainerRegistry.BUCKET_VOLUME), true);
			CraftingManagers.transposerManager.addFillRecipe(80, new ItemStack(Item.bucketEmpty), new ItemStack(MineFactoryReloadedCore.sludgeBucketItem), LiquidDictionary.getLiquid("sludge", LiquidContainerRegistry.BUCKET_VOLUME), true);
			CraftingManagers.transposerManager.addFillRecipe(80, new ItemStack(Item.bucketEmpty), new ItemStack(MineFactoryReloadedCore.sewageBucketItem), LiquidDictionary.getLiquid("sewage", LiquidContainerRegistry.BUCKET_VOLUME), true);
			CraftingManagers.transposerManager.addFillRecipe(80, new ItemStack(Item.bucketEmpty), new ItemStack(MineFactoryReloadedCore.mobEssenceBucketItem), LiquidDictionary.getLiquid("essence", LiquidContainerRegistry.BUCKET_VOLUME), true);
			CraftingManagers.transposerManager.addFillRecipe(80, new ItemStack(Item.bucketEmpty), new ItemStack(MineFactoryReloadedCore.bioFuelBucketItem), LiquidDictionary.getLiquid("biofuel", LiquidContainerRegistry.BUCKET_VOLUME), true);
			CraftingManagers.transposerManager.addFillRecipe(80, new ItemStack(Item.bucketEmpty), new ItemStack(MineFactoryReloadedCore.meatBucketItem), LiquidDictionary.getLiquid("meat", LiquidContainerRegistry.BUCKET_VOLUME), true);
			CraftingManagers.transposerManager.addFillRecipe(80, new ItemStack(Item.bucketEmpty), new ItemStack(MineFactoryReloadedCore.sewageBucketItem), LiquidDictionary.getLiquid("sewage", LiquidContainerRegistry.BUCKET_VOLUME), true);
			CraftingManagers.transposerManager.addFillRecipe(80, new ItemStack(Item.bucketEmpty), new ItemStack(MineFactoryReloadedCore.pinkSlimeBucketItem), LiquidDictionary.getLiquid("pinkslime", LiquidContainerRegistry.BUCKET_VOLUME), true);
			CraftingManagers.transposerManager.addFillRecipe(80, new ItemStack(Item.bucketEmpty), new ItemStack(MineFactoryReloadedCore.chocolateMilkBucketItem), LiquidDictionary.getLiquid("chocolatemilk", LiquidContainerRegistry.BUCKET_VOLUME), true);
			CraftingManagers.transposerManager.addFillRecipe(80, new ItemStack(Item.bucketEmpty), new ItemStack(MineFactoryReloadedCore.mushroomSoupBucketItem), LiquidDictionary.getLiquid("mushroomsoup", LiquidContainerRegistry.BUCKET_VOLUME), true);
			CraftingManagers.transposerManager.addFillRecipe(80, new ItemStack(Item.bowlEmpty), new ItemStack(Item.bowlSoup), LiquidDictionary.getLiquid("mushroomsoup", LiquidContainerRegistry.BUCKET_VOLUME), true);
			
			CraftingManagers.pulverizerManager.addRecipe(320, new ItemStack(MineFactoryReloadedCore.factoryDecorativeStoneBlock, 1, 0), new ItemStack(MineFactoryReloadedCore.factoryDecorativeStoneBlock, 1, 2)); // Smooth Blackstone -> Cobble
			CraftingManagers.pulverizerManager.addRecipe(320, new ItemStack(MineFactoryReloadedCore.factoryDecorativeStoneBlock, 1, 1), new ItemStack(MineFactoryReloadedCore.factoryDecorativeStoneBlock, 1, 3)); // Smooth Whitestone -> Cobble
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
}
