package powercrystals.minefactoryreloaded.modhelpers.forestry.trees;

import forestry.api.arboriculture.ITree;
import java.util.Random;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableForestryTree implements IFactoryFertilizable
{
	@Override
	public int getFertilizableBlockId()
	{
		return PlantableForestryTree.sapling.blockID;
	}
	
	@Override
	public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType)
	{
		return world.getBlockId(x, y, z) == this.getFertilizableBlockId();
	}
	
	@Override
	public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
	{
		if(world.getBlockId(x, y, z) == this.getFertilizableBlockId())
		{
			TileEntity t = world.getBlockTileEntity(x, y, z);
			if(PlantableForestryTree.TileTreeContainer.isInstance(t))
			{
				ITree tree = PlantableForestryTree.getTree(t);
				return tree.getTreeGenerator(world, x, y, z, true).generate(world, rand, x, y, z);
			}
		}
		return false;
	}
}
