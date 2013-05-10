package powercrystals.minefactoryreloaded.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockVanillaIce extends ItemBlock
{
	public ItemBlockVanillaIce(int blockId)
	{
		super(blockId);
		setMaxDamage(0);
		setHasSubtypes(true);
		setUnlocalizedName("ice");
	}
	
	@Override
	public int getMetadata(int meta)
	{
		return meta;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		if(stack.getItemDamage() == 1) return getUnlocalizedName() + ".unmelting";
		return getUnlocalizedName();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(int itemId, CreativeTabs creativeTab, List subTypes)
	{
		subTypes.add(new ItemStack(Block.ice.blockID, 1, 0));
		subTypes.add(new ItemStack(Block.ice.blockID, 1, 1));
	}
}
