package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemRecord;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.net.PacketWrapper;
import powercrystals.core.util.Util;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.client.GuiAutoJukebox;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerAutoJukebox;
import powercrystals.minefactoryreloaded.net.Packets;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityAutoJukebox extends TileEntityFactoryInventory
{
	public TileEntityAutoJukebox()
	{
		super(Machine.AutoJukebox);
	}

	private boolean _lastRedstoneState;
	private boolean _canCopy;
	private boolean _canPlay;
	
	@Override
	public String getGuiBackground()
	{
		return "autojukebox.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiAutoJukebox(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerAutoJukebox getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerAutoJukebox(this, inventoryPlayer);
	}
	
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
		else if(_inventory[0] != null && _inventory[0].getItem() instanceof ItemRecord && _inventory[1] != null &&
				_inventory[1].itemID == MineFactoryReloadedCore.blankRecordItem.itemID)
		{
			return true;
		}
		return false;
	}
	
	public void setCanPlay(boolean canPlay)
	{
		_canPlay = canPlay;
	}
	
	public boolean getCanPlay()
	{
		if(worldObj.isRemote)
		{
			return _canPlay;
		}
		else if(_inventory[0] != null && _inventory[0].getItem() instanceof ItemRecord)
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
	
	public void playRecord()
	{
		if(_inventory[0] != null && _inventory[0].getItem() instanceof ItemRecord)
		{
			worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1005, xCoord, yCoord, zCoord, _inventory[0].itemID);
			PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 50, worldObj.provider.dimensionId,
					PacketWrapper.createPacket(MineFactoryReloadedCore.modNetworkChannel, Packets.AutoJukeboxPlay, new Object[] { xCoord, yCoord, zCoord, _inventory[0].itemID } ));
		}
		worldObj.notifyBlockChange(xCoord, yCoord, zCoord, worldObj.getBlockId(xCoord, yCoord, zCoord));
	}
	
	public void stopRecord()
	{
		worldObj.playAuxSFX(1005, xCoord, yCoord, zCoord, 0);
		worldObj.notifyBlockChange(xCoord, yCoord, zCoord, worldObj.getBlockId(xCoord, yCoord, zCoord));
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
			stopRecord();
			playRecord();
		}
		
		_lastRedstoneState = redstoneState;
	}
	
	@Override
	public String getInvName()
	{
		return "Auto Jukebox";
	}
	
	@Override
	public int getSizeInventorySide(ForgeDirection side)
	{
		return 1;
	}
}
