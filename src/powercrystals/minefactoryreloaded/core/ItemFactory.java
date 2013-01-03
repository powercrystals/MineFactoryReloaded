package powercrystals.minefactoryreloaded.core;

import net.minecraft.item.Item;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class ItemFactory extends Item
{
	public ItemFactory(int i)
	{
		super(i);
	}
	
	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.itemTexture;
	}
}
