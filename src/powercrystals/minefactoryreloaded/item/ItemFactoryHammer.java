package powercrystals.minefactoryreloaded.item;

import buildcraft.api.tools.IToolWrench;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.minefactoryreloaded.api.IToolHammer;

public class ItemFactoryHammer extends ItemFactory implements IToolHammer, IToolWrench
{
	public ItemFactoryHammer(int i)
	{
		super(i);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		Block block = Block.blocksList[world.getBlockId(x, y, z)];
		if (block != null)
		{
			if (block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side))) 
			{
				player.swingItem();
				return !world.isRemote;
			}
		}
		return false;
	}
	
	@Override
	public boolean canWrench(EntityPlayer player, int x, int y, int z)
	{
		return true;
	}
	
	@Override
	public void wrenchUsed(EntityPlayer player, int x, int y, int z)
	{
	}
	
	@Override
	public boolean shouldPassSneakingClickToBlock(World world, int x, int y, int z)
	{
		return true;
	}
}
