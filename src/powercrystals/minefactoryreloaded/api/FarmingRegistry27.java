package powercrystals.minefactoryreloaded.api;

import java.lang.reflect.Method;

/**
 * @author PowerCrystals
 * 
 * Class used to register plants and other farming-related things with MFR. Will do nothing if MFR does not exist, but your mod should be set to load
 * after MFR or things may not work properly.
 * 
 * To avoid breaking the API, additional FarmingRegistry##s will appear on major MFR versions that contain API additions. On a Minecraft version change, 
 * these will be rolled back into this class.
 * 
 */
public class FarmingRegistry27
{
	/**
	 * Registers a grindable entity with the Grinder using the new grinder interface. This method will be renamed to the standard "registerGrindable"
	 * on MC 1.6.
	 * 
	 * @param grindable The entity to grind.
	 */
	public static void registerGrindable(IFactoryGrindable2 grindable)
	{
		try
		{
			Class<?> registry = Class.forName("powercrystals.minefactoryreloaded.MFRRegistry");
			if(registry != null)
			{
				Method reg = registry.getMethod("registerGrindable", IFactoryGrindable2.class);
				reg.invoke(registry, grindable);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Registers a grindable entity with the Grinder using the new grinder interface. This method will be renamed to the standard "registerGrindable"
	 * on MC 1.6.
	 * 
	 * @param grindable The entity to grind.
	 */
	public static void registerGrinderBlacklist(Class<?> ...ungrindables)
	{
		try
		{
			Class<?> registry = Class.forName("powercrystals.minefactoryreloaded.MFRRegistry");
			if(registry != null)
			{
				Method reg = registry.getMethod("registerGrinderBlacklist", Class[].class);
				reg.invoke(registry, (Object[])ungrindables);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
