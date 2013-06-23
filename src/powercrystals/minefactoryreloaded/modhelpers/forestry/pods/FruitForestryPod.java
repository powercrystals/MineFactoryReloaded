package powercrystals.minefactoryreloaded.modhelpers.forestry.pods;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IFactoryFruit;

public class FruitForestryPod implements IFactoryFruit
{
	public static Class<?> TileFruitPod;
	
	private int _id;
	private static Method _canMature;// Returns a boolean.
	private static Method _getDrops;
	
	static
	{
		try
		{
			TileFruitPod = Class.forName("forestry.arboriculture.gadgets.TileFruitPod");
			_canMature = TileFruitPod.getDeclaredMethod("canMature", new Class[0]);
			_getDrops = TileFruitPod.getDeclaredMethod("getDrop", new Class[0]);
		}
		catch(Throwable ex)
		{
		}
	}
	
	public static boolean canMature(TileEntity t)
	{
		try
		{
			return (Boolean)_canMature.invoke(t, new Object[0]);
		}
		catch(Throwable ex)
		{
		}
		return false;
	}
	
	private static ItemStack[] getDrops(TileEntity t)
	{
		try
		{
			return (ItemStack[])_getDrops.invoke(t, new Object[0]);
		}
		catch(Throwable ex)
		{
		}
		return new ItemStack[0];
	}
	
	public FruitForestryPod(int id)
	{
		this._id = id;
	}
	
	@Override
	public int getSourceBlockId()
	{
		return this._id;
	}
	
	@Override
	public boolean canBePicked(World world, int x, int y, int z)
	{
		TileEntity t = world.getBlockTileEntity(x, y, z);
		if(TileFruitPod.isInstance(t))
		{
			return !canMature(t);
		}
		return false;
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
		return Arrays.asList(getDrops(world.getBlockTileEntity(x, y, z)));
	}
	
	@Override
	public void postPick(World world, int x, int y, int z)
	{
		world.setBlockTileEntity(x, y, z, null);
	}
}
