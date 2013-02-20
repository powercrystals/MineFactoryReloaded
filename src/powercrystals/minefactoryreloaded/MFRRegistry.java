package powercrystals.minefactoryreloaded;

import java.util.ArrayList;
import java.util.List;

import powercrystals.minefactoryreloaded.api.IMobEggHandler;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;

public abstract class MFRRegistry
{
	private static List<IMobEggHandler> _eggHandlers = new ArrayList<IMobEggHandler>();
	private static List<ISafariNetHandler> _safariNetHandlers = new ArrayList<ISafariNetHandler>();
	
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
}
