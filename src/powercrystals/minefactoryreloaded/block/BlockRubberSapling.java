package powercrystals.minefactoryreloaded.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import powercrystals.minefactoryreloaded.setup.WorldGenRubberTree;

public class BlockRubberSapling extends BlockSapling
{
	public BlockRubberSapling(int id)
	{
		super(id);
		setHardness(0.0F);
		setStepSound(soundGrassFootstep);
		setUnlocalizedName("mfr.rubberwood.sapling");
		setCreativeTab(MFRCreativeTab.tab);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void func_94332_a(IconRegister par1IconRegister)
	{
        field_94336_cN = par1IconRegister.func_94245_a("powercrystals/minefactoryreloaded/" + getUnlocalizedName());
	}
	
	public Icon getBlockTextureFromSideAndMetadata(int side, int metadata)
	{
		return field_94336_cN;
	}
	
	@Override
	public void growTree(World world, int x, int y, int z, Random rand)
	{
		WorldGenRubberTree wg = new WorldGenRubberTree(true);
		
		world.func_94571_i(x, y, z);
		
		if(!wg.growTree(world, rand, x, y, z))
		{
			world.setBlockAndMetadataWithNotify(x, y, z, blockID, 0, 4);
		}
	}
	
	@Override
	public int idDropped(int meta, Random rand, int fortune)
	{
		return MineFactoryReloadedCore.rubberSaplingBlock.blockID;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int blockId, CreativeTabs tab, List subBlocks)
	{
		subBlocks.add(new ItemStack(blockId, 1, 0));
	}
}
