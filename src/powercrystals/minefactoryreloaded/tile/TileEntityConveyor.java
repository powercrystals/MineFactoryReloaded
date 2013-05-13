package powercrystals.minefactoryreloaded.tile;

import buildcraft.core.IMachine;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.net.PacketWrapper;
import powercrystals.core.position.IRotateableTile;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.net.Packets;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileEntityConveyor extends TileEntity implements IRotateableTile, ISidedInventory, IMachine
{
	private int _dye = -1;
	
	public int getDyeColor()
	{
		return _dye;
	}
	
	public void setDyeColor(int dye)
	{
		if(worldObj != null && !worldObj.isRemote && _dye != dye)
		{
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 50, worldObj.provider.dimensionId, getDescriptionPacket());
		}
		_dye = dye;
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		return PacketWrapper.createPacket(MineFactoryReloadedCore.modNetworkChannel, Packets.ConveyorDescription, new Object[] { xCoord, yCoord, zCoord, _dye });
	}
	
	@Override
	public void rotate()
	{
		int md = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		if(md == 0)
		{
			int nextBlockId = worldObj.getBlockId(xCoord + 1, yCoord, zCoord);
			int prevBlockId = worldObj.getBlockId(xCoord - 1, yCoord, zCoord);
			if(Block.blocksList[nextBlockId] != null && Block.blocksList[nextBlockId].isOpaqueCube())
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 4);
			}
			else if(Block.blocksList[prevBlockId] != null && Block.blocksList[prevBlockId].isOpaqueCube())
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 8);
			}
			else
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 1);
			}
		}
		else if(md == 4)
		{
			int prevBlockId = worldObj.getBlockId(xCoord - 1, yCoord, zCoord);
			if(Block.blocksList[prevBlockId] != null && Block.blocksList[prevBlockId].isOpaqueCube())
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 8);
			}
			else
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 1);
			}
		}
		else if(md == 8)
		{
			rotateTo(worldObj, xCoord, yCoord, zCoord, 1);
		}
		
		
		if(md == 1)
		{
			int nextBlockId = worldObj.getBlockId(xCoord, yCoord, zCoord + 1);
			int prevBlockId = worldObj.getBlockId(xCoord, yCoord, zCoord - 1);
			if(Block.blocksList[nextBlockId] != null && Block.blocksList[nextBlockId].isOpaqueCube())
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 5);
			}
			else if(Block.blocksList[prevBlockId] != null && Block.blocksList[prevBlockId].isOpaqueCube())
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 9);
			}
			else
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 2);
			}
		}
		else if(md == 5)
		{
			int prevBlockId = worldObj.getBlockId(xCoord, yCoord, zCoord - 1);
			if(Block.blocksList[prevBlockId] != null && Block.blocksList[prevBlockId].isOpaqueCube())
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 9);
			}
			else
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 2);
			}
		}
		else if(md == 9)
		{
			rotateTo(worldObj, xCoord, yCoord, zCoord, 2);
		}
		
		
		if(md == 2)
		{
			int nextBlockId = worldObj.getBlockId(xCoord - 1, yCoord, zCoord);
			int prevBlockId = worldObj.getBlockId(xCoord + 1, yCoord, zCoord);
			if(Block.blocksList[nextBlockId] != null && Block.blocksList[nextBlockId].isOpaqueCube())
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 6);
			}
			else if(Block.blocksList[prevBlockId] != null && Block.blocksList[prevBlockId].isOpaqueCube())
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 10);
			}
			else
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 3);
			}
		}
		else if(md == 6)
		{
			int prevBlockId = worldObj.getBlockId(xCoord + 1, yCoord, zCoord);
			if(Block.blocksList[prevBlockId] != null && Block.blocksList[prevBlockId].isOpaqueCube())
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 10);
			}
			else
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 3);
			}
		}
		else if(md == 10)
		{
			rotateTo(worldObj, xCoord, yCoord, zCoord, 3);
		}
		
		
		if(md == 3)
		{
			int nextBlockId = worldObj.getBlockId(xCoord, yCoord, zCoord - 1);
			int prevBlockId = worldObj.getBlockId(xCoord, yCoord, zCoord + 1);
			if(Block.blocksList[nextBlockId] != null && Block.blocksList[nextBlockId].isOpaqueCube())
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 7);
			}
			else if(Block.blocksList[prevBlockId] != null && Block.blocksList[prevBlockId].isOpaqueCube())
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 11);
			}
			else
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 0);
			}
		}
		else if(md == 7)
		{
			int prevBlockId = worldObj.getBlockId(xCoord, yCoord, zCoord + 1);
			if(Block.blocksList[prevBlockId] != null && Block.blocksList[prevBlockId].isOpaqueCube())
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 11);
			}
			else
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 0);
			}
		}
		else if(md == 11)
		{
			rotateTo(worldObj, xCoord, yCoord, zCoord, 0);
		}
	}
	
	private void rotateTo(World world, int xCoord, int yCoord, int zCoord, int newmd)
	{
		world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, newmd, 2);
	}
	
	@Override
	public boolean canRotate()
	{
		return true;
	}
	
	@Override
	public ForgeDirection getDirectionFacing() 
	{
		return ForgeDirection.UNKNOWN;
	}
	
	@Override
	public boolean canUpdate()
	{
		return false;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);
		
		nbtTagCompound.setInteger("dyeColor", _dye);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);
		
		if(nbtTagCompound.hasKey("dyeColor"))
		{
			_dye = nbtTagCompound.getInteger("dyeColor");
		}
	}
	
	//IInventory
	@Override
	public int getSizeInventory()
	{
		return 7;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return null;
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int count)
	{
		return null;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		float dropOffsetX = 0.5F;
		float dropOffsetY = 0.5F;
		float dropOffsetZ = 0.5F;
		
		//because of the setup, slot is also the ForgeDirection ordinal from which the item is being inserted
		switch(slot)
		{
			case 0: //DOWN
				dropOffsetY = 0.3F;
				break;
			case 1: //UP
				dropOffsetY = 0.8F;
				break;				
			case 2: //NORTH
				dropOffsetZ = 0.2F;
				break;
			case 3: //SOUTH
				dropOffsetZ = 0.8F;
				break;
			case 4: //EAST
				dropOffsetX = 0.8F;
				break;
			case 5: //WEST
				dropOffsetX = 0.2F;
				break;
			case 6: //UNKNOWN
		}
		
		EntityItem entityitem = new EntityItem(worldObj, this.xCoord + dropOffsetX, this.yCoord + dropOffsetY, this.zCoord + dropOffsetZ, stack.copy());
		entityitem.motionX = 0.0D;
		entityitem.motionY = 0.0D;
		entityitem.motionZ = 0.0D;
		entityitem.delayBeforeCanPickup = 20;
		worldObj.spawnEntityInWorld(entityitem);
	}
	
	@Override
	public String getInvName()
	{
		return "Conveyor Belt";
	}
	
	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public void onInventoryChanged()
	{
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return false;
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
	public boolean isStackValidForSlot(int slot, ItemStack stack)
    {
    	return true;
    }
    
    //ISidedInventory
    @Override
	public int[] getAccessibleSlotsFromSide(int sideOrdinal)
    {
    	int[] accessibleSlot = {sideOrdinal};
    	return accessibleSlot;
    }
    
    @Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j)
    {
    	return true;
    }
    
    @Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j)
    {
    	return false;
    }
    
    //IMachine
	@Override
	public boolean isActive()
	{
		return false;
	}
	
	@Override
	public boolean manageLiquids()
	{
		return false;
	}
	
	@Override
	public boolean manageSolids()
	{
		return true;
	}
	
	@Override
	public boolean allowActions()
	{
		return false;
	}
}
