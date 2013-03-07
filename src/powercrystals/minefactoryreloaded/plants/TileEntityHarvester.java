package powercrystals.minefactoryreloaded.plants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.google.common.collect.ImmutableMap;

import powercrystals.core.position.Area;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;

public class TileEntityHarvester extends TileEntityFactoryPowered implements ITankContainer, ISidedInventory
{
	private static Map<Integer, IFactoryHarvestable> harvestables = new HashMap<Integer, IFactoryHarvestable>();

	private HashMap<String, Boolean> _settings;
	
	private Random _rand;
	
	private HarvestAreaManager _areaManager;
	private TreeHarvestManager _treeManager;
	private BlockPosition _lastTree;
	
	private LiquidTank _tank;
	
	public static void registerHarvestable(IFactoryHarvestable harvestable)
	{
		harvestables.put(harvestable.getSourceId(), harvestable);
	}
	
	public TileEntityHarvester()
	{
		super(240);
		_areaManager = new HarvestAreaManager(this, 1, 0, 0);
		_tank = new LiquidTank(4 * LiquidContainerRegistry.BUCKET_VOLUME);
		_settings = new HashMap<String, Boolean>();
		
		_settings.put("silkTouch", false);
		_settings.put("harvestSmallMushrooms", false);
		_settings.put("harvestJungleWood", false);
		
		_rand = new Random();
	}
	
	@Override
	public String getGuiBackground()
	{
		return "harvester.png";
	}
	
	public HashMap<String, Boolean> getSettings()
	{
		return _settings;
	}
	
	@Override
	protected boolean shouldPumpLiquid()
	{
		return true;
	}
	
	@Override
	public ILiquidTank getTank()
	{
		return _tank;
	}

	@Override
	public int getEnergyStoredMax()
	{
		return 16000;
	}

	@Override
	public int getWorkMax()
	{
		return 1;
	}

	@Override
	public int getIdleTicksMax()
	{
		return 5;
	}
	
	@Override
	protected void onFactoryInventoryChanged()
	{
		_areaManager.updateUpgradeLevel(_inventory[0]);
	}

	@Override
	public boolean activateMachine()
	{
		MFRUtil.pumpLiquid(_tank, this);
		
		int harvestedBlockId = 0;
		int harvestedBlockMetadata = 0;

		BlockPosition targetCoords = getNextHarvest();
		
		if(targetCoords == null)
		{
			setIdleTicks(getIdleTicksMax());
			return false;
		}
		
		harvestedBlockId = worldObj.getBlockId(targetCoords.x, targetCoords.y, targetCoords.z);
		harvestedBlockMetadata = worldObj.getBlockMetadata(targetCoords.x, targetCoords.y, targetCoords.z);
		
		IFactoryHarvestable harvestable = harvestables.get(new Integer(harvestedBlockId));
		
		List<ItemStack> drops = harvestable.getDrops(worldObj, _rand, ImmutableMap.copyOf(_settings), targetCoords.x, targetCoords.y, targetCoords.z);

		harvestable.preHarvest(worldObj, targetCoords.x, targetCoords.y, targetCoords.z);

		if(drops != null)
		{
			for(ItemStack dropStack : drops)
			{
				MFRUtil.dropStack(this, dropStack);
			}
		}
		
		if(harvestable.breakBlock())
		{
			if(MineFactoryReloadedCore.playSounds.getBoolean(true))
			{
				worldObj.playAuxSFXAtEntity(null, 2001, targetCoords.x, targetCoords.y, targetCoords.z, harvestedBlockId + (harvestedBlockMetadata << 12));
			}
			worldObj.setBlockWithNotify(targetCoords.x, targetCoords.y, targetCoords.z, 0);
		}
		
		harvestable.postHarvest(worldObj, targetCoords.x, targetCoords.y, targetCoords.z);
		
		_tank.fill(new LiquidStack(MineFactoryReloadedCore.sludgeItem, 10), true);

		return true;
	}

	private BlockPosition getNextHarvest()
	{
		BlockPosition bp = _areaManager.getNextBlock();
		
		int searchId = worldObj.getBlockId(bp.x, bp.y, bp.z);
		
		if(!harvestables.containsKey(new Integer(searchId)))
		{
			return null;
		}
		
		IFactoryHarvestable harvestable = harvestables.get(new Integer(searchId));
		if(harvestable.canBeHarvested(worldObj, _settings, bp.x, bp.y, bp.z))
		{
			if(harvestable.getHarvestType() == HarvestType.Normal)
			{
				return null;
			}
			else if(harvestable.getHarvestType() == HarvestType.LeaveBottom)
			{
				return getNextVertical(bp.x, bp.y, bp.z);
			}
			else if(harvestable.getHarvestType() == HarvestType.Tree)
			{
				BlockPosition temp = getNextTreeSegment(bp.x, bp.y, bp.z);
				if(temp != null)
				{
					_areaManager.rewindBlock();
				}
				return temp;
			}
		}
		return null;
	}
	
	private BlockPosition getNextVertical(int x, int y, int z)
	{
		int highestBlockOffset = -1;
		
		for(int currentYoffset = 1; currentYoffset < MineFactoryReloadedCore.verticalHarvestSearchMaxVertical.getInt(); currentYoffset++)
		{
			int blockId = worldObj.getBlockId(x, y + currentYoffset, z);
			if(harvestables.containsKey(new Integer(blockId)) && harvestables.get(new Integer(blockId)).canBeHarvested(worldObj, _settings, x, y + currentYoffset, z))
			{
				highestBlockOffset = currentYoffset;
			}
			else
			{
				break;
			}
		}
		
		if(highestBlockOffset < 0)
		{
			return null;
		}
		
		return new BlockPosition(x, y + highestBlockOffset, z);
	}

	private BlockPosition getNextTreeSegment(int x, int y, int z)
	{
		int blockId;
 
		if(_lastTree == null || _lastTree.x != x || _lastTree.y != y || _lastTree.z != z)
		{
			Area a = new Area(x - MineFactoryReloadedCore.treeSearchMaxHorizontal.getInt(), x + MineFactoryReloadedCore.treeSearchMaxHorizontal.getInt(),
					y, y + MineFactoryReloadedCore.treeSearchMaxVertical.getInt(),
					z - MineFactoryReloadedCore.treeSearchMaxHorizontal.getInt(), z + MineFactoryReloadedCore.treeSearchMaxHorizontal.getInt());
			
			_treeManager = new TreeHarvestManager(a);
			_lastTree = new BlockPosition(x, y, z);
		}
		
		while(true)
		{
			if(_treeManager.getIsDone())
			{
				return null;
			}
			
			BlockPosition bp = _treeManager.getNextBlock();
			blockId = worldObj.getBlockId(bp.x, bp.y, bp.z);
			
			if(harvestables.containsKey(new Integer(blockId)) && harvestables.get(new Integer(blockId)).canBeHarvested(worldObj, _settings, bp.x, bp.y, bp.z))
			{
				if(_treeManager.getIsLeafPass() && harvestables.get(new Integer(blockId)).getHarvestType() == HarvestType.TreeLeaf)
				{
					return bp;
				}
				else if(!_treeManager.getIsLeafPass() && harvestables.get(new Integer(blockId)).getHarvestType() == HarvestType.Tree)
				{
					return bp;
				}
				else if(!_treeManager.getIsLeafPass() && harvestables.get(new Integer(blockId)).getHarvestType() == HarvestType.TreeLeaf)
				{
					_treeManager.reset();
					continue;
				}
			}
			_treeManager.moveNext();
		}
	}

	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
	{
		return 0;
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill)
	{
		return 0;
	}

	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		return null;
	}

	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain)
	{
		return null;
	}

	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction)
	{
		return new ILiquidTank[] { _tank };
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type)
	{
		return _tank;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		NBTTagCompound list = new NBTTagCompound();
		for(Entry<String, Boolean> setting : _settings.entrySet())
		{
			list.setByte(setting.getKey(), (byte)(setting.getValue() ? 1 : 0));
		}
		nbttagcompound.setTag("harvesterSettings", list);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagCompound list = (NBTTagCompound)nbttagcompound.getTag("harvesterSettings");
		if(list != null)
		{
			for(String s : _settings.keySet())
			{
				byte b = list.getByte(s); 
				if(b == 1)
				{
					_settings.put(s, true);
				}
			}
		}
		_areaManager.updateUpgradeLevel(_inventory[0]);
	}
	
	@Override
	public String getInvName()
	{
		return "Harvester";
	}
	
	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public boolean manageSolids()
	{
		return true;
	}

	@Override
	public int getStartInventorySide(ForgeDirection side)
	{
		return 0;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side)
	{
		return 0;
	}
}
