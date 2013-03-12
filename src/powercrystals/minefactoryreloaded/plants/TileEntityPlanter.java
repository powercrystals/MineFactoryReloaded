package powercrystals.minefactoryreloaded.plants;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileEntityPlanter extends TileEntityFactoryPowered implements ISidedInventory
{
	private HarvestAreaManager _areaManager;
	private boolean _isMultiPlanter = true;
		
	public TileEntityPlanter() 
	{
		super(160);
		_areaManager = new HarvestAreaManager(this, 1, 0, 0);
		_areaManager.setOverrideDirection(ForgeDirection.UP);
	}
	
	@Override
	public String getGuiBackground()
	{
		return "planter.png";
	}
	
	public static void registerPlantable(IFactoryPlantable plantable)
	{
		MFRRegistry.getPlantables().put(new Integer(plantable.getSourceId()), plantable);
	}
	
	@Override
	public boolean canRotate()
	{
		return false;
	}
	
	@Override
	protected void onFactoryInventoryChanged()
	{
		_areaManager.updateUpgradeLevel(_inventory[9]);
	}

	@Override
	public boolean activateMachine()
	{
		BlockPosition bp = _areaManager.getNextBlock().copy();
		bp.y += 1;
		List<Integer> slotOrder = Arrays.asList(0,1,2,3,4,5,6,7,8);
		
		if(_isMultiPlanter)
		{
			int ps = getPlanterSlotIdFromBp(bp); 
			Collections.shuffle(slotOrder);
			if(slotOrder.contains(ps) && slotOrder.indexOf(ps) != 0)
			{
				Collections.swap(slotOrder, slotOrder.indexOf(ps), 0);
			}
		}
		
		for(Integer stackIndex : slotOrder)
		{
			if(getStackInSlot(stackIndex) == null)
			{
				continue;
			}
			
			ItemStack availableStack = getStackInSlot(stackIndex);
			
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
			worldObj.setBlockAndMetadataWithNotify(bp.x, bp.y, bp.z,
					plantable.getPlantedBlockId(worldObj, bp.x, bp.y, bp.z, availableStack),
					plantable.getPlantedBlockMetadata(worldObj, bp.x, bp.y, bp.z, availableStack));
			plantable.postPlant(worldObj, bp.x, bp.y, bp.z, availableStack);
			decrStackSize(stackIndex, 1);
			return true;
		}
			
		setIdleTicks(getIdleTicksMax());
		return false;
	}
	
	//assumes a 3x3 grid in inventory slots 0-8
	//slot 0 is northwest, slot 2 is northeast, etc
	private int getPlanterSlotIdFromBp(BlockPosition bp)
	{
		int radius = _areaManager.getRadius();
		int xOffset = (bp.x - this.xCoord) / radius;
		int zOffset = (bp.z - this.zCoord) / radius;
		return 4 + xOffset + 3 * zOffset;
	}
	
	@Override
	public String getInvName()
	{
		return "Planter";
	}
	
	@Override
	public int getSizeInventory()
	{
		return 10;
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
		return 0;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side)
	{
		return 9;
	}
}
