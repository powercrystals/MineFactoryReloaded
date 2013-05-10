package powercrystals.minefactoryreloaded.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFactory extends Item
{
	private int _metaMax = 0;
	
	public ItemFactory(int id)
	{
		super(id);
		setCreativeTab(MFRCreativeTab.tab);
	}
	
	protected void setMetaMax(int max)
	{
		_metaMax = max;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(int itemId, CreativeTabs creativeTab, List subTypes)
	{
		for(int meta = 0; meta <= _metaMax; meta++)
		{
			subTypes.add(new ItemStack(itemId, 1, meta));
		}
	}
}
