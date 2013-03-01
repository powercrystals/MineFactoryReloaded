package powercrystals.minefactoryreloaded.animals;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSyringeZombie extends ItemSyringe
{
	public ItemSyringeZombie()
	{
		super(MineFactoryReloadedCore.syringeZombieId.getInt());
	}
	
	@Override
	public boolean canInject(World world, EntityLiving entity, ItemStack syringe)
	{
		return entity instanceof EntityAgeable && ((EntityAgeable)entity).getGrowingAge() < 0;
	}

	@Override
	public boolean inject(World world, EntityLiving entity, ItemStack syringe)
	{
		if(world.rand.nextInt(100) < 50)
		{
			EntityZombie z = new EntityZombie(world);
			z.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
			world.spawnEntityInWorld(z);
			entity.setDead();
		}
		else
		{
			((EntityAgeable)entity).setGrowingAge(0);
		}
		return true;
	}

}
