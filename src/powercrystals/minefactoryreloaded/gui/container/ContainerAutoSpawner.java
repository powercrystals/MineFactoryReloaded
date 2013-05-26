package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import powercrystals.minefactoryreloaded.gui.slot.SlotAcceptReusableSafariNet;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoSpawner;

public class ContainerAutoSpawner extends ContainerFactoryPowered
{
	public ContainerAutoSpawner(TileEntityAutoSpawner te, InventoryPlayer inv)
	{
		super(te, inv);
	}
	
	@Override
	protected void addSlots()
	{
		addSlotToContainer(new SlotAcceptReusableSafariNet(_te, 0, 8, 24));
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < crafters.size(); i++)
		{
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 100, ((TileEntityAutoSpawner)_te).getSpawnExact() ? 1 : 0);
		}
	}
	
	@Override
	public void updateProgressBar(int var, int value)
	{
		super.updateProgressBar(var, value);
		if(var == 100) ((TileEntityAutoSpawner)_te).setSpawnExact(value == 0 ? false : true);
	}
}
