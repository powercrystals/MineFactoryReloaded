package powercrystals.minefactoryreloaded.processing;

import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;

public abstract class TileEntityLiquidFabricator extends TileEntityFactoryPowered implements ITankContainer
{
	private int _liquidId;
	private int _liquidFabPerTick;
	
	private LiquidTank _tank;
	
	protected TileEntityLiquidFabricator(int liquidId, int liquidCostPermB, int liquidFabPerTick)
	{
		super(liquidCostPermB * liquidFabPerTick);
		_liquidId = liquidId;
		_liquidFabPerTick = liquidFabPerTick;
		_tank = new LiquidTank(LiquidContainerRegistry.BUCKET_VOLUME);
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
}
