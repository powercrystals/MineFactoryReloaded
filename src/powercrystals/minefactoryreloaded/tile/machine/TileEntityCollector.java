package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.core.MFRInventoryUtil;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;

public class TileEntityCollector extends TileEntityFactoryInventory
{
	public void addToChests(EntityItem i)
	{
		if(i.isDead)
		{
			return;
		}
		
		ItemStack s = i.getEntityItem();
		s = MFRInventoryUtil.dropStack(this, s);
		if(s == null)
		{
			i.setDead();
			return;
		}
		i.setEntityItemStack(s);
	}
	
	public int getSizeInventory()
	{
		return 0;
	}
	
	public String getInvName()
	{
		return "Item Collector";
	}
}
