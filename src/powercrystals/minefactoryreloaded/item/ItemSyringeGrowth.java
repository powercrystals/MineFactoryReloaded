package powercrystals.minefactoryreloaded.item;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.setup.MFRConfig;

public class ItemSyringeGrowth extends ItemSyringe
{
	public ItemSyringeGrowth()
	{
		super(MFRConfig.syringeGrowthItemId.getInt());
	}
	
	@Override
	public boolean canInject(World world, EntityLiving entity, ItemStack syringe)
	{
		return (entity instanceof EntityAgeable && ((EntityAgeable)entity).getGrowingAge() < 0) || entity instanceof EntityZombie;
	}
	
	@Override
	public boolean inject(World world, EntityLiving entity, ItemStack syringe)
	{
		if(entity instanceof EntityAgeable)
		{
			((EntityAgeable)entity).setGrowingAge(0);
		}
		else
		{
			EntityGiantZombie e = new EntityGiantZombie(world);
			e.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
			world.spawnEntityInWorld(e);
			entity.setDead();
		}
		return true;
	}
}
