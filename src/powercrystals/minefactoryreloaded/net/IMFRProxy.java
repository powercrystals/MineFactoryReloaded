package powercrystals.minefactoryreloaded.net;

import net.minecraft.entity.player.EntityPlayer;

public interface IMFRProxy
{
	public void init();
	
	public void movePlayerToCoordinates(EntityPlayer e, double x, double y, double z);
}
