package powercrystals.minefactoryreloaded.modhelpers.magicalcrops;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableCropPlant;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableCropPlant;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MFReloaded|CompatMagicalCrops", name = "MFR Compat: Magical Crops", version = MineFactoryReloadedCore.version, dependencies = "after:MFReloaded;after:Magical-Crops")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class MagicalCrops
{
	@Init
	public static void load(FMLInitializationEvent e)
	{
		if(!Loader.isModLoaded("Magical-Crops"))
		{
			FMLLog.warning("Magical Crops missing - MFR Compat: Magical Crops not loading");
			return;
		}
		try
		{
			Class<?> mod = Class.forName("magicCrop.mod_mCrops");
			
			String[] herbs = {"Guam", "Harr", "Marr", "Ran", "Tarr", "Toad"};
			String[] crops = {"Sberry", "Tomato", "Sweetcorn", "Cucum", "Melon", "Bberry", "Rberry", "Grape", "Chil"};
			String[] magicalCrops = {"Coal", "Iron", "Redstone", "Glowstone", "Gold", "Diamond", "Lapis", "Blaze", "Emerald", "Ender", "Obsidian", "Gunpowder", "XP", "Copper", "Tin", "Dye"};
			
			for(String herb : herbs)
			{
				int seedId = ((Item)mod.getField("SeedHerb" + herb).get(null)).itemID;
				int blockId = ((Block)mod.getField("PlantHerb" + herb).get(null)).blockID;
				MFRRegistry.registerPlantable(new PlantableCropPlant(seedId, blockId));
				MFRRegistry.registerHarvestable(new HarvestableCropPlant(blockId));
			}
			
			for(String crop : crops)
			{
				int seedId = ((Item)mod.getField("seed" + crop).get(null)).itemID;
				int blockId = ((Block)mod.getField("crop" + crop).get(null)).blockID;
				MFRRegistry.registerPlantable(new PlantableCropPlant(seedId, blockId));
				MFRRegistry.registerHarvestable(new HarvestableCropPlant(blockId));
			}
			
			for(String magicalCrop : magicalCrops)
			{
				int seedId = ((Item)mod.getField("mSeeds" + magicalCrop).get(null)).itemID;
				int blockId = ((Block)mod.getField("mCrop" + magicalCrop).get(null)).blockID;
				MFRRegistry.registerPlantable(new PlantableCropPlant(seedId, blockId));
				MFRRegistry.registerHarvestable(new HarvestableCropPlant(blockId));
			}
			
		}
		catch (Exception x)
		{
			FMLLog.warning("Something went wrong in MFR Compat: Magical Crops. Probably Emy's fault.");
			x.printStackTrace();
		}
	}
}
