package powercrystals.minefactoryreloaded.core;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.position.Area;
import powercrystals.core.position.BlockPosition;
import powercrystals.core.position.IRotateableTile;
import powercrystals.minefactoryreloaded.item.ItemUpgrade;

public class HarvestAreaManager
{
	private IRotateableTile _owner;
	
	private int _originX;
	private int _originY;
	private int _originZ;
	private ForgeDirection _originOrientation;
	
	private ForgeDirection _overrideDirection;
	private Area _harvestArea;
	private int _radius;
	private int _areaUp;
	private int _areaDown;
	
	private int _originOffsetX;
	private int _originOffsetY;
	private int _originOffsetZ;
	
	private List<BlockPosition> _harvestedBlocks;
	private int _currentBlock;
	
	private int _upgradeLevel;
	
	public HarvestAreaManager(IRotateableTile owner, int harvestRadius, int harvestAreaUp, int harvestAreaDown)
	{
		_overrideDirection = ForgeDirection.UNKNOWN;
		_radius = harvestRadius;
		_areaUp = harvestAreaUp;
		_areaDown = harvestAreaDown;
		_owner = owner;
		
		_originX = ((TileEntity)owner).xCoord;
		_originY = ((TileEntity)owner).yCoord;
		_originZ = ((TileEntity)owner).zCoord;
		_originOrientation = owner.getDirectionFacing();
	}
	
	public void setOriginOffset(int x, int y, int z)
	{
		_originOffsetX = x;
		_originOffsetY = y;
		_originOffsetZ = z;
		checkRecalculate();
	}
	
	public Area getHarvestArea()
	{
		checkRecalculate();
		return _harvestArea;
	}
	
	public int getOriginX()
	{
		return _originX;
	}
	
	public int getOriginY()
	{
		return _originY;
	}
	
	public int getOriginZ()
	{
		return _originZ;
	}
	
	public int getRadius()
	{
		return _radius + _upgradeLevel;
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
	
	public void rewindBlock()
	{
		_currentBlock--;
		if(_currentBlock < 0)
		{
			_currentBlock = _harvestedBlocks.size() - 1;
		}
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
		
		if(		(_overrideDirection != ForgeDirection.UNKNOWN && _originOrientation != _overrideDirection)
				|| (_overrideDirection == ForgeDirection.UNKNOWN && _originOrientation != _owner.getDirectionFacing())
				|| _originX != ((TileEntity)_owner).xCoord + _originOffsetX
				|| _originY != ((TileEntity)_owner).yCoord + _originOffsetY
				|| _originZ != ((TileEntity)_owner).zCoord + _originOffsetZ)
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
		
		_originX = ourpos.x + _originOffsetX;
		_originY = ourpos.y + _originOffsetY;
		_originZ = ourpos.z + _originOffsetZ;
		_originOrientation = ourpos.orientation;
		
		int radius = _radius + _upgradeLevel;
		
		if(ourpos.orientation == ForgeDirection.UP || ourpos.orientation == ForgeDirection.DOWN)
		{
			ourpos.moveForwards(1);
		}
		else
		{
			ourpos.moveForwards(radius + 1);
		}
		
		ourpos.x += _originOffsetX;
		ourpos.y += _originOffsetY;
		ourpos.z += _originOffsetZ;
		
		_harvestArea = new Area(ourpos, radius, _areaDown, _areaUp);
		_harvestedBlocks = _harvestArea.getPositionsBottomFirst();
		_currentBlock = 0;
	}
}
