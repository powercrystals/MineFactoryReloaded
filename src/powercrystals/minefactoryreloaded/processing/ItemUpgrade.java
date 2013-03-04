package powercrystals.minefactoryreloaded.processing;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.ItemFactory;

public class ItemUpgrade extends ItemFactory
{
	public ItemUpgrade(int id)
	{
		super(id);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		super.addInformation(stack, player, infoList, advancedTooltips);
		infoList.add("Radius increase: " + getUpgradeLevel(stack));
	}
	
	public int getUpgradeLevel(ItemStack s)
	{
		return Math.min(s.getItemDamage(), 4) + 1;
	}
	
	@Override
	public String getItemNameIS(ItemStack s)
	{
		if(s.getItemDamage() == 0) return "factoryUpgradeLapis";
		if(s.getItemDamage() == 1) return "factoryUpgradeGold";
		if(s.getItemDamage() == 2) return "factoryUpgradeDiamond";
		if(s.getItemDamage() == 3) return "factoryUpgradeEmerald";
		return "factoryUpgradeLapis";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getIconFromDamage(int meta)
	{
		if(meta == 0) return 49;
		if(meta == 1) return 48;
		if(meta == 2) return 38;
		if(meta == 3) return 39;
		return getIconFromDamage(0);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemId, CreativeTabs creativeTab, List subtypes)
	{
		for(int i = 0; i < 4; i++)
		{
			subtypes.add(new ItemStack(MineFactoryReloadedCore.machineBlock1.blockID, 1, i));
		}
	}
}
