package powercrystals.minefactoryreloaded.transport;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.core.TileEntityFactory;

public class TileEntityLiquidRouter extends TileEntityFactory implements IInventory, ITankContainer
{
	private ItemStack[] _inventory = new ItemStack[6];
	private LiquidTank[] _bufferTanks = new LiquidTank[6];
	private static final ForgeDirection[] _outputDirections = new ForgeDirection[]
			{ ForgeDirection.DOWN, ForgeDirection.UP, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST };

	public TileEntityLiquidRouter()
	{
		for(int i = 0; i < 6; i++)
		{
			_bufferTanks[i] = new LiquidTank(LiquidContainerRegistry.BUCKET_VOLUME);
			_bufferTanks[i].setTankPressure(-1);
		}
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		for(int i = 0; i < 6; i++)
		{
			if(_bufferTanks[i].getLiquid() != null && _bufferTanks[i].getLiquid().amount > 0)
			{
				_bufferTanks[i].getLiquid().amount -= pumpLiquid(_bufferTanks[i].getLiquid(), true);
			}
		}
	}
	
	private int pumpLiquid(LiquidStack resource, boolean doFill)
	{
		if(resource == null || resource.itemID <= 0 || resource.amount <= 0) return 0;
		
		int amountRemaining = resource.amount;
		
		for(int i = 0; i < 6; i++)
		{
			if(LiquidContainerRegistry.containsLiquid(_inventory[i], resource))
			{
				TileEntity te = BlockPosition.getAdjacentTileEntity(this, _outputDirections[i]);
				if(te != null && te instanceof ITankContainer)
				{
					amountRemaining -= ((ITankContainer)te).fill(_outputDirections[i].getOpposite(),	new LiquidStack(resource.itemID, amountRemaining, resource.itemMeta), doFill);
					if(amountRemaining <= 0)
					{
						break;
					}
				}
			}
		}
		
		return resource.amount - amountRemaining;
	}
	
	@Override
	public boolean canRotate()
	{
		return false;
	}

	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
	{
		return pumpLiquid(resource, doFill);
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill)
	{
		return pumpLiquid(resource, doFill);
	}

	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		return null;
	}

	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain)
	{
		return null;
	}

	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction)
	{
		return new ILiquidTank[] { _bufferTanks[direction.ordinal()] };
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type)
	{
		if(LiquidContainerRegistry.containsLiquid(_inventory[direction.ordinal()], type))
		{
			return _bufferTanks[direction.ordinal()];
		}
		return null;
	}

	@Override
	public int getSizeInventory()
	{
		return 6;
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
		return "Liquid Router";
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
}
