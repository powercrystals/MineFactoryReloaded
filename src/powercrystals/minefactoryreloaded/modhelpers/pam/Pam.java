package powercrystals.minefactoryreloaded.modhelpers.pam;

import java.lang.reflect.Method;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.FarmingRegistry;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.farmables.HarvestableCropPlant;
import powercrystals.minefactoryreloaded.farmables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.PlantableCropPlant;
import powercrystals.minefactoryreloaded.farmables.PlantableStandard;
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
			registerPamMod("Bean", "bean.PamHCBean", "bean.BlockPamBeanCrop", "beanseedItem", "pambeanCrop", false);
			registerPamMod("Bellpepper", "bellpepper.PamHCBellpepper", "bellpepper.BlockPamBellpepperCrop", "bellpepperseedItem", "pambellpepperCrop", true);
			registerPamMod("Blueberry", "blueberry.PamHCBlueberry", "blueberry.BlockPamBlueberryCrop", "blueberryseedItem", "pamblueberryCrop", true);
			registerPamMod("Chilipepper", "chilipepper.PamHCChilipepper", "chilipepper.BlockPamChilipepperCrop", "chilipepperseedItem", "pamchilipepperCrop", true);
			registerPamMod("Corn", "corn.PamHCCorn", "corn.BlockPamCornCrop", "cornseedItem", "pamcornCrop", false);
			registerPamMod("Cotton", "cotton.PamHCCotton", "cotton.BlockPamCottonCrop", "cottonseedItem", "pamcottonCrop", true);
			registerPamMod("Grape", "grape.PamHCGrape", "grape.BlockPamGrapeCrop", "grapeseedItem", "pamgrapeCrop", true);
			registerPamMod("Cucumber", "cucumber.PamHCCucumber", "cucumber.BlockPamCucumberCrop", "cucumberseedItem", "pamcucumberCrop", true);
			registerPamMod("Lettuce", "lettuce.PamHCLettuce", "lettuce.BlockPamLettuceCrop", "lettuceseedItem", "pamlettuceCrop", false);
			registerPamMod("Onion", "onion.PamHCOnion", "onion.BlockPamOnionCrop", "onionseedItem", "pamonionCrop", false);
			registerPamMod("Peanut", "peanut.PamHCPeanut", "peanut.BlockPamPeanutCrop", "peanutseedItem", "pampeanutCrop", true);
			registerPamMod("Strawberry", "strawberry.PamHCStrawberry", "strawberry.BlockPamStrawberryCrop", "strawberryseedItem", "pamstrawberryCrop", false);
			registerPamMod("Tomato", "tomato.PamHCTomato", "tomato.BlockPamTomatoCrop", "tomatoseedItem", "pamtomatoCrop", true);
			
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
					int seedId = ((Item)mod.getField(flower.toLowerCase() + "flowerseedItem").get(null)).shiftedIndex;
					int blockId = ((Block)mod.getField("pam" + flower.toLowerCase() + "flowerCrop").get(null)).blockID;
					Method fertilize = Class.forName("pamsmods.common.weeeflowers.BlockPam" + flower + "FlowerCrop").getMethod("fertilize", World.class, int.class, int.class, int.class);
					
					FarmingRegistry.registerPlantable(new PlantableStandard(seedId, blockId));
					FarmingRegistry.registerHarvestable(new HarvestableStandard(blockId, HarvestType.Normal));
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
	
	private static void registerPamMod(String modName, String modClass, String cropBlockClass, String itemField, String blockField, boolean isPerennial)
	{
		try
		{
			Class<?> mod;
			int blockId;
			int seedId;
			
			mod = Class.forName("pamsmods.common.harvestcraft." + modClass);
			blockId = ((Block)mod.getField(blockField).get(null)).blockID;
			seedId = ((Item)mod.getField(itemField).get(null)).shiftedIndex;
			FarmingRegistry.registerPlantable(new PlantableCropPlant(seedId, blockId));
			
			if(isPerennial)
			{
				FarmingRegistry.registerHarvestable(new HarvestablePamsPerennial(blockId));
			}
			else
			{
				FarmingRegistry.registerHarvestable(new HarvestableCropPlant(blockId));
			}
			
			FarmingRegistry.registerFertilizable(new FertilizableCropReflection(blockId,
					Class.forName("pamsmods.common.harvestcraft." + cropBlockClass).getMethod("fertilize", World.class, int.class, int.class, int.class)));
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
			int blockId;
			int seedId;
			
			mod = Class.forName("pamsmods.common.harvestcraft.rice.PamHCRice");
			blockId = ((Block)mod.getField("pamriceCrop").get(null)).blockID;
			seedId = ((Item)mod.getField("riceseedItem").get(null)).shiftedIndex;
			FarmingRegistry.registerPlantable(new PlantablePamRice(blockId, seedId));
			
			FarmingRegistry.registerHarvestable(new HarvestableCropPlant(blockId));
			
			FarmingRegistry.registerFertilizable(new FertilizableCropReflection(blockId,
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
			int blockId;
			int seedId;
			
			mod = Class.forName("pamsmods.common.harvestcraft.whitemushroom.PamHCWhitemushroom");
			blockId = ((Block)mod.getField("pamwhitemushroomCrop").get(null)).blockID;
			seedId = ((Item)mod.getField("whitemushroomseedItem").get(null)).shiftedIndex;
			FarmingRegistry.registerPlantable(new PlantablePamWhiteMushroom(blockId, seedId));
			
			FarmingRegistry.registerHarvestable(new HarvestableCropPlant(blockId));
			
			FarmingRegistry.registerFertilizable(new FertilizableCropReflection(blockId,
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
