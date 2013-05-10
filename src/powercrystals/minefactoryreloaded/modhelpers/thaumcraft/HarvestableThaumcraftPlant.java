package powercrystals.minefactoryreloaded.modhelpers.thaumcraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

public class HarvestableThaumcraftPlant implements IFactoryHarvestable
{
	private int _blockId;
	private ItemStack _quickSilver;
	
	public HarvestableThaumcraftPlant(int blockId) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException
	{
		_blockId = blockId;
		_quickSilver = new ItemStack((Item)Class.forName("thaumcraft.common.Config").getField("itemResource").get(null), 1, 3);
	}
	
	@Override
	public int getPlantId()
	{
		return _blockId;
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
		return world.getBlockMetadata(x, y, z) > 1;
	}
	
	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		List<ItemStack> drops = new ArrayList<ItemStack>();
		int md = world.getBlockMetadata(x, y, z);
		if(md == 2)
		{
			drops.add(new ItemStack(Item.blazePowder));
		}
		else if(md == 3)
		{
			drops.add(_quickSilver);
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