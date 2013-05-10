package powercrystals.minefactoryreloaded.farmables.plantables;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;

public class PlantableCocoa implements IFactoryPlantable
{
	@Override
	public int getSeedId()
	{
		return Item.dyePowder.itemID;
	}
	
	@Override
	public int getPlantedBlockId(World world, int x, int y, int z, ItemStack stack)
	{
		return Block.cocoaPlant.blockID;
	}
	
	@Override
	public int getPlantedBlockMetadata(World world, int x, int y, int z, ItemStack stack)
	{
		return 0;
	}
	
	@Override
	public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack)
	{
		return world.isAirBlock(x, y, z) && isNextToJungleLog(world, x, y, z);
	}
	
	private boolean isNextToJungleLog(World world, int x, int y, int z)
	{
		if (isJungleLog(world, x+1, y, z)
				|| isJungleLog(world, x-1, y, z)
				|| isJungleLog(world, x, y, z+1)
				|| isJungleLog(world, x, y, z-1))
		{
			return true;
		}
		
		return false;
	}
	
	private boolean isJungleLog(World world, int x, int y, int z)
	{
		return world.getBlockId(x, y, z) == Block.wood.blockID && BlockLog.limitToValidMetadata(world.getBlockMetadata(x, y, z)) == 3;
	}
	
	@Override
	public void prePlant(World world, int x, int y, int z, ItemStack stack)
	{
	}
	
	@Override
	public void postPlant(World world, int x, int y, int z, ItemStack stack)
	{
		int blockDirection = 4; // NORTH
		if (isJungleLog(world, x-1, y, z))
		{
			blockDirection = 5; // SOUTH
		}
		else if (isJungleLog(world, x, y, z+1))
		{
			blockDirection = 2; // EAST
		}
		else if (isJungleLog(world, x, y, z-1))
		{
			blockDirection = 3; // WEST
		}
		
		world.setBlockMetadataWithNotify(x, y, z, Direction.rotateOpposite[Direction.facingToDirection[blockDirection]], 2);
	}
	
}
