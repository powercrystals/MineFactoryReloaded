package powercrystals.minefactoryreloaded.core;

import net.minecraft.item.Item;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;

public class ItemFactory extends Item
{
	public ItemFactory(int i)
	{
		super(i);
		setCreativeTab(MFRCreativeTab.tab);
	}
	
	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.itemTexture;
	}
}
