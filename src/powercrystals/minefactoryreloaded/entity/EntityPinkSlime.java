package powercrystals.minefactoryreloaded.entity;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityPinkSlime extends EntitySlime
{
	public EntityPinkSlime(World world)
	{
		super(world);
		this.texture = MineFactoryReloadedCore.mobTextureFolder + "pinkslime.png";
	}

	@Override
	protected int getJumpDelay()
	{
		return this.rand.nextInt(10) + 5;
	}

	@Override
	protected int getDropItemId()
	{
		return this.getSlimeSize() == 1 ? MineFactoryReloadedCore.pinkSlimeballItem.itemID : 0;
	}

	@Override
	public void setSlimeSize(int size)
	{
		if(size > 4)
		{
			worldObj.newExplosion(this, posX, posY, posZ, 0.1F, false, true);
			this.attackEntityFrom(DamageSource.generic, 50);

			if(!worldObj.isRemote)
			{
				ItemStack meats = new ItemStack(MineFactoryReloadedCore.meatNuggetRawItem, worldObj.rand.nextInt(12) + 4);
				EntityItem e = new EntityItem(worldObj, posX, posY, posZ, meats);
				worldObj.spawnEntityInWorld(e);
			}
		}
		else
		{
			super.setSlimeSize(size);
		}
	}
	
	@Override
	protected String getSlimeParticle()
	{
		return "";
	}

	@Override
	protected EntityPinkSlime createInstance()
	{
		return new EntityPinkSlime(this.worldObj);
	}
}
