package powercrystals.minefactoryreloaded.item;

import java.util.List;

import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFactoryBucket extends ItemBucket
{
	private int _liquidId;
	
	public ItemFactoryBucket(int id, int liquidId)
	{
		super(id, liquidId);
		setCreativeTab(MFRCreativeTab.tab);
		_liquidId = liquidId;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName());
	}
	
	@Override
	public boolean tryPlaceContainedLiquid(World world, double xOffset, double yOffset, double zOffset, int x, int y, int z)
	{
		if(_liquidId <= 0)
		{
			return false;
		}
		else if(!world.isAirBlock(x, y, z) && world.getBlockMaterial(x, y, z).isSolid())
		{
			return false;
		}
		else
		{
			 world.setBlock(x, y, z, _liquidId, 7, 3);
			return true;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(int itemId, CreativeTabs creativeTab, List subTypes)
	{
		subTypes.add(new ItemStack(itemId, 1, 0));
	}
}
