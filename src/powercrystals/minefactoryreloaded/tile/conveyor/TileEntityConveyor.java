package powercrystals.minefactoryreloaded.tile.conveyor;

import buildcraft.api.gates.IAction;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import powercrystals.core.asm.relauncher.Implementable;
import powercrystals.core.net.PacketWrapper;
import powercrystals.core.position.IRotateableTile;
import powercrystals.core.util.Util;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.net.Packets;

import cpw.mods.fml.common.network.PacketDispatcher;

@Implementable("buildcraft.core.IMachine")
public class TileEntityConveyor extends TileEntity implements IRotateableTile, ISidedInventory
{
	private int _dye = -1;
	private boolean _isReversed = false;
	private boolean _redNetAllowsActive = true;
	private boolean _conveyorActive = true;
	
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
		return PacketWrapper.createPacket(MineFactoryReloadedCore.modNetworkChannel, Packets.ConveyorDescription, new Object[] { xCoord, yCoord, zCoord, _dye, _conveyorActive});
	}
	
	@Override
	public void rotate()
	{
		int md = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		if(md == 0)
		{
			if(worldObj.isBlockSolidOnSide(xCoord + 1, yCoord, zCoord, ForgeDirection.WEST))
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 4);
			}
			else if(worldObj.isBlockSolidOnSide(xCoord - 1, yCoord, zCoord, ForgeDirection.EAST))
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
			if(worldObj.isBlockSolidOnSide(xCoord - 1, yCoord, zCoord, ForgeDirection.EAST))
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
			if(worldObj.isBlockSolidOnSide(xCoord, yCoord, zCoord + 1, ForgeDirection.NORTH))
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 5);
			}
			else if(worldObj.isBlockSolidOnSide(xCoord, yCoord, zCoord - 1, ForgeDirection.SOUTH))
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
			if(worldObj.isBlockSolidOnSide(xCoord, yCoord, zCoord - 1, ForgeDirection.SOUTH))
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
			if(worldObj.isBlockSolidOnSide(xCoord - 1, yCoord, zCoord, ForgeDirection.EAST))
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 6);
			}
			else if(worldObj.isBlockSolidOnSide(xCoord + 1, yCoord, zCoord, ForgeDirection.WEST))
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
			if(worldObj.isBlockSolidOnSide(xCoord + 1, yCoord, zCoord, ForgeDirection.WEST))
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
			if(worldObj.isBlockSolidOnSide(xCoord, yCoord, zCoord - 1, ForgeDirection.SOUTH))
			{
				rotateTo(worldObj, xCoord, yCoord, zCoord, 7);
			}
			else if(worldObj.isBlockSolidOnSide(xCoord, yCoord, zCoord + 1, ForgeDirection.NORTH))
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
			if(worldObj.isBlockSolidOnSide(xCoord, yCoord, zCoord + 1, ForgeDirection.NORTH))
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
		nbtTagCompound.setBoolean("isReversed", _isReversed);
		nbtTagCompound.setBoolean("redNetActive", _conveyorActive);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);
		
		if(nbtTagCompound.hasKey("dyeColor"))
		{
			_dye = nbtTagCompound.getInteger("dyeColor");
		}

		if(nbtTagCompound.hasKey("isReversed"))
		{
			_isReversed = nbtTagCompound.getBoolean("isReversed");
		}
		
		if(nbtTagCompound.hasKey("conveyorActive"))
		{
			_conveyorActive = nbtTagCompound.getBoolean("conveyorActive");
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
		int	horizDirection = worldObj.getBlockMetadata(xCoord, yCoord, zCoord) & 0x03;
		
		float dropOffsetX = 0.5F;
		float dropOffsetY = 0.4F;
		float dropOffsetZ = 0.5F;
		double motionX = 0.0D;
		double motionY = 0.0D;
		double motionZ = 0.0D;
		
		//because of the setup, slot is also the ForgeDirection ordinal from which the item is being inserted
		switch(slot)
		{
			case 0: //DOWN
				dropOffsetY = 0.3F;
				motionY = 0.15D;
				break;
			case 1: //UP
				dropOffsetY = 0.8F;
				motionY = -0.15D;
				break;				
			case 2: //NORTH
				dropOffsetZ = 0.2F;
				motionZ = 0.15D;
				break;
			case 3: //SOUTH
				dropOffsetZ = 0.8F;
				motionZ = -0.15D;
				break;
			case 4: //WEST
				dropOffsetX = 0.2F;
				motionX = 0.15D;
				break;
			case 5: //EAST
				dropOffsetX = 0.8F;
				motionX = -0.15D;
				break;
			case 6: //UNKNOWN
		}
		
		if(horizDirection == 0)
		{
			motionX = 0.05D;
		}
		else if(horizDirection == 1)
		{
			motionZ = 0.05D;
		}
		else if(horizDirection == 3)
		{
			motionX = -0.05D;
		}
		else if(horizDirection == 3)
		{
			motionX = -0.05D;
		}
		
		EntityItem entityitem = new EntityItem(worldObj, xCoord + dropOffsetX, yCoord + dropOffsetY, zCoord + dropOffsetZ, stack.copy());
		entityitem.motionX = motionX;
		entityitem.motionY = motionY;
		entityitem.motionZ = motionZ;
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
    
    /*
     * From above: returns true if the conveyor is not going uphill
     * For the NSEW sides: returns true if (conveyor is going uphill) || (!conveyor is facing in the 'from' direction)
     * From below/unknown: returns true
     */
    @Override
	public boolean canInsertItem(int slot, ItemStack stack, int side)
    {
    	int blockmeta;
    	if(side == ForgeDirection.UP.ordinal())
    	{
    		return (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) & 0x04) == 0;
    	} 
    	else if(side == ForgeDirection.EAST.ordinal())
    	{
    		blockmeta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    		return (blockmeta & 0x04) != 0 || (blockmeta & 0x03) != 0; 
    	}
    	else if(side == ForgeDirection.SOUTH.ordinal())
    	{
    		blockmeta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    		return (blockmeta & 0x04) != 0 || (blockmeta & 0x03) != 1; 
    	}
    	else if(side == ForgeDirection.WEST.ordinal())
    	{
    		blockmeta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    		return (blockmeta & 0x04) != 0 || (blockmeta & 0x03) != 2; 
    	}
    	else if(side == ForgeDirection.NORTH.ordinal())
    	{
    		blockmeta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    		return (blockmeta & 0x04) != 0 || (blockmeta & 0x03) != 3; 
    	}
    	return true;
    }
    
    @Override
	public boolean canExtractItem(int slot, ItemStack stack, int side)
    {
    	return false;
    }
    
    //IMachine
	public boolean isActive()
	{
		return false;
	}
	
	public boolean manageLiquids()
	{
		return false;
	}
	
	public boolean manageSolids()
	{
		return true;
	}
	
	public boolean allowActions()
	{
		return false;
	}
	
	public boolean allowAction(IAction _)
	{
		return this.allowActions();
	}
	
	// RedNet
	public void onRedNetChanged(int value)
	{
		if(_redNetAllowsActive ^ value <= 0)
		{
			_redNetAllowsActive = value <= 0;
			updateConveyorActive();
		}
		setReversed(value < 0);
	}
	
	public void updateConveyorActive()
	{
		setConveyorActive(_redNetAllowsActive && !Util.isRedstonePowered(this));
	}
	
	public boolean getConveyorActive()
	{
		return _conveyorActive;
	}
	
	public void setConveyorActive(boolean conveyorActive)
	{
		boolean wasActive = _conveyorActive;
		_conveyorActive = conveyorActive;
		
		if(wasActive ^ _conveyorActive)
		{
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	private void setReversed(boolean isReversed)
	{
		boolean wasReversed = _isReversed;
		_isReversed = isReversed;
		
		if(wasReversed ^ _isReversed)
		{
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getReversedMeta(worldObj.getBlockMetadata(xCoord, yCoord, zCoord)), 3);
		}
	}
	
	private int getReversedMeta(int meta)
	{
		int directionComponent = ( meta + 2 ) % 4;
		int slopeComponent;
		
		if(meta / 4 == 1)
		{
			slopeComponent = 2;
		}
		else if(meta / 4 == 2)
		{
			slopeComponent = 1;
		}
		else
		{
			slopeComponent = 0;
		}
		
		return slopeComponent * 4 + directionComponent;
	}
}
