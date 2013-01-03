package powercrystals.minefactoryreloaded.core;

import powercrystals.minefactoryreloaded.core.Area;
import powercrystals.minefactoryreloaded.core.BlockPosition;
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
	
	public HarvestAreaManager(TileEntityFactory owner, int harvestRadius, int harvestAreaUp, int harvestAreaDown)
	{
		_overrideDirection = ForgeDirection.UNKNOWN;
		_radius = harvestRadius;
		_areaUp = harvestAreaUp;
		_areaDown = harvestAreaDown;
		_owner = owner;
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
		_harvestArea = a;
	}
	
	public Area getHarvestArea()
	{
		if(_harvestArea == null)
		{
			recalculateArea();
			return _harvestArea;
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
		return _harvestArea;
	}
	
	public void setOverrideDirection(ForgeDirection dir)
	{
		_overrideDirection = dir;
	}
}
