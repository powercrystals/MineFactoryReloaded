package powercrystals.minefactoryreloaded.item;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSyringeSlime extends ItemSyringe
{
	public ItemSyringeSlime(int id)
	{
		super(id);
	}
	
	@Override
	public boolean canInject(World world, EntityLiving entity, ItemStack syringe)
	{
		return entity instanceof EntitySlime && ((EntitySlime)entity).getSlimeSize() < 8;
	}
	
	@Override
	public boolean inject(World world, EntityLiving entity, ItemStack syringe)
	{
		EntitySlime slime = (EntitySlime)entity;
		slime.setSlimeSize(slime.getSlimeSize() << 1);
		return true;
	}
	
}
