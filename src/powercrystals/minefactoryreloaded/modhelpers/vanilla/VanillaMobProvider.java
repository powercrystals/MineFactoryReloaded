package powercrystals.minefactoryreloaded.modhelpers.vanilla;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IRandomMobProvider;
import powercrystals.minefactoryreloaded.api.RandomMob;
import powercrystals.minefactoryreloaded.core.MFRUtil;

public class VanillaMobProvider implements IRandomMobProvider
{
	@Override
	public List<RandomMob> getRandomMobs(World world)
	{
		List<RandomMob> mobs = new ArrayList<RandomMob>();
		
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityMooshroom.class, world), 20));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntitySlime.class, world), 20));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityCow.class, world), 100));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityChicken.class, world), 100));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntitySheep.class, world), 100));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityWitch.class, world), 10));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityGhast.class, world), 15));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityPig.class, world), 100));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityCreeper.class, world), 25));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntitySquid.class, world), 30));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityMinecartHopper.class, world), 15));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityOcelot.class, world), 20));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityWolf.class, world), 20));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityBat.class, world), 35));
		
		EntityCreeper chargedCreeper = (EntityCreeper)MFRUtil.prepareMob(EntityCreeper.class, world);
		NBTTagCompound creeperNBT = new NBTTagCompound(); 
		chargedCreeper.writeToNBT(creeperNBT);
		creeperNBT.setBoolean("powered", true);
		creeperNBT.setShort("Fuse", (short)120);
		chargedCreeper.readFromNBT(creeperNBT);
		mobs.add(new RandomMob(chargedCreeper, 5));
		
		EntityTNTPrimed armedTNT = (EntityTNTPrimed)MFRUtil.prepareMob(EntityTNTPrimed.class, world);
		armedTNT.fuse = 120;
		mobs.add(new RandomMob(armedTNT, 5));
		
		EntitySlime invisislime = (EntitySlime)MFRUtil.prepareMob(EntitySlime.class, world);
		invisislime.addPotionEffect(new PotionEffect(Potion.invisibility.id, 120 * 20));
		mobs.add(new RandomMob(invisislime, 5));
		
		EntityMooshroom invisishroom = (EntityMooshroom)MFRUtil.prepareMob(EntityMooshroom.class, world);
		invisishroom.addPotionEffect(new PotionEffect(Potion.invisibility.id, 120 * 20));
		mobs.add(new RandomMob(invisishroom, 5));
		
		EntitySkeleton skeleton1 = (EntitySkeleton)MFRUtil.prepareMob(EntitySkeleton.class, world);
		EntitySkeleton skeleton2 = (EntitySkeleton)MFRUtil.prepareMob(EntitySkeleton.class, world);
		EntitySkeleton skeleton3 = (EntitySkeleton)MFRUtil.prepareMob(EntitySkeleton.class, world);
		EntitySkeleton skeleton4 = (EntitySkeleton)MFRUtil.prepareMob(EntitySkeleton.class, world);
		skeleton4.mountEntity(skeleton3);
		skeleton3.mountEntity(skeleton2);
		skeleton2.mountEntity(skeleton1);
		mobs.add(new RandomMob(skeleton1, 2));
		
		EntityBlaze blazeJockey = (EntityBlaze)MFRUtil.prepareMob(EntityBlaze.class, world);
		EntityGhast blazeMount = (EntityGhast)MFRUtil.prepareMob(EntityGhast.class, world);
		blazeJockey.mountEntity(blazeMount);
		mobs.add(new RandomMob(blazeMount, 2));
		
		EntityCreeper creeperJockey = (EntityCreeper)MFRUtil.prepareMob(EntityCreeper.class, world);
		EntityCaveSpider creeperMount = (EntityCaveSpider)MFRUtil.prepareMob(EntityCaveSpider.class, world);
		creeperJockey.mountEntity(creeperMount);
		mobs.add(new RandomMob(creeperMount, 2));
		
		return mobs;
	}
}
