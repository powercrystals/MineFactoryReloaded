package powercrystals.minefactoryreloaded.core;

import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.api.IToolHammer;
import powercrystals.minefactoryreloaded.api.IToolHammerAdvanced;
import powercrystals.minefactoryreloaded.tile.conveyor.TileEntityConveyor;

public class MFRUtil
{
	public static boolean isHoldingHammer(EntityPlayer player)
	{
		if(player.inventory.getCurrentItem() == null)
		{
			return false;
		}
		Item currentItem = Item.itemsList[player.inventory.getCurrentItem().itemID];
		if(currentItem instanceof IToolHammerAdvanced)
		{
			return ((IToolHammerAdvanced)currentItem).isActive(player.inventory.getCurrentItem());
		}
		else if(currentItem instanceof IToolHammer)
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean isHolding(EntityPlayer player, Class<?> itemClass)
	{
		if(player.inventory.getCurrentItem() == null)
		{
			return false;
		}
		Item currentItem = Item.itemsList[player.inventory.getCurrentItem().itemID];
		if(currentItem != null && itemClass.isAssignableFrom(currentItem.getClass()))
		{
			return true;
		}
		return false;
	}
	
	public static Entity prepareMob(Class<? extends Entity> entity, World world)
	{
		try
		{
			Entity e = entity.getConstructor(new Class[] {World.class}).newInstance(new Object[] { world });
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
	
	public static ForgeDirection[] directionsWithoutConveyors(World world, int x, int y, int z)
	{
		ArrayList<ForgeDirection> nonConveyors = new ArrayList<ForgeDirection>();
		for(ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
		{
			BlockPosition bp = new BlockPosition(x, y, z);
			bp.orientation = direction;
			bp.moveForwards(1);
			TileEntity te = world.getBlockTileEntity(bp.x, bp.y, bp.z);
			if(te == null || !(te instanceof TileEntityConveyor))
			{
				nonConveyors.add(direction);
			}
		}
		return nonConveyors.toArray(new ForgeDirection[nonConveyors.size()]);
	}
}
