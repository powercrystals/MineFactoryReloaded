package powercrystals.minefactoryreloaded.tile.base;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.net.PacketWrapper;
import powercrystals.core.position.IRotateableTile;
import powercrystals.minefactoryreloaded.MineFactoryReloadedClient;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.IHarvestAreaContainer;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryInventory;
import powercrystals.minefactoryreloaded.net.Packets;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class TileEntityFactory extends TileEntity implements IRotateableTile
{
	// first index is rotation, second is side
	private static final int[][] _textureSelection = new int[][]
			{
				{ 0, 1, 2, 3, 4, 5 }, // 0 D (unused)
				{ 0, 1, 2, 3, 4, 5 }, // 1 U (unused)
				{ 0, 1, 2, 3, 4, 5 }, // 2 N
				{ 0, 1, 3, 2, 5, 4 }, // 3 S
				{ 0, 1, 5, 4, 2, 3 }, // 4 W
				{ 0, 1, 4, 5, 3, 2 }, // 5 E
			};
	
	private ForgeDirection _forwardDirection;
	
	private boolean _isActive;
	
	protected int _rednetState;
	
	protected TileEntityFactory()
	{
		_forwardDirection = ForgeDirection.NORTH;
	}
	
	@Override
	public void validate()
	{
		if(worldObj.isRemote && this instanceof IHarvestAreaContainer)
		{
			MineFactoryReloadedClient.addTileToAreaList((IHarvestAreaContainer)this);
		}
	}
	
	@Override
	public void invalidate()
	{
		if(worldObj.isRemote && this instanceof IHarvestAreaContainer)
		{
			MineFactoryReloadedClient.removeTileFromAreaList((IHarvestAreaContainer)this);
		}
	}
	
	@Override
	public ForgeDirection getDirectionFacing()
	{
		return _forwardDirection;
	}
	
	@Override
	public boolean canRotate()
	{
		return false;
	}
	
	@Override
	public void rotate()
	{
		if(!worldObj.isRemote)
		{
			if(_forwardDirection == ForgeDirection.NORTH)
			{
				_forwardDirection = ForgeDirection.EAST;
			}
			else if(_forwardDirection == ForgeDirection.EAST)
			{
				_forwardDirection = ForgeDirection.SOUTH;
			}
			else if(_forwardDirection == ForgeDirection.SOUTH)
			{
				_forwardDirection = ForgeDirection.WEST;
			}
			else if(_forwardDirection == ForgeDirection.WEST)
			{
				_forwardDirection = ForgeDirection.NORTH;
			}
			else
			{
				_forwardDirection = ForgeDirection.NORTH;
			}
			
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 50, worldObj.provider.dimensionId, getDescriptionPacket());
		}
	}
	
	public void rotateDirectlyTo(int rotation)
	{
		_forwardDirection = ForgeDirection.getOrientation(rotation);
		if (worldObj != null)
		{
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	public int getRotatedSide(int side)
	{
		return _textureSelection[_forwardDirection.ordinal()][side];
	}
	
	public ForgeDirection getDropDirection()
	{
		return getDirectionFacing().getOpposite();
	}
	
	public boolean getIsActive()
	{
		return _isActive;
	}
	
	public void setIsActive(boolean isActive)
	{
		if(worldObj != null && !worldObj.isRemote && _isActive != isActive)
		{
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 50, worldObj.provider.dimensionId, getDescriptionPacket());
		}
		_isActive = isActive;
	}
	
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return null;
	}
	
	public ContainerFactoryInventory getContainer(InventoryPlayer inventoryPlayer)
	{
		return null;
	}
	
	public String getGuiBackground()
	{
		return null;
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		Object[] toSend = {xCoord, yCoord, zCoord, _forwardDirection.ordinal(), _isActive};
		return PacketWrapper.createPacket(MineFactoryReloadedCore.modNetworkChannel, Packets.TileDescription, toSend);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		int rotation = nbttagcompound.getInteger("rotation");
		rotateDirectlyTo(rotation);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("rotation", getDirectionFacing().ordinal());
	}
	
	public void onEntityCollidedWithBlock(Entity entity)
	{
		
	}
	
	public void onRedNetChanged(ForgeDirection side, int value)
	{
		_rednetState = value;
	}
	
	public int getRedNetOutput(ForgeDirection side)
	{
		return 0;
	}
}
