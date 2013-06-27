package powercrystals.minefactoryreloaded.modhelpers.ic2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.IFactoryFruit;

public class FruitIC2Resin implements IFactoryFruit
{
	private Block _rubberWood;
	private int _rubberID;
	private ItemStack _resin;
	private Random _rand = new Random();

	public FruitIC2Resin(ItemStack rubberWood, ItemStack resin)
	{
		this._rubberID = ((ItemBlock)rubberWood.getItem()).getBlockID();
		this._rubberWood = Block.blocksList[_rubberID];
		this._resin = resin;
	}

	@Override
	public int getSourceBlockId()
	{
		return _rubberWood.blockID;
	}

	@Override
	public boolean canBePicked(World world, int x, int y, int z)
	{
		int blockID = world.getBlockId(x, y, z), meta = world.getBlockMetadata(x, y, z);
		return blockID == _rubberID && ((meta >= 2 & meta <= 5) || (meta > 5 && _rand.nextInt(100) == 0));
	}

	@Override
	public ItemStack getReplacementBlock(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if ((meta < 2 | meta > 5) || _rand.nextInt(5) == 0)
		{
			return new ItemStack(_rubberID, 0, 0);
		}
		return new ItemStack(_rubberID, 0, meta + 6);
	}

	@Override
	public void prePick(World world, int x, int y, int z)
	{
		
	}

	@Override
	public List<ItemStack> getDrops(World world, Random rand, int x, int y, int z)
	{
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(_resin.itemID, 1 + rand.nextInt(3), _resin.getItemDamage()));
		return list;
	}

	@Override
	public void postPick(World world, int x, int y, int z)
	{
		
	}

}
