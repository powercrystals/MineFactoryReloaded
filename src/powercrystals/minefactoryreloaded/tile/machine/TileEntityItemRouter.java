package powercrystals.minefactoryreloaded.tile.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiItemRouter;
import powercrystals.minefactoryreloaded.gui.container.ContainerItemRouter;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;

public class TileEntityItemRouter extends TileEntityFactoryInventory
{
	protected static final int[] _invOffsets = new int[] { 0, 0, 9, 18, 36, 27 };
	private static final ForgeDirection[] _outputDirections = new ForgeDirection[]
			{ ForgeDirection.DOWN, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST };
	private Random _rand;
	
	public TileEntityItemRouter()
	{
		_rand = new Random();
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
		List<ForgeDirection> filteredOutputs = new ArrayList<ForgeDirection>();
		List<ForgeDirection> emptyOutputs = new ArrayList<ForgeDirection>();
		for(ForgeDirection d : _outputDirections)
		{
			if(isSideValidForItem(stack, d))
			{
				filteredOutputs.add(d);
			}
			if(isSideEmpty(d))
			{
				emptyOutputs.add(d);
			}
		}
		
		if(filteredOutputs.size() > 0)
		{
			ForgeDirection outputside = filteredOutputs.get(_rand.nextInt(filteredOutputs.size()));
			stack = UtilInventory.dropStack(this, stack, outputside, outputside);
			return (stack == null || stack.stackSize == 0) ? null : stack;
		}
		else if(emptyOutputs.size() > 0)
		{
			ForgeDirection outputside = emptyOutputs.get(_rand.nextInt(emptyOutputs.size()));
			stack = UtilInventory.dropStack(this, stack, outputside, outputside);
			return (stack == null || stack.stackSize == 0) ? null : stack;
		}
		return stack;
	}
	
	public boolean hasRouteForItem(ItemStack stack)
	{
		for(ForgeDirection d : ForgeDirection.VALID_DIRECTIONS)
		{
			if(isSideValidForItem(stack, d))
			{
				return true;
			}
		}
		
		return false;
	}
	
	protected boolean isSideValidForItem(ItemStack stack, ForgeDirection side)
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
				if(_inventory[i].itemID == stack.itemID && (_inventory[i].getItemDamage() == stack.getItemDamage()) || stack.getItem().isDamageable())
				{
					return true;
				}
			}
		}
		
		return false;
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
	public ContainerItemRouter getContainer(InventoryPlayer inventoryPlayer)
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
}
