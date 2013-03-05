package powercrystals.minefactoryreloaded.animals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.IFactoryGrindable;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;

public class TileEntityGrinder extends TileEntityFactoryPowered implements ITankContainer
{
	private static Map<Class<?>, IFactoryGrindable> grindables = new HashMap<Class<?>, IFactoryGrindable>();
	
	private HarvestAreaManager _areaManager;
	private LiquidTank _tank;
	private Random _rand;

	public static void registerGrindable(IFactoryGrindable grindable)
	{
		grindables.put(grindable.getGrindableEntity(), grindable);
	}
	
	@Override
	public String getGuiBackground()
	{
		return "grinder.png";
	}
	
	public TileEntityGrinder()
	{
		super(3200);
		_areaManager = new HarvestAreaManager(this, 2, 2, 1);
		_tank = new LiquidTank(4 * LiquidContainerRegistry.BUCKET_VOLUME);
		_rand = new Random();
	}
	
	public Random getRandom()
	{
		return _rand;
	}
	
	@Override
	protected boolean shouldPumpLiquid()
	{
		return true;
	}

	@Override
	public int getEnergyStoredMax()
	{
		return 32000;
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
	public ILiquidTank getTank()
	{
		return _tank;
	}

	@Override
	public boolean activateMachine()
	{
		boolean foundMob = false;
		List<?> entities = worldObj.getEntitiesWithinAABB(EntityLiving.class, _areaManager.getHarvestArea().toAxisAlignedBB());
		
		for(Object o : entities)
		{
			if(o instanceof EntityPlayer)
			{
				continue;
			}
			if(o instanceof EntityAgeable && ((EntityAgeable)o).getGrowingAge() < 0)
			{
				continue;
			}
			EntityLiving e = (EntityLiving)o;
			if(e.getHealth() <= 0)
			{
				continue;
			}
			for(int slot = 0; slot < 5; slot++)
			{
				ItemStack s = e.getCurrentItemOrArmor(slot);
				if(s != null && s.hasTagCompound())
				{
					MFRUtil.dropStack(this, s);
					foundMob = true;
				}
			}
			if(grindables.containsKey(e.getClass()))
			{
				IFactoryGrindable r = grindables.get(e.getClass());
				List<ItemStack> drops = r.grind(worldObj, e, getRandom());
				if(drops != null)
				{
					if(drops.size() == 1)
					{
						MFRUtil.dropStack(this, drops.get(0));
					}
					else if(drops.size() > 1)
					{
						MFRUtil.dropStack(this, drops.get(_rand.nextInt(drops.size())));
					}
				}
				
				foundMob = true;
			}
			if(foundMob)
			{
				if(worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot"))
				{
					try
					{
						worldObj.getGameRules().setOrCreateGameRule("doMobLoot", "false");
						e.attackEntityFrom(DamageSource.generic, 50);
					}
					finally
					{
						worldObj.getGameRules().setOrCreateGameRule("doMobLoot", "true");
					}
				}
				else
				{
					e.attackEntityFrom(DamageSource.generic, 50);
				}
				_tank.fill(new LiquidStack(MineFactoryReloadedCore.mobEssenceItem, 100), true);
				setIdleTicks(20);
				return true;
			}
		}
		setIdleTicks(getIdleTicksMax());
		return false;
	}

	@Override
	public String getInvName()
	{
		return "Mob Grinder";
	}
	
	@Override
	public int getSizeInventory()
	{
		return 0;
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

	@Override
	public boolean manageSolids()
	{
		return true;
	}
}
