package powercrystals.minefactoryreloaded.farmables.grindables;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.IFactoryGrindable2;
import powercrystals.minefactoryreloaded.api.MobDrop;

public class GrindableStandard implements IFactoryGrindable2
{
	private Class<?> _grindableClass;
	private List<MobDrop> _drops;
	private boolean _entityProcessed;

	public GrindableStandard(Class<?> entityToGrind, MobDrop[] dropStacks, boolean entityProcessed)
	{
		_grindableClass = entityToGrind;
		_drops = new ArrayList<MobDrop>();
		for(MobDrop d : dropStacks)
		{
			_drops.add(d);
		}
		_entityProcessed = entityProcessed;
	}

	public GrindableStandard(Class<?> entityToGrind, MobDrop[] dropStacks)
	{
		this(entityToGrind, dropStacks, true);
	}

	public GrindableStandard(Class<?> entityToGrind, ItemStack dropStack, boolean entityProcessed)
	{
		_grindableClass = entityToGrind;
		_drops = new ArrayList<MobDrop>();
		_drops.add(new MobDrop(10, dropStack));
		_entityProcessed = entityProcessed;
	}

	public GrindableStandard(Class<?> entityToGrind, ItemStack dropStack)
	{
		this(entityToGrind, dropStack, true);
	}

	public GrindableStandard(Class<?> entityToGrind, boolean entityProcessed)
	{
		_grindableClass = entityToGrind;
		_drops = new ArrayList<MobDrop>();
		_entityProcessed = entityProcessed;
	}

	public GrindableStandard(Class<?> entityToGrind)
	{
		this(entityToGrind, true);
	}

	@Override
	public Class<?> getGrindableEntity()
	{
		return _grindableClass;
	}

	@Override
	public List<MobDrop> grind(World world, EntityLiving entity, Random random)
	{
		return _drops;
	}

	@Override
	public boolean processEntity(EntityLiving entity)
	{
		return _entityProcessed;
	}
}
