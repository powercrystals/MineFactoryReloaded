package powercrystals.minefactoryreloaded.farmables.grindables;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import powercrystals.minefactoryreloaded.api.IFactoryGrindable;
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
	public List<ItemStack> grind(World world, EntityLiving entity, Random random)
	{
		List<ItemStack> drops = new ArrayList<ItemStack>();
		drops.add(new ItemStack(Block.cloth, 1, ((EntitySheep)entity).getFleeceColor()));
		return drops;
	}
}
