package powercrystals.minefactoryreloaded.core;

import java.util.List;

import powercrystals.core.position.Area;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.core.TileEntityFactory;
import net.minecraftforge.common.ForgeDirection;

public class HarvestAreaManager
{
	private TileEntityFactory _owner;
	private ForgeDirection _overrideDirection;
	private Area _harvestArea;
	private int _radius;
	private int _areaUp;
	private int _areaDown;
	
	private List<BlockPosition> _harvestedBlocks;
	private int _currentBlock;
	
	public HarvestAreaManager(TileEntityFactory owner, int harvestRadius, int harvestAreaUp, int harvestAreaDown)
	{
		_overrideDirection = ForgeDirection.UNKNOWN;
		_radius = harvestRadius;
		_areaUp = harvestAreaUp;
		_areaDown = harvestAreaDown;
		_owner = owner;
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
	
	private void checkRecalculate()
	{
		if(_harvestArea == null)
		{
			recalculateArea();
			return;
		}
		
		BlockPosition origin = _harvestArea.getOrigin();
		if(        (_overrideDirection != ForgeDirection.UNKNOWN && origin.orientation != _overrideDirection)
				|| (_overrideDirection == ForgeDirection.UNKNOWN && origin.orientation != _owner.getDirectionFacing())
				|| origin.x != _owner.xCoord
				|| origin.y != _owner.yCoord
				|| origin.z != _owner.zCoord)
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
		ourpos.moveForwards(_radius + 1);
		Area a = new Area(ourpos, _radius, _areaDown, _areaUp);
		_harvestedBlocks = a.getPositionsBottomFirst();
		_currentBlock = 0;
		_harvestArea = a;
	}
	
	public void setOverrideDirection(ForgeDirection dir)
	{
		_overrideDirection = dir;
	}
}
