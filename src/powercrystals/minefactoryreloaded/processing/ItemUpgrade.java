package powercrystals.minefactoryreloaded.processing;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.ItemFactory;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;

public class ItemUpgrade extends ItemFactory
{
	public ItemUpgrade(int id)
	{
		super(id);
		setCreativeTab(MFRCreativeTab.tab);
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
		return Math.min(s.getItemDamage(), 10) + 1;
	}
	
	@Override
	public String getItemNameIS(ItemStack s)
	{
		if(s.getItemDamage() == 0) return "factoryUpgradeLapis";
		if(s.getItemDamage() == 1) return "factoryUpgradeIorn";
		if(s.getItemDamage() == 2) return "factoryUpgradeTin";
		if(s.getItemDamage() == 3) return "factoryUpgradeCopper";
		if(s.getItemDamage() == 4) return "factoryUpgradeBronze";
		if(s.getItemDamage() == 5) return "factoryUpgradeSilver";
		if(s.getItemDamage() == 6) return "factoryUpgradeGold";
		if(s.getItemDamage() == 7) return "factoryUpgradeQuartz";
		if(s.getItemDamage() == 8) return "factoryUpgradeDiamond";
		if(s.getItemDamage() == 9) return "factoryUpgradePlatinum";
		if(s.getItemDamage() == 10) return "factoryUpgradeEmerald";
		return "factoryUpgradeLapis";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getIconFromDamage(int meta)
	{
		if(meta == 0) return 49;
		if(meta == 1) return 51;
		if(meta == 2) return 52;
		if(meta == 3) return 53;
		if(meta == 4) return 54;
		if(meta == 5) return 55;
		if(meta == 6) return 48;
		if(meta == 7) return 56;
		if(meta == 8) return 38;
		if(meta == 9) return 57;
		if(meta == 10) return 39;
		return getIconFromDamage(0);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemId, CreativeTabs creativeTab, List subtypes)
	{
		for(int i = 0; i < 11; i++)
		{
			subtypes.add(new ItemStack(MineFactoryReloadedCore.machineBlock1.blockID, 1, i));
		}
	}
}
