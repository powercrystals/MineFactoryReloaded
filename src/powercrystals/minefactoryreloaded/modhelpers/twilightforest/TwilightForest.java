package powercrystals.minefactoryreloaded.modhelpers.twilightforest;

import java.util.HashMap;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MFReloaded|CompatTwilightForest", name = "MFR Compat: TwilightForest", version = MineFactoryReloadedCore.version, dependencies = "after:MFReloaded;after:TwilightForest")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class TwilightForest
{
	@SuppressWarnings("rawtypes")
	public static HashMap entityEggs; 
	
	@SuppressWarnings("rawtypes")
	@Init
	public static void load(FMLInitializationEvent e)
	{
		if(!Loader.isModLoaded("TwilightForest"))
		{
			FMLLog.warning("Twilight Forest missing - MFR Twilight Forest Compat not loading");
			return;
		}
		
		try
		{
			entityEggs = (HashMap)Class.forName("twilightforest.entity.TFCreatures").getField("entityEggs").get(null);
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
	}
}
