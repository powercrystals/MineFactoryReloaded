package powercrystals.minefactoryreloaded.net;

import net.minecraft.entity.player.EntityPlayer;
import powercrystals.minefactoryreloaded.MineFactoryReloadedClient;

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
}
