package powercrystals.minefactoryreloaded.animals;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.ItemFactory;

public class ItemGrowthSyringe extends ItemFactory
{
	public ItemGrowthSyringe()
	{
		super(MineFactoryReloadedCore.syringeGrowthItemId.getInt());
		setMaxStackSize(1);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack s, EntityLiving e)
	{
		if (e instanceof EntityAgeable && ((EntityAgeable)e).getGrowingAge() < 0)
		{
			((EntityAgeable)e).setGrowingAge(0);
			s.itemID = MineFactoryReloadedCore.syringeEmptyItem.shiftedIndex;
			
			return true;
		}
		
		return false;
	}
}
