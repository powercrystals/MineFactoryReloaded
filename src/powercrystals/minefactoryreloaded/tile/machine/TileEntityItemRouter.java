package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiItemRouter;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerItemRouter;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityItemRouter extends TileEntityFactoryInventory
{
	public TileEntityItemRouter()
	{
		super(Machine.ItemRouter);
	}

	public TileEntityItemRouter(Machine machine)
	{
		super(machine);
	}

	protected static final int[] _invOffsets = new int[] { 0, 0, 9, 18, 36, 27 };
	protected static final ForgeDirection[] _outputDirections = new ForgeDirection[]
			{ ForgeDirection.DOWN, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST };
	private int[] _defaultRoutes = new int[_outputDirections.length];
	
	private boolean _rejectUnmapped;
	
	public boolean getRejectUnmapped()
	{
		return _rejectUnmapped;
	}
	
	public void setRejectUnmapped(boolean rejectUnmapped)
	{
		_rejectUnmapped = rejectUnmapped;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(!worldObj.isRemote)
		{
			for(int i = 45; i < getSizeInventory(); i++)
			{
				if(_inventory[i] != null)
				{
					_inventory[i] = routeItem(_inventory[i]);
				}
			}
		}
	}
	
	public ItemStack routeItem(ItemStack stack)
	{
		int[] filteredRoutes = getRoutesForItem(stack);
		
		if(hasRoutes(filteredRoutes))
		{
			stack = weightedRouteItem(stack, filteredRoutes);
			return (stack == null || stack.stackSize == 0) ? null : stack;
		}
		else if(hasRoutes(_defaultRoutes) && !_rejectUnmapped)
		{
			stack = weightedRouteItem(stack, _defaultRoutes);
			return (stack == null || stack.stackSize == 0) ? null : stack;
		}
		return stack;
	}
	
	private ItemStack weightedRouteItem(ItemStack stack, int[] routes)
	{
		ItemStack remainingOverall = stack.copy();
		if(stack.stackSize >= totalWeight(routes))
		{
			int startingAmount = stack.stackSize;
			for(int i = 0; i < routes.length; i++)
			{
				ItemStack stackForThisRoute = stack.copy();
				stackForThisRoute.stackSize = startingAmount * routes[i] / totalWeight(routes);
				if(stackForThisRoute.stackSize > 0)
				{
					ItemStack remainingFromThisRoute = UtilInventory.dropStack(this, stackForThisRoute, _outputDirections[i], _outputDirections[i]);
					if(remainingFromThisRoute == null)
					{
						remainingOverall.stackSize -= stackForThisRoute.stackSize;
					}
					else
					{
						remainingOverall.stackSize -= (stackForThisRoute.stackSize - remainingFromThisRoute.stackSize);
					}
					
					if(remainingOverall.stackSize <= 0)
					{
						break;
					}
				}
			}
		}
		
		if(0 < remainingOverall.stackSize && remainingOverall.stackSize < totalWeight(routes))
		{
			int outdir = weightedRandomSide(routes);
			remainingOverall = UtilInventory.dropStack(this, remainingOverall, _outputDirections[outdir], _outputDirections[outdir]);
		}
		return remainingOverall;
	}
	
	private int weightedRandomSide(int[] routeWeights)
	{
		int random = worldObj.rand.nextInt(totalWeight(routeWeights));
		for(int i = 0; i < routeWeights.length; i++)
		{
			random -= routeWeights[i];
			if(random < 0)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	private int totalWeight(int[] routeWeights)
	{
		int total = 0;
		
		for(int weight : routeWeights)
		{
			total += weight;
		}
		return total;
	}
	
	private boolean hasRoutes(int[] routeWeights)
	{
		for(int weight : routeWeights)
		{
			if(weight > 0) return true;
		}
		return false;
	}
	
	
	protected int[] getRoutesForItem(ItemStack stack)
	{
		int[] routeWeights = new int[_outputDirections.length];
		
		for(int i = 0; i < _outputDirections.length; i++)
		{
			int sideStart = _invOffsets[_outputDirections[i].ordinal()];
			routeWeights[i] = 0;
			
			for(int j = sideStart; j < sideStart + 9; j++)
			{
				if(_inventory[j] != null)
				{
					if(_inventory[j].itemID == stack.itemID && (_inventory[j].getItemDamage() == stack.getItemDamage()) || stack.getItem().isDamageable())
					{
						routeWeights[i] += _inventory[j].stackSize;
					}
				}
			}
		}
		return routeWeights;
	}
	
	private void recalculateDefaultRoutes()
	{
		for(int i = 0; i < _outputDirections.length; i++)
		{
			_defaultRoutes[i] = isSideEmpty(_outputDirections[i]) ? 1 : 0;
		}
	}
	
	public boolean hasRouteForItem(ItemStack stack)
	{
		return hasRoutes(getRoutesForItem(stack));
	}
	
	private boolean isSideEmpty(ForgeDirection side)
	{
		if(side == ForgeDirection.UNKNOWN || side == ForgeDirection.UP)
		{
			return false;
		}
		
		int sideStart = _invOffsets[side.ordinal()];
		
		for(int i = sideStart; i < sideStart + 9; i++)
		{
			if(_inventory[i] != null)
			{
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public int getSizeInventory()
	{
		return 48;
	}
	
	@Override
	public boolean shouldDropSlotWhenBroken(int slot)
	{
		return slot >= 45;
	}
	
	@Override
	public String getInvName()
	{
		return "Item Router";
	}
	
	@Override
	public String getGuiBackground()
	{
		return "itemrouter.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiItemRouter(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerFactoryInventory getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerItemRouter(this, inventoryPlayer);
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public int getStartInventorySide(ForgeDirection side)
	{
		return 45;
	}
	
	@Override
	public int getSizeInventorySide(ForgeDirection side)
	{
		return 3;
	}
	
	@Override
	protected void onFactoryInventoryChanged()
	{
		recalculateDefaultRoutes();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		_rejectUnmapped = nbttagcompound.getBoolean("rejectUnmapped");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("rejectUnmapped", _rejectUnmapped);
	}
}
