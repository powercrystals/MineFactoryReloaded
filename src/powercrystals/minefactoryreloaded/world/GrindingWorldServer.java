package powercrystals.minefactoryreloaded.world;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;

import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;

import skyboy.core.world.WorldServerProxy;

public class GrindingWorldServer extends WorldServerProxy implements IGrindingWorld
{
	protected TileEntityFactoryInventory grinder;
	protected boolean allowSpawns;
	protected ArrayList<Entity> entitiesToGrind = new ArrayList<Entity>();
	
	public GrindingWorldServer(WorldServer world, TileEntityFactoryInventory grinder)
	{
		this(world, grinder, false);
	}
	
	public GrindingWorldServer(WorldServer world, TileEntityFactoryInventory grinder, boolean allowSpawns)
	{
		super(world);
		this.grinder = grinder;
		this.allowSpawns = allowSpawns;
	}
	
	@Override
	public void setAllowSpawns(boolean allow)
	{
		this.allowSpawns = allow;
	}
	
	@Override
	public boolean spawnEntityInWorld(Entity entity)
	{
		if(grinder != null && entity instanceof EntityItem)
		{
			if(grinder.manageSolids())
			{
				ItemStack drop = ((EntityItem)entity).getEntityItem();
				if(drop != null) UtilInventory.dropStack(grinder, drop, grinder.getDropDirection());
			}
		}
		else if(allowSpawns)
		{
			entity.worldObj = this.proxiedWorld;
			return super.spawnEntityInWorld(entity);
		}
		entity.setDead();
		return true;
	}
	
	@Override
	public boolean addEntityForGrinding(Entity entity)
	{
		if(entity.worldObj == this) return true;
		if(entity.worldObj == this.proxiedWorld)
		{
			entity.worldObj = this;
			entitiesToGrind.add(entity);
			return true;
		}
		return false;
	}
	
	@Override
	public void clearReferences()
	{
		for(Entity ent : entitiesToGrind)
		{
			if(ent.worldObj == this) ent.worldObj = this.proxiedWorld;
		}
		entitiesToGrind.clear();
	}
	
	@Override
	public void cleanReferences()
	{
		for(int i = entitiesToGrind.size(); i-- > 0;)
		{
			Entity ent = entitiesToGrind.get(i);
			if(ent.isDead) entitiesToGrind.remove(ent);
		}
	}
	
}
