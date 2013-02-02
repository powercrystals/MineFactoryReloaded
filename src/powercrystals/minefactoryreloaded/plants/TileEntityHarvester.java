package powercrystals.minefactoryreloaded.plants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.google.common.collect.ImmutableMap;

import buildcraft.core.IMachine;

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
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;

public class TileEntityHarvester extends TileEntityFactoryPowered implements IMachine
{
	private static Map<Integer, IFactoryHarvestable> harvestables = new HashMap<Integer, IFactoryHarvestable>();

	private HashMap<String, Boolean> _settings;
	
	private Random _rand;
	
	private HarvestAreaManager _areaManager;
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
		return 200;
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
		
		setIdleTicks(5);
		return true;
	}

	private BlockPosition getNextHarvest()
	{
		Area harvestArea = _areaManager.getHarvestArea();
		for(BlockPosition bp : harvestArea.getPositionsBottomFirst())
		{
			int searchId = worldObj.getBlockId(bp.x, bp.y, bp.z);
			
			if(!harvestables.containsKey(new Integer(searchId)))
			{
				continue;
			}
			
			IFactoryHarvestable harvestable = harvestables.get(new Integer(searchId));
			if(harvestable.canBeHarvested(worldObj, _settings, bp.x, bp.y, bp.z))
			{
				if(harvestable.getHarvestType() == HarvestType.Normal)
				{
					return bp;
				}
				else if(harvestable.getHarvestType() == HarvestType.LeaveBottom)
				{
					BlockPosition temp = getNextVertical(bp.x, bp.y, bp.z);
					if(temp == null)
					{
						continue;
					}
					return temp;
				}
				else if(harvestable.getHarvestType() == HarvestType.Tree)
				{
					return getNextTreeSegment(bp.x, bp.y, bp.z);
				}
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
 
		Area a = new Area(x - MineFactoryReloadedCore.treeSearchMaxHorizontal.getInt(), x + MineFactoryReloadedCore.treeSearchMaxHorizontal.getInt(),
				y, y + MineFactoryReloadedCore.treeSearchMaxVertical.getInt(),
				z - MineFactoryReloadedCore.treeSearchMaxHorizontal.getInt(), z + MineFactoryReloadedCore.treeSearchMaxHorizontal.getInt());
		
		for(BlockPosition bp : a.getPositionsBottomFirst())
		{
			blockId = worldObj.getBlockId(bp.x, bp.y, bp.z);
			if(harvestables.containsKey(new Integer(blockId)) && harvestables.get(new Integer(blockId)).getHarvestType() == HarvestType.TreeLeaf
					&& harvestables.get(new Integer(blockId)).canBeHarvested(worldObj, _settings, bp.x, bp.y, bp.z))
			{
				return new BlockPosition(bp.x, bp.y, bp.z);
			}
		}
		
		for(BlockPosition bp : a.getPositionsTopFirst())
		{
			blockId = worldObj.getBlockId(bp.x, bp.y, bp.z);
			if(harvestables.containsKey(new Integer(blockId)) && harvestables.get(new Integer(blockId)).getHarvestType() == HarvestType.Tree
					&& harvestables.get(new Integer(blockId)).canBeHarvested(worldObj, _settings, bp.x, bp.y, bp.z))
			{
				return new BlockPosition(bp.x, bp.y, bp.z);
			}

		}
		return null;
	}

	@Override
	public boolean isActive()
	{
		return false;
	}

	@Override
	public boolean manageLiquids()
	{
		return true;
	}

	@Override
	public boolean manageSolids()
	{
		return true;
	}

	@Override
	public boolean allowActions()
	{
		return false;
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
	}
	
	@Override
	public String getInvName()
	{
		return "Harvester";
	}
}
