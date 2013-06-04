package powercrystals.minefactoryreloaded.farmables.grindables;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.IFactoryGrindable2;
import powercrystals.minefactoryreloaded.api.MobDrop;

public class GrindableZombiePigman implements IFactoryGrindable2
{
	@Override
	public Class<?> getGrindableEntity()
	{
		return EntityPigZombie.class;
	}

	@Override
	public List<MobDrop> grind(World world, EntityLiving entity, Random random)
	{
		List<MobDrop> drops = new ArrayList<MobDrop>();

		if(random.nextInt(1000) == 0)
		{
			ItemStack battleSign = new ItemStack(Item.sign);
			battleSign.addEnchantment(Enchantment.sharpness, 4);
			battleSign.addEnchantment(Enchantment.knockback, 2);
			battleSign.addEnchantment(Enchantment.fireAspect, 1);
			drops.add(new MobDrop(10, battleSign));
		}

		return drops;
	}

	@Override
	public boolean processEntity(EntityLiving entity)
	{
		return false;
	}
}
