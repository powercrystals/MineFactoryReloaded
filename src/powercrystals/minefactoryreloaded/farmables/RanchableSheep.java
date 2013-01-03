package powercrystals.minefactoryreloaded.farmables;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.animals.TileEntityRancher;
import powercrystals.minefactoryreloaded.api.IFactoryRanchable;

public class RanchableSheep implements IFactoryRanchable
{
	@Override
	public Class<?> getRanchableEntity()
	{
		return EntitySheep.class;
	}

	@Override
	public List<ItemStack> ranch(World world, EntityLiving entity, TileEntityRancher rancher)
	{
		EntitySheep s = (EntitySheep)entity;
		
		if(s.getSheared())
		{
			return null;
		}
		
		List<ItemStack> stacks = new LinkedList<ItemStack>();
		stacks.add(new ItemStack(Block.cloth, 1, s.getFleeceColor()));
		s.setSheared(true);
		
		return stacks;
	}
}
