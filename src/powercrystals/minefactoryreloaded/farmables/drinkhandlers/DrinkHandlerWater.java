package powercrystals.minefactoryreloaded.farmables.drinkhandlers;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.ILiquidDrinkHandler;

public class DrinkHandlerWater implements ILiquidDrinkHandler
{
	@Override
	public void onDrink(EntityPlayer player)
	{
		player.extinguish();
		NBTTagCompound tag = player.getEntityData();
		World world = player.worldObj;
		if (tag.hasKey("drankLavaTime") && (world.getTotalWorldTime() - tag.getLong("drankLavaTime")) < 100)
		{
			player.dropPlayerItem(new ItemStack(Block.obsidian));
			tag.setLong("drankLavaTime", -100);
			world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		}
	}
}
