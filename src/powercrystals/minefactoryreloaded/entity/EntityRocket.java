package powercrystals.minefactoryreloaded.entity;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityRocket extends Entity
{
	private int _ticksAlive = 0;
	private EntityLiving _owner;
	private Entity _target;
	
	public EntityRocket(World world)
	{
		super(world);
	}
	
	public EntityRocket(World world, EntityLiving owner)
	{
		this(world);
		setSize(1.0F, 1.0F);
		setLocationAndAngles(owner.posX, owner.posY + owner.getEyeHeight(), owner.posZ, owner.rotationYaw, owner.rotationPitch);
		setPosition(posX, posY, posZ);
		recalculateVelocity();
		_owner = owner;
	}
	
	public EntityRocket(World world, EntityLiving owner, Entity target)
	{
		this(world, owner);
		_target = target;
	}
	
	private void recalculateVelocity()
	{
		motionX = -MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI);
		motionZ = MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI);
		motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float)Math.PI));
	}
	
	@Override
	protected void entityInit()
	{
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		_ticksAlive++;
		if(_ticksAlive > 200)
		{
			setDead();
		}
		
		if(worldObj.isRemote)
		{
			for(int i = 0; i < 4; i++)
			{
				worldObj.spawnParticle("smoke", posX + motionX * i / 4.0D, posY + motionY * i / 4.0D, posZ + motionZ * i / 4.0D,
						-motionX, -motionY + 0.2D, -motionZ);
			}
		}
		
		if(!worldObj.isRemote)
		{
			Vec3 pos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			Vec3 nextPos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition hit = this.worldObj.rayTraceBlocks_do_do(pos, nextPos, false, true);
			pos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			nextPos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			
			if(hit != null)
			{
				nextPos = this.worldObj.getWorldVec3Pool().getVecFromPool(hit.hitVec.xCoord, hit.hitVec.yCoord,	hit.hitVec.zCoord);
			}
			
			Entity entityHit = null;
			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,	this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double closestRange = 0.0D;
			double collisionRange = 0.3D;
			
			for(int i = 0, end = list.size(); i < end; ++i)
			{
				Entity e = (Entity)list.get(i);
				
				if(e.canBeCollidedWith() && (e != _owner))
				{
					AxisAlignedBB entitybb = e.boundingBox.expand(collisionRange, collisionRange, collisionRange);
					MovingObjectPosition entityHitPos = entitybb.calculateIntercept(pos, nextPos);
					
					if(entityHitPos != null)
					{
						double range = pos.distanceTo(entityHitPos.hitVec);
						
						if((range < closestRange) | closestRange == 0D)
						{
							entityHit = e;
							closestRange = range;
						}
					}
				}
			}
			
			if(entityHit != null)
			{
				hit = new MovingObjectPosition(entityHit);
			}
			
			if(hit != null && hit.entityHit != null && hit.entityHit instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer)hit.entityHit;
				
				if(entityplayer.capabilities.disableDamage || (_owner instanceof EntityPlayer && !((EntityPlayer)_owner).func_96122_a(entityplayer)))
				{
					hit = null;
				}
			}
			
			if(hit != null && !worldObj.isRemote)
			{
				if(hit.entityHit != null)
				{ // why not spawn explosion at nextPos x/y/z?
					worldObj.newExplosion(this, hit.entityHit.posX, hit.entityHit.posY, hit.entityHit.posZ, 4.0F, true, true);
				}
				else
				{
					worldObj.newExplosion(this, hit.blockX, hit.blockY, hit.blockZ, 4.0F, true, true);
				}
				setDead();
			}
		}
		
		if(_target != null)
		{
			// At this point, I suspect literally no one on this project actually understands what this does or how it works
			
			Vec3 targetVector = worldObj.getWorldVec3Pool().getVecFromPool(_target.posX - posX, _target.posY - posY, _target.posZ - posZ);
			float targetYaw = clampAngle(360 - (float)(Math.atan2(targetVector.xCoord, targetVector.zCoord) * 180.0D / Math.PI), 360, false);
			float targetPitch = clampAngle(-(float)(Math.atan2(targetVector.yCoord, Math.sqrt(targetVector.xCoord * targetVector.xCoord + targetVector.zCoord * targetVector.zCoord)) * 180.0D / Math.PI), 360, false);
			
			float yawDifference = clampAngle(targetYaw - rotationYaw, 3, true);
			float pitchDifference = clampAngle(targetPitch - rotationPitch, 3, true);

			float newYaw;
			float newPitch;
			
			if(Math.max(targetYaw, rotationYaw) - Math.min(targetYaw, rotationYaw) > 180)
			{
				newYaw = rotationYaw - yawDifference;
			}
			else
			{
				newYaw = rotationYaw + yawDifference;
			}
			
			if(Math.max(targetPitch, rotationPitch) - Math.min(targetPitch, rotationPitch) > 180)
			{
				newPitch = rotationPitch - pitchDifference;
			}
			else
			{
				newPitch = rotationPitch + pitchDifference;
			}
			
			rotationYaw = clampAngle(newYaw, 360F, false);
			rotationPitch = clampAngle(newPitch, 360F, false);
			recalculateVelocity();
		}
		
		setPosition(posX + motionX, posY + motionY, posZ + motionZ);
	}
	
	private float clampAngle(float angle, float maxValue, boolean allowNegative)
	{
		if(angle >= 0F)
		{
			angle %= 360F;
		}
		else
		{ // pretty sure that negativeValue % postiveValue has the same result
			angle = -(-angle % 360);
		}
		
		if(angle < 0 & !allowNegative)
		{
			angle += 360;
		}
		
		if(Math.abs(angle) > maxValue)
		{
			angle = Math.copySign(maxValue, angle);
		}
		
		return angle;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z)
	{
		motionX = x;
		motionY = y;
		motionZ = z;
		
		if(prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
		{
			double f = MathHelper.sqrt_double(x * x + z * z);
			prevRotationYaw = rotationYaw = (float)(Math.atan2(x, z) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float)(Math.atan2(y, f) * 180.0D / Math.PI);
			setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
		}
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
	}
}
