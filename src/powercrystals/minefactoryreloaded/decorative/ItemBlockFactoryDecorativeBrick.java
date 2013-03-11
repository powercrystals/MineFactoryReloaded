package powercrystals.minefactoryreloaded.decorative;

import java.util.List;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockFactoryDecorativeBrick extends ItemBlock
{
	public ItemBlockFactoryDecorativeBrick(int id)
	{
		super(id);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	public int getIconFromDamage(int damage)
	{
		return MineFactoryReloadedCore.factoryDecorativeBrickBlock.getBlockTextureFromSideAndMetadata(2, damage);
	}

	public int getMetadata(int meta)
	{
		return meta;
	}

	public String getItemNameIS(ItemStack stack)
	{
		if(stack.getItemDamage() == 0) return "factoryDecorativeBrickGlowstone";
		if(stack.getItemDamage() == 1) return "factoryDecorativeBrickIce";
		if(stack.getItemDamage() == 2) return "factoryDecorativeBrickLapis";
		if(stack.getItemDamage() == 3) return "factoryDecorativeBrickObsidian";
		if(stack.getItemDamage() == 4) return "factoryDecorativeBrickPavedStone";
		if(stack.getItemDamage() == 5) return "factoryDecorativeBrickSnow";
		
		return "factoryDecorativeBrickGlowstone";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(int itemId, CreativeTabs creativeTab, List subtypes)
	{
		for(int i = 0; i < 6; i++)
		{
			subtypes.add(new ItemStack(MineFactoryReloadedCore.factoryDecorativeBrickBlock.blockID, 1, i));
		}
	}
}
