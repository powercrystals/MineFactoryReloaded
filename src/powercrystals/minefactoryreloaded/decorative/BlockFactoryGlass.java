package powercrystals.minefactoryreloaded.decorative;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockFactoryGlass extends BlockGlass
{

	public BlockFactoryGlass(int blockId, int whiteTexture)
	{
		super(blockId, whiteTexture, Material.glass, false);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		setBlockName("factoryGlassBlock");
		setHardness(0.3F);
		setStepSound(soundGlassFootstep);
	}
	
    public int getRenderBlockPass()
    {
        return 1;
    }
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		return blockIndexInTexture + meta;
	}
	
	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}
}
