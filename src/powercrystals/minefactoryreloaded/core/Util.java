package powercrystals.minefactoryreloaded.core;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IToolHammer;

public class Util
{
	public static boolean isHoldingWrench(EntityPlayer player)
	{
		if(player.inventory.getCurrentItem() == null)
		{
			return false;
		}
		Item currentItem = Item.itemsList[player.inventory.getCurrentItem().itemID];
		if(currentItem != null && currentItem instanceof IToolHammer)
		{
			return true;
		}
		return false;
	}
	
	public static boolean isBlockUnbreakable(World world, int x, int y, int z)
	{
		Block b = Block.blocksList[world.getBlockId(x, y, z)];
		return b instanceof BlockFluid || b.getBlockHardness(world, x, y, z) < 0;
	}
	
	public static boolean isRedstonePowered(TileEntity te)
	{
		if(te.worldObj.isBlockIndirectlyGettingPowered(te.xCoord, te.yCoord, te.zCoord))
		{
			return true;
		}
		for(BlockPosition bp : new BlockPosition(te).getAdjacent(false))
		{
			int blockId = te.worldObj.getBlockId(bp.x, bp.y, bp.z);
			if(blockId == Block.redstoneWire.blockID && Block.blocksList[blockId].isProvidingStrongPower(te.worldObj, bp.x, bp.y, bp.z, 1))
			{
				return true;
			}
		}
		return false;
	}
}
