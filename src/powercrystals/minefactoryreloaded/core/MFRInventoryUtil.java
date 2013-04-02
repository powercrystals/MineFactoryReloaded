package powercrystals.minefactoryreloaded.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import buildcraft.api.transport.IPipeEntry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.inventory.IInventoryManager;
import powercrystals.core.inventory.InventoryManager;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.api.IDeepStorageUnit;

public abstract class MFRInventoryUtil
{
	/**
	 * Searches from position x, y, z, checking for BC-compatible pipes in all directions.
	 * @return HashMap<ForgeDirection, IPipeEntry> specifying all found pipes and their directions.
	 */
	public static HashMap<ForgeDirection, IPipeEntry> findPipes(World world, int x, int y, int z)
	{
		return findPipes(world, x, y, z, ForgeDirection.VALID_DIRECTIONS);
	}
	
	/**
	 * Searches from position x, y, z, checking for BC-compatible pipes in each directiontocheck.
	 * @return HashMap<ForgeDirection, IPipeEntry> specifying all found pipes and their directions.
	 */
	public static HashMap<ForgeDirection, IPipeEntry> findPipes(World world, int x, int y, int z, ForgeDirection[] directionstocheck)
	{
		HashMap<ForgeDirection, IPipeEntry> pipes = new HashMap<ForgeDirection, IPipeEntry>();
		for(ForgeDirection direction : directionstocheck)
		{
			BlockPosition bp = new BlockPosition(x, y, z);
			bp.orientation = direction;
			bp.moveForwards(1);
			TileEntity te = world.getBlockTileEntity(bp.x, bp.y, bp.z);
			if(te instanceof IPipeEntry)
			{
				pipes.put(direction, (IPipeEntry)te);
			}
		}
		return pipes;
	}
	
	/**
	 * Searches from position x, y, z, checking for inventories in all directions.
	 * @return HashMap<ForgeDirection, IInventory> specifying all found inventories and their directions.
	 */
	public static Map<ForgeDirection, IInventory> findChests(World world, int x, int y, int z)
	{
		return findChests(world, x, y, z, ForgeDirection.VALID_DIRECTIONS);
	}
	
	/**
	 * Searches from position x, y, z, checking for inventories in each directiontocheck.
	 * @return HashMap<ForgeDirection, IInventory> specifying all found inventories and their directions.
	 */
	public static Map<ForgeDirection, IInventory> findChests(World world, int x, int y, int z, ForgeDirection[] directionstocheck)
	{
		HashMap<ForgeDirection, IInventory> chests = new HashMap<ForgeDirection, IInventory>();
		for(ForgeDirection direction : directionstocheck)
		{
			BlockPosition bp = new BlockPosition(x, y, z);
			bp.orientation = direction;
			bp.moveForwards(1);
			TileEntity te = world.getBlockTileEntity(bp.x, bp.y, bp.z);
			if(te != null && te instanceof IInventory)
			{
				chests.put(direction, checkForDoubleChest(world, te, bp));
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
	
	/**
	 * Drops an ItemStack, checking all directions for pipes > chests. DOESN'T drop items into the world.
	 * Example of this behavior: Cargo dropoff rail.
	 * @return	The remainder of the ItemStack. Whatever -wasn't- successfully dropped. 
	 */
	public static ItemStack dropStack(TileEntity from, ItemStack stack)
	{
		return dropStack(from, stack, ForgeDirection.VALID_DIRECTIONS, ForgeDirection.UNKNOWN);
	}

	/**
	 * Drops an ItemStack, checking all directions for pipes > chests. Drop items into the world.
	 * Example of this behavior: Harvesters, sludge boilers, etc.
	 * @return	The remainder of the ItemStack. Whatever -wasn't- successfully dropped. 
	 */
	public static ItemStack dropStack(TileEntity from, ItemStack stack, ForgeDirection airdropdirection)
	{
		return dropStack(from, stack, ForgeDirection.VALID_DIRECTIONS, airdropdirection);
	}
	
	/**
	 * Drops an ItemStack, into chests > pipes > the world, but only in a single direction.
	 * Example of this behavior: Item Router, Ejector
	 * @param	dropdirection a -single- direction in which to check for pipes/chests
	 * @return	The remainder of the ItemStack. Whatever -wasn't- successfully dropped. 
	 */
	public static ItemStack dropStack(TileEntity from, ItemStack stack, ForgeDirection dropdirection, ForgeDirection airdropdirection)
	{
		ForgeDirection[] dropdirections = {dropdirection};
		return dropStack(from, stack, dropdirections, airdropdirection);
	}
	
	/**
	 * Drops an ItemStack, checks pipes > chests > world in that order.
	 * It generally shouldn't be necessary to call this explicitly. 
	 * @param	from	the TileEntity doing the dropping
	 * @param	stack	the ItemStack being dropped
	 * @param	dropdirections	directions in which stack may be dropped into chests or pipes
	 * @param	airdropdirection	the direction that the stack may be dropped into air. ForgeDirection.UNKNOWN or other invalid directions indicate that stack shouldn't be dropped into the world.
	 * @return	The remainder of the ItemStack. Whatever -wasn't- successfully dropped. 
	 */
	public static ItemStack dropStack(TileEntity from, ItemStack stack, ForgeDirection[] dropdirections, ForgeDirection airdropdirection)
	{
		if(stack == null || stack.stackSize == 0 || from.worldObj.isRemote)
		{
			return stack;
		}
		BlockPosition bp = new BlockPosition(from.xCoord, from.yCoord, from.zCoord);
		stack = stack.copy();		
		// (1) Try to put stack in pipes that are in valid directions
		for(Entry<ForgeDirection, IPipeEntry> pipe : findPipes(from.worldObj, bp.x, bp.y, bp.z, dropdirections).entrySet())
		{
			if(pipe.getValue().acceptItems())
			{
				pipe.getValue().entityEntering(stack.copy(), pipe.getKey());
				stack.stackSize = 0;
				return stack;
			}
		}
		// (2) Try to put stack in chests that are in valid directions
		for(Entry<ForgeDirection, IInventory> chest : findChests(from.worldObj, bp.x, bp.y, bp.z, dropdirections).entrySet())
		{
			if(chest.getValue() instanceof IDeepStorageUnit)
			{
				IDeepStorageUnit idsu = (IDeepStorageUnit)chest.getValue();
				// don't put stack in a DSU if it has NBT data
				if(stack.getTagCompound() != null)
				{
					continue;
				}
				// don't put stack in a DSU if the stored quantity is nonzero and it's mismatched with the stored item type
				if(idsu.getStoredItemType() != null && (idsu.getStoredItemType().itemID != stack.itemID || idsu.getStoredItemType().getItemDamage() != stack.getItemDamage()))
				{
					continue;
				}
			}
			// Try to stick shit in all non-DSU inventories.
			IInventoryManager manager = InventoryManager.create((IInventory)chest.getValue(), chest.getKey().getOpposite());
			stack = manager.addItem(stack);
			if(stack == null || stack.stackSize == 0)
			{
				return stack;
			}
		}
		// (3) Having failed to put it in a chest or a pipe, throw it on the fucking ground. If airdropdirection is a valid direction.
		System.out.println("Airdropdirection " + airdropdirection.toString() + " is valid: " + Arrays.asList(ForgeDirection.VALID_DIRECTIONS).contains(airdropdirection));
		System.out.println("Block is solid:" + from.worldObj.isBlockSolidOnSide(from.xCoord, from.yCoord, from.zCoord, airdropdirection.getOpposite()));
		if(Arrays.asList(ForgeDirection.VALID_DIRECTIONS).contains(airdropdirection) && !from.worldObj.isBlockSolidOnSide(from.xCoord, from.yCoord, from.zCoord, airdropdirection.getOpposite()))
		{
			System.out.println("EMYDEBUG: yay, the if triggered.");
			dropStackOnGround(stack, bp, from.worldObj, airdropdirection);
			stack.stackSize = 0;
		}
		// (4) Is the stack still here? :( Better give it back.
		return stack;
	}
	
	private static void dropStackOnGround(ItemStack stack, BlockPosition bp, World world, ForgeDirection towards)
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
				dropOffsetY = -0.75F;
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
		
		EntityItem entityitem = new EntityItem(world, bp.x + dropOffsetX, bp.y + dropOffsetY, bp.z + dropOffsetZ, stack.copy());
		entityitem.motionX = 0.0D;
		if(towards != ForgeDirection.DOWN) entityitem.motionY = 0.3D;
		entityitem.motionZ = 0.0D;
		entityitem.delayBeforeCanPickup = 20;
		world.spawnEntityInWorld(entityitem);
		}

	public static ItemStack consumeItem(ItemStack stack)
	{
		if(stack.stackSize == 1)
		{
			if(stack.getItem().hasContainerItem())
			{
				return stack.getItem().getContainerItemStack(stack);
			}
			else
			{
				return null;
			}
		}
		else
		{
			return stack.splitStack(1);
		}	
	}

	public static void mergeStacks(ItemStack to, ItemStack from)
	{
		if(to == null || from == null) return;
		
		if(to.itemID != from.itemID || to.getItemDamage() != from.getItemDamage()) return;
		if(to.getTagCompound() != null || from.getTagCompound() != null) return;
		
		int amountToCopy = Math.min(to.getMaxStackSize() - to.stackSize, from.stackSize);
		to.stackSize += amountToCopy;
		from.stackSize -= amountToCopy;
	}
}