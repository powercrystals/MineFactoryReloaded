package powercrystals.minefactoryreloaded.core;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomItem;

public class WeightedRandomItemStack extends WeightedRandomItem
{
	private ItemStack _stack;
	
	public WeightedRandomItemStack(int weight, ItemStack stack)
	{
		super(weight);
		_stack = stack;
	}
	
	public ItemStack getStack()
	{
		if(_stack == null) return null;
		return _stack.copy();
	}
}
