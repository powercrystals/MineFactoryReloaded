package powercrystals.minefactoryreloaded.plants;

import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileEntityPlanter extends TileEntityFactoryPowered implements ISidedInventory
{
	private HarvestAreaManager _areaManager;
	
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
		BlockPosition bp = _areaManager.getNextBlock();
		System.out.println("Testing position " + bp.toString());
		
		for(int stackIndex = 0; stackIndex < getSizeInventory(); stackIndex++)
		{
			if(getStackInSlot(stackIndex) == null)
			{
				System.out.println("Stack in slot " + stackIndex + " is null");
				continue;
			}
			
			ItemStack availableStack = getStackInSlot(stackIndex);
			
			if(!MFRRegistry.getPlantables().containsKey(new Integer(availableStack.itemID)))
			{
				System.out.println("Stack in slot " + stackIndex + " is not plantable");
				continue;
			}
			IFactoryPlantable plantable = MFRRegistry.getPlantables().get(new Integer(availableStack.itemID));

			if(!plantable.canBePlantedHere(worldObj, bp.x, bp.y, bp.z, availableStack))
			{
				System.out.println("Stack in slot " + stackIndex + " cannot be planted here");
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

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		
		onFactoryInventoryChanged();
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
