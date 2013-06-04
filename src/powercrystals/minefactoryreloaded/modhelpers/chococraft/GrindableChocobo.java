package powercrystals.minefactoryreloaded.modhelpers.chococraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.IFactoryGrindable2;
import powercrystals.minefactoryreloaded.api.MobDrop;

public class GrindableChocobo implements IFactoryGrindable2
{
	private final int _featherItem;
	private final int _legItem;
	private final Class<?> _entity;

	public GrindableChocobo(Class<?> entityChocobo, int legItem, int featherItem)
	{
		this._entity = entityChocobo;
		this._legItem = legItem;
		this._featherItem = featherItem;
	}
	@Override
	public Class<?> getGrindableEntity()
	{
		return this._entity;
	}

	@Override
	public List<MobDrop> grind(World world, EntityLiving entity, Random random)
	{
		List<MobDrop> items = new ArrayList<MobDrop>();

		items.add(new MobDrop(80, new ItemStack(_legItem, 1, 0)));
		items.add(new MobDrop(20, new ItemStack(_featherItem, random.nextInt(3), 0)));

		return items;
	}

	@Override
	public boolean processEntity(EntityLiving entity)
	{
		return true;
	}
}
