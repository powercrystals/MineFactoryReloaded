package powercrystals.minefactoryreloaded.farmables;

import powercrystals.minefactoryreloaded.api.IFactoryPlantable;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PlantableNetherWart implements IFactoryPlantable
{
	@Override
	public int getSourceId()
	{
		return Item.netherStalkSeeds.shiftedIndex;
	}

	@Override
	public int getPlantedBlockId(World world, int x, int y, int z, ItemStack stack)
	{
		return Block.netherStalk.blockID;
	}

	@Override
	public int getPlantedBlockMetadata(World world, int x, int y, int z, ItemStack stack)
	{
		return 0;
	}

	@Override
	public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack)
	{
		return world.getBlockId(x, y - 1, z) == Block.slowSand.blockID && world.isAirBlock(x, y, z);
	}

	@Override
	public void prePlant(World world, int x, int y, int z, ItemStack stack)
	{
	}

	@Override
	public void postPlant(World world, int x, int y, int z, ItemStack stack)
	{
	}

}
