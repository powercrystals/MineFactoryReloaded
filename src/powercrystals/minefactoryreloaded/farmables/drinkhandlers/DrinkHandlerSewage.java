package powercrystals.minefactoryreloaded.farmables.drinkhandlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import powercrystals.minefactoryreloaded.api.ILiquidDrinkHandler;

public class DrinkHandlerSewage implements ILiquidDrinkHandler
{
	@Override
	public void onDrink(EntityPlayer player)
	{
		player.addPotionEffect(new PotionEffect(Potion.confusion.id, 40 * 20, 0));
		player.addPotionEffect(new PotionEffect(Potion.poison.id, 40 * 20, 0));
		player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 40 * 20, 0));
	}
}
