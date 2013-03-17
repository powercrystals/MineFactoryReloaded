package powercrystals.minefactoryreloaded.tile.machine;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryPowered;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

public class TileEntityBreeder extends TileEntityFactoryPowered
{
	private HarvestAreaManager _areaManager;
	
	public TileEntityBreeder()
	{
		super(640);
		_areaManager = new HarvestAreaManager(this, 2, 2, 1);
	}
	
	@Override
	public String getGuiBackground()
	{
		return "breeder.png";
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
		List<?> entities = worldObj.getEntitiesWithinAABB(EntityLiving.class, _areaManager.getHarvestArea().toAxisAlignedBB());
		
		for(Object o : entities)
		{
			if(o != null && o instanceof EntityAnimal)
			{
				EntityAnimal a = ((EntityAnimal)o);
				ItemStack food = MFRRegistry.getBreederFoods().get(a.getClass());
				if(food == null)
				{
					food = new ItemStack(Item.wheat);
				}
				int stackIndex = UtilInventory.findFirstStack(this, food.itemID, food.getItemDamage());
				if(stackIndex < 0)
				{
					continue;
				}
				
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
