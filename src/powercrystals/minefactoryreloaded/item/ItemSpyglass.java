package powercrystals.minefactoryreloaded.item;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemSpyglass extends ItemFactory
{
	public ItemSpyglass(int id)
	{
		super(id);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(world.isRemote)
		{
			MovingObjectPosition mop = player.rayTrace(MineFactoryReloadedCore.spyglassRange.getInt(), 1.0F);
			if(mop == null || (mop.typeOfHit == EnumMovingObjectType.ENTITY && mop.entityHit == null))
			{
				player.sendChatToPlayer("Nothing in sight");
			}
			else if(mop.typeOfHit == EnumMovingObjectType.ENTITY)
			{
				player.sendChatToPlayer("Found a " + getEntityName(mop.entityHit) + " at " + mop.entityHit.posX + ", " + mop.entityHit.posY + ", " + mop.entityHit.posZ);
			}
			else
			{
				ItemStack tempStack = new ItemStack(world.getBlockId(mop.blockX, mop.blockY, mop.blockZ), 1, world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ));
				if(tempStack.getItem() != null)
				{
					player.sendChatToPlayer("Found " + tempStack.getDisplayName() + " at " + mop.blockX + ", " + mop.blockY + ", " + mop.blockZ);
				}
				else
				{
					player.sendChatToPlayer("Found UNKNOWN (bugged mod?) at " + mop.blockX + ", " + mop.blockY + ", " + mop.blockZ);
				}
			}
		}
		
		return super.onItemRightClick(stack, world, player);
	}
	
	private String getEntityName(Entity entity)
	{
		String name = (String)EntityList.classToStringMapping.get(entity.getClass());
		return name != null ? name : "Unknown Entity";
	}
}
