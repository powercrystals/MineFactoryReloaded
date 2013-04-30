package powercrystals.minefactoryreloaded.gui.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityEnchantmentRouter;

public class ContainerEnchantmentRouter extends ContainerItemRouter
{
	private TileEntityEnchantmentRouter _router;
	
	public ContainerEnchantmentRouter(TileEntityEnchantmentRouter router, InventoryPlayer inventoryPlayer)
	{
		super(router, inventoryPlayer);	
		_router = router;
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < crafters.size(); i++)
		{
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 100, _router.getMatchLevels() ? 1 : 0);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int var, int value)
	{
		super.updateProgressBar(var, value);
		
		if(var == 100) _router.setMatchLevels(value == 1 ? true : false);
	}

	@Override
	protected void addSlots()
	{
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(_te, j + i * 9, 8 + j * 18, 40 + i * 18));
			}
		}
	}
	
	@Override
	protected int getPlayerInventoryVerticalOffset()
	{
		return 144;
	}
}
