package powercrystals.minefactoryreloaded.modhelpers.soulshards;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.farmables.grindables.GrindableStandard;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

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
			Class<?> soulShardBlaze = Class.forName("com.shadwdrgn.soulshards.EntitySpawnedBlaze");
			Class<?> soulShardSkeleton = Class.forName("com.shadwdrgn.soulshards.EntitySpawnedSkeleton");
			Class<?> soulShardZombie = Class.forName("com.shadwdrgn.soulshards.EntitySpawnedZombie");
			MFRRegistry.registerGrindable(new GrindableStandard(soulShardBlaze, new ItemStack(Item.blazeRod)));
			MFRRegistry.registerGrindable(new GrindableSoulShardSkeleton(soulShardSkeleton));
			MFRRegistry.registerGrindable(new GrindableStandard(soulShardZombie, new ItemStack(Item.rottenFlesh)));
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
}
