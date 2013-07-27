package powercrystals.minefactoryreloaded.world;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import skyboy.core.world.WorldProxy;

// Nigel says: This is a
public class SmashingWorld extends WorldProxy
{
	protected int blockID, meta;
	protected int x = 0, y = 64, z = 0;
	
	public SmashingWorld(World world)
	{
		super(world);
	}
	
	@Override
	public int getBlockId(int X, int Y, int Z)
	{
		if (x == X & y == Y & z == Z)
			return blockID;
		return 0;
	}
	
	@Override
	public int getBlockMetadata(int X, int Y, int Z)
	{
		if (x == X & y == Y & z == Z)
			return meta;
		return 0;
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList smashBlock(Block block, int blockId, int meta, int fortune)
	{
		if (block != null)
			return block.getBlockDropped(this, x, y, z, meta, fortune);
		return null;
	}

}
