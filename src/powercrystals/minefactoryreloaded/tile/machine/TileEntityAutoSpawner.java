package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.core.ITankContainerBucketable;
import powercrystals.minefactoryreloaded.gui.client.GuiAutoSpawner;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerAutoSpawner;
import powercrystals.minefactoryreloaded.item.ItemSafariNet;
import powercrystals.minefactoryreloaded.setup.MFRConfig;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityAutoSpawner extends TileEntityFactoryPowered implements ITankContainerBucketable
{
	private static final int _spawnRange = 4;
	private LiquidTank _tank;
	private boolean _spawnExact = false;
	
	public TileEntityAutoSpawner()
	{
		super(Machine.AutoSpawner);
		_tank = new LiquidTank(LiquidContainerRegistry.BUCKET_VOLUME * 4);
	}
	
	public boolean getSpawnExact()
	{
		return _spawnExact;
	}
	
	public void setSpawnExact(boolean spawnExact)
	{
		_spawnExact = spawnExact;
	}
	
	@Override
	public String getGuiBackground()
	{
		return "autospawner.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiAutoSpawner(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerAutoSpawner getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerAutoSpawner(this, inventoryPlayer);
	}
	
	@Override
	public int getSizeInventory()
	{
		return 1;
	}
	
	@Override
	public String getInvName()
	{
		return "Auto-Spawner";
	}
	
	@Override
	protected boolean activateMachine()
	{
		ItemStack item = getStackInSlot(0);
		if(!isStackValidForSlot(0, item))
		{
			setWorkDone(0);
			return false;
		}
		NBTTagCompound itemTag = item.getTagCompound();
		String entityID = itemTag.getString("id");
		boolean isBlackListed = MFRRegistry.getAutoSpawnerBlacklist().contains(entityID);
		blackList: if (!isBlackListed)
		{
			Class<?> e = (Class<?>)EntityList.stringToClassMapping.get(entityID);
			if (e == null)
			{
				isBlackListed = true;
				break blackList;
			}
			for (Class<?> t : MFRRegistry.getAutoSpawnerClassBlacklist())
			{
				if(t.isAssignableFrom(e))
				{
					isBlackListed = true;
					break blackList;
				}
			}
		}
		if (isBlackListed)
		{
			setWorkDone(0);
			return false;
		}
		if(getWorkDone() < getWorkMax())
		{
			if(_tank.getLiquid() != null && _tank.getLiquid().amount >= 10)
			{
				_tank.getLiquid().amount -= 10;
				setWorkDone(getWorkDone() + 1);
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			Entity spawnedEntity = EntityList.createEntityByName(entityID, worldObj);
			
			if(!(spawnedEntity instanceof EntityLiving))
			{
				return false;
			}
			
			EntityLiving spawnedLiving = (EntityLiving)spawnedEntity;
			
			if(_spawnExact)
			{
				NBTTagCompound tag = (NBTTagCompound)itemTag.copy();
				spawnedLiving.readEntityFromNBT(tag);
				for (int i = 0; i < 5; ++i)
				{
					spawnedLiving.func_96120_a(i, 0);
				}
			}
			
			double x = xCoord + (worldObj.rand.nextDouble() - worldObj.rand.nextDouble()) * _spawnRange;
			double y = yCoord + worldObj.rand.nextInt(3) - 1;
			double z = zCoord + (worldObj.rand.nextDouble() - worldObj.rand.nextDouble()) * _spawnRange;
			
			spawnedLiving.setLocationAndAngles(x, y, z, worldObj.rand.nextFloat() * 360.0F, 0.0F);
			
			if(!worldObj.checkNoEntityCollision(spawnedLiving.boundingBox) ||
					!worldObj.getCollidingBoundingBoxes(spawnedLiving, spawnedLiving.boundingBox).isEmpty() ||
					(worldObj.isAnyLiquid(spawnedLiving.boundingBox) != (spawnedLiving instanceof EntityWaterMob)))
			{
				return false;
			}
			
			if (!_spawnExact)
			{
				spawnedLiving.initCreature();
			}
			
			worldObj.spawnEntityInWorld(spawnedLiving);
			worldObj.playAuxSFX(2004, this.xCoord, this.yCoord, this.zCoord, 0);
			
			spawnedLiving.spawnExplosionParticle();
			setWorkDone(0);
			return true;
		}
	}
	
	@Override
	public int getEnergyStoredMax()
	{
		return 32000;
	}
	
	@Override
	public int getWorkMax()
	{
		return _spawnExact ? MFRConfig.autospawnerCostExact.getInt() : MFRConfig.autospawnerCostStandard.getInt();
	}
	
	@Override
	public int getIdleTicksMax()
	{
		return 200;
	}
	
	@Override
	public ILiquidTank getTank()
	{
		return _tank;
	}
	
	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
	{
		if(resource == null || resource.itemID != LiquidDictionary.getLiquid("mobEssence", 1000).itemID)
		{
			return 0;
		}
		return _tank.fill(resource, doFill);
	}
	
	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill)
	{
		return fill(ForgeDirection.UNKNOWN, resource, doFill);
	}
	
	@Override
	public boolean allowBucketFill()
	{
		return true;
	}
	
	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		return null;
	}
	
	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain)
	{
		return null;
	}
	
	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction)
	{
		return new ILiquidTank[] { _tank };
	}
	
	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type)
	{
		return _tank;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side)
	{
		return isStackValidForSlot(slot, itemstack);
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack)
	{
		return ItemSafariNet.isSafariNet(itemstack) &&
				!ItemSafariNet.isSingleUse(itemstack) &&
				!ItemSafariNet.isEmpty(itemstack);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		_spawnExact = nbttagcompound.getBoolean("spawnExact");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("spawnExact", _spawnExact);
	}
}
