package powercrystals.minefactoryreloaded.decorative;

import java.util.Random;

import net.minecraft.block.BlockIce;
import net.minecraft.world.World;

public class BlockVanillaIce extends BlockIce
{
	public BlockVanillaIce()
	{
		super(79, 67);
		setHardness(0.5F);
		setLightOpacity(3);
		setStepSound(soundGlassFootstep);
		setBlockName("ice");
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if(world.getBlockMetadata(x, y, z) == 0)
		{
			super.updateTick(world, x, y, z, rand);
		}
	}
}
