package powercrystals.minefactoryreloaded.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFactoryFood extends ItemFood
{
	public ItemFactoryFood(int id, int foodRestored, float sustenance)
	{
		super(id, foodRestored, sustenance, false);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
		itemIcon = ir.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName());
	}
}
