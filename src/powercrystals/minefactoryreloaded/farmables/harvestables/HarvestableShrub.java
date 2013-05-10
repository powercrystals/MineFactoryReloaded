package powercrystals.minefactoryreloaded.farmables.harvestables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

public class HarvestableShrub implements IFactoryHarvestable
{
	private int _sourceId;
	
	public HarvestableShrub(int sourceId)
	{
		_sourceId = sourceId;
	}
	
	@Override
	public int getPlantId()
	{
		return _sourceId;
	}
	
	@Override
	public HarvestType getHarvestType()
	{
		return HarvestType.Normal;
	}
	
	@Override
	public boolean breakBlock()
	{
		return true;
	}
	
	@Override
	public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		return true;
	}
	
	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		List<ItemStack> drops = new ArrayList<ItemStack>();
		
		int meta = world.getBlockMetadata(x, y, z);
		if(_sourceId == Block.tallGrass.blockID && meta == 1 && !harvesterSettings.get("silkTouch"))
		{
			drops.addAll(Block.blocksList[_sourceId].getBlockDropped(world, x, y, z, meta, 0));
		}
		else
		{
			drops.add(new ItemStack(_sourceId, 1, meta));
		}
		
		return drops;
	}
	
	@Override
	public void preHarvest(World world, int x, int y, int z)
	{
	}
	
	@Override
	public void postHarvest(World world, int x, int y, int z)
	{
	}
}
