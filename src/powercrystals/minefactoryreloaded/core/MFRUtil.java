package powercrystals.minefactoryreloaded.core;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IToolHammer;
import powercrystals.minefactoryreloaded.api.IToolHammerAdvanced;

public class MFRUtil
{
	public static boolean isHoldingHammer(EntityPlayer player)
	{
		if(player.inventory.getCurrentItem() == null)
		{
			return false;
		}
		Item currentItem = Item.itemsList[player.inventory.getCurrentItem().itemID];
		if(currentItem != null && currentItem instanceof IToolHammerAdvanced)
		{
			return ((IToolHammerAdvanced)currentItem).isActive(player.inventory.getCurrentItem());
		}
		else if(currentItem instanceof IToolHammer)
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean isHolding(EntityPlayer player, Class<?> itemClass)
	{
		if(player.inventory.getCurrentItem() == null)
		{
			return false;
		}
		Item currentItem = Item.itemsList[player.inventory.getCurrentItem().itemID];
		if(currentItem != null && itemClass.isAssignableFrom(currentItem.getClass()))
		{
			return true;
		}
		return false;
	}
	
	public static Entity prepareMob(Class<? extends Entity> entity, World world)
	{
		try
		{
			Entity e = entity.getConstructor(new Class[] {World.class}).newInstance(new Object[] { world });
			if(e instanceof EntityLiving)
			{
				((EntityLiving)e).initCreature();
			}
			return e;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
