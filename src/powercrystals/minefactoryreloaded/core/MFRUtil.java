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
import powercrystals.minefactoryreloaded.core.MFRInventoryUtil;
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
	 * dropStack here should be equivalent to MFRInventoryUtil.dropStack(from, s, towards, towards) 
	 */
	@Deprecated
	public static ItemStack dropStackDirected(TileEntityFactory from, ItemStack s, ForgeDirection towards)
	{
		return MFRInventoryUtil.dropStack(from, s, towards, towards);
	}
	
	/**
	 * Methods from MFRInventoryUtil should be used instead. 
	 * dropStack here should be equivalent to MFRInventoryUtil.dropStack(from, s, from.getDropDirection()) 
	 */
	@Deprecated
	public static void dropStack(TileEntityFactory from, ItemStack s)
	{
		MFRInventoryUtil.dropStack(from, s, from.getDropDirection());
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
