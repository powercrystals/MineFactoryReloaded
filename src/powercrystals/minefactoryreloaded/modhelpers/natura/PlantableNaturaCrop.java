package powercrystals.minefactoryreloaded.modhelpers.natura;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableCropPlant;

public class PlantableNaturaCrop extends PlantableCropPlant
{

	public PlantableNaturaCrop(int sourceId, int plantedBlockId)
	{
		super(sourceId, plantedBlockId);
	}

	@Override
	public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack)
	{
		if(stack.getItemDamage() == 0 || stack.getItemDamage() == 1)
		{
			return super.canBePlantedHere(world, x, y, z, stack);
		}
		return false;
	}
	
	@Override
	public int getPlantedBlockMetadata(World world, int x, int y, int z, ItemStack stack)
	{
		return stack.getItemDamage() == 0 ? 0 : 4;
	}
}
