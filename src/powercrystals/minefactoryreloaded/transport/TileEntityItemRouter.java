package powercrystals.minefactoryreloaded.transport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.core.TileEntityFactory;

public class TileEntityItemRouter extends TileEntityFactory implements IInventory, ISidedInventory
{
	private ItemStack[] _inventory = new ItemStack[getSizeInventory()];
	private static final int[] _invOffsets = new int[] { 0, 0, 9, 18, 36, 27 };
	private static final ForgeDirection[] _outputDirections = new ForgeDirection[]
			{ ForgeDirection.DOWN, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST };
	private Random _rand;
	
	public TileEntityItemRouter()
	{
		_rand = new Random();
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(!worldObj.isRemote)
		{
			if(_inventory[45] != null && routeItem(_inventory[45]))
			{
				_inventory[45] = null;
			}
		}
	}
	
	@Override
	public boolean canRotate()
	{
		return false;
	}
	
	public boolean routeItem(ItemStack stack)
	{
		List<ForgeDirection> filteredOutputs = new ArrayList<ForgeDirection>();
		List<ForgeDirection> emptyOutputs = new ArrayList<ForgeDirection>();
		for(ForgeDirection d : _outputDirections)
		{
			if(isSideValidForItem(stack, d))
			{
				filteredOutputs.add(d);
			}
			if(isSideEmpty(d))
			{
				emptyOutputs.add(d);
			}
		}
		
		if(filteredOutputs.size() > 0)
		{
			MFRUtil.dropStackDirected(this, stack, filteredOutputs.get(_rand.nextInt(filteredOutputs.size())));
			return true;
		}
		else if(emptyOutputs.size() > 0)
		{
			MFRUtil.dropStackDirected(this, stack, emptyOutputs.get(_rand.nextInt(emptyOutputs.size())));
			return true;
		}
		return false;
	}
	
	private boolean isSideValidForItem(ItemStack stack, ForgeDirection side)
	{
		if(side == ForgeDirection.UNKNOWN || side == ForgeDirection.UP)
		{
			return false;
		}
		
		int sideStart = _invOffsets[side.ordinal()];
		
		for(int i = sideStart; i < sideStart + 9; i++)
		{
			if(_inventory[i] != null)
			{
				if(_inventory[i].itemID == stack.itemID && (_inventory[i].getItemDamage() == stack.getItemDamage()) || stack.getItem().isDamageable())
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean isSideEmpty(ForgeDirection side)
	{
		if(side == ForgeDirection.UNKNOWN || side == ForgeDirection.UP)
		{
			return false;
		}
		
		int sideStart = _invOffsets[side.ordinal()];
		
		for(int i = sideStart; i < sideStart + 9; i++)
		{
			if(_inventory[i] != null)
			{
				return false;
			}
		}
		
		return true;
	}

	@Override
	public int getSizeInventory()
	{
		return 46;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return _inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int size)
	{
		if(_inventory[slot] != null)
		{
			if(_inventory[slot].stackSize <= size)
			{
				ItemStack itemstack = _inventory[slot];
				_inventory[slot] = null;
				return itemstack;
			}
			ItemStack itemstack1 = _inventory[slot].splitStack(size);
			if(_inventory[slot].stackSize == 0)
			{
				_inventory[slot] = null;
			}
			return itemstack1;
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		_inventory[slot] = stack;
	}

	@Override
	public String getInvName()
	{
		return "Item Router";
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return player.getDistanceSq(xCoord, yCoord, zCoord) <= 64D;
	}

	@Override
	public void openChest()
	{
	}

	@Override
	public void closeChest()
	{
	}

	@Override
	public int getStartInventorySide(ForgeDirection side)
	{
		return 45;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side)
	{
		return 1;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		_inventory = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 0xff;
			if(j >= 0 && j < _inventory.length)
			{
				ItemStack s = new ItemStack(0, 0, 0);
				s.readFromNBT(nbttagcompound1);
				_inventory[j] = s;
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < _inventory.length; i++)
		{
			if(_inventory[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				_inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}
}
