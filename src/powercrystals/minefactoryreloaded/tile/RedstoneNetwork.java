package powercrystals.minefactoryreloaded.tile;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class RedstoneNetwork
{
	private static int _nextId = 0;
	private static boolean _ignoreUpdates;
	
	private int _id;
	private boolean _invalid;
	
	private Map<Integer, List<BlockPosition>> _nodes = new HashMap<Integer, List<BlockPosition>>();
	private Map<Integer, List<BlockPosition>> _poweringNodes = new HashMap<Integer, List<BlockPosition>>();
	private List<BlockPosition> _cables = new LinkedList<BlockPosition>();
	
	private int[] _powerLevelOutput = new int[16];
	private World _world;
	
	public RedstoneNetwork(World world)
	{
		_world = world;
		_id = _nextId;
		_nextId++;
		
		for(int i = 0; i < 16; i++)
		{
			_nodes.put(i, new LinkedList<BlockPosition>());
			_poweringNodes.put(i, new LinkedList<BlockPosition>());
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
	
	public void addNode(BlockPosition node, int subnet)
	{
		if(!_nodes.get(subnet).contains(node))
		{
			//System.out.println("Network with ID " + _id + ":" + subnet + " adding node " + node.toString());
			for(int i = 0; i < 16; i++)
			{
				if(i != subnet)
				{
					removeNode(node, i);
				}
			}
			
			_nodes.get(subnet).add(node);
			notifyNode(node);
			//System.out.println("Network with ID " + _id + ":" + subnet + " now has " + _nodes.size() + " nodes");
		}
	}
	
	public void removeNode(BlockPosition node, int subnet)
	{
		if(_nodes.get(subnet).contains(node))
		{
			//System.out.println("Network with ID " + _id + ":" + subnet + " removing node " + node.toString());
			removePoweringNode(node, subnet);
			_nodes.get(subnet).remove(node);
			//System.out.println("Network with ID " + _id + ":" + subnet + " now has " + _nodes.size() + " nodes");
		}
	}
	
	public void addCable(BlockPosition cable)
	{
		if(!_cables.contains(cable))
		{
			//System.out.println("Network with ID " + _id + " adding cable " + cable.toString());
			_cables.add(cable);
			//System.out.println("Network with ID " + _id + " now has " + _cables.size() + " cables");
		}
	}
	
	public void addPoweringNode(BlockPosition node, int subnet)
	{
		if(!_poweringNodes.get(subnet).contains(node))
		{
			for(int i = 0; i < 16; i++)
			{
				if(i != subnet)
				{
					removePoweringNode(node, i);
				}
			}
			
			//System.out.println("Network with ID " + _id + ":" + subnet + " adding powering node " + node.toString());
			_poweringNodes.get(subnet).add(node);
			updatePowerLevels();
			//System.out.println("Network with ID " + _id + ":" + subnet + " now has " + _poweringNodes.size() + " powering nodes");
		}
	}
	
	public void removePoweringNode(BlockPosition node, int subnet)
	{
		if(_poweringNodes.get(subnet).contains(node))
		{
			//System.out.println("Network with ID " + _id + ":" + subnet + " removing powering node " + node.toString());
			_poweringNodes.get(subnet).remove(node);
			updatePowerLevels();
			//System.out.println("Network with ID " + _id + ":" + subnet + " now has " + _poweringNodes.size() + " powering nodes");
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
			for(BlockPosition node : network._nodes.get(subnet))
			{
				//System.out.println("** adding node " + node.toString());
				_nodes.get(subnet).add(node);
			}
			for(BlockPosition poweringNode : network._poweringNodes.get(subnet))
			{
				//System.out.println("** adding powering node " + poweringNode.toString());
				_poweringNodes.get(subnet).add(poweringNode);
			}
		}
		for(BlockPosition cable : network._cables)
		{
			//System.out.println("** adding cable " + cable.toString());
			_cables.add(cable);
			TileEntity te = cable.getTileEntity(_world);
			if(te != null && te instanceof TileRedstoneCable)
			{
				((TileRedstoneCable)te).setNetwork(this);
			}
		}
		updatePowerLevels();
		for(int subnet = 0; subnet < 16; subnet++)
		{
			notifyNodes(subnet);
		}
	}
	
	private void updatePowerLevels()
	{
		for(int subnet = 0; subnet < 16; subnet++)
		{
			int lastPowerLevel = _powerLevelOutput[subnet];
			_powerLevelOutput[subnet] = 0;
			
			for(BlockPosition node : _poweringNodes.get(subnet))
			{
				_powerLevelOutput[subnet] = Math.max(_powerLevelOutput[subnet], _world.isBlockProvidingPowerTo(node.x, node.y, node.z, node.orientation.ordinal()) - 1);
				_powerLevelOutput[subnet] = Math.max(_powerLevelOutput[subnet], _world.getIndirectPowerLevelTo(node.x, node.y, node.z, node.orientation.ordinal()) - 1);
			}
			
			if(_powerLevelOutput[subnet] != lastPowerLevel)
			{
				//System.out.println("Network with ID " + _id + ":" + subnet + " recalculated power levels as: output: " + _powerLevelOutput[subnet]);
				notifyNodes(subnet);
			}
		}
	}
	
	private void notifyNodes(int subnet)
	{
		if(_ignoreUpdates)
		{
			return;
		}
		_ignoreUpdates = true;
		for(int i = 0; i < _nodes.get(subnet).size(); i++)
		{
			BlockPosition bp = _nodes.get(subnet).get(i);
			if(_world.getBlockId(bp.x, bp.y, bp.z) != MineFactoryReloadedCore.redstoneCableBlock.blockID)
			{
				//System.out.println("Network with ID " + _id + " notifying node " + bp.toString() + " of power state change to " + getPowerLevelOutput());
				notifyNode(bp);
				//System.out.println("** Block ID is now " + _world.getBlockId(bp.x, bp.y, bp.z));
			}
		}
		_ignoreUpdates = false;
	}
	
	private void notifyNode(BlockPosition node)
	{
		if(_world.getChunkProvider().chunkExists(node.x >> 4, node.z >> 4))
		{
			_world.notifyBlockOfNeighborChange(node.x, node.y, node.z, MineFactoryReloadedCore.redstoneCableBlock.blockID);
		}
	}
}
