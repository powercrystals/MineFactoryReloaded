package powercrystals.minefactoryreloaded.processing;

import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;

public abstract class TileEntityLiquidFabricator extends TileEntityFactoryPowered
{
	private int _liquidId;
	private int _liquidFabPerTick;
	
	private LiquidTank _tank;
	
	protected TileEntityLiquidFabricator(int liquidId, int liquidCostPermB, int liquidFabPerTick)
	{
		super(liquidCostPermB * liquidFabPerTick);
		_liquidId = liquidId;
		_liquidFabPerTick = liquidFabPerTick;
		_tank = new LiquidTank(LiquidContainerRegistry.BUCKET_VOLUME * 4);
	}
	
	@Override
	protected boolean activateMachine()
	{
		if(_liquidId < 0)
		{
			setIdleTicks(getIdleTicksMax());
			return false;
		}
		
		if(_tank.getLiquid() != null && _tank.getCapacity() - _tank.getLiquid().amount < _liquidFabPerTick)
		{
			return false;
		}
		
		_tank.fill(new LiquidStack(_liquidId, _liquidFabPerTick), true);
		
		return true;
	}

	@Override
	public int getEnergyStoredMax()
	{
		return 16000;
	}

	@Override
	public int getWorkMax()
	{
		return 0;
	}

	@Override
	public int getIdleTicksMax()
	{
		return 200;
	}
	
	@Override
	public ILiquidTank getTank()
	{
		return _tank;
	}
	
	@Override
	protected boolean shouldPumpLiquid()
	{
		return true;
	}
}
