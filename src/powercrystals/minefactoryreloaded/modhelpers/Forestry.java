package powercrystals.minefactoryreloaded.modhelpers;

import net.minecraft.item.Item;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.FarmingRegistry;
import powercrystals.minefactoryreloaded.farmables.FertilizerStandard;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MFReloaded|CompatForestry", name = "MFR Compat: Forestry", version = MineFactoryReloadedCore.version, dependencies = "after:MFReloaded;after:Forestry")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class Forestry
{
	@Init
	public static void load(FMLInitializationEvent e)
	{
		try
		{
			Item fertilizer = (Item)Class.forName("forestry.core.config.ForestryItem").getField("fertilizerCompound").get(null);
			if(fertilizer != null)
			{
				FarmingRegistry.registerFertilizer(new FertilizerStandard(fertilizer.shiftedIndex, 0));
			}
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
}