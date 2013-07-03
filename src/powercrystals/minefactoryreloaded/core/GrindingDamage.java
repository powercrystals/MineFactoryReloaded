package powercrystals.minefactoryreloaded.core;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;

public class GrindingDamage extends DamageSource
{
	protected int _msgCount;
	protected Random _rand;
	public GrindingDamage()
	{
		this(null, 1);
	}
	
	public GrindingDamage(String type)
	{
		this(type, 1);
	}

	public GrindingDamage(String type, int deathMessages)
	{
		super(type == null ? "mfr.grinder" : type);
		setDamageBypassesArmor();
		setDamageAllowedInCreativeMode();
		_msgCount = Math.max(deathMessages, 1);
		_rand = new Random();
	}
	
	@Override
    public String getDeathMessage(EntityLiving par1EntityLiving)
    {
        EntityLiving entityliving1 = par1EntityLiving.func_94060_bK();
        String s = "death.attack." + this.damageType;
        if (_msgCount > 0)
        {
        	int msg = _rand.nextInt(_msgCount);
        	if (msg != 0)
        	{
        		s += "." + msg;
        	}
        }
        String s1 = s + ".player";
        return entityliving1 != null && StatCollector.func_94522_b(s1) ? StatCollector.translateToLocalFormatted(s1, new Object[] {par1EntityLiving.getTranslatedEntityName(), entityliving1.getTranslatedEntityName()}): StatCollector.translateToLocalFormatted(s, new Object[] {par1EntityLiving.getTranslatedEntityName()});
    }
}