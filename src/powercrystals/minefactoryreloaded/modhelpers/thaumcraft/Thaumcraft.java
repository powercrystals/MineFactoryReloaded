package powercrystals.minefactoryreloaded.modhelpers.thaumcraft;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.FarmingRegistry;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.farmables.grindables.GrindableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableStandard;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MFReloaded|CompatThaumcraft", name = "MFR Compat: Thaumcraft", version = MineFactoryReloadedCore.version, dependencies = "after:MFReloaded;after:Thaumcraft")
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
		
			FarmingRegistry.registerHarvestable(new HarvestableStandard(tcLog.blockID, HarvestType.Tree));
			FarmingRegistry.registerHarvestable(new HarvestableStandard(tcLeaves.blockID, HarvestType.TreeLeaf));
			FarmingRegistry.registerHarvestable(new HarvestableThaumcraftPlant(tcSapling.blockID));
			
			FarmingRegistry.registerPlantable(new PlantableStandard(tcSapling.blockID, tcSapling.blockID));
			
			ItemStack zombieBrain = new ItemStack((Item)Class.forName("thaumcraft.common.Config").getField("itemResource").get(null), 1, 5);
			
			FarmingRegistry.registerGrindable(new GrindableStandard(Class.forName("thaumcraft.common.entities.monster.EntityBrainyZombie"), zombieBrain));
			FarmingRegistry.registerGrindable(new GrindableStandard(Class.forName("thaumcraft.common.entities.monster.EntityGiantBrainyZombie"), zombieBrain));
			FarmingRegistry.registerGrindable(new GrindableWisp());
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
	}
}