package powercrystals.minefactoryreloaded;

import powercrystals.minefactoryreloaded.entity.EntitySafariNet;
import powercrystals.minefactoryreloaded.render.RedNetLogicRenderer;
import powercrystals.minefactoryreloaded.render.RedstoneCableRenderer;
import powercrystals.minefactoryreloaded.render.RenderEntitySafariNet;
import powercrystals.minefactoryreloaded.render.RendererConveyor;
import powercrystals.minefactoryreloaded.render.RendererFactoryGlassPane;
import powercrystals.minefactoryreloaded.tile.TileEntityRedNetLogic;
import powercrystals.minefactoryreloaded.tile.TileRedstoneCable;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class MineFactoryReloadedClient
{
	public static void init()
	{
		MineFactoryReloadedCore.renderIdConveyor = RenderingRegistry.getNextAvailableRenderId();
		MineFactoryReloadedCore.renderIdFactoryGlassPane = RenderingRegistry.getNextAvailableRenderId();
		MineFactoryReloadedCore.renderIdRedstoneCable = RenderingRegistry.getNextAvailableRenderId();
		
		RenderingRegistry.registerBlockHandler(MineFactoryReloadedCore.renderIdConveyor, new RendererConveyor());
		RenderingRegistry.registerBlockHandler(MineFactoryReloadedCore.renderIdFactoryGlassPane, new RendererFactoryGlassPane());
		RenderingRegistry.registerEntityRenderingHandler(EntitySafariNet.class, new RenderEntitySafariNet());	
		
		RedstoneCableRenderer renderer = new RedstoneCableRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileRedstoneCable.class, renderer);
		RenderingRegistry.registerBlockHandler(renderer);
		
		RedNetLogicRenderer logicRender = new RedNetLogicRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRedNetLogic.class, logicRender);
		RenderingRegistry.registerBlockHandler(logicRender);
	}
}
