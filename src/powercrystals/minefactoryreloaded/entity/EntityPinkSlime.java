package powercrystals.minefactoryreloaded.entity;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class EntityPinkSlime extends EntitySlime
{
	public EntityPinkSlime(World world)
	{
		super(world);
		this.texture = MineFactoryReloadedCore.mobTextureFolder + "pinkslime.png";
	}
	
	@Override
	protected int getJumpDelay()
	{
		return this.rand.nextInt(10) + 5;
	}
	
	@Override
	protected int getDropItemId()
	{
		return this.getSlimeSize() == 1 ? MineFactoryReloadedCore.pinkSlimeballItem.itemID : 0;
	}
	
	@Override
	public void setSlimeSize(int size)
	{
		if(size > 4)
		{
			worldObj.newExplosion(this, posX, posY, posZ, 0.1F, false, true);
			this.attackEntityFrom(DamageSource.generic, 50);
			
			if(!worldObj.isRemote)
			{
				ItemStack meats = new ItemStack(MineFactoryReloadedCore.meatNuggetRawItem, worldObj.rand.nextInt(12) + size);
				EntityItem e = new EntityItem(worldObj, posX, posY, posZ, meats);
				e.motionX = rand.nextDouble() - 0.5D;
				e.motionY = rand.nextDouble() - 0.5D;
				e.motionZ = rand.nextDouble() - 0.5D;
				worldObj.spawnEntityInWorld(e);
			}
		}
		else
		{
			super.setSlimeSize(size);
		}
	}
	
	@Override
	protected String getSlimeParticle()
	{
		return "";
	}
	
	@Override
	protected EntityPinkSlime createInstance()
	{
		return new EntityPinkSlime(this.worldObj);
	}
	
	@Override
    public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt)
    {
        if (!this.worldObj.isRemote)
        {
        	this.setSlimeSize(this.getSlimeSize() + 3);
        }
    }
}
