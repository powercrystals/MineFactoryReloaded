package powercrystals.minefactoryreloaded.decorative;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import powercrystals.minefactoryreloaded.core.ItemFactory;

public class ItemCeramicDye extends ItemFactory
{
	public ItemCeramicDye(int itemId)
	{
		super(itemId);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(CreativeTabs.tabMaterials);
	}

    public int getIconFromDamage(int meta)
    {
        return iconIndex + meta;
    }

    public String getItemNameIS(ItemStack stack)
    {
        int damage = MathHelper.clamp_int(stack.getItemDamage(), 0, 15);
        return "itemCeramicDye." + damage;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 16; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
}
