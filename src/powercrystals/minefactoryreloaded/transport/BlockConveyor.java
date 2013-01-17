package powercrystals.minefactoryreloaded.transport;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import powercrystals.core.position.IRotateableTile;
import powercrystals.core.util.Util;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.MFRUtil;

public class BlockConveyor extends BlockContainer
{
	public BlockConveyor(int i, int j)
	{
		super(i, j, Material.circuits);
		setHardness(0.5F);
		setBlockName("factoryConveyor");
		setBlockBounds(0.0F, 0.0F, 0.0F, 0.1F, 0.1F, 0.1F);
		setRequiresSelfNotify();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity)
	{
		if(entity == null)
		{
			return;
		}
		int l = MathHelper.floor_double((double)((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		if(l == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 1);
		}
		if(l == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2);
		}
		if(l == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3);
		}
		if(l == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 0);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
	{
		if(!(entity instanceof EntityItem) && !(entity instanceof EntityLiving))
		{
			return;
		}
		if(Util.isRedstonePowered(world.getBlockTileEntity(i, j, k)))
		{
			return;
		}
		
		int md = world.getBlockMetadata(i, j, k);
		if(md == 4)
		{
			setEntityVelocity(entity, 0.1D, 0.2D, 0.0D);
			entity.onGround = false;
		}
		else if(md == 5)
		{
			setEntityVelocity(entity, 0.0D, 0.2D, 0.1D);
			entity.onGround = false;
		}
		else if(md == 6)
		{
			setEntityVelocity(entity, -0.1D, 0.2D, 0.0D);
			entity.onGround = false;
		}
		else if(md == 7)
		{
			setEntityVelocity(entity, 0.0D, 0.2D, -0.1D);
			entity.onGround = false;
		}
		else if(md == 8)
		{
			setEntityVelocity(entity, 0.1D, 0.0D, 0.0D);
			entity.onGround = false;
		}
		else if(md == 9)
		{
			setEntityVelocity(entity, 0.0D, 0.0D, 0.1D);
			entity.onGround = false;
		}
		else if(md == 10)
		{
			setEntityVelocity(entity, -0.1D, 0.0D, 0.0D);
			entity.onGround = false;
		}
		else if(md == 11)
		{
			setEntityVelocity(entity, 0.0D, 0.0D, -0.1D);
			entity.onGround = false;
		}
		else if(md == 0)
		{
			if(entity.posZ > (double)k + 0.55D)
			{
				setEntityVelocity(entity, 0.05D, 0.0D, -0.05D);
			}
			else if(entity.posZ < (double)k + 0.45D)
			{
				setEntityVelocity(entity, 0.05D, 0.0D, 0.05D);
			}
			else
			{
				setEntityVelocity(entity, 0.1D, 0.0D, 0.0D);
			}
		}
		else if(md == 1)
		{
			if(entity.posX > (double)i + 0.55D)
			{
				setEntityVelocity(entity, -0.05D, 0.0D, 0.05D);
			}
			else if(entity.posX < (double)i + 0.45D)
			{
				setEntityVelocity(entity, 0.05D, 0.0D, 0.05D);
			}
			else
			{
				setEntityVelocity(entity, 0.0D, 0.0D, 0.1D);
			}
		}
		else if(md == 2)
		{
			if(entity.posZ > (double)k + 0.55D)
			{
				setEntityVelocity(entity, -0.05D, 0.0D, -0.05D);
			}
			else if(entity.posZ < (double)k + 0.45D)
			{
				setEntityVelocity(entity, -0.05D, 0.0D, 0.05D);
			}
			else
			{
				setEntityVelocity(entity, -0.1D, 0.0D, 0.0D);
			}
		}
		else if(md == 3)
		{
			if(entity.posX > (double)i + 0.55D)
			{
				setEntityVelocity(entity, -0.05D, 0.0D, -0.05D);
			}
			else if(entity.posX < (double)i + 0.45D)
			{
				setEntityVelocity(entity, 0.05D, 0.0D, -0.05D);
			}
			else
			{
				setEntityVelocity(entity, 0.0D, 0.0D, -0.1D);
			}
		}
		
		if(entity instanceof EntityLiving)
		{
			((EntityLiving)entity).fallDistance = 0;
		}
	}
	
	@Override
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
	{
		TileEntity te = iblockaccess.getBlockTileEntity(i, j, k);
		if(te != null && te instanceof TileEntityConveyor)
		{
			if(Util.isRedstonePowered(te))
			{
				if(MineFactoryReloadedCore.animateBlockFaces.getBoolean(true))
				{
					return MineFactoryReloadedCore.conveyorOffTexture;
				}
				else
				{
					return MineFactoryReloadedCore.conveyorStillOffTexture;
				}
			}
			else
			{
				return blockIndexInTexture;
			}
		}
		else
		{
			return blockIndexInTexture;
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		int l = world.getBlockMetadata(i, j, k);
		float f = 0.2F;
		float f1 = 0.2F;
		if(l == 0 || l == 2)
		{
			f = 0.05F;
			f1 = 0.05F;
		}
		else if(l == 1 || l == 3)
		{
			f = 0.05F;
			f1 = 0.05F;
		}
		return AxisAlignedBB.getBoundingBox((float)i + f, j, (float)k + f1, (float)(i + 1) - f, (float)j + 0.1F, (float)(k + 1) - f1);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
	{
		int l = world.getBlockMetadata(i, j, k);
		float f = 0.2F;
		float f1 = 0.2F;
		if(l == 0 || l == 2)
		{
			f = 0.05F;
			f1 = 0.05F;
		}
		else if(l == 1 || l == 3)
		{
			f = 0.05F;
			f1 = 0.05F;
		}
		return AxisAlignedBB.getBoundingBox((float)i + f, j, (float)k + f1, (float)(i + 1) - f, (float)j + 0.1F, (float)(k + 1) - f1);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int i, int j, int k, Vec3 vec3d, Vec3 vec3d1)
	{
		setBlockBoundsBasedOnState(world, i, j, k);
		return super.collisionRayTrace(world, i, j, k, vec3d, vec3d1);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
	{
		int l = iblockaccess.getBlockMetadata(i, j, k);
		if(l >= 4 && l <= 11)
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}
		else
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		}
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return MineFactoryReloadedCore.renderIdConveyor;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 1;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		return world.isBlockOpaqueCube(i, j - 1, k);
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k)
	{
		super.onBlockAdded(world, i, j, k);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if(MFRUtil.isHoldingWrench(entityplayer))
		{
			TileEntity te = world.getBlockTileEntity(i, j, k);
			if(te != null && te instanceof IRotateableTile)
			{
				((IRotateableTile)te).rotate();
			}
		}
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		if(!world.isRemote && !world.isBlockOpaqueCube(i, j - 1, k))
		{
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockWithNotify(i, j, k, 0);
		}
	}
	
	private void setEntityVelocity(Entity e, double x, double y, double z)
	{
		e.motionX = x;
		e.motionY = y;
		e.motionZ = z;
	}

	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}

	@Override
	public boolean canProvidePower()
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1)
	{
		return new TileEntityConveyor();
	}
}
