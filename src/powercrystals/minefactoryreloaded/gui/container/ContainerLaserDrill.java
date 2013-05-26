package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import powercrystals.minefactoryreloaded.gui.slot.SlotAcceptLaserFocus;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityLaserDrill;

public class ContainerLaserDrill extends ContainerFactoryInventory
{
	private int _workDoneTemp;
	private int _energyStoredTemp;
	
	public ContainerLaserDrill(TileEntityLaserDrill tileentity, InventoryPlayer inv)
	{
		super(tileentity, inv);
	}
	
	@Override
	protected void addSlots()
	{
		for(int i = 0; i < 6; i++)
		{	
			addSlotToContainer(new SlotAcceptLaserFocus(_te, i, 62 + 18 * i, 79));
		}
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < crafters.size(); i++)
		{
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 100, ((TileEntityLaserDrill)_te).getWorkDone());
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 101, ((TileEntityLaserDrill)_te).getWorkDone() >> 16);
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 102, ((TileEntityLaserDrill)_te).getEnergyStored());
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 103, ((TileEntityLaserDrill)_te).getEnergyStored() >> 16);
		}
	}
	
	@Override
	public void updateProgressBar(int var, int value)
	{
		super.updateProgressBar(var, value);
		
		if(var == 100) _workDoneTemp = (value >= 0 ? value : value + 65536);
		else if(var == 101) ((TileEntityLaserDrill)_te).setWorkDone(_workDoneTemp | (value << 16));
		if(var == 102) _energyStoredTemp = (value >= 0 ? value : value + 65536);
		else if(var == 103) ((TileEntityLaserDrill)_te).setEnergyStored(_energyStoredTemp | (value << 16));
	}
	
	@Override
	protected int getPlayerInventoryVerticalOffset()
	{
		return 99;
	}
}
