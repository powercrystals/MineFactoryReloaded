package powercrystals.minefactoryreloaded.modhelpers.forestry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MineFactoryReloaded|CompatForestryPre", name = "MFR Compat: Forestry (2)", version = MineFactoryReloadedCore.version, dependencies = "before:Forestry")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class ForestryPre
{
	@Init
	public static void init(FMLInitializationEvent e)
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
	}
}
