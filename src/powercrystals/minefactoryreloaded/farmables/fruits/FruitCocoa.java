package powercrystals.minefactoryreloaded.farmables.fruits;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IFactoryFruit;

public class FruitCocoa implements IFactoryFruit
{
	@Override
	public int getSourceBlockId()
	{
		return Block.cocoaPlant.blockID;
	}
	
	@Override
	public boolean canBePicked(World world, int x, int y, int z)
	{
		int blockMetadata = world.getBlockMetadata(x, y, z);
		return ((blockMetadata & 12) >> 2) >= 2;
	}
	
	@Override
	public ItemStack getReplacementBlock(World world, int x, int y, int z)
	{
		return null;
	}
	
	@Override
	public void prePick(World world, int x, int y, int z)
	{
	}
	
	@Override
	public List<ItemStack> getDrops(World world, Random rand, int x, int y, int z)
	{
		ArrayList<ItemStack> result = new ArrayList<ItemStack>();
		result.add(new ItemStack(Item.dyePowder, 3, 3));
		return result;
	}
	
	@Override
	public void postPick(World world, int x, int y, int z)
	{
	}
}
