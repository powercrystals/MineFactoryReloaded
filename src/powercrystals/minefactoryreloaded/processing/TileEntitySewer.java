package powercrystals.minefactoryreloaded.processing;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import powercrystals.core.position.Area;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.core.TileEntityFactory;

public class TileEntitySewer extends TileEntityFactory implements ITankContainer
{
	private LiquidTank _tank;
	private Area _harvestArea;
	private int _tick;
	
	public TileEntitySewer()
	{
		_tank = new LiquidTank(1 * LiquidContainerRegistry.BUCKET_VOLUME);
	}
	
	@Override
	public boolean canRotate()
	{
		return false;
	}
	
	@Override
	public void validate()
	{
		super.validate();
		BlockPosition center = new BlockPosition(this);
		center.orientation = ForgeDirection.UP;
		center.moveForwards(1);
		_harvestArea = new Area(center, 0, 1, 0);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		_tick++;
		MFRUtil.pumpLiquid(_tank, this);
		
		if(_tick >= 31)
		{
			_tick = 0;
			List<?> entities = worldObj.getEntitiesWithinAABB(EntityLiving.class, _harvestArea.toAxisAlignedBB());
			for(Object o : entities)
			{
				if(o != null && o instanceof EntityAnimal)
				{
					_tank.fill(new LiquidStack(MineFactoryReloadedCore.sewageItem, 25), true);
					return;
				}
			}
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
}
