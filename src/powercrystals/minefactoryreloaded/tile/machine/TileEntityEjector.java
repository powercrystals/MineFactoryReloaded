package powercrystals.minefactoryreloaded.tile.machine;

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

import powercrystals.core.util.Util;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.core.MFRInventoryUtil;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactory;

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
				
				if(toSide != ForgeDirection.UNKNOWN && inventory instanceof ISidedInventory)
				{
					for(int i : ((ISidedInventory)inventory).getSizeInventorySide(toSide.getOpposite().ordinal()))
					{
						ItemStack targetStack = inventory.getStackInSlot(i);
						if(targetStack != null)
						{
							ItemStack output = targetStack.copy();
							output.stackSize = 1;
							output = MFRInventoryUtil.dropStack(this, output, this.getDirectionFacing(), this.getDirectionFacing());
							if(targetStack.stackSize == 1 && output != null && output.stackSize == 0)
							{
								inventory.setInventorySlotContents(i, null);
							}
							else if(output == null || output.stackSize == 0)
							{
								ItemStack newStack = targetStack.copy();
								newStack.stackSize--;
								inventory.setInventorySlotContents(i, newStack);
							}
							break inv;
						}
					}
				}
				else
				{
					for(int i = 0; i < inventory.getSizeInventory(); i++)
					{
						ItemStack targetStack = inventory.getStackInSlot(i);
						if(targetStack != null)
						{
							ItemStack output = targetStack.copy();
							output.stackSize = 1;
							MFRInventoryUtil.dropStack(this, output, this.getDirectionFacing(), this.getDirectionFacing());
							if(targetStack.stackSize == 1 && output.stackSize == 0)
							{
								inventory.setInventorySlotContents(i, null);
							}
							else if(output.stackSize == 0)
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
		}
		_lastRedstoneState = redstoneState;
	}
}
