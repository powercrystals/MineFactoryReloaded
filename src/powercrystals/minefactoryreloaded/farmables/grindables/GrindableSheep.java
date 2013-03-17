package powercrystals.minefactoryreloaded.farmables.grindables;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import powercrystals.minefactoryreloaded.api.IFactoryGrindable;
import powercrystals.minefactoryreloaded.api.MobDrop;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GrindableSheep implements IFactoryGrindable
{
	@Override
	public Class<?> getGrindableEntity()
	{
		return EntitySheep.class;
	}

	@Override
	public List<MobDrop> grind(World world, EntityLiving entity, Random random)
	{
		List<MobDrop> drops = new ArrayList<MobDrop>();
		drops.add(new MobDrop(10, new ItemStack(Block.cloth, 1, ((EntitySheep)entity).getFleeceColor())));
		return drops;
	}
}
