package powercrystals.minefactoryreloaded.decorative;

import java.util.List;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockFactoryRoad extends ItemBlock
{
    public ItemBlockFactoryRoad(int blockId)
    {
        super(blockId);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public int getIconFromDamage(int damage)
    {
        return MineFactoryReloadedCore.factoryRoadBlock.getBlockTextureFromSideAndMetadata(2, damage);
    }

    public int getMetadata(int meta)
    {
        return meta;
    }

    public String getItemNameIS(ItemStack stack)
    {
        if(stack.getItemDamage() == 0) return "factoryRoadItem";
        if(stack.getItemDamage() == 1) return "factoryRoadLightOffItem";
        if(stack.getItemDamage() == 2) return "factoryRoadLightOnItem";
        if(stack.getItemDamage() == 3) return "factoryRoadInvLightOffItem";
        if(stack.getItemDamage() == 4) return "factoryRoadInvLightOnItem";
        return "factoryRoadItem";
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(MineFactoryReloadedCore.factoryRoadBlock.blockID, 1, 0));
		par3List.add(new ItemStack(MineFactoryReloadedCore.factoryRoadBlock.blockID, 1, 1));
		par3List.add(new ItemStack(MineFactoryReloadedCore.factoryRoadBlock.blockID, 1, 4));
	}
}
