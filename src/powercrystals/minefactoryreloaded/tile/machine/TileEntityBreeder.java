package powercrystals.minefactoryreloaded.tile.machine;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.inventory.IInventoryManager;
import powercrystals.core.inventory.InventoryManager;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.IHarvestAreaContainer;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryPowered;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;
import powercrystals.minefactoryreloaded.setup.MFRConfig;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityBreeder extends TileEntityFactoryPowered implements IHarvestAreaContainer
{
	private HarvestAreaManager _areaManager;
	
	public TileEntityBreeder()
	{
		super(Machine.Breeder);
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
	public HarvestAreaManager getHAM()
	{
		return _areaManager;
	}
	
	@Override
	protected boolean activateMachine()
	{
		List<?> entities = worldObj.getEntitiesWithinAABB(EntityLiving.class, _areaManager.getHarvestArea().toAxisAlignedBB());
		
		IInventoryManager manager = InventoryManager.create(this, ForgeDirection.UNKNOWN);
		
		if(entities.size() > MFRConfig.breederShutdownThreshold.getInt())
		{
			setIdleTicks(getIdleTicksMax());
			return false;
		}
		
		for(Object o : entities)
		{
			if(o instanceof EntityAnimal)
			{
				EntityAnimal a = ((EntityAnimal)o);
				
				List<ItemStack> foodList;
				if(MFRRegistry.getBreederFoods().containsKey(a.getClass()))
				{
					foodList = MFRRegistry.getBreederFoods().get(a.getClass());
				}
				else
				{
					foodList = new ArrayList<ItemStack>();
					foodList.add(new ItemStack(Item.wheat));
				}
				for(ItemStack food : foodList)
				{
					int stackIndex = manager.findItem(food);
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
							this.worldObj.spawnParticle("heart", a.posX + a.getRNG().nextFloat() * a.width * 2.0F - a.width, a.posY + 0.5D + a.getRNG().nextFloat() * a.height, a.posZ + a.getRNG().nextFloat() * a.width * 2.0F - a.width, var4, var6, var8);
						}
						return true;
					}
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
	public boolean canRotate()
	{
		return true;
	}
}
