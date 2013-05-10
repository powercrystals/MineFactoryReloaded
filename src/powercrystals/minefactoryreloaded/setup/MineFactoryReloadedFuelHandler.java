package powercrystals.minefactoryreloaded.setup;

import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import cpw.mods.fml.common.IFuelHandler;

public class MineFactoryReloadedFuelHandler implements IFuelHandler
{
	@Override
	public int getBurnTime(ItemStack fuel)
	{
		if(fuel.itemID == MineFactoryReloadedCore.rubberWoodBlock.blockID)
		{
			return 300;
		}
		else if(fuel.itemID == MineFactoryReloadedCore.rubberSaplingBlock.blockID)
		{
			return 100;
		}
		else if(fuel.itemID == MineFactoryReloadedCore.sugarCharcoalItem.itemID)
		{
			return 400;
		}
		
		return 0;
	}
}
