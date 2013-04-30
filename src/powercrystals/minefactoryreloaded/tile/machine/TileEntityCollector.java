package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import powercrystals.core.util.UtilInventory;
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
		s = UtilInventory.dropStack(this, s);
		if(s == null)
		{
			i.setDead();
			return;
		}
		i.setEntityItemStack(s);
	}
	
	@Override
	public int getSizeInventory()
	{
		return 0;
	}
	
	@Override
	public String getInvName()
	{
		return "Item Collector";
	}
	
	@Override
	public boolean manageSolids()
	{
		return true;
	}
}
