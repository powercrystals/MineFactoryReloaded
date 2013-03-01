package powercrystals.minefactoryreloaded.animals;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class ItemGrowthSyringe extends ItemSyringe
{
	public ItemGrowthSyringe()
	{
		super(MineFactoryReloadedCore.syringeGrowthItemId.getInt());
	}
	
	@Override
	public boolean canInject(World world, EntityLiving entity, ItemStack syringe)
	{
		return entity instanceof EntityAgeable && ((EntityAgeable)entity).getGrowingAge() < 0;
	}
	
	@Override
	public boolean inject(World world, EntityLiving entity, ItemStack syringe)
	{
		((EntityAgeable)entity).setGrowingAge(0);
		return true;
	}
}
