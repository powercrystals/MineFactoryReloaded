package powercrystals.minefactoryreloaded.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.rednet.IConnectableRedNet;
import powercrystals.minefactoryreloaded.api.rednet.RedNetConnectionType;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import powercrystals.minefactoryreloaded.setup.WorldGenRubberTree;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRubberSapling extends BlockSapling implements IConnectableRedNet
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
	public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName());
	}
	
	@Override
	public Icon getIcon(int side, int metadata)
	{
		return blockIcon;
	}
	
	@Override
	public void growTree(World world, int x, int y, int z, Random rand)
	{
		WorldGenRubberTree wg = new WorldGenRubberTree(true);
		
		world.setBlockToAir(x, y, z);
		
		if(!wg.growTree(world, rand, x, y, z))
		{
			world.setBlock(x, y, z, blockID, 0, 4);
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
	
	@Override
	public RedNetConnectionType getConnectionType(World world, int x, int y, int z, ForgeDirection side)
	{
		return RedNetConnectionType.None;
	}
	
	@Override
	public int[] getOutputValues(World world, int x, int y, int z, ForgeDirection side)
	{
		return null;
	}
	
	@Override
	public int getOutputValue(World world, int x, int y, int z, ForgeDirection side, int subnet)
	{
		return 0;
	}
	
	@Override
	public void onInputsChanged(World world, int x, int y, int z, ForgeDirection side, int[] inputValues)
	{
	}
	
	@Override
	public void onInputChanged(World world, int x, int y, int z, ForgeDirection side, int inputValue)
	{
	}
}
