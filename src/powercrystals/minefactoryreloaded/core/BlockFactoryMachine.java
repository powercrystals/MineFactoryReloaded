package powercrystals.minefactoryreloaded.core;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import powercrystals.minefactoryreloaded.processing.TileEntityLiquiCrafter;
import powercrystals.minefactoryreloaded.transport.TileEntityDeepStorageUnit;
import powercrystals.minefactoryreloaded.transport.TileEntityItemRouter;
import powercrystals.minefactoryreloaded.transport.TileEntityLiquidRouter;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockFactoryMachine extends BlockContainer
{
	public BlockFactoryMachine(int blockId)
	{
		super(blockId, 0, Material.clay);
		setHardness(0.5F);
		setStepSound(soundMetalFootstep);
		setCreativeTab(MFRCreativeTab.tab);
	}

	@Override
	public int getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side)
	{
		int md = iblockaccess.getBlockMetadata(x, y, z);
		TileEntity te = iblockaccess.getBlockTileEntity(x, y, z);
		if(te instanceof TileEntityFactory)
		{
			return (md * 16) + ((TileEntityFactory)te).getRotatedSide(side) + (((TileEntityFactory)te).getIsActive() ? 0 : 6);
		}
		return  (md * 16) + side;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		if(side > 1)
		{
			side += 2;
			if(side > 5)
			{
				side -= 4;
			}
		}
		return meta * 16 + side;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity)
	{
		if(entity == null)
		{
			return;
		}
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te instanceof TileEntityFactory && ((TileEntityFactory)te).canRotate())
		{
			int facing = MathHelper.floor_double((double)((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3;
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
	public int damageDropped(int i)
	{
		return i;
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te == null)
		{
			return false;
		}
		if(MFRUtil.isHoldingWrench(entityplayer) && te instanceof TileEntityFactory && ((TileEntityFactory)te).canRotate())
		{
			((TileEntityFactory)te).rotate();
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		else if(te instanceof TileEntityFactoryPowered || te instanceof TileEntityItemRouter || te instanceof TileEntityLiquidRouter || te instanceof TileEntityDeepStorageUnit || te instanceof TileEntityLiquiCrafter)
		{
			entityplayer.openGui(MineFactoryReloadedCore.instance(), 0, world, x, y, z);
			return true;
		}
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof IInventory)
		{
		
			IInventory inventory = ((IInventory)te);
label0:
			for(int l = 0; l < inventory.getSizeInventory(); l++)
			{
				ItemStack itemstack = inventory.getStackInSlot(l);
				if(itemstack == null)
				{
					continue;
				}
				float f = world.rand.nextFloat() * 0.8F + 0.1F;
				float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
				float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
				do
				{
					if(itemstack.stackSize <= 0)
					{
						continue label0;
					}
					int i1 = world.rand.nextInt(21) + 10;
					if(i1 > itemstack.stackSize)
					{
						i1 = itemstack.stackSize;
					}
					itemstack.stackSize -= i1;
					EntityItem entityitem = new EntityItem(world, (float)x + f, (float)y + f1, (float)z + f2, new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
					float f3 = 0.05F;
					entityitem.motionX = (float)world.rand.nextGaussian() * f3;
					entityitem.motionY = (float)world.rand.nextGaussian() * f3 + 0.2F;
					entityitem.motionZ = (float)world.rand.nextGaussian() * f3;
					world.spawnEntityInWorld(entityitem);
				} while(true);
			}
		}
		
		if(te != null && te instanceof TileEntityFactoryPowered)
		{
			((TileEntityFactoryPowered)te).onBlockBroken();
		}

		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public boolean canProvidePower()
	{
		return true;
	}
}
