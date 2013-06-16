package powercrystals.minefactoryreloaded.modhelpers.thaumcraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.IFactoryGrindable2;
import powercrystals.minefactoryreloaded.api.MobDrop;

import thaumcraft.api.EnumTag;

public class GrindableWisp implements IFactoryGrindable2
{
	private Class<?> _wispClass;
	private Item _wispEssence;

	public GrindableWisp() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
	{
		_wispClass = Class.forName("thaumcraft.common.entities.monster.EntityWisp");
		_wispEssence = (Item)Class.forName("thaumcraft.common.Config").getField("itemWispEssence").get(null);
	}

	@Override
	public Class<?> getGrindableEntity()
	{
		return _wispClass;
	}

	@Override
	public List<MobDrop> grind(World world, EntityLiving entity, Random random)
	{
		List<MobDrop> drops = new ArrayList<MobDrop>();

		byte wispType;
		try
		{
			wispType = Class.forName("thaumcraft.common.entities.monster.EntityWisp").getField("type").getByte(entity);

			while(EnumTag.get(wispType) == EnumTag.UNKNOWN)
			{
				wispType = ((byte)random.nextInt(64));
			}
			drops.add(new MobDrop(10, new ItemStack(_wispEssence.itemID, 1, wispType)));
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}

		return drops;
	}

	@Override
	public boolean processEntity(EntityLiving e)
	{
		return true;
	}
}