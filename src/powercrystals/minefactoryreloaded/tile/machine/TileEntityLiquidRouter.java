package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiLiquidRouter;
import powercrystals.minefactoryreloaded.gui.container.ContainerLiquidRouter;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityLiquidRouter extends TileEntityFactoryInventory implements ITankContainer
{
	private LiquidTank[] _bufferTanks = new LiquidTank[6];
	private static final ForgeDirection[] _outputDirections = new ForgeDirection[]
			{ ForgeDirection.DOWN, ForgeDirection.UP, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST };
	
	public TileEntityLiquidRouter()
	{
		super(Machine.LiquidRouter);
		for(int i = 0; i < 6; i++)
		{
			_bufferTanks[i] = new LiquidTank(LiquidContainerRegistry.BUCKET_VOLUME);
			_bufferTanks[i].setTankPressure(-1);
		}
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		for(int i = 0; i < 6; i++)
		{
			if(_bufferTanks[i].getLiquid() != null && _bufferTanks[i].getLiquid().amount > 0)
			{
				_bufferTanks[i].getLiquid().amount -= pumpLiquid(_bufferTanks[i].getLiquid(), true);
			}
		}
	}
	
	private int pumpLiquid(LiquidStack resource, boolean doFill)
	{
		if(resource == null || resource.itemID <= 0 || resource.amount <= 0) return 0;
		
		int amountRemaining = resource.amount;
		int[] routes = getRoutesForLiquid(resource);
		int[] defaultRoutes = getDefaultRoutes();
		
		if(hasRoutes(routes))
		{
			amountRemaining = weightedRouteLiquid(resource, routes, amountRemaining, doFill);
		}
		else if(hasRoutes(defaultRoutes))
		{
			amountRemaining = weightedRouteLiquid(resource, defaultRoutes, amountRemaining, doFill);
		}
		
		return resource.amount - amountRemaining;
	}
	
	private int weightedRouteLiquid(LiquidStack resource, int[] routes, int amountRemaining, boolean doFill)
	{
		if(amountRemaining >= totalWeight(routes))
		{
			int startingAmount = amountRemaining;
			for(int i = 0; i < routes.length; i++)
			{
				TileEntity te = BlockPosition.getAdjacentTileEntity(this, _outputDirections[i]);
				int amountForThisRoute = startingAmount * routes[i] / totalWeight(routes);
				if(te != null && te instanceof ITankContainer && amountForThisRoute > 0)
				{
					amountRemaining -= ((ITankContainer)te).fill(_outputDirections[i].getOpposite(),	new LiquidStack(resource.itemID, amountForThisRoute, resource.itemMeta), doFill);
					if(amountRemaining <= 0)
					{
						break;
					}
				}
			}
		}
		
		if(0 < amountRemaining && amountRemaining < totalWeight(routes))
		{
			int outdir = weightedRandomSide(routes);
			TileEntity te = BlockPosition.getAdjacentTileEntity(this, _outputDirections[outdir]);
			if(te != null && te instanceof ITankContainer)
			{
				amountRemaining -= ((ITankContainer)te).fill(_outputDirections[outdir].getOpposite(),	new LiquidStack(resource.itemID, amountRemaining, resource.itemMeta), doFill);
			}
		}
		
		return amountRemaining;
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
	
	
	private int[] getRoutesForLiquid(LiquidStack resource)
	{
		int[] routeWeights = new int[6];
		
		for(int i = 0; i < 6; i++)
		{
			if(LiquidContainerRegistry.containsLiquid(_inventory[i], resource))
			{
				routeWeights[i] = _inventory[i].stackSize;
			}
			else
			{
				routeWeights[i] = 0;
			}
		}
		return routeWeights;
	}
	
	private int[] getDefaultRoutes()
	{
		int[] routeWeights = new int[6];
		
		for(int i = 0; i < 6; i++)
		{
			if(LiquidContainerRegistry.isEmptyContainer(_inventory[i]))
			{
				routeWeights[i] = _inventory[i].stackSize;
			}
			else
			{
				routeWeights[i] = 0;
			}
		}
		return routeWeights;
	}
	
	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
	{
		return pumpLiquid(resource, doFill);
	}
	
	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill)
	{
		return pumpLiquid(resource, doFill);
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
		return new ILiquidTank[] { _bufferTanks[direction.ordinal()] };
	}
	
	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type)
	{
		if(LiquidContainerRegistry.containsLiquid(_inventory[direction.ordinal()], type))
		{
			return _bufferTanks[direction.ordinal()];
		}
		return null;
	}
	
	@Override
	public int getSizeInventory()
	{
		return 6;
	}
	
	@Override
	public boolean shouldDropSlotWhenBroken(int slot)
	{
		return false;
	}
	
	@Override
	public String getInvName()
	{
		return "Liquid Router";
	}
	
	@Override
	public String getGuiBackground()
	{
		return "liquidrouter.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiLiquidRouter(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerLiquidRouter getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerLiquidRouter(this, inventoryPlayer);
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side)
	{
		return false;
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side)
	{
		return false;
	}
}
