package powercrystals.minefactoryreloaded.modhelpers.ae;

import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MineFactoryReloaded|CompatAppliedEnergistics", name = "MFR Compat: Applied Energistics", version = MineFactoryReloadedCore.version, dependencies = "after:MineFactoryReloaded;after:AppliedEnergistics")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class AppliedEnergistics
{
	private static final String lastUpdated = "Applied Energistics rv10.n, current release as of May 22 2013";
	
	@Init
	public static void load(FMLInitializationEvent e)
	{
		if(!Loader.isModLoaded("AppliedEnergistics"))
		{
			FMLLog.warning("Applied Energistics missing - MFR Applied Energistics Compat not loading");
			return;
		}
		try
		{
			ItemStack quartzOre = (ItemStack)Class.forName("appeng.api.Blocks").getField("blkQuartzOre").get(null);
			MFRRegistry.registerLaserOre(60, quartzOre);
			MFRRegistry.addLaserPreferredOre(0, quartzOre);
		}
		catch (Exception x)
		{
			System.out.println("Last updated for " + lastUpdated);
			x.printStackTrace();
		}
	}
}