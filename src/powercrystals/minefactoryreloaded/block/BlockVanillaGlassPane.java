package powercrystals.minefactoryreloaded.block;

import net.minecraft.util.Icon;

public class BlockVanillaGlassPane extends BlockFactoryGlassPane
{
	public BlockVanillaGlassPane()
	{
		super(102);
		setHardness(0.3F);
		setStepSound(soundGlassFootstep);
		setUnlocalizedName("thinGlass");
	}
	
	@Override
	public Icon getBlockSideTextureFromMetadata(int meta)
	{
		return getSideTextureIndex();
	}
}
