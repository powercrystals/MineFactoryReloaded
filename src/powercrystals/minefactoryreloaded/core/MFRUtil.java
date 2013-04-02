package powercrystals.minefactoryreloaded.core;

import java.util.Map.Entry;

import buildcraft.api.transport.IPipeEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.inventory.IInventoryManager;
import powercrystals.core.inventory.InventoryManager;
import powercrystals.core.position.BlockPosition;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.api.IToolHammer;
import powercrystals.minefactoryreloaded.api.IDeepStorageUnit;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactory;

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
	
	/**
	 * Methods from MFRInventoryUtil should be used instead. 
	 */
	@Deprecated
	public static ItemStack dropStackDirected(TileEntityFactory from, ItemStack s, ForgeDirection towards)
	{
		if(s == null)
		{
			return null;
		}
		s = s.copy();
		
		BlockPosition bp = new BlockPosition(from.xCoord, from.yCoord, from.zCoord);
		bp.orientation = towards;
		bp.moveForwards(1);
		TileEntity te = from.worldObj.getBlockTileEntity(bp.x, bp.y, bp.z);
		if(te != null && te instanceof IInventory)
		{
			IInventoryManager manager = InventoryManager.create((IInventory)te, towards.getOpposite());
			s = manager.addItem(s);
		}
		else if(te != null && te instanceof IPipeEntry && ((IPipeEntry)te).acceptItems())
		{
			((IPipeEntry)te).entityEntering(s.copy(), towards);
			s.stackSize = 0;
		}
		
		if(s != null && s.stackSize > 0 && !from.worldObj.isBlockSolidOnSide(bp.x, bp.y, bp.z, towards.getOpposite()))
		{
			dropStackOnGround(s, BlockPosition.fromFactoryTile(from), from.worldObj, towards);
		}
		return s;
	}
	
	/**
	 * Methods from MFRInventoryUtil should be used instead. 
	 * dropStack here should be equivalent to MFRInventoryUtil.dropStack(from, s, from.getDropDirection()) 
	 */
	@Deprecated
	public static void dropStack(TileEntityFactory from, ItemStack s)
	{
		if(s == null)
		{
			return;
		}
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
		
		for(Entry<ForgeDirection, IInventory> chest : UtilInventory.findChests(from.worldObj, from.xCoord, from.yCoord, from.zCoord).entrySet())
		{
			if(chest.getValue().getInvName() == "Engine")
			{
				continue;
			}
			if(chest.getValue() instanceof IDeepStorageUnit)
			{
				IDeepStorageUnit idsu = (IDeepStorageUnit)chest.getValue();
				// don't put s in a DSU if it has NBT data
				if(s.getTagCompound() != null)
				{
					continue;
				}
				// don't put s in a DSU if the stored quantity is nonzero and it's mismatched with the stored item type
				if(idsu.getStoredItemType() != null && (idsu.getStoredItemType().itemID != s.itemID || idsu.getStoredItemType().getItemDamage() != s.getItemDamage()))
				{
					continue;
				}
			}
			IInventoryManager manager = InventoryManager.create((IInventory)chest.getValue(), chest.getKey().getOpposite());
			s = manager.addItem(s);
			if(s == null || s.stackSize == 0)
			{
				return;
			}
		}
		
		if(s != null && s.stackSize > 0)
		{
			dropStackOnGround(s, BlockPosition.fromFactoryTile(from), from.worldObj, from.getDropDirection());
		}
	}
	
	/**
	 * Methods from MFRInventoryUtil should be used instead. 
	 * the one in MFRInventoryUtil is -almost- identical, except that the MFRInventoryUtil version doesn't modify the itemstack.  
	 */
	@Deprecated
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
		
		EntityItem entityitem = new EntityItem(world, from.x + dropOffsetX, from.y + dropOffsetY, from.z + dropOffsetZ, stack.copy());
		entityitem.motionX = 0.0D;
		if(towards != ForgeDirection.DOWN) entityitem.motionY = 0.3D;
		entityitem.motionZ = 0.0D;
		entityitem.delayBeforeCanPickup = 20;
		world.spawnEntityInWorld(entityitem);
		stack.stackSize = 0;
	}

	public static Entity prepareMob(Class<? extends Entity> entity, World world)
	{
		try
		{
			Entity e = (Entity)entity.getConstructor(new Class[] {World.class}).newInstance(new Object[] { world });
			if(e instanceof EntityLiving)
			{
				((EntityLiving)e).initCreature();
			}
			return e;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
