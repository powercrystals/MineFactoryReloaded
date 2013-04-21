package powercrystals.minefactoryreloaded.modhelpers.tinkersconstruct;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.farmables.grindables.GrindableStandard;

@Mod(modid = "MineFactoryReloaded|CompatTConstruct", name = "MFR Compat: TConstruct", version = MineFactoryReloadedCore.version, dependencies = "after:MineFactoryReloaded;after:TConstruct")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class TConstruct
{
	@Init
	@SuppressWarnings("rawtypes")
	public static void load(FMLInitializationEvent ev)
	{
		if(!Loader.isModLoaded("TConstruct"))
		{
			FMLLog.warning("TConstruct missing - MFR TConstruct Compat not loading");
			return;
		}
		try
		{
			Class tContent = Class.forName("mods.tinker.tconstruct.TContent");
			
			Class blueSlime = Class.forName("mods.tinker.tconstruct.entity.BlueSlime");
			Class metalSlime = Class.forName("mods.tinker.tconstruct.entity.MetalSlime");
			Class nitroCreeper = Class.forName("mods.tinker.tconstruct.entity.NitroCreeper");
			Class skyla = Class.forName("mods.tinker.tconstruct.entity.Skyla");
			
			ItemStack strangeFood = new ItemStack((Item)tContent.getField("strangeFood").get(null));
			
			MFRRegistry.registerGrindable(new GrindableStandard(blueSlime, strangeFood));
			MFRRegistry.registerGrindable(new GrindableStandard(metalSlime, strangeFood));
			MFRRegistry.registerGrindable(new GrindableStandard(nitroCreeper, new ItemStack(Item.gunpowder)));
			MFRRegistry.registerGrindable(new GrindableStandard(skyla, new ItemStack(Item.gunpowder)));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
