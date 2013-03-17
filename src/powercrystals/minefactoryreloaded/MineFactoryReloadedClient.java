package powercrystals.minefactoryreloaded;

import powercrystals.minefactoryreloaded.entity.EntitySafariNet;
import powercrystals.minefactoryreloaded.render.RenderEntitySafariNet;
import powercrystals.minefactoryreloaded.render.RendererConveyor;
import powercrystals.minefactoryreloaded.render.RendererFactoryGlassPane;

import cpw.mods.fml.client.registry.RenderingRegistry;

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

	private void load()
	{
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
