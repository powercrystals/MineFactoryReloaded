package powercrystals.minefactoryreloaded.item;

import powercrystals.core.position.BlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemNeedlegunAmmoBlock extends ItemNeedlegunAmmo
{
	private int _blockId;
	private int _blockMeta;
	
	public ItemNeedlegunAmmoBlock(int id, int blockId, int blockMeta)
	{
		super(id);
		setMaxDamage(3);
		setHasSubtypes(false);
		_blockId = blockId;
		_blockMeta = blockMeta;
	}
	
	@Override
	public void onHitBlock(EntityPlayer owner, World world, int x, int y, int z, int side, double distance)
	{
		BlockPosition bp = new BlockPosition(x, y, z, ForgeDirection.getOrientation(side));
		bp.moveForwards(1);
		placeBlockAt(world, bp.x, bp.y, bp.z);
	}
	
	@Override
	public boolean onHitEntity(EntityPlayer owner, Entity hit, double distance)
	{
		placeBlockAt(hit.worldObj, (int)hit.posX, (int)hit.posY, (int)hit.posZ);
		return true;
	}
	
	@Override
	public float getSpread()
	{
		return 0.5F;
	}
	
	protected void placeBlockAt(World world, int x, int y, int z)
	{
		if(!world.isRemote && world.isAirBlock(x, y, z))
		{
			world.setBlock(x, y, z, _blockId, _blockMeta, 3);
		}
	}
}
