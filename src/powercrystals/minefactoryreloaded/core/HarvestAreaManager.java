package powercrystals.minefactoryreloaded.core;

import java.util.List;

import powercrystals.core.position.Area;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.core.TileEntityFactory;
import powercrystals.minefactoryreloaded.processing.ItemUpgrade;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public class HarvestAreaManager
{
	private TileEntityFactory _owner;
	
	private int _originX;
	private int _originY;
	private int _originZ;
	private ForgeDirection _originOrientation;
	
	private ForgeDirection _overrideDirection;
	private Area _harvestArea;
	private int _radius;
	private int _areaUp;
	private int _areaDown;
	
	private List<BlockPosition> _harvestedBlocks;
	private int _currentBlock;
	
	private int _upgradeLevel;
	
	public HarvestAreaManager(TileEntityFactory owner, int harvestRadius, int harvestAreaUp, int harvestAreaDown)
	{
		_overrideDirection = ForgeDirection.UNKNOWN;
		_radius = harvestRadius;
		_areaUp = harvestAreaUp;
		_areaDown = harvestAreaDown;
		_owner = owner;
		
		_originX = owner.xCoord;
		_originY = owner.yCoord;
		_originZ = owner.zCoord;
		_originOrientation = owner.getDirectionFacing();
	}
	
	public Area getHarvestArea()
	{
		checkRecalculate();
		return _harvestArea;
	}
	
	public BlockPosition getNextBlock()
	{
		checkRecalculate();
		BlockPosition next = _harvestedBlocks.get(_currentBlock);
		_currentBlock++;
		if(_currentBlock >= _harvestedBlocks.size())
		{
			_currentBlock = 0;
		}
		
		return next;
	}
	
	public void setOverrideDirection(ForgeDirection dir)
	{
		_overrideDirection = dir;
	}
	
	public void updateUpgradeLevel(ItemStack stack)
	{
		int newUpgradeLevel = 0;
		if(stack != null && stack.getItem() instanceof ItemUpgrade)
		{
			newUpgradeLevel = ((ItemUpgrade)stack.getItem()).getUpgradeLevel(stack);
		}
		if(newUpgradeLevel != _upgradeLevel)
		{
			_upgradeLevel = newUpgradeLevel;
			recalculateArea();
		}
	}
	
	private void checkRecalculate()
	{
		if(_harvestArea == null)
		{
			recalculateArea();
			return;
		}
		
		if(        (_overrideDirection != ForgeDirection.UNKNOWN && _originOrientation != _overrideDirection)
				|| (_overrideDirection == ForgeDirection.UNKNOWN && _originOrientation != _owner.getDirectionFacing())
				|| _originX != _owner.xCoord
				|| _originY != _owner.yCoord
				|| _originZ != _owner.zCoord)
		{
			recalculateArea();
		}
	}
	
	private void recalculateArea()
	{
		BlockPosition ourpos = BlockPosition.fromFactoryTile(_owner);
		if(_overrideDirection != ForgeDirection.UNKNOWN)
		{
			ourpos.orientation = _overrideDirection;
		}
		
		_originX = ourpos.x;
		_originY = ourpos.y;
		_originZ = ourpos.z;
		_originOrientation = ourpos.orientation;
		
		ourpos.moveForwards(_radius + 1);
		_harvestArea = new Area(ourpos, _radius + _upgradeLevel, _areaDown, _areaUp);
		_harvestedBlocks = _harvestArea.getPositionsBottomFirst();
		_currentBlock = 0;
	}
}
