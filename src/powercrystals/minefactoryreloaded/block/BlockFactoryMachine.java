package powercrystals.minefactoryreloaded.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.ITankContainerBucketable;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactory;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidContainerRegistry;

public class BlockFactoryMachine extends BlockContainer
{
	private int _mfrMachineBlockIndex;
	
	public BlockFactoryMachine(int blockId, int index)
	{
		super(blockId, Material.clay);
		setHardness(0.5F);
		setStepSound(soundMetalFootstep);
		setCreativeTab(MFRCreativeTab.tab);
		setUnlocalizedName("mfr.machine." + index);
		_mfrMachineBlockIndex = index;
	}
	
	public int getBlockIndex()
	{
		return _mfrMachineBlockIndex;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void func_94332_a(IconRegister ir)
	{
		Machine.LoadTextures(_mfrMachineBlockIndex, ir);
	}

	@Override
	public Icon getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side)
	{
		int md = iblockaccess.getBlockMetadata(x, y, z);
		boolean isActive = false;
		TileEntity te = iblockaccess.getBlockTileEntity(x, y, z);
		if(te instanceof TileEntityFactory)
		{
			side = ((TileEntityFactory)te).getRotatedSide(side);
			isActive = ((TileEntityFactory)te).getIsActive();
		}
		return Machine.getMachineFromIndex(_mfrMachineBlockIndex, md).getIcon(side, isActive);
	}

	@Override
	public Icon getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		if(side > 1)
		{
			side += 2;
			if(side > 5)
			{
				side -= 4;
			}
		}
		return Machine.getMachineFromIndex(_mfrMachineBlockIndex, meta).getIcon(side, false);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity, ItemStack stack)
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
	public TileEntity createTileEntity(World world, int md)
	{
		return Machine.getMachineFromIndex(_mfrMachineBlockIndex, md).getNewTileEntity();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float xOffset, float yOffset, float zOffset)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te == null)
		{
			return false;
		}
		if(te instanceof ITankContainerBucketable && LiquidContainerRegistry.isEmptyContainer(entityplayer.inventory.getCurrentItem()) && ((ITankContainerBucketable)te).allowBucketDrain())
		{
			if(MFRUtil.manuallyDrainTank((ITankContainerBucketable)te, entityplayer))
			{
				return true;
			}
		}
		else if(te instanceof ITankContainerBucketable && LiquidContainerRegistry.isFilledContainer(entityplayer.inventory.getCurrentItem()) && ((ITankContainerBucketable)te).allowBucketFill())
		{
			if(MFRUtil.manuallyFillTank((ITankContainerBucketable)te, entityplayer))
			{
				return true;
			}
		}
		if(MFRUtil.isHoldingWrench(entityplayer) && te instanceof TileEntityFactory && ((TileEntityFactory)te).canRotate())
		{
			((TileEntityFactory)te).rotate();
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		else if(te instanceof TileEntityFactory && ((TileEntityFactory)te).getGui(entityplayer.inventory) != null)
		{
			entityplayer.openGui(MineFactoryReloadedCore.instance(), 0, world, x, y, z);
			return true;
		}
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int blockId, int meta)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof IInventory)
		{
		
			IInventory inventory = ((IInventory)te);
inv:		for(int i = 0; i < inventory.getSizeInventory(); i++)
			{
				if(te instanceof TileEntityFactoryInventory && !((TileEntityFactoryInventory)te).shouldDropSlotWhenBroken(i))
				{
					continue;
				}
				
				ItemStack itemstack = inventory.getStackInSlot(i);
				if(itemstack == null)
				{
					continue;
				}
				float xOffset = world.rand.nextFloat() * 0.8F + 0.1F;
				float yOffset = world.rand.nextFloat() * 0.8F + 0.1F;
				float zOffset = world.rand.nextFloat() * 0.8F + 0.1F;
				do
				{
					if(itemstack.stackSize <= 0)
					{
						continue inv;
					}
					int amountToDrop = world.rand.nextInt(21) + 10;
					if(amountToDrop > itemstack.stackSize)
					{
						amountToDrop = itemstack.stackSize;
					}
					itemstack.stackSize -= amountToDrop;
					EntityItem entityitem = new EntityItem(world, (float)x + xOffset, (float)y + yOffset, (float)z + zOffset, new ItemStack(itemstack.itemID, amountToDrop, itemstack.getItemDamage()));
					if(itemstack.getTagCompound() != null)
					{
						entityitem.getEntityItem().setTagCompound(itemstack.getTagCompound());
					}
					float motionMultiplier = 0.05F;
					entityitem.motionX = (float)world.rand.nextGaussian() * motionMultiplier;
					entityitem.motionY = (float)world.rand.nextGaussian() * motionMultiplier + 0.2F;
					entityitem.motionZ = (float)world.rand.nextGaussian() * motionMultiplier;
					world.spawnEntityInWorld(entityitem);
				} while(true);
			}
		}
		
		if(te != null && te instanceof TileEntityFactoryPowered)
		{
			((TileEntityFactoryPowered)te).onBlockBroken();
		}

		super.breakBlock(world, x, y, z, blockId, meta);
	}

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
	{
		return true;
	}
	
	@Override
	public boolean isBlockNormalCube(World world, int x, int y, int z)
	{
		return false;
	}
	
	@Override
	public boolean canProvidePower()
	{
		return true;
	}
	
	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side)
	{
		return 0;
	}
}
