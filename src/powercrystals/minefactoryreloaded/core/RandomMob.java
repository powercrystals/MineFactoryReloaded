package powercrystals.minefactoryreloaded.core;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandomItem;

public class RandomMob extends WeightedRandomItem
{
	private NBTTagCompound _mob;
	
	public RandomMob(int weight, NBTTagCompound savedMob)
	{
		super(weight);
		_mob = savedMob;
	}
	
	public NBTTagCompound getMob()
	{
		if(_mob == null) return null;
		return (NBTTagCompound)_mob.copy();
	}
}
