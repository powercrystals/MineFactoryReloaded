package powercrystals.minefactoryreloaded.core;

import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.position.BlockPosition;

public final class BlockNBTManager
{
	private static HashMap<BlockPosition, NBTTagCompound> _blockNbtData = new HashMap<BlockPosition, NBTTagCompound>();
	
	public static void setForBlock(BlockPosition bp, NBTTagCompound tag)
	{
		BlockPosition newbp = bp.copy();
		newbp.orientation = ForgeDirection.UNKNOWN;
		_blockNbtData.put(bp, tag);
	}
	
	public static void setForBlock(TileEntity te)
	{
		if(te == null)
		{
			return;
		}
		NBTTagCompound tag = new NBTTagCompound();
		te.writeToNBT(tag);
		
		setForBlock(new BlockPosition(te), tag);
	}
	
	public static NBTTagCompound getForBlock(int x, int y, int z)
	{
		BlockPosition bp = new BlockPosition(x, y, z, ForgeDirection.UNKNOWN);
		NBTTagCompound tag = _blockNbtData.get(bp);
		_blockNbtData.remove(bp);
		return tag;
	}
}
