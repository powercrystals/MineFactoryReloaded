package powercrystals.minefactoryreloaded.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.item.ItemSafariNet;

public class EntitySafariNet extends EntityThrowable
{
	public EntitySafariNet(World world)
	{
		super(world);
		dataWatcher.addObjectByDataType(13, 5);
	}
	
	public EntitySafariNet(World world, double x, double y, double z, ItemStack netStack)
	{
		super(world, x, y, z);
		dataWatcher.addObject(13, netStack);
	}
	
	public EntitySafariNet(World world, EntityLiving owner, ItemStack netStack)
	{
		super(world, owner);
		dataWatcher.addObject(13, netStack);
	}
	
	public void setStoredEntity(ItemStack s)
	{
		dataWatcher.updateObject(13, s);
	}
	
	@Override
	protected void onImpact(MovingObjectPosition mop)
	{
		ItemStack storedEntity = dataWatcher.getWatchableObjectItemStack(13);
		
		if(mop.typeOfHit == EnumMovingObjectType.TILE)
		{
			if(ItemSafariNet.isEmpty(storedEntity))
			{
				dropAsStack(storedEntity);
			}
			else
			{
				ItemSafariNet.releaseEntity(storedEntity, worldObj, mop.blockX, mop.blockY, mop.blockZ, mop.sideHit);
				if(ItemSafariNet.isSingleUse(storedEntity))
				{
					dropAsStack(null);
				}
				else
				{
					dropAsStack(storedEntity);
				}
			}
		}
		else
		{
			if(ItemSafariNet.isEmpty(storedEntity) && mop.entityHit instanceof EntityLiving)
			{
				ItemSafariNet.captureEntity(storedEntity, (EntityLiving)mop.entityHit);
				dropAsStack(storedEntity);
			}
			else
			{
				if(!ItemSafariNet.isEmpty(storedEntity))
				{
					Entity releasedEntity = ItemSafariNet.releaseEntity(storedEntity, worldObj, (int)mop.entityHit.posX, (int)mop.entityHit.posY, (int)mop.entityHit.posZ, 1);
					if(mop.entityHit instanceof EntityLiving)
					{
						if(releasedEntity instanceof EntityLiving)
						{
							//Functional for skeletons.
							((EntityLiving)releasedEntity).setAttackTarget((EntityLiving)mop.entityHit);
						}
						
						if(releasedEntity instanceof EntityCreature)
						{
							//functional for mobs that extend EntityCreature (everything but Ghasts) and not Skeletons.
							((EntityCreature)releasedEntity).setTarget(mop.entityHit);
						}
					}
					
					if(ItemSafariNet.isSingleUse(storedEntity))
					{
						dropAsStack(null);
						return;
					}
				}
				dropAsStack(storedEntity);
			}
		}
	}
	
	private void dropAsStack(ItemStack stack)
	{
		if(!worldObj.isRemote && stack != null)
		{
			EntityItem ei = new EntityItem(worldObj, posX, posY, posZ, stack);
			if(stack.getTagCompound() != null)
			{
				ei.getEntityItem().setTagCompound(stack.getTagCompound());
			}
			ei.delayBeforeCanPickup = 40;
			worldObj.spawnEntityInWorld(ei);
		}
		setDead();
	}
	
	public Icon getIcon()
	{
		return dataWatcher.getWatchableObjectItemStack(13).getIconIndex();
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		NBTTagCompound stackTag = new NBTTagCompound();
		dataWatcher.getWatchableObjectItemStack(13).writeToNBT(stackTag);
		nbttagcompound.setTag("safariNetStack", stackTag);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		NBTTagCompound stackTag = nbttagcompound.getCompoundTag("safariNetStack");
		dataWatcher.addObject(13, ItemStack.loadItemStackFromNBT(stackTag));
	}
}
