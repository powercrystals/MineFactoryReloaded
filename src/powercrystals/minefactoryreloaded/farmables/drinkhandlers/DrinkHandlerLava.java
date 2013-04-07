package powercrystals.minefactoryreloaded.farmables.drinkhandlers;

import net.minecraft.entity.player.EntityPlayer;
import powercrystals.minefactoryreloaded.api.ILiquidDrinkHandler;

public class DrinkHandlerLava implements ILiquidDrinkHandler
{
	@Override
	public void onDrink(EntityPlayer player)
	{
		player.setFire(30);
	}
}
