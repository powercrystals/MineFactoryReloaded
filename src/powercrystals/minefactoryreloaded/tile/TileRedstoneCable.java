package powercrystals.minefactoryreloaded.tile;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.net.PacketWrapper;
import powercrystals.core.position.BlockPosition;
import powercrystals.core.position.INeighboorUpdateTile;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.rednet.IConnectableRedNet;
import powercrystals.minefactoryreloaded.api.rednet.RedNetConnectionType;
import powercrystals.minefactoryreloaded.net.Packets;

public class TileRedstoneCable extends TileEntity implements INeighboorUpdateTile
{
	private int[] _sideColors = new int [6];
	private RedstoneNetwork _network;
	private boolean _needsNetworkUpdate;
	
	private static final int _maxVanillaBlockId = 158;
	private static List<Integer> _connectionWhitelist = Arrays.asList(23, 25, 27, 28, 29, 33, 46, 55, 64, 69, 70, 71, 72, 75, 76, 77, 93, 94, 96, 107, 123, 124, 131, 
			147, 148, 149, 150, 151, 152, 157, 158);
	private static List<Integer> _connectionBlackList;
	
	public TileRedstoneCable()
	{
		if(_connectionBlackList == null)
		{
			_connectionBlackList = new LinkedList<Integer>();
			for(String s : MineFactoryReloadedCore.redNetConnectionBlacklist.getString().replace("\"", "").split(","))
			{
				try
				{
					int i = Integer.parseInt(s.trim());
					System.out.println("Adding ID " + i + " to rednet blacklist");
					_connectionBlackList.add(i);
				}
				catch(NumberFormatException x)
				{
					System.out.println("Empty or invalid rednet blacklist entry found. Not adding to rednet blacklist.");
				}
			}
		}
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
	
	public RedNetConnectionType getConnectionState(ForgeDirection side)
	{
		BlockPosition bp = new BlockPosition(this);
		bp.orientation = side;
		bp.moveForwards(1);
		
		int blockId = worldObj.getBlockId(bp.x, bp.y, bp.z);
		Block b = Block.blocksList[blockId];
		
		if(b == null || (blockId <= _maxVanillaBlockId && !_connectionWhitelist.contains(blockId)) || _connectionBlackList.contains(blockId) || b.isAirBlock(worldObj, bp.x, bp.y, bp.z))
		{
			return RedNetConnectionType.None;
		}
		else if(blockId == MineFactoryReloadedCore.rednetCableBlock.blockID)
		{
			return RedNetConnectionType.CableAll;
		}
		else if(b instanceof IConnectableRedNet)
		{
			return ((IConnectableRedNet)b).getConnectionType(worldObj, bp.x, bp.y, bp.z, side.getOpposite());
		}
		else if(b.isBlockSolidOnSide(worldObj, bp.x, bp.y, bp.z, side.getOpposite()))
		{
			return RedNetConnectionType.CableSingle;
		}
		else
		{
			return RedNetConnectionType.PlateSingle;
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
		_network.tick();
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
		if(worldObj.isRemote)
		{
			return;
		}
		
		BlockPosition ourbp = new BlockPosition(this);
		//System.out.println("Cable at " + ourbp.toString() + " updating network");
		
		if(_network == null)
		{
			for(BlockPosition bp : ourbp.getAdjacent(true))
			{
				TileEntity te = bp.getTileEntity(worldObj);
				if(te != null && te instanceof TileRedstoneCable)
				{
					TileRedstoneCable cable = ((TileRedstoneCable)te);
					if(cable.getNetwork() != null && !cable.getNetwork().isInvalid())
					{
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
					cable.setNetwork(_network);
				}
				else if(cable.getNetwork() != _network && cable.getNetwork() != null && !cable.getNetwork().isInvalid())
				{
					_network.mergeNetwork(cable.getNetwork());
				}
			}
			else
			{
				int subnet = getSideColor(bp.orientation);
				RedNetConnectionType connectionType = getConnectionState(bp.orientation);
				
				if(!worldObj.isAirBlock(bp.x, bp.y, bp.z))
				{
					if(connectionType == RedNetConnectionType.CableSingle || connectionType == RedNetConnectionType.PlateSingle)
					{
						_network.addOrUpdateNode(bp, subnet);
					}
					else if(connectionType == RedNetConnectionType.CableAll || connectionType == RedNetConnectionType.PlateAll)
					{
						_network.addOrUpdateNode(bp);
					}
					else
					{
						_network.removeNode(bp);
					}
				}
				else
				{
					_network.removeNode(bp);
				}
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
