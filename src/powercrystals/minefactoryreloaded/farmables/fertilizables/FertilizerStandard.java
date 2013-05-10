package powercrystals.minefactoryreloaded.farmables.fertilizables;

import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizer;

public class FertilizerStandard implements IFactoryFertilizer
{
	private int _id;
	private int _meta;
	private FertilizerType _type;
	
	public FertilizerStandard(int id, int meta)
	{
		this(id, meta, FertilizerType.GrowPlant);
	}
	
	public FertilizerStandard(int id, int meta, FertilizerType type)
	{
		_id = id;
		_meta = meta;
		_type = type;
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
		return  _type;
	}
	
	@Override
	public void consume(ItemStack fertilizer)
	{
		fertilizer.stackSize--;
	}
}
