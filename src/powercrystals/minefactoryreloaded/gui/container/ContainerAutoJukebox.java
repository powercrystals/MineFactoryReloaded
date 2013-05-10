package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.gui.slot.SlotAcceptBlankRecord;
import powercrystals.minefactoryreloaded.gui.slot.SlotAcceptRecord;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoJukebox;

public class ContainerAutoJukebox extends ContainerFactoryInventory
{
	private TileEntityAutoJukebox _jukebox;
	
	public ContainerAutoJukebox(TileEntityAutoJukebox tileentity, InventoryPlayer inv)
	{
		super(tileentity, inv);
		_jukebox = tileentity;
	}
	
	@Override
	protected void addSlots()
	{
		addSlotToContainer(new SlotAcceptRecord(_te, 0, 8, 24));
		addSlotToContainer(new SlotAcceptBlankRecord(_te, 1, 8, 54));
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < crafters.size(); i++)
		{
			ICrafting crafter = (ICrafting)crafters.get(i);
			if(crafter != null)
			{
				crafter.sendProgressBarUpdate(this, 100, (_jukebox.getCanCopy() ? 1 : 0) | (_jukebox.getCanPlay() ? 2 : 0));
			}
		}
	}
	
	@Override
	public void updateProgressBar(int var, int value)
	{
		super.updateProgressBar(var, value);
		if(var == 100)
		{
			_jukebox.setCanCopy((value & 1) != 0 ? true : false);
			_jukebox.setCanPlay((value & 2) != 0 ? true : false);
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);
		int machInvSize = _jukebox.getSizeInventory();
		
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
