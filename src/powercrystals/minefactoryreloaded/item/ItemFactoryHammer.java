package powercrystals.minefactoryreloaded.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.asm.relauncher.Implementable;
import powercrystals.minefactoryreloaded.api.IToolHammer;

@Implementable("buildcraft.api.tools.IToolWrench")
public class ItemFactoryHammer extends ItemFactory implements IToolHammer
{
	public ItemFactoryHammer(int i)
	{
		super(i);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		int blockId = world.getBlockId(x, y, z);
		Block block = Block.blocksList[blockId];
		if (block != null && block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side)))
		{
			player.swingItem();
			return !world.isRemote;
		}
		return false;
	}
	
	public boolean canWrench(EntityPlayer player, int x, int y, int z)
	{
		return true;
	}
	
	public void wrenchUsed(EntityPlayer player, int x, int y, int z)
	{
	}
	
	@Override
	public boolean shouldPassSneakingClickToBlock(World world, int x, int y, int z)
	{
		return true;
	}
}
