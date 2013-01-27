package powercrystals.minefactoryreloaded.core;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore.Machine;
import powercrystals.minefactoryreloaded.processing.TileEntityLavaFabricator;
import powercrystals.minefactoryreloaded.processing.TileEntityOilFabricator;
import powercrystals.minefactoryreloaded.transport.TileEntityDeepStorageUnit;
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
				if(((TileEntityItemRouter)te).routeItem(((EntityItem)entity).func_92014_d()))
				{
					entity.setDead();
				}
			}
		}
		super.onEntityCollidedWithBlock(world, x, y, z, entity);
	}

	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.machine1Texture;
	}
}
