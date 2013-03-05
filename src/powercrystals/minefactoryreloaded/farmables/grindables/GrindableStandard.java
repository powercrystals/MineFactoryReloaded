package powercrystals.minefactoryreloaded.farmables.grindables;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.IFactoryGrindable;

public class GrindableStandard implements IFactoryGrindable
{
	private Class<?> _ranchableClass;
	private List<ItemStack> _drops;
	
	public GrindableStandard(Class<?> entityToRanch, ItemStack[] dropStacks)
	{
		_ranchableClass = entityToRanch;
		_drops = new ArrayList<ItemStack>();
		for(ItemStack d : dropStacks)
		{
			_drops.add(d);
		}
	}
	
	public GrindableStandard(Class<?> entityToRanch, ItemStack dropStack)
	{
		_ranchableClass = entityToRanch;
		_drops = new ArrayList<ItemStack>();
		_drops.add(dropStack);
	}
	
	public GrindableStandard(Class<?> entityToRanch)
	{
		_ranchableClass = entityToRanch;
		_drops = new ArrayList<ItemStack>();
	}
	
	@Override
	public Class<?> getGrindableEntity()
	{
		return _ranchableClass;
	}

	@Override
	public List<ItemStack> grind(World world, EntityLiving entity, Random random)
	{
		return _drops;
	}
}
