package powercrystals.minefactoryreloaded.modhelpers.forestry.pods;

import java.util.Random;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableForestryPods implements IFactoryFertilizable
{
	private int _blockId;
	
	public FertilizableForestryPods(int blockId)
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
		TileEntity t = world.getBlockTileEntity(x, y, z);
		if(FruitForestryPod.TileFruitPod.isInstance(t))
		{
			return FruitForestryPod.canMature(t);
		}
		return false;
	}
	
	@Override
	public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
	{
		if(world.getBlockId(x, y, z) == this.getFertilizableBlockId())
		{
			BonemealEvent event = new BonemealEvent(null, world, 1, x, y, z);
			MinecraftForge.EVENT_BUS.post(event);
			if(event.getResult().equals(BonemealEvent.Result.ALLOW))
			{
				return true;
			}
		}
		return false;
	}
}
