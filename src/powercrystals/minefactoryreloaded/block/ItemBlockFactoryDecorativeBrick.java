package powercrystals.minefactoryreloaded.block;

public class ItemBlockFactoryDecorativeBrick extends ItemBlockFactory
{
	public ItemBlockFactoryDecorativeBrick(int id)
	{
		super(id);
		setMaxDamage(0);
		setHasSubtypes(true);
		setNames(new String[] { "glowstone", "ice", "lapis", "obsidian", "pavedstone", "snow", "glowstone_large", "ice_large", "lapis_large", "obsidian_large", "snow_large" });
	}
}
