package powercrystals.minefactoryreloaded.modhelpers.rp2;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableRedPowerFlax implements IFactoryFertilizable
{
	private int _blockId;
	
	public FertilizableRedPowerFlax(int blockId)
	{
		_blockId = blockId;
	}
	
	@Override
	public int getFertilizableBlockId()
	{
		return _blockId;
	}
	
	@Override
	public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType)
	{
		int id = world.getBlockId(x, y + 1, z);
		return Block.blocksList[id] == null || Block.blocksList[id].isAirBlock(world, x, y + 1, z);
	}
	
	@Override
	public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
	{
		if(world.getBlockMetadata(x, y, z) < 4)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		world.setBlock(x, y + 1, z, _blockId, 5, 2);
		return true;
	}
}