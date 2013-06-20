package powercrystals.minefactoryreloaded.world;

import net.minecraft.entity.Entity;

public interface IGrindingWorld
{
	
	public boolean spawnEntityInWorld(Entity entity);
	
	public boolean addEntityForGrinding(Entity entity);
	
	public void clearReferences();
	
	public void cleanReferences();
	
	public void setAllowSpawns(boolean allow);
	
}
