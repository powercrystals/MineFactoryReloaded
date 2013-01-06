package powercrystals.minefactoryreloaded.animals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buildcraft.core.IMachine;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.IFactoryRanchable;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryInventory;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;

public class TileEntityRancher extends TileEntityFactoryInventory implements IMachine
{
	private static Map<Class<?>, IFactoryRanchable> ranchables = new HashMap<Class<?>, IFactoryRanchable>();
	
	private HarvestAreaManager _areaManager;
	private LiquidTank _tank;

	public static void registerRanchable(IFactoryRanchable ranchable)
	{
		ranchables.put(ranchable.getRanchableEntity(), ranchable);
	}
	
	public TileEntityRancher()
	{
		super(640);
		_areaManager = new HarvestAreaManager(this, 2, 2, 1);
		_tank = new LiquidTank(4 * LiquidContainerRegistry.BUCKET_VOLUME);
	}
	
	@Override
	protected boolean pumpLiquid()
	{
		return true;
	}
	
	@Override
	public ILiquidTank getTank()
	{
		return _tank;
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
	public boolean activateMachine()
	{
		MFRUtil.pumpLiquid(_tank, this);
		
		boolean didDrop = false;
		
		List<?> entities = worldObj.getEntitiesWithinAABB(EntityLiving.class, _areaManager.getHarvestArea().toAxisAlignedBB());
		
		for(Object o : entities)
		{
			if(!(o instanceof EntityLiving))
			{
				if (o instanceof EntityItem)
				{
					MFRUtil.dropStack(this, ((EntityItem)o).func_92014_d());
				}
				continue;
			}
			
			EntityLiving e = (EntityLiving)o;
			if(ranchables.containsKey(e.getClass()))
			{
				IFactoryRanchable r = ranchables.get(e.getClass());
				List<ItemStack> drops = r.ranch(worldObj, e, this);
				if(drops != null)
				{
					for(ItemStack s : drops)
					{
						LiquidStack ls = MineFactoryReloadedCore.instance().getLiquidStackFromLiquidItem(s);
						if (ls != null)
						{
							_tank.fill(ls, true);
							didDrop = true;
							continue;
						}
						
						MFRUtil.dropStack(this, s);
						didDrop = true;
					}
					if(didDrop)
					{
						setIdleTicks(20);
						return true;
					}
				}
			}
		}
		
		setIdleTicks(getIdleTicksMax());
		return false;
	}
	
	@Override
	public String getInvName()
	{
		return "Rancher";
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
}
