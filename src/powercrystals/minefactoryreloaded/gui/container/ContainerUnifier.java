package powercrystals.minefactoryreloaded.gui.container;

import powercrystals.minefactoryreloaded.gui.slot.SlotRemoveOnly;
import powercrystals.minefactoryreloaded.processing.TileEntityUnifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerUnifier extends ContainerFactoryInventory
{
	private TileEntityUnifier _unifier;
	
	public ContainerUnifier(TileEntityUnifier tileentity, InventoryPlayer inv)
	{
		super(tileentity, inv);
		_unifier = tileentity;
	}
	
	@Override
	protected void addSlots()
	{
		addSlotToContainer(new Slot(_unifier, 0, 8, 24));
		addSlotToContainer(new SlotRemoveOnly(_unifier, 1, 8, 54));
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);
		int machInvSize = ((TileEntityUnifier)_unifier).getSizeInventory();

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
