package powercrystals.minefactoryreloaded.modhelpers.rp2;

import java.lang.reflect.Method;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableCropPlant;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableStandard;

@Mod(modid = "MFReloaded|CompatRP2", name = "MFR Compat: RP2", version = MineFactoryReloadedCore.version, dependencies = "after:MFReloaded;after:RedPowerWorld")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class RP2
{
	@Init
	public static void load(FMLInitializationEvent e)
	{
		if(!Loader.isModLoaded("RedPowerWorld"))
		{
			FMLLog.warning("RedPowerWorld missing - MFR RedPower2 Compat not loading");
			return;
		}
		try
		{
			Class<?> modClass = Class.forName("com.eloraam.redpower.RedPowerWorld");
			
			int blockIdLeaves = ((Block)modClass.getField("blockLeaves").get(null)).blockID;
			int blockIdLogs = ((Block)modClass.getField("blockLogs").get(null)).blockID;
			int blockIdPlants = ((Block)modClass.getField("blockPlants").get(null)).blockID;
			int blockIdCrops = ((Block)modClass.getField("blockCrops").get(null)).blockID;
			
			int itemCropSeedId = ((Item)modClass.getField("itemSeeds").get(null)).itemID;
			
			Method fertilizeMethod = Class.forName("com.eloraam.redpower.world.BlockCustomFlower").getMethod("growTree", World.class, int.class, int.class, int.class);
			
			MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(blockIdLeaves));
			MFRRegistry.registerHarvestable(new HarvestableStandard(blockIdLogs, HarvestType.Tree));
			MFRRegistry.registerHarvestable(new HarvestableRedPowerPlant(blockIdPlants));
			MFRRegistry.registerHarvestable(new HarvestableRedPowerFlax(blockIdCrops));
			
			MFRRegistry.registerPlantable(new PlantableStandard(blockIdPlants, blockIdPlants));
			MFRRegistry.registerPlantable(new PlantableCropPlant(itemCropSeedId, blockIdCrops));
			
			MFRRegistry.registerFertilizable(new FertilizableRedPowerFlax(blockIdCrops));
			MFRRegistry.registerFertilizable(new FertilizableRedPowerRubberTree(blockIdPlants, fertilizeMethod));
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
}
