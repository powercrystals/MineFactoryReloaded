package powercrystals.minefactoryreloaded.api;

import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IFactoryFruit
{
	public int getSourceBlockId();
	public boolean canBePicked(World world, int x, int y, int z);
	
	public ItemStack getReplacementBlock(World world, int x, int y, int z);
	
	public void prePick(World world, int x, int y, int z);
	
	public List<ItemStack> getDrops(World world, Random rand, int x, int y, int z);
	
	public void postPick(World world, int x, int y, int z);
}
