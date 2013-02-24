package powercrystals.minefactoryreloaded.processing;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import net.minecraftforge.oredict.OreDictionary;
import powercrystals.core.oredict.OreDictTracker;
import powercrystals.minefactoryreloaded.core.TileEntityFactory;

public class TileEntityUnifier extends TileEntityFactory implements IInventory, ISidedInventory
{
	private ItemStack[] _inventory = new ItemStack[2];
	
	public TileEntityUnifier()
	{
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(!worldObj.isRemote)
		{
			ItemStack output = null;
			if(_inventory[0] != null)
			{
				System.out.println("Checking for names for " + _inventory[0].itemID + ":" + _inventory[0].getItemDamage());
				List<String> names = OreDictTracker.getNamesFromItem(_inventory[0]);
				if(names == null) System.out.println("Found null names");
				else System.out.println("Found " + names.size() + " names");
				
				if(names == null || names.size() != 1)
				{
					System.out.println("Invalid match - moving original");
					output = _inventory[0];
				}
				else
				{
					System.out.println("Found name " + names.get(0));
					output = OreDictionary.getOres(names.get(0)).get(0).copy();
					output.stackSize = _inventory[0].stackSize;
					System.out.println("New stack is " + output.stackSize + " of " + output.itemID + ":" + output.getItemDamage());
				}
				
				moveItemStack(output);
				if(_inventory[1] != null)
				{
					System.out.println("Inv 1 now contains: " + _inventory[1].stackSize + " of " + _inventory[1].itemID + ":" + _inventory[1].getItemDamage());
				}
				else
				{
					System.out.println("Inv 1 is null");
				}
			}
		}
	}
	
	private void moveItemStack(ItemStack source)
	{
		int amt = 0;
		if(source == null)
		{
			return;
		}
		else if(_inventory[1] == null)
		{
			amt = Math.min(getInventoryStackLimit(), source.getMaxStackSize());
		}
		else if(source.itemID != _inventory[1].itemID || source.getItemDamage() != _inventory[1].getItemDamage())
		{
			return;
		}
		else if(source.getTagCompound() != null || _inventory[1].getTagCompound() != null)
		{
			return;
		}
		else
		{
			amt = Math.min(getInventoryStackLimit(), _inventory[1].getMaxStackSize()) - _inventory[1].stackSize;
		}
		amt = Math.min(amt, source.stackSize);
		
		if(_inventory[1] == null)
		{
			_inventory[1] = source;
			_inventory[0].stackSize -= source.stackSize;
		}
		else
		{
			_inventory[1].stackSize += amt;
			_inventory[0].stackSize -= amt;
		}
		
		if(_inventory[0].stackSize == 0)
		{
			_inventory[0] = null;
		}
	}

	@Override
	public int getSizeInventory()
	{
		return 2;
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

	@Override
	public int getStartInventorySide(ForgeDirection side)
	{
		if(side.ordinal() < 2) return 0;
		return 1;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side)
	{
		return 1;
	}
}
