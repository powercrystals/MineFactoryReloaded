package powercrystals.minefactoryreloaded.modhelpers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.FarmingRegistry;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import powercrystals.minefactoryreloaded.farmables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.farmables.PlantableStandard;

@Mod(modid = "MFReloaded|CompatExtraBiomes", name = "MFR Compat: ExtraBiomes", version = MineFactoryReloadedCore.version, dependencies = "after:MFReloaded;after:ExtrabiomesXL")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class ExtraBiomes
{
	private static Map<String, HarvestType> _harvestRegistries;
	
	@Init
	public static void load(FMLInitializationEvent ev)
	{
		_harvestRegistries = new HashMap<String, HarvestType>();
		_harvestRegistries.put("CATTAIL", HarvestType.Normal);
		_harvestRegistries.put("FLOWER", HarvestType.Normal);
		_harvestRegistries.put("GRASS", HarvestType.Normal);
		_harvestRegistries.put("LEAFPILE", HarvestType.Normal);
		_harvestRegistries.put("CUSTOMLOG", HarvestType.Tree);
		_harvestRegistries.put("QUARTERLOG0", HarvestType.Tree);
		_harvestRegistries.put("QUARTERLOG1", HarvestType.Tree);
		_harvestRegistries.put("QUARTERLOG2", HarvestType.Tree);
		_harvestRegistries.put("QUARTERLOG3", HarvestType.Tree);
		
		try
		{
			Class<?> xbbs = Class.forName("extrabiomes.lib.BlockSettings");
			
			for(String s : new String[] { "AUTUMNLEAVES", "GREENLEAVES" })
			{
				Object o = xbbs.getField(s).get(null);
				Integer blockID = (Integer)xbbs.getMethod("getID").invoke(o);
				FarmingRegistry.registerHarvestable(new HarvestableTreeLeaves(blockID));
			}
			
			for(Entry<String, HarvestType> e : _harvestRegistries.entrySet())
			{
				Object o = xbbs.getField(e.getKey()).get(null);
				Integer blockID = (Integer)xbbs.getMethod("getID").invoke(o);
				FarmingRegistry.registerHarvestable(new HarvestableStandard(blockID, e.getValue()));
			}
			
			for(String s : new String[] { "SAPLING", "CATTAIL" })
			{
				Object o = xbbs.getField(s).get(null);
				Integer blockID = (Integer)xbbs.getMethod("getID").invoke(o);
				FarmingRegistry.registerPlantable(new PlantableStandard(blockID, blockID));
			}
			FertilizableExtraBiomesSapling fxbs = new ExtraBiomes().new FertilizableExtraBiomesSapling();
			FarmingRegistry.registerFertilizable(fxbs);
			
			FarmingRegistry.registerSludgeDrop(5, new ItemStack((Integer)xbbs.getMethod("getID").invoke(xbbs.getField("QUICKSAND").get(null)), 1, 0));
			FarmingRegistry.registerSludgeDrop(5, new ItemStack((Integer)xbbs.getMethod("getID").invoke(xbbs.getField("CRACKEDSAND").get(null)), 1, 0));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public class FertilizableExtraBiomesSapling implements IFactoryFertilizable
	{
		private int _blockID;
		private Method _fertilizeMethod;
		
		public FertilizableExtraBiomesSapling() throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchFieldException
		{
			
			Class<?> xbs = Class.forName("extrabiomes.blocks.BlockCustomSapling");
			Method fert = xbs.getMethod("growTree", World.class, int.class, int.class, int.class, Random.class);
			_fertilizeMethod = fert;

			Class<?> xbbs = Class.forName("extrabiomes.lib.BlockSettings");
			Object o = xbbs.getField("SAPLING").get(null);
			_blockID = (Integer)xbbs.getMethod("getID").invoke(o);
		}
		
		@Override
		public int getFertilizableBlockId()
		{
			return _blockID;
		}

		@Override
		public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType)
		{
			return fertilizerType == FertilizerType.GrowPlant;
		}

		@Override
		public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
		{
			try
			{
				_fertilizeMethod.invoke(Block.blocksList[_blockID], world, x, y, z, rand);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return world.getBlockId(x, y, z) != _blockID;
		}
	}
}
