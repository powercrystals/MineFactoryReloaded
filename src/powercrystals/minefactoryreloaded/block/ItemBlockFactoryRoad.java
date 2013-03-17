package powercrystals.minefactoryreloaded.block;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemBlockFactoryRoad extends ItemBlockFactory
{
	public ItemBlockFactoryRoad(int blockId)
	{
		super(blockId);
		setMaxDamage(0);
		setHasSubtypes(true);
		setNames(new String[] { "default", "light.off", "light.on", "light.inverted.off", "light.inverted.on" });
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(int itemId, CreativeTabs creativeTab, List subTypes)
	{
		subTypes.add(new ItemStack(itemId, 1, 0));
		subTypes.add(new ItemStack(itemId, 1, 1));
		subTypes.add(new ItemStack(itemId, 1, 4));
	}
}
