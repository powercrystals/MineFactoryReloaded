package powercrystals.minefactoryreloaded.rails;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class BlockRailPassengerDropoff extends BlockRail
{
	public BlockRailPassengerDropoff(int blockId, int textureIndex)
	{
		super(blockId, textureIndex, true);
		setBlockName("passengerDropoffRail");
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
 	{
		if(world.isRemote || !(entity instanceof EntityMinecart))
		{
			return;
		}
		EntityMinecart minecart = (EntityMinecart)entity;
		if(minecart.minecartType != 0 || minecart.riddenByEntity == null || !(minecart.riddenByEntity instanceof EntityPlayer))
		{
			return;
		}
		
		int[] dropCoords = findSpaceForPlayer(x, y, z, world);
		if(dropCoords[1] < 0)
		{
			return;
		}
		Entity player = minecart.riddenByEntity;
		player.mountEntity(minecart);
		MineFactoryReloadedCore.proxy.movePlayerToCoordinates((EntityPlayer)player, dropCoords[0] + 0.5, dropCoords[1] + 0.5, dropCoords[2] + 0.5);
	}
	
	private int[] findSpaceForPlayer(int x, int y, int z, World world)
	{
		int[] targetCoords = new int[3];
		targetCoords[1] = -1;
		
		int offsetX;
		int offsetY;
		int offsetZ;
		
		int targetX;
		int targetY;
		int targetZ;
		
		for(offsetX = -MineFactoryReloadedCore.passengerRailSearchMaxHorizontal.getInt(); offsetX < MineFactoryReloadedCore.passengerRailSearchMaxHorizontal.getInt(); offsetX++)
		{
			for(offsetY = -MineFactoryReloadedCore.passengerRailSearchMaxVertical.getInt(); offsetY < MineFactoryReloadedCore.passengerRailSearchMaxVertical.getInt(); offsetY++)
			{
				for(offsetZ = -MineFactoryReloadedCore.passengerRailSearchMaxHorizontal.getInt(); offsetZ < MineFactoryReloadedCore.passengerRailSearchMaxHorizontal.getInt(); offsetZ++)
				{
					targetX = x + offsetX;
					targetY = y + offsetY;
					targetZ = z + offsetZ;
					
					if(world.getBlockId(targetX, targetY, targetZ) == 0 && world.getBlockId(targetX, targetY + 1, targetZ) == 0
							&& !isBadBlockToStandOn(world.getBlockId(targetX, targetY - 1, targetZ)))
					{
						targetCoords[0] = targetX;
						targetCoords[1] = targetY;
						targetCoords[2] = targetZ;
						return targetCoords;
					}
				}
			}
		}
		
		return targetCoords;
	}
	
	private boolean isBadBlockToStandOn(int blockId)
	{
		if(blockId == 0
				|| Block.blocksList[blockId].blockMaterial == Material.lava
				|| Block.blocksList[blockId].blockMaterial == Material.water
				|| Block.blocksList[blockId].blockMaterial == Material.fire
				|| Block.blocksList[blockId] instanceof BlockRail)
		{
			return true;
		}
		return false;
	}

	@Override
	public String getTextureFile()
	{
        return MineFactoryReloadedCore.terrainTexture;
	}
}
