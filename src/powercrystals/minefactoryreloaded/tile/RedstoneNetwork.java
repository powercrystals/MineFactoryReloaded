package powercrystals.minefactoryreloaded.tile;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bouncycastle.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.rednet.IConnectableRedNet;

public class RedstoneNetwork
{
	private static int _nextId = 0;
	private static boolean _ignoreUpdates;
	
	private int _id;
	private boolean _invalid;
	
	private Map<Integer, List<BlockPosition>> _singleNodes = new HashMap<Integer, List<BlockPosition>>();
	private List<BlockPosition> _omniNodes = new LinkedList<BlockPosition>();
	
	private List<BlockPosition> _cables = new LinkedList<BlockPosition>();
	
	private int[] _powerLevelOutput = new int[16];
	private BlockPosition[] _powerProviders = new BlockPosition[16];
	
	private World _world;
	
	public RedstoneNetwork(World world)
	{
		_world = world;
		_id = _nextId;
		_nextId++;
		
		for(int i = 0; i < 16; i++)
		{
			_singleNodes.put(i, new LinkedList<BlockPosition>());
		}
	}
	
	public void setInvalid()
	{
		_invalid = true;
	}
	
	public boolean isInvalid()
	{
		return _invalid;
	}
	
	public int getPowerLevelOutput(int subnet)
	{
		return _powerLevelOutput[subnet];
	}
	
	public int getId()
	{
		return _id;
	}
	
	public void addOrUpdateNode(BlockPosition node)
	{
		int blockId = _world.getBlockId(node.x, node.y, node.z);
		if(blockId == MineFactoryReloadedCore.rednetCableBlock.blockID)
		{
			return;
		}
		
		if(!_omniNodes.contains(node))
		{
			//System.out.println("Network with ID " + _id + " adding omni node " + node.toString());
			_omniNodes.add(node);
			notifyOmniNode(node);
		}
		
		for(int subnet = 0; subnet < 16; subnet++)
		{
			int power = getOmniNodePowerLevel(node, subnet);
			if(power > _powerLevelOutput[subnet])
			{
				//System.out.println("Network with ID " + _id + ":" + subnet + " has omni node " + node.toString() + " as new power provider");
				_powerLevelOutput[subnet] = power;
				_powerProviders[subnet] = node;
				notifyNodes(subnet);
			}
			else if(node.equals(_powerProviders[subnet]) && power < _powerLevelOutput[subnet])
			{
				updatePowerLevels(subnet);
			}
		}
	}
	
	public void addOrUpdateNode(BlockPosition node, int subnet)
	{
		int blockId = _world.getBlockId(node.x, node.y, node.z);
		if(blockId == MineFactoryReloadedCore.rednetCableBlock.blockID)
		{
			return;
		}
		
		if(!_singleNodes.get(subnet).contains(node))
		{
			removeNode(node);
			//System.out.println("Network with ID " + _id + ":" + subnet + " adding node " + node.toString());
			
			_singleNodes.get(subnet).add(node);
			notifySingleNode(node);
		}
		
		int power = getSingleNodePowerLevel(node);
		if(power > _powerLevelOutput[subnet])
		{
			//System.out.println("Network with ID " + _id + ":" + subnet + " has node " + node.toString() + " as new power provider");
			_powerLevelOutput[subnet] = power;
			_powerProviders[subnet] = node;
			notifyNodes(subnet);
		}
		else if(node.equals(_powerProviders[subnet]) && power < _powerLevelOutput[subnet])
		{
			//System.out.println("Network with ID " + _id + ":" + subnet + " removing power provider node, recalculating");
			updatePowerLevels(subnet);
		}
	}
	
	public void removeNode(BlockPosition node)
	{		
		_omniNodes.remove(node);
		
		for(int subnet = 0; subnet < 16; subnet++)
		{
			if(_singleNodes.get(subnet).contains(node))
			{
				//System.out.println("Network with ID " + _id + ":" + subnet + " removing node " + node.toString());
				_singleNodes.get(subnet).remove(node);
			}
			
			if(_powerProviders[subnet] == node)
			{
				//System.out.println("Network with ID " + _id + ":" + subnet + " removing power provider node, recalculating");
				updatePowerLevels(subnet);
			}
		}
	}
	
	public void addCable(BlockPosition cable)
	{
		if(!_cables.contains(cable))
		{
			_cables.add(cable);
		}
	}
	
	public void mergeNetwork(RedstoneNetwork network)
	{
		if(_invalid)
		{
			return;
		}
		
		//System.out.println("Network with ID " + _id + " merging with network " + network._id);
		network.setInvalid();
		for(int subnet = 0; subnet < 16; subnet++)
		{
			_singleNodes.get(subnet).addAll(network._singleNodes.get(subnet));
		}
		
		_omniNodes.addAll(network._omniNodes);
		
		for(BlockPosition cable : network._cables)
		{
			_cables.add(cable);
			TileEntity te = cable.getTileEntity(_world);
			if(te != null && te instanceof TileRedstoneCable)
			{
				((TileRedstoneCable)te).setNetwork(this);
			}
		}
		
		updatePowerLevels();
	}
	
	private void updatePowerLevels()
	{
		for(int subnet = 0; subnet < 16; subnet++)
		{
			updatePowerLevels(subnet);
		}
	}
	
	private void updatePowerLevels(int subnet)
	{
		_powerLevelOutput[subnet] = 0;
		_powerProviders[subnet] = null;
		
		for(BlockPosition node : _singleNodes.get(subnet))
		{
			if(!isNodeLoaded(node))
			{
				continue;
			}
			int power = getSingleNodePowerLevel(node);
			if(power > _powerLevelOutput[subnet])
			{
				_powerLevelOutput[subnet] = power;
				_powerProviders[subnet] = node;
			}
		}
		
		for(BlockPosition node : _omniNodes)
		{
			if(!isNodeLoaded(node))
			{
				continue;
			}
			int power = getOmniNodePowerLevel(node, subnet);
			if(power > _powerLevelOutput[subnet])
			{
				_powerLevelOutput[subnet] = power;
				_powerProviders[subnet] = node;
			}
					
		}
		
		//System.out.println("Network with ID " + _id + ":" + subnet + " recalculated power levels as: output: " + _powerLevelOutput[subnet] + " with powering node " + _powerProviders[subnet]);
		notifyNodes(subnet);
	}
	
	private void notifyNodes(int subnet)
	{
		if(_ignoreUpdates)
		{
			//System.out.println("**** NETWORK INGORING UPDATES");
			return;
		}
		_ignoreUpdates = true;
		//System.out.println("Network with ID " + _id + ":" + subnet + " notifying " + _singleNodes.get(subnet).size() + " single nodes and " + _omniNodes.size() + " omni nodes");
		for(int i = 0; i < _singleNodes.get(subnet).size(); i++)
		{
			BlockPosition bp = _singleNodes.get(subnet).get(i);
			//System.out.println("Network with ID " + _id + ":" + subnet + " notifying node " + bp.toString() + " of power state change to " + _powerLevelOutput[subnet]);
			notifySingleNode(bp);
		}
		for(int i = 0; i < _omniNodes.size(); i++)
		{
			BlockPosition bp = _omniNodes.get(i);
			//System.out.println("Network with ID " + _id + ":" + subnet + " notifying omni node " + bp.toString() + " of power state change to " + _powerLevelOutput[subnet]);
			notifyOmniNode(bp);
		}
		_ignoreUpdates = false;
	}
	
	private boolean isNodeLoaded(BlockPosition node)
	{
		return _world.getChunkProvider().chunkExists(node.x >> 4, node.z >> 4);
	}
	
	private void notifySingleNode(BlockPosition node)
	{
		if(isNodeLoaded(node))
		{
			int blockId = _world.getBlockId(node.x, node.y, node.z);
			if(blockId == MineFactoryReloadedCore.rednetCableBlock.blockID)
			{
				return;
			}
			else
			{
				_world.notifyBlockOfNeighborChange(node.x, node.y, node.z, MineFactoryReloadedCore.rednetCableBlock.blockID);
			}
		}
	}
	
	private void notifyOmniNode(BlockPosition node)
	{
		if(isNodeLoaded(node))
		{
			int blockId = _world.getBlockId(node.x, node.y, node.z);
			((IConnectableRedNet)Block.blocksList[blockId]).onInputChanged(_world, node.x, node.y, node.z, node.orientation.getOpposite(), Arrays.clone(_powerLevelOutput));
		}
	}
	
	private int getOmniNodePowerLevel(BlockPosition node, int subnet)
	{
		if(!isNodeLoaded(node))
		{
			return 0;
		}
		IConnectableRedNet b = ((IConnectableRedNet)Block.blocksList[_world.getBlockId(node.x, node.y, node.z)]);
		if(b != null)
		{
			return Math.max(0, b.getOutputValue(_world, node.x, node.y, node.z, node.orientation.getOpposite(), subnet) - 1);
		}
		else
		{
			return 0;
		}
	}
	
	private int getSingleNodePowerLevel(BlockPosition node)
	{
		if(!isNodeLoaded(node))
		{
			return 0;
		}
		if(_world.isBlockSolidOnSide(node.x, node.y, node.z, node.orientation.getOpposite()))
		{
			return Math.max(0, _world.isBlockProvidingPowerTo(node.x, node.y, node.z, node.orientation.ordinal()) - 1);
		}
		else
		{
			return Math.max(0, Math.max(_world.isBlockProvidingPowerTo(node.x, node.y, node.z, node.orientation.ordinal()) - 1,
					_world.getIndirectPowerLevelTo(node.x, node.y, node.z, node.orientation.ordinal()) - 1));
		}
	}
}