package powercrystals.minefactoryreloaded.modhelpers.twilightforest;

import powercrystals.minefactoryreloaded.farmables.ranchables.RanchableSheep;

public class RanchableTFBighorn extends RanchableSheep
{
private Class<?> _tfBighornClass;

public RanchableTFBighorn(Class<?> tfBighornClass)
{
_tfBighornClass = tfBighornClass;
}

@Override
public Class<?> getRanchableEntity()
{
return _tfBighornClass;
}
}