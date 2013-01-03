package powercrystals.minefactoryreloaded.farmables;

import net.minecraft.item.ItemStack;

import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizer;

public class FertilizerStandard implements IFactoryFertilizer
{
	private int _id;
	private int _meta;
	
	public FertilizerStandard(int id, int meta)
	{
		_id = id;
		_meta = meta;
	}
	
	@Override
	public int getFertilizerId()
	{
		return _id;
	}

	@Override
	public int getFertilizerMeta()
	{
		return _meta;
	}

	@Override
	public FertilizerType getFertilizerType()
	{
		return FertilizerType.GrowPlant;
	}

	@Override
	public void consume(ItemStack fertilizer)
	{
		fertilizer.stackSize--;
	}
}
