package powercrystals.minefactoryreloaded.tile.machine;

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

import powercrystals.core.inventory.IInventoryManager;
import powercrystals.core.inventory.InventoryManager;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactory;

public class TileEntityCollector extends TileEntityFactory
{
	@Override
	public boolean canRotate()
	{
		return false;
	}
	
	public void addToChests(EntityItem i)
	{
		if(i.isDead)
		{
			return;
		}
		
		ItemStack s = i.getEntityItem();
		Map<ForgeDirection, IInventory> chests = UtilInventory.findChests(worldObj, xCoord, yCoord, zCoord);
		for(Entry<ForgeDirection, IInventory> chest : chests.entrySet())
		{
			IInventoryManager manager = InventoryManager.create(chest.getValue(), chest.getKey().getOpposite()); 
			s = manager.addItem(s);
			if(s == null)
			{
				i.setDead();
				return;
			}
		}
	}
}
