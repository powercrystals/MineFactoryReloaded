package powercrystals.minefactoryreloaded.net;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.liquids.LiquidDictionary;
import powercrystals.minefactoryreloaded.MineFactoryReloadedClient;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class ClientProxy implements IMFRProxy
{
	@Override
	public void init()
	{
		MineFactoryReloadedClient.init();
	}

	@Override
	public void movePlayerToCoordinates(EntityPlayer e, double x, double y, double z)
	{
		e.setPosition(x, y, z);
	}
	
	@Override
	@ForgeSubscribe
	public void onPostTextureStitch(TextureStitchEvent.Post e)
	{
		LiquidDictionary.getCanonicalLiquid("milk").setRenderingIcon(MineFactoryReloadedCore.milkStill.getBlockTextureFromSide(1));
		LiquidDictionary.getCanonicalLiquid("sludge").setRenderingIcon(MineFactoryReloadedCore.sludgeStill.getBlockTextureFromSide(1));
		LiquidDictionary.getCanonicalLiquid("sewage").setRenderingIcon(MineFactoryReloadedCore.sewageStill.getBlockTextureFromSide(1));
		LiquidDictionary.getCanonicalLiquid("mobEssence").setRenderingIcon(MineFactoryReloadedCore.essenceStill.getBlockTextureFromSide(1));
		LiquidDictionary.getCanonicalLiquid("biofuel").setRenderingIcon(MineFactoryReloadedCore.biofuelStill.getBlockTextureFromSide(1));
	}
}
