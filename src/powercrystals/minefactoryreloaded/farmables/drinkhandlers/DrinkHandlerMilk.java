package powercrystals.minefactoryreloaded.farmables.drinkhandlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.api.ILiquidDrinkHandler;

public class DrinkHandlerMilk implements ILiquidDrinkHandler
{
	@Override
	public void onDrink(EntityPlayer player)
	{
		player.curePotionEffects(new ItemStack(Item.bucketMilk));
	}
}
