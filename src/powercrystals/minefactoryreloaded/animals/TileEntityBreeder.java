package powercrystals.minefactoryreloaded.animals;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.Item;

import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;

public class TileEntityBreeder extends TileEntityFactoryPowered
{
	private HarvestAreaManager _areaManager;
	
	public TileEntityBreeder()
	{
		super(640);
		_areaManager = new HarvestAreaManager(this, 2, 2, 1);
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
	protected boolean activateMachine()
	{
		int stackIndex = UtilInventory.findFirstStack(this, Item.wheat.itemID, 0);
		if(stackIndex < 0)
		{
			return false;
		}
		
		List<?> entities = worldObj.getEntitiesWithinAABB(EntityLiving.class, _areaManager.getHarvestArea().toAxisAlignedBB());
		
		for(Object o : entities)
		{
			if(o != null && o instanceof EntityAnimal)
			{
				EntityAnimal a = ((EntityAnimal)o);
				if(!a.isInLove() && a.getGrowingAge() == 0)
				{
					a.inLove = 600;
					decrStackSize(stackIndex, 1);
					
					for (int var3 = 0; var3 < 7; ++var3)
					{
						double var4 = a.getRNG().nextGaussian() * 0.02D;
						double var6 = a.getRNG().nextGaussian() * 0.02D;
						double var8 = a.getRNG().nextGaussian() * 0.02D;
						this.worldObj.spawnParticle("heart", a.posX + (double)(a.getRNG().nextFloat() * a.width * 2.0F) - (double)a.width, a.posY + 0.5D + (double)(a.getRNG().nextFloat() * a.height), a.posZ + (double)(a.getRNG().nextFloat() * a.width * 2.0F) - (double)a.width, var4, var6, var8);
					}
					return true;
				}
			}
		}
		setIdleTicks(getIdleTicksMax());
		return false;
	}
	
	@Override
	public int getSizeInventory()
	{
		return 9;
	}

	@Override
	public String getInvName()
	{
		return "Breeder";
	}
}
