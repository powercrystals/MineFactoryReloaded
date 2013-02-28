package powercrystals.minefactoryreloaded.decorative;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import powercrystals.core.net.PacketWrapper;
import powercrystals.core.util.Util;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.TileEntityFactory;
import powercrystals.minefactoryreloaded.net.Packets;

public class TileEntityAutoJukebox extends TileEntityFactory implements IInventory, ISidedInventory
{
	private ItemStack[] _inventory = new ItemStack[2];
	private boolean _lastRedstoneState;
	private boolean _canCopy;
	
	public void setCanCopy(boolean canCopy)
	{
		_canCopy = canCopy;
	}
	
	public boolean getCanCopy()
	{
		if(worldObj.isRemote)
		{
			return _canCopy;
		}
		else if(_inventory[0] != null && _inventory[0].getItem() instanceof ItemRecord && _inventory[1] != null && _inventory[1].itemID == MineFactoryReloadedCore.blankRecordItem.itemID)
		{
			return true;
		}
		return false;
	}
	
	public void copyRecord()
	{
		if(!worldObj.isRemote && getCanCopy())
		{
			_inventory[1] = _inventory[0].copy();
		}
	}
	
	@Override
	public int getSizeInventory()
	{
		return 2;
	}

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
			worldObj.playAuxSFX(1005, xCoord, yCoord, zCoord, 0);
			if(_inventory[0] != null && _inventory[0].getItem() instanceof ItemRecord)
			{
				worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1005, xCoord, yCoord, zCoord, _inventory[0].itemID);
				PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 50, worldObj.getWorldInfo().getDimension(),
						PacketWrapper.createPacket(MineFactoryReloadedCore.modId, Packets.AutoJukeboxPlay, new Object[] { xCoord, yCoord, zCoord, _inventory[0].itemID } ));
			}
		}
		
		_lastRedstoneState = redstoneState;
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
		return "Auto Jukebox";
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
		return 0;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side)
	{
		return 1;
	}
}
