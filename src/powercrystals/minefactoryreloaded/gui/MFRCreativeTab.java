package powercrystals.minefactoryreloaded.gui;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

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
		return new ItemStack(MineFactoryReloadedCore.conveyorBlock);
	}

	@Override
	public String getTranslatedTabLabel()
	{
		return this.getTabLabel();
	}
}
