package powercrystals.minefactoryreloaded.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityLaserDrill;

public class BlockFakeLaser extends Block
{
	public BlockFakeLaser(int id)
	{
		super(id, Material.air);
		setHardness(-1);
		setResistance(100000000);
		setBlockBounds(0F, 0F, 0F, 0F, 0F, 0F);
	}
	
	@Override
	public int getRenderType()
	{
		return -1;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		entity.setFire(10);
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		return 15;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public boolean isAirBlock(World world, int x, int y, int z)
	{
		return true;
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		world.scheduleBlockUpdate(x, y, z, blockID, 1);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id)
	{
		world.scheduleBlockUpdate(x, y, z, blockID, 1);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		TileEntity upperTE = world.getBlockTileEntity(x, y + 1, z);
		int upperId = world.getBlockId(x, y + 1, z);
		int lowerId = world.getBlockId(x, y - 1, z);
		
		if(!world.isRemote && !(upperTE instanceof TileEntityLaserDrill) && upperId != blockID)
		{
			world.setBlockToAir(x, y, z);
		}
		if(lowerId != blockID && (Block.blocksList[lowerId] == null || Block.blocksList[lowerId].isAirBlock(world, x, y - 1, z)))
		{
			world.setBlock(x, y - 1, z, blockID);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
	}
}
