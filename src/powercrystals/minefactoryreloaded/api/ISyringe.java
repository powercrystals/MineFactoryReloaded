package powercrystals.minefactoryreloaded.api;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ISyringe
{
	public boolean canInject(World world, EntityLiving entity, ItemStack syringe);
	
	public boolean inject(World world, EntityLiving entity, ItemStack syringe);
}
