package powercrystals.minefactoryreloaded.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.IToolHammer;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import powercrystals.minefactoryreloaded.tile.TileRedstoneCable;
import powercrystals.minefactoryreloaded.tile.TileRedstoneCable.ConnectionState;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockRedstoneCable extends BlockContainer
{
	private static float _wireSize = 0.25F;
	private static float _plateWidth = 14.0F / 16.0F;
	private static float _plateDepth = 1.0F / 16.0F;
	private static float _bandWidth = 5.0F / 16.0F;
	private static float _bandOffset = 2.0F / 16.0F;
	private static float _bandDepth = 1.0F / 16.0F;
	
	private static float _wireStart = 0.5F - _wireSize / 2.0F;
	private static float _wireEnd = 0.5F + _wireSize / 2.0F;
	private static float _plateStart = 0.5F - _plateWidth / 2.0F;
	private static float _plateEnd = 0.5F + _plateWidth / 2.0F;
	private static float _bandWidthStart = 0.5F - _bandWidth / 2.0F;
	private static float _bandWidthEnd = 0.5F + _bandWidth / 2.0F;
	
	private static float _bandDepthStart = _bandOffset;
	private static float _bandDepthEnd = _bandOffset + _bandDepth;
	
	private static int[] _partSideMappings = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 5, 0, 1, 2, 3 };
	
	public BlockRedstoneCable(int id)
	{
		super(id, Material.clay);
		
		setUnlocalizedName("mfr.cable.redstone");
		setHardness(0.8F);
		
		setCreativeTab(MFRCreativeTab.tab);
	}
	
	private AxisAlignedBB[] getParts(TileRedstoneCable cable)
	{
		ConnectionState csu = cable.getConnectionState(ForgeDirection.UP);
		ConnectionState csd = cable.getConnectionState(ForgeDirection.DOWN);
		ConnectionState csn = cable.getConnectionState(ForgeDirection.NORTH);
		ConnectionState css = cable.getConnectionState(ForgeDirection.SOUTH);
		ConnectionState csw = cable.getConnectionState(ForgeDirection.WEST);
		ConnectionState cse = cable.getConnectionState(ForgeDirection.EAST); 
		
		AxisAlignedBB[] parts = new AxisAlignedBB[15];
		
		parts[0] = AxisAlignedBB.getBoundingBox(csw != ConnectionState.None ? 0 : _wireStart, _wireStart, _wireStart, cse != ConnectionState.None ? 1 : _wireEnd, _wireEnd, _wireEnd);
		parts[1] = AxisAlignedBB.getBoundingBox(_wireStart, csd != ConnectionState.None ? 0 : _wireStart, _wireStart, _wireEnd, csu != ConnectionState.None ? 1 : _wireEnd, _wireEnd);
		parts[2] = AxisAlignedBB.getBoundingBox(_wireStart, _wireStart, csn != ConnectionState.None ? 0 : _wireStart, _wireEnd, _wireEnd, css != ConnectionState.None ? 1 : _wireEnd);
		
		parts[3] = csw != ConnectionState.FlatSingle ? null : AxisAlignedBB.getBoundingBox(0, _plateStart, _plateStart, _plateDepth, _plateEnd, _plateEnd);
		parts[4] = cse != ConnectionState.FlatSingle ? null : AxisAlignedBB.getBoundingBox(1.0F - _plateDepth, _plateStart, _plateStart, 1.0F, _plateEnd, _plateEnd);
		parts[5] = csd != ConnectionState.FlatSingle ? null : AxisAlignedBB.getBoundingBox(_plateStart, 0 , _plateStart, _plateEnd, _plateDepth, _plateEnd);
		parts[6] = csu != ConnectionState.FlatSingle ? null : AxisAlignedBB.getBoundingBox(_plateStart, 1.0F - _plateDepth, _plateStart, _plateEnd, 1.0F, _plateEnd);
		parts[7] = csn != ConnectionState.FlatSingle ? null : AxisAlignedBB.getBoundingBox(_plateStart, _plateStart, 0, _plateEnd, _plateDepth, _plateEnd);
		parts[8] = css != ConnectionState.FlatSingle ? null : AxisAlignedBB.getBoundingBox(_plateStart, _plateStart, 1.0F - _plateDepth, _plateEnd, _plateEnd, 1.0F);
		
		parts[9]  = csw != ConnectionState.FlatSingle && csw != ConnectionState.CableSingle ? null : AxisAlignedBB.getBoundingBox(_bandDepthStart, _bandWidthStart, _bandWidthStart, _bandDepthEnd, _bandWidthEnd, _bandWidthEnd);
		parts[10] = cse != ConnectionState.FlatSingle && cse != ConnectionState.CableSingle ? null : AxisAlignedBB.getBoundingBox(1.0F - _bandDepthEnd, _bandWidthStart, _bandWidthStart, 1.0F - _bandDepthStart, _bandDepthEnd, _bandWidthEnd);
		parts[11] = csd != ConnectionState.FlatSingle && csd != ConnectionState.CableSingle ? null : AxisAlignedBB.getBoundingBox(_bandWidthStart, _bandDepthStart, _bandWidthStart, _bandWidthEnd, _bandDepthEnd, _bandWidthEnd);
		parts[12] = csu != ConnectionState.FlatSingle && csu != ConnectionState.CableSingle ? null : AxisAlignedBB.getBoundingBox(_bandWidthStart, 1.0F - _bandDepthEnd, _bandWidthStart, _bandWidthEnd, 1.0F - _bandDepthStart, _bandWidthEnd);
		parts[13] = csn != ConnectionState.FlatSingle && csn != ConnectionState.CableSingle ? null : AxisAlignedBB.getBoundingBox(_bandWidthStart, _bandWidthStart, _bandDepthStart, _bandWidthEnd, _bandWidthEnd, _bandDepthEnd);
		parts[14] = css != ConnectionState.FlatSingle && css != ConnectionState.CableSingle ? null : AxisAlignedBB.getBoundingBox(_bandWidthStart, _bandWidthStart, 1.0F - _bandDepthEnd, _bandWidthEnd, _bandWidthEnd, 1.0F - _bandDepthStart);
				
		return parts;
	}
	
	private int getPartClicked(EntityPlayer player, double reachDistance, TileRedstoneCable cable)
	{
		AxisAlignedBB[] wireparts = getParts(cable);
		
		Vec3 playerPosition = Vec3.createVectorHelper(player.posX - cable.xCoord, player.posY - cable.yCoord + player.getEyeHeight(), player.posZ - cable.zCoord);
		Vec3 playerLook = player.getLookVec();
		 
		Vec3 playerViewOffset = Vec3.createVectorHelper(playerPosition.xCoord + playerLook.xCoord * reachDistance, playerPosition.yCoord + playerLook.yCoord * reachDistance, playerPosition.zCoord + playerLook.zCoord * reachDistance);
		int closest = -1;
		double closestdistance = Double.MAX_VALUE;
		
		for(int i = 0; i < wireparts.length; i++)
		{
			AxisAlignedBB part = wireparts[i];
			if(part == null)
			{
				continue;
			}
			MovingObjectPosition hit = part.calculateIntercept(playerPosition, playerViewOffset);
			if(hit != null)
			{
				double distance = playerPosition.distanceTo(hit.hitVec);
				if(distance < closestdistance)
				{
					distance = closestdistance;
					closest = i;
				}
			}
		}
		return closest;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileRedstoneCable)
		{
			TileRedstoneCable cable = (TileRedstoneCable)te;

			int subHit = getPartClicked(player, 3.0F, cable);
			
			if(subHit >= 9)
			{
				ItemStack s = player.inventory.getCurrentItem();
				if(s != null && s.getItem() instanceof IToolHammer)
				{
					if(!world.isRemote)
					{
						side = _partSideMappings[subHit];
						int nextColor = cable.getSideColor(ForgeDirection.VALID_DIRECTIONS[side]) + 1;
						if(nextColor > 15) nextColor = 0;
						cable.setSideColor(ForgeDirection.VALID_DIRECTIONS[side], nextColor);
						world.markBlockForUpdate(x, y, z);
						return true;
					}
				}
				else if(s != null && s.itemID == Item.dyePowder.itemID)
				{
					if(!world.isRemote)
					{
						side = _partSideMappings[subHit];
						cable.setSideColor(ForgeDirection.VALID_DIRECTIONS[side], s.getItemDamage());
						world.markBlockForUpdate(x, y, z);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		super.setBlockBoundsBasedOnState(world, x, y, z);
		/*
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
		}*/
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
			int subnet = ((TileRedstoneCable)te).getSideColor(ForgeDirection.VALID_DIRECTIONS[side].getOpposite());
			power = ((TileRedstoneCable)te).getNetwork().getPowerLevelOutput(subnet);
			//System.out.println("Asked for weak power at " + x + "," + y + "," + z + " - got " + power + " from network " + ((TileRedstoneCable)te).getNetwork().getId() + ":" + subnet);
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
			int subnet = ((TileRedstoneCable)te).getSideColor(ForgeDirection.VALID_DIRECTIONS[side].getOpposite());
			power = ((TileRedstoneCable)te).getNetwork().getPowerLevelOutput(subnet);
			//System.out.println("Asked for strong power at " + x + "," + y + "," + z + " - got " + power + " from network " + ((TileRedstoneCable)te).getNetwork().getId() + ":" + subnet);
		}
		return power;
	}
	
	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
	{
		return true;
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
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
		blockIcon = ir.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName());
	}
}
