package powercrystals.minefactoryreloaded.gui.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;

public class ContainerFactoryInventory extends Container
{
	protected TileEntityFactoryInventory _te;

	private int _tankAmount;
	private int _tankId;
	
	public ContainerFactoryInventory(TileEntityFactoryInventory tileentity, InventoryPlayer inv)
	{
		_te = tileentity;
		if(_te.getSizeInventory() > 0)
		{
			addSlots();
		}
		bindPlayerInventory(inv);
	}
	
	protected void addSlots()
	{
		addSlotToContainer(new Slot((TileEntityFactoryInventory)_te, 0, 8, 15));
		addSlotToContainer(new Slot((TileEntityFactoryInventory)_te, 1, 26, 15));
		addSlotToContainer(new Slot((TileEntityFactoryInventory)_te, 2, 44, 15));
		addSlotToContainer(new Slot((TileEntityFactoryInventory)_te, 3, 8, 33));
		addSlotToContainer(new Slot((TileEntityFactoryInventory)_te, 4, 26, 33));
		addSlotToContainer(new Slot((TileEntityFactoryInventory)_te, 5, 44, 33));
		addSlotToContainer(new Slot((TileEntityFactoryInventory)_te, 6, 8, 51));
		addSlotToContainer(new Slot((TileEntityFactoryInventory)_te, 7, 26, 51));
		addSlotToContainer(new Slot((TileEntityFactoryInventory)_te, 8, 44, 51));
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for(int i = 0; i < crafters.size(); i++)
		{
			if(_te.getTank() != null && _te.getTank().getLiquid() != null)
			{
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 3, _te.getTank().getLiquid().amount);
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 4, _te.getTank().getLiquid().itemID);
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 5, _te.getTank().getLiquid().itemMeta);
			}
			else if(_te.getTank() != null)
			{
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 3, 0);
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 4, 0);
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 5, 0);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int var, int value)
	{
		super.updateProgressBar(var, value);

		if(var == 3) _tankAmount = value;
		else if(var == 4) _tankId = value;
		else if(var == 5) ((LiquidTank)_te.getTank()).setLiquid(new LiquidStack(_tankId, _tankAmount, value));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return ((TileEntityFactoryInventory)_te).isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);
		int machInvSize = ((TileEntityFactoryInventory)_te).getSizeInventory();

		if(slotObject != null && slotObject.getHasStack())
		{
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();
			
			if(slot < machInvSize)
			{
				if(!mergeItemStack(stackInSlot, machInvSize, inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if(!mergeItemStack(stackInSlot, 0, machInvSize, false))
			{
				return null;
			}
			
			if(stackInSlot.stackSize == 0)
			{
				slotObject.putStack(null);
			}
			else
			{
				slotObject.onSlotChanged();
			}
			
			if(stackInSlot.stackSize == stack.stackSize)
			{
				return null;
			}
			
			slotObject.onPickupFromSlot(player, stackInSlot);
		}

		return stack;
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
					addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}
}
