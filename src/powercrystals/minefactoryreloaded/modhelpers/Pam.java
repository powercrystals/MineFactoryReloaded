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
import powercrystals.minefactoryreloaded.farmables.PlantableCropPlant;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MFReloaded|CompatPamsHarvestCraft", name = "MFR Compat: HarvestCraft", version = MineFactoryReloadedCore.version, dependencies = "after:MFReloaded;after:PamHCCorn;after:PamHCCotton;after:PamHCCucumber;after:PamHCLettuce;after:PamHCOnion;after:PamHCPeanut;after:PamHCRice;after:PamHCStrawberry;after:PamHCTomato")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class Pam
{
	public static Pam instance;
	
	@Init
	public static void load(FMLInitializationEvent e)
	{
		instance = new Pam();
		
		try
		{
			registerPamMod("corn.PamHCCorn", "corn.BlockPamCornCrop", "cornseedItem", "pamcornCrop", false);
			registerPamMod("cotton.PamHCCotton", "cotton.BlockPamCottonCrop", "cottonseedItem", "pamcottonCrop", true);
			registerPamMod("cucumber.PamHCCucumber", "cucumber.BlockPamCucumberCrop", "cucumberseedItem", "pamcucumberCrop", true);
			registerPamMod("lettuce.PamHCLettuce", "lettuce.BlockPamLettuceCrop", "lettuceseedItem", "pamlettuceCrop", false);
			registerPamMod("onion.PamHCOnion", "onion.BlockPamOnionCrop", "onionseedItem", "pamonionCrop", false);
			registerPamMod("peanut.PamHCPeanut", "peanut.BlockPamPeanutCrop", "peanutseedItem", "pampeanutCrop", true);
			registerPamMod("strawberry.PamHCStrawberry", "strawberry.BlockPamStrawberryCrop", "strawberryseedItem", "pamstrawberryCrop", false);
			registerPamMod("tomato.PamHCTomato", "tomato.BlockPamTomatoCrop", "tomatoseedItem", "pamtomatoCrop", true);
			
			// rice needs different plantable
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
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
	
	private static void registerPamMod(String modClass, String cropBlockClass, String itemField, String blockField, boolean isPerennial)
			throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException
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
			return world.getBlockId(x, y - 1, z) == Block.waterStill.blockID;
		}

		@Override
		public void prePlant(World world, int x, int y, int z, ItemStack stack)
		{
		}

		@Override
		public void postPlant(World world, int x, int y, int z)
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
	
	public class HarvestablePamsPerennial implements IFactoryHarvestable
	{
		private int _sourceId;
		
		public HarvestablePamsPerennial(int sourceId)
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
		}

		@Override
		public void postHarvest(World world, int x, int y, int z)
		{
			world.setBlockAndMetadataWithNotify(x, y, z, _sourceId, 0);
		}
	}
}
