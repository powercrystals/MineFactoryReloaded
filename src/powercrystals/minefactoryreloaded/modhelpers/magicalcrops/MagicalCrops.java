package powercrystals.minefactoryreloaded.modhelpers.magicalcrops;

import java.lang.reflect.Method;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizerStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableCropPlant;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableCropPlant;
import powercrystals.minefactoryreloaded.modhelpers.FertilizableCropReflection;
import powercrystals.minefactoryreloaded.modhelpers.magicalcrops.FertilizableMagicalCropReflection;
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
			
			// the various plants are separated by type to make future changes easier (mostly considering magicFertilizer behavior)
			String[] herbs = {"Guam", "Harr", "Marr", "Ran", "Tarr", "Toad"};
			String[] crops = {"Sberry", "Tomato", "Sweetcorn", "Cucum", "Melon", "Bberry", "Rberry", "Grape", "Chil"};
			String[] magicalCrops = {"Coal", "Iron", "Redstone", "Glowstone", "Gold", "Diamond", "Lapis", "Blaze", "Emerald", "Ender", "Obsidian", "Gunpowder", "XP", "Copper", "Tin", "Nether"};
			String[] soulCrops = {"Cow", "Pigmen", "Skele", "Spider"};
			
			int seedId;
			int blockId;
			Method fertilize;
			
			for(String herb : herbs)
			{
				seedId = ((Item)mod.getField("SeedHerb" + herb).get(null)).itemID;
				blockId = ((Block)mod.getField("PlantHerb" + herb).get(null)).blockID;
				fertilize = Class.forName("magicCrop.PlantHerb" + herb).getMethod("fertilize", World.class, int.class, int.class, int.class);
				MFRRegistry.registerPlantable(new PlantableCropPlant(seedId, blockId));
				MFRRegistry.registerHarvestable(new HarvestableCropPlant(blockId));
				MFRRegistry.registerFertilizable(new FertilizableCropReflection(blockId, fertilize));
			}
			
			for(String crop : crops)
			{
				seedId = ((Item)mod.getField("seed" + crop).get(null)).itemID;
				blockId = ((Block)mod.getField("crop" + crop).get(null)).blockID;
				fertilize = Class.forName("magicCrop.crop" + crop).getMethod("func_72272_c_", World.class, int.class, int.class, int.class);
				MFRRegistry.registerPlantable(new PlantableCropPlant(seedId, blockId));
				MFRRegistry.registerHarvestable(new HarvestableCropPlant(blockId));
				MFRRegistry.registerFertilizable(new FertilizableCropReflection(blockId, fertilize));
			}
			
			/*
			 *  mCropDye is named as a magical crop, but it actually extends vanilla BlockCrops.
			 *  This means that it needs to get its own special registration, rather than going on one of the string lists. 
			 */
			seedId = ((Item)mod.getField("mSeedsDye").get(null)).itemID;
			blockId = ((Block)mod.getField("mCropDye").get(null)).blockID;
			fertilize = Class.forName("magicCrop.mCropDye").getMethod("func_72272_c_", World.class, int.class, int.class, int.class);
			MFRRegistry.registerPlantable(new PlantableCropPlant(seedId, blockId));
			MFRRegistry.registerHarvestable(new HarvestableCropPlant(blockId));
			MFRRegistry.registerFertilizable(new FertilizableCropReflection(blockId, fertilize));
			
			for(String magicalCrop : magicalCrops)
			{
				seedId = ((Item)mod.getField("mSeeds" + magicalCrop).get(null)).itemID;
				blockId = ((Block)mod.getField("mCrop" + magicalCrop).get(null)).blockID;
				fertilize = Class.forName("magicCrop.mCrop" + magicalCrop).getMethod("fertilize", World.class, int.class, int.class, int.class);
				MFRRegistry.registerPlantable(new PlantableCropPlant(seedId, blockId));
				MFRRegistry.registerHarvestable(new HarvestableCropPlant(blockId));
				MFRRegistry.registerFertilizable(new FertilizableMagicalCropReflection(blockId, fertilize));
			}
			
			for(String soulCrop : soulCrops)
			{
				seedId = ((Item)mod.getField("soulSeed" + soulCrop).get(null)).itemID;
				blockId = ((Block)mod.getField("soulCrop" + soulCrop).get(null)).blockID;
				fertilize = Class.forName("magicCrop.soulCrop" + soulCrop).getMethod("fertilize", World.class, int.class, int.class, int.class);
				MFRRegistry.registerPlantable(new PlantableCropPlant(seedId, blockId));
				MFRRegistry.registerHarvestable(new HarvestableCropPlant(blockId));
				MFRRegistry.registerFertilizable(new FertilizableMagicalCropReflection(blockId, fertilize));
			}
			
			Item magicalFertilizer = (Item)mod.getField("magicFertilizer").get(null);
			if(magicalFertilizer != null)
			{
				MFRRegistry.registerFertilizer(new FertilizerStandard(magicalFertilizer.itemID, 0, FertilizerType.GrowMagicalCrop));
			}
			
		}
		catch (Exception x)
		{
			FMLLog.warning("Something went wrong in MFR Compat: Magical Crops. Probably Emy's fault.");
			x.printStackTrace();
		}
	}
}
