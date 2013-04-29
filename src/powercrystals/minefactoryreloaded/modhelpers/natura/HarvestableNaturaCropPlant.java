package powercrystals.minefactoryreloaded.modhelpers.natura;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

public class HarvestableNaturaCropPlant implements IFactoryHarvestable
{	
	private int _sourceId;
	private int _cottonItemId;
	
	public HarvestableNaturaCropPlant(int sourceId, int cottonItemId)
	{
		_sourceId = sourceId;
		_cottonItemId = cottonItemId;
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
		return false;
	}

	@Override
	public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z) == 3 || world.getBlockMetadata(x, y, z) == 8;
	}

	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		if(world.getBlockMetadata(x, y, z) == 8)
		{
			ItemStack[] returnItems = {new ItemStack(_cottonItemId, 1, 3)};
			return Arrays.asList(returnItems);
		}
		else
		{
			return Block.blocksList[_sourceId].getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		}
	}

	@Override
	public void preHarvest(World world, int x, int y, int z)
	{
	}

	@Override
	public void postHarvest(World world, int x, int y, int z)
	{
		if(world.getBlockMetadata(x, y, z) == 3)
		{
			if(MineFactoryReloadedCore.playSounds.getBoolean(true))
			{
				world.playAuxSFXAtEntity(null, 2001, x, y, z, _sourceId + (3 << 12));
			}
			world.setBlockToAir(x, y, z);
		}
		else if(world.getBlockMetadata(x, y, z) == 8)
		{
			world.setBlockMetadataWithNotify(x, y, z, 6, 2);
		}
	}
}
