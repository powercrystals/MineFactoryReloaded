package powercrystals.minefactoryreloaded.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.setup.MFRConfig;

public class ItemSyringeZombie extends ItemSyringe
{
	public ItemSyringeZombie()
	{
		super(MFRConfig.syringeZombieId.getInt());
	}
	
	@Override
	public boolean canInject(World world, EntityLiving entity, ItemStack syringe)
	{
		return entity instanceof EntityAgeable && ((EntityAgeable)entity).getGrowingAge() < 0;
	}
	
	@Override
	public boolean inject(World world, EntityLiving entity, ItemStack syringe)
	{
		if(world.rand.nextInt(100) < 5)
		{
			Entity e;
			if(entity instanceof EntityPig)
			{
				e = new EntityPigZombie(world);
			}
			else
			{
				e = new EntityZombie(world);
			}
			e.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
			world.spawnEntityInWorld(e);
			entity.setDead();
		}
		else
		{
			((EntityAgeable)entity).setGrowingAge(0);
		}
		return true;
	}
	
}
