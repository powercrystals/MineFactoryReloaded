package powercrystals.minefactoryreloaded.modhelpers.thermalexpansion;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

@Mod(modid = "MineFactoryReloaded|CompatThermalExpansion", name = "MFR Compat: ThermalExpansion", version = MineFactoryReloadedCore.version, dependencies = "after:MineFactoryReloaded;after:ThermalExpansion")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class ThermalExpansion
{

	@Init
	public static void load(FMLInitializationEvent e)
	{
		if(!Loader.isModLoaded("ThermalExpansion"))
		{
			FMLLog.warning("ThermalExpansion missing - MFR ThermalExpansion Compat not loading");
			return;
		}
		try
		{
			if(MineFactoryReloadedCore.thermalExpansionRecipes.getBoolean(false))
			{
				System.out.println("EMYDEBUG Alright, let's do TE recipes hoo-ah!");
				powercrystals.minefactoryreloaded.setup.recipe.ThermalExpansion.registerRecipes();
			}
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
}
