package powercrystals.minefactoryreloaded.net;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.TextureStitchEvent;

public interface IMFRProxy
{
	public void init();
	
	public void movePlayerToCoordinates(EntityPlayer e, double x, double y, double z);
	
	public void onPostTextureStitch(TextureStitchEvent.Post e);
}
