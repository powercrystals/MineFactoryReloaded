package powercrystals.minefactoryreloaded;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import buildcraft.api.transport.FacadeManager;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.oredict.OreDictionary;

import powercrystals.core.mod.BaseMod;
import powercrystals.core.updater.UpdateManager;
import powercrystals.minefactoryreloaded.block.BlockConveyor;
import powercrystals.minefactoryreloaded.block.BlockDecorativeStone;
import powercrystals.minefactoryreloaded.block.BlockFactoryDecorativeBricks;
import powercrystals.minefactoryreloaded.block.BlockFactoryGlass;
import powercrystals.minefactoryreloaded.block.BlockFactoryGlassPane;
import powercrystals.minefactoryreloaded.block.BlockFactoryMachine;
import powercrystals.minefactoryreloaded.block.BlockFactoryRoad;
import powercrystals.minefactoryreloaded.block.BlockFakeLaser;
import powercrystals.minefactoryreloaded.block.BlockFactoryFluid;
import powercrystals.minefactoryreloaded.block.BlockPinkSlimeFluid;
import powercrystals.minefactoryreloaded.block.BlockRailCargoDropoff;
import powercrystals.minefactoryreloaded.block.BlockRailCargoPickup;
import powercrystals.minefactoryreloaded.block.BlockRailPassengerDropoff;
import powercrystals.minefactoryreloaded.block.BlockRailPassengerPickup;
import powercrystals.minefactoryreloaded.block.BlockRedNetPanel;
import powercrystals.minefactoryreloaded.block.BlockRedNetLogic;
import powercrystals.minefactoryreloaded.block.BlockRedNetCable;
import powercrystals.minefactoryreloaded.block.BlockRubberLeaves;
import powercrystals.minefactoryreloaded.block.BlockRubberSapling;
import powercrystals.minefactoryreloaded.block.BlockRubberWood;
import powercrystals.minefactoryreloaded.block.BlockVanillaGlassPane;
import powercrystals.minefactoryreloaded.block.BlockVanillaIce;
import powercrystals.minefactoryreloaded.block.BlockVineScaffold;
import powercrystals.minefactoryreloaded.block.ItemBlockConveyor;
import powercrystals.minefactoryreloaded.block.ItemBlockDecorativeStone;
import powercrystals.minefactoryreloaded.block.ItemBlockFactoryDecorativeBrick;
import powercrystals.minefactoryreloaded.block.ItemBlockFactoryGlass;
import powercrystals.minefactoryreloaded.block.ItemBlockFactoryGlassPane;
import powercrystals.minefactoryreloaded.block.ItemBlockFactoryMachine;
import powercrystals.minefactoryreloaded.block.ItemBlockFactoryRoad;
import powercrystals.minefactoryreloaded.block.ItemBlockRedNetLogic;
import powercrystals.minefactoryreloaded.block.ItemBlockRedNetPanel;
import powercrystals.minefactoryreloaded.block.ItemBlockVanillaIce;
import powercrystals.minefactoryreloaded.block.ItemBlockVineScaffold;
import powercrystals.minefactoryreloaded.entity.EntityNeedle;
import powercrystals.minefactoryreloaded.entity.EntityPinkSlime;
import powercrystals.minefactoryreloaded.entity.EntitySafariNet;
import powercrystals.minefactoryreloaded.gui.MFRGUIHandler;
import powercrystals.minefactoryreloaded.item.ItemCeramicDye;
import powercrystals.minefactoryreloaded.item.ItemFactory;
import powercrystals.minefactoryreloaded.item.ItemFactoryBucket;
import powercrystals.minefactoryreloaded.item.ItemFactoryCup;
import powercrystals.minefactoryreloaded.item.ItemFactoryFood;
import powercrystals.minefactoryreloaded.item.ItemFactoryHammer;
import powercrystals.minefactoryreloaded.item.ItemLaserFocus;
import powercrystals.minefactoryreloaded.item.ItemLogicUpgradeCard;
import powercrystals.minefactoryreloaded.item.ItemMilkBottle;
import powercrystals.minefactoryreloaded.item.ItemNeedleGun;
import powercrystals.minefactoryreloaded.item.ItemNeedlegunAmmoBlock;
import powercrystals.minefactoryreloaded.item.ItemNeedlegunAmmoFire;
import powercrystals.minefactoryreloaded.item.ItemNeedlegunAmmoStandard;
import powercrystals.minefactoryreloaded.item.ItemPortaSpawner;
import powercrystals.minefactoryreloaded.item.ItemRedNetMemoryCard;
import powercrystals.minefactoryreloaded.item.ItemRedNetMeter;
import powercrystals.minefactoryreloaded.item.ItemRuler;
import powercrystals.minefactoryreloaded.item.ItemSafariNet;
import powercrystals.minefactoryreloaded.item.ItemSafariNetLauncher;
import powercrystals.minefactoryreloaded.item.ItemSpyglass;
import powercrystals.minefactoryreloaded.item.ItemStraw;
import powercrystals.minefactoryreloaded.item.ItemSyringeCure;
import powercrystals.minefactoryreloaded.item.ItemSyringeGrowth;
import powercrystals.minefactoryreloaded.item.ItemSyringeHealth;
import powercrystals.minefactoryreloaded.item.ItemSyringeSlime;
import powercrystals.minefactoryreloaded.item.ItemSyringeZombie;
import powercrystals.minefactoryreloaded.item.ItemUpgrade;
import powercrystals.minefactoryreloaded.item.ItemXpExtractor;
import powercrystals.minefactoryreloaded.net.ClientPacketHandler;
import powercrystals.minefactoryreloaded.net.ConnectionHandler;
import powercrystals.minefactoryreloaded.net.IMFRProxy;
import powercrystals.minefactoryreloaded.net.ServerPacketHandler;
import powercrystals.minefactoryreloaded.setup.BehaviorDispenseSafariNet;
import powercrystals.minefactoryreloaded.setup.MFRConfig;
import powercrystals.minefactoryreloaded.setup.MineFactoryReloadedFuelHandler;
import powercrystals.minefactoryreloaded.setup.MineFactoryReloadedWorldGen;
import powercrystals.minefactoryreloaded.setup.recipe.GregTech;
import powercrystals.minefactoryreloaded.setup.recipe.ThermalExpansion;
import powercrystals.minefactoryreloaded.setup.recipe.Vanilla;
import powercrystals.minefactoryreloaded.setup.village.VillageCreationHandler;
import powercrystals.minefactoryreloaded.setup.village.VillageTradeHandler;
import powercrystals.minefactoryreloaded.tile.conveyor.TileEntityConveyor;
import powercrystals.minefactoryreloaded.tile.rednet.TileEntityRedNetHistorian;
import powercrystals.minefactoryreloaded.tile.rednet.TileEntityRedNetLogic;
import powercrystals.minefactoryreloaded.tile.rednet.TileEntityRedNetCable;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = MineFactoryReloadedCore.modId, name = MineFactoryReloadedCore.modName, version = MineFactoryReloadedCore.version,
dependencies = "after:BuildCraft|Core;after:BuildCraft|Factory;after:BuildCraft|Energy;after:BuildCraft|Builders;after:BuildCraft|Transport;after:IC2;required-after:PowerCrystalsCore")
@NetworkMod(clientSideRequired = true, serverSideRequired = false,
clientPacketHandlerSpec = @SidedPacketHandler(channels = { MineFactoryReloadedCore.modNetworkChannel }, packetHandler = ClientPacketHandler.class),
serverPacketHandlerSpec = @SidedPacketHandler(channels = { MineFactoryReloadedCore.modNetworkChannel }, packetHandler = ServerPacketHandler.class),
connectionHandler = ConnectionHandler.class)
public class MineFactoryReloadedCore extends BaseMod
{
	@SidedProxy(clientSide = "powercrystals.minefactoryreloaded.net.ClientProxy", serverSide = "powercrystals.minefactoryreloaded.net.CommonProxy")
	public static IMFRProxy proxy;
	
	public static final String modId = "MineFactoryReloaded";
	public static final String modNetworkChannel = "MFReloaded";
	public static final String version = "1.5.1R2.7.0B2";
	public static final String modName = "Minefactory Reloaded";
	
	public static final String guiFolder = "/powercrystals/minefactoryreloaded/textures/gui/";
	public static final String villagerFolder = "/powercrystals/minefactoryreloaded/textures/villager/";
	public static final String tileEntityFolder = "/powercrystals/minefactoryreloaded/textures/tileentity/";
	public static final String mobTextureFolder = "/textures/mob/powercrystals/minefactoryreloaded/";
	
	public static int renderIdConveyor = 1000;
	public static int renderIdFactoryGlassPane = 1001;
	public static int renderIdRedstoneCable = 1002;
	public static int renderIdFluidClassic = 1003;
	public static int renderIdRedNetLogic = 1004;
	public static int renderIdVineScaffold = 1005;
	public static int renderIdRedNetPanel = 1006;
	public static int renderIdFactoryGlass = 1007;
	
	public static Map<Integer, Block> machineBlocks = new HashMap<Integer, Block>();
	
	public static Block conveyorBlock;
	
	public static Block factoryGlassBlock;
	public static Block factoryGlassPaneBlock;
	public static Block factoryRoadBlock;
	public static Block factoryDecorativeBrickBlock;
	public static Block factoryDecorativeStoneBlock;
	
	public static Block rubberWoodBlock;
	public static Block rubberLeavesBlock;
	public static Block rubberSaplingBlock;
	
	public static Block railPickupCargoBlock;
	public static Block railDropoffCargoBlock;
	public static Block railPickupPassengerBlock;
	public static Block railDropoffPassengerBlock;
	
	public static BlockRedNetCable rednetCableBlock;
	public static BlockRedNetLogic rednetLogicBlock;
	public static BlockRedNetPanel rednetPanelBlock;
	
	public static BlockFactoryFluid milkLiquid;
	public static BlockFactoryFluid sludgeLiquid;
	public static BlockFactoryFluid sewageLiquid;
	public static BlockFactoryFluid essenceLiquid;
	public static BlockFactoryFluid biofuelLiquid;
	public static BlockFactoryFluid meatLiquid;
	public static BlockFactoryFluid pinkSlimeLiquid;
	public static BlockFactoryFluid chocolateMilkLiquid;
	public static BlockFactoryFluid mushroomSoupLiquid;
	
	public static Block fakeLaserBlock;

	public static Block vineScaffoldBlock;

	public static Item machineItem;

	public static Item factoryHammerItem;
	public static Item fertilizerItem;
	public static Item plasticSheetItem;
	public static Item rubberBarItem;
	public static Item rawPlasticItem;
	public static Item sewageBucketItem;
	public static Item sludgeBucketItem;
	public static Item mobEssenceBucketItem;
	public static Item syringeEmptyItem;
	public static Item syringeHealthItem;
	public static Item syringeGrowthItem;
	public static Item rawRubberItem;
	public static Item machineBaseItem;
	public static Item safariNetItem;
	public static Item ceramicDyeItem;
	public static Item blankRecordItem;
	public static Item syringeZombieItem;
	public static Item safariNetSingleItem;
	public static Item bioFuelBucketItem;
	public static Item upgradeItem;
	public static Item safariNetLauncherItem;
	public static Item sugarCharcoalItem;
	public static Item milkBottleItem;
	public static Item spyglassItem;
	public static Item portaSpawnerItem;
	public static Item strawItem;
	public static Item xpExtractorItem;
	public static Item syringeSlimeItem;
	public static Item syringeCureItem;
	public static Item logicCardItem;
	public static Item rednetMeterItem;
	public static Item rednetMemoryCardItem;
	public static Item rulerItem;
	public static Item meatIngotRawItem;
	public static Item meatIngotCookedItem;
	public static Item meatNuggetRawItem;
	public static Item meatNuggetCookedItem;
	public static Item meatBucketItem;
	public static Item pinkSlimeBucketItem;
	public static Item pinkSlimeballItem;
	public static Item safariNetJailerItem;
	public static Item laserFocusItem;
	public static Item chocolateMilkBucketItem;
	public static Item mushroomSoupBucketItem;
	public static Item needlegunItem;
	public static Item needlegunAmmoEmptyItem;
	public static Item needlegunAmmoStandardItem;
	public static Item needlegunAmmoLavaItem;
	public static Item needlegunAmmoSludgeItem;
	public static Item needlegunAmmoSewageItem;
	public static Item needlegunAmmoFireItem;
	public static Item needlegunAmmoAnvilItem;
	
	public static ItemFactoryCup plasticCup;

	private static MineFactoryReloadedCore instance;

	public static MineFactoryReloadedCore instance()
	{
		return instance;
	}
	
	private static int itemOffset;
	public static int getItemOffset()
	{
		return itemOffset;
	}

	@PreInit
	public void preInit(FMLPreInitializationEvent evt)
	{
		setConfigFolderBase(evt.getModConfigurationDirectory());
		
		MFRConfig.loadCommonConfig(getCommonConfig());
		MFRConfig.loadClientConfig(getClientConfig());
		
		extractLang(new String[] { "en_US", "es_AR", "es_ES", "es_MX", "es_UY", "es_VE", "zh_CN", "zh_TW", "ru_RU", "ko_KR", "de_DE" });
		loadLang();
		
		milkLiquid = new BlockFactoryFluid(MFRConfig.milkStillBlockId.getInt(), "milk");
		sludgeLiquid = new BlockFactoryFluid(MFRConfig.sludgeStillBlockId.getInt(), "sludge");
		sewageLiquid = new BlockFactoryFluid(MFRConfig.sewageStillBlockId.getInt(), "sewage");
		essenceLiquid = new BlockFactoryFluid(MFRConfig.essenceStillBlockId.getInt(), "essence");
		biofuelLiquid = new BlockFactoryFluid(MFRConfig.biofuelStillBlockId.getInt(), "biofuel");
		meatLiquid = new BlockFactoryFluid(MFRConfig.meatStillBlockId.getInt(), "meat");
		pinkSlimeLiquid = new BlockPinkSlimeFluid(MFRConfig.pinkslimeStillBlockId.getInt(), "pinkslime");
		chocolateMilkLiquid = new BlockFactoryFluid(MFRConfig.chocolateMilkStillBlockId.getInt(), "chocolatemilk");
		mushroomSoupLiquid = new BlockFactoryFluid(MFRConfig.mushroomSoupStillBlockId.getInt(), "mushroomsoup");

		sewageBucketItem = (new ItemFactoryBucket(MFRConfig.sewageBucketItemId.getInt(), sewageLiquid.blockID)).setUnlocalizedName("mfr.bucket.sewage").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		sludgeBucketItem = (new ItemFactoryBucket(MFRConfig.sludgeBucketItemId.getInt(), sludgeLiquid.blockID)).setUnlocalizedName("mfr.bucket.sludge").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		mobEssenceBucketItem = (new ItemFactoryBucket(MFRConfig.mobEssenceBucketItemId.getInt(), essenceLiquid.blockID)).setUnlocalizedName("mfr.bucket.essence").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		bioFuelBucketItem = (new ItemFactoryBucket(MFRConfig.bioFuelBucketItemId.getInt(), biofuelLiquid.blockID)).setUnlocalizedName("mfr.bucket.biofuel").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		meatBucketItem = (new ItemFactoryBucket(MFRConfig.meatBucketItemId.getInt(), meatLiquid.blockID)).setUnlocalizedName("mfr.bucket.meat").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		pinkSlimeBucketItem = (new ItemFactoryBucket(MFRConfig.pinkSlimeBucketItemId.getInt(), pinkSlimeLiquid.blockID)).setUnlocalizedName("mfr.bucket.pinkslime").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		chocolateMilkBucketItem = (new ItemFactoryBucket(MFRConfig.chocolateMilkBucketItemId.getInt(), chocolateMilkLiquid.blockID)).setUnlocalizedName("mfr.bucket.chocolatemilk").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		mushroomSoupBucketItem = (new ItemFactoryBucket(MFRConfig.mushroomSoupBucketItemId.getInt(), mushroomSoupLiquid.blockID)).setUnlocalizedName("mfr.bucket.mushroomsoup").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		
		itemOffset = sewageBucketItem.itemID - MFRConfig.sewageBucketItemId.getInt();
		
		if(MFRConfig.vanillaOverrideMilkBucket.getBoolean(true))
		{
			int milkBucketId = Item.bucketMilk.itemID;
			Item.itemsList[milkBucketId] = null;
			Item.bucketMilk = new ItemFactoryBucket(milkBucketId - itemOffset, milkLiquid.blockID).setUnlocalizedName("mfr.bucket.milk").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		}
	}

	@Init
	public void init(FMLInitializationEvent evt)
	{
		instance = this;
		
		float meatNuggetSaturation = MFRConfig.meatSaturation.getBoolean(false) ? 0.1F : 0.2F;
		float meatIngotSaturation = MFRConfig.meatSaturation.getBoolean(false) ? 0.2F : 0.8F;

		conveyorBlock = new BlockConveyor(MFRConfig.conveyorBlockId.getInt());
		machineBlocks.put(0, new BlockFactoryMachine(MFRConfig.machineBlock0Id.getInt(), 0));
		machineBlocks.put(1, new BlockFactoryMachine(MFRConfig.machineBlock1Id.getInt(), 1));
		machineBlocks.put(2, new BlockFactoryMachine(MFRConfig.machineBlock2Id.getInt(), 2));
		factoryGlassBlock = new BlockFactoryGlass(MFRConfig.factoryGlassBlockId.getInt());
		factoryGlassPaneBlock = new BlockFactoryGlassPane(MFRConfig.factoryGlassPaneBlockId.getInt());
		factoryRoadBlock = new BlockFactoryRoad(MFRConfig.factoryRoadBlockId.getInt());
		factoryDecorativeBrickBlock = new BlockFactoryDecorativeBricks(MFRConfig.factoryDecorativeBrickBlockId.getInt());
		factoryDecorativeStoneBlock = new BlockDecorativeStone(MFRConfig.factoryDecorativeStoneBlockId.getInt());
		rubberWoodBlock = new BlockRubberWood(MFRConfig.rubberWoodBlockId.getInt());
		rubberLeavesBlock = new BlockRubberLeaves(MFRConfig.rubberLeavesBlockId.getInt());
		rubberSaplingBlock = new BlockRubberSapling(MFRConfig.rubberSaplingBlockId.getInt());
		railDropoffCargoBlock = new BlockRailCargoDropoff(MFRConfig.railDropoffCargoBlockId.getInt());
		railPickupCargoBlock = new BlockRailCargoPickup(MFRConfig.railPickupCargoBlockId.getInt());
		railDropoffPassengerBlock = new BlockRailPassengerDropoff(MFRConfig.railDropoffPassengerBlockId.getInt());
		railPickupPassengerBlock = new BlockRailPassengerPickup(MFRConfig.railPickupPassengerBlockId.getInt());
		rednetCableBlock = new BlockRedNetCable(MFRConfig.rednetCableBlockId.getInt());
		rednetLogicBlock = new BlockRedNetLogic(MFRConfig.rednetLogicBlockId.getInt());
		rednetPanelBlock = new BlockRedNetPanel(MFRConfig.rednetPanelBlockId.getInt());
		fakeLaserBlock = new BlockFakeLaser(MFRConfig.fakeLaserBlockId.getInt());
		vineScaffoldBlock = new BlockVineScaffold(MFRConfig.vineScaffoldBlockId.getInt());

		factoryHammerItem = (new ItemFactoryHammer(MFRConfig.hammerItemId.getInt())).setUnlocalizedName("mfr.hammer").setMaxStackSize(1);
		fertilizerItem = (new ItemFactory(MFRConfig.fertilizerItemId.getInt())).setUnlocalizedName("mfr.fertilizer");
		plasticSheetItem = (new ItemFactory(MFRConfig.plasticSheetItemId.getInt())).setUnlocalizedName("mfr.plastic.sheet");
		rawPlasticItem = (new ItemFactory(MFRConfig.rawPlasticItemId.getInt())).setUnlocalizedName("mfr.plastic.raw");
		rubberBarItem = (new ItemFactory(MFRConfig.rubberBarItemId.getInt())).setUnlocalizedName("mfr.rubber.bar");
		syringeEmptyItem = (new ItemFactory(MFRConfig.syringeEmptyItemId.getInt())).setUnlocalizedName("mfr.syringe.empty");
		syringeHealthItem = (new ItemSyringeHealth()).setUnlocalizedName("mfr.syringe.health").setContainerItem(syringeEmptyItem);
		syringeGrowthItem = (new ItemSyringeGrowth()).setUnlocalizedName("mfr.syringe.growth").setContainerItem(syringeEmptyItem);
		rawRubberItem = (new ItemFactory(MFRConfig.rawRubberItemId.getInt())).setUnlocalizedName("mfr.rubber.raw");
		machineBaseItem = (new ItemFactory(MFRConfig.machineBaseItemId.getInt())).setUnlocalizedName("mfr.machineblock");
		safariNetItem = (new ItemSafariNet(MFRConfig.safariNetItemId.getInt())).setUnlocalizedName("mfr.safarinet.reusable");
		ceramicDyeItem = (new ItemCeramicDye(MFRConfig.ceramicDyeItemId.getInt())).setUnlocalizedName("mfr.ceramicdye");
		blankRecordItem = (new ItemFactory(MFRConfig.blankRecordId.getInt())).setUnlocalizedName("mfr.record.blank").setMaxStackSize(1);
		syringeZombieItem = (new ItemSyringeZombie()).setUnlocalizedName("mfr.syringe.zombie").setContainerItem(syringeEmptyItem);
		safariNetSingleItem = (new ItemSafariNet(MFRConfig.safariNetSingleItemId.getInt())).setUnlocalizedName("mfr.safarinet.singleuse");
		upgradeItem = (new ItemUpgrade(MFRConfig.upgradeItemId.getInt())).setUnlocalizedName("mfr.upgrade.radius").setMaxStackSize(1);
		safariNetLauncherItem = (new ItemSafariNetLauncher(MFRConfig.safariNetLauncherItemId.getInt())).setUnlocalizedName("mfr.safarinet.launcher").setMaxStackSize(1);
		sugarCharcoalItem = (new ItemFactory(MFRConfig.sugarCharcoalItemId.getInt())).setUnlocalizedName("mfr.sugarcharcoal");
		milkBottleItem = (new ItemMilkBottle(MFRConfig.milkBottleItemId.getInt())).setUnlocalizedName("mfr.milkbottle").setMaxStackSize(16);
		spyglassItem = (new ItemSpyglass(MFRConfig.spyglassItemId.getInt())).setUnlocalizedName("mfr.spyglass").setMaxStackSize(1);
		portaSpawnerItem = (new ItemPortaSpawner(MFRConfig.portaSpawnerItemId.getInt())).setUnlocalizedName("mfr.portaspawner").setMaxStackSize(1);
		strawItem = (new ItemStraw(MFRConfig.strawItemId.getInt())).setUnlocalizedName("mfr.straw").setMaxStackSize(1);
		xpExtractorItem = (new ItemXpExtractor(MFRConfig.xpExtractorItemId.getInt())).setUnlocalizedName("mfr.xpextractor").setMaxStackSize(1);
		syringeSlimeItem = (new ItemSyringeSlime(MFRConfig.syringeSlimeItemId.getInt())).setUnlocalizedName("mfr.syringe.slime").setContainerItem(syringeEmptyItem);
		syringeCureItem = (new ItemSyringeCure(MFRConfig.syringeCureItemId.getInt())).setUnlocalizedName("mfr.syringe.cure").setContainerItem(syringeEmptyItem);
		logicCardItem = (new ItemLogicUpgradeCard(MFRConfig.logicCardItemId.getInt())).setUnlocalizedName("mfr.upgrade.logic").setMaxStackSize(1);
		rednetMeterItem = (new ItemRedNetMeter(MFRConfig.rednetMeterItemId.getInt())).setUnlocalizedName("mfr.rednet.meter").setMaxStackSize(1);
		rednetMemoryCardItem = (new ItemRedNetMemoryCard(MFRConfig.rednetMemoryCardItemId.getInt())).setUnlocalizedName("mfr.rednet.memorycard").setMaxStackSize(1);
		rulerItem = (new ItemRuler(MFRConfig.rulerItemId.getInt())).setUnlocalizedName("mfr.ruler").setMaxStackSize(1);
		meatIngotRawItem = (new ItemFactoryFood(MFRConfig.meatIngotRawItemId.getInt(), 4, meatIngotSaturation)).setUnlocalizedName("mfr.meat.ingot.raw");
		meatIngotCookedItem = (new ItemFactoryFood(MFRConfig.meatIngotCookedItemId.getInt(), 10, meatIngotSaturation)).setUnlocalizedName("mfr.meat.ingot.cooked");
		meatNuggetRawItem = (new ItemFactoryFood(MFRConfig.meatNuggetRawItemId.getInt(), 1, meatNuggetSaturation)).setUnlocalizedName("mfr.meat.nugget.raw");
		meatNuggetCookedItem = (new ItemFactoryFood(MFRConfig.meatNuggetCookedItemId.getInt(), 4, meatNuggetSaturation)).setUnlocalizedName("mfr.meat.nugget.cooked");
		pinkSlimeballItem = (new ItemFactory(MFRConfig.pinkSlimeballItemId.getInt())).setUnlocalizedName("mfr.pinkslimeball");
		safariNetJailerItem = (new ItemSafariNet(MFRConfig.safariNetJailerItemId.getInt())).setUnlocalizedName("mfr.safarinet.jailer");
		laserFocusItem = (new ItemLaserFocus(MFRConfig.laserFocusItemId.getInt())).setUnlocalizedName("mfr.laserfocus").setMaxStackSize(1);
		needlegunItem = (new ItemNeedleGun(MFRConfig.needlegunItemId.getInt())).setUnlocalizedName("mfr.needlegun").setMaxStackSize(1);
		needlegunAmmoEmptyItem = (new ItemFactory(MFRConfig.needlegunAmmoEmptyItemId.getInt())).setUnlocalizedName("mfr.needlegun.ammo.empty");
		needlegunAmmoStandardItem = (new ItemNeedlegunAmmoStandard(MFRConfig.needlegunAmmoStandardItemId.getInt())).setUnlocalizedName("mfr.needlegun.ammo.standard");
		needlegunAmmoLavaItem = (new ItemNeedlegunAmmoBlock(MFRConfig.needlegunAmmoLavaItemId.getInt(), Block.lavaMoving.blockID, 3)).setUnlocalizedName("mfr.needlegun.ammo.lava");
		needlegunAmmoSludgeItem = (new ItemNeedlegunAmmoBlock(MFRConfig.needlegunAmmoSludgeItemId.getInt(), sludgeLiquid.blockID, 6)).setUnlocalizedName("mfr.needlegun.ammo.sludge");
		needlegunAmmoSewageItem = (new ItemNeedlegunAmmoBlock(MFRConfig.needlegunAmmoSewageItemId.getInt(), sewageLiquid.blockID, 6)).setUnlocalizedName("mfr.needlegun.ammo.sewage");
		needlegunAmmoFireItem = (new ItemNeedlegunAmmoFire(MFRConfig.needlegunAmmoFireItemId.getInt())).setUnlocalizedName("mfr.needlegun.ammo.fire");
		needlegunAmmoAnvilItem = (new ItemNeedlegunAmmoBlock(MFRConfig.needlegunAmmoAnvilItemId.getInt(), Block.anvil.blockID, 2)).setUnlocalizedName("mfr.needlegun.ammo.anvil").setMaxDamage(0);
		plasticCup = (ItemFactoryCup)new ItemFactoryCup(MFRConfig.plasticCupItemId.getInt(), 64, 16).setUnlocalizedName("mfr.bucket.plasticcup");

		for(Entry<Integer, Block> machine : machineBlocks.entrySet())
		{
			GameRegistry.registerBlock(machine.getValue(), ItemBlockFactoryMachine.class, machine.getValue().getUnlocalizedName());
		}
		
		GameRegistry.registerBlock(conveyorBlock, ItemBlockConveyor.class, conveyorBlock.getUnlocalizedName());
		GameRegistry.registerBlock(factoryGlassBlock, ItemBlockFactoryGlass.class, factoryGlassBlock.getUnlocalizedName());
		GameRegistry.registerBlock(factoryGlassPaneBlock, ItemBlockFactoryGlassPane.class, factoryGlassPaneBlock.getUnlocalizedName());
		GameRegistry.registerBlock(factoryRoadBlock, ItemBlockFactoryRoad.class, factoryRoadBlock.getUnlocalizedName());
		GameRegistry.registerBlock(factoryDecorativeBrickBlock, ItemBlockFactoryDecorativeBrick.class, factoryDecorativeBrickBlock.getUnlocalizedName());
		GameRegistry.registerBlock(factoryDecorativeStoneBlock, ItemBlockDecorativeStone.class, factoryDecorativeStoneBlock.getUnlocalizedName());
		GameRegistry.registerBlock(rubberWoodBlock, rubberWoodBlock.getUnlocalizedName());
		GameRegistry.registerBlock(rubberLeavesBlock, rubberLeavesBlock.getUnlocalizedName());
		GameRegistry.registerBlock(rubberSaplingBlock, rubberSaplingBlock.getUnlocalizedName());
		GameRegistry.registerBlock(railPickupCargoBlock, railPickupCargoBlock.getUnlocalizedName());
		GameRegistry.registerBlock(railDropoffCargoBlock, railDropoffCargoBlock.getUnlocalizedName());
		GameRegistry.registerBlock(railPickupPassengerBlock, railPickupPassengerBlock.getUnlocalizedName());
		GameRegistry.registerBlock(railDropoffPassengerBlock, railDropoffPassengerBlock.getUnlocalizedName());
		GameRegistry.registerBlock(rednetCableBlock, rednetCableBlock.getUnlocalizedName());
		GameRegistry.registerBlock(rednetLogicBlock, ItemBlockRedNetLogic.class, rednetLogicBlock.getUnlocalizedName());
		GameRegistry.registerBlock(rednetPanelBlock, ItemBlockRedNetPanel.class, rednetPanelBlock.getUnlocalizedName());
		GameRegistry.registerBlock(vineScaffoldBlock, ItemBlockVineScaffold.class, vineScaffoldBlock.getUnlocalizedName());
		
		GameRegistry.registerBlock(milkLiquid, milkLiquid.getUnlocalizedName());
		GameRegistry.registerBlock(sludgeLiquid, sludgeLiquid.getUnlocalizedName());
		GameRegistry.registerBlock(sewageLiquid, sewageLiquid.getUnlocalizedName());
		GameRegistry.registerBlock(essenceLiquid, essenceLiquid.getUnlocalizedName());
		GameRegistry.registerBlock(biofuelLiquid, biofuelLiquid.getUnlocalizedName());
		GameRegistry.registerBlock(meatLiquid, meatLiquid.getUnlocalizedName());
		GameRegistry.registerBlock(pinkSlimeLiquid, pinkSlimeLiquid.getUnlocalizedName());
		GameRegistry.registerBlock(chocolateMilkLiquid, chocolateMilkLiquid.getUnlocalizedName());
		GameRegistry.registerBlock(mushroomSoupLiquid, mushroomSoupLiquid.getUnlocalizedName());
		
		Block.setBurnProperties(rubberWoodBlock.blockID, 4, 20);
		Block.setBurnProperties(rubberLeavesBlock.blockID, 30, 20);
		
		MinecraftForge.setBlockHarvestLevel(MineFactoryReloadedCore.rednetCableBlock, 0, "pickaxe", 0);
		
		if(MFRConfig.vanillaOverrideGlassPane.getBoolean(true))
		{
			Block.blocksList[Block.thinGlass.blockID] = null;
			Item.itemsList[Block.thinGlass.blockID] = null;
			Block.thinGlass = new BlockVanillaGlassPane();
			GameRegistry.registerBlock(Block.thinGlass, Block.thinGlass.getUnlocalizedName());
		}
		if(MFRConfig.vanillaOverrideIce.getBoolean(true))
		{
			Block.blocksList[Block.ice.blockID] = null;
			Item.itemsList[Block.ice.blockID] = null;
			Block.ice = new BlockVanillaIce();
			GameRegistry.registerBlock(Block.ice, ItemBlockVanillaIce.class, "blockVanillaIce");
		}

		GameRegistry.registerTileEntity(TileEntityConveyor.class, "factoryConveyor");
		GameRegistry.registerTileEntity(TileEntityRedNetCable.class, "factoryRedstoneCable");
		GameRegistry.registerTileEntity(TileEntityRedNetLogic.class, "factoryRednetLogic");
		GameRegistry.registerTileEntity(TileEntityRedNetHistorian.class, "factoryRednetHistorian");
		
		EntityRegistry.registerModEntity(EntitySafariNet.class, "entitySafariNet", 0, instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityPinkSlime.class, "mfrEntityPinkSlime", 1, instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityNeedle.class, "mfrEntityNeedle", 2, instance, 160, 5, true);

		MinecraftForge.EVENT_BUS.register(instance);
		MinecraftForge.EVENT_BUS.register(proxy);
		
		OreDictionary.registerOre("itemRubber", MineFactoryReloadedCore.rubberBarItem);
		OreDictionary.registerOre("woodRubber", MineFactoryReloadedCore.rubberWoodBlock);
		OreDictionary.registerOre("sheetPlastic", MineFactoryReloadedCore.plasticSheetItem);
		OreDictionary.registerOre("dustPlastic", MineFactoryReloadedCore.rawPlasticItem);
		OreDictionary.registerOre("ingotMeat", MineFactoryReloadedCore.meatIngotCookedItem);
		OreDictionary.registerOre("ingotMeatRaw", MineFactoryReloadedCore.meatIngotRawItem);
		OreDictionary.registerOre("nuggetMeat", MineFactoryReloadedCore.meatNuggetCookedItem);
		OreDictionary.registerOre("nuggetMeatRaw", MineFactoryReloadedCore.meatNuggetRawItem);
		OreDictionary.registerOre("itemCharcoalSugar", MineFactoryReloadedCore.sugarCharcoalItem);
				
		GameRegistry.registerFuelHandler(new MineFactoryReloadedFuelHandler());
		
		proxy.init();
		
		NetworkRegistry.instance().registerGuiHandler(this, new MFRGUIHandler());
		
		BlockDispenser.dispenseBehaviorRegistry.putObject(safariNetItem, new BehaviorDispenseSafariNet());
		BlockDispenser.dispenseBehaviorRegistry.putObject(safariNetSingleItem, new BehaviorDispenseSafariNet());
		
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(safariNetSingleItem), 1, 1, 25));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(safariNetSingleItem), 1, 1, 25));
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(safariNetSingleItem), 1, 1, 25));
		
		VillagerRegistry.instance().registerVillageCreationHandler(new VillageCreationHandler());
		VillagerRegistry.instance().registerVillagerType(MFRConfig.zoolologistEntityId.getInt(), villagerFolder + "zoologist.png");
		VillagerRegistry.instance().registerVillageTradeHandler(MFRConfig.zoolologistEntityId.getInt(), new VillageTradeHandler());
		
		GameRegistry.registerWorldGenerator(new MineFactoryReloadedWorldGen());
		
		TickRegistry.registerScheduledTickHandler(new UpdateManager(this), Side.CLIENT);
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent evt)
	{
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("milk", new LiquidStack(milkLiquid, LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(Item.bucketMilk), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("sludge", new LiquidStack(sludgeLiquid, LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(sludgeBucketItem), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("sewage", new LiquidStack(sewageLiquid, LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(sewageBucketItem), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("mobEssence", new LiquidStack(essenceLiquid, LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(mobEssenceBucketItem), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("biofuel", new LiquidStack(biofuelLiquid, LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(bioFuelBucketItem), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("meat", new LiquidStack(meatLiquid, LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(meatBucketItem), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("pinkslime", new LiquidStack(pinkSlimeLiquid, LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(pinkSlimeBucketItem), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("chocolatemilk", new LiquidStack(chocolateMilkLiquid, LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(chocolateMilkBucketItem), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("mushroomsoup", new LiquidStack(mushroomSoupLiquid, LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(mushroomSoupBucketItem), new ItemStack(Item.bucketEmpty)));
		
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("mushroomsoup", new LiquidStack(mushroomSoupLiquid, LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(Item.bowlSoup), new ItemStack(Item.bowlEmpty)));
		
		for(ItemStack s : OreDictionary.getOres("itemRubber"))
		{
			FurnaceRecipes.smelting().addSmelting(s.itemID, s.getItemDamage(), new ItemStack(rawPlasticItem), 0.3F);
		}
		
		FurnaceRecipes.smelting().addSmelting(Item.sugar.itemID, new ItemStack(sugarCharcoalItem), 0.1F);
		FurnaceRecipes.smelting().addSmelting(meatIngotRawItem.itemID, new ItemStack(meatIngotCookedItem), 0.5F);
		FurnaceRecipes.smelting().addSmelting(meatNuggetRawItem.itemID, new ItemStack(meatNuggetCookedItem), 0.3F);
		
		String[] biomeWhitelist = MFRConfig.rubberTreeBiomeWhitelist.getString().split(",");
		for(String biome : biomeWhitelist)
		{
			MFRRegistry.registerRubberTreeBiome(biome);
		}
		
		String[] biomeBlacklist = MFRConfig.rubberTreeBiomeBlacklist.getString().split(",");
		for(String biome : biomeBlacklist)
		{
			MFRRegistry.getRubberTreeBiomes().remove(biome);
		}
		
		if(MFRConfig.vanillaRecipes.getBoolean(true))
		{
			new Vanilla().registerRecipes();
		}
		
		if(MFRConfig.thermalExpansionRecipes.getBoolean(false))
		{
			new ThermalExpansion().registerRecipes();
		}
		
		if(MFRConfig.gregTechRecipes.getBoolean(false))
		{
			new GregTech().registerRecipes();
		}
		
		for(int i = 0; i < 14; i++)
		{
			FacadeManager.addFacade(new ItemStack(factoryDecorativeBrickBlock.blockID, 1, i));
		}
		
		for(int i = 0; i < 12; i++)
		{
			FacadeManager.addFacade(new ItemStack(factoryDecorativeStoneBlock.blockID, 1, i));
		}
		
		FacadeManager.addFacade(new ItemStack(factoryRoadBlock.blockID, 1, 0));
		FacadeManager.addFacade(new ItemStack(factoryRoadBlock.blockID, 1, 1));
		FacadeManager.addFacade(new ItemStack(factoryRoadBlock.blockID, 1, 4));
	}
	
	@ForgeSubscribe
	public void onBonemeal(BonemealEvent e)
	{
		if(!e.world.isRemote && e.world.getBlockId(e.X, e.Y, e.Z) == MineFactoryReloadedCore.rubberSaplingBlock.blockID)
		{
			((BlockRubberSapling)MineFactoryReloadedCore.rubberSaplingBlock).growTree(e.world, e.X, e.Y, e.Z, e.world.rand);
			e.setResult(Result.ALLOW);
		}
	}
	
	@ForgeSubscribe
	public void onBucketFill(FillBucketEvent e)
	{
		if(e.current.itemID != Item.bucketEmpty.itemID)
		{
			return;
		}
		ItemStack filledBucket = fillBucket(e.world, e.target);
		if(filledBucket != null)
		{
			e.world.setBlockToAir(e.target.blockX, e.target.blockY, e.target.blockZ);
			e.result = filledBucket;
			e.setResult(Result.ALLOW);
		}
	}
	
	private ItemStack fillBucket(World world, MovingObjectPosition block)
	{
		int blockId = world.getBlockId(block.blockX, block.blockY, block.blockZ);
		if(blockId == milkLiquid.blockID) return new ItemStack(Item.bucketMilk);
		else if(blockId == sludgeLiquid.blockID) return new ItemStack(sludgeBucketItem);
		else if(blockId == sewageLiquid.blockID) return new ItemStack(sewageBucketItem);
		else if(blockId == essenceLiquid.blockID) return new ItemStack(mobEssenceBucketItem);
		else if(blockId == biofuelLiquid.blockID) return new ItemStack(bioFuelBucketItem);
		else if(blockId == meatLiquid.blockID) return new ItemStack(meatBucketItem);
		else if(blockId == pinkSlimeLiquid.blockID) return new ItemStack(pinkSlimeBucketItem);
		else if(blockId == chocolateMilkLiquid.blockID) return new ItemStack(chocolateMilkBucketItem);
		else if(blockId == mushroomSoupLiquid.blockID) return new ItemStack(mushroomSoupBucketItem);
		else return null;
	}
	
	@Override
	public String getModId()
	{
		return modId;
	}

	@Override
	public String getModName()
	{
		return modName;
	}

	@Override
	public String getModVersion()
	{
		return version;
	}
}
