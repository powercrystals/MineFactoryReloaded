package powercrystals.minefactoryreloaded.world;

import java.util.Random;

import net.minecraft.block.BlockSapling;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;

public class BlockRubberSapling extends BlockSapling
{
	public BlockRubberSapling(int id)
	{
		super(id, 3);
		setHardness(0.0F);
		setStepSound(soundGrassFootstep);
		setBlockName("sapling");
		setRequiresSelfNotify();
		setCreativeTab(MFRCreativeTab.tab);
	}
	
	@Override
	public void growTree(World world, int x, int y, int z, Random rand)
	{
		WorldGenRubberTree wg = new WorldGenRubberTree(true);
		
		world.setBlock(x, y, z, 0);
		
		if(!wg.growTree(world, rand, x, y, z))
		{
			world.setBlock(x, y, z, blockID);
		}
	}
	
	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return MineFactoryReloadedCore.rubberSaplingBlock.blockID;
	}
}
