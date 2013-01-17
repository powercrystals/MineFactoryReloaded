package powercrystals.minefactoryreloaded.core;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore.Machine;
import powercrystals.minefactoryreloaded.animals.TileEntityBreeder;
import powercrystals.minefactoryreloaded.animals.TileEntityChronotyper;
import powercrystals.minefactoryreloaded.animals.TileEntityGrinder;
import powercrystals.minefactoryreloaded.animals.TileEntityRancher;
import powercrystals.minefactoryreloaded.animals.TileEntityVet;
import powercrystals.minefactoryreloaded.plants.TileEntityFertilizer;
import powercrystals.minefactoryreloaded.plants.TileEntityHarvester;
import powercrystals.minefactoryreloaded.plants.TileEntityPlanter;
import powercrystals.minefactoryreloaded.processing.TileEntityBlockBreaker;
import powercrystals.minefactoryreloaded.processing.TileEntityCollector;
import powercrystals.minefactoryreloaded.processing.TileEntityComposter;
import powercrystals.minefactoryreloaded.processing.TileEntityAutoEnchanter;
import powercrystals.minefactoryreloaded.processing.TileEntityFisher;
import powercrystals.minefactoryreloaded.processing.TileEntitySewer;
import powercrystals.minefactoryreloaded.processing.TileEntitySludgeBoiler;
import powercrystals.minefactoryreloaded.processing.TileEntityWeather;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.transport.IPipeConnection;

public class BlockFactoryMachine extends BlockContainer implements IPipeConnection
{
	public BlockFactoryMachine(int i, int j)
	{
		super(i, j, Material.clay);
		setBlockName("blockFactoryMachine");
		setHardness(0.5F);
		setStepSound(soundMetalFootstep);
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
	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if(i > 1)
		{
			i += 2;
			if(i > 5)
			{
				i -= 4;
			}
		}
		return j * 16 + i;
	}

	@Override
	public int damageDropped(int i)
	{
		return i;
	}

	@Override
	public TileEntity createNewTileEntity(World var1)
	{
		return null;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int md)
	{
		if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Planter)) return new TileEntityPlanter();
		if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Fisher)) return new TileEntityFisher();
		if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Harvester)) return new TileEntityHarvester();
		if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Rancher)) return new TileEntityRancher();
		if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Fertilizer)) return new TileEntityFertilizer();
		if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Vet)) return new TileEntityVet();
		if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Collector)) return new TileEntityCollector();
		if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Breaker)) return new TileEntityBlockBreaker();
		if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Weather)) return new TileEntityWeather();
		if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Boiler)) return new TileEntitySludgeBoiler();
		if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Sewer)) return new TileEntitySewer();
		if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Composter)) return new TileEntityComposter();
		if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Breeder)) return new TileEntityBreeder();
		if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Grinder)) return new TileEntityGrinder();
		if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Enchanter)) return new TileEntityAutoEnchanter();
		if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Chronotyper)) return new TileEntityChronotyper();
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if(!entityplayer.isSneaking())
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
			}
			else if(te instanceof TileEntityFactoryPowered)
			{
				entityplayer.openGui(MineFactoryReloadedCore.instance(), 0, world, x, y, z);
			}
			return true;
		}
		return false;
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, int par5, int par6)
	{
		TileEntity te = world.getBlockTileEntity(i, j, k);
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
					EntityItem entityitem = new EntityItem(world, (float)i + f, (float)j + f1, (float)k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
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

		super.breakBlock(world, i, j, k, par5, par6);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileEntityCollector)
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
		int md = world.getBlockMetadata(x, y, z);
		if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Collector))
		{
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if(te != null && te instanceof TileEntityCollector && entity instanceof EntityItem)
			{
				((TileEntityCollector)te).addToChests((EntityItem)entity);
			}
		}
		super.onEntityCollidedWithBlock(world, x, y, z, entity);
	}

	@Override
	public boolean canProvidePower()
	{
		return true;
	}

	@Override
	public boolean isPipeConnected(ForgeDirection with)
	{
		return true;
	}

	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}
	
}
