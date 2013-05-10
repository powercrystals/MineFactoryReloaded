package powercrystals.minefactoryreloaded.modhelpers.chococraft;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

public class HarvestableChococraft implements IFactoryHarvestable
{
	
	private int blockId;
	
	public HarvestableChococraft(int blockId)
	{
		this.blockId = blockId;
	}
	
	@Override
	public int getPlantId()
	{
		return this.blockId;
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
		return world.getBlockMetadata(x, y, z) >= 4;
	}
	
	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		return Block.blocksList[blockId].getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
	}
	
	@Override
	public void preHarvest(World world, int x, int y, int z)
	{
		if (world.getBlockMetadata(x, y, z) > 4)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}
	
	@Override
	public void postHarvest(World world, int x, int y, int z)
	{
	}
}
