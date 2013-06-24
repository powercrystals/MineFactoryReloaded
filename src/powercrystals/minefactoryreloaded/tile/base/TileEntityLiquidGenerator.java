package powercrystals.minefactoryreloaded.tile.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import powercrystals.core.util.Util;
import powercrystals.minefactoryreloaded.core.ITankContainerBucketable;
import powercrystals.minefactoryreloaded.setup.Machine;

public abstract class TileEntityLiquidGenerator extends TileEntityGenerator implements ITankContainerBucketable
{
	private int _liquidConsumedPerTick;
	private int _powerProducedPerConsumption;
	private int _ticksBetweenConsumption;
	private int _outputPulseSize;
	
	private int _ticksSinceLastConsumption = 0;
	private int _bufferMax = 1000;
	private int _buffer;
	
	private LiquidTank _tank;
	
	public TileEntityLiquidGenerator(Machine machine, int liquidConsumedPerTick, int powerProducedPerConsumption, int ticksBetweenConsumption)
	{
		super(machine);
		_liquidConsumedPerTick = liquidConsumedPerTick;
		_powerProducedPerConsumption = powerProducedPerConsumption;
		_ticksBetweenConsumption = ticksBetweenConsumption;
		_outputPulseSize = machine.getActivationEnergyMJ() * TileEntityFactoryPowered.energyPerMJ;
		
		_tank = new LiquidTank(LiquidContainerRegistry.BUCKET_VOLUME * 4);
	}
	
	protected abstract LiquidStack getLiquidType();
	
	public int getBuffer()
	{
		return _buffer;
	}
	
	public void setBuffer(int buffer)
	{
		_buffer = buffer;
	}
	
	public int getBufferMax()
	{
		return _bufferMax;
	}
	
	@Override
	public ILiquidTank getTank()
	{
		return _tank;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(!worldObj.isRemote)
		{
			setIsActive(_buffer > _outputPulseSize * 2);
			
			int mjPulse = Math.min(_buffer, _outputPulseSize);
			_buffer -= mjPulse;
			_buffer += producePower(mjPulse);
			
			if(_ticksSinceLastConsumption < _ticksBetweenConsumption)
			{
				_ticksSinceLastConsumption++;
				return;
			}
			_ticksSinceLastConsumption = 0;
			
			if(Util.isRedstonePowered(this))
			{
				return;
			}
			
			if(_tank.getLiquid() == null || _tank.getLiquid().amount < _liquidConsumedPerTick || _bufferMax - _buffer < _powerProducedPerConsumption)
			{
				return;
			}
			
			_tank.drain(_liquidConsumedPerTick, true);
			_buffer += _powerProducedPerConsumption;
		}
	}
	
	@Override
	public boolean allowBucketFill()
	{
		return true;
	}
	
	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
	{
		if(resource == null || resource.itemID != getLiquidType().itemID || resource.itemMeta != getLiquidType().itemMeta)
		{
			return 0;
		}
		return _tank.fill(resource, doFill);
	}
	
	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill)
	{
		return fill(ForgeDirection.UNKNOWN, resource, doFill);
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
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("ticksSinceLastConsumption", _ticksSinceLastConsumption);
		nbttagcompound.setInteger("buffer", _buffer);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		
		_ticksSinceLastConsumption = nbttagcompound.getInteger("ticksSinceLastConsumption");
		_buffer = nbttagcompound.getInteger("buffer");
	}
}
