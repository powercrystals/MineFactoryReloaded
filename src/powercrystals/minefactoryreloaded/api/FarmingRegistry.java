package powercrystals.minefactoryreloaded.api;

import java.lang.reflect.Method;

import net.minecraft.item.ItemStack;

/**
 * @author PowerCrystals
 * 
 * Class used to register plants and other farming-related things with MFR. Will do nothing if MFR does not exist, but your mod should be set to load
 * after MFR or things may not work properly.
 * 
 */
public class FarmingRegistry
{
	/**
	 * Registers a plantable object with the Planter.
	 * 
	 * @param plantable The thing to plant.
	 */
	public static void registerPlantable(IFactoryPlantable plantable)
	{
		try
		{
			Class<?> planter = Class.forName("powercrystals.minefactoryreloaded.plants.TileEntityPlanter");
			if(planter != null)
			{
				Method reg = planter.getMethod("registerPlantable", IFactoryPlantable.class);
				reg.invoke(planter, plantable);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Registers a harvestable block with the Harvester.
	 * 
	 * @param harvestable The thing to harvest.
	 */
	public static void registerHarvestable(IFactoryHarvestable harvestable)
	{
		try
		{
			Class<?> harvester = Class.forName("powercrystals.minefactoryreloaded.plants.TileEntityHarvester");
			if(harvester != null)
			{
				Method reg = harvester.getMethod("registerHarvestable", IFactoryHarvestable.class);
				reg.invoke(harvester, harvestable);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Registers a fertilizable block with the Fertilizer.
	 * 
	 * @param fertilizable The thing to fertilize.
	 */
	public static void registerFertilizable(IFactoryFertilizable fertilizable)
	{
		try
		{
			Class<?> fertilizer = Class.forName("powercrystals.minefactoryreloaded.plants.TileEntityFertilizer");
			if(fertilizer != null)
			{
				Method reg = fertilizer.getMethod("registerFertilizable", IFactoryFertilizable.class);
				reg.invoke(fertilizer, fertilizable);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Registers a fertilizer item Fertilizer.
	 * 
	 * @param fertilizable The thing to fertilize with.
	 */
	public static void registerFertilizer(IFactoryFertilizer fertilizer)
	{
		try
		{
			Class<?> fertilizerTE = Class.forName("powercrystals.minefactoryreloaded.plants.TileEntityFertilizer");
			if(fertilizerTE != null)
			{
				Method reg = fertilizerTE.getMethod("registerFertilizer", IFactoryFertilizer.class);
				reg.invoke(fertilizerTE, fertilizer);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Registers a ranchable entity with the Rancher.
	 * 
	 * @param ranchable The entity to ranch.
	 */
	public static void registerRanchable(IFactoryRanchable ranchable)
	{
		try
		{
			Class<?> rancher = Class.forName("powercrystals.minefactoryreloaded.animals.TileEntityRancher");
			if(rancher != null)
			{
				Method reg = rancher.getMethod("registerRanchable", IFactoryRanchable.class);
				reg.invoke(rancher, ranchable);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Registers a grindable entity with the Grinder.
	 * 
	 * @param grindable The entity to grind.
	 */
	public static void registerGrindable(IFactoryGrindable grindable)
	{
		try
		{
			Class<?> grinder = Class.forName("powercrystals.minefactoryreloaded.animals.TileEntityGrinder");
			if(grinder != null)
			{
				Method reg = grinder.getMethod("registerGrindable", IFactoryGrindable.class);
				reg.invoke(grinder, grindable);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Registers a possible output with the sludge boiler.
	 * 
	 * @param weight Likelihood that this item will be produced. Lower means rarer. 
	 * @param drop The thing being produced by the sludge boiler.
	 */
	public static void registerSludgeDrop(int weight, ItemStack drop)
	{
		try
		{
			Class<?> boiler = Class.forName("powercrystals.minefactoryreloaded.processing.TileEntitySludgeBoiler");
			if(boiler != null)
			{
				Method reg = boiler.getMethod("registerSludgeDrop", int.class, ItemStack.class);
				reg.invoke(boiler, weight, drop);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
