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
	}
	
	public EntitySafariNet(World world, double x, double y, double z, boolean singleUse)
	{
		super(world, x, y, z);
		dataWatcher.addObject(12, (byte)(singleUse ? 1 : 0));
	}

	public EntitySafariNet(World world, EntityLiving owner, boolean singleUse)
	{
		super(world, owner);
		dataWatcher.addObject(12, (byte)(singleUse ? 1 : 0));
	}
	
	@Override
	public boolean isInRangeToRenderDist(double distance)
	{
		double var3 = this.boundingBox.getAverageEdgeLength() * 4.0D;
		var3 *= 64.0D;
		return distance < var3 * var3;
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
