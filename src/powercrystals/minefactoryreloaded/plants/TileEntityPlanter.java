package powercrystals.minefactoryreloaded.plants;

import java.util.HashMap;
import java.util.Map;

import powercrystals.core.position.Area;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityPlanter extends TileEntityFactoryPowered
{
	private static Map<Integer, IFactoryPlantable> plantables = new HashMap<Integer, IFactoryPlantable>();
	
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
		plantables.put(new Integer(plantable.getSourceId()), plantable);
	}
	
	@Override
	public boolean canRotate()
	{
		return false;
	}

	@Override
	public boolean activateMachine()
	{
		Area a = _areaManager.getHarvestArea();
		for(BlockPosition bp : a.getPositionsBottomFirst())
		{
			for(int stackIndex = 0; stackIndex < getSizeInventory(); stackIndex++)
			{
				if(getStackInSlot(stackIndex) == null)
				{
					continue;
				}
				
				ItemStack availableStack = getStackInSlot(stackIndex);
				
				if(!plantables.containsKey(new Integer(availableStack.itemID)))
				{
					continue;
				}
				IFactoryPlantable plantable = plantables.get(new Integer(availableStack.itemID));
	
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
		}
		setIdleTicks(getIdleTicksMax());
		return false;
	}

	@Override
	public String getInvName()
	{
		return "Planter";
	}
	
	@Override
	public int getSizeInventory()
	{
		return 9;
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
		return 200;
	}
}
