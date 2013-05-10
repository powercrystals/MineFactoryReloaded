package powercrystals.minefactoryreloaded.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.tile.TileEntityRedNetLogic;
import powercrystals.minefactoryreloaded.tile.TileRedstoneCable;

public class ItemRedNetMeter extends ItemFactory
{
	private static String[] _colorNames = new String[] { "White", "Orange", "Magenta", "LightBlue", "Yellow", "Lime", "Pink", "Gray",
		"LightGray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black" };
	
	public ItemRedNetMeter(int id)
	{
		super(id);
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float xOffset, float yOffset, float zOffset)
	{
		if(world.isRemote)
		{
			return true;
		}
		
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te instanceof TileRedstoneCable)
		{
			int value;
			boolean foundNonZero = false;
			for(int i = 0; i < 16; i++)
			{
				value = ((TileRedstoneCable)te).getNetwork().getPowerLevelOutput(i);
				
				if(value != 0)
				{
					player.sendChatToPlayer(_colorNames[i] + ": " + value);
					foundNonZero = true;
				}
			}
			
			if(!foundNonZero)
			{
				player.sendChatToPlayer("All RedNet subnets are 0");
			}
			else
			{
				player.sendChatToPlayer("All other RedNet subnets are 0");
			}
			
			return true;
		}
		else if(te instanceof TileEntityRedNetLogic)
		{
			int value;
			boolean foundNonZero = false;
			for(int i = 0; i < ((TileEntityRedNetLogic)te).getBufferLength(13); i++)
			{
				value = ((TileEntityRedNetLogic)te).getVariableValue(i);
				
				if(value != 0)
				{
					player.sendChatToPlayer("Variable " + i +  ": " + value);
					foundNonZero = true;
				}
			}
			
			if(!foundNonZero)
			{
				player.sendChatToPlayer("All variables are 0");
			}
			else
			{
				player.sendChatToPlayer("All other variables are 0");
			}
			
			return true;
		}
		return false;
	}
}
