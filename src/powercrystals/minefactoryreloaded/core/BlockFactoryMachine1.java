package powercrystals.minefactoryreloaded.core;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore.Machine;
import powercrystals.minefactoryreloaded.processing.TileEntityDeepStorageUnit;
import powercrystals.minefactoryreloaded.processing.TileEntityLavaFabricator;
import powercrystals.minefactoryreloaded.processing.TileEntityLiquiCrafter;
import powercrystals.minefactoryreloaded.processing.TileEntityOilFabricator;
import powercrystals.minefactoryreloaded.transport.TileEntityEjector;
import powercrystals.minefactoryreloaded.transport.TileEntityItemRouter;
import powercrystals.minefactoryreloaded.transport.TileEntityLiquidRouter;

public class BlockFactoryMachine1 extends BlockFactoryMachine
{
	public BlockFactoryMachine1(int blockId)
	{
		super(blockId);
		setBlockName("blockFactoryMachine1");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int md)
	{
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.Ejector)) return new TileEntityEjector();
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.ItemRouter)) return new TileEntityItemRouter();
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.LiquidRouter)) return new TileEntityLiquidRouter();
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.DeepStorageUnit)) return new TileEntityDeepStorageUnit();
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.LiquiCrafter)) return new TileEntityLiquiCrafter();
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.LavaFabricator)) return new TileEntityLavaFabricator();
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.OilFabricator)) return new TileEntityOilFabricator();
		return null;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileEntityItemRouter)
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
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.ItemRouter))
		{
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if(te != null && te instanceof TileEntityItemRouter && entity instanceof EntityItem)
			{
				if(((TileEntityItemRouter)te).routeItem(((EntityItem)entity).getEntityItem()))
				{
					entity.setDead();
				}
			}
		}
		super.onEntityCollidedWithBlock(world, x, y, z, entity);
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
		return meta != MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.DeepStorageUnit) ? 1 : 0;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int blockId, int meta)
	{
		if(meta != MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.DeepStorageUnit))
		{
			super.breakBlock(world, x, y, z, blockId, meta);
		}
		else
		{	
			ItemStack s = new ItemStack(blockId, 1, meta);
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if(te != null && te instanceof TileEntityDeepStorageUnit && ((TileEntityDeepStorageUnit)te).getQuantity() > 0)
			{
				TileEntityDeepStorageUnit dsu = (TileEntityDeepStorageUnit)te;
				NBTTagCompound c = new NBTTagCompound();
				int storedId = dsu.getId();
				int storedMeta = dsu.getMeta();
				int storedQuantity = dsu.getQuantity();
				
				for(int i = 0; i < 3; i++)
				{
					ItemStack inv = dsu.getStackInSlot(i);
					if(inv != null)
					{
						if(inv.itemID == storedId && inv.getItemDamage() == storedMeta)
						{
							storedQuantity += inv.stackSize;
						}
						else
						{
							dropBlockAsItem_do(world, x, y, z, inv.copy());
						}
					}
				}

				c.setInteger("storedId", storedId);
				c.setInteger("storedMeta", storedMeta);
				c.setInteger("storedQuantity", storedQuantity);
				s.setTagCompound(c);
			}

			dropBlockAsItem_do(world, x, y, z, s);
			world.removeBlockTileEntity(x, y, z);
		}
	}

	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.machine1Texture;
	}
}
