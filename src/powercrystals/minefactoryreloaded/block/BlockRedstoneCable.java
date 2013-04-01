package powercrystals.minefactoryreloaded.block;

import powercrystals.minefactoryreloaded.tile.TileRedstoneCable;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneCable extends BlockContainer
{
	public BlockRedstoneCable(int id)
	{
		super(id, Material.clay);
		setUnlocalizedName("mfr.cable.redstone");
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId)
	{
		super.onNeighborBlockChange(world, x, y, z, blockId);
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileRedstoneCable)
		{
			((TileRedstoneCable)te).onNeighboorChanged();
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileRedstoneCable && ((TileRedstoneCable)te).getNetwork() != null)
		{
			((TileRedstoneCable)te).getNetwork().setInvalid();
		}
		super.breakBlock(world, x, y, z, id, meta);
	}
	
	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side)
	{
		return 0;
	}
	
	@Override
	public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileRedstoneCable && ((TileRedstoneCable)te).getNetwork() != null)
		{
			return ((TileRedstoneCable)te).getNetwork().getPowerLevelOutput();
		}
		return 0;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileRedstoneCable();
	}
}
