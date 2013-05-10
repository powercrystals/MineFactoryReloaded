package powercrystals.minefactoryreloaded.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemXpExtractor extends ItemFactory
{
	private Icon _icon1;
	private Icon _icon2;
	private Icon _icon3;
	
	public ItemXpExtractor(int id)
	{
		super(id);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		infoList.add("Requires an empty bucket to fill");
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.bow;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 32;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(player.experienceLevel > 0 && player.inventory.hasItem(Item.bucketEmpty.itemID))
		{
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}
		
		return stack;
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		if(player.experienceLevel > 0 && player.inventory.hasItem(Item.bucketEmpty.itemID))
		{
			player.experienceLevel--;
			player.inventory.consumeInventoryItem(Item.bucketEmpty.itemID);
			if(!player.inventory.addItemStackToInventory(new ItemStack(MineFactoryReloadedCore.mobEssenceBucketItem)))
			{
				player.dropItem(MineFactoryReloadedCore.mobEssenceBucketItem.itemID, 1);
			}
		}
		return stack;
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
		if(usingItem != null && usingItem.itemID == itemID)
		{
			if(useRemaining > 24) return _icon1;
			if(useRemaining > 12) return _icon2;
			return _icon3;
		}
		return _icon1;
	}
	
	@Override
	public void registerIcons(IconRegister ir)
	{
		_icon1 = ir.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName() + ".1");
		_icon2 = ir.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName() + ".2");
		_icon3 = ir.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName() + ".3");
		
		itemIcon = _icon1;
	}
}
