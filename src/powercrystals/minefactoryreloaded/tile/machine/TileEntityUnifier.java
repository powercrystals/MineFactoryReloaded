package powercrystals.minefactoryreloaded.tile.machine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import net.minecraftforge.oredict.OreDictionary;
import powercrystals.core.oredict.OreDictTracker;
import powercrystals.minefactoryreloaded.core.ITankContainerBucketable;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiUnifier;
import powercrystals.minefactoryreloaded.gui.container.ContainerUnifier;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityUnifier extends TileEntityFactoryInventory implements ITankContainerBucketable
{
	private LiquidTank _tank;
	
	private static LiquidStack _biofuel;
	private static LiquidStack _ethanol;
	private static LiquidStack _essence;
	private static LiquidStack _liquidxp;
	private int _roundingCompensation;
	
	private Map<String, ItemStack> _preferredOutputs = new HashMap<String, ItemStack>();
	
	public TileEntityUnifier()
	{
		super(Machine.Unifier);
		_tank = new LiquidTank(LiquidContainerRegistry.BUCKET_VOLUME * 4);
		_roundingCompensation = 1;
	}

	public static void updateUnifierLiquids()
	{
		_biofuel = LiquidDictionary.getLiquid("biofuel", 1);
		_ethanol = LiquidDictionary.getLiquid("ethanol", 1);
		_essence = LiquidDictionary.getLiquid("mobEssence", 1);
		_liquidxp = LiquidDictionary.getLiquid("immibis.liquidxp", 1);
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
		return new GuiUnifier(getContainer(inventoryPlayer), this);
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
				else if(_preferredOutputs.containsKey(names.get(0)))
				{
					output = _preferredOutputs.get(names.get(0)).copy();
					output.stackSize = _inventory[0].stackSize;
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
	protected void onFactoryInventoryChanged()
	{
		_preferredOutputs.clear();
		for(int i = 2; i < 11; i++)
		{
			if(_inventory[i] == null)
			{
				continue;
			}
			List<String> names = OreDictTracker.getNamesFromItem(_inventory[i]);
			if(names != null)
			{
				for(String name : names)
				{
					_preferredOutputs.put(name, _inventory[i].copy());
				}
			}
		}
	}
	
	@Override
	public int getSizeInventory()
	{
		return 11;
	}
	
	@Override
	public boolean shouldDropSlotWhenBroken(int slot)
	{
		return slot < 2;
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
	public int getSizeInventorySide(ForgeDirection side)
	{
		return 11;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int sideordinal)
	{
		if(slot == 0) return true;
		return false;
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int sideordinal)
	{
		if(slot == 1) return true;
		return false;
	}
	
	@Override
	public ILiquidTank getTank()
	{
		return _tank;
	}
	
	@Override
	public boolean allowBucketFill()
	{
		return true;
	}
	
	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
	{
		if(resource == null || resource.amount == 0) return 0;
		
		LiquidStack converted = unifierTransformLiquid(resource, doFill);
		
		if(converted == null || converted.amount == 0) return 0;
		
		int filled = _tank.fill(converted, doFill);
		
		if(filled == converted.amount)
		{
			return resource.amount;
		}
		else
		{
			return filled * resource.amount / converted.amount + (resource.amount & _roundingCompensation);
		}
	}
	
	private LiquidStack unifierTransformLiquid(LiquidStack resource, boolean doFill)
	{
		if(_ethanol != null && _biofuel != null &&
				resource.itemID == _ethanol.itemID && resource.itemMeta == _ethanol.itemMeta)
		{
			return new LiquidStack(_biofuel.itemID, resource.amount, _biofuel.itemMeta);
		}
		else if(_ethanol != null && _biofuel != null &&
				resource.itemID == _biofuel.itemID && resource.itemMeta == _biofuel.itemMeta)
		{
			return new LiquidStack(_ethanol.itemID, resource.amount, _ethanol.itemMeta);
		}
		else if(_essence != null && _liquidxp != null &&
				resource.itemID == _essence.itemID && resource.itemMeta == _essence.itemMeta)
		{
			return new LiquidStack(_liquidxp.itemID, resource.amount * 2, _liquidxp.itemMeta);
		}
		else if(_essence != null && _liquidxp != null &&
				resource.itemID == _liquidxp.itemID && resource.itemMeta == _liquidxp.itemMeta)
		{
			if(doFill)
			{
				_roundingCompensation ^= (resource.amount & 1);
			}
			return new LiquidStack(_essence.itemID, resource.amount / 2 + (resource.amount & _roundingCompensation), _essence.itemMeta);
		}
		
		return null;
	}
	
	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill)
	{
		return fill(ForgeDirection.UNKNOWN, resource, doFill);
	}
	
	@Override
	public boolean allowBucketDrain()
	{
		return true;
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
