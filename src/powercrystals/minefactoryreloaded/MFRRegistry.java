package powercrystals.minefactoryreloaded;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomItem;

import powercrystals.core.random.WeightedRandomItemStack;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizer;
import powercrystals.minefactoryreloaded.api.IFactoryGrindable;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import powercrystals.minefactoryreloaded.api.IFactoryRanchable;
import powercrystals.minefactoryreloaded.api.IMobEggHandler;
import powercrystals.minefactoryreloaded.api.IRandomMobProvider;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;

public abstract class MFRRegistry
{
	private static Map<Integer, IFactoryPlantable> _plantables = new HashMap<Integer, IFactoryPlantable>();
	private static Map<Integer, IFactoryHarvestable> _harvestables = new HashMap<Integer, IFactoryHarvestable>();
	private static Map<Integer, IFactoryFertilizer> _fertilizers = new HashMap<Integer, IFactoryFertilizer>();
	private static Map<Integer, IFactoryFertilizable> _fertilizables = new HashMap<Integer, IFactoryFertilizable>();
	private static Map<Class<?>, IFactoryRanchable> _ranchables = new HashMap<Class<?>, IFactoryRanchable>();
	private static Map<Class<?>, IFactoryGrindable> _grindables = new HashMap<Class<?>, IFactoryGrindable>();
	private static List<WeightedRandomItem> _sludgeDrops  = new ArrayList<WeightedRandomItem>();
	private static Map<Class<?>, ItemStack> _breederFoods = new HashMap<Class<?>, ItemStack>();
	
	private static List<IMobEggHandler> _eggHandlers = new ArrayList<IMobEggHandler>();
	private static List<ISafariNetHandler> _safariNetHandlers = new ArrayList<ISafariNetHandler>();
	private static List<String> _rubberTreeBiomes = new ArrayList<String>();
	private static List<Class<?>> _safariNetBlacklist = new ArrayList<Class<?>>();
	private static List<IRandomMobProvider> _randomMobProviders = new ArrayList<IRandomMobProvider>();
	
	public static void registerPlantable(IFactoryPlantable plantable)
	{
		_plantables.put(new Integer(plantable.getSeedId()), plantable);
	}
	
	public static Map<Integer, IFactoryPlantable> getPlantables()
	{
		return _plantables;
	}
	
	public static void registerHarvestable(IFactoryHarvestable harvestable)
	{
		_harvestables.put(harvestable.getPlantId(), harvestable);
	}
	
	public static Map<Integer, IFactoryHarvestable> getHarvestables()
	{
		return _harvestables;
	}
	
	public static void registerFertilizable(IFactoryFertilizable fertilizable)
	{
		_fertilizables.put(fertilizable.getFertilizableBlockId(), fertilizable);
	}
	
	public static Map<Integer, IFactoryFertilizable> getFertilizables()
	{
		return _fertilizables;
	}
	
	public static void registerFertilizer(IFactoryFertilizer fertilizer)
	{
		Integer i = new Integer(fertilizer.getFertilizerId());
		if(!_fertilizers.containsKey(i))
		{
			_fertilizers.put(i, fertilizer);
		}
	}
	
	public static Map<Integer, IFactoryFertilizer> getFertilizers()
	{
		return _fertilizers;
	}
	
	public static void registerRanchable(IFactoryRanchable ranchable)
	{
		_ranchables.put(ranchable.getRanchableEntity(), ranchable);
	}
	
	public static Map<Class<?>, IFactoryRanchable> getRanchables()
	{
		return _ranchables;
	}

	public static void registerGrindable(IFactoryGrindable grindable)
	{
		_grindables.put(grindable.getGrindableEntity(), grindable);
	}
	
	public static Map<Class<?>, IFactoryGrindable> getGrindables()
	{
		return _grindables;
	}
	
	public static void registerSludgeDrop(int weight, ItemStack drop)
	{
		_sludgeDrops.add(new WeightedRandomItemStack(weight, drop.copy()));
	}
	
	public static List<WeightedRandomItem> getSludgeDrops()
	{
		return _sludgeDrops;
	}

	public static void registerBreederFood(Class<?> entityToBreed, ItemStack food)
	{
		_breederFoods.put(entityToBreed, food.copy());
	}
	
	public static Map<Class<?>, ItemStack> getBreederFoods()
	{
		return _breederFoods;
	}
	
	public static void registerMobEggHandler(IMobEggHandler handler)
	{
		_eggHandlers.add(handler);
	}
	
	public static List<IMobEggHandler> getModMobEggHandlers()
	{
		return _eggHandlers;
	}
	
	public static void registerSafariNetHandler(ISafariNetHandler handler)
	{
		_safariNetHandlers.add(handler);
	}
	
	public static List<ISafariNetHandler> getSafariNetHandlers()
	{
		return _safariNetHandlers;
	}
	
	public static void registerRubberTreeBiome(String biome)
	{
		_rubberTreeBiomes.add(biome);
	}
	
	public static List<String> getRubberTreeBiomes()
	{
		return _rubberTreeBiomes;
	}
	
	public static void registerSafariNetBlacklist(Class<?> entityClass)
	{
		_safariNetBlacklist.add(entityClass);
	}
	
	public static List<Class<?>> getSafariNetBlacklist()
	{
		return _safariNetBlacklist;
	}
	
	public static void registerRandomMobProvider(IRandomMobProvider mobProvider)
	{
		_randomMobProviders.add(mobProvider);
	}
	
	public static List<IRandomMobProvider> getRandomMobProviders()
	{
		return _randomMobProviders;
	}
}
