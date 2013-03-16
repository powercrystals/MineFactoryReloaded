package powercrystals.minefactoryreloaded.tile.machine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.google.common.collect.ImmutableMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import powercrystals.core.position.Area;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.core.TreeHarvestManager;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiHarvester;
import powercrystals.minefactoryreloaded.gui.container.ContainerHarvester;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;

public class TileEntityHarvester extends TileEntityFactoryPowered implements ITankContainer
{
	private Map<String, Boolean> _settings;
	
	private Random _rand;
	
	private HarvestAreaManager _areaManager;
	private TreeHarvestManager _treeManager;
	private BlockPosition _lastTree;
	
	private LiquidTank _tank;
	
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
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiHarvester(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerHarvester getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerHarvester(this, inventoryPlayer);
	}
	
	public Map<String, Boolean> getSettings()
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
		
		IFactoryHarvestable harvestable = MFRRegistry.getHarvestables().get(new Integer(harvestedBlockId));
		
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
			worldObj.func_94571_i(targetCoords.x, targetCoords.y, targetCoords.z);
		}
		
		harvestable.postHarvest(worldObj, targetCoords.x, targetCoords.y, targetCoords.z);
		
		_tank.fill(new LiquidStack(MineFactoryReloadedCore.sludgeItem, 10), true);

		return true;
	}

	private BlockPosition getNextHarvest()
	{
		BlockPosition bp = _areaManager.getNextBlock();
		
		int searchId = worldObj.getBlockId(bp.x, bp.y, bp.z);
		
		if(!MFRRegistry.getHarvestables().containsKey(new Integer(searchId)))
		{
			_lastTree = null;
			return null;
		}
		
		IFactoryHarvestable harvestable = MFRRegistry.getHarvestables().get(new Integer(searchId));
		if(harvestable.canBeHarvested(worldObj, _settings, bp.x, bp.y, bp.z))
		{
			if(harvestable.getHarvestType() == HarvestType.Normal)
			{
				_lastTree = null;
				return bp;
			}
			else if(harvestable.getHarvestType() == HarvestType.LeaveBottom)
			{
				_lastTree = null;
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
		_lastTree = null;
		return null;
	}
	
	private BlockPosition getNextVertical(int x, int y, int z)
	{
		int highestBlockOffset = -1;
		
		for(int currentYoffset = 1; currentYoffset < MineFactoryReloadedCore.verticalHarvestSearchMaxVertical.getInt(); currentYoffset++)
		{
			int blockId = worldObj.getBlockId(x, y + currentYoffset, z);
			if(MFRRegistry.getHarvestables().containsKey(new Integer(blockId)) && MFRRegistry.getHarvestables().get(new Integer(blockId)).canBeHarvested(worldObj, _settings, x, y + currentYoffset, z))
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
		else if(_treeManager.getIsDone())
		{
			_treeManager.reset();
		}
		
		while(true)
		{
			if(_treeManager.getIsDone())
			{
				return null;
			}
			
			BlockPosition bp = _treeManager.getNextBlock();
			blockId = worldObj.getBlockId(bp.x, bp.y, bp.z);
			
			if(MFRRegistry.getHarvestables().containsKey(new Integer(blockId)) && MFRRegistry.getHarvestables().get(new Integer(blockId)).canBeHarvested(worldObj, _settings, bp.x, bp.y, bp.z))
			{
				if(_treeManager.getIsLeafPass() && MFRRegistry.getHarvestables().get(new Integer(blockId)).getHarvestType() == HarvestType.TreeLeaf)
				{
					return bp;
				}
				else if(!_treeManager.getIsLeafPass() && MFRRegistry.getHarvestables().get(new Integer(blockId)).getHarvestType() == HarvestType.Tree)
				{
					return bp;
				}
				else if(!_treeManager.getIsLeafPass() && MFRRegistry.getHarvestables().get(new Integer(blockId)).getHarvestType() == HarvestType.TreeLeaf)
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
