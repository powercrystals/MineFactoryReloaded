package powercrystals.minefactoryreloaded.net;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;

public interface IMFRProxy
{
	public void preInit(File configFile);
	
	public void init();
	
	public void movePlayerToCoordinates(EntityPlayer e, double x, double y, double z);
}
