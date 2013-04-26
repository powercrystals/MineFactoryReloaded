package powercrystals.minefactoryreloaded.modhelpers.natura;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizableSapling;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableCropPlant;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableStandard;

@Mod(modid = "MineFactoryReloaded|CompatNatura", name = "MFR Compat: Natura", version = MineFactoryReloadedCore.version, dependencies = "after:MineFactoryReloaded;after:Natura")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class Natura
{
	@Init
	@SuppressWarnings("rawtypes")
	public static void load(FMLInitializationEvent ev)
	{
		if(!Loader.isModLoaded("Natura"))
		{
			FMLLog.warning("Natura missing - MFR Natura Compat not loading");
			return;
		}
		try
		{
			Class naturaContent = Class.forName("mods.natura.common.NaturaContent");
			
			int seedsId = ((Item)naturaContent.getField("seeds").get(null)).itemID;
			int berryItemId = ((Item)naturaContent.getField("berryItem").get(null)).itemID;
			int netherBerryItemId = ((Item)naturaContent.getField("netherBerryItem").get(null)).itemID;
			int saguaroFruitId = ((Item)naturaContent.getField("seedFood").get(null)).itemID;
			int cottonItemId = ((Item)naturaContent.getField("plantItem").get(null)).itemID;
			
			int cropsId = ((Block)naturaContent.getField("crops").get(null)).blockID;
			int berryBushId = ((Block)naturaContent.getField("berryBush").get(null)).blockID;
			int netherBerryBushId = ((Block)naturaContent.getField("netherBerryBush").get(null)).blockID;
			int saguaroId = ((Block)naturaContent.getField("saguaro").get(null)).blockID;
			int treeId = ((Block)naturaContent.getField("tree").get(null)).blockID;
			int redwoodId = ((Block)naturaContent.getField("redwood").get(null)).blockID;
			int floraSaplingId = ((Block)naturaContent.getField("floraSapling").get(null)).blockID;
			int floraLeavesId = ((Block)naturaContent.getField("floraLeaves").get(null)).blockID;
			int floraLeavesNoColorId = ((Block)naturaContent.getField("floraLeavesNoColor").get(null)).blockID;
			int bloodwoodId = ((Block)naturaContent.getField("floraLeaves").get(null)).blockID;
			
			MFRRegistry.registerPlantable(new PlantableCropPlant(seedsId, cropsId));
			MFRRegistry.registerPlantable(new PlantableStandard(berryBushId, berryBushId));
			MFRRegistry.registerPlantable(new PlantableStandard(netherBerryBushId, netherBerryBushId));
			MFRRegistry.registerPlantable(new PlantableStandard(saguaroFruitId, saguaroId));
			MFRRegistry.registerPlantable(new PlantableStandard(floraSaplingId, floraSaplingId));
			
			MFRRegistry.registerHarvestable(new HarvestableNaturaCropPlant(cropsId, cottonItemId));
			MFRRegistry.registerHarvestable(new HarvestableNaturaBerry(berryBushId, berryItemId));
			MFRRegistry.registerHarvestable(new HarvestableNaturaBerry(netherBerryBushId, netherBerryItemId));
			MFRRegistry.registerHarvestable(new HarvestableStandard(treeId, HarvestType.Tree));
			MFRRegistry.registerHarvestable(new HarvestableStandard(redwoodId, HarvestType.Tree));
			MFRRegistry.registerHarvestable(new HarvestableStandard(bloodwoodId, HarvestType.Tree));
			MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(floraLeavesId));
			MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(floraLeavesNoColorId));
			
			MFRRegistry.registerFertilizable(new FertilizableNaturaCrop(cropsId));
			MFRRegistry.registerFertilizable(new FertilizableSapling(floraSaplingId));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
