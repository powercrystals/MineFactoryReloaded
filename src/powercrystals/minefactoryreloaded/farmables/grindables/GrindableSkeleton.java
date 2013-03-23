package powercrystals.minefactoryreloaded.farmables.grindables;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IFactoryGrindable;
import powercrystals.minefactoryreloaded.api.MobDrop;

public class GrindableSkeleton implements IFactoryGrindable
{
	@Override
	public Class<?> getGrindableEntity()
	{
		return EntitySkeleton.class;
	}

	@Override
	public List<MobDrop> grind(World world, EntityLiving entity, Random random)
	{
		List<MobDrop> drops = new ArrayList<MobDrop>();
		EntitySkeleton s = (EntitySkeleton)entity;
		
		if(s.getSkeletonType() == 0)
		{
			drops.add(new MobDrop(10, new ItemStack(Item.bone)));
			drops.add(new MobDrop(10, new ItemStack(Item.arrow)));
		}
		else if(s.getSkeletonType() == 1)
		{
			drops.add(new MobDrop(48, new ItemStack(Item.bone)));
			drops.add(new MobDrop(48, new ItemStack(Item.coal)));
			drops.add(new MobDrop(4, new ItemStack(Item.skull.itemID, 1, 1)));
		}
		
		return drops;
	}

}
