package powercrystals.minefactoryreloaded;

import java.util.ArrayList;
import java.util.List;

import powercrystals.minefactoryreloaded.api.IMobEggHandler;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;

public abstract class MFRRegistry
{
	private static List<IMobEggHandler> _eggHandlers = new ArrayList<IMobEggHandler>();
	private static List<ISafariNetHandler> _safariNetHandlers = new ArrayList<ISafariNetHandler>();
	private static List<String> _rubberTreeBiomes = new ArrayList<String>();
	private static List<Class<?>> _safariNetBlacklist = new ArrayList<Class<?>>();
	
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
}
