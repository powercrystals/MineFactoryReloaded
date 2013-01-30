package powercrystals.minefactoryreloaded.animals;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.ItemFactory;

public class ItemHealthSyringe extends ItemFactory
{
	public ItemHealthSyringe()
	{
		super(MineFactoryReloadedCore.syringeHealthItemId.getInt());
		setMaxStackSize(1);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack s, EntityLiving e)
	{
		if (e.getHealth() < e.getMaxHealth())
		{
			e.heal(2);
			s.itemID = MineFactoryReloadedCore.syringeEmptyItem.itemID;
			
			return true;
		}
		
		return false;
	}
}
