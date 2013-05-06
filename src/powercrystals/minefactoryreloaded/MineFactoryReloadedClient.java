package powercrystals.minefactoryreloaded;

import java.util.EnumSet;
import java.util.HashMap;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.MinecraftForgeClient;
import powercrystals.core.position.BlockPosition;
import powercrystals.core.render.RenderBlockFluidClassic;
import powercrystals.minefactoryreloaded.entity.EntitySafariNet;
import powercrystals.minefactoryreloaded.render.RedNetCardItemRenderer;
import powercrystals.minefactoryreloaded.render.RedNetLogicRenderer;
import powercrystals.minefactoryreloaded.render.RedstoneCableRenderer;
import powercrystals.minefactoryreloaded.render.RenderEntitySafariNet;
import powercrystals.minefactoryreloaded.render.RendererConveyor;
import powercrystals.minefactoryreloaded.render.RendererFactoryGlassPane;
import powercrystals.minefactoryreloaded.render.VineScaffoldRenderer;
import powercrystals.minefactoryreloaded.tile.TileEntityRedNetLogic;
import powercrystals.minefactoryreloaded.tile.TileRedstoneCable;

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
		
		RenderingRegistry.registerBlockHandler(MineFactoryReloadedCore.renderIdConveyor, new RendererConveyor());
		RenderingRegistry.registerBlockHandler(MineFactoryReloadedCore.renderIdFactoryGlassPane, new RendererFactoryGlassPane());
		RenderingRegistry.registerEntityRenderingHandler(EntitySafariNet.class, new RenderEntitySafariNet());	
		RenderingRegistry.registerBlockHandler(MineFactoryReloadedCore.renderIdFluidClassic, new RenderBlockFluidClassic(MineFactoryReloadedCore.renderIdFluidClassic));
		RenderingRegistry.registerBlockHandler(MineFactoryReloadedCore.renderIdVineScaffold, new VineScaffoldRenderer());
		
		RedstoneCableRenderer renderer = new RedstoneCableRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileRedstoneCable.class, renderer);
		RenderingRegistry.registerBlockHandler(renderer);
		
		RedNetLogicRenderer logicRender = new RedNetLogicRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRedNetLogic.class, logicRender);
		RenderingRegistry.registerBlockHandler(logicRender);
		
		MinecraftForgeClient.registerItemRenderer(MineFactoryReloadedCore.logicCardItem.itemID, new RedNetCardItemRenderer());
		
		TickRegistry.registerScheduledTickHandler(_instance, Side.CLIENT);
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		EntityPlayerSP player = (EntityPlayerSP)tickData[0];
		int frontX = MathHelper.floor_double(player.posX + player.getLookVec().xCoord);
		int frontY = MathHelper.floor_double(player.posY + player.getLookVec().yCoord);
		int frontZ = MathHelper.floor_double(player.posZ + player.getLookVec().zCoord);
		
		int blockId = player.worldObj.getBlockId(frontX, frontY, frontZ);
		int lowerId = player.worldObj.getBlockId(frontX, frontY - 1, frontZ);
		if(blockId == MineFactoryReloadedCore.vineScaffoldBlock.blockID || (player.worldObj.isAirBlock(frontX, frontY, frontZ) && lowerId == MineFactoryReloadedCore.vineScaffoldBlock.blockID))
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
