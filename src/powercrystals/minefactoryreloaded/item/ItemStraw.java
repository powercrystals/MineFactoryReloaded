package powercrystals.minefactoryreloaded.item;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemStraw extends ItemFactory
{
	public ItemStraw(int id)
	{
		super(id);
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		if(!world.isRemote)
		{
			MovingObjectPosition mop = getMovingObjectPositionFromPlayer(world, player, true);
			if(mop != null && mop.typeOfHit == EnumMovingObjectType.TILE)
			{
				if(isValidLiquid(world.getBlockId(mop.blockX, mop.blockY, mop.blockZ)))
				{
					applyEffect(world.getBlockId(mop.blockX, mop.blockY, mop.blockZ), player);
					world.setBlockToAir(mop.blockX, mop.blockY, mop.blockZ);
				}
			}
		}
		
		return stack;
	}


	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 32;
	}


	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.drink;
	}


	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		MovingObjectPosition mop = getMovingObjectPositionFromPlayer(world, player, true);
		if(mop != null && mop.typeOfHit == EnumMovingObjectType.TILE)
		{
			if(isValidLiquid(world.getBlockId(mop.blockX, mop.blockY, mop.blockZ)))
			{
				player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
			}
		}
		return stack;
	}
	
	private boolean isValidLiquid(int blockId)
	{
		return  blockId == Block.waterMoving.blockID || blockId == Block.waterStill.blockID ||
				blockId == Block.lavaMoving.blockID || blockId == Block.lavaStill.blockID ||
				blockId == MineFactoryReloadedCore.milkFlowing.blockID || blockId == MineFactoryReloadedCore.milkStill.blockID ||
				blockId == MineFactoryReloadedCore.sewageFlowing.blockID || blockId == MineFactoryReloadedCore.sewageStill.blockID ||
				blockId == MineFactoryReloadedCore.sludgeFlowing.blockID || blockId == MineFactoryReloadedCore.sludgeStill.blockID ||
				blockId == MineFactoryReloadedCore.biofuelFlowing.blockID || blockId == MineFactoryReloadedCore.biofuelStill.blockID ||
				blockId == MineFactoryReloadedCore.essenceFlowing.blockID || blockId == MineFactoryReloadedCore.essenceStill.blockID;
	}
	
	private void applyEffect(int blockId, EntityPlayer player)
	{
		if(blockId == Block.lavaMoving.blockID || blockId == Block.lavaStill.blockID)
		{
			player.extinguish();
		}
		else if(blockId == Block.lavaMoving.blockID || blockId == Block.lavaStill.blockID)
		{
			player.setFire(30);
		}
		else if(blockId == MineFactoryReloadedCore.milkFlowing.blockID || blockId == MineFactoryReloadedCore.milkStill.blockID)
		{
			player.curePotionEffects(new ItemStack(Item.bucketMilk));
		}
		else if(blockId == MineFactoryReloadedCore.sewageFlowing.blockID || blockId == MineFactoryReloadedCore.sewageStill.blockID)
		{
			player.addPotionEffect(new PotionEffect(Potion.confusion.id, 40 * 20, 0));
			player.addPotionEffect(new PotionEffect(Potion.poison.id, 40 * 20, 0));
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 40 * 20, 0));
		}
		else if(blockId == MineFactoryReloadedCore.sludgeFlowing.blockID || blockId == MineFactoryReloadedCore.sludgeStill.blockID)
		{
			player.addPotionEffect(new PotionEffect(Potion.poison.id, 40 * 20, 0));
			player.addPotionEffect(new PotionEffect(Potion.blindness.id, 40 * 20, 0));
			player.addPotionEffect(new PotionEffect(Potion.confusion.id, 40 * 20, 0));
		}
		else if(blockId == MineFactoryReloadedCore.essenceFlowing.blockID || blockId == MineFactoryReloadedCore.essenceStill.blockID)
		{
			player.addExperience(10);
		}
		else if(blockId == MineFactoryReloadedCore.biofuelFlowing.blockID || blockId == MineFactoryReloadedCore.biofuelStill.blockID)
		{
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 40 * 20, 0));
			player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 40 * 20, 0));
		}
	}
}
