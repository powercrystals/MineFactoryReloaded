package powercrystals.minefactoryreloaded.tile.machine;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomItem;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.inventory.InventoryManager;
import powercrystals.core.random.WeightedRandomItemStack;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiLaserDrill;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerLaserDrill;
import powercrystals.minefactoryreloaded.setup.MFRConfig;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityLaserDrill extends TileEntityFactoryInventory
{
	private static final int _energyPerWork = 8000;
	private static final int _energyDrawMax = 10000;
	
	private static final int _energyStoredMax = 1000000;
	private int _energyStored;
	
	private int _workStoredMax = MFRConfig.laserdrillCost.getInt();
	private int _workStored;
	
	private int _bedrockLevel;
	
	private Random _rand;
	
	public TileEntityLaserDrill()
	{
		super(Machine.LaserDrill);
		_rand = new Random();
	}
	
	@Override
	public String getGuiBackground()
	{
		return "laserdrill.png";
	}
	
	@Override
	public ContainerFactoryInventory getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerLaserDrill(this, inventoryPlayer);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiLaserDrill(getContainer(inventoryPlayer), this);
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
		int energyPerWorkHere = (int)(_energyPerWork * (1 - 0.2 * Math.min(yCoord - _bedrockLevel, 128.0) / 128.0));
		
		int workDone = energyToDraw / energyPerWorkHere;
		_workStored += workDone;
		_energyStored -= workDone * energyPerWorkHere;
		
		while(_workStored >= _workStoredMax)
		{
			_workStored -= _workStoredMax;
			UtilInventory.dropStack(this, getRandomDrop(), ForgeDirection.UP);
		}
	}
	
	public int getWorkDone()
	{
		return _workStored;
	}
	
	public void setWorkDone(int work)
	{
		_workStored = work;
	}
	
	public int getWorkMax()
	{
		return _workStoredMax;
	}
	
	public int getEnergyStored()
	{
		return _energyStored;
	}
	
	public void setEnergyStored(int energy)
	{
		_energyStored = energy;
	}
	
	public int getEnergyMax()
	{
		return _energyStoredMax;
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
		
		_bedrockLevel = 0;
	}
	
	private ItemStack getRandomDrop()
	{
		List<WeightedRandomItemStack> drops = new LinkedList<WeightedRandomItemStack>();
		int boost = WeightedRandom.getTotalWeight(MFRRegistry.getLaserOres()) / 30;
		
		for(WeightedRandomItem i : MFRRegistry.getLaserOres())
		{
			WeightedRandomItemStack oldStack = (WeightedRandomItemStack)i;
			WeightedRandomItemStack newStack = new WeightedRandomItemStack(oldStack.itemWeight, oldStack.getStack());
			drops.add(newStack);
			for(ItemStack s : _inventory)
			{
				if(s == null || s.itemID != MineFactoryReloadedCore.laserFocusItem.itemID)
				{
					continue;
				}
				if(InventoryManager.stacksEqual(newStack.getStack(), MFRRegistry.getLaserPreferredOre(s.getItemDamage())))
				{
					newStack.itemWeight += boost;
				}
			}
		}
		
		return ((WeightedRandomItemStack)WeightedRandom.getRandomItem(_rand, drops)).getStack();
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
	
	@Override
	public int getSizeInventory()
	{
		return 6;
	}
	
	@Override
	public String getInvName()
	{
		return "Laser Drill";
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side)
	{
		return false;
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side)
	{
		return false;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return entityplayer.getDistanceSq(xCoord, yCoord, zCoord) <= 64;
	}
	
	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack)
	{
		return false;
	}
	
	@Override
	public boolean manageSolids()
	{
		return true;
	}
}
