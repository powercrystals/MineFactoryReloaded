package powercrystals.minefactoryreloaded.tile;

import java.util.LinkedList;
import java.util.List;

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
	
	private List<BlockPosition> _nodes = new LinkedList<BlockPosition>();
	private List<BlockPosition> _poweringNodes = new LinkedList<BlockPosition>();
	private List<BlockPosition> _cables = new LinkedList<BlockPosition>();
	//private int _powerLevelInput = 0;
	private int _powerLevelOutput = 0;
	private World _world;
	
	public RedstoneNetwork(World world)
	{
		_world = world;
		_id = _nextId;
		_nextId++;
	}
	
	public void setInvalid()
	{
		_invalid = true;
	}
	
	public boolean isInvalid()
	{
		return _invalid;
	}
	
	public int getPowerLevelOutput()
	{
		return _powerLevelOutput;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public void addNode(BlockPosition node)
	{
		if(!_nodes.contains(node))
		{
			//System.out.println("Network with ID " + _id + " adding node " + node.toString());
			_nodes.add(node);
			notifyNode(node);
			//System.out.println("Network with ID " + _id + " now has " + _nodes.size() + " nodes");
		}
	}
	
	public void removeNode(BlockPosition node)
	{
		if(_nodes.contains(node))
		{
			//System.out.println("Network with ID " + _id + " removing node " + node.toString());
			_nodes.remove(node);
			//System.out.println("Network with ID " + _id + " now has " + _nodes.size() + " nodes");
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
	
	public void mergeNetwork(RedstoneNetwork network)
	{
		if(_invalid)
		{
			return;
		}
		//System.out.println("Network with ID " + _id + " merging with network " + network._id);
		network.setInvalid();
		for(BlockPosition node : network._nodes)
		{
			//System.out.println("** adding node " + node.toString());
			_nodes.add(node);
		}
		for(BlockPosition poweringNode : network._poweringNodes)
		{
			//System.out.println("** adding powering node " + poweringNode.toString());
			_poweringNodes.add(poweringNode);
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
		notifyNodes();
	}
	
	public void addPoweringNode(BlockPosition node)
	{
		int lastPowerLevel = _powerLevelOutput;
		if(!_poweringNodes.contains(node))
		{
			//System.out.println("Network with ID " + _id + " adding powering node " + node.toString());
			_poweringNodes.add(node);
			//System.out.println("Network with ID " + _id + " now has " + _poweringNodes.size() + " powering nodes");
		}
		updatePowerLevels();
		if(_powerLevelOutput != lastPowerLevel)
		{
			notifyNodes();
		}
	}
	
	public void removePoweringNode(BlockPosition node)
	{
		if(_poweringNodes.contains(node))
		{
			//System.out.println("Network with ID " + _id + " removing powering node " + node.toString());
			int lastPowerLevel = _powerLevelOutput;
			_poweringNodes.remove(node);
			updatePowerLevels();
			//System.out.println("Network with ID " + _id + " now has " + _poweringNodes.size() + " powering nodes");
			if(_powerLevelOutput != lastPowerLevel)
			{
				notifyNodes();
			}
		}
	}
	
	private void updatePowerLevels()
	{
		//_powerLevelInput = 0;
		_powerLevelOutput = 0;
		
		for(BlockPosition node : _poweringNodes)
		{
		//	_powerLevelInput = Math.max(_powerLevelInput, _world.isBlockProvidingPowerTo(node.x, node.y, node.z, node.orientation.ordinal()));
			_powerLevelOutput = Math.max(_powerLevelOutput, _world.isBlockProvidingPowerTo(node.x, node.y, node.z, node.orientation.ordinal()) - 1);
		}
		//System.out.println("Network with ID " + _id + " recalculated power levels as: output: " + _powerLevelOutput);
	}
	
	private void notifyNodes()
	{
		if(_ignoreUpdates)
		{
			return;
		}
		_ignoreUpdates = true;
		for(int i = 0; i < _nodes.size(); i++)
		{
			BlockPosition bp = _nodes.get(i);
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
