package powercrystals.minefactoryreloaded.net;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import powercrystals.minefactoryreloaded.MineFactoryReloadedClient;

public class ClientProxy implements IMFRProxy
{
	@Override
	public void preInit(File configFile)
	{
		MineFactoryReloadedClient.preInit(configFile);
	}
	
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
}
