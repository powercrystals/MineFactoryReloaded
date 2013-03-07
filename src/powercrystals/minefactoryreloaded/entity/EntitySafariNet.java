package powercrystals.minefactoryreloaded.entity;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.animals.ItemSafariNet;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySafariNet extends EntityThrowable
{
	public EntitySafariNet(World world)
	{
		super(world);
		dataWatcher.addObject(12, (byte)0);
		dataWatcher.addObjectByDataType(13, 5);
	}
	
	public EntitySafariNet(World world, double x, double y, double z, boolean singleUse)
	{
		super(world, x, y, z);
		dataWatcher.addObject(12, (byte)(singleUse ? 1 : 0));
		dataWatcher.addObjectByDataType(13, 5);
	}

	public EntitySafariNet(World world, EntityLiving owner, boolean singleUse)
	{
		super(world, owner);
		dataWatcher.addObject(12, (byte)(singleUse ? 1 : 0));
		dataWatcher.addObjectByDataType(13, 5);
	}
	
	public void setStoredEntity(ItemStack s)
	{
		dataWatcher.updateObject(13, s);
	}

	@Override
	protected void onImpact(MovingObjectPosition mop)
	{
		ItemStack emptyNet;
		
		if(dataWatcher.getWatchableObjectByte(12) == 1)
		{
			emptyNet = new ItemStack(MineFactoryReloadedCore.safariNetSingleItem);
		}
		else
		{
			emptyNet = new ItemStack(MineFactoryReloadedCore.safariNetItem);
		}
		
		ItemStack storedEntity = dataWatcher.getWatchableObjectItemStack(13);
		if(storedEntity == null || ItemSafariNet.isEmpty(storedEntity))
		{
			if(mop.typeOfHit == EnumMovingObjectType.TILE)
			{
				dropAsStack(emptyNet);
			}
			else if(mop.entityHit != null && mop.entityHit instanceof EntityLiving)
			{
				ItemStack entityNet = emptyNet.copy();
				if(ItemSafariNet.captureEntity(entityNet, (EntityLiving)mop.entityHit))
				{
					dropAsStack(entityNet);
				}
				else
				{
					dropAsStack(emptyNet);
				}
			}
		}
		else
		{
			if(mop.typeOfHit == EnumMovingObjectType.TILE)
			{
				ItemSafariNet.releaseEntity(storedEntity, worldObj, mop.blockX, mop.blockY, mop.blockZ, mop.sideHit);
				dropAsStack(emptyNet);
			}
			else
			{
				ItemSafariNet.releaseEntity(storedEntity, worldObj, (int)mop.entityHit.posX, (int)mop.entityHit.posY, (int)mop.entityHit.posZ, 1);
				dropAsStack(emptyNet);
			}
		}
	}
	
	private void dropAsStack(ItemStack stack)
	{
		if(!worldObj.isRemote)
		{
			EntityItem ei = new EntityItem(worldObj, posX, posY, posZ, stack);
			if(stack.getTagCompound() != null)
			{
				ei.getEntityItem().setTagCompound(stack.getTagCompound());
			}
			worldObj.spawnEntityInWorld(ei);
		}
		setDead();
	}
	
	public int getIconIndex()
	{
		return (dataWatcher.getWatchableObjectByte(12) == 1) ? MineFactoryReloadedCore.safariNetSingleItem.getIconFromDamage(0) + 3 : MineFactoryReloadedCore.safariNetItem.getIconFromDamage(0) + 3;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setByte("singleUseSafariNet", dataWatcher.getWatchableObjectByte(12));
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		dataWatcher.addObject(12, nbttagcompound.getByte("singleUseSafariNet"));
	}
}
