package powercrystals.minefactoryreloaded;

import java.io.File;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import powercrystals.minefactoryreloaded.entity.EntitySafariNet;
import powercrystals.minefactoryreloaded.render.RenderEntitySafariNet;
import powercrystals.minefactoryreloaded.render.RendererConveyor;
import powercrystals.minefactoryreloaded.render.RendererFactoryGlassPane;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class MineFactoryReloadedClient
{
	public static Property playSounds;

	public static void preInit(File configFile)
	{
		Configuration c = new Configuration(configFile);

		playSounds = c.get(Configuration.CATEGORY_GENERAL, "PlaySounds", true);
		playSounds.comment = "Set to false to disable the harvester's sound when a block is harvested.";
		
		c.save();
	}

	public static void init()
	{
		MineFactoryReloadedCore.renderIdConveyor = RenderingRegistry.getNextAvailableRenderId();
		MineFactoryReloadedCore.renderIdFactoryGlassPane = RenderingRegistry.getNextAvailableRenderId();
		
		RenderingRegistry.registerBlockHandler(MineFactoryReloadedCore.renderIdConveyor, new RendererConveyor());
		RenderingRegistry.registerBlockHandler(MineFactoryReloadedCore.renderIdFactoryGlassPane, new RendererFactoryGlassPane());
		RenderingRegistry.registerEntityRenderingHandler(EntitySafariNet.class, new RenderEntitySafariNet());
	}
}
