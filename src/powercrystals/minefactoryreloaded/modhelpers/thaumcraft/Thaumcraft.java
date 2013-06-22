package powercrystals.minefactoryreloaded.modhelpers.thaumcraft;

import net.minecraft.block.Block;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStandard;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MFReloaded|CompatThaumcraft", name = "MFR Compat: Thaumcraft", version = MineFactoryReloadedCore.version, dependencies = "after:MineFactoryReloaded;after:Thaumcraft")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class Thaumcraft
{
	@Init
	public static void load(FMLInitializationEvent e)
	{
		if(!Loader.isModLoaded("Thaumcraft"))
		{
			FMLLog.warning("Thaumcraft missing - MFR Thaumcraft Compat not loading");
			return;
		}
		
		try
		{
			Block tcSapling = (Block)Class.forName("thaumcraft.common.Config").getField("blockCustomPlant").get(null);
			Block tcLog = (Block)Class.forName("thaumcraft.common.Config").getField("blockMagicalLog").get(null);
			Block tcLeaves = (Block)Class.forName("thaumcraft.common.Config").getField("blockMagicalLeaves").get(null);
			
			MFRRegistry.registerHarvestable(new HarvestableStandard(tcLog.blockID, HarvestType.Tree));
			MFRRegistry.registerHarvestable(new HarvestableThaumcraftLeaves(tcLeaves.blockID, tcSapling.blockID));
			MFRRegistry.registerHarvestable(new HarvestableThaumcraftPlant(tcSapling.blockID));
			
			MFRRegistry.registerPlantable(new PlantableThaumcraftTree(tcSapling.blockID, tcSapling.blockID));
			
			// TODO: blacklist golems, redo wisp?
			
			MFRRegistry.registerGrindable(new GrindableWisp());
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
	}
}