package powercrystals.minefactoryreloaded.farmables;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.animals.TileEntityRancher;
import powercrystals.minefactoryreloaded.api.IFactoryRanchable;

public class RanchableMooshroom implements IFactoryRanchable {

	@Override
	public Class<?> getRanchableEntity()
	{
		return EntityMooshroom.class;
	}

	@Override
	public List<ItemStack> ranch(World world, EntityLiving entity, TileEntityRancher rancher)
	{
		List<ItemStack> drops = new LinkedList<ItemStack>();
		
		int bowlIndex = rancher.findFirstStack(Item.bowlEmpty.shiftedIndex, 0);
		if(bowlIndex >= 0)
		{
			drops.add(new ItemStack(Item.bowlSoup));
			rancher.decrStackSize(bowlIndex, 1);
		}
		
		int bucketIndex = rancher.findFirstStack(Item.bucketEmpty.shiftedIndex, 0);
		if(bucketIndex >= 0)
		{
			drops.add(new ItemStack(Item.bucketMilk));
			rancher.setInventorySlotContents(bucketIndex, null);
		}
		else
		{
			drops.add(new ItemStack(MineFactoryReloadedCore.milkItem));
		}
		
		return drops;
	}
}
