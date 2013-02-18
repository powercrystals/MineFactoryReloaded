package powercrystals.minefactoryreloaded;

import java.util.ArrayList;
import java.util.List;

import powercrystals.minefactoryreloaded.api.ISafariNetHandler;

public abstract class MFRRegistry
{
	private static List<ISafariNetHandler> _safariNetHandlers = new ArrayList<ISafariNetHandler>();
	
	public static void registerSafariNetHandler(ISafariNetHandler handler)
	{
		_safariNetHandlers.add(handler);
	}
	
	public static List<ISafariNetHandler> getSafariNetHandlers()
	{
		return _safariNetHandlers;
	}
}
