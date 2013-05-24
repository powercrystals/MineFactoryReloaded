package powercrystals.minefactoryreloaded.block;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.rednet.IConnectableRedNet;
import powercrystals.minefactoryreloaded.api.rednet.RedNetConnectionType;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactory;
import powercrystals.minefactoryreloaded.tile.rednet.TileEntityRedNetHistorian;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class BlockRedNetPanel extends BlockContainer implements IConnectableRedNet
{
	private int[] _blankOutputs = new int[16];
	
	public BlockRedNetPanel(int id)
	{
		super(id, Material.clay);
		setUnlocalizedName("mfr.rednet.panel");
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te instanceof TileEntityFactory)
		{
			if(((TileEntityFactory)te).getDirectionFacing() == ForgeDirection.NORTH)
			{
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
			}
			else if(((TileEntityFactory)te).getDirectionFacing() == ForgeDirection.SOUTH)
			{
				setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
			}
			else if(((TileEntityFactory)te).getDirectionFacing() == ForgeDirection.EAST)
			{
				setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
			else if(((TileEntityFactory)te).getDirectionFacing() == ForgeDirection.WEST)
			{
				setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
			}
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity, ItemStack stack)
	{
		if(entity == null)
		{
			return;
		}
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(stack.getTagCompound() != null)
		{
			stack.getTagCompound().setInteger("x", x);
			stack.getTagCompound().setInteger("y", y);
			stack.getTagCompound().setInteger("z", z);
			te.readFromNBT(stack.getTagCompound());
		}
		
		if(te instanceof TileEntityFactory && ((TileEntityFactory)te).canRotate())
		{
			int facing = MathHelper.floor_double((entity.rotationYaw * 4F) / 360F + 0.5D) & 3;
			if(facing == 0)
			{
				((TileEntityFactory)te).rotateDirectlyTo(3);
			}
			else if(facing == 1)
			{
				((TileEntityFactory)te).rotateDirectlyTo(4);
			}
			else if(facing == 2)
			{
				((TileEntityFactory)te).rotateDirectlyTo(2);
			}
			else if(facing == 3)
			{
				((TileEntityFactory)te).rotateDirectlyTo(5);
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float xOffset, float yOffset, float zOffset)
	{
		PlayerInteractEvent e = new PlayerInteractEvent(entityplayer, Action.RIGHT_CLICK_BLOCK, x, y, z, side);
		if(MinecraftForge.EVENT_BUS.post(e) || e.getResult() == Result.DENY)
		{
			return false;
		}
		
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(MFRUtil.isHoldingHammer(entityplayer) && te instanceof TileEntityFactory && ((TileEntityFactory)te).canRotate())
		{
			((TileEntityFactory)te).rotate();
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		else if(te instanceof TileEntityFactory && ((TileEntityFactory)te).getContainer(entityplayer.inventory) != null)
		{
			entityplayer.openGui(MineFactoryReloadedCore.instance(), 0, world, x, y, z);
			return true;
		}
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public int getRenderType()
	{
		return MineFactoryReloadedCore.renderIdRedNetPanel;
	}
	
	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return null;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata)
	{
		return new TileEntityRedNetHistorian();
	}

	@Override
	public RedNetConnectionType getConnectionType(World world, int x, int y, int z, ForgeDirection side)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te instanceof TileEntityFactory)
		{
			return side == ((TileEntityFactory)te).getDirectionFacing() ? RedNetConnectionType.CableAll : RedNetConnectionType.None;
		}
		return RedNetConnectionType.None;
	}

	@Override
	public int[] getOutputValues(World world, int x, int y, int z, ForgeDirection side)
	{
		return _blankOutputs;
	}

	@Override
	public int getOutputValue(World world, int x, int y, int z, ForgeDirection side, int subnet)
	{
		return 0;
	}

	@Override
	public void onInputsChanged(World world, int x, int y, int z, ForgeDirection side, int[] inputValues)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te instanceof TileEntityRedNetHistorian)
		{
			((TileEntityRedNetHistorian)te).valuesChanged(inputValues);
		}
	}

	@Override
	public void onInputChanged(World world, int x, int y, int z, ForgeDirection side, int inputValue)
	{
	}
}
