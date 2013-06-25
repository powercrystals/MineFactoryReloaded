package powercrystals.minefactoryreloaded.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class ItemBlockVineScaffold extends ItemBlock
{
	public ItemBlockVineScaffold(int id)
	{
		super(id);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float xOffset, float yOffset, float zOffset)
	{
		if(!player.isSneaking() && world.getBlockId(x, y, z) == MineFactoryReloadedCore.vineScaffoldBlock.blockID)
		{
			player.swingItem();
			return false;
		}
		return super.onItemUse(stack, player, world, x, y, z, side, xOffset, yOffset, zOffset);
	}
}
