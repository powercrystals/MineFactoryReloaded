package powercrystals.minefactoryreloaded.modhelpers.pam;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;

public class PlantablePamSpecial implements IFactoryPlantable
{
	private int _blockId;
	private int _itemId;
	private int _plantableBlockId;
	
	public PlantablePamSpecial(int blockId, int itemId)
	{
		this(blockId, itemId, Block.tilledField.blockID);
	}
	
	public PlantablePamSpecial(int blockId, int itemId, int plantableBlockId)
	{
		_blockId = blockId;
		_itemId = itemId;
		_plantableBlockId = plantableBlockId;
	}
	
	@Override
	public int getSeedId()
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
		return world.getBlockId(x, y - 1, z) == _plantableBlockId && world.getBlockId(x, y, z) == 0;
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
