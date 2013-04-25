package powercrystals.minefactoryreloaded.tile.machine;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryPowered;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

public class TileEntitySlaughterhouse extends TileEntityFactoryPowered implements ITankContainer
{
	private HarvestAreaManager _areaManager;
	private LiquidTank _tank;
	
	public TileEntitySlaughterhouse()
	{
		super(Machine.Slaughterhouse);
		_areaManager = new HarvestAreaManager(this, 2, 2, 1);
		_tank = new LiquidTank(4 * LiquidContainerRegistry.BUCKET_VOLUME);
	}

	@Override
	public int getSizeInventory()
	{
		return 0;
	}

	@Override
	public String getInvName()
	{
		return "Slaughterhouse";
	}
	
	@Override
	public String getGuiBackground()
	{
		return "slaughterhouse.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiFactoryPowered(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerFactoryPowered getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerFactoryPowered(this, inventoryPlayer);
	}

	@Override
	protected boolean activateMachine()
	{
		double massFound = 0;
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
			massFound = Math.pow(e.boundingBox.getAverageEdgeLength(), 2);
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
			break;
		}
		
		if(massFound == 0)
		{
			setIdleTicks(getIdleTicksMax());
			return false;
		}
		else
		{
			if(worldObj.rand.nextInt(8) == 0)
			{
				_tank.fill(LiquidDictionary.getLiquid("pinkslime", (int)(100 * massFound)), true);
			}
			else
			{
				_tank.fill(LiquidDictionary.getLiquid("meat", (int)(100 * massFound)), true);
			}
			setIdleTicks(10);
			return true;
		}
	}

	@Override
	public int getEnergyStoredMax()
	{
		return 16000;
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
	public boolean canRotate()
	{
		return true;
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
	public boolean allowBucketDrain()
	{
		return true;
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
	public ILiquidTank getTank()
	{
		return _tank;
	}
	
	@Override
	protected boolean shouldPumpLiquid()
	{
		return true;
	}
}
