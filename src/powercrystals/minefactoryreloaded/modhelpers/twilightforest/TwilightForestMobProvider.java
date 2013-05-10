package powercrystals.minefactoryreloaded.modhelpers.twilightforest;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IRandomMobProvider;
import powercrystals.minefactoryreloaded.api.RandomMob;
import powercrystals.minefactoryreloaded.core.MFRUtil;

public class TwilightForestMobProvider implements IRandomMobProvider
{
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<RandomMob> getRandomMobs(World world)
	{
		List<RandomMob> mobs = new ArrayList<RandomMob>();
		
		try
		{
			Class tfBoar = Class.forName("twilightforest.entity.passive.EntityTFBoar");
			Class tfDeathTome = Class.forName("twilightforest.entity.EntityTFDeathTome");
			Class tfDeer = Class.forName("twilightforest.entity.passive.EntityTFDeer");
			Class tfMazeSlime = Class.forName("twilightforest.entity.EntityTFMazeSlime");
			Class tfPenguin = Class.forName("twilightforest.entity.passive.EntityTFPenguin");
			Class tfPinchBeetle = Class.forName("twilightforest.entity.EntityTFPinchBeetle");
			Class tfRaven = Class.forName("twilightforest.entity.passive.EntityTFRaven");
			Class tfSquirrel = Class.forName("twilightforest.entity.passive.EntityTFSquirrel");
			Class tfTinyBird = Class.forName("twilightforest.entity.passive.EntityTFTinyBird");
			Class tfWraith = Class.forName("twilightforest.entity.EntityTFWraith");
			
			mobs.add(new RandomMob(MFRUtil.prepareMob(tfBoar, world), 80));
			mobs.add(new RandomMob(MFRUtil.prepareMob(tfDeer, world), 80));
			mobs.add(new RandomMob(MFRUtil.prepareMob(tfRaven, world), 50));
			mobs.add(new RandomMob(MFRUtil.prepareMob(tfPenguin, world), 25));
			mobs.add(new RandomMob(MFRUtil.prepareMob(tfSquirrel, world), 25));
			mobs.add(new RandomMob(MFRUtil.prepareMob(tfTinyBird, world), 15));
			mobs.add(new RandomMob(MFRUtil.prepareMob(tfMazeSlime, world), 15));
			mobs.add(new RandomMob(MFRUtil.prepareMob(tfPinchBeetle, world), 15));
			mobs.add(new RandomMob(MFRUtil.prepareMob(tfWraith, world), 5));
			mobs.add(new RandomMob(MFRUtil.prepareMob(tfDeathTome, world), 10));
			
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return mobs;
	}
	
}
