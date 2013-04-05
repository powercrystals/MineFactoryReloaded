package powercrystals.minefactoryreloaded;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
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
import powercrystals.core.updater.IUpdateableMod;
import powercrystals.core.updater.UpdateManager;
import powercrystals.minefactoryreloaded.block.BlockConveyor;
import powercrystals.minefactoryreloaded.block.BlockFactoryDecorativeBricks;
import powercrystals.minefactoryreloaded.block.BlockFactoryGlass;
import powercrystals.minefactoryreloaded.block.BlockFactoryGlassPane;
import powercrystals.minefactoryreloaded.block.BlockFactoryLiquidFlowing;
import powercrystals.minefactoryreloaded.block.BlockFactoryLiquidStill;
import powercrystals.minefactoryreloaded.block.BlockFactoryMachine;
import powercrystals.minefactoryreloaded.block.BlockFactoryRoad;
import powercrystals.minefactoryreloaded.block.BlockRailCargoDropoff;
import powercrystals.minefactoryreloaded.block.BlockRailCargoPickup;
import powercrystals.minefactoryreloaded.block.BlockRailPassengerDropoff;
import powercrystals.minefactoryreloaded.block.BlockRailPassengerPickup;
import powercrystals.minefactoryreloaded.block.BlockRedstoneCable;
import powercrystals.minefactoryreloaded.block.BlockRubberLeaves;
import powercrystals.minefactoryreloaded.block.BlockRubberSapling;
import powercrystals.minefactoryreloaded.block.BlockRubberWood;
import powercrystals.minefactoryreloaded.block.BlockVanillaGlassPane;
import powercrystals.minefactoryreloaded.block.BlockVanillaIce;
import powercrystals.minefactoryreloaded.block.ItemBlockConveyor;
import powercrystals.minefactoryreloaded.block.ItemBlockFactoryDecorativeBrick;
import powercrystals.minefactoryreloaded.block.ItemBlockFactoryGlass;
import powercrystals.minefactoryreloaded.block.ItemBlockFactoryGlassPane;
import powercrystals.minefactoryreloaded.block.ItemBlockFactoryMachine;
import powercrystals.minefactoryreloaded.block.ItemBlockFactoryRoad;
import powercrystals.minefactoryreloaded.block.ItemBlockVanillaIce;
import powercrystals.minefactoryreloaded.entity.EntitySafariNet;
import powercrystals.minefactoryreloaded.gui.MFRGUIHandler;
import powercrystals.minefactoryreloaded.item.ItemCeramicDye;
import powercrystals.minefactoryreloaded.item.ItemFactory;
import powercrystals.minefactoryreloaded.item.ItemFactoryBucket;
import powercrystals.minefactoryreloaded.item.ItemFactoryHammer;
import powercrystals.minefactoryreloaded.item.ItemMilkBottle;
import powercrystals.minefactoryreloaded.item.ItemPortaSpawner;
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
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.setup.MineFactoryReloadedFuelHandler;
import powercrystals.minefactoryreloaded.setup.MineFactoryReloadedWorldGen;
import powercrystals.minefactoryreloaded.setup.recipe.Vanilla;
import powercrystals.minefactoryreloaded.setup.village.VillageCreationHandler;
import powercrystals.minefactoryreloaded.setup.village.VillageTradeHandler;
import powercrystals.minefactoryreloaded.tile.TileEntityConveyor;
import powercrystals.minefactoryreloaded.tile.TileRedstoneCable;

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
dependencies = "after:BuildCraft|Core;after:BuildCraft|Factory;after:BuildCraft|Energy;after:BuildCraft|Builders;after:BuildCraft|Transport;after:IC2;required-after:PowerCrystalsCore;before:Forestry")
@NetworkMod(clientSideRequired = true, serverSideRequired = false,
clientPacketHandlerSpec = @SidedPacketHandler(channels = { MineFactoryReloadedCore.modNetworkChannel }, packetHandler = ClientPacketHandler.class),
serverPacketHandlerSpec = @SidedPacketHandler(channels = { MineFactoryReloadedCore.modNetworkChannel }, packetHandler = ServerPacketHandler.class),
connectionHandler = ConnectionHandler.class)
public class MineFactoryReloadedCore extends BaseMod implements IUpdateableMod
{
	@SidedProxy(clientSide = "powercrystals.minefactoryreloaded.net.ClientProxy", serverSide = "powercrystals.minefactoryreloaded.net.CommonProxy")
	public static IMFRProxy proxy;
	
	public static final String modId = "MineFactoryReloaded";
	public static final String modNetworkChannel = "MFReloaded";
	public static final String version = "1.5.1R2.4.0B3";
	public static final String modName = "Minefactory Reloaded";
	
	public static final String guiFolder = "/powercrystals/minefactoryreloaded/textures/gui/";
	public static final String villagerFolder = "/powercrystals/minefactoryreloaded/textures/villager/";
	public static final String tileEntityFolder = "/powercrystals/minefactoryreloaded/textures/tileentity/";
	
	public static int renderIdConveyor = 1000;
	public static int renderIdFactoryGlassPane = 1001;
	public static int renderIdRedstoneCable = 1002;
	
	public static Map<Integer, Block> machineBlocks = new HashMap<Integer, Block>();
	
	public static Block conveyorBlock;
	
	public static Block factoryGlassBlock;
	public static Block factoryGlassPaneBlock;
	public static Block factoryRoadBlock;
	public static Block factoryDecorativeBrickBlock;
	
	public static Block rubberWoodBlock;
	public static Block rubberLeavesBlock;
	public static Block rubberSaplingBlock;
	
	public static Block railPickupCargoBlock;
	public static Block railDropoffCargoBlock;
	public static Block railPickupPassengerBlock;
	public static Block railDropoffPassengerBlock;
	
	public static BlockRedstoneCable rednetCableBlock;
	
	public static Block milkFlowing;
	public static Block milkStill;
	public static Block sludgeFlowing;
	public static Block sludgeStill;
	public static Block sewageFlowing;
	public static Block sewageStill;
	public static Block essenceFlowing;
	public static Block essenceStill;
	public static Block biofuelFlowing;
	public static Block biofuelStill;

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

	// client config
	public static Property spyglassRange;
	
	// common config
	public static Property machineBlock0Id;
	public static Property machineBlock1Id;
	
	public static Property conveyorBlockId;
	
	public static Property factoryGlassBlockId;
	public static Property factoryGlassPaneBlockId;
	public static Property factoryRoadBlockId;
	public static Property factoryDecorativeBrickBlockId;
	
	public static Property rubberWoodBlockId;
	public static Property rubberLeavesBlockId;
	public static Property rubberSaplingBlockId;
	
	public static Property railPickupCargoBlockId;
	public static Property railDropoffCargoBlockId;
	public static Property railPickupPassengerBlockId;
	public static Property railDropoffPassengerBlockId;
	
	public static Property rednetCableBlockId;
	
	public static Property milkFlowingBlockId;
	public static Property milkStillBlockId;
	public static Property sludgeFlowingBlockId;
	public static Property sludgeStillBlockId;
	public static Property sewageFlowingBlockId;
	public static Property sewageStillBlockId;
	public static Property essenceFlowingBlockId;
	public static Property essenceStillBlockId;
	public static Property biofuelFlowingBlockId;
	public static Property biofuelStillBlockId;

	public static Property hammerItemId;
	public static Property milkItemId;
	public static Property sludgeItemId;
	public static Property sewageItemId;
	public static Property mobEssenceItemId;
	public static Property fertilizerItemId;
	public static Property plasticSheetItemId;
	public static Property rawPlasticItemId;
	public static Property rubberBarItemId;
	public static Property sewageBucketItemId;
	public static Property sludgeBucketItemId;
	public static Property mobEssenceBucketItemId;
	public static Property syringeEmptyItemId;
	public static Property syringeHealthItemId;
	public static Property syringeGrowthItemId;
	public static Property rawRubberItemId;
	public static Property machineBaseItemId;
	public static Property safariNetItemId;
	public static Property ceramicDyeId;
	public static Property blankRecordId;
	public static Property syringeZombieId;
	public static Property safariNetSingleItemId;
	public static Property bioFuelItemId;
	public static Property bioFuelBucketItemId;
	public static Property upgradeItemId;
	public static Property safariNetLauncherItemId;
	public static Property sugarCharcoalItemId;
	public static Property milkBottleItemId;
	public static Property spyglassItemId;
	public static Property portaSpawnerItemId;
	public static Property strawItemId;
	public static Property xpExtractorItemId;
	public static Property syringeSlimeItemId;
	public static Property syringeCureItemId;

	public static Property zoolologistEntityId;
	
	public static Property treeSearchMaxVertical;
	public static Property treeSearchMaxHorizontal;
	public static Property verticalHarvestSearchMaxVertical;
	public static Property rubberTreeWorldGen;
	public static Property mfrLakeWorldGen;
	public static Property enableBonemealFertilizing;
	public static Property enableCheapDSU;
	public static Property enableMossyCobbleRecipe;
	public static Property conveyorCaptureNonItems;
	public static Property playSounds;
	
	public static Property vanillaOverrideGlassPane;
	public static Property vanillaOverrideIce;
	public static Property vanillaOverrideMilkBucket;
	
	public static Property enableCompatibleAutoEnchanter;
	public static Property enableSlipperyRoads;
	
	public static Property rubberTreeBiomeWhitelist;
	public static Property rubberTreeBiomeBlacklist;
	
	public static Property passengerRailSearchMaxHorizontal;
	public static Property passengerRailSearchMaxVertical;

	private static MineFactoryReloadedCore instance;

	public static MineFactoryReloadedCore instance()
	{
		return instance;
	}

	@PreInit
	public void preInit(FMLPreInitializationEvent evt)
	{
		setConfigFolderBase(evt.getModConfigurationDirectory());
		
		loadCommonConfig(getCommonConfig());
		loadClientConfig(getClientConfig());
		
		extractLang(new String[] { "en_US" });
		loadLang();
	}

	@Init
	public void init(FMLInitializationEvent evt)
	{
		instance = this;

		conveyorBlock = new BlockConveyor(conveyorBlockId.getInt());
		machineBlocks.put(0, new BlockFactoryMachine(machineBlock0Id.getInt(), 0));
		machineBlocks.put(1, new BlockFactoryMachine(machineBlock1Id.getInt(), 1));
		factoryGlassBlock = new BlockFactoryGlass(factoryGlassBlockId.getInt());
		factoryGlassPaneBlock = new BlockFactoryGlassPane(factoryGlassPaneBlockId.getInt());
		factoryRoadBlock = new BlockFactoryRoad(factoryRoadBlockId.getInt());
		factoryDecorativeBrickBlock = new BlockFactoryDecorativeBricks(factoryDecorativeBrickBlockId.getInt());
		rubberWoodBlock = new BlockRubberWood(rubberWoodBlockId.getInt());
		rubberLeavesBlock = new BlockRubberLeaves(rubberLeavesBlockId.getInt());
		rubberSaplingBlock = new BlockRubberSapling(rubberSaplingBlockId.getInt());
		railDropoffCargoBlock = new BlockRailCargoDropoff(railDropoffCargoBlockId.getInt());
		railPickupCargoBlock = new BlockRailCargoPickup(railPickupCargoBlockId.getInt());
		railDropoffPassengerBlock = new BlockRailPassengerDropoff(railDropoffPassengerBlockId.getInt());
		railPickupPassengerBlock = new BlockRailPassengerPickup(railPickupPassengerBlockId.getInt());
		rednetCableBlock = new BlockRedstoneCable(rednetCableBlockId.getInt());
		
		milkStill = new BlockFactoryLiquidStill(milkFlowingBlockId.getInt(), milkStillBlockId.getInt(), "milk");
		milkFlowing = new BlockFactoryLiquidFlowing(milkFlowingBlockId.getInt(), milkStillBlockId.getInt(), "milk");
		sludgeStill = new BlockFactoryLiquidStill(sludgeFlowingBlockId.getInt(), sludgeStillBlockId.getInt(), "sludge");
		sludgeFlowing = new BlockFactoryLiquidFlowing(sludgeFlowingBlockId.getInt(), sludgeStillBlockId.getInt(), "sludge");
		sewageStill = new BlockFactoryLiquidStill(sewageFlowingBlockId.getInt(), sewageStillBlockId.getInt(), "sewage");
		sewageFlowing = new BlockFactoryLiquidFlowing(sewageFlowingBlockId.getInt(), sewageStillBlockId.getInt(), "sewage");
		essenceStill = new BlockFactoryLiquidStill(essenceFlowingBlockId.getInt(), essenceStillBlockId.getInt(), "essence");
		essenceFlowing = new BlockFactoryLiquidFlowing(essenceFlowingBlockId.getInt(), essenceStillBlockId.getInt(), "essence");
		biofuelStill = new BlockFactoryLiquidStill(biofuelFlowingBlockId.getInt(), biofuelStillBlockId.getInt(), "biofuel");
		biofuelFlowing = new BlockFactoryLiquidFlowing(biofuelFlowingBlockId.getInt(), biofuelStillBlockId.getInt(), "biofuel");

		factoryHammerItem = (new ItemFactoryHammer(hammerItemId.getInt())).setUnlocalizedName("mfr.hammer").setMaxStackSize(1);
		fertilizerItem = (new ItemFactory(fertilizerItemId.getInt())).setUnlocalizedName("mfr.fertilizer");
		plasticSheetItem = (new ItemFactory(plasticSheetItemId.getInt())).setUnlocalizedName("mfr.plastic.sheet");
		rawPlasticItem = (new ItemFactory(rawPlasticItemId.getInt())).setUnlocalizedName("mfr.plastic.raw");
		rubberBarItem = (new ItemFactory(rubberBarItemId.getInt())).setUnlocalizedName("mfr.rubber.bar");
		sewageBucketItem = (new ItemFactoryBucket(sewageBucketItemId.getInt(), sewageFlowing.blockID)).setUnlocalizedName("mfr.bucket.sewage").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		sludgeBucketItem = (new ItemFactoryBucket(sludgeBucketItemId.getInt(), sludgeFlowing.blockID)).setUnlocalizedName("mfr.bucket.sludge").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		mobEssenceBucketItem = (new ItemFactoryBucket(mobEssenceBucketItemId.getInt(), essenceFlowing.blockID)).setUnlocalizedName("mfr.bucket.essence").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		syringeEmptyItem = (new ItemFactory(syringeEmptyItemId.getInt())).setUnlocalizedName("mfr.syringe.empty");
		syringeHealthItem = (new ItemSyringeHealth()).setUnlocalizedName("mfr.syringe.health").setContainerItem(syringeEmptyItem);
		syringeGrowthItem = (new ItemSyringeGrowth()).setUnlocalizedName("mfr.syringe.growth").setContainerItem(syringeEmptyItem);
		rawRubberItem = (new ItemFactory(rawRubberItemId.getInt())).setUnlocalizedName("mfr.rubber.raw");
		machineBaseItem = (new ItemFactory(machineBaseItemId.getInt())).setUnlocalizedName("mfr.machineblock");
		safariNetItem = (new ItemSafariNet(safariNetItemId.getInt())).setUnlocalizedName("mfr.safarinet.reusable");
		ceramicDyeItem = (new ItemCeramicDye(ceramicDyeId.getInt())).setUnlocalizedName("mfr.ceramicdye");
		blankRecordItem = (new ItemFactory(blankRecordId.getInt())).setUnlocalizedName("mfr.record.blank").setMaxStackSize(1);
		syringeZombieItem = (new ItemSyringeZombie()).setUnlocalizedName("mfr.syringe.zombie").setContainerItem(syringeEmptyItem);
		safariNetSingleItem = (new ItemSafariNet(safariNetSingleItemId.getInt())).setUnlocalizedName("mfr.safarinet.singleuse");
		bioFuelBucketItem = (new ItemFactoryBucket(bioFuelBucketItemId.getInt(), biofuelFlowing.blockID)).setUnlocalizedName("mfr.bucket.biofuel").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		upgradeItem = (new ItemUpgrade(upgradeItemId.getInt())).setUnlocalizedName("mfr.upgrade.radius").setMaxStackSize(1);
		safariNetLauncherItem = (new ItemSafariNetLauncher(safariNetLauncherItemId.getInt())).setUnlocalizedName("mfr.safarinet.launcher").setMaxStackSize(1);
		sugarCharcoalItem = (new ItemFactory(sugarCharcoalItemId.getInt())).setUnlocalizedName("mfr.sugarcharcoal");
		milkBottleItem = (new ItemMilkBottle(milkBottleItemId.getInt())).setUnlocalizedName("mfr.milkbottle").setMaxStackSize(1);
		spyglassItem = (new ItemSpyglass(spyglassItemId.getInt())).setUnlocalizedName("mfr.spyglass").setMaxStackSize(1);
		portaSpawnerItem = (new ItemPortaSpawner(portaSpawnerItemId.getInt())).setUnlocalizedName("mfr.portaspawner").setMaxStackSize(1);
		strawItem = (new ItemStraw(strawItemId.getInt())).setUnlocalizedName("mfr.straw").setMaxStackSize(1);
		xpExtractorItem = (new ItemXpExtractor(xpExtractorItemId.getInt())).setUnlocalizedName("mfr.xpextractor").setMaxStackSize(1);
		syringeSlimeItem = (new ItemSyringeSlime(syringeSlimeItemId.getInt())).setUnlocalizedName("mfr.syringe.slime").setContainerItem(syringeEmptyItem);
		syringeCureItem = (new ItemSyringeCure(syringeCureItemId.getInt())).setUnlocalizedName("mfr.syringe.cure").setContainerItem(syringeEmptyItem);

		for(Entry<Integer, Block> machine : machineBlocks.entrySet())
		{
			GameRegistry.registerBlock(machine.getValue(), ItemBlockFactoryMachine.class, machine.getValue().getUnlocalizedName());
		}
		
		GameRegistry.registerBlock(conveyorBlock, ItemBlockConveyor.class, conveyorBlock.getUnlocalizedName());
		GameRegistry.registerBlock(factoryGlassBlock, ItemBlockFactoryGlass.class, factoryGlassBlock.getUnlocalizedName());
		GameRegistry.registerBlock(factoryGlassPaneBlock, ItemBlockFactoryGlassPane.class, factoryGlassPaneBlock.getUnlocalizedName());
		GameRegistry.registerBlock(factoryRoadBlock, ItemBlockFactoryRoad.class, factoryRoadBlock.getUnlocalizedName());
		GameRegistry.registerBlock(factoryDecorativeBrickBlock, ItemBlockFactoryDecorativeBrick.class, factoryDecorativeBrickBlock.getUnlocalizedName());
		GameRegistry.registerBlock(rubberWoodBlock, rubberWoodBlock.getUnlocalizedName());
		GameRegistry.registerBlock(rubberLeavesBlock, rubberLeavesBlock.getUnlocalizedName());
		GameRegistry.registerBlock(rubberSaplingBlock, rubberSaplingBlock.getUnlocalizedName());
		GameRegistry.registerBlock(railPickupCargoBlock, railPickupCargoBlock.getUnlocalizedName());
		GameRegistry.registerBlock(railDropoffCargoBlock, railDropoffCargoBlock.getUnlocalizedName());
		GameRegistry.registerBlock(railPickupPassengerBlock, railPickupPassengerBlock.getUnlocalizedName());
		GameRegistry.registerBlock(railDropoffPassengerBlock, railDropoffPassengerBlock.getUnlocalizedName());
		GameRegistry.registerBlock(rednetCableBlock, rednetCableBlock.getUnlocalizedName());
		
		GameRegistry.registerBlock(milkStill, milkStill.getUnlocalizedName());
		GameRegistry.registerBlock(milkFlowing, milkFlowing.getUnlocalizedName());
		GameRegistry.registerBlock(sludgeStill, sludgeStill.getUnlocalizedName());
		GameRegistry.registerBlock(sludgeFlowing, sludgeFlowing.getUnlocalizedName());
		GameRegistry.registerBlock(sewageStill, sewageStill.getUnlocalizedName());
		GameRegistry.registerBlock(sewageFlowing, sewageFlowing.getUnlocalizedName());
		GameRegistry.registerBlock(essenceStill, essenceStill.getUnlocalizedName());
		GameRegistry.registerBlock(essenceFlowing, essenceFlowing.getUnlocalizedName());
		GameRegistry.registerBlock(biofuelStill, biofuelStill.getUnlocalizedName());
		GameRegistry.registerBlock(biofuelFlowing, biofuelFlowing.getUnlocalizedName());
		
		Block.setBurnProperties(rubberWoodBlock.blockID, 4, 20);
		Block.setBurnProperties(rubberLeavesBlock.blockID, 30, 20);
		
		if(vanillaOverrideGlassPane.getBoolean(true))
		{
			Block.blocksList[Block.thinGlass.blockID] = null;
			Item.itemsList[Block.thinGlass.blockID] = null;
			Block.thinGlass = new BlockVanillaGlassPane();
			GameRegistry.registerBlock(Block.thinGlass, Block.thinGlass.getUnlocalizedName());
		}
		if(vanillaOverrideIce.getBoolean(true))
		{
			Block.blocksList[Block.ice.blockID] = null;
			Item.itemsList[Block.ice.blockID] = null;
			Block.ice = new BlockVanillaIce();
			GameRegistry.registerBlock(Block.ice, ItemBlockVanillaIce.class, "blockVanillaIce");
		}
		if(vanillaOverrideMilkBucket.getBoolean(true))
		{
			int milkBucketId = Item.bucketMilk.itemID;
			Item.itemsList[milkBucketId] = null;
			Item.bucketMilk = new ItemFactoryBucket(milkBucketId, milkFlowing.blockID).setUnlocalizedName("mfr.bucket.milk").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		}

		GameRegistry.registerTileEntity(TileEntityConveyor.class, "factoryConveyor");
		GameRegistry.registerTileEntity(TileRedstoneCable.class, "factoryRedstoneCable");
		
		EntityRegistry.registerModEntity(EntitySafariNet.class, "entitySafariNet", 0, instance, 160, 5, true);

		MinecraftForge.EVENT_BUS.register(instance);
		
		Vanilla.registerRecipes();
		
		OreDictionary.registerOre("itemRubber", MineFactoryReloadedCore.rubberBarItem);
		OreDictionary.registerOre("woodRubber", MineFactoryReloadedCore.rubberWoodBlock);
		
		GameRegistry.registerFuelHandler(new MineFactoryReloadedFuelHandler());
		
		proxy.init();
		
		NetworkRegistry.instance().registerGuiHandler(this, new MFRGUIHandler());
		
		BlockDispenser.dispenseBehaviorRegistry.putObject(safariNetItem, new BehaviorDispenseSafariNet());
		BlockDispenser.dispenseBehaviorRegistry.putObject(safariNetSingleItem, new BehaviorDispenseSafariNet());
		
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(safariNetSingleItem), 1, 1, 25));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(safariNetSingleItem), 1, 1, 25));
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(safariNetSingleItem), 1, 1, 25));
		
		VillagerRegistry.instance().registerVillageCreationHandler(new VillageCreationHandler());
		VillagerRegistry.instance().registerVillagerType(zoolologistEntityId.getInt(), villagerFolder + "zoologist.png");
		VillagerRegistry.instance().registerVillageTradeHandler(zoolologistEntityId.getInt(), new VillageTradeHandler());
		
		GameRegistry.registerWorldGenerator(new MineFactoryReloadedWorldGen());
		
		TickRegistry.registerScheduledTickHandler(new UpdateManager(this), Side.CLIENT);
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent evt)
	{
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("milk", new LiquidStack(milkStill,  LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(Item.bucketMilk), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("sludge", new LiquidStack(sludgeStill,  LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(sludgeBucketItem), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("sewage", new LiquidStack(sewageStill,  LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(sewageBucketItem), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("mobEssence", new LiquidStack(essenceStill,  LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(mobEssenceBucketItem), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("biofuel", new LiquidStack(biofuelStill,  LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(bioFuelBucketItem), new ItemStack(Item.bucketEmpty)));
		
		for(ItemStack s : OreDictionary.getOres("itemRubber"))
		{
			FurnaceRecipes.smelting().addSmelting(s.itemID, s.getItemDamage(), new ItemStack(rawPlasticItem), 0.3F);
		}
		
		FurnaceRecipes.smelting().addSmelting(Item.sugar.itemID, new ItemStack(sugarCharcoalItem), 0.1F);
		
		String[] biomeWhitelist = rubberTreeBiomeWhitelist.getString().split(",");
		for(String biome : biomeWhitelist)
		{
			MFRRegistry.registerRubberTreeBiome(biome);
		}
		
		String[] biomeBlacklist = rubberTreeBiomeBlacklist.getString().split(",");
		for(String biome : biomeBlacklist)
		{
			MFRRegistry.getRubberTreeBiomes().remove(biome);
		}
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
		if(blockId == milkFlowing.blockID || blockId == milkStill.blockID) return new ItemStack(Item.bucketMilk);
		else if(blockId == sludgeFlowing.blockID || blockId == sludgeStill.blockID) return new ItemStack(sludgeBucketItem);
		else if(blockId == sewageFlowing.blockID || blockId == sewageStill.blockID) return new ItemStack(sewageBucketItem);
		else if(blockId == essenceFlowing.blockID || blockId == essenceStill.blockID) return new ItemStack(mobEssenceBucketItem);
		else if(blockId == biofuelFlowing.blockID || blockId == biofuelStill.blockID) return new ItemStack(bioFuelBucketItem);
		else return null;
	}

	private static void loadClientConfig(File configFile)
	{
		Configuration c = new Configuration(configFile);

		spyglassRange = c.get(Configuration.CATEGORY_GENERAL, "SpyglassRange", 200);
		spyglassRange.comment = "The maximum number of blocks the spyglass can look to find something. This calculation is performed only on the client side.";
		
		c.save();
	}
	
	private static void loadCommonConfig(File configFile)
	{
		Configuration c = new Configuration(configFile);
		c.load();
		machineBlock0Id = c.getBlock("ID.MachineBlock", 3120);
		conveyorBlockId = c.getBlock("ID.ConveyorBlock", 3121);
		rubberWoodBlockId = c.getBlock("ID.RubberWood", 3122);
		rubberLeavesBlockId = c.getBlock("ID.RubberLeaves", 3123);
		rubberSaplingBlockId = c.getBlock("ID.RubberSapling", 3124);
		railDropoffCargoBlockId = c.getBlock("ID.CargoRailDropoffBlock", 3125);
		railPickupCargoBlockId = c.getBlock("ID.CargoRailPickupBlock", 3126);
		railDropoffPassengerBlockId = c.getBlock("ID.PassengerRailDropoffBlock", 3127);
		railPickupPassengerBlockId = c.getBlock("ID.PassengerRailPickupBlock", 3128);
		factoryGlassBlockId = c.getBlock("ID.StainedGlass", 3129);
		factoryGlassPaneBlockId = c.getBlock("ID.StainedGlassPane", 3130);
		machineBlock1Id = c.getBlock("ID.MachineBlock1", 3131);
		factoryRoadBlockId = c.getBlock("ID.Road", 3132);
		factoryDecorativeBrickBlockId = c.getBlock("ID.Bricks", 3133);
		milkFlowingBlockId = c.getBlock("ID.Milk.Flowing", 3134);
		milkStillBlockId = c.getBlock("ID.Milk.Still", 3135);
		sludgeFlowingBlockId = c.getBlock("ID.Sludge.Flowing", 3136);
		sludgeStillBlockId = c.getBlock("ID.Sludge.Still", 3137);
		sewageFlowingBlockId = c.getBlock("ID.Sewage.Flowing", 3138);
		sewageStillBlockId = c.getBlock("ID.Sewage.Still", 3139);
		essenceFlowingBlockId = c.getBlock("ID.MobEssence.Flowing", 3140);
		essenceStillBlockId = c.getBlock("ID.MobEssence.Still", 3141);
		biofuelFlowingBlockId = c.getBlock("ID.BioFuel.Flowing", 3142);
		biofuelStillBlockId = c.getBlock("ID.BioFuel.Still", 3143);
		rednetCableBlockId = c.getBlock("ID.RedNet.Cable", 3144);

		hammerItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Hammer", 11987);
		milkItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Milk", 11988);
		sludgeItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Sludge", 11989);
		sewageItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Sewage", 11990);
		mobEssenceItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.MobEssence", 11991);
		fertilizerItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.FertilizerItem", 11992);
		plasticSheetItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.PlasticSheet", 11993);
		rawPlasticItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.RawPlastic", 11994);
		rubberBarItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.RubberBar", 11995);
		sewageBucketItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SewageBucket", 11996);
		sludgeBucketItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SludgeBucket", 11997);
		mobEssenceBucketItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.MobEssenceBucket", 11998);
		syringeEmptyItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SyringeEmpty", 11999);
		syringeHealthItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SyringeHealth", 12000);
		syringeGrowthItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SyringeGrowth", 12001);
		rawRubberItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.RawRubber", 12002);
		machineBaseItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.MachineBlock", 12003);
		safariNetItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SafariNet", 12004);
		ceramicDyeId = c.getItem(Configuration.CATEGORY_ITEM, "ID.CeramicDye", 12005);
		blankRecordId = c.getItem(Configuration.CATEGORY_ITEM, "ID.BlankRecord", 12006);
		syringeZombieId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SyringeZombie", 12007);
		safariNetSingleItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SafariNetSingleUse", 12008);
		bioFuelItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.BioFuel", 12009);
		bioFuelBucketItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.BioFuelBucket", 12010);
		upgradeItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Upgrade", 12011);
		safariNetLauncherItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SafariNetLauncher", 12012);
		sugarCharcoalItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SugarCharcoal", 12013);
		milkBottleItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.MilkBottle", 12014);
		spyglassItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Spyglass", 12015);
		portaSpawnerItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.PortaSpawner", 12016);
		strawItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Straw", 12017);
		xpExtractorItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.XPExtractor", 12018);
		syringeSlimeItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SyringeSlime", 12019);
		syringeCureItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.SyringeCure", 12020);
		
		zoolologistEntityId = c.get("Entity", "ID.Zoologist", 330);

		treeSearchMaxHorizontal = c.get(Configuration.CATEGORY_GENERAL, "SearchDistance.TreeMaxHoriztonal", 8);
		treeSearchMaxHorizontal.comment = "When searching for parts of a tree, how far out to the sides (radius) to search";
		treeSearchMaxVertical = c.get(Configuration.CATEGORY_GENERAL, "SearchDistance.TreeMaxVertical", 40);
		treeSearchMaxVertical.comment = "When searching for parts of a tree, how far up to search";
		verticalHarvestSearchMaxVertical = c.get(Configuration.CATEGORY_GENERAL, "SearchDistance.StackingBlockMaxVertical", 3);
		verticalHarvestSearchMaxVertical.comment = "How far upward to search for members of \"stacking\" blocks, like cactus and sugarcane";
		passengerRailSearchMaxVertical = c.get(Configuration.CATEGORY_GENERAL, "SearchDistance.PassengerRailMaxVertical", 2);
		passengerRailSearchMaxVertical.comment = "When searching for players or dropoff locations, how far up to search";
		passengerRailSearchMaxHorizontal = c.get(Configuration.CATEGORY_GENERAL, "SearchDistance.PassengerRailMaxHorizontal", 3);
		passengerRailSearchMaxHorizontal.comment = "When searching for players or dropoff locations, how far out to the sides (radius) to search";
		rubberTreeWorldGen = c.get(Configuration.CATEGORY_GENERAL, "WorldGen.RubberTree", true);
		rubberTreeWorldGen.comment = "Whether or not to generate rubber trees during map generation";
		mfrLakeWorldGen = c.get(Configuration.CATEGORY_GENERAL, "WorldGen.MFRLakes", true);
		mfrLakeWorldGen.comment = "Whether or not to generate MFR lakes during map generation";
		enableBonemealFertilizing = c.get(Configuration.CATEGORY_GENERAL, "Fertilizer.EnableBonemeal", false);
		enableBonemealFertilizing.comment = "If true, the fertilizer will use bonemeal as well as MFR fertilizer. Provided for those who want a less work-intensive farm.";
		enableCheapDSU = c.get(Configuration.CATEGORY_GENERAL, "DSU.EnableCheaperRecipe", false);
		enableCheapDSU.comment = "If true, DSU can be built out of chests instead of ender pearls. Does nothing if the DSU recipe is disabled.";
		enableMossyCobbleRecipe = c.get(Configuration.CATEGORY_GENERAL, "EnableMossyCobbleRecipe", true);
		enableMossyCobbleRecipe.comment = "If true, mossy cobble can be crafted.";
		conveyorCaptureNonItems = c.get(Configuration.CATEGORY_GENERAL, "Conveyor.CaptureNonItems", true);
		conveyorCaptureNonItems.comment = "If false, conveyors will not grab non-item entities. Breaks conveyor mob grinders but makes them safe for golems, etc.";
		playSounds = c.get(Configuration.CATEGORY_GENERAL, "PlaySounds", true);
		playSounds.comment = "Set to false to disable the harvester's sound when a block is harvested.";
		
		vanillaOverrideGlassPane = c.get(Configuration.CATEGORY_GENERAL, "VanillaOverride.GlassPanes", true);
		vanillaOverrideGlassPane.comment = "If true, allows vanilla glass panes to connect to MFR stained glass panes.";
		vanillaOverrideIce = c.get(Configuration.CATEGORY_GENERAL, "VanillaOverride.Ice", true);
		vanillaOverrideIce.comment = "If true, enables MFR unmelting ice as well as vanilla ice.";
		vanillaOverrideMilkBucket = c.get(Configuration.CATEGORY_GENERAL, "VanillaOverride.MilkBucket", true);
		vanillaOverrideMilkBucket.comment = "If true, replaces the vanilla milk bucket so milk can be placed in the world.";
		
		enableCompatibleAutoEnchanter = c.get(Configuration.CATEGORY_GENERAL, "AutoEnchanter.EnableSafeMode", false);
		enableCompatibleAutoEnchanter.comment = "If true, the Auto Enchanter will accept entire stacks of books. This is provided to prevent a crash with BuildCraft. This will allow many books to be enchanted at once - only enable this if you know what you're doing.";
		
		enableSlipperyRoads = c.get(Configuration.CATEGORY_GENERAL, "Road.Slippery", true);
		enableSlipperyRoads.comment = "If true, roads will be slippery like ice.";
		
		rubberTreeBiomeWhitelist = c.get(Configuration.CATEGORY_GENERAL, "WorldGen.RubberTreeBiomeWhitelist", "");
		rubberTreeBiomeWhitelist.comment = "A comma-separated list of biomes to allow rubber trees to spawn in. Does nothing if rubber tree worldgen is disabled.";
		rubberTreeBiomeBlacklist = c.get(Configuration.CATEGORY_GENERAL, "WorldGen.RubberTreeBiomeBlacklist", "");
		rubberTreeBiomeBlacklist.comment = "A comma-separated list of biomes to disallow rubber trees to spawn in. Overrides any other biomes added.";

		for(Machine machine : Machine.values())
		{
			machine.load(c);
		}
		
		c.save();
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
