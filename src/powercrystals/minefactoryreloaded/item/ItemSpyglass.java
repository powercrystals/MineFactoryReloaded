package powercrystals.minefactoryreloaded.item;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.setup.MFRConfig;

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
			MovingObjectPosition mop = rayTrace();
			if(mop == null || (mop.typeOfHit == EnumMovingObjectType.ENTITY && mop.entityHit == null))
			{
				player.sendChatToPlayer("Nothing in sight");
			}
			else if(mop.typeOfHit == EnumMovingObjectType.ENTITY)
			{
				player.sendChatToPlayer(String.format("Found a %s at %.1f, %.1f, %.1f", getEntityName(mop.entityHit), mop.entityHit.posX, mop.entityHit.posY, mop.entityHit.posZ));
			}
			else
			{
				ItemStack tempStack = new ItemStack(world.getBlockId(mop.blockX, mop.blockY, mop.blockZ), 1, world.getBlockMetadata(mop.blockX, mop.blockY,
						mop.blockZ));
				if(tempStack.getItem() != null)
				{
					player.sendChatToPlayer("Found " + tempStack.getDisplayName() + " [" + world.getBlockId(mop.blockX, mop.blockY, mop.blockZ) + ":"
							+ world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ) + "] at " + mop.blockX + ", " + mop.blockY + ", " + mop.blockZ);
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
		return name != null ? StatCollector.translateToLocal("entity." + name + ".name") : "Unknown Entity";
	}
	
	private MovingObjectPosition rayTrace()
	{
		if(Minecraft.getMinecraft().renderViewEntity == null || Minecraft.getMinecraft().theWorld == null)
		{
			return null;
		}
		
		double range = MFRConfig.spyglassRange.getInt();
		MovingObjectPosition objHit = Minecraft.getMinecraft().renderViewEntity.rayTrace(range, 1.0F);
		double blockDist = range;
		Vec3 playerPos = Minecraft.getMinecraft().renderViewEntity.getPosition(1.0F);
		
		if(objHit != null)
		{
			blockDist = objHit.hitVec.distanceTo(playerPos);
		}
		
		Vec3 playerLook = Minecraft.getMinecraft().renderViewEntity.getLook(1.0F);
		Vec3 playerLookRel = playerPos.addVector(playerLook.xCoord * range, playerLook.yCoord * range, playerLook.zCoord * range);
		List<?> list = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABBExcludingEntity(
				Minecraft.getMinecraft().renderViewEntity,
				Minecraft.getMinecraft().renderViewEntity.boundingBox.addCoord(playerLook.xCoord * range, playerLook.yCoord * range, playerLook.zCoord * range).expand(1, 1, 1));
		
		double entityDistTotal = blockDist;
		Entity pointedEntity = null;
		for(int i = 0; i < list.size(); ++i)
		{
			Entity entity = (Entity)list.get(i);
			
			if(entity.canBeCollidedWith())
			{
				double entitySize = entity.getCollisionBorderSize();
				AxisAlignedBB axisalignedbb = entity.boundingBox.expand(entitySize, entitySize, entitySize);
				MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(playerPos, playerLookRel);
				
				if(axisalignedbb.isVecInside(playerPos))
				{
					if(0.0D < entityDistTotal || entityDistTotal == 0.0D)
					{
						pointedEntity = entity;
						entityDistTotal = 0.0D;
					}
				}
				else if(movingobjectposition != null)
				{
					double entityDist = playerPos.distanceTo(movingobjectposition.hitVec);
					
					if(entityDist < entityDistTotal || entityDistTotal == 0.0D)
					{
						pointedEntity = entity;
						entityDistTotal = entityDist;
					}
				}
			}
		}
		
		if(pointedEntity != null && (entityDistTotal < blockDist || objHit == null))
		{
			objHit = new MovingObjectPosition(pointedEntity);
		}
		return objHit;
	}
}
