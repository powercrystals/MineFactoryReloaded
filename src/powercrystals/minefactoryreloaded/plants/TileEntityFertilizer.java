package powercrystals.minefactoryreloaded.plants;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizer;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;

public class TileEntityFertilizer extends TileEntityFactoryPowered implements ISidedInventory
{
	private static Map<Integer, IFactoryFertilizer> fertilizers = new HashMap<Integer, IFactoryFertilizer>();
	private static Map<Integer, IFactoryFertilizable> fertilizables = new HashMap<Integer, IFactoryFertilizable>();
	private Random _rand;
	
	private HarvestAreaManager _areaManager;
	
	public static void registerFertilizable(IFactoryFertilizable fertilizable)
	{
		fertilizables.put(fertilizable.getFertilizableBlockId(), fertilizable);
	}
	
	public static void registerFertilizer(IFactoryFertilizer fertilizer)
	{
		Integer i = new Integer(fertilizer.getFertilizerId());
		if(!fertilizers.containsKey(i))
		{
			fertilizers.put(i, fertilizer);
		}
	}

	public TileEntityFertilizer()
	{
		super(960);
		_rand = new Random();
		_areaManager = new HarvestAreaManager(this, 1, 0, 0);
	}
	
	@Override
	protected void onFactoryInventoryChanged()
	{
		_areaManager.updateUpgradeLevel(_inventory[9]);
	}
	
	@Override
	public String getGuiBackground()
	{
		return "fertilizer.png";
	}

	@Override
	public boolean activateMachine()
	{
		BlockPosition bp = _areaManager.getNextBlock();
		
		int targetId = worldObj.getBlockId(bp.x, bp.y, bp.z);
		if(!fertilizables.containsKey(new Integer(targetId)))
		{
			setIdleTicks(getIdleTicksMax());
			return false;
		}
		for(int stackIndex = 0; stackIndex < getSizeInventory(); stackIndex++)
		{
			ItemStack fertStack = getStackInSlot(stackIndex);
			if(fertStack == null || !fertilizers.containsKey(new Integer(fertStack.itemID)))
			{
				continue;
			}
			IFactoryFertilizer fertilizer = fertilizers.get(new Integer(fertStack.itemID));
			IFactoryFertilizable fertilizable = fertilizables.get(new Integer(targetId));
			
			if(fertilizer.getFertilizerMeta() != fertStack.getItemDamage())
			{
				continue;
			}
			
			if(!fertilizable.canFertilizeBlock(worldObj, bp.x, bp.y, bp.z, fertilizer.getFertilizerType()))
			{
				continue;
			}
			if(fertilizable.fertilize(worldObj, _rand, bp.x, bp.y, bp.z, fertilizer.getFertilizerType()))
			{
				fertilizer.consume(fertStack);
				if(fertStack.stackSize <= 0)
				{
					setInventorySlotContents(stackIndex, null);
				}
				return true;
			}
		}
			
		setIdleTicks(getIdleTicksMax());
		return false;
	}

	@Override
	public String getInvName()
	{
		return "Fertilizer";
	}
	
	@Override
	public int getSizeInventory()
	{
		return 10;
	}

	@Override
	public int getEnergyStoredMax()
	{
		return 32000;
	}

	@Override
	public int getWorkMax()
	{
		return 1;
	}

	@Override
	public int getIdleTicksMax()
	{
		return 20;
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
