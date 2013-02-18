package powercrystals.minefactoryreloaded.modhelpers.soulshards;

import powercrystals.minefactoryreloaded.farmables.grindables.GrindableSkeleton;

public class GrindableSoulShardSkeleton extends GrindableSkeleton
{
	private Class<?> _soulShardSkeletonClass;
	
	public GrindableSoulShardSkeleton(Class<?> soulShardSkeletonClass)
	{
		_soulShardSkeletonClass = soulShardSkeletonClass;
	}
	
	@Override
	public Class<?> getGrindableEntity()
	{
		return _soulShardSkeletonClass;
	}
}
