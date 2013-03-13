package powercrystals.minefactoryreloaded.decorative;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemRecord;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import powercrystals.core.net.PacketWrapper;
import powercrystals.core.util.Util;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryInventory;
import powercrystals.minefactoryreloaded.net.Packets;

public class TileEntityAutoJukebox extends TileEntityFactoryInventory implements ISidedInventory
{
	private boolean _lastRedstoneState;
	private boolean _canCopy;
	private boolean _canPlay;
	
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
			PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 50, worldObj.getWorldInfo().getDimension(),
					PacketWrapper.createPacket(MineFactoryReloadedCore.modId, Packets.AutoJukeboxPlay, new Object[] { xCoord, yCoord, zCoord, _inventory[0].itemID } ));
		}
	}
	
	public void stopRecord()
	{
		worldObj.playAuxSFX(1005, xCoord, yCoord, zCoord, 0);
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
	public String getGuiBackground()
	{
		return "autojukebox.png";
	}

	@Override
	public String getInvName()
	{
		return "Auto Jukebox";
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
