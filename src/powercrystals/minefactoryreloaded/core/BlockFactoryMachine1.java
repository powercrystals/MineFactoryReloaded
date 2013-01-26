package powercrystals.minefactoryreloaded.core;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore.Machine;
import powercrystals.minefactoryreloaded.processing.TileEntityLavaFabricator;
import powercrystals.minefactoryreloaded.processing.TileEntityOilFabricator;

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
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.LavaFabricator)) return new TileEntityLavaFabricator();
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.OilFabricator)) return new TileEntityOilFabricator();
		return null;
	}

	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.machine1Texture;
	}
}
