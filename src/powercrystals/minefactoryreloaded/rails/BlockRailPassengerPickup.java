package powercrystals.minefactoryreloaded.rails;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class BlockRailPassengerPickup extends BlockRail
{
	public BlockRailPassengerPickup(int blockId, int textureIndex)
	{
		super(blockId, textureIndex, true);
		setBlockName("passengerPickupRail");
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
		if(minecart.minecartType != 0 || minecart.riddenByEntity != null)
		{
			return;
		}
		
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(
				x - MineFactoryReloadedCore.passengerRailSearchMaxHorizontal.getInt(),
				y - MineFactoryReloadedCore.passengerRailSearchMaxVertical.getInt(),
				z - MineFactoryReloadedCore.passengerRailSearchMaxHorizontal.getInt(),
				x + MineFactoryReloadedCore.passengerRailSearchMaxHorizontal.getInt() + 1,
				y + MineFactoryReloadedCore.passengerRailSearchMaxVertical.getInt() + 1,
				z + MineFactoryReloadedCore.passengerRailSearchMaxHorizontal.getInt() + 1);
		
		@SuppressWarnings("rawtypes")
		List entities = world.getEntitiesWithinAABB(EntityPlayer.class, bb);
		
		for(Object o : entities)
		{
			if(!(o instanceof EntityPlayer))
			{
				continue;
			}
			((EntityPlayer)o).mountEntity(minecart);
			return;
		}
	}

	@Override
	public String getTextureFile()
	{
        return MineFactoryReloadedCore.terrainTexture;
	}
}
