package powercrystals.minefactoryreloaded.modhelpers.xycraft;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableCropPlant;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MineFactoryReloaded|CompatXyCraft", name = "MFR Compat: XyCraft", version = MineFactoryReloadedCore.version, dependencies = "after:MineFactoryReloaded;after:XyCraftWorld")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class Xycraft 
{
	@Init
	public static void load(FMLInitializationEvent e)
	{
		if(!Loader.isModLoaded("XyCraftWorld"))
		{
			FMLLog.warning("XyCraft missing - MFR Xycraft Compat not loading");
			return;
		}
		try
		{
			Class<?> blockClass = Class.forName("soaryn.xycraft.world.XyCraftWorldBlocks");
			
			int CornCropsID = ((Block)blockClass.getField("corn").get(null)).blockID;
			int HenequenCropsID = ((Block)blockClass.getField("henequen").get(null)).blockID;
			
			Class<?> itemClass = Class.forName("soaryn.xycraft.world.XyCraftWorldItems");
			
			int CornCropSeedId = ((Item)itemClass.getField("kernel").get(null)).itemID;
			int HenequenCropSeedId = ((Item)itemClass.getField("henequenSeeds").get(null)).itemID;
			
			MFRRegistry.registerHarvestable(new HarvestableXycraftCorn(CornCropsID));
			MFRRegistry.registerHarvestable(new HarvestableHenequen(HenequenCropsID));
			
			MFRRegistry.registerPlantable(new PlantableCropPlant(CornCropSeedId, CornCropsID));
			MFRRegistry.registerPlantable(new PlantableCropPlant(HenequenCropSeedId, HenequenCropsID));
			
			MFRRegistry.registerFertilizable(new FertilizableCorn(CornCropsID));
			MFRRegistry.registerFertilizable(new FertilizableHenequen(HenequenCropsID));
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
}