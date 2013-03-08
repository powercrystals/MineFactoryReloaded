package powercrystals.minefactoryreloaded;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidDictionary.LiquidRegisterEvent;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import powercrystals.core.updater.IUpdateableMod;
import powercrystals.core.updater.UpdateManager;
import powercrystals.minefactoryreloaded.animals.ItemSafariNetLauncher;
import powercrystals.minefactoryreloaded.animals.ItemSyringeGrowth;
import powercrystals.minefactoryreloaded.animals.ItemSyringeHealth;
import powercrystals.minefactoryreloaded.animals.ItemSafariNet;
import powercrystals.minefactoryreloaded.animals.ItemSyringeZombie;
import powercrystals.minefactoryreloaded.animals.TileEntityAutoSpawner;
import powercrystals.minefactoryreloaded.animals.TileEntityBreeder;
import powercrystals.minefactoryreloaded.animals.TileEntityChronotyper;
import powercrystals.minefactoryreloaded.animals.TileEntityGrinder;
import powercrystals.minefactoryreloaded.animals.TileEntityRancher;
import powercrystals.minefactoryreloaded.animals.TileEntityVet;
import powercrystals.minefactoryreloaded.core.BehaviorDispenseSafariNet;
import powercrystals.minefactoryreloaded.core.BlockFactoryMachine0;
import powercrystals.minefactoryreloaded.core.BlockFactoryMachine1;
import powercrystals.minefactoryreloaded.core.ItemBlockFactoryMachine1;
import powercrystals.minefactoryreloaded.core.ItemFactory;
import powercrystals.minefactoryreloaded.core.ItemFactoryHammer;
import powercrystals.minefactoryreloaded.core.ItemBlockFactoryMachine0;
import powercrystals.minefactoryreloaded.core.MineFactoryReloadedFuelHandler;
import powercrystals.minefactoryreloaded.decorative.BlockFactoryDecorativeBricks;
import powercrystals.minefactoryreloaded.decorative.BlockFactoryGlass;
import powercrystals.minefactoryreloaded.decorative.BlockFactoryGlassPane;
import powercrystals.minefactoryreloaded.decorative.BlockFactoryRoad;
import powercrystals.minefactoryreloaded.decorative.BlockVanillaGlassPane;
import powercrystals.minefactoryreloaded.decorative.BlockVanillaIce;
import powercrystals.minefactoryreloaded.decorative.ItemBlockFactoryDecorativeBrick;
import powercrystals.minefactoryreloaded.decorative.ItemBlockFactoryGlass;
import powercrystals.minefactoryreloaded.decorative.ItemBlockFactoryGlassPane;
import powercrystals.minefactoryreloaded.decorative.ItemBlockFactoryRoad;
import powercrystals.minefactoryreloaded.decorative.ItemBlockVanillaIce;
import powercrystals.minefactoryreloaded.decorative.ItemCeramicDye;
import powercrystals.minefactoryreloaded.decorative.TileEntityAutoJukebox;
import powercrystals.minefactoryreloaded.entity.EntitySafariNet;
import powercrystals.minefactoryreloaded.gui.MFRGUIHandler;
import powercrystals.minefactoryreloaded.net.ClientPacketHandler;
import powercrystals.minefactoryreloaded.net.ConnectionHandler;
import powercrystals.minefactoryreloaded.net.IMFRProxy;
import powercrystals.minefactoryreloaded.net.ServerPacketHandler;
import powercrystals.minefactoryreloaded.plants.TileEntityFertilizer;
import powercrystals.minefactoryreloaded.plants.TileEntityHarvester;
import powercrystals.minefactoryreloaded.plants.TileEntityPlanter;
import powercrystals.minefactoryreloaded.power.TileEntityBioFuelGenerator;
import powercrystals.minefactoryreloaded.processing.ItemUpgrade;
import powercrystals.minefactoryreloaded.processing.TileEntityBioReactor;
import powercrystals.minefactoryreloaded.processing.TileEntityBlockBreaker;
import powercrystals.minefactoryreloaded.processing.TileEntityComposter;
import powercrystals.minefactoryreloaded.processing.TileEntityAutoEnchanter;
import powercrystals.minefactoryreloaded.processing.TileEntityDeepStorageUnit;
import powercrystals.minefactoryreloaded.processing.TileEntityFisher;
import powercrystals.minefactoryreloaded.processing.TileEntityLavaFabricator;
import powercrystals.minefactoryreloaded.processing.TileEntityLiquiCrafter;
import powercrystals.minefactoryreloaded.processing.TileEntityOilFabricator;
import powercrystals.minefactoryreloaded.processing.TileEntitySewer;
import powercrystals.minefactoryreloaded.processing.TileEntitySludgeBoiler;
import powercrystals.minefactoryreloaded.processing.TileEntityUnifier;
import powercrystals.minefactoryreloaded.processing.TileEntityWeather;
import powercrystals.minefactoryreloaded.rails.BlockRailCargoDropoff;
import powercrystals.minefactoryreloaded.rails.BlockRailCargoPickup;
import powercrystals.minefactoryreloaded.rails.BlockRailPassengerDropoff;
import powercrystals.minefactoryreloaded.rails.BlockRailPassengerPickup;
import powercrystals.minefactoryreloaded.transport.BlockConveyor;
import powercrystals.minefactoryreloaded.transport.ItemBlockConveyor;
import powercrystals.minefactoryreloaded.transport.TileEntityCollector;
import powercrystals.minefactoryreloaded.transport.TileEntityConveyor;
import powercrystals.minefactoryreloaded.transport.TileEntityEjector;
import powercrystals.minefactoryreloaded.transport.TileEntityItemRouter;
import powercrystals.minefactoryreloaded.transport.TileEntityLiquidRouter;
import powercrystals.minefactoryreloaded.world.BlockRubberLeaves;
import powercrystals.minefactoryreloaded.world.BlockRubberSapling;
import powercrystals.minefactoryreloaded.world.BlockRubberWood;
import powercrystals.minefactoryreloaded.world.MineFactoryReloadedWorldGen;

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
import cpw.mods.fml.relauncher.Side;

@Mod(modid = MineFactoryReloadedCore.modId, name = MineFactoryReloadedCore.modName, version = MineFactoryReloadedCore.version,
dependencies = "after:BuildCraft|Core;after:BuildCraft|Factory;after:BuildCraft|Energy;after:BuildCraft|Builders;after:BuildCraft|Transport;after:IC2;required-after:PowerCrystalsCore")
@NetworkMod(clientSideRequired = true, serverSideRequired = false,
clientPacketHandlerSpec = @SidedPacketHandler(channels = { MineFactoryReloadedCore.modId }, packetHandler = ClientPacketHandler.class),
serverPacketHandlerSpec = @SidedPacketHandler(channels = { MineFactoryReloadedCore.modId }, packetHandler = ServerPacketHandler.class),
connectionHandler = ConnectionHandler.class)
public class MineFactoryReloadedCore implements IUpdateableMod
{
	@SidedProxy(clientSide = "powercrystals.minefactoryreloaded.net.ClientProxy", serverSide = "powercrystals.minefactoryreloaded.net.CommonProxy")
	public static IMFRProxy proxy;
	
	public static final String modId = "MFReloaded";
	public static final String version = "1.4.6R2.2.1B1";
	public static final String modName = "Minefactory Reloaded";
	
	private static final String textureFolder = "/powercrystals/minefactoryreloaded/textures/";
	public static final String terrainTexture = textureFolder + "terrain_0.png";
	public static final String machine0Texture = textureFolder + "machine_0.png";
	public static final String machine1Texture = textureFolder + "machine_1.png";
	public static final String itemTexture = textureFolder + "items_0.png";
	public static final String animationFolder = textureFolder + "animations/";
	public static final String guiFolder = textureFolder + "gui/";
	
	public static int renderIdConveyor = 1000;
	public static int renderIdFactoryGlassPane = 1001;
	
	public static Block machineBlock0;
	public static Block machineBlock1;
	
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

	public static Item machineItem;

	public static Item factoryHammerItem;
	public static Item milkItem;
	public static Item sludgeItem;
	public static Item sewageItem;
	public static Item mobEssenceItem;
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
	public static Item bioFuelItem;
	public static Item bioFuelBucketItem;
	public static Item upgradeItem;
	public static Item safariNetLauncherItem;

	public static int conveyorTexture = 0;
	public static int conveyorOffTexture = 1;
	public static int conveyorStillOffTexture = 2;

	public static Map<MineFactoryReloadedCore.Machine, Integer> machine0MetadataMappings = new HashMap<Machine, Integer>();
	public static Map<MineFactoryReloadedCore.Machine, Integer> machine1MetadataMappings = new HashMap<Machine, Integer>();

	// Config
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

	public static Property animateBlockFaces;
	public static Property treeSearchMaxVertical;
	public static Property treeSearchMaxHorizontal;
	public static Property playSounds;
	public static Property machinesCanDropInChests;
	public static Property verticalHarvestSearchMaxVertical;
	public static Property rubberTreeWorldGen;
	public static Property enableBonemealFertilizing;
	public static Property enableCheapDSU;
	public static Property conveyorCaptureNonItems;
	
	public static Property vanillaOverrideGlassPane;
	public static Property vanillaOverrideIce;
	
	public static Property enableCompatibleAutoEnchanter;
	
	public static Property rubberTreeBiomeWhitelist;
	public static Property rubberTreeBiomeBlacklist;
	
	public static Property passengerRailSearchMaxHorizontal;
	public static Property passengerRailSearchMaxVertical;
	
	public static Property enableMachinePlanter;
	public static Property enableMachineFisher;
	public static Property enableMachineHarvester;
	public static Property enableMachineRancher;
	public static Property enableMachineFertilizer;
	public static Property enableMachineVet;
	public static Property enableMachineCollector;
	public static Property enableMachineBreaker;
	public static Property enableMachineWeather;
	public static Property enableMachineBoiler;
	public static Property enableMachineSewer;
	public static Property enableMachineComposter;
	public static Property enableMachineBreeder;
	public static Property enableMachineGrinder;
	public static Property enableMachineEnchanter;
	public static Property enableMachineChronotyper;
	
	public static Property enableMachineEjector;
	public static Property enableMachineItemRouter;
	public static Property enableMachineLiquidRouter;
	public static Property enableMachineDeepStorageUnit;
	public static Property enableMachineLiquiCrafter;
	public static Property enableMachineLavaFabricator;
	public static Property enableMachineOiLFabricator;
	public static Property enableMachineAutoJukebox;
	public static Property enableMachineUnifier;
	public static Property enableMachineAutoSpawner;
	public static Property enableMachineBioReactor;

	private static MineFactoryReloadedCore instance;
	
	public static int oilLiquidId = -1;

	public enum Machine
	{
		Planter, Fisher, Harvester, Fertilizer, Rancher, Vet, Collector, Breaker, Weather, Boiler, Sewer, Composter, Breeder, Grinder, Enchanter, Chronotyper,
		Ejector, ItemRouter, LiquidRouter, DeepStorageUnit, LiquiCrafter, OilFabricator, LavaFabricator, AutoJukebox, Unifier, AutoSpawner, BioReactor,
		BioFuelGenerator
	}

	public static MineFactoryReloadedCore instance()
	{
		return instance;
	}

	@PreInit
	public void preInit(FMLPreInitializationEvent evt)
	{
		loadConfig(evt.getSuggestedConfigurationFile());
	}

	@Init
	public void load(FMLInitializationEvent evt)
	{
		instance = this;

		machine0MetadataMappings.put(Machine.Planter, 0);
		machine0MetadataMappings.put(Machine.Fisher, 1);
		machine0MetadataMappings.put(Machine.Harvester, 2);
		machine0MetadataMappings.put(Machine.Rancher, 3);
		machine0MetadataMappings.put(Machine.Fertilizer, 4);
		machine0MetadataMappings.put(Machine.Vet, 5);
		machine0MetadataMappings.put(Machine.Collector, 6);
		machine0MetadataMappings.put(Machine.Breaker, 7);
		machine0MetadataMappings.put(Machine.Weather, 8);
		machine0MetadataMappings.put(Machine.Boiler, 9);
		machine0MetadataMappings.put(Machine.Sewer, 10);
		machine0MetadataMappings.put(Machine.Composter, 11);
		machine0MetadataMappings.put(Machine.Breeder, 12);
		machine0MetadataMappings.put(Machine.Grinder, 13);
		machine0MetadataMappings.put(Machine.Enchanter, 14);
		machine0MetadataMappings.put(Machine.Chronotyper, 15);

		machine1MetadataMappings.put(Machine.Ejector, 0);
		machine1MetadataMappings.put(Machine.ItemRouter, 1);
		machine1MetadataMappings.put(Machine.LiquidRouter, 2);
		machine1MetadataMappings.put(Machine.DeepStorageUnit, 3);
		machine1MetadataMappings.put(Machine.LiquiCrafter, 4);
		machine1MetadataMappings.put(Machine.LavaFabricator, 5);
		machine1MetadataMappings.put(Machine.OilFabricator, 6);
		machine1MetadataMappings.put(Machine.AutoJukebox, 7);
		machine1MetadataMappings.put(Machine.Unifier, 8);
		machine1MetadataMappings.put(Machine.AutoSpawner, 9);
		machine1MetadataMappings.put(Machine.BioReactor, 10);
		machine1MetadataMappings.put(Machine.BioFuelGenerator, 11);

		conveyorBlock = new BlockConveyor(conveyorBlockId.getInt(), conveyorOffTexture);
		machineBlock0 = new BlockFactoryMachine0(machineBlock0Id.getInt());
		machineBlock1 = new BlockFactoryMachine1(machineBlock1Id.getInt());
		factoryGlassBlock = new BlockFactoryGlass(factoryGlassBlockId.getInt(), 16);
		factoryGlassPaneBlock = new BlockFactoryGlassPane(factoryGlassPaneBlockId.getInt(), 16, 32);
		factoryRoadBlock = new BlockFactoryRoad(factoryRoadBlockId.getInt());
		factoryDecorativeBrickBlock = new BlockFactoryDecorativeBricks(factoryDecorativeBrickBlockId.getInt());
		rubberWoodBlock = new BlockRubberWood(rubberWoodBlockId.getInt());
		rubberLeavesBlock = new BlockRubberLeaves(rubberLeavesBlockId.getInt());
		rubberSaplingBlock = new BlockRubberSapling(rubberSaplingBlockId.getInt());
		railDropoffCargoBlock = new BlockRailCargoDropoff(railDropoffCargoBlockId.getInt(), 8);
		railPickupCargoBlock = new BlockRailCargoPickup(railPickupCargoBlockId.getInt(), 9);
		railDropoffPassengerBlock = new BlockRailPassengerDropoff(railDropoffPassengerBlockId.getInt(), 10);
		railPickupPassengerBlock = new BlockRailPassengerPickup(railPickupPassengerBlockId.getInt(), 11);

		factoryHammerItem = (new ItemFactoryHammer(hammerItemId.getInt())).setIconIndex(0).setItemName("factoryHammer").setMaxStackSize(1);
		milkItem = (new ItemFactory(milkItemId.getInt())).setIconIndex(2).setItemName("milkItem");
		sludgeItem = (new ItemFactory(sludgeItemId.getInt())).setIconIndex(3).setItemName("sludgeItem");
		sewageItem = (new ItemFactory(sewageItemId.getInt())).setIconIndex(4).setItemName("sewageItem");
		mobEssenceItem = (new ItemFactory(mobEssenceItemId.getInt())).setIconIndex(5).setItemName("mobEssenceItem");
		fertilizerItem = (new ItemFactory(fertilizerItemId.getInt())).setIconIndex(6).setItemName("fertilizerFactoryItem");
		plasticSheetItem = (new ItemFactory(plasticSheetItemId.getInt())).setIconIndex(7).setItemName("plasticSheetItem");
		rawPlasticItem = (new ItemFactory(rawPlasticItemId.getInt())).setIconIndex(8).setItemName("rawPlasticItem");
		rubberBarItem = (new ItemFactory(rubberBarItemId.getInt())).setIconIndex(9).setItemName("rubberBarItem");
		sewageBucketItem = (new ItemFactory(sewageBucketItemId.getInt())).setIconIndex(10).setItemName("sewageBucketItem").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		sludgeBucketItem = (new ItemFactory(sludgeBucketItemId.getInt())).setIconIndex(11).setItemName("sludgeBucketItem").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		mobEssenceBucketItem = (new ItemFactory(mobEssenceBucketItemId.getInt())).setIconIndex(12).setItemName("mobEssenceBucketItem").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		syringeEmptyItem = (new ItemFactory(syringeEmptyItemId.getInt())).setIconIndex(13).setItemName("syringeEmptyItem");
		syringeHealthItem = (new ItemSyringeHealth()).setIconIndex(14).setItemName("syringeHealthItem").setContainerItem(syringeEmptyItem);
		syringeGrowthItem = (new ItemSyringeGrowth()).setIconIndex(15).setItemName("syringeGrowthItem").setContainerItem(syringeEmptyItem);
		rawRubberItem = (new ItemFactory(rawRubberItemId.getInt())).setIconIndex(16).setItemName("rawRubberItem");
		machineBaseItem = (new ItemFactory(machineBaseItemId.getInt())).setIconIndex(17).setItemName("factoryMachineBlock");
		safariNetItem = (new ItemSafariNet(safariNetItemId.getInt())).setIconIndex(18).setItemName("safariNetItem");
		ceramicDyeItem = (new ItemCeramicDye(ceramicDyeId.getInt())).setIconIndex(22).setItemName("ceramicDyeItem");
		blankRecordItem = (new ItemFactory(blankRecordId.getInt())).setIconIndex(40).setItemName("blankRecordItem").setMaxStackSize(1);
		syringeZombieItem = (new ItemSyringeZombie()).setIconIndex(41).setItemName("syringeZombieItem").setContainerItem(syringeEmptyItem);
		safariNetSingleItem = (new ItemSafariNet(safariNetSingleItemId.getInt())).setIconIndex(42).setItemName("safariNetSingleItem");
		bioFuelItem = (new ItemFactory(bioFuelItemId.getInt())).setIconIndex(46).setItemName("factoryBioFuelItem");
		bioFuelBucketItem = (new ItemFactory(bioFuelBucketItemId.getInt())).setIconIndex(47).setItemName("factoryBioFuelBucketItem").setMaxStackSize(1).setContainerItem(Item.bucketEmpty);
		upgradeItem = (new ItemUpgrade(upgradeItemId.getInt())).setItemName("factoryUpgradeItem").setMaxStackSize(1);
		safariNetLauncherItem = (new ItemSafariNetLauncher(safariNetLauncherItemId.getInt())).setIconIndex(50).setItemName("safariNetLauncherItem").setMaxStackSize(1);

		GameRegistry.registerBlock(machineBlock0, ItemBlockFactoryMachine0.class, "blockMachine");
		GameRegistry.registerBlock(machineBlock1, ItemBlockFactoryMachine1.class, "blockMachine1");
		GameRegistry.registerBlock(conveyorBlock, ItemBlockConveyor.class, "blockConveyor");
		GameRegistry.registerBlock(factoryGlassBlock, ItemBlockFactoryGlass.class, "blockFactoryGlass");
		GameRegistry.registerBlock(factoryGlassPaneBlock, ItemBlockFactoryGlassPane.class, "blockFactoryGlassPane");
		GameRegistry.registerBlock(factoryRoadBlock, ItemBlockFactoryRoad.class, "blockFactoryRoad");
		GameRegistry.registerBlock(factoryDecorativeBrickBlock, ItemBlockFactoryDecorativeBrick.class, "blockFactoryDecorativeBrick");
		GameRegistry.registerBlock(rubberWoodBlock, "blockRubberWood");
		GameRegistry.registerBlock(rubberLeavesBlock, "blockRubberLeaves");
		GameRegistry.registerBlock(rubberSaplingBlock, "blockRubberSapling");
		GameRegistry.registerBlock(railPickupCargoBlock, "blockRailPickupCargo");
		GameRegistry.registerBlock(railDropoffCargoBlock, "blockRailDropoffCargo");
		GameRegistry.registerBlock(railPickupPassengerBlock, "blockRailPickupPassenger");
		GameRegistry.registerBlock(railDropoffPassengerBlock, "blockRailDropoffPassenger");
		
		Block.setBurnProperties(rubberWoodBlock.blockID, 4, 20);
		Block.setBurnProperties(rubberLeavesBlock.blockID, 30, 20);
		
		if(vanillaOverrideGlassPane.getBoolean(true))
		{
			Block.blocksList[Block.thinGlass.blockID] = null;
			Item.itemsList[Block.thinGlass.blockID] = null;
			Block.thinGlass = new BlockVanillaGlassPane();
			GameRegistry.registerBlock(Block.thinGlass, "blockVanillaGlassPane");
		}
		if(vanillaOverrideIce.getBoolean(true))
		{
			Block.blocksList[Block.ice.blockID] = null;
			Item.itemsList[Block.ice.blockID] = null;
			Block.ice = new BlockVanillaIce();
			GameRegistry.registerBlock(Block.ice, ItemBlockVanillaIce.class, "blockVanillaIce");
		}

		GameRegistry.registerTileEntity(TileEntityFisher.class, "factoryFisher");
		GameRegistry.registerTileEntity(TileEntityPlanter.class, "factoryPlanter");
		GameRegistry.registerTileEntity(TileEntityHarvester.class, "factoryHarvester");
		GameRegistry.registerTileEntity(TileEntityRancher.class, "factoryRancher");
		GameRegistry.registerTileEntity(TileEntityFertilizer.class, "factoryFertilizer");
		GameRegistry.registerTileEntity(TileEntityConveyor.class, "factoryConveyor");
		GameRegistry.registerTileEntity(TileEntityVet.class, "factoryVet");
		GameRegistry.registerTileEntity(TileEntityCollector.class, "factoryItemCollector");
		GameRegistry.registerTileEntity(TileEntityBlockBreaker.class, "factoryBlockBreaker");
		GameRegistry.registerTileEntity(TileEntityWeather.class, "factoryWeather");
		GameRegistry.registerTileEntity(TileEntitySludgeBoiler.class, "factorySludgeBoiler");
		GameRegistry.registerTileEntity(TileEntitySewer.class, "factorySewer");
		GameRegistry.registerTileEntity(TileEntityComposter.class, "factoryComposter");
		GameRegistry.registerTileEntity(TileEntityBreeder.class, "factoryBreeder");
		GameRegistry.registerTileEntity(TileEntityGrinder.class, "factoryGrinder");
		GameRegistry.registerTileEntity(TileEntityAutoEnchanter.class, "factoryEnchanter");
		GameRegistry.registerTileEntity(TileEntityChronotyper.class, "factoryChronotyper");

		GameRegistry.registerTileEntity(TileEntityEjector.class, "factoryEjector");
		GameRegistry.registerTileEntity(TileEntityItemRouter.class, "factoryItemRouter");
		GameRegistry.registerTileEntity(TileEntityLiquidRouter.class, "factoryLiquidRouter");
		GameRegistry.registerTileEntity(TileEntityDeepStorageUnit.class, "factoryDeepStorageUnit");
		GameRegistry.registerTileEntity(TileEntityLiquiCrafter.class, "factoryLiquiCrafter");
		GameRegistry.registerTileEntity(TileEntityLavaFabricator.class, "factoryLavaFabricator");
		GameRegistry.registerTileEntity(TileEntityOilFabricator.class, "factoryOilFabricator");
		GameRegistry.registerTileEntity(TileEntityAutoJukebox.class, "factoryAutoJukebox");
		GameRegistry.registerTileEntity(TileEntityUnifier.class, "factoryUnifier");
		GameRegistry.registerTileEntity(TileEntityAutoSpawner.class, "factoryAutoSpawner");
		GameRegistry.registerTileEntity(TileEntityBioReactor.class, "factoryBioReactor");
		GameRegistry.registerTileEntity(TileEntityBioFuelGenerator.class, "factoryBioFuelGenerator");
		
		EntityRegistry.registerModEntity(EntitySafariNet.class, "entitySafariNet", 0, instance, 160, 5, true);

		MinecraftForge.EVENT_BUS.register(instance);
		
		registerRecipes();
		
		proxy.load();
		
		NetworkRegistry.instance().registerGuiHandler(this, new MFRGUIHandler());
		
		BlockDispenser.dispenseBehaviorRegistry.putObject(safariNetItem, new BehaviorDispenseSafariNet());
		BlockDispenser.dispenseBehaviorRegistry.putObject(safariNetSingleItem, new BehaviorDispenseSafariNet());
		
		if(rubberTreeWorldGen.getBoolean(true))
		{
			GameRegistry.registerWorldGenerator(new MineFactoryReloadedWorldGen());
		}
		
		if(LiquidDictionary.getLiquids().get("Oil") != null)
		{
			oilLiquidId = LiquidDictionary.getLiquids().get("Oil").itemID;
		}
		
		TickRegistry.registerScheduledTickHandler(new UpdateManager(this), Side.CLIENT);
	}
	
	@PostInit
	public void afterModsLoaded(FMLPostInitializationEvent evt)
	{
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("milk", new LiquidStack(milkItem,  LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(Item.bucketMilk), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("sludge", new LiquidStack(sludgeItem,  LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(sludgeBucketItem), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("sewage", new LiquidStack(sewageItem,  LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(sewageBucketItem), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("mobEssence", new LiquidStack(mobEssenceItem,  LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(mobEssenceBucketItem), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("bioFuel", new LiquidStack(bioFuelItem,  LiquidContainerRegistry.BUCKET_VOLUME)), new ItemStack(bioFuelBucketItem), new ItemStack(Item.bucketEmpty)));
		
		for(ItemStack s : OreDictionary.getOres("itemRubber"))
		{
			FurnaceRecipes.smelting().addSmelting(s.itemID, s.getItemDamage(), new ItemStack(rawPlasticItem), 0.3F);
		}
		
		String[] biomeWhitelist = rubberTreeBiomeWhitelist.value.split(",");
		for(String biome : biomeWhitelist)
		{
			MFRRegistry.registerRubberTreeBiome(biome);
		}
		
		String[] biomeBlacklist = rubberTreeBiomeBlacklist.value.split(",");
		for(String biome : biomeBlacklist)
		{
			MFRRegistry.getRubberTreeBiomes().remove(biome);
		}
	}
	
	@ForgeSubscribe
	public void forgeEvent(Event e)
	{
		if(e instanceof BonemealEvent)
		{
			BonemealEvent b = (BonemealEvent)e;
			if(b.world.getBlockId(b.X, b.Y, b.Z) == MineFactoryReloadedCore.rubberSaplingBlock.blockID)
			{
				((BlockRubberSapling)MineFactoryReloadedCore.rubberSaplingBlock).growTree(b.world, b.X, b.Y, b.Z, b.world.rand);
				b.entityPlayer.inventory.mainInventory[b.entityPlayer.inventory.currentItem].stackSize--;
				if(b.entityPlayer.inventory.mainInventory[b.entityPlayer.inventory.currentItem].stackSize <= 0)
				{
					b.entityPlayer.inventory.mainInventory[b.entityPlayer.inventory.currentItem] = null;
				}
			}
		}
		else if(oilLiquidId == -1 && e instanceof LiquidRegisterEvent)
		{
			LiquidRegisterEvent l = (LiquidRegisterEvent)e;
			if(l.Name.equals("Oil"))
			{
				oilLiquidId = l.Liquid.itemID;
			}
		}
	}

	private static void loadConfig(File configFile)
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

		animateBlockFaces = c.get(Configuration.CATEGORY_GENERAL, "AnimateBlockFaces", true);
		animateBlockFaces.comment = "Set to false to disable animation of harvester, rancher, conveyor, etc. This may be required if using certain mods that affect rendering.";
		playSounds = c.get(Configuration.CATEGORY_GENERAL, "PlaySounds", true);
		playSounds.comment = "Set to false to disable the harvester's sound when a block is harvested.";
		machinesCanDropInChests = c.get(Configuration.CATEGORY_GENERAL, "MachinesCanDropInChests", true);
		machinesCanDropInChests.comment = "Set to false to disable machines placing items into chests adjacent to them";

		treeSearchMaxHorizontal = c.get(Configuration.CATEGORY_GENERAL, "SearchDistance.TreeMaxHoriztonal", 5);
		treeSearchMaxHorizontal.comment = "When searching for parts of a tree, how far out to the sides (radius) to search";
		treeSearchMaxVertical = c.get(Configuration.CATEGORY_GENERAL, "SearchDistance.TreeMaxVertical", 15);
		treeSearchMaxVertical.comment = "When searching for parts of a tree, how far up to search";
		verticalHarvestSearchMaxVertical = c.get(Configuration.CATEGORY_GENERAL, "SearchDistance.StackingBlockMaxVertical", 3);
		verticalHarvestSearchMaxVertical.comment = "How far upward to search for members of \"stacking\" blocks, like cactus and sugarcane";
		passengerRailSearchMaxVertical = c.get(Configuration.CATEGORY_GENERAL, "SearchDistance.PassengerRailMaxVertical", 2);
		passengerRailSearchMaxVertical.comment = "When searching for players or dropoff locations, how far up to search";
		passengerRailSearchMaxHorizontal = c.get(Configuration.CATEGORY_GENERAL, "SearchDistance.PassengerRailMaxHorizontal", 3);
		passengerRailSearchMaxHorizontal.comment = "When searching for players or dropoff locations, how far out to the sides (radius) to search";
		rubberTreeWorldGen = c.get(Configuration.CATEGORY_GENERAL, "WorldGen.RubberTree", true);
		rubberTreeWorldGen.comment = "Whether or not to generate rubber trees during map generation";
		enableBonemealFertilizing = c.get(Configuration.CATEGORY_GENERAL, "Fertilizer.EnableBonemeal", false);
		enableBonemealFertilizing.comment = "If true, the fertilizer will use bonemeal as well as MFR fertilizer. Provided for those who want a less work-intensive farm.";
		enableCheapDSU = c.get(Configuration.CATEGORY_GENERAL, "DSU.EnableCheaperRecipe", false);
		enableCheapDSU.comment = "If true, DSU can be built out of chests instead of ender pearls. Does nothing if the DSU recipe is disabled.";
		conveyorCaptureNonItems = c.get(Configuration.CATEGORY_GENERAL, "Conveyor.CaptureNonItems", true);
		conveyorCaptureNonItems.comment = "If false, conveyors will not grab non-item entities. Breaks conveyor mob grinders but makes them safe for golems, etc.";
		
		vanillaOverrideGlassPane = c.get(Configuration.CATEGORY_GENERAL, "VanillaOverride.GlassPanes", true);
		vanillaOverrideGlassPane.comment = "If true, allows vanilla glass panes to connect to MFR stained glass panes.";
		vanillaOverrideIce = c.get(Configuration.CATEGORY_GENERAL, "VanillaOverride.Ice", true);
		vanillaOverrideIce.comment = "If true, enables MFR unmelting ice as well as vanilla ice.";
		
		enableCompatibleAutoEnchanter = c.get(Configuration.CATEGORY_GENERAL, "AutoEnchanter.EnableSafeMode", false);
		enableCompatibleAutoEnchanter.comment = "If true, the Auto Enchanter will accept entire stacks of books. This is provided to prevent a crash with BuildCraft. This will allow many books to be enchanted at once - only enable this if you know what you're doing.";
		
		rubberTreeBiomeWhitelist = c.get(Configuration.CATEGORY_GENERAL, "WorldGen.RubberTreeBiomeWhitelist", "");
		rubberTreeBiomeWhitelist.comment = "A comma-separated list of biomes to allow rubber trees to spawn in. Does nothing if rubber tree worldgen is disabled.";
		rubberTreeBiomeBlacklist = c.get(Configuration.CATEGORY_GENERAL, "WorldGen.RubberTreeBiomeBlacklist", "");
		rubberTreeBiomeBlacklist.comment = "A comma-separated list of biomes to disallow rubber trees to spawn in. Overrides any other biomes added.";
		
		enableMachinePlanter = c.get("MachineEnables", "Planter", true);
		enableMachineFisher = c.get("MachineEnables", "Fisher", true);
		enableMachineHarvester = c.get("MachineEnables", "Harvester", true);
		enableMachineRancher = c.get("MachineEnables", "Rancher", true);
		enableMachineFertilizer = c.get("MachineEnables", "Fertilizer", true);
		enableMachineVet = c.get("MachineEnables", "Vet", true);
		enableMachineCollector = c.get("MachineEnables", "ItemCollector", true);
		enableMachineBreaker = c.get("MachineEnables", "BlockBreaker", true);
		enableMachineWeather = c.get("MachineEnables", "WeatherCollector", true);
		enableMachineBoiler = c.get("MachineEnables", "SludgeBoiler", true);
		enableMachineSewer = c.get("MachineEnables", "Sewer", true);
		enableMachineComposter = c.get("MachineEnables", "Composter", true);
		enableMachineBreeder = c.get("MachineEnables", "Breeder", true);
		enableMachineGrinder = c.get("MachineEnables", "MobGrinder", true);
		enableMachineEnchanter = c.get("MachineEnables", "AutoEnchanter", true);
		enableMachineChronotyper = c.get("MachineEnables", "Chronotyper", true);
		enableMachineEjector = c.get("MachineEnables", "Ejector", true);
		enableMachineItemRouter = c.get("MachineEnables", "ItemRouter", true);
		enableMachineLiquidRouter = c.get("MachineEnables", "LiquidRouter", true);
		enableMachineDeepStorageUnit = c.get("MachineEnables", "DeepStorageUnit", true);
		enableMachineLiquiCrafter = c.get("MachineEnables", "LiquiCrafter", true);
		enableMachineLavaFabricator = c.get("MachineEnables", "LavaFabricator", true);
		enableMachineOiLFabricator = c.get("MachineEnables", "OilFabricator", true);
		enableMachineAutoJukebox = c.get("MachineEnables", "AutoJukebox", true);
		enableMachineUnifier = c.get("MachineEnables", "Unifier", true);
		enableMachineAutoSpawner = c.get("MachineEnables", "AutoSpawner", true);
		enableMachineBioReactor = c.get("MachineEnables", "BioReactor", true);

		c.addCustomCategoryComment("MachineEnables", "Set to false to disable that machine's recipes. Machines will still work if gotten through other means.");
		c.save();
	}
	
	public LiquidStack getLiquidStackFromLiquidItem(ItemStack s)
	{
		if(LiquidContainerRegistry.isLiquid(s))
		{
			return new LiquidStack(s.itemID, LiquidContainerRegistry.BUCKET_VOLUME, s.getItemDamage());
		}
		return null;
	}
	
	private void registerRecipes()
	{
		OreDictionary.registerOre("itemRubber", rubberBarItem);
		OreDictionary.registerOre("woodRubber", rubberWoodBlock);
		
		FurnaceRecipes.smelting().addSmelting(rawRubberItem.itemID, 0, new ItemStack(rubberBarItem), 0.1F);
		FurnaceRecipes.smelting().addSmelting(rubberWoodBlock.blockID, 0, new ItemStack(Item.coal, 1, 1), 0.1F);
		
		GameRegistry.registerFuelHandler(new MineFactoryReloadedFuelHandler());
		
		GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 3, 3), new ItemStack(rubberWoodBlock));
		
		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 4), new Object[]
				{
					"R",
					"S",
					Character.valueOf('R'), rawRubberItem,
					Character.valueOf('S'), Item.stick
				} );
		
		GameRegistry.addRecipe(new ItemStack(Block.pistonStickyBase), new Object[]
				{
					"R",
					"P",
					Character.valueOf('R'), rawRubberItem,
					Character.valueOf('P'), Block.pistonBase
				} );
		
		GameRegistry.addRecipe(new ItemStack(plasticSheetItem, 4), new Object[]
				{
					"##",
					"##",
					Character.valueOf('#'), rawPlasticItem,
				} );
		
		GameRegistry.addRecipe(new ItemStack(machineBaseItem, 3), new Object[]
				{
					"PPP",
					"SSS",
					Character.valueOf('P'), plasticSheetItem,
					Character.valueOf('S'), Block.stone,
				} );
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(syringeEmptyItem, 1), new Object[]
				{
					"PRP",
					"P P",
					" I ",
					Character.valueOf('P'), plasticSheetItem,
					Character.valueOf('R'), "itemRubber",
					Character.valueOf('I'), Item.ingotIron,
				} ));
		
		GameRegistry.addShapelessRecipe(new ItemStack(syringeHealthItem), new Object[] { syringeEmptyItem, Item.appleRed });
		GameRegistry.addShapelessRecipe(new ItemStack(syringeGrowthItem), new Object[] { syringeEmptyItem, Item.goldenCarrot });
		
		GameRegistry.addRecipe(new ItemStack(syringeZombieItem, 1), new Object[]
				{
					"FFF",
					"FSF",
					"FFF",
					Character.valueOf('F'), Item.rottenFlesh,
					Character.valueOf('S'), syringeEmptyItem,
				} );
		
		GameRegistry.addRecipe(new ItemStack(fertilizerItem, 16), new Object[]
				{
					"WBW",
					"STS",
					"WBW",
					Character.valueOf('W'), Item.wheat,
					Character.valueOf('B'), new ItemStack(Item.dyePowder, 1, 15),
					Character.valueOf('S'), Item.silk,
					Character.valueOf('T'), Item.stick,
				} );
		
		GameRegistry.addRecipe(new ItemStack(safariNetItem, 1), new Object[]
				{
					" E ",
					"EGE",
					" E ",
					Character.valueOf('E'), Item.enderPearl,
					Character.valueOf('G'), Item.ghastTear,
				} );
		
		GameRegistry.addRecipe(new ItemStack(safariNetItem, 1), new Object[]
				{
					"SLS",
					" B ",
					"S S",
					Character.valueOf('S'), Item.silk,
					Character.valueOf('L'), Item.leather,
					Character.valueOf('B'), Item.slimeBall,
				} );
		
		GameRegistry.addRecipe(new ItemStack(factoryHammerItem, 1), new Object[]
				{
					"PPP",
					" S ",
					" S ",
					Character.valueOf('P'), plasticSheetItem,
					Character.valueOf('S'), Item.stick,
				} );
		
		GameRegistry.addRecipe(new ItemStack(factoryRoadBlock, 16), new Object[]
				{
					"BBB",
					"BPB",
					"BBB",
					Character.valueOf('P'), plasticSheetItem,
					Character.valueOf('B'), new ItemStack(Block.stoneBrick, 1, 0),
				} );
		
		GameRegistry.addRecipe(new ItemStack(factoryRoadBlock, 4, 1), new Object[]
				{
					"R R",
					" G ",
					"R R",
					Character.valueOf('R'), new ItemStack(factoryRoadBlock, 1, 0),
					Character.valueOf('G'), Block.redstoneLampIdle,
				} );
		
		GameRegistry.addShapelessRecipe(new ItemStack(factoryRoadBlock, 1, 4), new ItemStack(factoryRoadBlock, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(factoryRoadBlock, 1, 1), new ItemStack(factoryRoadBlock, 1, 4));
		
		if(vanillaOverrideIce.getBoolean(true))
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Block.ice, 1, 1), new ItemStack(Block.ice, 1, 0), new ItemStack(rawPlasticItem, 1));
		}
		
		GameRegistry.addRecipe(new ItemStack(blankRecordItem, 1), new Object[]
				{
					"RRR",
					"RPR",
					"RRR",
					Character.valueOf('R'), rawPlasticItem,
					Character.valueOf('P'), Item.paper,
				} );
		
		if(enableMachinePlanter.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 0), new Object[]
					{
						"GGG",
						"CPC",
						" M ",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('P'), Block.pistonBase,
						Character.valueOf('C'), Item.flowerPot,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineFisher.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 1), new Object[]
					{
						"GGG",
						"RRR",
						"BMB",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('R'), Item.fishingRod,
						Character.valueOf('B'), Item.bucketEmpty,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineHarvester.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 2), new Object[]
					{
						"GGG",
						"SXS",
						" M ",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('X'), Item.axeGold,
						Character.valueOf('S'), Item.shears,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineRancher.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 3), new Object[]
					{
						"GGG",
						"SBS",
						" M ",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('B'), Item.bucketEmpty,
						Character.valueOf('S'), Item.shears,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineFertilizer.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 4), new Object[]
					{
						"GGG",
						"LBL",
						" M ",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('L'), Item.leather,
						Character.valueOf('B'), Item.glassBottle,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineVet.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 5), new Object[]
					{
						"GGG",
						"SSS",
						"EME",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('E'), Item.spiderEye,
						Character.valueOf('S'), syringeEmptyItem,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineCollector.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock0, 8, 6), new Object[]
					{
						"GGG",
						" C ",
						" M ",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('C'), Block.chest,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineBreaker.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 7), new Object[]
					{
						"GGG",
						"PHS",
						" M ",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('P'), Item.pickaxeGold,
						Character.valueOf('H'), factoryHammerItem,
						Character.valueOf('S'), Item.shovelGold,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineWeather.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 8), new Object[]
					{
						"GGG",
						"BBB",
						"UMU",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('B'), Block.fenceIron,
						Character.valueOf('U'), Item.bucketEmpty,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineBoiler.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 9), new Object[]
					{
						"GGG",
						"FFF",
						" M ",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('F'), Block.stoneOvenIdle,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineSewer.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock0, 4, 10), new Object[]
					{
						"GGG",
						"BUB",
						"BMB",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('B'), Item.brick,
						Character.valueOf('U'), Item.bucketEmpty,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineComposter.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 11), new Object[]
					{
						"GGG",
						"PFP",
						" M ",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('P'), Block.pistonBase,
						Character.valueOf('F'), Block.stoneOvenIdle,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineBreeder.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 12), new Object[]
					{
						"GGG",
						"CAC",
						"PMP",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('P'), new ItemStack(Item.dyePowder, 1, 5),
						Character.valueOf('C'), Item.goldenCarrot,
						Character.valueOf('A'), Item.appleGold,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineGrinder.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 13), new Object[]
					{
						"GGG",
						"BSP",
						" M ",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('P'), Block.pistonBase,
						Character.valueOf('B'), Item.book,
						Character.valueOf('S'), Item.swordGold,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineEnchanter.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 14), new Object[]
					{
						"GGG",
						"BBB",
						"DMD",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('B'), Item.book,
						Character.valueOf('D'), Item.diamond,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineChronotyper.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 15), new Object[]
					{
						"GGG",
						"EEE",
						"PMP",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('E'), Item.emerald,
						Character.valueOf('P'), new ItemStack(Item.dyePowder, 1, 5),
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineEjector.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock1, 8, 0), new Object[]
					{
						"GGG",
						" D ",
						"RMR",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('D'), Block.dispenser,
						Character.valueOf('R'), Item.redstone,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineItemRouter.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock1, 8, 1), new Object[]
					{
						"GGG",
						"RCR",
						" M ",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('C'), Block.chest,
						Character.valueOf('R'), Item.redstoneRepeater,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineLiquidRouter.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock1, 8, 2), new Object[]
					{
						"GGG",
						"RBR",
						"BMB",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('B'), Item.bucketEmpty,
						Character.valueOf('R'), Item.redstoneRepeater,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineDeepStorageUnit.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock1, 4, 3), new Object[]
					{
						"GGG",
						"PPP",
						"EME",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('P'), Item.enderPearl,
						Character.valueOf('E'), Item.eyeOfEnder,
						Character.valueOf('M'), machineBaseItem,
					} );
			
			if(enableCheapDSU.getBoolean(false))
			{
				GameRegistry.addRecipe(new ItemStack(machineBlock1, 1, 3), new Object[]
						{
							"GGG",
							"CCC",
							"CMC",
							Character.valueOf('G'), plasticSheetItem,
							Character.valueOf('C'), Block.chest,
							Character.valueOf('M'), machineBaseItem,
						} );
			}
		}
		
		if(enableMachineLiquiCrafter.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock1, 1, 4), new Object[]
					{
						"GGG",
						"BWB",
						"FMF",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('B'), Item.bucketEmpty,
						Character.valueOf('W'), Block.workbench,
						Character.valueOf('F'), Item.itemFrame,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineLavaFabricator.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock1, 1, 5), new Object[]
					{
						"GGG",
						"OBO",
						"CMC",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('O'), Block.obsidian,
						Character.valueOf('B'), Item.blazeRod,
						Character.valueOf('C'), Item.magmaCream,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineOiLFabricator.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock1, 1, 6), new Object[]
					{
						"GGG",
						"OTO",
						"OMO",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('O'), Block.obsidian,
						Character.valueOf('T'), Block.tnt,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineAutoJukebox.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock1, 1, 7), new Object[]
					{
						"GGG",
						" J ",
						" M ",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('J'), Block.jukebox,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineUnifier.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock1, 1, 8), new Object[]
					{
						"GGG",
						"PBP",
						" M ",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('B'), Item.book,
						Character.valueOf('P'), Block.pumpkin,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		if(enableMachineAutoSpawner.getBoolean(true))
		{
			GameRegistry.addRecipe(new ItemStack(machineBlock1, 1, 9), new Object[]
					{
						"GGG",
						"ECE",
						"NMS",
						Character.valueOf('G'), plasticSheetItem,
						Character.valueOf('E'), Item.emerald,
						Character.valueOf('C'), Item.magmaCream,
						Character.valueOf('N'), Item.netherStalkSeeds,
						Character.valueOf('S'), Item.sugar,
						Character.valueOf('M'), machineBaseItem,
					} );
		}
		
		
		
		
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(conveyorBlock, 16, 16), new Object[]
				{
					"UUU",
					"RIR",
					Character.valueOf('U'), "itemRubber",
					Character.valueOf('R'), Item.redstone,
					Character.valueOf('I'), Item.ingotIron,
				} ));
		

		GameRegistry.addRecipe(new ItemStack(railPickupCargoBlock, 2), new Object[]
				{
					" C ",
					"SDS",
					"SSS",
					Character.valueOf('C'), Block.chest,
					Character.valueOf('S'), plasticSheetItem,
					Character.valueOf('D'), Block.railDetector
				} );
		
		GameRegistry.addRecipe(new ItemStack(railDropoffCargoBlock, 2), new Object[]
				{
					"SSS",
					"SDS",
					" C ",
					Character.valueOf('C'), Block.chest,
					Character.valueOf('S'), plasticSheetItem,
					Character.valueOf('D'), Block.railDetector
				} );
		
		GameRegistry.addRecipe(new ItemStack(railPickupPassengerBlock, 3), new Object[]
				{
					" L ",
					"SDS",
					"SSS",
					Character.valueOf('L'), Block.blockLapis,
					Character.valueOf('S'), plasticSheetItem,
					Character.valueOf('D'), Block.railDetector
				} );
		
		GameRegistry.addRecipe(new ItemStack(railDropoffPassengerBlock, 3), new Object[]
				{
					"SSS",
					"SDS",
					" L ",
					Character.valueOf('L'), Block.blockLapis,
					Character.valueOf('S'), plasticSheetItem,
					Character.valueOf('D'), Block.railDetector
				} );
		
		for(int i = 0; i < 16; i++)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(ceramicDyeItem, 4, i), new ItemStack(Item.clay), new ItemStack(Item.dyePowder, 1, 15 - i));
			GameRegistry.addShapelessRecipe(new ItemStack(factoryGlassBlock, 1, i), new ItemStack(Block.glass), new ItemStack(ceramicDyeItem, 1, i));
			GameRegistry.addShapelessRecipe(new ItemStack(factoryGlassPaneBlock, 1, i), new ItemStack(Block.thinGlass), new ItemStack(ceramicDyeItem, 1, i));
			GameRegistry.addShapelessRecipe(new ItemStack(conveyorBlock, 1, i), new ItemStack(conveyorBlock, 1, 16), new ItemStack(ceramicDyeItem, 1, i));
			
			GameRegistry.addRecipe(new ItemStack(factoryGlassPaneBlock, 16, i), new Object[]
					{
						"GGG",
						"GGG",
						Character.valueOf('G'), new ItemStack(factoryGlassBlock, 1, i)
					} );
		}
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
	public String getModFolder()
	{
		return "MineFactoryReloaded";
	}

	@Override
	public String getModVersion()
	{
		return version;
	}
}
