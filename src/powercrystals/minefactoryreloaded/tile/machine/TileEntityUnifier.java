package powercrystals.minefactoryreloaded.tile.machine;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import net.minecraftforge.oredict.OreDictionary;
import powercrystals.core.oredict.OreDictTracker;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerUnifier;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;

public class TileEntityUnifier extends TileEntityFactoryInventory implements ITankContainer
{
	private LiquidTank _tank;
	
	private LiquidStack _biofuel;
	private LiquidStack _ethanol;
	
	public TileEntityUnifier()
	{
		_tank = new LiquidTank(LiquidContainerRegistry.BUCKET_VOLUME * 4);
		_biofuel = LiquidDictionary.getLiquid("biofuel", 1);
		_ethanol = LiquidDictionary.getLiquid("ethanol", 1);
	}
	
	@Override
	public String getGuiBackground()
	{
		return "unifier.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiFactoryInventory(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerUnifier getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerUnifier(this, inventoryPlayer);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(!worldObj.isRemote)
		{
			ItemStack output = null;
			if(_inventory[0] != null)
			{
				List<String> names = OreDictTracker.getNamesFromItem(_inventory[0]);
				
				if(names == null || names.size() != 1)
				{
					output = _inventory[0].copy();
				}
				else
				{
					output = OreDictionary.getOres(names.get(0)).get(0).copy();
					output.stackSize = _inventory[0].stackSize;
				}

				moveItemStack(output);
			}
		}
	}
	
	private void moveItemStack(ItemStack source)
	{
		if(source == null)
		{
			return;
		}

		int amt = source.stackSize;
		
		if(_inventory[1] == null)
		{
			amt = Math.min(getInventoryStackLimit(), source.getMaxStackSize());
		}
		else if(source.itemID != _inventory[1].itemID || source.getItemDamage() != _inventory[1].getItemDamage())
		{
			return;
		}
		else if(source.getTagCompound() != null || _inventory[1].getTagCompound() != null)
		{
			return;
		}
		else
		{
			amt = Math.min(_inventory[0].stackSize, _inventory[1].getMaxStackSize() - _inventory[1].stackSize);
		}
		
		if(_inventory[1] == null)
		{
			_inventory[1] = source.copy();
			_inventory[0].stackSize -= source.stackSize;
		}
		else
		{
			_inventory[1].stackSize += amt;
			_inventory[0].stackSize -= amt;
		}
		
		if(_inventory[0].stackSize == 0)
		{
			_inventory[0] = null;
		}
	}

	@Override
	public int getSizeInventory()
	{
		return 2;
	}

	@Override
	public String getInvName()
	{
		return "Unifier";
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public int getStartInventorySide(ForgeDirection side)
	{
		if(side.ordinal() < 2) return 0;
		return 1;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side)
	{
		return 1;
	}

	@Override
	public ILiquidTank getTank()
	{
		return _tank;
	}
	
	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
	{
		if(resource != null && resource.itemID == _ethanol.itemID && resource.itemMeta == _ethanol.itemMeta)
		{
			return _tank.fill(new LiquidStack(_biofuel.itemID, resource.amount, _biofuel.itemMeta), doFill);
		}
		return 0;
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill)
	{
		return fill(ForgeDirection.UNKNOWN, resource, doFill);
	}

	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		return _tank.drain(maxDrain, doDrain);
	}

	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain)
	{
		return _tank.drain(maxDrain, doDrain);
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
}
