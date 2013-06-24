package powercrystals.minefactoryreloaded.block;

import java.util.ArrayList;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import powercrystals.core.position.IRotateableTile;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.rednet.IConnectableRedNet;
import powercrystals.minefactoryreloaded.api.rednet.RedNetConnectionType;
import powercrystals.minefactoryreloaded.core.BlockNBTManager;
import powercrystals.minefactoryreloaded.core.ITankContainerBucketable;
import powercrystals.minefactoryreloaded.core.MFRLiquidMover;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactory;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoJukebox;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityCollector;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityDeepStorageUnit;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityItemRouter;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityLaserDrill;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFactoryMachine extends BlockContainer implements IConnectableRedNet
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
	public void registerIcons(IconRegister ir)
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
	public Icon getIcon(int side, int meta)
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
	public int getLightOpacity(World world, int x, int y, int z)
	{
		if(world.getBlockTileEntity(x, y, z) instanceof TileEntityLaserDrill)
		{
			return 0;
		}
		return super.getLightOpacity(world, x, y, z);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && (te instanceof TileEntityItemRouter || te instanceof TileEntityCollector))
		{
			float shrinkAmount = 0.125F;
			return AxisAlignedBB.getBoundingBox(x + shrinkAmount, y + shrinkAmount, z + shrinkAmount,
					x + 1 - shrinkAmount, y + 1 - shrinkAmount, z + 1 - shrinkAmount);
		}
		else
		{
			return super.getCollisionBoundingBoxFromPool(world, x, y, z);
		}
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if(world.isRemote)
		{
			return;
		}
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileEntityItemRouter && entity instanceof EntityItem)
		{
			ItemStack s = ((TileEntityItemRouter)te).routeItem(((EntityItem)entity).getEntityItem()); 
			if(s == null)
			{
				entity.setDead();
			}
			else
			{
				((EntityItem)entity).setEntityItemStack(s);
			}
		}
		else if(te != null && te instanceof TileEntityCollector && entity instanceof EntityItem)
		{
			((TileEntityCollector)te).addToChests((EntityItem)entity);
		}
		super.onEntityCollidedWithBlock(world, x, y, z, entity);
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
			
			if (te instanceof TileEntityFactoryInventory)
			{
		        if (stack.hasDisplayName())
		        {
		            ((TileEntityFactoryInventory)te).setInvName(stack.getDisplayName());
		        }
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
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis)
	{
        if (world.isRemote)
        {
            return false;
        }
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof IRotateableTile)
		{
			IRotateableTile tile = ((IRotateableTile)te);
			if (tile.canRotate())
			{
				tile.rotate();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float xOffset, float yOffset, float zOffset)
	{
		PlayerInteractEvent e = new PlayerInteractEvent(entityplayer, Action.RIGHT_CLICK_BLOCK, x, y, z, side);
		if(MinecraftForge.EVENT_BUS.post(e) || e.getResult() == Result.DENY || e.useBlock == Result.DENY)
		{
			return false;
		}
		
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te == null)
		{
			return false;
		}
		if(te instanceof ITankContainerBucketable && LiquidContainerRegistry.isEmptyContainer(entityplayer.inventory.getCurrentItem()) && ((ITankContainerBucketable)te).allowBucketDrain())
		{
			if(MFRLiquidMover.manuallyDrainTank((ITankContainerBucketable)te, entityplayer))
			{
				return true;
			}
		}
		else if(te instanceof ITankContainerBucketable && LiquidContainerRegistry.isFilledContainer(entityplayer.inventory.getCurrentItem()) && ((ITankContainerBucketable)te).allowBucketFill())
		{
			if(MFRLiquidMover.manuallyFillTank((ITankContainerBucketable)te, entityplayer))
			{
				return true;
			}
		}
		if(MFRUtil.isHoldingHammer(entityplayer) && te instanceof TileEntityFactory && ((TileEntityFactory)te).canRotate())
		{
			((TileEntityFactory)te).rotate();
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		else if(te instanceof TileEntityFactory && ((TileEntityFactory)te).getContainer(entityplayer.inventory) != null)
		{
			if(!world.isRemote)
			{
				entityplayer.openGui(MineFactoryReloadedCore.instance(), 0, world, x, y, z);
			}
			return true;
		}
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int blockId, int meta)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te instanceof IInventory && !(te instanceof TileEntityDeepStorageUnit))
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
					EntityItem entityitem = new EntityItem(world, x + xOffset, y + yOffset, z + zOffset, new ItemStack(itemstack.itemID, amountToDrop, itemstack.getItemDamage()));
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
		else if(te instanceof TileEntityDeepStorageUnit && ((TileEntityDeepStorageUnit)te).getQuantityAdjusted() > 0)
		{
			BlockNBTManager.setForBlock(te);
		}
		
		if(te != null && te instanceof TileEntityFactoryPowered)
		{
			((TileEntityFactoryPowered)te).onBlockBroken();
		}
		
		if(te != null && te instanceof TileEntityAutoJukebox)
		{
			((TileEntityAutoJukebox)te).stopRecord();
		}
		
		super.breakBlock(world, x, y, z, blockId, meta);
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		ItemStack machine = new ItemStack(idDropped(blockID, world.rand, fortune), 1, damageDropped(metadata));
		machine.setTagCompound(BlockNBTManager.getForBlock(x, y, z));
		drops.add(machine);
		return drops;
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
	
	@Override
	public RedNetConnectionType getConnectionType(World world, int x, int y, int z, ForgeDirection side)
	{
		return RedNetConnectionType.CableSingle;
	}
	
	@Override
	public int[] getOutputValues(World world, int x, int y, int z, ForgeDirection side)
	{
		return null;
	}
	
	@Override
	public int getOutputValue(World world, int x, int y, int z, ForgeDirection side, int subnet)
	{
		return 0;
	}
	
	@Override
	public void onInputsChanged(World world, int x, int y, int z, ForgeDirection side, int[] inputValues)
	{
	}
	
	@Override
	public void onInputChanged(World world, int x, int y, int z, ForgeDirection side, int inputValue)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te instanceof TileEntityFactory)
		{
			((TileEntityFactory)te).onRedNetChanged(inputValue);
			onNeighborBlockChange(world, x, y, z, MineFactoryReloadedCore.rednetCableBlock.blockID);
		}
	}
}
