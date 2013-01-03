package powercrystals.minefactoryreloaded.api;

/**
 * @author PowerCrystals
 *
 * Determines what algorithm the Harvester uses when it encounters this IFactoryHarvestable in the world.
 */
public enum HarvestType
{
	/**
	 * Just break the block - no special action needed. Carrots, flowers, etc.
	 */
	Normal,
	/**
	 * Search for identical blocks above but leave this bottom one for the future. Cactus and sugarcane.
	 */
	LeaveBottom,
	/**
	 * This block is the base of a tree and the harvester should enter tree-cutting mode.
	 */
	Tree,
	/**
	 * This block is part of a tree as above, but leaves are cut before tree logs so that leaves do not decay more than necessary.
	 */
	TreeLeaf
}
