package powercrystals.minefactoryreloaded.decorative;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;

public class GlassPane extends BlockPane
{
	public GlassPane()
	{
		super(102, 49, 148, Material.glass, false);
		setHardness(0.3F);
		setStepSound(soundGlassFootstep);
		setBlockName("thinGlass");
	}
	
	@Override
    public boolean canThisPaneConnectToThisBlockID(int blockId)
    {
        return Block.opaqueCubeLookup[blockId] || blockId == this.blockID || blockId == Block.glass.blockID
        		|| blockId == MineFactoryReloadedCore.factoryGlassBlock.blockID || blockId == MineFactoryReloadedCore.factoryGlassPaneBlock.blockID;
    }
}
