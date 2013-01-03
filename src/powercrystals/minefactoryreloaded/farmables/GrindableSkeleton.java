package powercrystals.minefactoryreloaded.farmables;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IFactoryGrindable;

public class GrindableSkeleton implements IFactoryGrindable
{
	@Override
	public Class<?> getGrindableEntity()
	{
		return EntitySkeleton.class;
	}

	@Override
	public List<ItemStack> grind(World world, EntityLiving entity, Random random)
	{
		List<ItemStack> drops = new ArrayList<ItemStack>();
		EntitySkeleton s = (EntitySkeleton)entity;
		
		if(s.getSkeletonType() == 0)
		{
			drops.add(new ItemStack(Item.bone));
			drops.add(new ItemStack(Item.arrow));
		}
		else if(s.getSkeletonType() == 1)
		{
			drops.add(new ItemStack(Item.bone));
			drops.add(new ItemStack(Item.coal));
		}
		
		return drops;
	}

}
