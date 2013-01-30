package powercrystals.minefactoryreloaded.processing;

import net.minecraft.block.Block;

public class TileEntityLavaFabricator extends TileEntityLiquidFabricator
{
	public TileEntityLavaFabricator()
	{
		super(Block.lavaStill.blockID, 200, 20);
	}

	@Override
	public String getInvName()
	{
		return "Lava Fabricator";
	}
}
