package powercrystals.minefactoryreloaded.farmables;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IFactoryGrindable;

public class GrindableZombiePigman implements IFactoryGrindable
{
	@Override
	public Class<?> getGrindableEntity()
	{
		return EntityPigZombie.class;
	}

	@Override
	public List<ItemStack> grind(World world, EntityLiving entity, Random random)
	{
		List<ItemStack> drops = new ArrayList<ItemStack>();
		drops.add(new ItemStack(Item.rottenFlesh));
		drops.add(new ItemStack(Item.goldNugget));
		
		if(random.nextInt(1000) == 0)
		{
			drops.add(new ItemStack(Item.sign));
		}
		
		return drops;
	}

}
