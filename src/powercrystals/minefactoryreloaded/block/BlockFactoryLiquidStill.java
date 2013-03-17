package powercrystals.minefactoryreloaded.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockStationary;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFactoryLiquidStill extends BlockStationary
{
	private int _flowingId;

	private Icon _iconFlowing;
	private Icon _iconStill;
	
	public BlockFactoryLiquidStill(int flowingId, int stillId, String liquidName)
	{
		super(stillId, Material.water);
		setUnlocalizedName("mfr.liquid." + liquidName + ".still");
		_flowingId = flowingId;
	}
	
	@SideOnly(Side.CLIENT)
	public void func_94332_a(IconRegister ir)
	{
		_iconStill = ir.func_94245_a("powercrystals/minefactoryreloaded/" + getUnlocalizedName());
		_iconFlowing = ir.func_94245_a("powercrystals/minefactoryreloaded/" + getUnlocalizedName().replace(".still", ".flowing"));
	}
	
	public Icon getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		return side > 1 ? _iconStill : _iconFlowing;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z)
	{
		return 16777215;
	}
	
	@Override
	public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId)
	{
		super.onNeighborBlockChange(world, x, y, z, blockId);

		if(world.getBlockId(x, y, z) == this.blockID)
		{
			this.setNotStationary(world, x, y, z);
		}
	}

	private void setNotStationary(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		world.setBlockAndMetadataWithNotify(x, y, z, _flowingId, meta, 2);
		world.scheduleBlockUpdate(x, y, z, _flowingId, this.tickRate(world));
	}
}
