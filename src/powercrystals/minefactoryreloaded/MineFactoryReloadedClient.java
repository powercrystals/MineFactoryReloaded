package powercrystals.minefactoryreloaded;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore.Machine;
import powercrystals.minefactoryreloaded.render.TextureLiquidStillFX;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.client.texturepacks.TexturePackList;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModTextureAnimation;
import net.minecraftforge.client.MinecraftForgeClient;

public class MineFactoryReloadedClient
{	
	private static MineFactoryReloadedClient instance;

	public MineFactoryReloadedClient()
	{
		instance = this;
		load();
	}

	public static MineFactoryReloadedClient instance()
	{
		return instance;
	}
	
	public int renderId = 1000;

	private void load()
	{
		MinecraftForgeClient.preloadTexture(MineFactoryReloadedCore.terrainTexture);
		MinecraftForgeClient.preloadTexture(MineFactoryReloadedCore.itemTexture);

		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Planter)), "Planter");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Fisher)), "Fisher");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Harvester)), "Harvester");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Rancher)), "Rancher");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Fertilizer)), "Fertilizer");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Vet)), "Veterinary Station");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Collector)), "Item Collector");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Breaker)), "Block Breaker");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Weather)), "Weather Collector");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Boiler)), "Sludge Boiler");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Sewer)), "Sewage Collector");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Composter)), "Composter");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Breeder)), "Breeder");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Grinder)), "Grinder");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Enchanter)), "Auto-Enchanter");
		LanguageRegistry.addName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Chronotyper)), "Chronotyper");

		LanguageRegistry.addName(MineFactoryReloadedCore.conveyorBlock, "Conveyor Belt");
		LanguageRegistry.addName(MineFactoryReloadedCore.rubberWoodBlock, "Rubber Wood");
		LanguageRegistry.addName(MineFactoryReloadedCore.rubberLeavesBlock, "Rubber Leaves");
		LanguageRegistry.addName(MineFactoryReloadedCore.rubberSaplingBlock, "Rubber Sapling");

		LanguageRegistry.addName(MineFactoryReloadedCore.railPickupCargoBlock, "Cargo Pickup Rail");
		LanguageRegistry.addName(MineFactoryReloadedCore.railDropoffCargoBlock, "Cargo Dropoff Rail");
		LanguageRegistry.addName(MineFactoryReloadedCore.railPickupPassengerBlock, "Passenger Pickup Rail");
		LanguageRegistry.addName(MineFactoryReloadedCore.railDropoffPassengerBlock, "Passenger Dropoff Rail");

		LanguageRegistry.addName(MineFactoryReloadedCore.factoryHammerItem, "Precision Sledgehammer");
		LanguageRegistry.addName(MineFactoryReloadedCore.milkItem, "Spilled Milk");
		LanguageRegistry.addName(MineFactoryReloadedCore.sludgeItem, "Spilled Sludge");
		LanguageRegistry.addName(MineFactoryReloadedCore.sewageItem, "Spilled Sewage");
		LanguageRegistry.addName(MineFactoryReloadedCore.mobEssenceItem, "Spilled Monster Essence");
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

		if(MineFactoryReloadedCore.animateBlockFaces.getBoolean(true))
		{
			registerAnimation(MineFactoryReloadedCore.conveyorTexture, MineFactoryReloadedCore.animationFolder + "Conveyor.png", MineFactoryReloadedCore.terrainTexture);
			
			registerMachineAnimation(Machine.Harvester, MineFactoryReloadedCore.animationFolder + "Harvester.png");
			registerMachineAnimation(Machine.Rancher, MineFactoryReloadedCore.animationFolder + "Rancher.png");
			registerMachineAnimation(Machine.Breaker, MineFactoryReloadedCore.animationFolder + "BlockBreaker.png");
			registerMachineAnimation(Machine.Fertilizer, MineFactoryReloadedCore.animationFolder + "Fertilizer.png");
			registerMachineAnimation(Machine.Vet, MineFactoryReloadedCore.animationFolder + "Vet.png");
			registerMachineAnimation(Machine.Breeder, MineFactoryReloadedCore.animationFolder + "Breeder.png");
			registerMachineAnimation(Machine.Grinder, MineFactoryReloadedCore.animationFolder + "Mobgrinder.png");
			registerMachineAnimation(Machine.Chronotyper, MineFactoryReloadedCore.animationFolder + "Chronotyper.png");
			
			TextureFXManager.instance().addAnimation(new TextureLiquidStillFX(2, MineFactoryReloadedCore.itemTexture, 240, 255, 240, 255, 230, 245)); // milk
			TextureFXManager.instance().addAnimation(new TextureLiquidStillFX(3, MineFactoryReloadedCore.itemTexture, 20, 20, 20, 20, 32, 32)); // sludge
			TextureFXManager.instance().addAnimation(new TextureLiquidStillFX(4, MineFactoryReloadedCore.itemTexture, 106, 106, 52, 52, 45, 45)); // sewage
			TextureFXManager.instance().addAnimation(new TextureLiquidStillFX(5, MineFactoryReloadedCore.itemTexture, 0, 0, 100, 128, 0, 0)); // essence
		}
	}
	
	private void registerMachineAnimation(Machine machine, String animation)
	{
		registerAnimation(MineFactoryReloadedCore.machineMetadataMappings.get(machine) * 16 + 2, animation, MineFactoryReloadedCore.terrainTexture);
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
	}
}
