package powercrystals.minefactoryreloaded.decorative;

import powercrystals.minefactoryreloaded.MineFactoryReloadedClient;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockFactoryGlassPane extends BlockPane
{
	public BlockFactoryGlassPane(int blockId, int whiteTexture, int whiteSideTexture)
	{
		super(blockId, whiteTexture, whiteSideTexture, Material.glass, false);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		setBlockName("factoryGlassPaneBlock");
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		return blockIndexInTexture + meta;
	}
	
    public int getRenderBlockPass()
    {
        return 1;
    }
	
	public int getBlockSideTextureFromMetadata(int meta)
	{
		return getSideTextureIndex() + meta;
	}
	
    public boolean canThisFactoryPaneConnectToThisBlockID(int blockId)
    {
        return Block.opaqueCubeLookup[blockId] || blockId == this.blockID || blockId == Block.thinGlass.blockID || blockId == Block.glass.blockID;
    }

    @Override
    public int getRenderType()
    {
    	return MineFactoryReloadedClient.renderIdFactoryGlassPane;
    }
    
	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}
}
