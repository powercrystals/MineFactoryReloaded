package powercrystals.minefactoryreloaded.decorative;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import powercrystals.core.util.Util;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFactoryRoad extends Block
{
	public BlockFactoryRoad(int id)
	{
		super(id, Material.rock);
		blockIndexInTexture = 12;
		slipperiness = 0.98F;
		setHardness(2.0F);
		setBlockName("factoryRoadBlock");
		setResistance(25.0F);
		setStepSound(soundStoneFootstep);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		if(meta == 0) return 12;
		if(meta == 1 || meta == 3) return 13;
		if(meta == 2 || meta == 4) return 14;
		return 12;
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborId)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			boolean isPowered = Util.isRedstonePowered(world, x, y, z);
			if((meta == 1 || meta == 3) && isPowered)
			{
				world.setBlockMetadataWithNotify(x, y, z, meta + 1);
			}
			else if((meta == 2 || meta == 4) && !isPowered)
			{
				world.setBlockMetadataWithNotify(x, y, z, meta - 1);
			}
		}
	}
	
	@Override
	public int damageDropped(int meta)
	{
		if(meta == 1 || meta == 2) return 1;
		if(meta == 3 || meta == 4) return 4;
		return 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public float getBlockBrightness(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		return meta == 2 || meta == 4 ? 1 : 0;
	}
	
	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}
}
