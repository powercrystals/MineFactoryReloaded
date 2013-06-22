package powercrystals.minefactoryreloaded.modhelpers.forestry.leaves;

import powercrystals.minefactoryreloaded.modhelpers.forestry.utils.ForestryUtils;
import forestry.api.genetics.IFruitBearer;
import java.util.List;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IFactoryFruit;

public class FruitForestry implements IFactoryFruit
{
	
	private int id;
	
	public FruitForestry(int id)
	{
		this.id = id;
	}
	
	@Override
	public int getSourceBlockId()
	{
		return this.id;
	}
	
	@Override
	public boolean canBePicked(World world, int x, int y, int z)
	{
		IFruitBearer f = ForestryUtils.getFruitBearer(world.getBlockTileEntity(x, y, z));
		if(f.hasFruit() && f.getRipeness() >= 1.0f)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public ItemStack getReplacementBlock(World world, int x, int y, int z)
	{
		return new ItemStack(this.getSourceBlockId(), 1, 0);
	}
	
	@Override
	public void prePick(World world, int x, int y, int z)
	{
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ItemStack> getDrops(World world, Random rand, int x, int y, int z)
	{
		IFruitBearer f = ForestryUtils.getFruitBearer(world.getBlockTileEntity(x, y, z));
		return((List)f.pickFruit(ForestryUtils.getItem("grafter").copy()));
	}
	
	@Override
	public void postPick(World world, int x, int y, int z)
	{
	}
}
