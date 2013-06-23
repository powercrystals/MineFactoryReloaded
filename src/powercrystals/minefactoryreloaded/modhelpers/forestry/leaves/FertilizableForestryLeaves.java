package powercrystals.minefactoryreloaded.modhelpers.forestry.leaves;

import powercrystals.minefactoryreloaded.modhelpers.forestry.utils.ForestryUtils;
import forestry.api.genetics.IFruitBearer;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableForestryLeaves implements IFactoryFertilizable
{
	private int _id;
	
	public FertilizableForestryLeaves(int id)
	{
		_id = id;
	}
	
	@Override
	public int getFertilizableBlockId()
	{
		return _id;
	}
	
	@Override
	public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType)
	{
		if(world.getBlockId(x, y, z) == this.getFertilizableBlockId())
		{
			return true;
		}
		return false;
	}
	
	@Override
	public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
	{
		if(world.getBlockId(x, y, z) == this.getFertilizableBlockId())
		{
			IFruitBearer f = ForestryUtils.getFruitBearer(world.getBlockTileEntity(x, y, z));
			if(f.hasFruit() && f.getRipeness() < 1.0f)
			{
				BonemealEvent event = new BonemealEvent(null, world, 1, x, y, z);
				MinecraftForge.EVENT_BUS.post(event);
				if(event.getResult().equals(BonemealEvent.Result.ALLOW))
				{
					return true;
				}
			}
		}
		return false;
	}
}
