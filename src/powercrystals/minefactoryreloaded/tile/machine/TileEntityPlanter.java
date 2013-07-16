package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.IHarvestAreaContainer;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiPlanter;
import powercrystals.minefactoryreloaded.gui.container.ContainerPlanter;
import powercrystals.minefactoryreloaded.gui.container.ContainerUpgradable;
import powercrystals.minefactoryreloaded.item.ItemUpgrade;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityPlanter extends TileEntityFactoryPowered implements IHarvestAreaContainer
{
	private HarvestAreaManager _areaManager;
	
	public TileEntityPlanter() 
	{
		super(Machine.Planter);
		_areaManager = new HarvestAreaManager(this, 1, 0, 0);
		_areaManager.setOverrideDirection(ForgeDirection.UP);
		_areaManager.setOriginOffset(0, 1, 0);
	}
	
	@Override
	public String getGuiBackground()
	{
		return "planter.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiPlanter(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerUpgradable getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerPlanter(this, inventoryPlayer);
	}
	
	@Override
	protected void onFactoryInventoryChanged()
	{
		_areaManager.updateUpgradeLevel(_inventory[9]);
	}
	
	@Override
	public HarvestAreaManager getHAM()
	{
		return _areaManager;
	}
	
	@Override
	public boolean activateMachine()
	{
		BlockPosition bp = _areaManager.getNextBlock();
		
		ItemStack match = _inventory[getPlanterSlotIdFromBp(bp)];
		
		for(int stackIndex = 10; stackIndex <= 25; stackIndex++)
		{		
			ItemStack availableStack = getStackInSlot(stackIndex);
			
			//skip planting attempt if there's no stack in that slot, or if there's a template item that's not matched
			if(availableStack == null ||
					(match != null &&
					!stacksEqual(match, availableStack)))
			{
				continue;
			}
			
			if(!MFRRegistry.getPlantables().containsKey(new Integer(availableStack.itemID)))
			{
				continue;
			}
			IFactoryPlantable plantable = MFRRegistry.getPlantables().get(new Integer(availableStack.itemID));
			
			if(!plantable.canBePlantedHere(worldObj, bp.x, bp.y, bp.z, availableStack))
			{
				continue;
			}
			plantable.prePlant(worldObj, bp.x, bp.y, bp.z, availableStack);
			worldObj.setBlock(bp.x, bp.y, bp.z,
					plantable.getPlantedBlockId(worldObj, bp.x, bp.y, bp.z, availableStack),
					plantable.getPlantedBlockMetadata(worldObj, bp.x, bp.y, bp.z, availableStack), 3);
			plantable.postPlant(worldObj, bp.x, bp.y, bp.z, availableStack);
			decrStackSize(stackIndex, 1);
			return true;
		}
		
		setIdleTicks(getIdleTicksMax());
		return false;
	}
	 
	private boolean stacksEqual(ItemStack a, ItemStack b)
	{
		if (a == null | b == null ||
				(a.itemID != b.itemID) ||
				(a.getItemDamage() != b.getItemDamage()) ||
				a.hasTagCompound() != b.hasTagCompound())
		{
			return false;
		}
		if (!a.hasTagCompound())
		{
			return true;
		}
		NBTTagCompound tagA = (NBTTagCompound)a.getTagCompound().copy(),
				tagB = (NBTTagCompound)b.getTagCompound().copy();
		tagA.removeTag("display"); tagB.removeTag("display");
		tagA.removeTag("ench"); tagB.removeTag("ench");
		tagA.removeTag("RepairCost"); tagB.removeTag("RepairCost");
		return tagA.equals(tagB);
	}
	
	//assumes a 3x3 grid in inventory slots 0-8
	//slot 0 is northwest, slot 2 is northeast, etc
	private int getPlanterSlotIdFromBp(BlockPosition bp)
	{
		int radius = _areaManager.getRadius();
		int xAdjusted = Math.round( 1.49F * (bp.x - this.xCoord) / radius);
		int zAdjusted = Math.round( 1.49F * (bp.z - this.zCoord) / radius);
		return 4 + xAdjusted + 3 * zAdjusted;
	}
	
	@Override
	public int getSizeInventory()
	{
		return 26;
	}
	
	@Override
	public int getEnergyStoredMax()
	{
		return 8000;
	}
	
	@Override
	public int getWorkMax()
	{
		return 1;
	}
	
	@Override
	public int getIdleTicksMax()
	{
		return 5;
	}
	
	@Override
	public int getStartInventorySide(ForgeDirection side)
	{
		return 9;
	}
	
	@Override
	public boolean shouldDropSlotWhenBroken(int slot)
	{
		return slot > 8;
	}
	
	@Override
	public int getSizeInventorySide(ForgeDirection side)
	{
		return 17;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int sideordinal)
	{
		if(slot > 9)
		{
			return true;
		}
		else if(slot == 9)
		{
			return stack != null && stack.getItem() instanceof ItemUpgrade;
		}
		return false;
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int sideordinal)
	{
		if(slot >= 10) return true;
		return false;
	}
}
