package powercrystals.minefactoryreloaded.modhelpers;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.FarmingRegistry;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import powercrystals.minefactoryreloaded.farmables.HarvestableCropPlant;
import powercrystals.minefactoryreloaded.farmables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.PlantableCropPlant;
import powercrystals.minefactoryreloaded.farmables.PlantableStandard;
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
	public static Pam instance;
	
	@Init
	public static void load(FMLInitializationEvent e)
	{
		instance = new Pam();
		
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
					FarmingRegistry.registerFertilizable(instance.new FertilizablePams(blockId, fertilize));
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
				FarmingRegistry.registerHarvestable(instance.new HarvestablePamsPerennial(blockId));
			}
			else
			{
				FarmingRegistry.registerHarvestable(new HarvestableCropPlant(blockId));
			}
			
			FarmingRegistry.registerFertilizable(instance.new FertilizablePams(blockId,
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
			FarmingRegistry.registerPlantable(instance.new PlantablePamRice(blockId, seedId));
			
			FarmingRegistry.registerHarvestable(new HarvestableCropPlant(blockId));
			
			FarmingRegistry.registerFertilizable(instance.new FertilizablePams(blockId,
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
			FarmingRegistry.registerPlantable(instance.new PlantablePamWhiteMushroom(blockId, seedId));
			
			FarmingRegistry.registerHarvestable(new HarvestableCropPlant(blockId));
			
			FarmingRegistry.registerFertilizable(instance.new FertilizablePams(blockId,
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
	public class PlantablePamWhiteMushroom implements IFactoryPlantable
	{
		private int _blockId;
		private int _itemId;
		
		public PlantablePamWhiteMushroom(int blockId, int itemId)
		{
			_blockId = blockId;
			_itemId = itemId;
		}
		
		@Override
		public int getSourceId()
		{
			return _itemId;
		}

		@Override
		public int getPlantedBlockId(World world, int x, int y, int z, ItemStack stack)
		{
			return _blockId;
		}

		@Override
		public int getPlantedBlockMetadata(World world, int x, int y, int z, ItemStack stack)
		{
			return 0;
		}

		@Override
		public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack)
		{
			return world.getBlockId(x, y - 1, z) == Block.wood.blockID && world.getBlockId(x, y, z) == 0;
		}

		@Override
		public void prePlant(World world, int x, int y, int z, ItemStack stack)
		{
		}

		@Override
		public void postPlant(World world, int x, int y, int z, ItemStack stack)
		{
		}
	}
	
	public class PlantablePamRice implements IFactoryPlantable
	{
		private int _blockId;
		private int _itemId;
		
		public PlantablePamRice(int blockId, int itemId)
		{
			_blockId = blockId;
			_itemId = itemId;
		}
		
		@Override
		public int getSourceId()
		{
			return _itemId;
		}

		@Override
		public int getPlantedBlockId(World world, int x, int y, int z, ItemStack stack)
		{
			return _blockId;
		}

		@Override
		public int getPlantedBlockMetadata(World world, int x, int y, int z, ItemStack stack)
		{
			return 0;
		}

		@Override
		public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack)
		{
			return world.getBlockId(x, y - 1, z) == Block.waterStill.blockID && world.getBlockId(x, y, z) == 0;
		}

		@Override
		public void prePlant(World world, int x, int y, int z, ItemStack stack)
		{
		}

		@Override
		public void postPlant(World world, int x, int y, int z, ItemStack stack)
		{
		}
	}
	
	public class FertilizablePams implements IFactoryFertilizable
	{
		private Method _fertilize;
		private int _blockId;
		
		public FertilizablePams(int blockId, Method fertilize)
		{
			_blockId = blockId;
			_fertilize = fertilize;
		}
		
		@Override
		public int getFertilizableBlockId()
		{
			return _blockId;
		}

		@Override
		public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType)
		{
			return world.getBlockMetadata(x, y, z) < 7 && fertilizerType == FertilizerType.GrowPlant;
		}

		@Override
		public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
		{
			try
			{
				_fertilize.invoke(Block.blocksList[_blockId], world, x, y, z);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return world.getBlockMetadata(x, y, z) >= 7;
		}
	}
	
	public class HarvestablePams implements IFactoryHarvestable
	{
		private int _sourceId;
		
		public HarvestablePams(int sourceId)
		{
			_sourceId = sourceId;
		}
		
		@Override
		public int getSourceId()
		{
			return _sourceId;
		}

		@Override
		public HarvestType getHarvestType()
		{
			return HarvestType.Normal;
		}

		@Override
		public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, int x, int y, int z)
		{
			return world.getBlockMetadata(x, y, z) >= 7;
		}

		@Override
		public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
		{
			return Block.blocksList[_sourceId].getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		}

		@Override
		public void preHarvest(World world, int x, int y, int z)
		{
			if(world.getBlockMetadata(x, y, z) > 7)
			{
				world.setBlockMetadata(x, y, z, 7);
			}
		}

		@Override
		public void postHarvest(World world, int x, int y, int z)
		{
		}
	}
	
	public class HarvestablePamsPerennial extends HarvestablePams
	{
		public HarvestablePamsPerennial(int sourceId)
		{
			super(sourceId);
		}

		@Override
		public void postHarvest(World world, int x, int y, int z)
		{
			world.setBlockAndMetadataWithNotify(x, y, z, getSourceId(), 0);
		}
	}
}
