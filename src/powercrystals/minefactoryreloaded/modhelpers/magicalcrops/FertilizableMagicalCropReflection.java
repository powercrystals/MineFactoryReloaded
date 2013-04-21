package powercrystals.minefactoryreloaded.modhelpers.magicalcrops;

import java.lang.reflect.Method;

import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.modhelpers.FertilizableCropReflection;

public class FertilizableMagicalCropReflection extends FertilizableCropReflection
{
	
	public FertilizableMagicalCropReflection(int blockId, Method fertilize, int targetMeta)
	{
		super(blockId, fertilize, targetMeta);
	}
	
	@Override
	public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType)
	{
		return world.getBlockMetadata(x, y, z) < _targetMeta && fertilizerType == FertilizerType.GrowMagicalCrop;
	}
}