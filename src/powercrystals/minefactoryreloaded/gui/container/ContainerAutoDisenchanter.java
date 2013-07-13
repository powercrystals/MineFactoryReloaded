package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.gui.slot.SlotRemoveOnly;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoDisenchanter;

public class ContainerAutoDisenchanter extends ContainerFactoryPowered
{
	private TileEntityAutoDisenchanter _disenchanter;
	
	public ContainerAutoDisenchanter(TileEntityAutoDisenchanter disenchanter, InventoryPlayer inv)
	{
		super(disenchanter, inv);
		_disenchanter = disenchanter;
	}
	
	@Override
	protected void addSlots()
	{
		addSlotToContainer(new Slot(_te, 0, 8, 24));
		addSlotToContainer(new Slot(_te, 1, 26, 24));
		addSlotToContainer(new SlotRemoveOnly(_te, 2, 8, 54));
		addSlotToContainer(new SlotRemoveOnly(_te, 3, 26, 54));
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < crafters.size(); i++)
		{
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 100, _disenchanter.getRepeatDisenchant() ? 1 : 0);
		}
	}
	
	@Override
	public void updateProgressBar(int var, int value)
	{
		super.updateProgressBar(var, value);
		if(var == 100) _disenchanter.setRepeatDisenchant(value == 1 ? true : false);
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
			
			if(slot < 4)
			{
				if(!mergeItemStack(stackInSlot, 4, inventorySlots.size(), true))
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
}
