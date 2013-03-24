package powercrystals.minefactoryreloaded.item;

import java.util.List;

import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;

public class ItemFactoryBucket extends ItemBucket
{
	public ItemFactoryBucket(int id, int liquidId)
	{
		super(id, liquidId);
		setCreativeTab(MFRCreativeTab.tab);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void func_94581_a(IconRegister par1IconRegister)
	{
		this.iconIndex = par1IconRegister.func_94245_a("powercrystals/minefactoryreloaded/" + getUnlocalizedName());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(int itemId, CreativeTabs creativeTab, List subTypes)
	{
		subTypes.add(new ItemStack(itemId, 1, 0));
	}
}
