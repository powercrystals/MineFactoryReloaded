package powercrystals.minefactoryreloaded.tile.base;

import java.util.ArrayList;
import java.util.List;

import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import powercrystals.core.power.PowerProviderAdvanced;
import powercrystals.core.util.Util;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.setup.Machine;
import universalelectricity.core.block.IConnector;
import universalelectricity.core.block.IVoltage;
import universalelectricity.core.electricity.ElectricityNetworkHelper;
import universalelectricity.core.electricity.ElectricityPack;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;

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

public abstract class TileEntityFactoryPowered extends TileEntityFactoryInventory implements IPowerReceptor, IEnergySink, IVoltage, IConnector
{	
	public static final int energyPerEU = 4;
	public static final int energyPerMJ = 10;
	public static final int wPerEnergy = 7;
	
	private int _energyStored;
	protected int _energyActivation;
	
	private int _workDone;
	
	private int _idleTicks;
	
	protected List<ItemStack> failedDrops = null;
	
	protected int _failedDropTicksMax = 20;
	private int _failedDropTicks = 0;
	
	// buildcraft-related fields
	
	private IPowerProvider _powerProvider;
	
	// IC2-related fields
	
	private boolean _isAddedToIC2EnergyNet;
	private boolean _addToNetOnNextTick;
	
	// UE-related fields
	
	private int _ueBuffer;
	
	// constructors
	
	protected TileEntityFactoryPowered(Machine machine)
	{
		this(machine, machine.getActivationEnergyMJ());
	}
	
	protected TileEntityFactoryPowered(Machine machine, int activationCostMJ)
	{
		super(machine);
		this._energyActivation = activationCostMJ * energyPerMJ;
		_powerProvider = new PowerProviderAdvanced();
		_powerProvider.configure(25, 10, 10, 1, 1000);
		setIsActive(false);
	}
	
	// local methods
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		_energyStored = Math.min(_energyStored, getEnergyStoredMax());
		
		if(worldObj.isRemote)
		{
			return;
		}
		
		if(_addToNetOnNextTick)
		{
			if(!worldObj.isRemote)
			{
				MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			}
			_addToNetOnNextTick = false;
			_isAddedToIC2EnergyNet = true;
		}
		
		if(getPowerProvider() != null)
		{
			getPowerProvider().update(this);
			
			int mjRequired = Math.min((getEnergyStoredMax() - getEnergyStored()) / energyPerMJ, 100);
			if(_energyStored < getEnergyStoredMax() && getPowerProvider().useEnergy(1, mjRequired, false) > 0)
			{
				int mjGained = (int)(getPowerProvider().useEnergy(1, mjRequired, true));
				_energyStored += mjGained * energyPerMJ;
			}
		}
		
		ElectricityPack powerRequested = new ElectricityPack((getEnergyStoredMax() - getEnergyStored()) * wPerEnergy / getVoltage(), getVoltage());
		ElectricityPack powerPack = ElectricityNetworkHelper.consumeFromMultipleSides(this, powerRequested);
		_ueBuffer += powerPack.getWatts();
		
		int energyFromUE = Math.min(_ueBuffer / wPerEnergy, getEnergyStoredMax() - getEnergyStored());
		_energyStored += energyFromUE;
		_ueBuffer -= (energyFromUE * wPerEnergy);
		
		setIsActive(_energyStored >= _energyActivation * 2);
		
		if (failedDrops != null)
		{
			if (_failedDropTicks < _failedDropTicksMax)
			{
				_failedDropTicks++;
				return;
			}
			if (!doDrop(failedDrops))
			{
				setIdleTicks(getIdleTicksMax());
				return;
			}
			failedDrops = null;
		}
		
		if(Util.isRedstonePowered(this))
		{
			setIdleTicks(getIdleTicksMax());
		}
		else if(_idleTicks > 0)
		{
			_idleTicks--;
		}
		else if(_energyStored >= _energyActivation)
		{
			if(activateMachine())
			{
				_energyStored -= _energyActivation;
			}
		}
	}

	public boolean doDrop(ItemStack drop)
	{
		drop = UtilInventory.dropStack(this, drop, this.getDropDirection());
		if (drop != null && drop.stackSize > 0)
		{
			if (failedDrops == null)
			{
				failedDrops = new ArrayList<ItemStack>();
			}
			failedDrops.add(drop);
		}
		return true;
	}
	
	public boolean doDrop(List<ItemStack> drops)
	{
		if (drops == null || drops.size() <= 0)
		{
			return true;
		}
		for (int i = drops.size(); i --> 0; )
		{
			ItemStack dropStack = drops.get(i);
			dropStack = UtilInventory.dropStack(this, dropStack, this.getDropDirection());
			if (dropStack == null || dropStack.stackSize <= 0)
			{
				drops.remove(i);
			}
		}
		
		if (drops.size() != 0)
		{
			if (drops != failedDrops)
			{
				if (failedDrops == null)
				{
					failedDrops = new ArrayList<ItemStack>();
				}
				failedDrops.addAll(drops);
				drops.clear();
			}
			return false;
		}
		
		return true;
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
			if(!worldObj.isRemote)
			{
				MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			}
			_isAddedToIC2EnergyNet = false;
		}
		ElectricityNetworkHelper.invalidate(this);
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
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		
		nbttagcompound.setInteger("energyStored", _energyStored);
		nbttagcompound.setInteger("workDone", _workDone);
		nbttagcompound.setInteger("ueBuffer", _ueBuffer);
		_powerProvider.writeToNBT(nbttagcompound);
		
		if (failedDrops != null)
		{
			NBTTagList nbttaglist = new NBTTagList();
			for (ItemStack item : failedDrops)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				item.writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
			nbttagcompound.setTag("DropItems", nbttaglist);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		
		_energyStored = Math.min(nbttagcompound.getInteger("energyStored"), getEnergyStoredMax());
		_workDone = Math.min(nbttagcompound.getInteger("workDone"), getWorkMax());
		_ueBuffer = nbttagcompound.getInteger("ueBuffer");
		_powerProvider.readFromNBT(nbttagcompound);
		_powerProvider.configure(25, 10, 10, 1, 1000);

		if (nbttagcompound.hasKey("DropItems"))
		{
			List<ItemStack> drops = new ArrayList<ItemStack>();
			NBTTagList nbttaglist = nbttagcompound.getTagList("DropItems");
			for (int i = nbttaglist.tagCount(); i --> 0; )
			{
				NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
				ItemStack item = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				if (item != null && item.stackSize > 0)
				{
					drops.add(item);
				}
			}
			if (drops.size() != 0)
			{
				failedDrops = drops;
			}
		}
	}
	
	// BC methods
	
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
	public int powerRequest(ForgeDirection from)
	{
		return Math.max((getEnergyStoredMax() - getEnergyStored()) / energyPerMJ, 0);
	}
	
	@Override
	public final void doWork()
	{
	}
	
	// IC2 methods
	
	
	@Override
	public int demandsEnergy()
	{
		return ((getEnergyStoredMax() - getEnergyStored()) / energyPerEU);
	}
	
	
	@Override
	public int injectEnergy(Direction directionFrom, int amount)
	{
		int euInjected = Math.min(demandsEnergy(), amount);
		_energyStored += euInjected * energyPerEU;
		return amount - euInjected;
	}
	
	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction)
	{
		return true;
	}
	
	@Override
	public boolean isAddedToEnergyNet()
	{
		return _isAddedToIC2EnergyNet;
	}
	
	@Override
	public int getMaxSafeInput()
	{
		return 128;
	}
	
	// UE Methods
	
	@Override
	public double getVoltage()
	{
		return 120;
	}
	
	@Override
	public boolean canConnect(ForgeDirection direction)
	{
		return true;
	}
}
