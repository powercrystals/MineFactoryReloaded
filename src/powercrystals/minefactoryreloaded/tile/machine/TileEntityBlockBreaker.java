package powercrystals.minefactoryreloaded.tile.machine;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import powercrystals.core.position.BlockPosition;
import powercrystals.core.util.Util;
import powercrystals.minefactoryreloaded.setup.MFRConfig;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

public class TileEntityBlockBreaker extends TileEntityFactoryPowered
{
	public TileEntityBlockBreaker()
	{
		super(Machine.BlockBreaker);
	}
	
	@Override
	public boolean activateMachine()
	{		
		BlockPosition bp = BlockPosition.fromFactoryTile(this);
		bp.moveForwards(1);
		int blockId = worldObj.getBlockId(bp.x, bp.y, bp.z);
		int blockMeta = worldObj.getBlockMetadata(bp.x, bp.y, bp.z);
		
		Block b = Block.blocksList[blockId];
		if(b != null && !b.isAirBlock(worldObj, bp.x, bp.y, bp.z) && !Util.isBlockUnbreakable(worldObj, bp.x, bp.y, bp.z) && b.getBlockHardness(worldObj, bp.x, bp.y, bp.z) >= 0)
		{
			List<ItemStack> drops = b.getBlockDropped(worldObj, bp.x, bp.y, bp.z, blockMeta, 0);
			
			doDrop(drops);
			
			if(MFRConfig.playSounds.getBoolean(true))
			{
				worldObj.playAuxSFXAtEntity(null, 2001, bp.x, bp.y, bp.z, blockId + (blockMeta << 12));
			}
			worldObj.setBlockToAir(bp.x, bp.y, bp.z);
			return true;
		}
		setIdleTicks(getIdleTicksMax());
		return false;
	}
	
	@Override
	public int getEnergyStoredMax()
	{
		return 64000;
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
	public String getInvName()
	{
		return "Block Breaker";
	}
	
	@Override
	public int getSizeInventory()
	{
		return 0;
	}
	
	@Override
	public boolean manageSolids()
	{
		return true;
	}
	
	@Override
	public boolean canRotate()
	{
		return true;
	}
}
