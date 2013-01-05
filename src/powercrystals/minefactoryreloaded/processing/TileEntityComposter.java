package powercrystals.minefactoryreloaded.processing;

import buildcraft.core.IMachine;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;

public class TileEntityComposter extends TileEntityFactoryPowered implements ITankContainer, IMachine
{
	private LiquidTank _tank;
	
	public TileEntityComposter()
	{
		super(80);
		_tank = new LiquidTank(4 * LiquidContainerRegistry.BUCKET_VOLUME);
	}
	
	@Override
	public boolean canRotate()
	{
		return false;
	}
	
	@Override
	public ILiquidTank getTank()
	{
		return _tank;
	}
	
	@Override
	protected boolean activateMachine()
	{
		if(_tank.getLiquid() != null && _tank.getLiquid().amount > 20)
		{
			_tank.drain(20, true);
			setWorkDone(getWorkDone() + 20);
			
			if(getWorkDone() >= getWorkMax())
			{
				MFRUtil.dropStack(this, new ItemStack(MineFactoryReloadedCore.fertilizerItem));
				setWorkDone(0);
			}
			return true;
		}
		return false;
	}
	
	@Override
	public ForgeDirection getDropDirection()
	{
		return ForgeDirection.UP;
	}

	@Override
	public int getEnergyStoredMax()
	{
		return 16000;
	}

	@Override
	public int getWorkMax()
	{
		return 8000;
	}

	@Override
	public int getIdleTicksMax()
	{
		return 1;
	}

	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
	{
		if(resource.itemID != MineFactoryReloadedCore.sewageItem.shiftedIndex)
		{
			return 0;
		}
		else
		{
			return _tank.fill(resource, doFill);
		}
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill)
	{
		if(resource.itemID != MineFactoryReloadedCore.sewageItem.shiftedIndex)
		{
			return 0;
		}
		else
		{
			return _tank.fill(resource, doFill);
		}
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
		if(type.itemID == MineFactoryReloadedCore.sewageItem.shiftedIndex)
		{
			return _tank;
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
	public String getInvName()
	{
		return "Composter";
	}
}
