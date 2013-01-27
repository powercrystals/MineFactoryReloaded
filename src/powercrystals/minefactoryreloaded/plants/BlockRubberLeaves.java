package powercrystals.minefactoryreloaded.plants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;

public class BlockRubberLeaves extends BlockLeaves
{
	public BlockRubberLeaves(int id)
	{
		super(id, 6);
		setHardness(0.2F);
		setLightOpacity(1);
		setStepSound(soundGrassFootstep);
		setBlockName("mfrRubberLeaves");
		setRequiresSelfNotify();
		setCreativeTab(MFRCreativeTab.tab);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
	}
	
	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		return Block.leaves.graphicsLevel ? 30 : 31;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return !Block.leaves.graphicsLevel;
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return MineFactoryReloadedCore.rubberSaplingBlock.blockID;
	}
	
	@Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		if (!par1World.isRemote)
		{
			ArrayList<ItemStack> items = getBlockDropped(par1World, par2, par3, par4, par5, par7);

			for (ItemStack item : items)
			{
				if (par1World.rand.nextFloat() <= par6)
				{
					this.dropBlockAsItem_do(par1World, par2, par3, par4, item);
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int side)
	{
		return Block.leaves.graphicsLevel ? true : super.shouldSideBeRendered(iba, x, y, z, side);
	}
}
