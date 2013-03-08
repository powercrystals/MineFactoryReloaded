package powercrystals.minefactoryreloaded.gui.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import powercrystals.minefactoryreloaded.gui.slot.SlotRemoveOnly;
import powercrystals.minefactoryreloaded.processing.TileEntityBioReactor;

public class ContainerBioReactor extends ContainerFactoryInventory
{
	public ContainerBioReactor(TileEntityBioReactor tileentity, InventoryPlayer inv)
	{
		super(tileentity, inv);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < crafters.size(); i++)
		{
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 100, ((TileEntityBioReactor)_te).getBurnTime());
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int var, int value)
	{
		super.updateProgressBar(var, value);
		if(var == 100) ((TileEntityBioReactor)_te).setBurnTime(value);
	}
	
	@Override
	protected void addSlots()
	{
		super.addSlots();
		
		for(int i = 0; i < 9; i++)
		{
			addSlotToContainer(new SlotRemoveOnly(_te, 9 + i, 8 + 18 * i, 83));
		}
	}
	
	@Override
	protected int getPlayerInventoryVerticalOffset()
	{
		return 113;
	}
}
