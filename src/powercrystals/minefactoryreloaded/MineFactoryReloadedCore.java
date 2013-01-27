package powercrystals.minefactoryreloaded;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
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
import powercrystals.minefactoryreloaded.animals.ItemGrowthSyringe;
import powercrystals.minefactoryreloaded.animals.ItemHealthSyringe;
import powercrystals.minefactoryreloaded.animals.ItemSafariNet;
import powercrystals.minefactoryreloaded.animals.TileEntityBreeder;
import powercrystals.minefactoryreloaded.animals.TileEntityChronotyper;
import powercrystals.minefactoryreloaded.animals.TileEntityGrinder;
import powercrystals.minefactoryreloaded.animals.TileEntityRancher;
import powercrystals.minefactoryreloaded.animals.TileEntityVet;
import powercrystals.minefactoryreloaded.api.*;
import powercrystals.minefactoryreloaded.core.BlockFactoryMachine0;
import powercrystals.minefactoryreloaded.core.BlockFactoryMachine1;
import powercrystals.minefactoryreloaded.core.ItemBlockFactoryMachine1;
import powercrystals.minefactoryreloaded.core.ItemFactory;
import powercrystals.minefactoryreloaded.core.ItemFactoryHammer;
import powercrystals.minefactoryreloaded.core.ItemBlockFactoryMachine0;
import powercrystals.minefactoryreloaded.decorative.BlockFactoryGlass;
import powercrystals.minefactoryreloaded.decorative.BlockFactoryGlassPane;
import powercrystals.minefactoryreloaded.decorative.ItemBlockFactoryGlass;
import powercrystals.minefactoryreloaded.decorative.ItemBlockFactoryGlassPane;
import powercrystals.minefactoryreloaded.decorative.ItemCeramicDye;
import powercrystals.minefactoryreloaded.farmables.FertilizableCocoa;
import powercrystals.minefactoryreloaded.farmables.FertilizableCropPlant;
import powercrystals.minefactoryreloaded.farmables.FertilizableGiantMushroom;
import powercrystals.minefactoryreloaded.farmables.FertilizableGrass;
import powercrystals.minefactoryreloaded.farmables.FertilizableNetherWart;
import powercrystals.minefactoryreloaded.farmables.FertilizableRubberSapling;
import powercrystals.minefactoryreloaded.farmables.FertilizableSapling;
import powercrystals.minefactoryreloaded.farmables.FertilizableStemPlants;
import powercrystals.minefactoryreloaded.farmables.FertilizerStandard;
import powercrystals.minefactoryreloaded.farmables.GrindableSheep;
import powercrystals.minefactoryreloaded.farmables.GrindableSkeleton;
import powercrystals.minefactoryreloaded.farmables.GrindableStandard;
import powercrystals.minefactoryreloaded.farmables.GrindableZombiePigman;
import powercrystals.minefactoryreloaded.farmables.HarvestableCocoa;
import powercrystals.minefactoryreloaded.farmables.HarvestableCropPlant;
import powercrystals.minefactoryreloaded.farmables.HarvestableMushroom;
import powercrystals.minefactoryreloaded.farmables.HarvestableNetherWart;
import powercrystals.minefactoryreloaded.farmables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.HarvestableStemPlant;
import powercrystals.minefactoryreloaded.farmables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.farmables.HarvestableVine;
import powercrystals.minefactoryreloaded.farmables.HarvestableWood;
import powercrystals.minefactoryreloaded.farmables.PlantableCocoa;
import powercrystals.minefactoryreloaded.farmables.PlantableCropPlant;
import powercrystals.minefactoryreloaded.farmables.PlantableNetherWart;
import powercrystals.minefactoryreloaded.farmables.PlantableStandard;
import powercrystals.minefactoryreloaded.farmables.RanchableCow;
import powercrystals.minefactoryreloaded.farmables.RanchableMooshroom;
import powercrystals.minefactoryreloaded.farmables.RanchableSheep;
import powercrystals.minefactoryreloaded.gui.MFRGUIHandler;
import powercrystals.minefactoryreloaded.net.ClientPacketHandler;
import powercrystals.minefactoryreloaded.net.ConnectionHandler;
import powercrystals.minefactoryreloaded.net.IMFRProxy;
import powercrystals.minefactoryreloaded.net.ServerPacketHandler;
import powercrystals.minefactoryreloaded.plants.BlockRubberLeaves;
import powercrystals.minefactoryreloaded.plants.BlockRubberSapling;
import powercrystals.minefactoryreloaded.plants.BlockRubberWood;
import powercrystals.minefactoryreloaded.plants.TileEntityFertilizer;
import powercrystals.minefactoryreloaded.plants.TileEntityHarvester;
import powercrystals.minefactoryreloaded.plants.TileEntityPlanter;
import powercrystals.minefactoryreloaded.processing.TileEntityBlockBreaker;
import powercrystals.minefactoryreloaded.processing.TileEntityComposter;
import powercrystals.minefactoryreloaded.processing.TileEntityAutoEnchanter;
import powercrystals.minefactoryreloaded.processing.TileEntityFisher;
import powercrystals.minefactoryreloaded.processing.TileEntityLavaFabricator;
import powercrystals.minefactoryreloaded.processing.TileEntityOilFabricator;
import powercrystals.minefactoryreloaded.processing.TileEntitySewer;
import powercrystals.minefactoryreloaded.processing.TileEntitySludgeBoiler;
import powercrystals.minefactoryreloaded.processing.TileEntityWeather;
import powercrystals.minefactoryreloaded.rails.BlockRailCargoDropoff;
import powercrystals.minefactoryreloaded.rails.BlockRailCargoPickup;
import powercrystals.minefactoryreloaded.rails.BlockRailPassengerDropoff;
import powercrystals.minefactoryreloaded.rails.BlockRailPassengerPickup;
import powercrystals.minefactoryreloaded.transport.BlockConveyor;
import powercrystals.minefactoryreloaded.transport.TileEntityCollector;
import powercrystals.minefactoryreloaded.transport.TileEntityConveyor;
import powercrystals.minefactoryreloaded.transport.TileEntityDeepStorageUnit;
import powercrystals.minefactoryreloaded.transport.TileEntityEjector;
import powercrystals.minefactoryreloaded.transport.TileEntityItemRouter;
import powercrystals.minefactoryreloaded.transport.TileEntityLiquidRouter;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarted;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
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
	public static final String version = "1.4.6R2.1.0B4";
	public static final String modName = "Minefactory Reloaded";
	
	private static final String textureFolder = "/powercrystals/minefactoryreloaded/textures/";
	public static final String terrainTexture = textureFolder + "terrain_0.png";
	public static final String machine0Texture = textureFolder + "machine_0.png";
	public static final String machine1Texture = textureFolder + "machine_1.png";
	public static final String itemTexture = textureFolder + "items_0.png";
	public static final String animationFolder = textureFolder + "animations/";
	public static final String guiFolder = textureFolder + "gui/";
	
	public static Block machineBlock0;
	public static Block machineBlock1;
	
	public static Block conveyorBlock;
	
	public static Block factoryGlassBlock;
	public static Block factoryGlassPaneBlock;
	
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

	public static Property animateBlockFaces;
	public static Property treeSearchMaxVertical;
	public static Property treeSearchMaxHorizontal;
	public static Property playSounds;
	public static Property machinesCanDropInChests;
	public static Property verticalHarvestSearchMaxVertical;
	public static Property rubberTreeWorldGen;
	public static Property enableBonemealFertilizing;
	
	public static Property passengerRailSearchMaxHorizontal;
	public static Property passengerRailSearchMaxVertical;

	private static MineFactoryReloadedCore instance;
	
	public static int oilLiquidId = -1;

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

		conveyorBlock = new BlockConveyor(conveyorBlockId.getInt(), conveyorOffTexture);
		machineBlock0 = new BlockFactoryMachine0(machineBlock0Id.getInt());
		machineBlock1 = new BlockFactoryMachine1(machineBlock1Id.getInt());
		factoryGlassBlock = new BlockFactoryGlass(factoryGlassBlockId.getInt(), 16);
		factoryGlassPaneBlock = new BlockFactoryGlassPane(factoryGlassPaneBlockId.getInt(), 16, 32);
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
		syringeHealthItem = (new ItemHealthSyringe()).setIconIndex(14).setItemName("syringeHealthItem").setContainerItem(syringeEmptyItem);
		syringeGrowthItem = (new ItemGrowthSyringe()).setIconIndex(15).setItemName("syringeGrowthItem").setContainerItem(syringeEmptyItem);
		rawRubberItem = (new ItemFactory(rawRubberItemId.getInt())).setIconIndex(16).setItemName("rawRubberItem");
		machineBaseItem = (new ItemFactory(machineBaseItemId.getInt())).setIconIndex(17).setItemName("factoryMachineBlock");
		safariNetItem = (new ItemSafariNet()).setIconIndex(18).setItemName("safariNetItem");
		ceramicDyeItem = (new ItemCeramicDye(ceramicDyeId.getInt())).setIconIndex(22).setItemName("ceramicDyeItem");

		GameRegistry.registerBlock(machineBlock0, ItemBlockFactoryMachine0.class, "blockMachine");
		GameRegistry.registerBlock(machineBlock1, ItemBlockFactoryMachine1.class, "blockMachine1");
		GameRegistry.registerBlock(conveyorBlock, "blockConveyor");
		GameRegistry.registerBlock(factoryGlassBlock, ItemBlockFactoryGlass.class, "blockFactoryGlass");
		GameRegistry.registerBlock(factoryGlassPaneBlock, ItemBlockFactoryGlassPane.class, "blockFactoryGlassPane");
		GameRegistry.registerBlock(rubberWoodBlock, "blockRubberWood");
		GameRegistry.registerBlock(rubberLeavesBlock, "blockRubberLeaves");
		GameRegistry.registerBlock(rubberSaplingBlock, "blockRubberSapling");
		GameRegistry.registerBlock(railPickupCargoBlock, "blockRailPickupCargo");
		GameRegistry.registerBlock(railDropoffCargoBlock, "blockRailDropoffCargo");
		GameRegistry.registerBlock(railPickupPassengerBlock, "blockRailPickupPassenger");
		GameRegistry.registerBlock(railDropoffPassengerBlock, "blockRailDropoffPassenger");
		
		Block.setBurnProperties(rubberWoodBlock.blockID, 4, 20);
		Block.setBurnProperties(rubberLeavesBlock.blockID, 30, 20);

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
		GameRegistry.registerTileEntity(TileEntityLavaFabricator.class, "factoryLavaFabricator");
		GameRegistry.registerTileEntity(TileEntityOilFabricator.class, "factoryOilFabricator");

		MinecraftForge.EVENT_BUS.register(instance);

		registerFarmables();
		
		proxy.load();
		
		NetworkRegistry.instance().registerGuiHandler(this, new MFRGUIHandler());
		
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
		
		for(ItemStack s : OreDictionary.getOres("itemRubber"))
		{
			FurnaceRecipes.smelting().addSmelting(s.itemID, s.getItemDamage(), new ItemStack(rawPlasticItem), 0.3F);
		}
	}
	
	@ServerStarted
	public void serverStarted(FMLServerStartedEvent event)
	{
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

	private void registerFarmables()
	{
		FarmingRegistry.registerPlantable(new PlantableStandard(Block.sapling.blockID, Block.sapling.blockID));
		FarmingRegistry.registerPlantable(new PlantableStandard(Item.reed.shiftedIndex, Block.reed.blockID));
		FarmingRegistry.registerPlantable(new PlantableStandard(Block.cactus.blockID, Block.cactus.blockID));
		FarmingRegistry.registerPlantable(new PlantableStandard(Item.pumpkinSeeds.shiftedIndex, Block.pumpkinStem.blockID));
		FarmingRegistry.registerPlantable(new PlantableStandard(Item.melonSeeds.shiftedIndex, Block.melonStem.blockID));
		FarmingRegistry.registerPlantable(new PlantableStandard(Block.mushroomBrown.blockID, Block.mushroomBrown.blockID));
		FarmingRegistry.registerPlantable(new PlantableStandard(Block.mushroomRed.blockID, Block.mushroomRed.blockID));
		FarmingRegistry.registerPlantable(new PlantableCropPlant(Item.seeds.shiftedIndex, Block.crops.blockID));
		FarmingRegistry.registerPlantable(new PlantableCropPlant(Item.carrot.shiftedIndex, Block.carrot.blockID));
		FarmingRegistry.registerPlantable(new PlantableCropPlant(Item.potato.shiftedIndex, Block.potato.blockID));
		FarmingRegistry.registerPlantable(new PlantableNetherWart());
		FarmingRegistry.registerPlantable(new PlantableCocoa());
		FarmingRegistry.registerPlantable(new PlantableStandard(rubberSaplingBlock.blockID, rubberSaplingBlock.blockID));

		FarmingRegistry.registerHarvestable(new HarvestableWood());
		FarmingRegistry.registerHarvestable(new HarvestableTreeLeaves(Block.leaves.blockID));
		FarmingRegistry.registerHarvestable(new HarvestableStandard(Block.reed.blockID, HarvestType.LeaveBottom));
		FarmingRegistry.registerHarvestable(new HarvestableStandard(Block.cactus.blockID, HarvestType.LeaveBottom));
		FarmingRegistry.registerHarvestable(new HarvestableStandard(Block.plantRed.blockID, HarvestType.Normal));
		FarmingRegistry.registerHarvestable(new HarvestableStandard(Block.plantYellow.blockID, HarvestType.Normal));
		FarmingRegistry.registerHarvestable(new HarvestableStandard(Block.tallGrass.blockID, HarvestType.Normal));
		FarmingRegistry.registerHarvestable(new HarvestableStandard(Block.mushroomCapBrown.blockID, HarvestType.Tree));
		FarmingRegistry.registerHarvestable(new HarvestableStandard(Block.mushroomCapRed.blockID, HarvestType.Tree));
		FarmingRegistry.registerHarvestable(new HarvestableMushroom(Block.mushroomBrown.blockID));
		FarmingRegistry.registerHarvestable(new HarvestableMushroom(Block.mushroomRed.blockID));
		FarmingRegistry.registerHarvestable(new HarvestableStemPlant(Block.pumpkin.blockID, HarvestType.Normal));
		FarmingRegistry.registerHarvestable(new HarvestableStemPlant(Block.melon.blockID, HarvestType.Normal));
		FarmingRegistry.registerHarvestable(new HarvestableCropPlant(Block.crops.blockID));
		FarmingRegistry.registerHarvestable(new HarvestableCropPlant(Block.carrot.blockID));
		FarmingRegistry.registerHarvestable(new HarvestableCropPlant(Block.potato.blockID));
		FarmingRegistry.registerHarvestable(new HarvestableVine());
		FarmingRegistry.registerHarvestable(new HarvestableNetherWart());
		FarmingRegistry.registerHarvestable(new HarvestableCocoa());
		FarmingRegistry.registerHarvestable(new HarvestableStandard(rubberWoodBlock.blockID, HarvestType.Tree));
		FarmingRegistry.registerHarvestable(new HarvestableTreeLeaves(rubberLeavesBlock.blockID));

		FarmingRegistry.registerFertilizable(new FertilizableSapling());
		FarmingRegistry.registerFertilizable(new FertilizableCropPlant(Block.crops.blockID));
		FarmingRegistry.registerFertilizable(new FertilizableCropPlant(Block.carrot.blockID));
		FarmingRegistry.registerFertilizable(new FertilizableCropPlant(Block.potato.blockID));
		FarmingRegistry.registerFertilizable(new FertilizableGiantMushroom(Block.mushroomBrown.blockID));
		FarmingRegistry.registerFertilizable(new FertilizableGiantMushroom(Block.mushroomRed.blockID));
		FarmingRegistry.registerFertilizable(new FertilizableStemPlants(Block.pumpkinStem.blockID));
		FarmingRegistry.registerFertilizable(new FertilizableStemPlants(Block.melonStem.blockID));
		FarmingRegistry.registerFertilizable(new FertilizableNetherWart());
		FarmingRegistry.registerFertilizable(new FertilizableCocoa());
		FarmingRegistry.registerFertilizable(new FertilizableGrass());
		FarmingRegistry.registerFertilizable(new FertilizableRubberSapling());

		FarmingRegistry.registerFertilizer(new FertilizerStandard(fertilizerItem.shiftedIndex, 0));
		if(enableBonemealFertilizing.getBoolean(false))
		{
			FarmingRegistry.registerFertilizer(new FertilizerStandard(Item.dyePowder.shiftedIndex, 15));
		}

		FarmingRegistry.registerRanchable(new RanchableCow());
		FarmingRegistry.registerRanchable(new RanchableMooshroom());
		FarmingRegistry.registerRanchable(new RanchableSheep());
		
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityChicken.class, new ItemStack[] { new ItemStack(Item.feather), new ItemStack(Item.chickenRaw), new ItemStack(Item.egg) }));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityCow.class, new ItemStack[] { new ItemStack(Item.leather), new ItemStack(Item.beefRaw) }));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityMooshroom.class, new ItemStack[] { new ItemStack(Item.leather), new ItemStack(Item.beefRaw) }));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityOcelot.class, new ItemStack[] { new ItemStack(Item.fishRaw), new ItemStack(Item.silk) }));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityPig.class, new ItemStack(Item.porkRaw)));
		FarmingRegistry.registerGrindable(new GrindableSheep());
		FarmingRegistry.registerGrindable(new GrindableStandard(EntitySquid.class, new ItemStack(Item.dyePowder)));

		FarmingRegistry.registerGrindable(new GrindableStandard(EntityEnderman.class, new ItemStack(Item.enderPearl)));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityWolf.class, new ItemStack(Item.bone)));
		FarmingRegistry.registerGrindable(new GrindableZombiePigman());

		FarmingRegistry.registerGrindable(new GrindableStandard(EntityBlaze.class, new ItemStack(Item.blazeRod)));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityCaveSpider.class, new ItemStack[] {new ItemStack(Item.silk), new ItemStack(Item.spiderEye) }));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityCreeper.class, new ItemStack(Item.gunpowder)));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityGhast.class, new ItemStack[] {new ItemStack(Item.gunpowder), new ItemStack(Item.ghastTear) }));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityMagmaCube.class, new ItemStack(Item.magmaCream)));
		FarmingRegistry.registerGrindable(new GrindableSkeleton());
		FarmingRegistry.registerGrindable(new GrindableStandard(EntitySlime.class, new ItemStack(Item.slimeBall)));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntitySpider.class, new ItemStack[] {new ItemStack(Item.silk), new ItemStack(Item.spiderEye) }));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityWitch.class, new ItemStack[] { 
			new ItemStack(Item.glassBottle, 2),
			new ItemStack(Item.lightStoneDust, 2),
			new ItemStack(Item.gunpowder, 2),
			new ItemStack(Item.redstone, 2),
			new ItemStack(Item.spiderEye, 2),
			new ItemStack(Item.stick, 2),
			new ItemStack(Item.sugar, 2)
		}));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityZombie.class, new ItemStack(Item.rottenFlesh)));
		
		FarmingRegistry.registerSludgeDrop(25, new ItemStack(Block.sand));
		FarmingRegistry.registerSludgeDrop(20, new ItemStack(Block.dirt));
		FarmingRegistry.registerSludgeDrop(15, new ItemStack(Block.blockClay));
		FarmingRegistry.registerSludgeDrop(1, new ItemStack(Block.slowSand));
		
		registerRecipes();
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

		c.save();
	}

	public enum Machine
	{
		Planter, Fisher, Harvester, Fertilizer, Rancher, Vet, Collector, Breaker, Weather, Boiler, Sewer, Composter, Breeder, Grinder, Enchanter, Chronotyper,
		Ejector, ItemRouter, LiquidRouter, DeepStorageUnit, LiquiCrafter, OilFabricator, LavaFabricator
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
		
		FurnaceRecipes.smelting().addSmelting(rawRubberItem.shiftedIndex, 0, new ItemStack(rubberBarItem), 0.1F);
		FurnaceRecipes.smelting().addSmelting(rubberWoodBlock.blockID, 0, new ItemStack(Item.coal, 1, 1), 0.1F);
		
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
		
		GameRegistry.addRecipe(new ItemStack(fertilizerItem, 6), new Object[]
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
		
		GameRegistry.addRecipe(new ItemStack(factoryHammerItem, 1), new Object[]
				{
					"PPP",
					" S ",
					" S ",
					Character.valueOf('P'), plasticSheetItem,
					Character.valueOf('S'), Item.stick,
				} );
		
		// planter
		GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 0), new Object[]
				{
					"GGG",
					"CPC",
					" M ",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('P'), Block.pistonBase,
					Character.valueOf('C'), Item.flowerPot,
					Character.valueOf('M'), machineBaseItem,
				} );
		
		// fisher
		GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 1), new Object[]
				{
					"GGG",
					"RRR",
					"BMB",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('R'), Item.fishingRod,
					Character.valueOf('B'), Item.bucketEmpty,
					Character.valueOf('M'), machineBaseItem,
				} );
		
		// harvester
		GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 2), new Object[]
				{
					"GGG",
					"SXS",
					" M ",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('X'), Item.axeGold,
					Character.valueOf('S'), Item.shears,
					Character.valueOf('M'), machineBaseItem,
				} );
		
		// rancher
		GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 3), new Object[]
				{
					"GGG",
					"SBS",
					" M ",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('B'), Item.bucketEmpty,
					Character.valueOf('S'), Item.shears,
					Character.valueOf('M'), machineBaseItem,
				} );
		
		// fertilizer
		GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 4), new Object[]
				{
					"GGG",
					"LBL",
					" M ",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('L'), Item.leather,
					Character.valueOf('B'), Item.glassBottle,
					Character.valueOf('M'), machineBaseItem,
				} );
		
		// vet
		GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 5), new Object[]
				{
					"GGG",
					"SSS",
					"EME",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('E'), Item.spiderEye,
					Character.valueOf('S'), syringeEmptyItem,
					Character.valueOf('M'), machineBaseItem,
				} );
		
		// collector
		GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 6), new Object[]
				{
					"GGG",
					" C ",
					" M ",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('C'), Block.chest,
					Character.valueOf('M'), machineBaseItem,
				} );
		
		// breaker
		GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 7), new Object[]
				{
					"GGG",
					"PHS",
					" M ",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('P'), Item.pickaxeGold,
					Character.valueOf('H'), factoryHammerItem,
					Character.valueOf('S'), Item.shovelGold,
					Character.valueOf('M'), machineBaseItem,
				} );
		
		// weather
		GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 8), new Object[]
				{
					"GGG",
					"BBB",
					"UMU",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('B'), Block.fenceIron,
					Character.valueOf('U'), Item.bucketEmpty,
					Character.valueOf('M'), machineBaseItem,
				} );
		
		// boiler
		GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 9), new Object[]
				{
					"GGG",
					"FFF",
					" M ",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('F'), Block.stoneOvenIdle,
					Character.valueOf('M'), machineBaseItem,
				} );
		
		// sewer
		GameRegistry.addRecipe(new ItemStack(machineBlock0, 4, 10), new Object[]
				{
					"GGG",
					"BUB",
					"BMB",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('B'), Item.brick,
					Character.valueOf('U'), Item.bucketEmpty,
					Character.valueOf('M'), machineBaseItem,
				} );
		
		// composter
		GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 11), new Object[]
				{
					"GGG",
					"PFP",
					" M ",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('P'), Block.pistonBase,
					Character.valueOf('F'), Block.stoneOvenIdle,
					Character.valueOf('M'), machineBaseItem,
				} );
		
		// breeder
		GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 12), new Object[]
				{
					"GGG",
					"CAC",
					"PMP",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('P'), new ItemStack(Item.dyePowder, 1, 5),
					Character.valueOf('C'), Item.goldenCarrot,
					Character.valueOf('A'), Item.appleGold,
					Character.valueOf('M'), machineBaseItem,
				} );
		
		// grinder
		GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 13), new Object[]
				{
					"GGG",
					"BSP",
					" M ",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('P'), Block.pistonBase,
					Character.valueOf('B'), Item.book,
					Character.valueOf('S'), Item.swordGold,
					Character.valueOf('M'), machineBaseItem,
				} );
		
		// enchanter
		GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 14), new Object[]
				{
					"GGG",
					"BBB",
					"DMD",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('B'), Item.book,
					Character.valueOf('D'), Item.diamond,
					Character.valueOf('M'), machineBaseItem,
				} );
		
		// chronotyper
		GameRegistry.addRecipe(new ItemStack(machineBlock0, 1, 15), new Object[]
				{
					"GGG",
					"EEE",
					"PMP",
					Character.valueOf('G'), Item.ingotGold,
					Character.valueOf('E'), Item.emerald,
					Character.valueOf('P'), new ItemStack(Item.dyePowder, 1, 5),
					Character.valueOf('M'), machineBaseItem,
				} );
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(conveyorBlock, 16), new Object[]
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
