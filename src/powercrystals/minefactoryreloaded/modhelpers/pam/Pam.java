package powercrystals.minefactoryreloaded.modhelpers.pam;

import java.lang.reflect.Method;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
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

@Mod(modid = "MineFactoryReloaded|CompatPams", name = "MFR Compat: Pam's Mods", version = MineFactoryReloadedCore.version,
dependencies = "after:MineFactoryReloaded;after:PamHCAsparagus;after:PamHCBean;after:PamHCBeet;after:PamHCBellpepper;"
		+ "after:PamHCBlackberry;after:PamHCBlueberry;after:PamHCBroccoli;after:PamHCCactusfruit;after:PamHCCandle;"
		+ "after:PamHCCantaloupe;after:PamHCCelery;after:PamHCChilipepper;after:PamHCCoffee;after:PamHCCorn;after:PamHCCotton;"
		+ "after:PamHCCranberry;after:PamHCCucumber;after:PamHCEggplant;after:PamHCGarlic;after:PamHCGinger;"
		+ "after:PamHCGrape;after:PamHCKiwi;after:PamHCLettuce;after:PamHCMustard;after:PamHCOnion;"
		+ "after:PamHCPeanut;after:PamHCPeas;after:PamHCPineapple;after:PamHCRadish;after:PamHCRaspberry;"
		+ "after:PamHCRice;after:PamHCRotten;after:PamHCSpiceleaf;after:PamHCStrawberry;after:PamHCSunflower;"
		+ "after:PamHCSweetpotato;after:PamHCTea;after:PamHCTomato;after:PamHCTurnip;after:PamHCWhitemushroom;"
		+ "after:PamHCZucchini;after:PamHarvestCraft;after:PamWeeeFlowers;after:PamHCApple;after:PamHCAvocado;after:PamHCBanana;"
		+ "after:PamHCCherry;after:PamHCCinnamon;after:PamHCCoconut;after:PamHCLemon;after:PamHCLime;after:PamHCMango;after:PamHCNutmeg;"
		+ "after:PamHCOlive;after:PamHCOrange;after:PamHCPapaya;after:PamHCPeach;after:PamHCPear;after:PamHCPeppercorn;after:PamHCPlum;"
		+ "after:PamHCPomegranate;after:PamHCStarfruit;after:PamHCVanillabean;after:PamHCWalnut")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class Pam
{
	private enum Category
	{
		BUSH("bushes"),
		CROP("crops"),
		MISC("misc");
		
		private String packageName;
		Category(String packageName)
		{
			this.packageName = packageName;
		}
		
		public String getPackageName()
		{
			return packageName;
		}
	}
	
	@Init
	public static void load(FMLInitializationEvent e)
	{
		if(!Loader.isModLoaded("PamHarvestCraft"))
		{
			FMLLog.warning("Pam's HC base missing - MFR Pam HC Compat not loading");
		}
		else
		{
			// Bushes
			registerBush("Blackberry", true, true); 
			registerBush("Blueberry", true, true);
			registerBush("Grape", true, true);
			registerBush("Kiwi", true, true);
			registerBush("Raspberry", true, true);
			registerBush("Spiceleaf", true, true);
			registerBush("Strawberry", true, true);
			registerBush("Sunflower", false, true);
			
			// Crops
			registerCrop("Asparagus", false, false);
			registerCrop("Bean", false, false);
			registerCrop("Beet", false, false);
			registerCrop("Bellpepper", true, false);
			registerCrop("Broccoli", false, false);
			registerCrop("Cantaloupe", true, false);
			registerCrop("Celery", false, false);
			registerCrop("Chilipepper", true, false);
			registerCrop("Coffee", true, false);
			registerCrop("Corn", true, false);
			registerCrop("Cucumber", false, false);
			registerCrop("Eggplant", true, false);
			registerCrop("Garlic", false, false);
			registerCrop("Ginger", false, false);
			registerCrop("Lettuce", false, false);
			registerCrop("Mustard", true, false);
			registerCrop("Onion", false, false);
			registerCrop("Peanut", false, false);
			registerCrop("Peas", true, false);
			registerCrop("Pineapple", false, false);
			registerCrop("Radish", false, false);
			registerCrop("Sweetpotato", false, false);
			registerCrop("Tea", false, false);
			registerCrop("Tomato", true, false);
			registerCrop("Turnip", false, false);
			registerCrop("Zucchini", true, false);
			
			// misc types
			registerPamMod("Candle", "Candleberry", Category.MISC, true, true, Block.tilledField.blockID);
			registerMisc("Cotton", true, true);
			
			// plants that require different base soils
			registerPamMod("Rice", "Rice", Category.CROP, false, false, Block.waterStill.blockID);
			registerPamMod("Cranberry", "Cranberry", Category.BUSH, false, true, Block.waterStill.blockID);
			registerPamMod("Whitemushroom", "Whitemushroom", Category.BUSH, false, true, Block.wood.blockID);
			registerPamMod("Rotten", "Rotten", Category.CROP, false, false, Block.slowSand.blockID);
			registerPamMod("Cactusfruit", "Cactusfruit", Category.BUSH, true, true, Block.sand.blockID);
			
			// fruits
			registerFruit("Apple");
			registerFruit("Avocado");
			registerFruit("Banana");
			registerFruit("Cherry");
			registerFruit("Coconut");
			registerFruit("Lemon");
			registerFruit("Lime");
			registerFruit("Mango");
			registerFruit("Nutmeg");
			registerFruit("Olive");
			registerFruit("Orange");
			registerFruit("Papaya");
			registerFruit("Peach");
			registerFruit("Pear");
			registerFruit("Peppercorn");
			registerFruit("Plum");
			registerFruit("Pomegranate");
			registerFruit("Starfruit");
			registerFruit("Vanillabean");
			registerFruit("Walnut");
			
			// special case for candle and cinnamon
			registerCandle();
			registerCinnamon();
			
			try
			{
				Class<?> mod = Class.forName("mods.PamHarvestCraft.PamHarvestCraft");
				MFRRegistry.registerSludgeDrop(25, new ItemStack((Item)mod.getField("saltItem").get(null)));
			}
			catch(Exception x)
			{
				x.printStackTrace();
			}
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
				Class<?> mod = Class.forName("mods.PamWeeeFlowers.PamWeeeFlowers");
				
				MFRRegistry.registerHarvestable(new HarvestableStandard(((Block)mod.getField("pamFlower").get(null)).blockID, HarvestType.Normal));
				
				for(String flower : flowers)
				{
					int seedId = ((Item)mod.getField(flower.toLowerCase() + "flowerseedItem").get(null)).itemID;
					int blockId = ((Block)mod.getField("pam" + flower.toLowerCase() + "flowerCrop").get(null)).blockID;
					Method fertilize = Class.forName("mods.PamWeeeFlowers.BlockPamFlowerCrop").getMethod("fertilize", World.class, int.class, int.class, int.class);
					
					MFRRegistry.registerPlantable(new PlantableCropPlant(seedId, blockId));
					MFRRegistry.registerHarvestable(new HarvestablePams(blockId));
					MFRRegistry.registerFertilizable(new FertilizableCropReflection(blockId, fertilize, 7));
				}
			}
			catch(ClassNotFoundException x)
			{
				FMLLog.warning("Unable to load Pam support for Weee! Flowers even though Weee! FLowers was present");
			}
			catch(Exception x)
			{
				x.printStackTrace();
			}
		}
	}
	
	private static void registerBush(String modName, boolean isPerennial, boolean hasWild)
	{
		registerPamModBasic(modName, Category.BUSH, isPerennial, hasWild);
	}
	
	private static void registerCrop(String modName, boolean isPerennial, boolean hasWild)
	{
		registerPamModBasic(modName, Category.CROP, isPerennial, hasWild);
	}
	
	private static void registerMisc(String modName, boolean isPerennial, boolean hasWild)
	{
		registerPamModBasic(modName, Category.MISC, isPerennial, hasWild);
	}
	
	private static void registerPamModBasic(String modName, Category category, boolean isPerennial, boolean hasWild)
	{
		registerPamMod(modName, modName, category, isPerennial, hasWild, Block.tilledField.blockID);
	}
	
	private static void registerPamMod(String modName, String cropName, Category category, boolean isPerennial, boolean hasWild, int plantableBlockId)
	{
		try
		{
			Class<?> mod;
			int blockIdCrop;
			int blockIdWild;
			int seedId;
			final String cropNameLC;
			cropNameLC = cropName.toLowerCase();
			final String baseClassPath;
			baseClassPath = String.format("mods.PamHarvestCraft.%s.%s", category.getPackageName(), modName.toLowerCase());
			
			mod = Class.forName(String.format("%s.PamHC%s", baseClassPath, modName));
			blockIdCrop = ((Block)mod.getField(String.format("pam%sCrop", cropNameLC)).get(null)).blockID;
			seedId = ((Item)mod.getField(String.format("%sseedItem", cropNameLC)).get(null)).itemID;
			
			if (plantableBlockId == Block.tilledField.blockID)
			{
				MFRRegistry.registerPlantable(new PlantableCropPlant(seedId, blockIdCrop));
			}
			else
			{
				MFRRegistry.registerPlantable(new PlantablePamSpecial(blockIdCrop, seedId, plantableBlockId));
			}
			
			if(hasWild)
			{
				blockIdWild = ((Block)mod.getField(String.format("pam%sWild", cropNameLC)).get(null)).blockID;
				MFRRegistry.registerHarvestable(new HarvestableStandard(blockIdWild, HarvestType.Normal));
			}
			
			if(isPerennial)
			{
				MFRRegistry.registerHarvestable(new HarvestablePamsPerennial(blockIdCrop));
			}
			else
			{
				MFRRegistry.registerHarvestable(new HarvestablePams(blockIdCrop));
			}
			
			MFRRegistry.registerFertilizable(new FertilizableCropReflection(blockIdCrop,
					Class.forName(String.format("%s.BlockPam%sCrop", baseClassPath, cropName)).getMethod("fertilize", World.class, int.class, int.class, int.class), 7));
		}
		catch(ClassNotFoundException x)
		{
			FMLLog.warning("Unable to load Pam support for %s", modName);
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
	}
	
	private static void registerFruit(String name)
	{
		try
		{
			Block fruit = (Block)Class.forName("mods.PamHarvestCraft.trees." + name.toLowerCase() + ".PamHC" + name).getField("pam" + name).get(null);
			MFRRegistry.registerFruit(new PamFruit(fruit.blockID));
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
	}
	
	private static void registerCandle()
	{
		try
		{
			Block fruit = (Block)Class.forName("mods.PamHarvestCraft.misc.candle.PamHCCandle").getField("pamCandle").get(null);
			MFRRegistry.registerFruit(new PamFruit(fruit.blockID));
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
	}
	
	private static void registerCinnamon()
	{
		try
		{
			Block fruit = (Block)Class.forName("mods.PamHarvestCraft.trees.cinnamon.PamHCCinnamon").getField("pamCinnamon").get(null);
			Item cinnamon = (Item)Class.forName("mods.PamHarvestCraft.trees.cinnamon.PamHCCinnamon").getField("cinnamonItem").get(null);
			MFRRegistry.registerFruit(new PamFruitCinnamon(fruit.blockID, cinnamon.itemID));
			MFRRegistry.registerFruitLogBlockId(fruit.blockID);
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
	}
}
