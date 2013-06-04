package powercrystals.minefactoryreloaded.farmables.grindables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.IFactoryGrindable2;
import powercrystals.minefactoryreloaded.api.MobDrop;

public class GrindableSlime implements IFactoryGrindable2
{
	protected Class<?> grindable;
	protected ArrayList<MobDrop> drops;
	protected int dropSize;

	public GrindableSlime(Class<?> slime, MobDrop[] drops, int dropSize)
	{
		grindable = slime;
		ArrayList<MobDrop> q = new ArrayList<MobDrop>();
		q.addAll(Arrays.asList(drops));
		this.drops = q;
		this.dropSize = dropSize;
	}

	public GrindableSlime(Class<?> slime, MobDrop drop, int dropSize)
	{
		this(slime, new MobDrop[]{drop}, dropSize);
	}

	public GrindableSlime(Class<?> slime, ItemStack[] drops, int dropSize)
	{
		grindable = slime;
		ArrayList<MobDrop> q = new ArrayList<MobDrop>();
		for (ItemStack drop : drops)
			q.add(new MobDrop(10, drop));
		this.drops = q;
		this.dropSize = dropSize;
	}

	public GrindableSlime(Class<?> slime, ItemStack drop, int dropSize)
	{
		this(slime, new MobDrop[]{new MobDrop(10, drop)}, dropSize);
	}

	@Override
	public Class<?> getGrindableEntity() {
		return grindable;
	}

	@Override
	public List<MobDrop> grind(World world, EntityLiving entity, Random random) {
		if (((EntitySlime)entity).getSlimeSize() > dropSize)
			return drops;
		return null;
	}

	@Override
	public boolean processEntity(EntityLiving entity) {
		return false;
	}

}
