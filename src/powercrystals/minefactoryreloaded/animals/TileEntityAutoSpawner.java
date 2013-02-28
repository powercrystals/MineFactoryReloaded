package powercrystals.minefactoryreloaded.animals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryInventory;

public class TileEntityAutoSpawner extends TileEntityFactoryInventory implements ITankContainer
{
	private static int _spawnRange = 4;
	private LiquidTank _tank;
	
	public TileEntityAutoSpawner()
	{
		super(1408);
		_tank = new LiquidTank(LiquidContainerRegistry.BUCKET_VOLUME * 4);
	}
	
	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public String getInvName()
	{
		return "Auto-Spawner";
	}

	@Override
	protected boolean activateMachine()
	{
		if(_inventory[0] == null || !(_inventory[0].getItem() instanceof ItemSafariNet) || _inventory[0].getTagCompound() == null)
		{
			return false;	
		}
		if(getWorkDone() < getWorkMax())
		{
			if(_tank.getLiquid() != null && _tank.getLiquid().amount >= 10)
			{
				_tank.getLiquid().amount -= 10;
			}
			setWorkDone(getWorkDone() + 1);
		}
		else
		{
			Entity spawnedEntity = EntityList.createEntityByName((String)EntityList.classToStringMapping.get(_inventory[0].getTagCompound().getString("_class")), worldObj);
			if(!(spawnedEntity instanceof EntityLiving))
			{
				return false;
			}
			EntityLiving spawnedLiving = (EntityLiving)spawnedEntity;
			
			double x = xCoord + (worldObj.rand.nextDouble() - worldObj.rand.nextDouble()) * _spawnRange;
			double y = yCoord + worldObj.rand.nextInt(3) - 1;
			double z = zCoord + (worldObj.rand.nextDouble() - worldObj.rand.nextDouble()) * _spawnRange;
			
			if(!this.worldObj.checkIfAABBIsClear(spawnedLiving.boundingBox) ||
					!this.worldObj.getCollidingBoundingBoxes(spawnedLiving, spawnedLiving.boundingBox).isEmpty() ||
					this.worldObj.isAnyLiquid(spawnedLiving.boundingBox))
			{
				return false;
			}
			
			spawnedLiving.setLocationAndAngles(x, y, z, worldObj.rand.nextFloat() * 360.0F, 0.0F);

			worldObj.spawnEntityInWorld(spawnedLiving);
			worldObj.playAuxSFX(2004, this.xCoord, this.yCoord, this.zCoord, 0);

			spawnedLiving.spawnExplosionParticle();
		}
		return false;
	}

	@Override
	public int getEnergyStoredMax()
	{
		return 32767;
	}

	@Override
	public int getWorkMax()
	{
		return 15;
	}

	@Override
	public int getIdleTicksMax()
	{
		return 1000;
	}
	
	@Override
	public ILiquidTank getTank()
	{
		return _tank;
	}

	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
	{
		if(resource == null || resource.itemID != LiquidDictionary.getLiquid("mobEssence", 1000).itemID)
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
}
