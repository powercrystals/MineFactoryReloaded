package powercrystals.minefactoryreloaded.transport;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockConveyor extends ItemBlock
{
    public ItemBlockConveyor(int blockId)
    {
        super(blockId);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public int getIconFromDamage(int damage)
    {
        return damage == 16 ? MineFactoryReloadedCore.conveyorOffTexture : 64 + damage;
    }

    public int getMetadata(int meta)
    {
        return meta;
    }

    public String getItemNameIS(ItemStack stack)
    {
        return "factoryConveyorItem." + (stack.getItemDamage() + 1);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < 17; i++)
		{
			par3List.add(new ItemStack(MineFactoryReloadedCore.conveyorBlock.blockID, 1, i));
		}
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{
		if(super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata))
		{
			((TileEntityConveyor)world.getBlockTileEntity(x, y, z)).setDyeColor(stack.getItemDamage() == 16 ? -1 : stack.getItemDamage());
			return true;	
		}
		return false;
	}
}
