package powercrystals.minefactoryreloaded.animals;

import java.util.List;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.nbt.NBTTagCompound;

import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;

public class TileEntityChronotyper extends TileEntityFactoryPowered
{
	private HarvestAreaManager _areaManager;
	private boolean _moveOld;
	
	public TileEntityChronotyper()
	{
		super(1280);
		_areaManager = new HarvestAreaManager(this, 2, 2, 1);
	}
	
	@Override
	public String getInvName()
	{
		return "Chronotyper";
	}

	@Override
	protected boolean activateMachine()
	{
		List<?> entities = worldObj.getEntitiesWithinAABB(EntityAgeable.class, _areaManager.getHarvestArea().toAxisAlignedBB());
		
		for(Object o : entities)
		{
			if(!(o instanceof EntityAgeable))
			{
				continue;
			}
			EntityAgeable a = (EntityAgeable)o;
			if((a.getGrowingAge() < 0 && !_moveOld) || (a.getGrowingAge() >= 0 && _moveOld))
			{
				BlockPosition bp = BlockPosition.fromFactoryTile(this);
				bp.moveBackwards(1);
				a.setPosition(bp.x + 0.5, bp.y + 0.5, bp.z + 0.5);
				
				return true;
			}
		}
		setIdleTicks(getIdleTicksMax());
		return false;
	}
	
	public boolean getMoveOld()
	{
		return _moveOld;
	}
	
	public void setMoveOld(boolean moveOld)
	{
		_moveOld = moveOld;
	}

	@Override
	public int getEnergyStoredMax()
	{
		return 96000;
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
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		
		nbttagcompound.setByte("moveOld", (byte)(_moveOld ? 1 : 0));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		_moveOld = nbttagcompound.getByte("moveOld") == 0 ? false : true;
	}
}
