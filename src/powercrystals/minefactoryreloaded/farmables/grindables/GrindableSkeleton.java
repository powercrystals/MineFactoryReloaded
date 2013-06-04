package powercrystals.minefactoryreloaded.farmables.grindables;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.IFactoryGrindable2;
import powercrystals.minefactoryreloaded.api.MobDrop;

public class GrindableSkeleton implements IFactoryGrindable2
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

		switch (((EntitySkeleton)entity).getSkeletonType())
		{
		case 1:
			drops.add(new MobDrop(48, null));
			drops.add(new MobDrop(48, null));
			drops.add(new MobDrop(4, new ItemStack(Item.skull.itemID, 1, 1)));
			break;
		default:
		}

		return drops;
	}

	@Override
	public boolean processEntity(EntityLiving entity)
	{
		return false;
	}
}
