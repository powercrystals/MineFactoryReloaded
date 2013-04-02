package powercrystals.minefactoryreloaded.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactory;

public abstract class MFRLiquidMover
{
	/**
	 * Attempts to fill tank with the player's current item.
	 * @param	itcb			the tank the liquid is going into
	 * @param	entityplayer	the player trying to fill the tank
	 * @return	True if liquid was transferred to the tank.
	 */
	public static boolean manuallyFillTank(ITankContainerBucketable itcb, EntityPlayer entityplayer)
	{
		ItemStack ci = entityplayer.inventory.getCurrentItem();
		LiquidStack liquid = LiquidContainerRegistry.getLiquidForFilledItem(ci);
		if(liquid != null)
		{
			if(itcb.fill(ForgeDirection.UNKNOWN, liquid, false) == liquid.amount)
			{
				itcb.fill(ForgeDirection.UNKNOWN, liquid, true);
				if(!entityplayer.capabilities.isCreativeMode)
				{
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, MFRInventoryUtil.consumeItem(ci));					
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Attempts to drain tank into the player's current item.
	 * @param	itcb			the tank the liquid is coming from
	 * @param	entityplayer	the player trying to take liquid from the tank
	 * @return	True if liquid was transferred from the tank.
	 */
	public static boolean manuallyDrainTank(ITankContainerBucketable itcb, EntityPlayer entityplayer)
	{
		ItemStack ci = entityplayer.inventory.getCurrentItem();
		if(LiquidContainerRegistry.isEmptyContainer(ci))
		{
			for(ILiquidTank tank : itcb.getTanks(ForgeDirection.UNKNOWN))
			{
				LiquidStack tankLiquid = tank.getLiquid();
				ItemStack filledBucket = LiquidContainerRegistry.fillLiquidContainer(tankLiquid, ci);
				if(LiquidContainerRegistry.isFilledContainer(filledBucket))
				{
					LiquidStack bucketLiquid = LiquidContainerRegistry.getLiquidForFilledItem(filledBucket);
					if(entityplayer.capabilities.isCreativeMode)
					{
						tank.drain(bucketLiquid.amount, true);
						return true;
					}
					else if(ci.stackSize == 1)
					{
						tank.drain(bucketLiquid.amount, true);
						entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, filledBucket);
						return true;
					}
					else if(entityplayer.inventory.addItemStackToInventory(filledBucket))
					{
						tank.drain(bucketLiquid.amount, true);
						ci.stackSize -= 1;
						return true;
					}
				}
					
			}
		}
		return false;
	}

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
					l.amount -= filled;
					if(l.amount <= 0)
					{
						break;
					}
				}
			}
		}
	}
	
}