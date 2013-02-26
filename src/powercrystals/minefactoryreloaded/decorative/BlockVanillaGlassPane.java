package powercrystals.minefactoryreloaded.decorative;


public class BlockVanillaGlassPane extends BlockFactoryGlassPane
{
	public BlockVanillaGlassPane()
	{
		super(102, 49, 148);
		setHardness(0.3F);
		setStepSound(soundGlassFootstep);
		setBlockName("thinGlass");
	}
	
	@Override
	public int getBlockSideTextureFromMetadata(int meta)
	{
		return getSideTextureIndex();
	}
	
	@Override
	public String getTextureFile()
	{
		return "/terrain.png";
	}
}
