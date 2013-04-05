package powercrystals.minefactoryreloaded.tile.machine;

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

import powercrystals.core.util.Util;
import powercrystals.core.inventory.IInventoryManager;
import powercrystals.core.inventory.InventoryManager;
import powercrystals.minefactoryreloaded.core.MFRInventoryUtil;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactory;

@SuppressWarnings("unused")
public class TileEntityEjector extends TileEntityFactory
{
	private boolean _lastRedstoneState;
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(worldObj.isRemote)
		{
			return;
		}
		boolean redstoneState = Util.isRedstonePowered(this);
		if(redstoneState && !_lastRedstoneState)
		{
			Map<ForgeDirection, IInventory> chests = MFRInventoryUtil.findChests(worldObj, xCoord, yCoord, zCoord);
inv:		for(Entry<ForgeDirection, IInventory> chest : chests.entrySet())
			{
				if(chest.getKey() == getDirectionFacing())
				{
					continue;
				}
				
				IInventoryManager inventory = InventoryManager.create(chest.getValue(), chest.getKey());
				
				ItemStack output = inventory.removeItem(1);
				ItemStack remaining = MFRInventoryUtil.dropStack(this, output, this.getDirectionFacing(), this.getDirectionFacing());
				if(remaining != null)
				{
					// put the removed item back in its original container if it wasn't successfully dropped
					inventory.addItem(remaining);
				}
				else
				{
					break inv;
				}
			}
		}
		_lastRedstoneState = redstoneState;
	}
}
