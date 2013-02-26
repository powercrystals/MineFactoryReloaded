package powercrystals.minefactoryreloaded.decorative;

import java.util.List;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFactoryGlassPane extends BlockPane
{
	public BlockFactoryGlassPane(int blockId, int whiteTexture, int whiteSideTexture)
	{
		super(blockId, whiteTexture, whiteSideTexture, Material.glass, false);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		setBlockName("factoryGlassPaneBlock");
		setHardness(0.3F);
		setStepSound(soundGlassFootstep);
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
        return Block.opaqueCubeLookup[blockId] || blockId == this.blockID || blockId == Block.glass.blockID || blockId == MineFactoryReloadedCore.factoryGlassPaneBlock.blockID ||
        		(blockId == Block.thinGlass.blockID && MineFactoryReloadedCore.vanillaOverrideGlassPane.getBoolean(true));
    }
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        float var5 = 0.4375F;
        float var6 = 0.5625F;
        float var7 = 0.4375F;
        float var8 = 0.5625F;
        boolean var9 = this.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x, y, z - 1));
        boolean var10 = this.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x, y, z + 1));
        boolean var11 = this.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x - 1, y, z));
        boolean var12 = this.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x + 1, y, z));

        if ((!var11 || !var12) && (var11 || var12 || var9 || var10))
        {
            if (var11 && !var12)
            {
                var5 = 0.0F;
            }
            else if (!var11 && var12)
            {
                var6 = 1.0F;
            }
        }
        else
        {
            var5 = 0.0F;
            var6 = 1.0F;
        }

        if ((!var9 || !var10) && (var11 || var12 || var9 || var10))
        {
            if (var9 && !var10)
            {
                var7 = 0.0F;
            }
            else if (!var9 && var10)
            {
                var8 = 1.0F;
            }
        }
        else
        {
            var7 = 0.0F;
            var8 = 1.0F;
        }

        this.setBlockBounds(var5, 0.0F, var7, var6, 1.0F, var8);
    }
    
    @SuppressWarnings("rawtypes")
	@Override
    public void addCollidingBlockToList(World world, int x, int y, int z, AxisAlignedBB aabb, List blockList, Entity e)
    {
        boolean var8 = this.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x, y, z - 1));
        boolean var9 = this.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x, y, z + 1));
        boolean var10 = this.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x - 1, y, z));
        boolean var11 = this.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x + 1, y, z));

        if ((!var10 || !var11) && (var10 || var11 || var8 || var9))
        {
            if (var10 && !var11)
            {
                this.setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
                super.addCollidingBlockToList(world, x, y, z, aabb, blockList, e);
            }
            else if (!var10 && var11)
            {
                this.setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
                super.addCollidingBlockToList(world, x, y, z, aabb, blockList, e);
            }
        }
        else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
            super.addCollidingBlockToList(world, x, y, z, aabb, blockList, e);
        }

        if ((!var8 || !var9) && (var10 || var11 || var8 || var9))
        {
            if (var8 && !var9)
            {
                this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
                super.addCollidingBlockToList(world, x, y, z, aabb, blockList, e);
            }
            else if (!var8 && var9)
            {
                this.setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
                super.addCollidingBlockToList(world, x, y, z, aabb, blockList, e);
            }
        }
        else
        {
            this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
            super.addCollidingBlockToList(world, x, y, z, aabb, blockList, e);
        }
    }

    @Override
    public int getRenderType()
    {
    	return MineFactoryReloadedCore.renderIdFactoryGlassPane;
    }
    
	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}
}
