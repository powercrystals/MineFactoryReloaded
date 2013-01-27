package powercrystals.minefactoryreloaded.decorative;

import java.util.List;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCloth;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;

public class ItemBlockFactoryGlassPane extends ItemBlock
{
    public ItemBlockFactoryGlassPane(int blockId)
    {
        super(blockId);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public int getIconFromDamage(int damage)
    {
        return MineFactoryReloadedCore.factoryGlassPaneBlock.getBlockTextureFromSideAndMetadata(2, damage);
    }

    public int getMetadata(int meta)
    {
        return meta;
    }

    public String getItemNameIS(ItemStack stack)
    {
        return "factoryGlassPaneItem." + ItemDye.dyeColorNames[BlockCloth.getBlockFromDye(stack.getItemDamage())];
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < 16; i++)
		{
			par3List.add(new ItemStack(MineFactoryReloadedCore.factoryGlassPaneBlock.blockID, 1, i));
		}
	}
}
