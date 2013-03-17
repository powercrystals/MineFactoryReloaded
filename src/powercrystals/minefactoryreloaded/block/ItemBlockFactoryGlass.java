package powercrystals.minefactoryreloaded.block;

public class ItemBlockFactoryGlass extends ItemBlockFactory
{
	public ItemBlockFactoryGlass(int blockId)
	{
		super(blockId);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		setNames(new String[] { "white", "orange", "magenta", "lightblue", "yellow", "lime", "pink", "gray", "lightgray", "cyan", "purple", "blue", "brown", "green", "red", "black" });
	}
}
