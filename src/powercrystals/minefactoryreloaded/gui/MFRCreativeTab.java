package powercrystals.minefactoryreloaded.gui;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class MFRCreativeTab extends CreativeTabs
{
	public static final MFRCreativeTab tab = new MFRCreativeTab("MineFactory Reloaded");
	
	public MFRCreativeTab(String label)
	{
		super(label);
	}
	
	@Override
	public ItemStack getIconItemStack()
	{
		return new ItemStack(MineFactoryReloadedCore.conveyorBlock, 1, 16);
	}
	
	@Override
	public String getTranslatedTabLabel()
	{
		return this.getTabLabel();
	}
}
