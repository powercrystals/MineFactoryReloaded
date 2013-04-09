package powercrystals.minefactoryreloaded.modhelpers.atum;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.MobDrop;
import powercrystals.minefactoryreloaded.farmables.grindables.GrindableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableTreeLeaves;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MineFactoryReloaded|CompatAtum", name = "MFR Compat: Atum", version = MineFactoryReloadedCore.version, dependencies = "after:MineFactoryReloaded;after:Atum")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class Atum
{
	@SuppressWarnings("rawtypes")
	@Init
	public static void load(FMLInitializationEvent e)
	{
		if(!Loader.isModLoaded("Atum"))
		{
			FMLLog.warning("Atum missing - MFR Atum Compat not loading");
			return;
		}
		try
		{
			// The line after this comment is the worst. There must be a better way to do this, right?
			Class Atum = Class.forName("rebelkeithy.mods.atum.Atum");

			String entityprefix = "rebelkeithy.mods.atum.entities.Entity";
						
			Class banditArcher = Class.forName(entityprefix + "BanditArcher");
			Class banditWarrior = Class.forName(entityprefix + "BanditWarrior");
			Class dustySkeleton = Class.forName(entityprefix + "DustySkeleton");
			Class ghost = Class.forName(entityprefix + "Ghost");
			Class mummy = Class.forName(entityprefix + "Mummy");
			Class pharaoh = Class.forName(entityprefix + "Pharaoh");
			Class stoneSoldier = Class.forName(entityprefix + "StoneSoldier");
			
			ItemStack scimitar = new ItemStack((Item)Atum.getField("itemScimitar").get(null));
			ItemStack clothScrap = new ItemStack((Item)Atum.getField("itemClothScrap").get(null));
			ItemStack ectoplasm = new ItemStack((Item)Atum.getField("itemEctoplasm").get(null));
			ItemStack stoneChunk = new ItemStack((Item)Atum.getField("itemStoneChunk").get(null));
			
			int atumLogID = ((Block)Atum.getField("atumLog").get(null)).blockID;
			int atumLeavesID = ((Block)Atum.getField("atumLeaves").get(null)).blockID;
			
			MFRRegistry.registerSafariNetBlacklist(pharaoh);
			
			MFRRegistry.registerGrindable(new GrindableStandard(banditArcher, new ItemStack(Item.arrow, 2)));
			MFRRegistry.registerGrindable(new GrindableStandard(banditWarrior, new MobDrop[]
					{
						new MobDrop(1, scimitar),
						new MobDrop(9, null)
					}));
			MFRRegistry.registerGrindable(new GrindableStandard(dustySkeleton, new ItemStack(Item.bone, 1)));
			MFRRegistry.registerGrindable(new GrindableStandard(ghost, ectoplasm));
			MFRRegistry.registerGrindable(new GrindableStandard(mummy, new MobDrop[]
					{
						new MobDrop(3, new ItemStack(Item.rottenFlesh)),
						new MobDrop(1, clothScrap),
					}));
			MFRRegistry.registerGrindable(new GrindableStandard(stoneSoldier, stoneChunk));
			
			MFRRegistry.registerHarvestable(new HarvestableStandard(atumLogID, HarvestType.Tree));
			MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(atumLeavesID));
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
}
