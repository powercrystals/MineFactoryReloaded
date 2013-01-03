package powercrystals.minefactoryreloaded.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryInventory;

public class ContainerFactoryInventory extends ContainerFactoryPowered
{
	public ContainerFactoryInventory(TileEntityFactoryInventory tileentity, InventoryPlayer inv)
	{
		super(tileentity, inv);
		_te = tileentity;
	}
	
	@Override
	protected void addSlots(InventoryPlayer inv)
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
		
		bindPlayerInventory(inv);
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
}
