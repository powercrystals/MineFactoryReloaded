package powercrystals.minefactoryreloaded.processing;

import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryInventory;

public class TileEntityAutoEnchanter extends TileEntityFactoryInventory implements ISidedInventory, ITankContainer
{
	private Random _rand;
	private int _targetLevel;
	private LiquidTank _tank;
	
	public TileEntityAutoEnchanter()
	{
		super(160);
		_rand = new Random();
		
		_targetLevel = 30;
		_tank = new LiquidTank(4 * LiquidContainerRegistry.BUCKET_VOLUME);
	}
	
	@Override
	public boolean canRotate()
	{
		return false;
	}

	@Override
	public int getWorkMax()
	{
		return (_targetLevel + (int)Math.pow(((double)_targetLevel) / 7.5, 4)) * 10;
	}

	@Override
	public int getEnergyStoredMax()
	{
		return 16000;
	}

	@Override
	public int getIdleTicksMax()
	{
		return 1;
	}
	
	public int getTargetLevel()
	{
		return _targetLevel;
	}
	
	public void setTargetLevel(int targetLevel)
	{
		_targetLevel = targetLevel;
		if(_targetLevel > 30) _targetLevel = 30;
		if(_targetLevel < 1) _targetLevel = 1;
		if(getWorkDone() >= getWorkMax())
		{
			activateMachine();
		}
	}
	
	@Override
	public ILiquidTank getTank()
	{
		return _tank;
	}
	
	@Override
	protected boolean activateMachine()
	{
		ItemStack s = getStackInSlot(0);
		if(s == null)
		{
			setWorkDone(0);
			return false;
		}
		if(s.getItem().getItemEnchantability() == 0 || s.hasTagCompound())
		{
			setInventorySlotContents(0, null);
			setInventorySlotContents(1, s);
			setWorkDone(0);
			return true;
		}
		else if(getWorkDone() >= getWorkMax())
		{
			EnchantmentHelper.addRandomEnchantment(this._rand, s, _targetLevel);
			setInventorySlotContents(0, null);
			setInventorySlotContents(1, s);
			setWorkDone(0);
			return true;
		}
		else if(_tank.getLiquid() != null && _tank.getLiquid().amount >= 10)
		{
			_tank.drain(10, true);
			setWorkDone(getWorkDone() + 1);
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public String getInvName()
	{
		return "Enchanter";
	}
	
	@Override
	public int getSizeInventory()
	{
		return 2;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return MineFactoryReloadedCore.enableCompatibleAutoEnchanter.getBoolean(false) ? 64 : 1;
	}

	@Override
	public int getStartInventorySide(ForgeDirection side)
	{
		if(side == ForgeDirection.UP || side == ForgeDirection.DOWN)
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side)
	{
		if(side == ForgeDirection.UP || side == ForgeDirection.DOWN)
		{
			return 1;
		}
		else
		{
			return 1;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("targetLevel", _targetLevel);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		_targetLevel = nbttagcompound.getInteger("targetLevel");
	}

	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
	{
		if(resource == null || (resource.itemID != MineFactoryReloadedCore.mobEssenceItem.itemID))
		{
			return 0;
		}

		return _tank.fill(resource, doFill);
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill)
	{
		if(resource == null || (resource.itemID != MineFactoryReloadedCore.mobEssenceItem.itemID))
		{
			return 0;
		}

		return _tank.fill(resource, doFill);
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
		if(type != null && type.itemID == MineFactoryReloadedCore.mobEssenceItem.itemID)
		{
			return _tank;
		}
		return null;
	}
}
