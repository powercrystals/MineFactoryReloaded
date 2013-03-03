package powercrystals.minefactoryreloaded.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import buildcraft.core.IMachine;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomItem;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import powercrystals.core.position.Area;
import powercrystals.core.position.BlockPosition;
import powercrystals.core.random.WeightedRandomItemStack;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;

public class TileEntitySludgeBoiler extends TileEntityFactoryPowered implements ITankContainer, IMachine
{
	private LiquidTank _tank;
	private static List<WeightedRandomItem> _drops  = new ArrayList<WeightedRandomItem>();
	private Random _rand;
	private int _tick;
	
	public static void registerSludgeDrop(int weight, ItemStack drop)
	{
		_drops.add(new WeightedRandomItemStack(weight, drop));
	}
	
	public TileEntitySludgeBoiler()
	{
		super(20);
		_tank = new LiquidTank(4 * LiquidContainerRegistry.BUCKET_VOLUME);
		
		_rand = new Random();
	}
	
	@Override
	public String getGuiBackground()
	{
		return "sludgeboiler.png";
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
	public int getEnergyStoredMax()
	{
		return 16000;
	}

	@Override
	public int getWorkMax()
	{
		return 1000;
	}

	@Override
	public int getIdleTicksMax()
	{
		return 1;
	}
	
	@Override
	protected boolean activateMachine()
	{
		if(_tank.getLiquid() != null && _tank.getLiquid().amount > 10)
		{
			_tank.drain(10, true);
			setWorkDone(getWorkDone() + 10);
			_tick++;
			
			if(getWorkDone() >= getWorkMax())
			{
				ItemStack s = ((WeightedRandomItemStack)WeightedRandom.getRandomItem(_rand, _drops)).getStack();
				if(s != null)
				{
					MFRUtil.dropStack(this, s);
				}
				setWorkDone(0);
			}
			
			if(_tick >= 23)
			{
				Area a = new Area(new BlockPosition(this), 3, 3, 3);
				List<?> entities = worldObj.getEntitiesWithinAABB(EntityLiving.class, a.toAxisAlignedBB());
				for(Object o : entities)
				{
					if(o != null && o instanceof EntityPlayer)
					{
						((EntityPlayer)o).addPotionEffect(new PotionEffect(Potion.hunger.id, 20 * 20, 0));
					}
					if(o != null && o instanceof EntityLiving)
					{
						((EntityLiving)o).addPotionEffect(new PotionEffect(Potion.poison.id, 6 * 20, 0));
					}
				}
				_tick = 0;
			}
			return true;
		}
		return false;
	}
	
	@Override
	public ForgeDirection getDropDirection()
	{
		return ForgeDirection.DOWN;
	}

	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
	{
		if(resource == null || (resource.itemID != MineFactoryReloadedCore.sludgeItem.itemID))
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
		if(resource == null || (resource.itemID != MineFactoryReloadedCore.sludgeItem.itemID))
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
		if(type != null && type.itemID == MineFactoryReloadedCore.sludgeItem.itemID)
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
		return "Sludge Boiler";
	}
	
	@Override
	public int getSizeInventory()
	{
		return 0;
	}
}
