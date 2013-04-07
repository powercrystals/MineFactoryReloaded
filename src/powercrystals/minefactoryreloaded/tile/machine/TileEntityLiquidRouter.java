package powercrystals.minefactoryreloaded.tile.machine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
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
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;

public class TileEntityLiquidRouter extends TileEntityFactoryInventory implements ITankContainer
{
	private LiquidTank[] _bufferTanks = new LiquidTank[6];
	private static final ForgeDirection[] _outputDirections = new ForgeDirection[]
			{ ForgeDirection.DOWN, ForgeDirection.UP, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST };

	public TileEntityLiquidRouter()
	{
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
		
		for(int i = 0; i < 6; i++)
		{
			if(LiquidContainerRegistry.containsLiquid(_inventory[i], resource))
			{
				TileEntity te = BlockPosition.getAdjacentTileEntity(this, _outputDirections[i]);
				if(te != null && te instanceof ITankContainer)
				{
					amountRemaining -= ((ITankContainer)te).fill(_outputDirections[i].getOpposite(),	new LiquidStack(resource.itemID, amountRemaining, resource.itemMeta), doFill);
					if(amountRemaining <= 0)
					{
						break;
					}
				}
			}
		}
		
		return resource.amount - amountRemaining;
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
}
