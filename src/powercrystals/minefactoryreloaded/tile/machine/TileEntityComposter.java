package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.ITankContainerBucketable;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryPowered;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityComposter extends TileEntityFactoryPowered implements ITankContainerBucketable
{
	private LiquidTank _tank;
	
	public TileEntityComposter()
	{
		super(Machine.Composter);
		_tank = new LiquidTank(4 * LiquidContainerRegistry.BUCKET_VOLUME);
	}
	
	@Override
	public String getGuiBackground()
	{
		return "composter.png";
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
	public ILiquidTank getTank()
	{
		return _tank;
	}
	
	@Override
	protected boolean activateMachine()
	{
		if(_tank.getLiquid() != null && _tank.getLiquid().amount >= 20)
		{
			setWorkDone(getWorkDone() + 1);
			
			if(getWorkDone() >= getWorkMax())
			{
				doDrop(new ItemStack(MineFactoryReloadedCore.fertilizerItem));
				setWorkDone(0);
			}
			_tank.drain(20, true);
			return true;
		}
		return false;
	}
	
	@Override
	public ForgeDirection getDropDirection()
	{
		return ForgeDirection.UP;
	}
	
	@Override
	public int getEnergyStoredMax()
	{
		return 16000;
	}
	
	@Override
	public int getWorkMax()
	{
		return 100;
	}
	
	@Override
	public int getIdleTicksMax()
	{
		return 1;
	}
	
	@Override
	public boolean allowBucketFill()
	{
		return true;
	}
	
	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
	{
		if(resource == null || (resource.itemID != LiquidDictionary.getCanonicalLiquid("sewage").itemID))
		{
			return 0;
		}
		else
		{
			return _tank.fill(resource, doFill);
		}
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
		if(type != null && type.itemID == LiquidDictionary.getCanonicalLiquid("sewage").itemID)
		{
			return _tank;
		}
		return null;
	}
	
	@Override
	public String getInvName()
	{
		return "Composter";
	}
	
	@Override
	public int getSizeInventory()
	{
		return 0;
	}
	
	@Override
	public boolean manageSolids()
	{
		return true;
	}
}
