package powercrystals.minefactoryreloaded.modhelpers.forestry.fertilizer;

import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizer;

public class FertilizerForestry implements IFactoryFertilizer
{
	private ItemStack _fert;
	
	public FertilizerForestry(ItemStack fert)
	{
		_fert = fert;
	}
	
	@Override
	public int getFertilizerId()
	{
		return _fert.itemID;
	}
	
	@Override
	public int getFertilizerMeta()
	{
		return _fert.getItemDamage();
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
