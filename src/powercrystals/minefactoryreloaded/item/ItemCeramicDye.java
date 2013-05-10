package powercrystals.minefactoryreloaded.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCeramicDye extends ItemFactory
{
	private String[] _dyeNames = new String[]
			{ "white", "orange", "magenta", "lightblue", "yellow", "lime", "pink", "gray", "lightgray", "cyan", "purple", "blue", "brown", "green", "red", "black" };
	private Icon[] _dyeIcons = new Icon[_dyeNames.length];
	
	public ItemCeramicDye(int itemId)
	{
		super(itemId);
		setHasSubtypes(true);
		setMaxDamage(0);
		setMetaMax(15);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack s)
	{
		int md = Math.min(s.getItemDamage(), _dyeNames.length);
		return getUnlocalizedName() + "." + _dyeNames[md];
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister ir)
	{
		for(int i = 0; i < _dyeIcons.length; i++)
		{
			_dyeIcons[i] = ir.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName() + "." + _dyeNames[i]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta)
	{
		meta = Math.min(meta, _dyeIcons.length);
		return _dyeIcons[meta];
	}
}
