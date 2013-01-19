package powercrystals.minefactoryreloaded.modhelpers.pam;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;

public class PlantablePamRice implements IFactoryPlantable
{
	private int _blockId;
	private int _itemId;
	
	public PlantablePamRice(int blockId, int itemId)
	{
		_blockId = blockId;
		_itemId = itemId;
	}
	
	@Override
	public int getSourceId()
	{
		return _itemId;
	}

	@Override
	public int getPlantedBlockId(World world, int x, int y, int z, ItemStack stack)
	{
		return _blockId;
	}

	@Override
	public int getPlantedBlockMetadata(World world, int x, int y, int z, ItemStack stack)
	{
		return 0;
	}

	@Override
	public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack)
	{
		return world.getBlockId(x, y - 1, z) == Block.waterStill.blockID && world.getBlockId(x, y, z) == 0;
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