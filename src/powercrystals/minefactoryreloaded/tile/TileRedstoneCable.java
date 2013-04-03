package powercrystals.minefactoryreloaded.tile;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.net.PacketWrapper;
import powercrystals.core.position.BlockPosition;
import powercrystals.core.position.INeighboorUpdateTile;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.net.Packets;

public class TileRedstoneCable extends TileEntity implements INeighboorUpdateTile
{
	private int[] _sideColors = new int [6];
	private RedstoneNetwork _network;
	private boolean _needsNetworkUpdate;
	
	public enum ConnectionState
	{
		None,
		CableAll,
		CableSingle,
		FlatSingle
	}
	
	public void setSideColor(ForgeDirection side, int color)
	{
		if(side == ForgeDirection.UNKNOWN)
		{
			return;
		}
		_sideColors[side.ordinal()] = color;
		updateNetwork();
	}
	
	public int getSideColor(ForgeDirection side)
	{
		if(side == ForgeDirection.UNKNOWN)
		{
			return 0;
		}
		return _sideColors[side.ordinal()];
	}
	
	public ConnectionState getConnectionState(ForgeDirection side)
	{
		BlockPosition bp = new BlockPosition(this);
		bp.orientation = side;
		bp.moveForwards(1);
		
		if(worldObj.isAirBlock(bp.x, bp.y, bp.z))
		{
			return ConnectionState.None;
		}
		else if(worldObj.getBlockId(bp.x, bp.y, bp.z) == MineFactoryReloadedCore.redstoneCableBlock.blockID)
		{
			return ConnectionState.CableAll;
		}
		else if(worldObj.isBlockSolidOnSide(bp.x, bp.y, bp.z, side.getOpposite()))
		{
			return ConnectionState.CableSingle;
		}
		else
		{
			return ConnectionState.FlatSingle;
		}
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		return PacketWrapper.createPacket(MineFactoryReloadedCore.modNetworkChannel, Packets.CableDescription, new Object[]
				{
					xCoord, yCoord, zCoord, _sideColors[0], _sideColors[1], _sideColors[2], _sideColors[3], _sideColors[4], _sideColors[5]
				});
	}
	
	@Override
	public void validate()
	{
		super.validate();
		_needsNetworkUpdate = true;
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(worldObj.isRemote)
		{
			return;
		}
		if(_network != null && _network.isInvalid())
		{
			_network = null;
			_needsNetworkUpdate = true;
		}
		if(_needsNetworkUpdate)
		{
			updateNetwork();
			_needsNetworkUpdate = false;
		}
	}
	
	@Override
	public void onNeighboorChanged()
	{
		updateNetwork();
	}
	
	public RedstoneNetwork getNetwork()
	{
		return _network;
	}
	
	public void setNetwork(RedstoneNetwork network)
	{
		_network = network;
		_network.addCable(new BlockPosition(this));
	}
	
	private void updateNetwork()
	{
		BlockPosition ourbp = new BlockPosition(this);
		//System.out.println("Updating network for cable at " + ourbp.toString());
		
		if(_network == null)
		{
			for(BlockPosition bp : ourbp.getAdjacent(true))
			{
				//System.out.println("Checking for networks at " + bp.toString());
				TileEntity te = bp.getTileEntity(worldObj);
				if(te != null && te instanceof TileRedstoneCable)
				{
					TileRedstoneCable cable = ((TileRedstoneCable)te);
					if(cable.getNetwork() != null && !cable.getNetwork().isInvalid())
					{
						//System.out.println("Found existing network " + cable.getNetwork().getId() + " at " + bp.toString());
						_network = cable.getNetwork();
						break;
					}
				}
			}
		}
		if(_network == null)
		{
			//System.out.println("Initializing new network at" + ourbp.toString());
			setNetwork(new RedstoneNetwork(worldObj));
		}
		for(BlockPosition bp : ourbp.getAdjacent(true))
		{
			TileEntity te = bp.getTileEntity(worldObj);
			if(te != null && te instanceof TileRedstoneCable)
			{
				TileRedstoneCable cable = ((TileRedstoneCable)te);
				if(cable.getNetwork() == null)
				{
					//System.out.println("Found cable with no network at " + bp.toString());
					cable.setNetwork(_network);
				}
				else if(cable.getNetwork() != _network && cable.getNetwork() != null && !cable.getNetwork().isInvalid())
				{
					//System.out.println("Found cable with existing network " + cable.getNetwork().getId() + " at " + bp.toString());
					_network.mergeNetwork(cable.getNetwork());
				}
			}
			
			int subnet = getSideColor(bp.orientation);
			
			//System.out.println("Checking status of block at " + bp.toString());
			if(!worldObj.isAirBlock(bp.x, bp.y, bp.z))
			{
				if(worldObj.getBlockId(bp.x, bp.y, bp.z) != MineFactoryReloadedCore.redstoneCableBlock.blockID)
				{
					//System.out.println("** is valid node");
					_network.addNode(bp, subnet);
				}
				else
				{
					//System.out.println("** is redstone cable");
				}
			}
			else
			{
				//System.out.println("** is not valid node");
				_network.removeNode(bp, subnet);
			}
			
			if(te != null && te instanceof TileRedstoneCable)
			{
				continue;
			}
			else if(worldObj.getBlockId(bp.x, bp.y, bp.z) == Block.redstoneWire.blockID)
			{
				if(worldObj.getBlockMetadata(bp.x, bp.y, bp.z) < getNetwork().getPowerLevelOutput(subnet))
				{
					_network.removePoweringNode(bp, subnet);
				}
				else
				{
					_network.addPoweringNode(bp, subnet);
				}
			}
			else if(worldObj.isBlockProvidingPowerTo(bp.x, bp.y, bp.z, bp.orientation.ordinal()) > 0)
			{
				_network.addPoweringNode(bp, subnet);
			}
			else if(worldObj.getIndirectPowerLevelTo(bp.x, bp.y, bp.z, bp.orientation.ordinal()) > 0 && getConnectionState(bp.orientation) == ConnectionState.FlatSingle)
			{
				_network.addPoweringNode(bp, subnet);
			}
			else
			{
				_network.removePoweringNode(bp, subnet);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setIntArray("sideSubnets", _sideColors);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		_sideColors = nbttagcompound.getIntArray("sideSubnets");
		if(_sideColors.length == 0)
		{
			_sideColors = new int[6];
		}
	}
}
