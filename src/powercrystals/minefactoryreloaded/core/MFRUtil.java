package powercrystals.minefactoryreloaded.core;

import java.util.Map.Entry;

import buildcraft.api.transport.IPipeEntry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import powercrystals.core.position.BlockPosition;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.IToolHammer;

public class MFRUtil
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
	
	public static void dropStackDirected(TileEntityFactory from, ItemStack s, ForgeDirection towards)
	{
		s = s.copy();
		BlockPosition bp = new BlockPosition(from.xCoord, from.yCoord, from.zCoord);
		bp.orientation = towards;
		bp.moveForwards(1);
		TileEntity te = from.worldObj.getBlockTileEntity(bp.x, bp.y, bp.z);
		if(te != null && te instanceof IInventory)
		{
			UtilInventory.addToInventory((IInventory)te, towards, s);
		}
		else if(te != null && te instanceof IPipeEntry && ((IPipeEntry)te).acceptItems())
		{
			((IPipeEntry)te).entityEntering(s, towards);
			return;
		}
		
		if(s.stackSize > 0)
		{
			dropStackOnGround(s, BlockPosition.fromFactoryTile(from), from.worldObj, towards);
		}
	}
	
	public static void dropStack(TileEntityFactory from, ItemStack s)
	{
		s = s.copy();
		if(from.worldObj.isRemote || s.stackSize <= 0)
		{
			return;
		}
		for(ForgeDirection o : UtilInventory.findPipes(from.worldObj, from.xCoord, from.yCoord, from.zCoord))
		{
			BlockPosition bp = new BlockPosition(from.xCoord, from.yCoord, from.zCoord);
			bp.orientation = o;
			bp.moveForwards(1);
			TileEntity te = from.worldObj.getBlockTileEntity(bp.x, bp.y, bp.z);
			if(te != null && te instanceof IPipeEntry && ((IPipeEntry)te).acceptItems())
			{
				((IPipeEntry)te).entityEntering(s, o);
				return;
			}
		}
		
		if(MineFactoryReloadedCore.machinesCanDropInChests.getBoolean(true));
		{
			for(Entry<ForgeDirection, IInventory> chest : UtilInventory.findChests(from.worldObj, from.xCoord, from.yCoord, from.zCoord).entrySet())
			{
				if(chest.getValue().getInvName() == "Engine")
				{
					continue;
				}
				s.stackSize = UtilInventory.addToInventory(chest.getValue(), chest.getKey(), s);
				if(s.stackSize == 0)
				{
					return;
				}
			}
		}
		
		if(s.stackSize > 0)
		{
			dropStackOnGround(s, BlockPosition.fromFactoryTile(from), from.worldObj, from.getDropDirection());
		}
	}
	
	private static void dropStackOnGround(ItemStack stack, BlockPosition from, World world, ForgeDirection towards)
	{
		float dropOffsetX = 0.0F;
		float dropOffsetY = 0.0F;
		float dropOffsetZ = 0.0F;
		
		switch(towards)
		{
			case UNKNOWN:
			case UP:
				dropOffsetX = 0.5F;
				dropOffsetY = 1.5F;
				dropOffsetZ = 0.5F;
				break;
			case DOWN:
				dropOffsetX = 0.5F;
				dropOffsetY = -0.5F;
				dropOffsetZ = 0.5F;
				break;
			case NORTH:
				dropOffsetX = 0.5F;
				dropOffsetY = 0.5F;
				dropOffsetZ = -0.5F;
				break;
			case SOUTH:
				dropOffsetX = 0.5F;
				dropOffsetY = 0.5F;
				dropOffsetZ = 1.5F;
				break;
			case EAST:
				dropOffsetX = 1.5F;
				dropOffsetY = 0.5F;
				dropOffsetZ = 0.5F;
				break;
			case WEST:
				dropOffsetX = -0.5F;
				dropOffsetY = 0.5F;
				dropOffsetZ = 0.5F;
				break;
			default:
				break;
				
		}
		
		EntityItem entityitem = new EntityItem(world, from.x + dropOffsetX, from.y + dropOffsetY, from.z + dropOffsetZ, stack);
		entityitem.motionX = 0.0D;
		entityitem.motionY = 0.3D;
		entityitem.motionZ = 0.0D;
		world.spawnEntityInWorld(entityitem);
	}
}
