package powercrystals.minefactoryreloaded;

import java.util.EnumSet;
import java.util.HashMap;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.MinecraftForgeClient;
import powercrystals.core.position.BlockPosition;
import powercrystals.core.render.RenderBlockFluidClassic;
import powercrystals.minefactoryreloaded.entity.EntityNeedle;
import powercrystals.minefactoryreloaded.entity.EntitySafariNet;
import powercrystals.minefactoryreloaded.render.block.ConveyorRenderer;
import powercrystals.minefactoryreloaded.render.block.FactoryGlassPaneRenderer;
import powercrystals.minefactoryreloaded.render.block.FactoryGlassRenderer;
import powercrystals.minefactoryreloaded.render.block.VineScaffoldRenderer;
import powercrystals.minefactoryreloaded.render.entity.EntityNeedleRenderer;
import powercrystals.minefactoryreloaded.render.entity.EntitySafariNetRenderer;
import powercrystals.minefactoryreloaded.render.item.FactoryGlassPaneItemRenderer;
import powercrystals.minefactoryreloaded.render.item.NeedleGunItemRenderer;
import powercrystals.minefactoryreloaded.render.tileentity.LaserDrillPrechargerRenderer;
import powercrystals.minefactoryreloaded.render.tileentity.LaserDrillRenderer;
import powercrystals.minefactoryreloaded.render.tileentity.RedNetCardItemRenderer;
import powercrystals.minefactoryreloaded.render.tileentity.RedNetHistorianRenderer;
import powercrystals.minefactoryreloaded.render.tileentity.RedNetLogicRenderer;
import powercrystals.minefactoryreloaded.render.tileentity.RedstoneCableRenderer;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityLaserDrill;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityLaserDrillPrecharger;
import powercrystals.minefactoryreloaded.tile.rednet.TileEntityRedNetHistorian;
import powercrystals.minefactoryreloaded.tile.rednet.TileEntityRedNetLogic;
import powercrystals.minefactoryreloaded.tile.rednet.TileEntityRedNetCable;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class MineFactoryReloadedClient implements IScheduledTickHandler
{
	private static MineFactoryReloadedClient _instance;
	
	public static HashMap<BlockPosition, Integer> prcPages = new HashMap<BlockPosition, Integer>();
	
	public static void init()
	{
		_instance = new MineFactoryReloadedClient();
		
		MineFactoryReloadedCore.renderIdConveyor = RenderingRegistry.getNextAvailableRenderId();
		MineFactoryReloadedCore.renderIdFactoryGlassPane = RenderingRegistry.getNextAvailableRenderId();
		MineFactoryReloadedCore.renderIdRedstoneCable = RenderingRegistry.getNextAvailableRenderId();
		MineFactoryReloadedCore.renderIdFluidClassic = RenderingRegistry.getNextAvailableRenderId();
		MineFactoryReloadedCore.renderIdRedNetLogic = RenderingRegistry.getNextAvailableRenderId();
		MineFactoryReloadedCore.renderIdVineScaffold = RenderingRegistry.getNextAvailableRenderId();
		MineFactoryReloadedCore.renderIdFactoryGlass = RenderingRegistry.getNextAvailableRenderId();
		
		RenderingRegistry.registerBlockHandler(MineFactoryReloadedCore.renderIdConveyor, new ConveyorRenderer());
		RenderingRegistry.registerBlockHandler(MineFactoryReloadedCore.renderIdFactoryGlassPane, new FactoryGlassPaneRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntitySafariNet.class, new EntitySafariNetRenderer());	
		RenderingRegistry.registerBlockHandler(MineFactoryReloadedCore.renderIdFluidClassic, new RenderBlockFluidClassic(MineFactoryReloadedCore.renderIdFluidClassic));
		RenderingRegistry.registerBlockHandler(MineFactoryReloadedCore.renderIdVineScaffold, new VineScaffoldRenderer());
		RenderingRegistry.registerBlockHandler(MineFactoryReloadedCore.renderIdFactoryGlass, new FactoryGlassRenderer());
		MinecraftForgeClient.registerItemRenderer(MineFactoryReloadedCore.factoryGlassPaneBlock.blockID, new FactoryGlassPaneItemRenderer());
		
		RedstoneCableRenderer renderer = new RedstoneCableRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRedNetCable.class, renderer);
		RenderingRegistry.registerBlockHandler(renderer);
		
		RedNetLogicRenderer logicRender = new RedNetLogicRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRedNetLogic.class, logicRender);
		RenderingRegistry.registerBlockHandler(logicRender);
		
		RedNetHistorianRenderer panelRenderer = new RedNetHistorianRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRedNetHistorian.class, panelRenderer);
		RenderingRegistry.registerBlockHandler(panelRenderer);
		
		MinecraftForgeClient.registerItemRenderer(MineFactoryReloadedCore.logicCardItem.itemID, new RedNetCardItemRenderer());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaserDrill.class, new LaserDrillRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaserDrillPrecharger.class, new LaserDrillPrechargerRenderer());
		
		MinecraftForgeClient.registerItemRenderer(MineFactoryReloadedCore.needlegunItem.itemID, new NeedleGunItemRenderer());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityNeedle.class, new EntityNeedleRenderer());
		
		TickRegistry.registerScheduledTickHandler(_instance, Side.CLIENT);
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		if(!(tickData[0] instanceof EntityPlayerSP))
		{
			return;
		}
		EntityPlayerSP player = (EntityPlayerSP)tickData[0];
		int frontX = MathHelper.floor_double(player.posX + player.getLookVec().xCoord);
		int frontY = MathHelper.floor_double(player.boundingBox.minY);
		int frontZ = MathHelper.floor_double(player.posZ + player.getLookVec().zCoord);
		
		int blockId = player.worldObj.getBlockId(frontX, frontY, frontZ);
		if(blockId == MineFactoryReloadedCore.vineScaffoldBlock.blockID)
		{
			if(player.movementInput.moveForward > 0)
			{
				player.motionY = 0.2D;
			}
			else if(player.isSneaking())
			{
				player.motionY = 0.0D;
			}
			else
			{
				player.motionY = -0.15D;
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel()
	{
		return MineFactoryReloadedCore.modId + ".client";
	}

	@Override
	public int nextTickSpacing()
	{
		return 0;
	}
}
