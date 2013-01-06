package powercrystals.minefactoryreloaded.modhelpers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
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
import powercrystals.minefactoryreloaded.farmables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.farmables.PlantableCropPlant;
import powercrystals.minefactoryreloaded.farmables.PlantableStandard;

@Mod(modid = "MFReloaded|CompatRP2", name = "MFR Compat: RP2", version = MineFactoryReloadedCore.version, dependencies = "after:MFReloaded;after:RedPowerWorld")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class RP2
{
	@Init
	public static void load(FMLInitializationEvent e)
	{
		try
		{
			Class<?> modClass = Class.forName("com.eloraam.redpower.RedPowerWorld");
			
			int blockIdLeaves = ((Block)modClass.getField("blockLeaves").get(null)).blockID;
			int blockIdLogs = ((Block)modClass.getField("blockLogs").get(null)).blockID;
			int blockIdPlants = ((Block)modClass.getField("blockPlants").get(null)).blockID;
			int blockIdCrops = ((Block)modClass.getField("blockCrops").get(null)).blockID;
			
			int itemCropSeedId = ((Item)modClass.getField("itemSeeds").get(null)).shiftedIndex;
			
			Method fertilizeMethod = Class.forName("com.eloraam.redpower.world.BlockCustomFlower").getMethod("growTree", World.class, int.class, int.class, int.class);
			
			FarmingRegistry.registerHarvestable(new HarvestableTreeLeaves(blockIdLeaves));
			FarmingRegistry.registerHarvestable(new HarvestableStandard(blockIdLogs, HarvestType.Tree));
			FarmingRegistry.registerHarvestable(new RP2().new HarvestableRedPowerPlant(blockIdPlants));
			FarmingRegistry.registerHarvestable(new RP2().new HarvestableRedPowerFlax(blockIdCrops));
			
			FarmingRegistry.registerPlantable(new PlantableStandard(blockIdPlants, blockIdPlants));
			FarmingRegistry.registerPlantable(new PlantableCropPlant(itemCropSeedId, blockIdCrops));
			
			FarmingRegistry.registerFertilizable(new RP2().new FertilizableRedPowerFlax(blockIdCrops));
			FarmingRegistry.registerFertilizable(new RP2().new FertilizableRedPowerRubberTree(blockIdPlants, fertilizeMethod));
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
	
	public class HarvestableRedPowerFlax implements IFactoryHarvestable
	{
		private int _sourceId;
		
		public HarvestableRedPowerFlax(int sourceId)
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
			return world.getBlockId(x, y + 1, z) == _sourceId;
		}

		@Override
		public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
		{
			List<ItemStack> drops = new ArrayList<ItemStack>();
			drops.addAll(Block.blocksList[_sourceId].getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0));
			drops.addAll(Block.blocksList[_sourceId].getBlockDropped(world, x, y + 1, z, world.getBlockMetadata(x, y + 1, z), 0));
			return drops;
		}

		@Override
		public void preHarvest(World world, int x, int y, int z)
		{
			world.setBlockWithNotify(x, y + 1, z, 0);
		}

		@Override
		public void postHarvest(World world, int x, int y, int z)
		{
		}
	}
	
	public class HarvestableRedPowerPlant implements IFactoryHarvestable
	{
		private int _blockId;
		
		public HarvestableRedPowerPlant(int blockId)
		{
			_blockId = blockId;
		}
		
		@Override
		public int getSourceId()
		{
			return _blockId;
		}

		@Override
		public HarvestType getHarvestType()
		{
			return HarvestType.Normal;
		}

		@Override
		public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, int x, int y, int z)
		{
			return world.getBlockMetadata(x, y, z) != 1;
		}

		@Override
		public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
		{
			return null;
		}

		@Override
		public void preHarvest(World world, int x, int y, int z)
		{
		}

		@Override
		public void postHarvest(World world, int x, int y, int z)
		{
		}
	}
	
	public class FertilizableRedPowerFlax implements IFactoryFertilizable
	{
		private int _blockId;
		
		public FertilizableRedPowerFlax(int blockId)
		{
			_blockId = blockId;
		}
		
		@Override
		public int getFertilizableBlockId()
		{
			return _blockId;
		}

		@Override
		public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType)
		{
			int id = world.getBlockId(x, y + 1, z);
			return Block.blocksList[id] == null || Block.blocksList[id].isAirBlock(world, x, y + 1, z);
		}

		@Override
		public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
		{
			if(world.getBlockMetadata(x, y, z) < 4)
			{
				world.setBlockMetadataWithNotify(x, y, z, 4);
			}
			world.setBlockAndMetadataWithNotify(x, y + 1, z, _blockId, 5);
			return true;
		}
	}
	
	public class FertilizableRedPowerRubberTree implements IFactoryFertilizable
	{
		private int _blockId;
		private Method _fertilize;
		
		public FertilizableRedPowerRubberTree(int blockId, Method fertilize)
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
			return world.getBlockMetadata(x, y, z) == 1;
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
			return world.getBlockId(x, y, z) != _blockId;
		}
	}
}
