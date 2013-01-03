package powercrystals.minefactoryreloaded.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import buildcraft.api.transport.IPipeEntry;

public class UtilInventory
{
	public static void dropStack(TileEntityFactory from, ItemStack s)
	{
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
			float dropOffsetX = 0.0F;
			float dropOffsetY = 0.0F;
			float dropOffsetZ = 0.0F;
			
			switch(from.getDropDirection())
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
			
			EntityItem entityitem = new EntityItem(from.worldObj, from.xCoord + dropOffsetX, from.yCoord + dropOffsetY, from.zCoord + dropOffsetZ, s);
			entityitem.motionX = 0.0D;
			entityitem.motionY = 0.3D;
			entityitem.motionZ = 0.0D;
			from.worldObj.spawnEntityInWorld(entityitem);
		}
	}

	public static int addToInventory(IInventory targetInventory, ForgeDirection toSide, ItemStack stackToAdd)
	{
		int amountLeftToAdd = stackToAdd.stackSize;
		int stackSizeLimit;

		stackSizeLimit = Math.min(targetInventory.getInventoryStackLimit(), stackToAdd.getMaxStackSize());

		int slotIndex;
		
		while(amountLeftToAdd > 0)
		{
			slotIndex = getAvailableSlot(targetInventory, toSide, stackToAdd);
			if(slotIndex < 0)
			{
				break;
			}
			ItemStack targetStack = targetInventory.getStackInSlot(slotIndex);
			if(targetStack == null)
			{
				if(stackToAdd.stackSize <= stackSizeLimit)
				{
					ItemStack s = stackToAdd.copy();
					s.stackSize = amountLeftToAdd;
					targetInventory.setInventorySlotContents(slotIndex, s);
					amountLeftToAdd = 0;
					break;
				}
				else
				{
					ItemStack s = stackToAdd.copy();
					s.stackSize = stackSizeLimit;
					targetInventory.setInventorySlotContents(slotIndex, stackToAdd);
					amountLeftToAdd -= s.stackSize;
				}
			}
			else
			{
				int amountToAdd = Math.min(amountLeftToAdd, stackSizeLimit - targetStack.stackSize);
				targetStack.stackSize += amountToAdd;
				amountLeftToAdd -= amountToAdd;
			}
		}
		
		return amountLeftToAdd;
	}
	
	private static int getAvailableSlot(IInventory inventory, ForgeDirection toSide, ItemStack stack)
	{
		int stackSizeLimit;

		stackSizeLimit = Math.min(inventory.getInventoryStackLimit(), stack.getMaxStackSize());
		
		int invStart = 0;
		int invEnd = inventory.getSizeInventory();
		
		if(toSide != ForgeDirection.UNKNOWN && inventory instanceof ISidedInventory)
		{
			invStart = ((ISidedInventory)inventory).getStartInventorySide(toSide.getOpposite());
			invEnd = invStart + ((ISidedInventory)inventory).getSizeInventorySide(toSide.getOpposite());
		}
		
		for(int i = invStart; i < invEnd; i++)
		{
			ItemStack targetStack = inventory.getStackInSlot(i);
			if(targetStack == null)
			{
				return i;
			}
			if(targetStack.itemID == stack.itemID && targetStack.getItemDamage() == stack.getItemDamage()
					&& targetStack.stackSize < stackSizeLimit)
			{
				return i;
			}
		}
		
		return -1;
	}

	public static List<ForgeDirection> findPipes(World world, int x, int y, int z)
	{
		List<ForgeDirection> pipes = new LinkedList<ForgeDirection>();
		BlockPosition ourpos = new BlockPosition(x, y, z);
		for(ForgeDirection o : ForgeDirection.values())
		{
			BlockPosition bp = new BlockPosition(ourpos);
			bp.orientation = o;
			bp.moveForwards(1);
			TileEntity te = world.getBlockTileEntity(bp.x, bp.y, bp.z);
			if(te instanceof IPipeEntry)
			{
				pipes.add(o);
			}
		}
		
		return pipes;
	}
	
	public static Map<ForgeDirection, IInventory> findChests(World world, int x, int y, int z)
	{
		HashMap<ForgeDirection, IInventory> chests = new HashMap<ForgeDirection, IInventory>();
		
		for(ForgeDirection d : ForgeDirection.values())
		{
			if(d == ForgeDirection.UNKNOWN)
			{
				continue;
			}
			BlockPosition bp = new BlockPosition(x, y, z);
			bp.orientation = d;
			bp.moveForwards(1);
			TileEntity te = world.getBlockTileEntity(bp.x, bp.y, bp.z);
			if(te != null && te instanceof IInventory)
			{
				chests.put(d, checkForDoubleChest(world, te, bp));
			}
		}
		return chests;
	}
	
	private static IInventory checkForDoubleChest(World world, TileEntity te, BlockPosition chestloc)
	{
		if(world.getBlockId(chestloc.x, chestloc.y, chestloc.z) == Block.chest.blockID)
		{
			for(BlockPosition bp : chestloc.getAdjacent(false))
			{
				if(world.getBlockId(bp.x, bp.y, bp.z) == Block.chest.blockID)
				{
					return new InventoryLargeChest("Large Chest", ((IInventory)te), ((IInventory)world.getBlockTileEntity(bp.x, bp.y, bp.z)));
				}
			}
		}
		return ((IInventory)te);
	}

}
