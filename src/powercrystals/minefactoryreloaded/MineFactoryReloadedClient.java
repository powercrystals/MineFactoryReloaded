package powercrystals.minefactoryreloaded;

import powercrystals.minefactoryreloaded.entity.EntitySafariNet;
import powercrystals.minefactoryreloaded.render.RenderEntitySafariNet;
import powercrystals.minefactoryreloaded.render.RendererConveyor;
import powercrystals.minefactoryreloaded.render.RendererFactoryGlassPane;
import powercrystals.minefactoryreloaded.setup.Machine;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class MineFactoryReloadedClient
{	
	private static MineFactoryReloadedClient instance;
	
	private static String[] colorNames = { "White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black" };

	public MineFactoryReloadedClient()
	{
		instance = this;
		load();
	}

	public static MineFactoryReloadedClient instance()
	{
		return instance;
	}

	private void load()
	{
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, Machine.Planter.getMeta()), "Planter");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, Machine.Fisher.getMeta()), "Fisher");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, Machine.Harvester.getMeta()), "Harvester");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, Machine.Rancher.getMeta()), "Rancher");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, Machine.Fertilizer.getMeta()), "Fertilizer");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, Machine.Vet.getMeta()), "Veterinary Station");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, Machine.ItemCollector.getMeta()), "Item Collector");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, Machine.BlockBreaker.getMeta()), "Block Breaker");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, Machine.WeatherCollector.getMeta()), "Weather Collector");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, Machine.SludgeBoiler.getMeta()), "Sludge Boiler");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, Machine.Sewer.getMeta()), "Sewage Collector");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, Machine.Composter.getMeta()), "Composter");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, Machine.Breeder.getMeta()), "Breeder");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, Machine.Grinder.getMeta()), "Grinder");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, Machine.AutoEnchanter.getMeta()), "Auto-Enchanter");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(0), 1, Machine.Chronotyper.getMeta()), "Chronotyper");;
		
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, Machine.Ejector.getMeta()), "Ejector");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, Machine.ItemRouter.getMeta()), "Item Router");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, Machine.LiquidRouter.getMeta()), "Liquid Router");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, Machine.DeepStorageUnit.getMeta()), "Deep Storage Unit");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, Machine.LiquiCrafter.getMeta()), "LiquiCrafter");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, Machine.LavaFabricator.getMeta()), "Lava Fabricator");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, Machine.OilFabricator.getMeta()), "Oil Fabricator");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, Machine.AutoJukebox.getMeta()), "Auto-Jukebox");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, Machine.Unifier.getMeta()), "Unifier");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, Machine.AutoSpawner.getMeta()), "Auto-Spawner");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, Machine.BioReactor.getMeta()), "Bio Reactor");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlocks.get(1), 1, Machine.BioFuelGenerator.getMeta()), "BioFuel Generator");

		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.conveyorBlock, 1, 16), "Conveyor Belt");

		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.factoryRoadBlock, 1, 0), "Road");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.factoryRoadBlock, 1, 1), "Road Light");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.factoryRoadBlock, 1, 2), "Road Light");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.factoryRoadBlock, 1, 3), "Road Light (Inverted)");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.factoryRoadBlock, 1, 4), "Road Light (Inverted)");

		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.factoryDecorativeBrickBlock, 1, 0), "Glowstone Bricks");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.factoryDecorativeBrickBlock, 1, 1), "Ice Bricks");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.factoryDecorativeBrickBlock, 1, 2), "Lapis Lazuli Bricks");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.factoryDecorativeBrickBlock, 1, 3), "Obsidian Bricks");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.factoryDecorativeBrickBlock, 1, 4), "Paved Stone Bricks");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.factoryDecorativeBrickBlock, 1, 5), "Snow Bricks");

		//LanguageRegistry.addName(new ItemStack(Block.ice, 1, 0), "Ice");
		LanguageRegistry.addName(new ItemStack(Block.ice, 1, 1), "Ice (Unmelting)");
		
		LanguageRegistry.addName(MineFactoryReloadedCore.rubberWoodBlock, "Rubber Wood");
		LanguageRegistry.addName(MineFactoryReloadedCore.rubberLeavesBlock, "Rubber Leaves");
		LanguageRegistry.addName(MineFactoryReloadedCore.rubberSaplingBlock, "Rubber Sapling");

		LanguageRegistry.addName(MineFactoryReloadedCore.railPickupCargoBlock, "Cargo Pickup Rail");
		LanguageRegistry.addName(MineFactoryReloadedCore.railDropoffCargoBlock, "Cargo Dropoff Rail");
		LanguageRegistry.addName(MineFactoryReloadedCore.railPickupPassengerBlock, "Passenger Pickup Rail");
		LanguageRegistry.addName(MineFactoryReloadedCore.railDropoffPassengerBlock, "Passenger Dropoff Rail");

		LanguageRegistry.addName(MineFactoryReloadedCore.factoryHammerItem, "Precision Sledgehammer");
		LanguageRegistry.addName(MineFactoryReloadedCore.milkItem, "Milk");
		LanguageRegistry.addName(MineFactoryReloadedCore.sludgeItem, "Sludge");
		LanguageRegistry.addName(MineFactoryReloadedCore.sewageItem, "Sewage");
		LanguageRegistry.addName(MineFactoryReloadedCore.mobEssenceItem, "Monster Essence");
		LanguageRegistry.addName(MineFactoryReloadedCore.fertilizerItem, "Industrial Fertilizer");
		LanguageRegistry.addName(MineFactoryReloadedCore.plasticSheetItem, "Plastic Sheets");
		LanguageRegistry.addName(MineFactoryReloadedCore.rawPlasticItem, "Raw Plastic");
		LanguageRegistry.addName(MineFactoryReloadedCore.rubberBarItem, "Rubber Bar");
		LanguageRegistry.addName(MineFactoryReloadedCore.sewageBucketItem, "Sewage Bucket");
		LanguageRegistry.addName(MineFactoryReloadedCore.sludgeBucketItem, "Sludge Bucket");
		LanguageRegistry.addName(MineFactoryReloadedCore.mobEssenceBucketItem, "Mob Essence Bucket");
		LanguageRegistry.addName(MineFactoryReloadedCore.syringeEmptyItem, "Empty Syringe");
		LanguageRegistry.addName(MineFactoryReloadedCore.syringeHealthItem, "Healing Syringe");
		LanguageRegistry.addName(MineFactoryReloadedCore.syringeGrowthItem, "Growth Hormone Syringe");
		LanguageRegistry.addName(MineFactoryReloadedCore.rawRubberItem, "Rubber");
		LanguageRegistry.addName(MineFactoryReloadedCore.machineBaseItem, "Factory Machine Block");
		LanguageRegistry.addName(MineFactoryReloadedCore.safariNetItem, "Safari Net");
		LanguageRegistry.addName(MineFactoryReloadedCore.blankRecordItem, "Blank Record");
		LanguageRegistry.addName(MineFactoryReloadedCore.syringeZombieItem, "Zombie Syringe");
		LanguageRegistry.addName(MineFactoryReloadedCore.safariNetSingleItem, "Safari Net (Single Use)");
		LanguageRegistry.addName(MineFactoryReloadedCore.bioFuelItem, "BioFuel");
		LanguageRegistry.addName(MineFactoryReloadedCore.bioFuelBucketItem, "BioFuel Bucket");
		LanguageRegistry.addName(MineFactoryReloadedCore.safariNetLauncherItem, "Safari Net Launcher");

		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 0), "Lapis Upgrade");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 1), "Iron Upgrade");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 2), "Tin Upgrade");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 3), "Copper Upgrade");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 4), "Bronze Upgrade");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 5), "Silver Upgrade");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 6), "Gold Upgrade");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 7), "Quartz Upgrade");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 8), "Diamond Upgrade");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 9), "Platinum Upgrade");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.upgradeItem, 1, 10), "Emerald Upgrade");
		
		for(int i = 0; i < 16; i++)
		{
			LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.factoryGlassBlock, 1, i), colorNames[i] + " Stained Glass");
			LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.factoryGlassPaneBlock, 1, i), colorNames[i] + " Stained Glass Pane");
			LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.ceramicDyeItem, 1, i), colorNames[i] + " Ceramic Dye");
			LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.conveyorBlock, 1, i), colorNames[i] + " Conveyor Belt");
		}
		
		MineFactoryReloadedCore.renderIdConveyor = RenderingRegistry.getNextAvailableRenderId();
		MineFactoryReloadedCore.renderIdFactoryGlassPane = RenderingRegistry.getNextAvailableRenderId();
		
		RenderingRegistry.registerBlockHandler(MineFactoryReloadedCore.renderIdConveyor, new RendererConveyor());
		RenderingRegistry.registerBlockHandler(MineFactoryReloadedCore.renderIdFactoryGlassPane, new RendererFactoryGlassPane());
		RenderingRegistry.registerEntityRenderingHandler(EntitySafariNet.class, new RenderEntitySafariNet());
	}
		/*if(MineFactoryReloadedCore.animateBlockFaces.getBoolean(true))
		{
			registerAnimation(MineFactoryReloadedCore.conveyorTexture, MineFactoryReloadedCore.animationFolder + "Conveyor.png", MineFactoryReloadedCore.terrainTexture);
			registerAnimation(48, MineFactoryReloadedCore.animationFolder + "Conveyor-White.png", MineFactoryReloadedCore.terrainTexture);
			registerAnimation(49, MineFactoryReloadedCore.animationFolder + "Conveyor-Orange.png", MineFactoryReloadedCore.terrainTexture);
			registerAnimation(50, MineFactoryReloadedCore.animationFolder + "Conveyor-Magenta.png", MineFactoryReloadedCore.terrainTexture);
			registerAnimation(51, MineFactoryReloadedCore.animationFolder + "Conveyor-LightBlue.png", MineFactoryReloadedCore.terrainTexture);
			registerAnimation(52, MineFactoryReloadedCore.animationFolder + "Conveyor-Yellow.png", MineFactoryReloadedCore.terrainTexture);
			registerAnimation(53, MineFactoryReloadedCore.animationFolder + "Conveyor-Lime.png", MineFactoryReloadedCore.terrainTexture);
			registerAnimation(54, MineFactoryReloadedCore.animationFolder + "Conveyor-Pink.png", MineFactoryReloadedCore.terrainTexture);
			registerAnimation(55, MineFactoryReloadedCore.animationFolder + "Conveyor-Gray.png", MineFactoryReloadedCore.terrainTexture);
			registerAnimation(56, MineFactoryReloadedCore.animationFolder + "Conveyor-LightGray.png", MineFactoryReloadedCore.terrainTexture);
			registerAnimation(57, MineFactoryReloadedCore.animationFolder + "Conveyor-Cyan.png", MineFactoryReloadedCore.terrainTexture);
			registerAnimation(58, MineFactoryReloadedCore.animationFolder + "Conveyor-Purple.png", MineFactoryReloadedCore.terrainTexture);
			registerAnimation(59, MineFactoryReloadedCore.animationFolder + "Conveyor-Blue.png", MineFactoryReloadedCore.terrainTexture);
			registerAnimation(60, MineFactoryReloadedCore.animationFolder + "Conveyor-Brown.png", MineFactoryReloadedCore.terrainTexture);
			registerAnimation(61, MineFactoryReloadedCore.animationFolder + "Conveyor-Green.png", MineFactoryReloadedCore.terrainTexture);
			registerAnimation(62, MineFactoryReloadedCore.animationFolder + "Conveyor-Red.png", MineFactoryReloadedCore.terrainTexture);
			registerAnimation(63, MineFactoryReloadedCore.animationFolder + "Conveyor-Black.png", MineFactoryReloadedCore.terrainTexture);
			
			registerMachineAnimation(Machine.Harvester, "Harvester.png");
			registerMachineAnimation(Machine.Rancher, "Rancher.png");
			registerMachineAnimation(Machine.BlockBreaker, "BlockBreaker.png");
			registerMachineAnimation(Machine.Fertilizer, "Fertilizer.png");
			registerMachineAnimation(Machine.Vet, "Vet.png");
			registerMachineAnimation(Machine.Breeder, "Breeder.png");
			registerMachineAnimation(Machine.Grinder, "Mobgrinder.png");
			registerMachineAnimation(Machine.Chronotyper, "Chronotyper.png");
			
			registerMachineAnimation(Machine.OilFabricator, "OilFabricator.png");
			registerAnimation(Machine.OilFabricator.getMeta() * 16 + 3, MineFactoryReloadedCore.animationFolder + "OilFabricator.png", MineFactoryReloadedCore.machineBlocks.get(1).getTextureFile());
			registerAnimation(Machine.OilFabricator.getMeta() * 16 + 4, MineFactoryReloadedCore.animationFolder + "OilFabricator.png", MineFactoryReloadedCore.machineBlocks.get(1).getTextureFile());
			registerAnimation(Machine.OilFabricator.getMeta() * 16 + 5, MineFactoryReloadedCore.animationFolder + "OilFabricator.png", MineFactoryReloadedCore.machineBlocks.get(1).getTextureFile());
			
			registerAnimation(2, MineFactoryReloadedCore.animationFolder + "liquids/Milk_Still.png", MineFactoryReloadedCore.itemTexture);
			registerAnimation(3, MineFactoryReloadedCore.animationFolder + "liquids/Sludge_Still.png", MineFactoryReloadedCore.itemTexture);
			registerAnimation(4, MineFactoryReloadedCore.animationFolder + "liquids/Sewage_Still.png", MineFactoryReloadedCore.itemTexture);
			registerAnimation(5, MineFactoryReloadedCore.animationFolder + "liquids/MobEssence_Still.png", MineFactoryReloadedCore.itemTexture);
			registerAnimation(46, MineFactoryReloadedCore.animationFolder + "liquids/BioFuel_Still.png", MineFactoryReloadedCore.itemTexture);
		}
	}
	
	private void registerMachineAnimation(Machine machine, String animation)
	{
		registerAnimation(machine.getMeta() * 16 + 2, MineFactoryReloadedCore.animationFolder + animation, MineFactoryReloadedCore.machineBlocks.get(machine.getBlockIndex()).getTextureFile());
	}
	
	private void registerAnimation(int index, String animation, String targetTexture)
	{
		TexturePackList tpl = FMLClientHandler.instance().getClient().texturePackList;
		InputStream s;
		
		s = tpl.getSelectedTexturePack().getResourceAsStream(animation);
		if(s == null)
		{
			s = (net.minecraft.client.Minecraft.class).getResourceAsStream(animation);
		}
		
		BufferedImage bufferedimage;
		try
		{
			bufferedimage = ImageIO.read(s);
			TextureFXManager.instance().addAnimation(new ModTextureAnimation(index, 1, targetTexture, bufferedimage, 1));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}*/
}
