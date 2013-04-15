package powercrystals.minefactoryreloaded.block;

public class ItemBlockDecorativeStone extends ItemBlockFactory
{
	public ItemBlockDecorativeStone(int id)
	{
		super(id);
		setMaxDamage(0);
		setHasSubtypes(true);
		setNames(new String [] { "black.smooth", "white.smooth", "black.cobble", "white.cobble", "black.brick.large", "white.brick.large",
				"black.brick.small", "white.brick.small", "black.gravel", "white.gravel", "black.paved", "white.paved" });
	}
}
