package powercrystals.minefactoryreloaded.modhelpers.mystcraft;

import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;


@Mod(modid = "MineFactoryReloaded|CompatMystcraft", name = "MFR Compat: Mystcraft", version = MineFactoryReloadedCore.version, dependencies = "after:MineFactoryReloaded;after:Mystcraft")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class Mystcraft
{
	private static final String lastUpdated = "for Mystcraft-uni-1.5.1-0.10.3.00, current release as of May 25 2013";
	
	@Init
	@SuppressWarnings("rawtypes")
	public static void load(FMLInitializationEvent e)
	{
		if(!Loader.isModLoaded("Mystcraft"))
		{
			FMLLog.warning("Mystcraft missing - MFR Mystcraft Compat not loading");
			return;
		}
		try
		{
			Class entityLinkbook = Class.forName("com.xcompwiz.mystcraft.entity.EntityLinkbook");
			MFRRegistry.registerSafariNetBlacklist(entityLinkbook);
		}
		catch (Exception x)
		{
			System.out.println("Last updated for " + lastUpdated);
			x.printStackTrace();
		}
	}
}
