package powercrystals.minefactoryreloaded.block;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import powercrystals.minefactoryreloaded.tile.TileRedstoneCable;
import powercrystals.minefactoryreloaded.tile.TileRedstoneCable.ConnectionState;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockRedstoneCable extends BlockContainer
{
	public BlockRedstoneCable(int id)
	{
		super(id, Material.clay);
		
		setUnlocalizedName("mfr.cable.redstone");
		setCreativeTab(MFRCreativeTab.tab);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
	{
		if(world.isRemote)
		{
			return false;
		}
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileRedstoneCable)
		{
			TileRedstoneCable cable = (TileRedstoneCable)te;
			int nextColor = cable.getSideColor(ForgeDirection.VALID_DIRECTIONS[side]) + 1;
			if(nextColor > 15) nextColor = 0;
			cable.setSideColor(ForgeDirection.VALID_DIRECTIONS[side], nextColor);
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		return false;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileRedstoneCable)
		{
			TileRedstoneCable cable = (TileRedstoneCable)te;
			float xMin = cable.getConnectionState(ForgeDirection.WEST) != ConnectionState.None ? 0 : 0.375F;
			float xMax = cable.getConnectionState(ForgeDirection.EAST) != ConnectionState.None ? 1 : 0.625F;
			float yMin = cable.getConnectionState(ForgeDirection.DOWN) != ConnectionState.None ? 0 : 0.375F;
			float yMax = cable.getConnectionState(ForgeDirection.UP) != ConnectionState.None ? 1 : 0.625F;
			float zMin = cable.getConnectionState(ForgeDirection.NORTH) != ConnectionState.None ? 0 : 0.375F;
			float zMax = cable.getConnectionState(ForgeDirection.SOUTH) != ConnectionState.None ? 1 : 0.625F;
			setBlockBounds(xMin, yMin, zMin, xMax, yMax, zMax);
		}
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
		int power = 0;
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileRedstoneCable && ((TileRedstoneCable)te).getNetwork() != null)
		{
			power = ((TileRedstoneCable)te).getNetwork().getPowerLevelOutput();
			//System.out.println("Asked for weak power at " + x + "," + y + "," + z + " - got " + power + " from network " + ((TileRedstoneCable)te).getNetwork().getId());
		}
		else
		{
			//System.out.println("Asked for weak power at " + x + "," + y + "," + z + " - no tile or no network!");
		}
		return power;
	}
	
	@Override
	public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side)
	{
		int power = 0;
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileRedstoneCable && ((TileRedstoneCable)te).getNetwork() != null)
		{
			power = ((TileRedstoneCable)te).getNetwork().getPowerLevelOutput();
			//System.out.println("Asked for strong power at " + x + "," + y + "," + z + " - got " + power + " from network " + ((TileRedstoneCable)te).getNetwork().getId());
		}
		else
		{
			//System.out.println("Asked for strong power at " + x + "," + y + "," + z + " - no tile or no network!");
		}
		return power;
	}
	
	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileRedstoneCable && ((TileRedstoneCable)te).getNetwork() != null)
		{
			return ((TileRedstoneCable)te).getConnectionState(side) == ConnectionState.FlatSingle;
		}
		return false;
	}
	
	@Override
	public boolean canProvidePower()
	{
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileRedstoneCable();
	}
	
	@Override
	public int getRenderType()
	{
		return MineFactoryReloadedCore.renderIdRedstoneCable;
	}
}
