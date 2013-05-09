package powercrystals.minefactoryreloaded.tile.machine;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import buildcraft.core.IMachine;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactory;

public class TileEntityLaserDrill extends TileEntityFactory implements IInventory, IMachine
{
	private static final int _energyPerWork = 2500;
	private static final int _energyDrawMax = 10000;
	
	private static final int _energyStoredMax = 100000;
	private int _energyStored;
	
	private static final int _workStoredMax = 300;
	private int _workStored;
	
	private List<ItemStack> _drops = new ArrayList<ItemStack>();
	
	private int _bedrockLevel;
	
	public TileEntityLaserDrill()
	{
		_drops.add(new ItemStack(Block.oreCoal));
		_drops.add(new ItemStack(Block.oreDiamond));
		_drops.add(new ItemStack(Block.oreEmerald));
		_drops.add(new ItemStack(Block.oreGold));
		_drops.add(new ItemStack(Block.oreIron));
		_drops.add(new ItemStack(Block.oreLapis));
		_drops.add(new ItemStack(Block.oreRedstone));
	}
	
	public int addEnergy(int energy)
	{
		int energyToAdd = Math.min(energy, _energyStoredMax - _energyStored);
		_energyStored += energyToAdd;
		return energy - energyToAdd;
	}
	
	@Override
	public void updateEntity()
	{
		if(worldObj.isRemote || isInvalid())
		{
			return;
		}

		if(shouldCheckDrill())
		{
			updateDrill();
		}

		int lowerId = worldObj.getBlockId(xCoord, yCoord - 1, zCoord);
		
		if(_bedrockLevel < 0)
		{
			if(lowerId == MineFactoryReloadedCore.fakeLaserBlock.blockID)
			{
				worldObj.setBlockToAir(xCoord, yCoord - 1, zCoord);
			}
			return;
		}
			
		if(lowerId != MineFactoryReloadedCore.fakeLaserBlock.blockID && (Block.blocksList[lowerId] == null || Block.blocksList[lowerId].isAirBlock(worldObj, xCoord, yCoord - 1, zCoord)))
		{
			worldObj.setBlock(xCoord, yCoord - 1, zCoord, MineFactoryReloadedCore.fakeLaserBlock.blockID);
		}
		
		int energyToDraw = Math.min(_energyDrawMax, _energyStored / 4);
		int workDone = energyToDraw / _energyPerWork;
		_workStored += workDone;
		_energyStored -= workDone * _energyPerWork;
		
		while(_workStored >= _workStoredMax)
		{
			_workStored -= _workStoredMax;
			UtilInventory.dropStack(this, getRandomDrop(), ForgeDirection.UP);
		}
	}
	
	private boolean shouldCheckDrill()
	{
		return worldObj.getWorldTime() % 32 == 0;
	}
	
	private void updateDrill()
	{
		int y = Integer.MAX_VALUE;
		for(y = yCoord - 1; y >= 0; y--)
		{
			int id = worldObj.getBlockId(xCoord, y, zCoord);
			if(id != MineFactoryReloadedCore.fakeLaserBlock.blockID && id != Block.bedrock.blockID && id != 0)
			{
				_bedrockLevel = -1;
				return;
			}
			else if(id == Block.bedrock.blockID)
			{
				_bedrockLevel = y;
				return;
			}
		}

		_bedrockLevel = -1;
	}
	
	private ItemStack getRandomDrop()
	{
		return _drops.get(worldObj.rand.nextInt(_drops.size())).copy();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536;
	}
	
	public boolean shouldDrawBeam()
	{
		updateDrill();
		return _bedrockLevel >= 0;
	}
	
	public int getBeamHeight()
	{
		return yCoord - _bedrockLevel;
	}
	
	// IInventory

	@Override
	public int getSizeInventory()
	{
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
	}

	@Override
	public String getInvName()
	{
		return "Laser Drill";
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return entityplayer.getDistanceSq(xCoord, yCoord, zCoord) <= 64;
	}

	@Override
	public void openChest()
	{
	}

	@Override
	public void closeChest()
	{
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack)
	{
		return false;
	}

	// IMachine
	
	@Override
	public boolean isActive()
	{
		return false;
	}

	@Override
	public boolean manageLiquids()
	{
		return false;
	}

	@Override
	public boolean manageSolids()
	{
		return true;
	}

	@Override
	public boolean allowActions()
	{
		return false;
	}
}
