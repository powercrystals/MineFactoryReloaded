package powercrystals.minefactoryreloaded.item;

import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IToolHammer;

public class ItemFactoryHammer extends ItemFactory implements IToolHammer
{
	public ItemFactoryHammer(int i)
	{
		super(i);
	}
	
	@Override
	public boolean shouldPassSneakingClickToBlock(World world, int x, int y, int z)
	{
		return true;
	}
}
