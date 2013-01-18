package powercrystals.minefactoryreloaded.animals;

import java.util.List;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryInventory;

public class TileEntityVet extends TileEntityFactoryInventory
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
		return 64000;
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
			if (e.getHealth() < e.getMaxHealth())
			{
				for(int i = 0; i < getSizeInventory(); i++)
				{
					ItemStack s = getStackInSlot(i);
					if(s != null && s.itemID == MineFactoryReloadedCore.syringeHealthItem.shiftedIndex)
					{
						e.heal(2);
						s.itemID = MineFactoryReloadedCore.syringeEmptyItem.shiftedIndex;
						
						return true;
					}
				}
			}
			else if(e instanceof EntityAgeable && ((EntityAgeable)e).getGrowingAge() < 0)
			{
				for(int i = 0; i < getSizeInventory(); i++)
				{
					ItemStack s = getStackInSlot(i);
					if(s != null && s.itemID == MineFactoryReloadedCore.syringeGrowthItem.shiftedIndex)
					{
						((EntityAgeable)e).setGrowingAge(0);
						s.itemID = MineFactoryReloadedCore.syringeEmptyItem.shiftedIndex;
						
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
		return "Veterinary";
	}
}
