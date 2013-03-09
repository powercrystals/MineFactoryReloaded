package powercrystals.minefactoryreloaded.core;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore.Machine;
import powercrystals.minefactoryreloaded.animals.TileEntityBreeder;
import powercrystals.minefactoryreloaded.animals.TileEntityChronotyper;
import powercrystals.minefactoryreloaded.animals.TileEntityGrinder;
import powercrystals.minefactoryreloaded.animals.TileEntityRancher;
import powercrystals.minefactoryreloaded.animals.TileEntitySewer;
import powercrystals.minefactoryreloaded.animals.TileEntityVet;
import powercrystals.minefactoryreloaded.plants.TileEntityFertilizer;
import powercrystals.minefactoryreloaded.plants.TileEntityHarvester;
import powercrystals.minefactoryreloaded.plants.TileEntityPlanter;
import powercrystals.minefactoryreloaded.processing.TileEntityBlockBreaker;
import powercrystals.minefactoryreloaded.processing.TileEntityComposter;
import powercrystals.minefactoryreloaded.processing.TileEntityAutoEnchanter;
import powercrystals.minefactoryreloaded.processing.TileEntityFisher;
import powercrystals.minefactoryreloaded.processing.TileEntitySludgeBoiler;
import powercrystals.minefactoryreloaded.processing.TileEntityWeather;
import powercrystals.minefactoryreloaded.transport.TileEntityCollector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockFactoryMachine0 extends BlockFactoryMachine
{
	public BlockFactoryMachine0(int blockId)
	{
		super(blockId);
		setBlockName("blockFactoryMachine");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int md)
	{
		if(md == MineFactoryReloadedCore.machine0MetadataMappings.get(Machine.Planter)) return new TileEntityPlanter();
		if(md == MineFactoryReloadedCore.machine0MetadataMappings.get(Machine.Fisher)) return new TileEntityFisher();
		if(md == MineFactoryReloadedCore.machine0MetadataMappings.get(Machine.Harvester)) return new TileEntityHarvester();
		if(md == MineFactoryReloadedCore.machine0MetadataMappings.get(Machine.Rancher)) return new TileEntityRancher();
		if(md == MineFactoryReloadedCore.machine0MetadataMappings.get(Machine.Fertilizer)) return new TileEntityFertilizer();
		if(md == MineFactoryReloadedCore.machine0MetadataMappings.get(Machine.Vet)) return new TileEntityVet();
		if(md == MineFactoryReloadedCore.machine0MetadataMappings.get(Machine.Collector)) return new TileEntityCollector();
		if(md == MineFactoryReloadedCore.machine0MetadataMappings.get(Machine.Breaker)) return new TileEntityBlockBreaker();
		if(md == MineFactoryReloadedCore.machine0MetadataMappings.get(Machine.Weather)) return new TileEntityWeather();
		if(md == MineFactoryReloadedCore.machine0MetadataMappings.get(Machine.Boiler)) return new TileEntitySludgeBoiler();
		if(md == MineFactoryReloadedCore.machine0MetadataMappings.get(Machine.Sewer)) return new TileEntitySewer();
		if(md == MineFactoryReloadedCore.machine0MetadataMappings.get(Machine.Composter)) return new TileEntityComposter();
		if(md == MineFactoryReloadedCore.machine0MetadataMappings.get(Machine.Breeder)) return new TileEntityBreeder();
		if(md == MineFactoryReloadedCore.machine0MetadataMappings.get(Machine.Grinder)) return new TileEntityGrinder();
		if(md == MineFactoryReloadedCore.machine0MetadataMappings.get(Machine.Enchanter)) return new TileEntityAutoEnchanter();
		if(md == MineFactoryReloadedCore.machine0MetadataMappings.get(Machine.Chronotyper)) return new TileEntityChronotyper();
		return null;
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
		if(md == MineFactoryReloadedCore.machine0MetadataMappings.get(Machine.Collector))
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
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.machine0Texture;
	}
}
