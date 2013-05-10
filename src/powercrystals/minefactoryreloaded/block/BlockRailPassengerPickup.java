package powercrystals.minefactoryreloaded.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRailPassengerPickup extends BlockRailBase
{
	public BlockRailPassengerPickup(int blockId)
	{
		super(blockId, true);
		setUnlocalizedName("mfr.rail.passenger.pickup");
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
		setCreativeTab(MFRCreativeTab.tab);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if(world.isRemote || !(entity instanceof EntityMinecartEmpty))
		{
			return;
		}
		EntityMinecart minecart = (EntityMinecart)entity;
		if(minecart.riddenByEntity != null)
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
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName());
	}
}
