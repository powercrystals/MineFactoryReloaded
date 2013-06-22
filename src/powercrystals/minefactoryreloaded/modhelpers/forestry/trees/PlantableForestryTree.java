package powercrystals.minefactoryreloaded.modhelpers.forestry.trees;

import powercrystals.minefactoryreloaded.modhelpers.forestry.utils.ForestryUtils;
import forestry.api.arboriculture.ITree;
import java.lang.reflect.Method;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;

public class PlantableForestryTree implements IFactoryPlantable
{
	private ItemStack _sapling;
	private Method _setTree;
	
	public static Block sapling;
	public static Class<?> TileTreeContainer;
	public static Method getTree;
	
	static
	{
		try
		{
			sapling = (Block)Class.forName("forestry.core.config.ForestryBlock").getField("saplingGE").get(null);
			TileTreeContainer = Class.forName("forestry.arboriculture.gadgets.TileTreeContainer");
			getTree = TileTreeContainer.getDeclaredMethod("getTree", new Class[0]);
		}
		catch(Throwable s)
		{
		}
	}
	
	public static ITree getTree(TileEntity t)
	{
		try
		{
			return (ITree)getTree.invoke(t, new Object[0]);
		}
		catch(Throwable ex)
		{
		}
		return null;
	}
	
	public PlantableForestryTree()
	{
		try
		{
			_sapling = ForestryUtils.getItem("sapling");
			// Need to reference this class later.
			_setTree = TileTreeContainer.getDeclaredMethod("setTree", new Class[] { ITree.class });
		}
		catch(Throwable stuff)
		{
		}
	}
	
	@Override
	public int getSeedId()
	{
		return _sapling.itemID;
	}
	
	@Override
	public int getPlantedBlockId(World world, int x, int y, int z, ItemStack stack)
	{
		if(stack.isItemEqual(_sapling))
		{
			return sapling.blockID;
		}
		return -1;
	}
	
	@Override
	public int getPlantedBlockMetadata(World world, int x, int y, int z, ItemStack stack)
	{
		if(stack.isItemEqual(_sapling))
		{
			return _sapling.getItemDamage();
		}
		return 0;
	}
	
	@Override
	public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack)
	{
		if(stack.isItemEqual(_sapling))
		{
			int groundId = world.getBlockId(x, y - 1, z);
			if(!world.isAirBlock(x, y, z))
			{
				return false;
			}
			return groundId == Block.dirt.blockID || groundId == Block.grass.blockID;
		}
		return false;
	}
	
	@Override
	public void prePlant(World world, int x, int y, int z, ItemStack stack)
	{
	}
	
	@Override
	public void postPlant(World world, int x, int y, int z, ItemStack stack)
	{
		if(stack.isItemEqual(_sapling))
		{
			TileEntity t = world.getBlockTileEntity(x, y, z);
			if(TileTreeContainer.isInstance(t))
			{
				ITree tree = ForestryUtils.root.getMember(stack);
				try
				{
					_setTree.invoke(t, new Object[] { tree });
				}
				catch(Throwable TREE)
				{
				}
			}
		}
	}
}
