package powercrystals.minefactoryreloaded.core;

import net.minecraft.util.DamageSource;

public class GrindingDamage extends DamageSource
{
	public GrindingDamage()
	{
		this(null);
	}

	public GrindingDamage(String type)
	{
		super(type == null ? "mfr.grinder" : type);
		setDamageBypassesArmor();
		setDamageAllowedInCreativeMode();
	}
}