package powercrystals.minefactoryreloaded.modhelpers.twilightforest;

import java.util.HashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.FarmingRegistry;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.MobDrop;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizableSapling;
import powercrystals.minefactoryreloaded.farmables.grindables.GrindableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableStandard;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;


@Mod(modid = "MFReloaded|CompatTwilightForest", name = "MFR Compat: TwilightForest", version = MineFactoryReloadedCore.version, dependencies = "after:MFReloaded;after:TwilightForest")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class TwilightForest
{
	@SuppressWarnings("rawtypes")
	public static HashMap entityEggs; 
	
	public static ModContainer twilightForestContainer;
	
	@SuppressWarnings("rawtypes")
	@Init
	public static void load(FMLInitializationEvent e)
	{
		if(!Loader.isModLoaded("TwilightForest"))
		{
			FMLLog.warning("Twilight Forest missing - MFR Twilight Forest Compat not loading");
			return;
		}
		
		try
		{
							
			entityEggs = (HashMap)Class.forName("twilightforest.entity.TFCreatures").getField("entityEggs").get(null);
			twilightForestContainer = FMLCommonHandler.instance().findContainerFor(Class.forName("twilightforest.TwilightForestMod").getField("instance").get(null));
			
			Class tfBighorn = Class.forName("twilightforest.entity.EntityTFBighorn");
			Class tfBird = Class.forName("twilightforest.entity.EntityTFBird");
			Class tfBoar = Class.forName("twilightforest.entity.EntityTFBoar");
			Class tfBoggard = Class.forName("twilightforest.entity.EntityTFBoggard");
			Class tfBunny = Class.forName("twilightforest.entity.EntityTFBunny");
			Class tfDeathTome = Class.forName("twilightforest.entity.EntityTFDeathTome");
			Class tfDeer = Class.forName("twilightforest.entity.EntityTFDeer");
			Class tfFireBeetle = Class.forName("twilightforest.entity.EntityTFFireBeetle");
			Class tfHedgeSpider = Class.forName("twilightforest.entity.EntityTFHedgeSpider");
			Class tfHostileWolf = Class.forName("twilightforest.entity.EntityTFHostileWolf");
			Class tfHydra = Class.forName("twilightforest.entity.EntityTFHydra");
			Class tfHydraHead = Class.forName("twilightforest.entity.EntityTFHydraHead");
			Class tfHydraNeck = Class.forName("twilightforest.entity.EntityTFHydraNeck");
			Class tfHydraPart = Class.forName("twilightforest.entity.EntityTFHydraPart");
			Class tfKingSpider = Class.forName("twilightforest.entity.EntityTFKingSpider");
			Class tfKobold = Class.forName("twilightforest.entity.EntityTFKobold");
			Class tfLich = Class.forName("twilightforest.entity.EntityTFLich");
			Class tfLichMinion = Class.forName("twilightforest.entity.EntityTFLichMinion");
			Class tfLoyalZombie = Class.forName("twilightforest.entity.EntityTFLoyalZombie");
			Class tfMazeSlime = Class.forName("twilightforest.entity.EntityTFMazeSlime");
			Class tfMiniGhast = Class.forName("twilightforest.entity.EntityTFMiniGhast");
			Class tfMinoshroom = Class.forName("twilightforest.entity.EntityTFMinoshroom");
			Class tfMinotaur = Class.forName("twilightforest.entity.EntityTFMinotaur");
			Class tfMistWolf = Class.forName("twilightforest.entity.EntityTFMistWolf");
			Class tfNaga = Class.forName("twilightforest.entity.EntityTFNaga");
			Class tfNagaSegment = Class.forName("twilightforest.entity.EntityTFNagaSegment");
			Class tfPenguin = Class.forName("twilightforest.entity.EntityTFPenguin");
			Class tfPinchBeetle = Class.forName("twilightforest.entity.EntityTFPinchBeetle");
			Class tfQuestRam = Class.forName("twilightforest.entity.EntityTFQuestRam");
			Class tfRaven = Class.forName("twilightforest.entity.EntityTFRaven");
			Class tfRedcap = Class.forName("twilightforest.entity.EntityTFRedcap");
			Class tfRedcapSapper = Class.forName("twilightforest.entity.EntityTFRedcapSapper");
			Class tfSkeletonDruid = Class.forName("twilightforest.entity.EntityTFSkeletonDruid");
			Class tfSlimeBeetle = Class.forName("twilightforest.entity.EntityTFSlimeBeetle");
			Class tfSquirrel = Class.forName("twilightforest.entity.EntityTFSquirrel");
			Class tfSwarmSpider = Class.forName("twilightforest.entity.EntityTFSwarmSpider");
			Class tfTinyBird = Class.forName("twilightforest.entity.EntityTFTinyBird");
			Class tfTowerBoss = Class.forName("twilightforest.entity.EntityTFTowerBoss");
			Class tfTowerBroodling = Class.forName("twilightforest.entity.EntityTFTowerBroodling");
			Class tfTowerGhast = Class.forName("twilightforest.entity.EntityTFTowerGhast");
			Class tfTowerGolem = Class.forName("twilightforest.entity.EntityTFTowerGolem");
			Class tfTowerTermite = Class.forName("twilightforest.entity.EntityTFTowerTermite");
			Class tfWraith = Class.forName("twilightforest.entity.EntityTFWraith");

			MFRRegistry.registerSafariNetBlacklist(tfHydra);
			MFRRegistry.registerSafariNetBlacklist(tfHydraHead);
			MFRRegistry.registerSafariNetBlacklist(tfHydraNeck);
			MFRRegistry.registerSafariNetBlacklist(tfHydraPart);
			MFRRegistry.registerSafariNetBlacklist(tfLich);
			MFRRegistry.registerSafariNetBlacklist(tfNaga);
			MFRRegistry.registerSafariNetBlacklist(tfNagaSegment);
			MFRRegistry.registerSafariNetBlacklist(tfQuestRam);
			MFRRegistry.registerSafariNetBlacklist(tfTowerBoss);
			
			FarmingRegistry.registerMobEggHandler(new TwilightForestEggHandler());
			
			FarmingRegistry.registerRanchable(new RanchableTFBighorn(tfBighorn));
			
			Class tfItems = Class.forName("twilightforest.item.TFItems");
			if(tfItems != null)
			{
				MobDrop[] spiderDrops = new MobDrop[]
				{
					new MobDrop(3, new ItemStack(Item.silk)),
					new MobDrop(1, new ItemStack(Item.spiderEye))
				};
				
				ItemStack borerEssence = new ItemStack((Item)tfItems.getField("borerEssence").get(null));
				ItemStack charmOfKeeping1 = new ItemStack((Item)tfItems.getField("charmOfKeeping1").get(null));
				ItemStack tfFeather = new ItemStack((Item)tfItems.getField("feather").get(null));
				ItemStack magicMapFocus = new ItemStack((Item)tfItems.getField("magicMapFocus").get(null));
				ItemStack mazeMapFocus = new ItemStack((Item)tfItems.getField("mazeMapFocus").get(null));
				ItemStack meefRaw = new ItemStack((Item)tfItems.getField("meefRaw").get(null));
				ItemStack meefStroganoff = new ItemStack((Item)tfItems.getField("meefStroganoff").get(null));
				ItemStack torchberries = new ItemStack((Item)tfItems.getField("torchberries").get(null), 3);
				ItemStack venisonRaw = new ItemStack((Item)tfItems.getField("venisonRaw").get(null));
				
				FarmingRegistry.registerGrindable(new GrindableTFBighorn(tfBighorn));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfBird, new ItemStack(Item.feather)));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfBoar, new ItemStack(Item.porkRaw)));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfBoggard, new MobDrop[]
				{
					new MobDrop(20, mazeMapFocus),
					new MobDrop(17, new ItemStack(Item.bootsSteel)),
					new MobDrop(11, new ItemStack(Item.pickaxeSteel)),
					new MobDrop(52, null)
				}));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfBunny));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfDeathTome, new MobDrop[]
				{
					new MobDrop(3, magicMapFocus),
					new MobDrop(16, new ItemStack(Item.writableBook)),
					new MobDrop(30, new ItemStack(Item.book)),
					new MobDrop(31, new ItemStack(Item.paper))
				}));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfDeer, new MobDrop[]
				{
					new MobDrop(10, new ItemStack(Item.leather)),
					new MobDrop(10, venisonRaw)
				}));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfFireBeetle, new ItemStack(Item.gunpowder)));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfHedgeSpider, spiderDrops));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfHostileWolf));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfKingSpider, spiderDrops));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfKobold, new ItemStack(Item.wheat)));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfLichMinion, new ItemStack(Item.rottenFlesh)));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfLoyalZombie, new ItemStack(Item.rottenFlesh)));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfMazeSlime, new MobDrop[]
				{
					new MobDrop(1, charmOfKeeping1),
					new MobDrop(39, new ItemStack(Item.slimeBall))
				}));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfMiniGhast, new MobDrop[]
				{
					new MobDrop(10, new ItemStack(Item.gunpowder)),
					new MobDrop(10, new ItemStack(Item.ghastTear))
				}));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfMinoshroom, meefStroganoff));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfMinotaur, new MobDrop[]
				{
					new MobDrop(1, mazeMapFocus),
					new MobDrop(39, meefRaw)
					}));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfMistWolf));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfPenguin));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfPinchBeetle));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfRaven, tfFeather));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfRedcap, new ItemStack(Item.coal)));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfRedcapSapper, new ItemStack(Item.coal)));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfSkeletonDruid, new MobDrop[]
				{
					new MobDrop(10, new ItemStack(Item.bone, 3)),
					new MobDrop(10, torchberries)
				}));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfSlimeBeetle, new ItemStack(Item.slimeBall)));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfSquirrel));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfSwarmSpider, spiderDrops));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfTinyBird, new ItemStack(Item.feather)));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfTowerBroodling, spiderDrops));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfTowerGhast, new MobDrop[]
				{
					new MobDrop(10, new ItemStack(Item.gunpowder)),
					new MobDrop(10, new ItemStack(Item.ghastTear))
				}));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfTowerGolem, new MobDrop[]
				{
					new MobDrop(10, new ItemStack(Block.plantRed)),
					new MobDrop(10, new ItemStack(Item.ingotIron))
				}));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfTowerTermite, borerEssence));
				FarmingRegistry.registerGrindable(new GrindableStandard(tfWraith, new ItemStack(Item.lightStoneDust)));
			
			}
			
			Class tfBlocks = Class.forName("twilightforest.block.TFBlocks");
			if(tfBlocks != null)
			{
				FarmingRegistry.registerHarvestable(new HarvestableStandard(((Block)tfBlocks.getField("log").get(null)).blockID, HarvestType.Tree));
				FarmingRegistry.registerHarvestable(new HarvestableStandard(((Block)tfBlocks.getField("magicLog").get(null)).blockID, HarvestType.Tree));
				FarmingRegistry.registerHarvestable(new HarvestableStandard(((Block)tfBlocks.getField("magicLogSpecial").get(null)).blockID, HarvestType.Tree));
				FarmingRegistry.registerHarvestable(new HarvestableStandard(((Block)tfBlocks.getField("root").get(null)).blockID, HarvestType.Tree));
				FarmingRegistry.registerHarvestable(new HarvestableTreeLeaves(((Block)tfBlocks.getField("leaves").get(null)).blockID));
				FarmingRegistry.registerHarvestable(new HarvestableTreeLeaves(((Block)tfBlocks.getField("magicLeaves").get(null)).blockID));
				FarmingRegistry.registerHarvestable(new HarvestableTreeLeaves(((Block)tfBlocks.getField("hedge").get(null)).blockID));
				FarmingRegistry.registerHarvestable(new HarvestableTreeLeaves(((Block)tfBlocks.getField("firefly").get(null)).blockID));
				FarmingRegistry.registerHarvestable(new HarvestableTreeLeaves(((Block)tfBlocks.getField("cicada").get(null)).blockID));
				FarmingRegistry.registerHarvestable(new HarvestableStandard(((Block)tfBlocks.getField("plant").get(null)).blockID, HarvestType.Normal));
				
				FarmingRegistry.registerPlantable(new PlantableStandard(((Block)tfBlocks.getField("sapling").get(null)).blockID, ((Block)tfBlocks.getField("sapling").get(null)).blockID));
				
				FarmingRegistry.registerFertilizable(new FertilizableSapling(((Block)tfBlocks.getField("sapling").get(null)).blockID));
			}
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
	}
}
