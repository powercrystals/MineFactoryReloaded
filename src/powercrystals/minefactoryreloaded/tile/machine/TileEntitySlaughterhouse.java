package powercrystals.minefactoryreloaded.tile.machine;

import java.util.List;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;
import net.minecraftforge.liquids.LiquidDictionary;

import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.core.GrindingDamage;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;
import powercrystals.minefactoryreloaded.setup.Machine;

public class TileEntitySlaughterhouse extends TileEntityGrinder
{
	public TileEntitySlaughterhouse()
	{
		super(Machine.Slaughterhouse);
		_damageSource = new GrindingDamage("mfr.slaughterhouse");
	}
	
	@Override
	public void setWorldObj(World world)
	{
		super.setWorldObj(world);
		this._grindingWorld.setAllowSpawns(true);
	}
	
	@Override
	public String getInvName()
	{
		return "Slaughterhouse";
	}
	
	@Override
	public String getGuiBackground()
	{
		return "slaughterhouse.png";
	}
	
	@Override
	public ContainerFactoryPowered getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerFactoryPowered(this, inventoryPlayer);
	}
	
	@Override
	public boolean activateMachine()
	{
		_grindingWorld.cleanReferences();
		List<?> entities = worldObj.getEntitiesWithinAABB(EntityLiving.class, _areaManager.getHarvestArea().toAxisAlignedBB());
		
		for(Object o : entities)
		{
			if(o instanceof EntityPlayer)
			{
				continue;
			}
			EntityLiving e = (EntityLiving)o;
			for(Class<?> t : MFRRegistry.getSlaughterhouseBlacklist())
			{
				if(t.isInstance(e))
				{
					continue;
				}
			}
			if((e instanceof EntityAgeable && ((EntityAgeable)e).getGrowingAge() < 0) || e.isEntityInvulnerable() || e.getHealth() <= 0
					|| !_grindingWorld.addEntityForGrinding(e))
			{
				continue;
			}
			double massFound = Math.pow(e.boundingBox.getAverageEdgeLength(), 2);
			damageEntity(e);
			if(e.getHealth() <= 0)
			{
				_tank.fill(LiquidDictionary.getLiquid(_rand.nextInt(8) == 0 ? "pinkslime" : "meat", (int)(100 * massFound)), true);
				setIdleTicks(10);
			}
			else
			{
				setIdleTicks(5);
			}
			return true;
		}
		setIdleTicks(getIdleTicksMax());
		return false;
	}
	
	@Override
	protected void damageEntity(EntityLiving entity)
	{
		setRecentlyHit(entity, 0);
		entity.attackEntityFrom(_damageSource, 500000);
	}
	
	@Override
	public int getEnergyStoredMax()
	{
		return 16000;
	}
	
	@Override
	public boolean manageSolids()
	{
		return false;
	}
}
