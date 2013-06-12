package powercrystals.minefactoryreloaded.core;

import net.minecraft.util.DamageSource;

public class GrinderDamageSource extends DamageSource
{
	public GrinderDamageSource(String par1Str)
	{
		super(par1Str);
		setDamageBypassesArmor();
	}
}

