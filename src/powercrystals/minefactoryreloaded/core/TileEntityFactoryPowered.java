package powercrystals.minefactoryreloaded.core;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerFramework;

/*
 * There are three pieces of information tracked - energy, work, and idle ticks.
 * 
 * Energy is stored and used when the machine activates. The energy stored must be >= energyActivation for the activateMachine() method to be called.
 * If activateMachine() returns true, energy will be drained.
 * 
 * Work is built up and then when at 100% something happens. This is tracked/used entirely by the derived class. If not used (f.ex. harvester), return max 1.
 * 
 * Idle ticks cause an artificial delay before activateMachine() is called again. Max should be the highest value the machine will use, to draw the
 * progress bar correctly.
 */

public abstract class TileEntityFactoryPowered extends TileEntityFactory implements IPowerReceptor, IEnergySink
{	
	private static int energyPerEU = 4;
	private static int energyPerMJ = 10;
	
	private int _energyStored;
	private int _energyActivation;
	
	private int _workDone;
	
	private int _idleTicks;
	
	// buildcraft-related fields
	
	private IPowerProvider _powerProvider;
	
	// IC2-related fields
	
	private boolean _isAddedToIC2EnergyNet;
	private boolean _addToNetOnNextTick;
	
	// constructors
	
	protected TileEntityFactoryPowered()
	{
		this(100);
	}
	
	protected TileEntityFactoryPowered(int energyActivation)
	{
		this._energyActivation = energyActivation;
		if(PowerFramework.currentFramework != null)
		{
			_powerProvider = PowerFramework.currentFramework.createPowerProvider();
			_powerProvider.configure(25, 10, 10, 1, 1000);
		}
	}
	
	// local methods
	
	public abstract String getInvName();
	
	protected boolean pumpLiquid()
	{
		return false;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if(_addToNetOnNextTick)
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			_addToNetOnNextTick = false;
			_isAddedToIC2EnergyNet = true;
		}
		
		if(getPowerProvider() != null)
		{
			getPowerProvider().update(this);
			
			if(_energyStored < getEnergyStoredMax() && getPowerProvider().useEnergy(1, 100, false) > 0)
			{
				int mjGained = (int)(getPowerProvider().useEnergy(1, 100, true));
				_energyStored += mjGained * energyPerMJ;
			}
		}
		
		if(_idleTicks > 0)
		{
			_idleTicks--;
		}
		else if(_energyStored >= _energyActivation)
		{
			if(activateMachine())
			{
				_energyStored -= _energyActivation;
				setIsActive(true);
			}
			else
			{
				setIsActive(false);
			}
		}

		if(pumpLiquid())
		{
			MFRUtil.pumpLiquid(getTank(), this);
		}
	}
	
	@Override
	public void validate()
	{
		super.validate();
		if(!_isAddedToIC2EnergyNet)
		{
			_addToNetOnNextTick = true;
		}
	}
	
	@Override
	public void invalidate()
	{
		if(_isAddedToIC2EnergyNet)
		{
			_isAddedToIC2EnergyNet = false;
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		}
		super.invalidate();
	}
	
	protected abstract boolean activateMachine();
	
	public void onBlockBroken()
	{
		if(_isAddedToIC2EnergyNet)
		{
			_isAddedToIC2EnergyNet = false;
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		}
	}
	
	public int getEnergyStored()
	{
		return _energyStored;
	}
	
	public abstract int getEnergyStoredMax();
	
	public void setEnergyStored(int energy)
	{
		_energyStored = energy;
	}
	
	public int getWorkDone()
	{
		return _workDone;
	}
	
	public abstract int getWorkMax();
	
	public void setWorkDone(int work)
	{
		_workDone = work;
	}
	
	public int getIdleTicks()
	{
		return _idleTicks;
	}
	
	public abstract int getIdleTicksMax();
	
	public void setIdleTicks(int ticks)
	{
		_idleTicks = ticks;
	}
	
	public ILiquidTank getTank()
	{
		return null;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);

		nbttagcompound.setInteger("energyStored", _energyStored);
		nbttagcompound.setInteger("workDone", _workDone);
		
		if(getTank() != null && getTank().getLiquid() != null)
		{
			nbttagcompound.setInteger("tankAmount", getTank().getLiquid().amount);
			nbttagcompound.setInteger("tankItemId", getTank().getLiquid().itemID);
			nbttagcompound.setInteger("tankMeta", getTank().getLiquid().itemMeta);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		
		_energyStored = nbttagcompound.getInteger("energyStored");
		_workDone = nbttagcompound.getInteger("workDone");
		if(getTank() != null)
		{
			((LiquidTank)getTank()).setLiquid(new LiquidStack(nbttagcompound.getInteger("tankItemId"), nbttagcompound.getInteger("tankAmount"), nbttagcompound.getInteger("tankItemMeta")));
		}
	}
	
	// IPowerReceptor methods

	@Override
	public void setPowerProvider(IPowerProvider provider)
	{
		_powerProvider = provider;
	}

	@Override
	public IPowerProvider getPowerProvider()
	{
		return _powerProvider;
	}

	@Override
	public int powerRequest()
	{
		//return (energyStoredMax - energyStored) / energyPerMJ;
		return 10;
	}
	
	@Override
	public final void doWork()
	{
	}
	
	// IEnergySink methods
	
	/**
	 * Determine whether the sink requires energy.
	 * 
	 * @return max accepted input in eu
	 */
	public int demandsEnergy()
	{
		return ((getEnergyStoredMax() - _energyStored) / energyPerEU);
	}

	/**
	 * Transfer energy to the sink.
	 *
	 * @param directionFrom direction from which the energy comes from
	 * @param amount energy to be transferred
	 * @return Energy not consumed (leftover)
	 */
	public int injectEnergy(Direction directionFrom, int amount)
	{
		int maxEnergyInjectable = (getEnergyStoredMax() - _energyStored) / energyPerEU;
		int euInjected = Math.min(maxEnergyInjectable, amount);
		_energyStored += euInjected * energyPerEU;
		return amount - euInjected;
	}
	
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction)
	{
		return true;
	}
	
	public boolean isAddedToEnergyNet()
	{
		return _isAddedToIC2EnergyNet;
	}
	
	@Override
	public int getMaxSafeInput()
	{
		return 128;
	}
}
