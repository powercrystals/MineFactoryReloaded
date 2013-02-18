package powercrystals.minefactoryreloaded.modhelpers.pam;

import java.lang.reflect.Method;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.FarmingRegistry;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableCropPlant;
import powercrystals.minefactoryreloaded.modhelpers.FertilizableCropReflection;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MFReloaded|CompatPams", name = "MFR Compat: Pam's Mods", version = MineFactoryReloadedCore.version,
dependencies = "after:MFReloaded;after:PamHCBean;after:PamHCBellpepper;after:PamHCBlueberry;after:PamHCChilipepper;after:PamHCCorn;after:PamHCCotton;after:PamHCCucumber;"
 + "after:PamHCGrape;after:PamHCLettuce;after:PamHCOnion;after:PamHCPeanut;after:PamHCRice;after:PamHCStrawberry;after:PamHCTomato;after:PamHCWhitemushroom;"
 + "after:PamWeeeFlowers;after:PamHCBase")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class Pam
{
	@Init
	public static void load(FMLInitializationEvent e)
	{
		if(!Loader.isModLoaded("PamHCBase"))
		{
			FMLLog.warning("Pam's HC base missing - MFR Pam HC Compat not loading");
		}
		else
		{
			registerPamMod("Bean", false, false);
			registerPamMod("Bellpepper", true, false);
			registerPamMod("Blueberry", true, true);
			registerPamMod("Chilipepper", true, false);
			registerPamMod("Corn", false, false);
			registerPamMod("Cotton", true, true);
			registerPamMod("Grape", true, true);
			registerPamMod("Cucumber", true, false);
			registerPamMod("Lettuce", false, false);
			registerPamMod("Onion", false, false);
			registerPamMod("Peanut", true, false);
			registerPamMod("Strawberry", true, true);
			registerPamMod("Tomato", true, false);
			
			// rice and white mushroom need different plantable
			registerPamRice();
			registerPamWhiteMushroom();
		}
		
		if(!Loader.isModLoaded("PamWeeeFlowers"))
		{
			FMLLog.warning("Pam's Weee! Flowers missing - MFR Pam Weee! Flowers Compat not loading");
		}
		else
		{
			String[] flowers = { "White", "Orange", "Magenta", "LightBlue", "Yellow", "Lime", "Pink", "LightGrey", "DarkGrey", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black" };
			
			try
			{
				Class<?> mod = Class.forName("pamsmods.common.weeeflowers.PamWeeeFlowers");
				
				FarmingRegistry.registerHarvestable(new HarvestableStandard(((Block)mod.getField("pamFlower").get(null)).blockID, HarvestType.Normal));
				
				for(String flower : flowers)
				{
					int seedId = ((Item)mod.getField(flower.toLowerCase() + "flowerseedItem").get(null)).itemID;
					int blockId = ((Block)mod.getField("pam" + flower.toLowerCase() + "flowerCrop").get(null)).blockID;
					Method fertilize = Class.forName("pamsmods.common.weeeflowers.BlockPam" + flower + "FlowerCrop").getMethod("fertilize", World.class, int.class, int.class, int.class);
					
					FarmingRegistry.registerPlantable(new PlantableCropPlant(seedId, blockId));
					FarmingRegistry.registerHarvestable(new HarvestablePams(blockId));
					FarmingRegistry.registerFertilizable(new FertilizableCropReflection(blockId, fertilize));
				}
			}
			catch(ClassNotFoundException x)
			{
				FMLLog.warning("Unable to load Pam support for Weee! Flowers even though HarvestCraft base was present");
			}
			catch(Exception x)
			{
				x.printStackTrace();
			}
		}
	}
	
	private static void registerPamMod(String modName, boolean isPerennial, boolean hasWild)
	{
		try
		{
			Class<?> mod;
			int blockIdCrop;
			int blockIdWild;
			int seedId;
			
			mod = Class.forName("pamsmods.common.harvestcraft." + modName.toLowerCase() + ".PamHC" + modName);
			blockIdCrop = ((Block)mod.getField("pam" + modName.toLowerCase() + "Crop").get(null)).blockID;
			seedId = ((Item)mod.getField(modName.toLowerCase() + "seedItem").get(null)).itemID;
			
			FarmingRegistry.registerPlantable(new PlantableCropPlant(seedId, blockIdCrop));
			
			if(hasWild)
			{
				blockIdWild = ((Block)mod.getField("pam" + modName.toLowerCase() + "Wild").get(null)).blockID;
				FarmingRegistry.registerHarvestable(new HarvestableStandard(blockIdWild, HarvestType.Normal));
			}
			
			if(isPerennial)
			{
				FarmingRegistry.registerHarvestable(new HarvestablePamsPerennial(blockIdCrop));
			}
			else
			{
				FarmingRegistry.registerHarvestable(new HarvestablePams(blockIdCrop));
			}
			
			FarmingRegistry.registerFertilizable(new FertilizableCropReflection(blockIdCrop,
					Class.forName("pamsmods.common.harvestcraft." + modName.toLowerCase() + ".BlockPam" + modName + "Crop").getMethod("fertilize", World.class, int.class, int.class, int.class)));
		}
		catch(ClassNotFoundException x)
		{
			FMLLog.warning("Unable to load Pam support for %s even though HarvestCraft base was present", modName);
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
	}
	
	private static void registerPamRice()
	{
		try
		{
			Class<?> mod;
			int blockIdCrop;
			int seedId;
			
			mod = Class.forName("pamsmods.common.harvestcraft.rice.PamHCRice");
			blockIdCrop = ((Block)mod.getField("pamriceCrop").get(null)).blockID;
			seedId = ((Item)mod.getField("riceseedItem").get(null)).itemID;
			FarmingRegistry.registerPlantable(new PlantablePamRice(blockIdCrop, seedId));
			
			FarmingRegistry.registerHarvestable(new HarvestablePams(blockIdCrop));
			
			FarmingRegistry.registerFertilizable(new FertilizableCropReflection(blockIdCrop,
					Class.forName("pamsmods.common.harvestcraft.rice.BlockPamRiceCrop").getMethod("fertilize", World.class, int.class, int.class, int.class)));
		}
		catch(ClassNotFoundException x)
		{
			FMLLog.warning("Unable to load Pam support for Rice even though HarvestCraft base was present");
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
	}

	
	private static void registerPamWhiteMushroom()
	{
		try
		{
			Class<?> mod;
			int blockIdCrop;
			int blockIdWild;
			int seedId;
			
			mod = Class.forName("pamsmods.common.harvestcraft.whitemushroom.PamHCWhitemushroom");
			blockIdCrop = ((Block)mod.getField("pamwhitemushroomCrop").get(null)).blockID;
			blockIdWild = ((Block)mod.getField("pamwhitemushroomWild").get(null)).blockID;
			seedId = ((Item)mod.getField("whitemushroomseedItem").get(null)).itemID;
			FarmingRegistry.registerPlantable(new PlantablePamWhiteMushroom(blockIdCrop, seedId));

			FarmingRegistry.registerHarvestable(new HarvestableStandard(blockIdWild, HarvestType.Normal));
			FarmingRegistry.registerHarvestable(new HarvestablePams(blockIdCrop));
			
			FarmingRegistry.registerFertilizable(new FertilizableCropReflection(blockIdCrop,
					Class.forName("pamsmods.common.harvestcraft.whitemushroom.BlockPamWhitemushroomCrop").getMethod("fertilize", World.class, int.class, int.class, int.class)));
		}
		catch(ClassNotFoundException x)
		{
			FMLLog.warning("Unable to load Pam support for WhiteMushroom even though HarvestCraft base was present");
			x.printStackTrace();
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
	}
}
