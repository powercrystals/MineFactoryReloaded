package powercrystals.minefactoryreloaded.core;

import java.util.List;

import powercrystals.core.position.Area;
import powercrystals.core.position.BlockPosition;

public class TreeHarvestManager
{
	private List<BlockPosition> _treeBlocks;
	private int _currentBlock;
	private boolean _isLeafPass;
	private boolean _isDone;
	private boolean _treeIsUpsideDown; 
	
	private Area _treeArea;
	
	public TreeHarvestManager(Area treeArea, boolean treeIsUpsideDown)
	{
		_treeIsUpsideDown = treeIsUpsideDown;
		_treeArea = treeArea;
		reset();
	}
	
	public BlockPosition getNextBlock()
	{
		return _treeBlocks.get(_currentBlock);
	}
	
	public void moveNext()
	{
		_currentBlock++;
		if(_currentBlock >= _treeBlocks.size())
		{
			if(_isLeafPass)
			{
				_currentBlock = 0;
				_treeBlocks = (_treeIsUpsideDown ? _treeArea.getPositionsBottomFirst() : _treeArea.getPositionsTopFirst());
				_isLeafPass = false;
			}
			else
			{
				_isDone = true;
			}
		}
	}
	
	public void reset()
	{
		_currentBlock = 0;
		_isLeafPass = true;
		_treeBlocks = (_treeIsUpsideDown ? _treeArea.getPositionsTopFirst() : _treeArea.getPositionsBottomFirst());
	}
	
	public boolean getIsLeafPass()
	{
		return _isLeafPass;
	}
	
	public boolean getIsDone()
	{
		return _isDone;
	}
}
