package powercrystals.minefactoryreloaded.modhelpers.twilightforest;

import powercrystals.minefactoryreloaded.farmables.grindables.GrindableSheep;

public class GrindableTFBighorn extends GrindableSheep
{
private Class<?> _tfBighornClass;

public GrindableTFBighorn(Class<?> tfBighornClass)
{
_tfBighornClass = tfBighornClass;
}

@Override
public Class<?> getGrindableEntity()
{
return _tfBighornClass;
}
}