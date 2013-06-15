package powercrystals.minefactoryreloaded.tile.rednet;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.bouncycastle.util.Arrays;

import cpw.mods.fml.common.FMLLog;

import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.rednet.IConnectableRedNet;
import powercrystals.minefactoryreloaded.setup.MFRConfig;

public class RedstoneNetwork
{
	private static int _nextId = 0;
	
	private boolean _ignoreUpdates;
	
	private boolean _mustUpdate;
	
	private int _id;
	private boolean _invalid;
	
	private Map<Integer, List<BlockPosition>> _singleNodes = new HashMap<Integer, List<BlockPosition>>();
	private List<BlockPosition> _omniNodes = new LinkedList<BlockPosition>();
	
	private List<BlockPosition> _weakNodes = new LinkedList<BlockPosition>();
	
	private List<BlockPosition> _cables = new LinkedList<BlockPosition>();
	
	private int[] _powerLevelOutput = new int[16];
	private BlockPosition[] _powerProviders = new BlockPosition[16];
	
	private World _world;
	
	public static void log(String format, Object... data)
	{
		if(MFRConfig.redNetDebug.getBoolean(false) && format != null)
		{
			FMLLog.info("RedNet Debug: " + format, data);
		}
	}
	
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
	
	public void tick()
	{
		if(_mustUpdate)
		{
			_mustUpdate = false;
			updatePowerLevels();
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
	
	public boolean isWeakNode(BlockPosition node)
	{
		return _weakNodes.contains(node);
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
			RedstoneNetwork.log("Network with ID %d adding omni node %s", _id, node.toString());
			_omniNodes.add(node);
			notifyOmniNode(node);
		}
		
		for(int subnet = 0; subnet < 16; subnet++)
		{
			int power = getOmniNodePowerLevel(node, subnet);
			if(Math.abs(power) > Math.abs(_powerLevelOutput[subnet]))
			{
				RedstoneNetwork.log("Network with ID %d:%d has omni node %s as new power provider", _id, subnet, node.toString());
				_powerLevelOutput[subnet] = power;
				_powerProviders[subnet] = node;
				notifyNodes(subnet);
			}
			else if(node.equals(_powerProviders[subnet]) && Math.abs(power) < Math.abs(_powerLevelOutput[subnet]))
			{
				updatePowerLevels(subnet);
			}
		}
	}
	
	public void addOrUpdateNode(BlockPosition node, int subnet, boolean allowWeak)
	{
		int blockId = _world.getBlockId(node.x, node.y, node.z);
		if(blockId == MineFactoryReloadedCore.rednetCableBlock.blockID)
		{
			return;
		}
		
		if(!_singleNodes.get(subnet).contains(node))
		{
			removeNode(node);
			RedstoneNetwork.log("Network with ID %d:%d adding node %s", _id, subnet, node.toString());
			
			_singleNodes.get(subnet).add(node);
			notifySingleNode(node, subnet);
		}
		
		if(allowWeak)
		{
			_weakNodes.add(node);
		}
		else
		{
			_weakNodes.remove(node);
		}
		
		int power = getSingleNodePowerLevel(node);
		RedstoneNetwork.log("Network with ID %d:%d calculated power for node %s as %d", _id, subnet, node.toString(), power);
		if(Math.abs(power) > Math.abs(_powerLevelOutput[subnet]))
		{
			RedstoneNetwork.log("Network with ID %d:%d has node %s as new power provider", _id, subnet, node.toString());
			_powerLevelOutput[subnet] = power;
			_powerProviders[subnet] = node;
			notifyNodes(subnet);
		}
		else if(node.equals(_powerProviders[subnet]) && Math.abs(power) < Math.abs(_powerLevelOutput[subnet]))
		{
			RedstoneNetwork.log("Network with ID %d:%d removing power provider node, recalculating", _id, subnet);
			updatePowerLevels(subnet);
		}
	}
	
	public void removeNode(BlockPosition node)
	{
		boolean notify = _omniNodes.contains(node);
		
		_omniNodes.remove(node);
		_weakNodes.remove(node);
		
		for(int subnet = 0; subnet < 16; subnet++)
		{
			if(_singleNodes.get(subnet).contains(node))
			{
				notify = true;
				RedstoneNetwork.log("Network with ID %d:%d removing node %s", _id, subnet, node.toString());
				_singleNodes.get(subnet).remove(node);
			}
			
			if(node.equals(_powerProviders[subnet]))
			{
				RedstoneNetwork.log("Network with ID %d:%d removing power provider node, recalculating", _id, subnet);
				updatePowerLevels(subnet);
			}
		}
		
		if(notify)
		{
			_world.notifyBlockOfNeighborChange(node.x, node.y, node.z, MineFactoryReloadedCore.rednetCableBlock.blockID);
			_world.notifyBlocksOfNeighborChange(node.x, node.y, node.z, MineFactoryReloadedCore.rednetCableBlock.blockID);
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
		
		RedstoneNetwork.log("Network with ID %d merging with network %d", _id, network._id);
		network.setInvalid();
		for(int subnet = 0; subnet < 16; subnet++)
		{
			_singleNodes.get(subnet).addAll(network._singleNodes.get(subnet));
		}
		
		_omniNodes.addAll(network._omniNodes);
		
		_weakNodes.addAll(network._weakNodes);
		
		_mustUpdate = _mustUpdate || network._mustUpdate;
		
		for(BlockPosition cable : network._cables)
		{
			_cables.add(cable);
			TileEntity te = cable.getTileEntity(_world);
			if(te != null && te instanceof TileEntityRedNetCable)
			{
				((TileEntityRedNetCable)te).setNetwork(this);
			}
		}
		
		updatePowerLevels();
	}
	
	public void updatePowerLevels()
	{
		for(int subnet = 0; subnet < 16; subnet++)
		{
			updatePowerLevels(subnet);
		}
	}
	
	public void updatePowerLevels(int subnet)
	{
		int lastPower = _powerLevelOutput[subnet];
		
		_powerLevelOutput[subnet] = 0;
		_powerProviders[subnet] = null;
		
		log("Network with ID %d:%d recalculating power levels for %d single nodes and %d omni nodes", _id, subnet, _singleNodes.get(subnet).size(), _omniNodes.size());
		
		for(BlockPosition node : _singleNodes.get(subnet))
		{
			if(!isNodeLoaded(node))
			{
				continue;
			}
			int power = getSingleNodePowerLevel(node);
			if(Math.abs(power) > Math.abs(_powerLevelOutput[subnet]))
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
			if(Math.abs(power) > Math.abs(_powerLevelOutput[subnet]))
			{
				_powerLevelOutput[subnet] = power;
				_powerProviders[subnet] = node;
			}
			
		}
		
		RedstoneNetwork.log("Network with ID %d:%d recalculated power levels as: output: %d with powering node %s", _id, subnet, _powerLevelOutput[subnet], _powerProviders[subnet]);
		if(_powerLevelOutput[subnet] != lastPower)
		{
			notifyNodes(subnet);
		}
	}
	
	private void notifyNodes(int subnet)
	{
		if(_ignoreUpdates)
		{
			RedstoneNetwork.log("Network asked to notify nodes while ignoring updates (API misuse?)!");
			_mustUpdate = true;
			return;
		}
		_ignoreUpdates = true;
		RedstoneNetwork.log("Network with ID %d:%d notifying %d single nodes and %d omni nodes", _id, subnet, _singleNodes.get(subnet).size(), _omniNodes.size());
		for(int i = 0; i < _singleNodes.get(subnet).size(); i++)
		{
			BlockPosition bp = _singleNodes.get(subnet).get(i);
			RedstoneNetwork.log("Network with ID %d:%d notifying node %s of power state change to %d", _id, subnet, bp.toString(), _powerLevelOutput[subnet]);
			notifySingleNode(bp, subnet);
		}
		for(int i = 0; i < _omniNodes.size(); i++)
		{
			BlockPosition bp = _omniNodes.get(i);
			RedstoneNetwork.log("Network with ID %d:%d notifying omni node %s of power state change to %d", _id, subnet, bp.toString(), _powerLevelOutput[subnet]);
			notifyOmniNode(bp);
		}
		_ignoreUpdates = false;
	}
	
	private boolean isNodeLoaded(BlockPosition node)
	{
		return _world.getChunkProvider().chunkExists(node.x >> 4, node.z >> 4);
	}
	
	private void notifySingleNode(BlockPosition node, int subnet)
	{
		if(isNodeLoaded(node))
		{
			int blockId = _world.getBlockId(node.x, node.y, node.z);
			if(blockId == MineFactoryReloadedCore.rednetCableBlock.blockID)
			{
				return;
			}
			else if(Block.blocksList[blockId] instanceof IConnectableRedNet)
			{
				((IConnectableRedNet)Block.blocksList[blockId]).onInputChanged(_world, node.x, node.y, node.z, node.orientation.getOpposite(), _powerLevelOutput[subnet]);
			}
			else
			{
				_world.notifyBlockOfNeighborChange(node.x, node.y, node.z, MineFactoryReloadedCore.rednetCableBlock.blockID);
				_world.notifyBlocksOfNeighborChange(node.x, node.y, node.z, MineFactoryReloadedCore.rednetCableBlock.blockID);
			}
		}
	}
	
	private void notifyOmniNode(BlockPosition node)
	{
		if(isNodeLoaded(node))
		{
			int blockId = _world.getBlockId(node.x, node.y, node.z);
			if(Block.blocksList[blockId] instanceof IConnectableRedNet)
			{
				((IConnectableRedNet)Block.blocksList[blockId]).onInputsChanged(_world, node.x, node.y, node.z, node.orientation.getOpposite(), Arrays.clone(_powerLevelOutput));
			}
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
			return b.getOutputValue(_world, node.x, node.y, node.z, node.orientation.getOpposite(), subnet);
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
		
		int offset = 0;
		int blockId = _world.getBlockId(node.x, node.y, node.z);
		if(blockId == Block.redstoneWire.blockID)
		{
			offset = -1;
		}
		
		if(_weakNodes.contains(node) || Block.blocksList[blockId] instanceof IConnectableRedNet)
		{
			int weakPower = _world.getIndirectPowerLevelTo(node.x, node.y, node.z, node.orientation.ordinal()) + offset;
			int strongPower = _world.isBlockProvidingPowerTo(node.x, node.y, node.z, node.orientation.ordinal()) + offset;
			return Math.abs(weakPower) > Math.abs(strongPower) ? weakPower : strongPower;
		}
		else
		{
			return _world.isBlockProvidingPowerTo(node.x, node.y, node.z, node.orientation.ordinal()) + offset;
		}
	}
}