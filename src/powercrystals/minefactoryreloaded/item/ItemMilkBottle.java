package powercrystals.minefactoryreloaded.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMilkBottle extends ItemFactory
{
	public ItemMilkBottle(int id)
	{
		super(id);
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
		if(!world.isRemote)
		{
			player.curePotionEffects(new ItemStack(Item.bucketMilk));
		}
		
		if (!player.capabilities.isCreativeMode)
		{
			stack.stackSize--;
			
			if(stack.stackSize <= 0)
			{
				return new ItemStack(Item.glassBottle);
			}
			else if(!player.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle)))
			{
				player.dropPlayerItem(new ItemStack(Item.glassBottle));
			}
		}

		if(stack.stackSize <= 0)
		{
			stack.stackSize = 0;
		}

		return stack;
	}
	
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 32;
	}
	
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.drink;
	}
	
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		return stack;
	}
}
