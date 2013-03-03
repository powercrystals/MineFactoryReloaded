package powercrystals.minefactoryreloaded.processing;

import buildcraft.core.IMachine;
import powercrystals.core.position.Area;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.ForgeDirection;

public class TileEntityFisher extends TileEntityFactoryPowered implements IMachine
{
	public TileEntityFisher()
	{
		super(20);
	}
	
	@Override
	public String getGuiBackground()
	{
		return "fisher.png";
	}
	
	@Override
	public boolean canRotate()
	{
		return false;
	}
	
	public boolean activateMachine()
	{
		BlockPosition fishCenter = BlockPosition.fromFactoryTile(this);
		fishCenter.moveDown(1);
		Area fishingHole = new Area(fishCenter, 1, 0, 0);
		for(BlockPosition bp: fishingHole.getPositionsBottomFirst())
		{
			if(worldObj.getBlockId(bp.x, bp.y, bp.z) != Block.waterStill.blockID)
			{
				setIdleTicks(getIdleTicksMax());
				return false;
			}
		}
		
		setWorkDone(getWorkDone() + 1);
		
		if(getWorkDone() > getWorkMax())
		{
			MFRUtil.dropStack(this, new ItemStack(Item.fishRaw));
			setWorkDone(0);
		}
		return true;
	}
	
	@Override
	public ForgeDirection getDropDirection()
	{
		return ForgeDirection.UP;
	}

	@Override
	public int getEnergyStoredMax()
	{
		return 16000;
	}

	@Override
	public int getWorkMax()
	{
		return 400;
	}

	@Override
	public int getIdleTicksMax()
	{
		return 200;
	}

	@Override
	public String getInvName()
	{
		return "Fisher";
	}
	
	@Override
	public int getSizeInventory()
	{
		return 0;
	}

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
