package powercrystals.minefactoryreloaded.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.tile.TileEntityConveyor;

public class ItemBlockConveyor extends ItemBlockFactory
{
	public ItemBlockConveyor(int blockId)
	{
		super(blockId);
		setMaxDamage(0);
		setHasSubtypes(true);
		setNames(new String[] { "white", "orange", "magenta", "lightblue", "yellow", "lime", "pink", "gray", "lightgray", "cyan", "purple", "blue", "brown", "green", "red", "black", "default" });
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
