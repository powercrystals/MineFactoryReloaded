package powercrystals.minefactoryreloaded.api;

/**
 * @author PowerCrystals
 *
 * Determines what kind of action a given fertilizer can perform. Currently only has "GrowPlant". Your IFactoryFertilizable instances should check this before
 * performing any action to maintain future compatibility.
 */
public enum FertilizerType
{
	/**
	 * The fertilizer will grow a plant.
	 */
	GrowPlant,
}
