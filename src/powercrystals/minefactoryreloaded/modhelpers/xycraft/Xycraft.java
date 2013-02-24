package powercrystals.minefactoryreloaded.modhelpers.xycraft;

@Mod(modid = "MFReloaded|CompatXycraft", name = "MFR Compat: Xycraft", version = MineFactoryReloadedCore.version, dependencies = "after:MFReloaded;after:Xycraft")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class Xycraft 
{
	@Init
	public static void load(FMLInitializationEvent e)
	{
		if(!Loader.isModLoaded("Xycraft"))
		{
			FMLLog.warning("Xycraft missing - MFR Xycraft Compat not loading");
			return;
		}
		try
		{
			Class<?> blockClass = Class.forName("soaryn.xycraft.world.XyCraftWorldBlocks");
			
			int CornCropsID = ((Block)modClass.getField("corn").get(null)).blockID;
			int HenequenCropsID = ((Block)modClass.getField("henequen").get(null)).blockID;
			
			Class<?> itemClass = Class.forName("soaryn.xycraft.world.XyCraftWorldItems");
			
			int CornCropSeedId = ((Item)modClass.getField("corn").get(null)).itemID;
			int HenequenCropSeedId = ((Item)modClass.getField("henequen").get(null)).itemID;
			
			FarmingRegistry.registerHarvestable(new HarvestableXycraftCorn(CornCropsID));
			FarmingRegistry.registerHarvestable(new HarvestableXycraftHennequen(HenequenCropsID));
			
			//FarmingRegistry.registerPlantable(new PlantableStandard(blockIdPlants, blockIdPlants));
			FarmingRegistry.registerPlantable(new PlantableCropPlant(CornCropSeedId, CornCropsID));
			FarmingRegistry.registerPlantable(new PlantableCropPlant(HenequenCropSeedId, HenequenCropsID));
			
			FarmingRegistry.registerFertilizable(new FertilizableCorn(CornCropsID));
			FarmingRegistry.registerFertilizable(new FertilizableHenequen(HenequenCropsID));
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
}