package powercrystals.minefactoryreloaded.modhelpers.soulshards;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

@Mod(modid = "MineFactoryReloaded|CompatSoulShards", name = "MFR Compat: Soul Shards", version = MineFactoryReloadedCore.version, dependencies = "after:MineFactoryReloaded;after:SoulShards")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class SoulShards
{
	@Init
	public static void load(FMLInitializationEvent e)
	{
		if(!Loader.isModLoaded("SoulShards"))
		{
			FMLLog.warning("SoulShards missing - MFR SoulShards Compat not loading");
			return;
		}
		try
		{
			Class<?> soulShardSkeleton = Class.forName("com.shadwdrgn.soulshards.EntitySpawnedSkeleton");
			MFRRegistry.registerGrindable(new GrindableSoulShardSkeleton(soulShardSkeleton));
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
}
