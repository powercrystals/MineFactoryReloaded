package powercrystals.minefactoryreloaded.processing;

import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;
import powercrystals.minefactoryreloaded.core.UtilInventory;
import powercrystals.minefactoryreloaded.core.UtilLiquid;

import buildcraft.core.IMachine;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;

public class TileEntityWeather extends TileEntityFactoryPowered implements IMachine
{	
	private LiquidTank _tank;
	
	public TileEntityWeather()
	{
		super(80);
		_tank = new LiquidTank(4 * LiquidContainerRegistry.BUCKET_VOLUME);
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
		return 64000;
	}

	@Override
	public int getWorkMax()
	{
		return 50;
	}

	@Override
	public int getIdleTicksMax()
	{
		return 600;
	}

	@Override
	public boolean activateMachine()
	{
		UtilLiquid.pumpLiquid(_tank, this);
		
		if(worldObj.getWorldInfo().isRaining() && canSeeSky())
		{
			BiomeGenBase bgb = worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord);
			
			if(!bgb.canSpawnLightningBolt() && !bgb.getEnableSnow())
			{
				setIdleTicks(getIdleTicksMax());
				return false;
			}
			setWorkDone(getWorkDone() + 1);
			if(getWorkDone() >= getWorkMax())
			{
				setWorkDone(0);
				if(worldObj.getWorldChunkManager().getTemperatureAtHeight(bgb.getFloatTemperature(), worldObj.getPrecipitationHeight(this.xCoord, this.zCoord)) >= 0.15F)
				{
					_tank.fill(new LiquidStack(Block.waterStill.blockID, LiquidContainerRegistry.BUCKET_VOLUME), true);
				}
				else
				{
					UtilInventory.dropStack(this, new ItemStack(Item.snowball));
				}
			}
			return true;
		}
		setIdleTicks(getIdleTicksMax());
		return false;
	}
	
	@Override
	public ForgeDirection getDropDirection()
	{
		return ForgeDirection.DOWN;
	}
	
	private boolean canSeeSky()
	{
		for(int y = yCoord + 1; y <= 256; y++)
		{
			int blockId = worldObj.getBlockId(xCoord, y, zCoord);
			if(Block.blocksList[blockId] != null && !Block.blocksList[blockId].isAirBlock(worldObj, xCoord, y, zCoord))
			{
				return false;
			}
		}
		return true;
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
		return "Weather Collector";
	}
}
