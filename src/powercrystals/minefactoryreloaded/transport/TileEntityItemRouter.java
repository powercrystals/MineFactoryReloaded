package powercrystals.minefactoryreloaded.transport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryInventory;

public class TileEntityItemRouter extends TileEntityFactoryInventory implements ISidedInventory
{
	private static final int[] _invOffsets = new int[] { 0, 0, 9, 18, 36, 27 };
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
				if(_inventory[i] != null && routeItem(_inventory[i]))
				{
					_inventory[i] = null;
				}
			}
		}
	}
	
	@Override
	public boolean canRotate()
	{
		return false;
	}
	
	public boolean routeItem(ItemStack stack)
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
			MFRUtil.dropStackDirected(this, stack, filteredOutputs.get(_rand.nextInt(filteredOutputs.size())));
			return true;
		}
		else if(emptyOutputs.size() > 0)
		{
			MFRUtil.dropStackDirected(this, stack, emptyOutputs.get(_rand.nextInt(emptyOutputs.size())));
			return true;
		}
		return false;
	}
	
	private boolean isSideValidForItem(ItemStack stack, ForgeDirection side)
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
