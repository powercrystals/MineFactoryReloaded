package powercrystals.minefactoryreloaded.tile;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.position.BlockPosition;
import powercrystals.core.position.INeighboorUpdateTile;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class TileRedstoneCable extends TileEntity implements INeighboorUpdateTile
{
	public enum ConnectionState
	{
		NoConnection,
		ConnectToCable,
		ConnectToMachine,
		ConnectToInterface
	}
	
	private RedstoneNetwork _network;
	private boolean _needsNetworkUpdate;
	
	public void setSideColor(ForgeDirection side, int color)
	{
	}
	
	public int getSideColor(ForgeDirection side)
	{
		return 0;
	}
	
	public ConnectionState getConnectionState(ForgeDirection side)
	{
		BlockPosition bp = new BlockPosition(this);
		bp.orientation = side;
		bp.moveForwards(1);
		
		if(worldObj.isAirBlock(bp.x, bp.y, bp.z))
		{
			return ConnectionState.NoConnection;
		}
		else if(worldObj.getBlockId(bp.x, bp.y, bp.z) == MineFactoryReloadedCore.redstoneCableBlock.blockID)
		{
			return ConnectionState.ConnectToCable;
		}
		else if(worldObj.isBlockSolidOnSide(bp.x, bp.y, bp.z, side.getOpposite()))
		{
			return ConnectionState.ConnectToMachine;
		}
		else
		{
			return ConnectionState.ConnectToInterface;
		}
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
			
			//System.out.println("Checking status of block at " + bp.toString());
			if(!worldObj.isAirBlock(bp.x, bp.y, bp.z))
			{
				if(worldObj.getBlockId(bp.x, bp.y, bp.z) != MineFactoryReloadedCore.redstoneCableBlock.blockID)
				{
					//System.out.println("** is valid node");
					_network.addNode(bp);
				}
				else
				{
					//System.out.println("** is redstone cable");
				}
			}
			else
			{
				//System.out.println("** is not valid node");
				_network.removeNode(bp);
				_network.removePoweringNode(bp);
			}
			
			//getIndirectPowerLevelTo
			//isBlockProvidingPowerTo
			if(worldObj.getBlockId(bp.x, bp.y, bp.z) == Block.redstoneWire.blockID)
			{
				if(worldObj.getBlockMetadata(bp.x, bp.y, bp.z) < getNetwork().getPowerLevelOutput())
				{
					_network.removePoweringNode(bp);
				}
				else
				{
					_network.addPoweringNode(bp);
				}
			}
			else if(worldObj.isBlockProvidingPowerTo(bp.x, bp.y, bp.z, bp.orientation.ordinal()) > 0)
			{
				_network.addPoweringNode(bp);
			}
			else if(worldObj.getIndirectPowerLevelTo(bp.x, bp.y, bp.z, bp.orientation.ordinal()) > 0 && getConnectionState(bp.orientation) == ConnectionState.ConnectToInterface)
			{
				_network.addPoweringNode(bp);
			}
			else
			{
				_network.removePoweringNode(bp);
			}
			
			/*if(worldObj.isBlockProvidingPowerTo(bp.x, bp.y, bp.z, bp.orientation.ordinal()) == 0 ||
					(getConnectionState(bp.orientation) == ConnectionState.ConnectToInterface &&
					worldObj.isBlockProvidingPowerTo(bp.x, bp.y, bp.z, bp.orientation.ordinal()) == 0) ||
					(worldObj.getBlockId(bp.x, bp.y, bp.z) == Block.redstoneWire.blockID &&
					worldObj.getBlockMetadata(bp.x, bp.y, bp.z) < getNetwork().getPowerLevelOutput()))
			{
				//System.out.println("** is not providing power");
				_network.removePoweringNode(bp);
			}
			else
			{
				//System.out.println("** is providing power");
				if(worldObj.getBlockId(bp.x, bp.y, bp.z) != MineFactoryReloadedCore.redstoneCableBlock.blockID)
				{
					_network.addPoweringNode(bp);
				}
			}*/
		}
	}
}
