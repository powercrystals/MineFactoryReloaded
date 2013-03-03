package powercrystals.minefactoryreloaded.animals;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import powercrystals.minefactoryreloaded.api.ISyringe;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;

public class TileEntityVet extends TileEntityFactoryPowered
{
	private HarvestAreaManager _areaManager;
	
	public TileEntityVet()
	{
		super(320);
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
	public boolean activateMachine()
	{
		List<?> entities = worldObj.getEntitiesWithinAABB(EntityLiving.class, _areaManager.getHarvestArea().toAxisAlignedBB());
		for(Object o : entities)
		{
			if(!(o instanceof EntityLiving) || o instanceof EntityPlayer || o instanceof EntityMob)
			{
				continue;
			}
			EntityLiving e = (EntityLiving)o;
			
			for(int i = 0; i < getSizeInventory(); i++)
			{
				ItemStack s = getStackInSlot(i);
				if(s != null && s.getItem() instanceof ISyringe)
				{
					if(((ISyringe)s.getItem()).canInject(worldObj, e, s))
					{
						if(((ISyringe)s.getItem()).inject(worldObj, e, s))
						{
							return true;
						}
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
		return "Veterinary";
	}
	
	@Override
	public int getSizeInventory()
	{
		return 9;
	}
}
