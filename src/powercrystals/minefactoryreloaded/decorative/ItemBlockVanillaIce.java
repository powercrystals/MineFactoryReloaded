package powercrystals.minefactoryreloaded.decorative;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockVanillaIce extends ItemBlock
{
    public ItemBlockVanillaIce(int blockId)
    {
        super(blockId);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public int getIconFromDamage(int damage)
    {
        return Block.ice.getBlockTextureFromSideAndMetadata(2, damage);
    }

    public int getMetadata(int meta)
    {
        return meta;
    }

    public String getItemNameIS(ItemStack stack)
    {
        if(stack.getItemDamage() == 0) return "ice";
        if(stack.getItemDamage() == 1) return "iceUnmelting";
        return "ice";
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(Block.ice.blockID, 1, 0));
		par3List.add(new ItemStack(Block.ice.blockID, 1, 1));
	}
}
