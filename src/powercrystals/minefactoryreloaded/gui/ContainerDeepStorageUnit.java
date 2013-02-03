package powercrystals.minefactoryreloaded.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.processing.TileEntityDeepStorageUnit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDeepStorageUnit extends Container
{
	private TileEntityDeepStorageUnit _dsu;
	private int _tempQuantity;
	
	public ContainerDeepStorageUnit(TileEntityDeepStorageUnit dsu, InventoryPlayer inventoryPlayer)
	{
		_dsu = dsu;
		addSlotToContainer(new Slot(dsu, 0, 134, 16));
		addSlotToContainer(new Slot(dsu, 1, 152, 16));
		addSlotToContainer(new SlotRemoveOnly(dsu, 2, 152, 49));
		
		bindPlayerInventory(inventoryPlayer);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return _dsu.isUseableByPlayer(player);
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
					addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 124 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 182));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		if(slotObject != null && slotObject.getHasStack())
		{
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();
			
			if(slot < 3)
			{
				if(!mergeItemStack(stackInSlot, 3, inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if(!mergeItemStack(stackInSlot, 0, 2, false))
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
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < crafters.size(); i++)
		{
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 0, _dsu.getIsSideOutput(0) ? 1 : 0);
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 1, _dsu.getIsSideOutput(1) ? 1 : 0);
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 2, _dsu.getIsSideOutput(2) ? 1 : 0);
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 3, _dsu.getIsSideOutput(3) ? 1 : 0);
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 4, _dsu.getIsSideOutput(4) ? 1 : 0);
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 5, _dsu.getIsSideOutput(5) ? 1 : 0);

			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 100, _dsu.getQuantity());
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 101, _dsu.getQuantity() >> 16);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int var, int value)
	{
		super.updateProgressBar(var, value);
		
		if(var == 0) _dsu.setSideIsOutput(0, value == 1);
		if(var == 1) _dsu.setSideIsOutput(1, value == 1);
		if(var == 2) _dsu.setSideIsOutput(2, value == 1);
		if(var == 3) _dsu.setSideIsOutput(3, value == 1);
		if(var == 4) _dsu.setSideIsOutput(4, value == 1);
		if(var == 5) _dsu.setSideIsOutput(5, value == 1);
		
		if(var == 100) _tempQuantity = upcastShort(value);
		if(var == 101) _dsu.setQuantity(_tempQuantity | (value << 16));
	}
	
	private int upcastShort(int value)
	{
		if(value < 0) value += 65536;
		return value;
	}
}
