package powercrystals.minefactoryreloaded.core;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;

public class UtilLiquid
{
	public static void pumpLiquid(ILiquidTank tank, TileEntityFactory from)
	{
		if(tank != null && tank.getLiquid() != null && tank.getLiquid().amount > 0)
		{
			LiquidStack l = tank.getLiquid().copy();
			l.amount = Math.min(l.amount, LiquidContainerRegistry.BUCKET_VOLUME);
			for(BlockPosition adj : new BlockPosition(from).getAdjacent(true))
			{
				TileEntity tile = from.worldObj.getBlockTileEntity(adj.x, adj.y, adj.z);
				if(tile != null && tile instanceof ITankContainer)
				{
					int filled = ((ITankContainer)tile).fill(adj.orientation.getOpposite(), l, true);
					tank.drain(filled, true);
				}
			}
		}
	}
}
