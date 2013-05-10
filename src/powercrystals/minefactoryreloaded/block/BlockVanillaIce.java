package powercrystals.minefactoryreloaded.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class BlockVanillaIce extends BlockIce
{
	public BlockVanillaIce()
	{
		super(79);
		setHardness(0.5F);
		setLightOpacity(3);
		setStepSound(soundGlassFootstep);
		setUnlocalizedName("ice");
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if(world.getBlockMetadata(x, y, z) == 0)
		{
			super.updateTick(world, x, y, z, rand);
		}
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta)
	{
		player.addStat(StatList.mineBlockStatArray[this.blockID], 1);
		player.addExhaustion(0.025F);
		
		if(this.canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(player))
		{
			ItemStack droppedStack = this.createStackedBlock(meta);
			
			if(droppedStack != null)
			{
				this.dropBlockAsItem_do(world, x, y, z, droppedStack);
			}
		}
		else
		{
			if(world.provider.isHellWorld)
			{
				return;
			}
			
			int fortune = EnchantmentHelper.getFortuneModifier(player);
			this.dropBlockAsItem(world, x, y, z, meta, fortune);
			Material var8 = world.getBlockMaterial(x, y - 1, z);
			
			if((var8.blocksMovement() || var8.isLiquid()) && meta == 0)
			{
				world.setBlock(x, y, z, Block.waterMoving.blockID);
			}
		}
	}
}
