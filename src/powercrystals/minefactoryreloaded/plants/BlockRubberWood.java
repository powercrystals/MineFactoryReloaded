package powercrystals.minefactoryreloaded.plants;

import java.util.ArrayList;

import net.minecraft.block.BlockLog;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;

public class BlockRubberWood extends BlockLog
{
	public BlockRubberWood(int id)
	{
		super(id);
		blockIndexInTexture = 28;
		setHardness(2.0F);
		setStepSound(soundWoodFootstep);
		setBlockName("mfRubberWood");
		setRequiresSelfNotify();
		setCreativeTab(MFRCreativeTab.tab);
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int par1, int par2)
	{
		int var3 = par2 & 12;
		return var3 == 0 && (par1 == 1 || par1 == 0) ? 29 : (var3 == 4 && (par1 == 5 || par1 == 4) ? 29 : (var3 == 8 && (par1 == 2 || par1 == 3) ? 29 : 28));
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		
		drops.add(new ItemStack(blockID, 1, 0));
		if((metadata & 3) > 0)
		{
			drops.add(new ItemStack(MineFactoryReloadedCore.rawRubberItem, 1));
		}
		
		return drops;
	}
	
	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}
}
