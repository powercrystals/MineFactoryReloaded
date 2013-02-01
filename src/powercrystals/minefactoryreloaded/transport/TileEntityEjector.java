package powercrystals.minefactoryreloaded.transport;

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

import powercrystals.core.util.Util;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.core.TileEntityFactory;

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
			Map<ForgeDirection, IInventory> chests = UtilInventory.findChests(worldObj, xCoord, yCoord, zCoord);
inv:		for(Entry<ForgeDirection, IInventory> chest : chests.entrySet())
			{
				if(chest.getKey() == getDirectionFacing())
				{
					continue;
				}
				
				IInventory inventory = chest.getValue();
				ForgeDirection toSide = chest.getKey();
				
				int invStart = 0;
				int invEnd = inventory.getSizeInventory();
				
				if(toSide != ForgeDirection.UNKNOWN && inventory instanceof ISidedInventory)
				{
					invStart = ((ISidedInventory)inventory).getStartInventorySide(toSide.getOpposite());
					invEnd = invStart + ((ISidedInventory)inventory).getSizeInventorySide(toSide.getOpposite());
				}
				
				for(int i = invStart; i < invEnd; i++)
				{
					ItemStack targetStack = inventory.getStackInSlot(i);
					if(targetStack != null)
					{
						ItemStack output = targetStack.copy();
						output.stackSize = 1;
						MFRUtil.dropStackDirected(this, output, this.getDirectionFacing());
						if(targetStack.stackSize == 1)
						{
							inventory.setInventorySlotContents(i, null);
						}
						else
						{
							ItemStack newStack = targetStack.copy();
							newStack.stackSize--;
							inventory.setInventorySlotContents(i, newStack);
						}
						break inv;
					}
				}
			}
		}
		_lastRedstoneState = redstoneState;
	}
}
