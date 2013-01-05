package powercrystals.minefactoryreloaded.rails;

import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class BlockRailCargoDropoff extends BlockRail
{
	public BlockRailCargoDropoff(int i, int j)
	{
		super(i, j, true);
		setBlockName("cargoDropoffRail");
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if(world.isRemote || !(entity instanceof EntityMinecart))
		{
			return;
		}
		EntityMinecart minecart = (EntityMinecart)entity;
		if(minecart.minecartType != 1)
		{
			return;
		}
		
		for(int slotIndex = 0; slotIndex < minecart.getSizeInventory(); slotIndex++)
		{
			ItemStack ourStack = minecart.getStackInSlot(slotIndex);
			if(ourStack == null)
			{
				continue;
			}
			for(Entry<ForgeDirection, IInventory> chest : UtilInventory.findChests(world, x, y, z).entrySet())
			{
				ItemStack stackToAdd = ourStack.copy();
				int amountRemaining = UtilInventory.addToInventory(chest.getValue(), chest.getKey(), stackToAdd);
				if(amountRemaining == 0)
				{
					minecart.setInventorySlotContents(slotIndex, null);
					break;
				}
				else
				{
					ourStack.stackSize = amountRemaining;
				}
			}
		}
	}

	@Override
	public String getTextureFile()
	{
        return MineFactoryReloadedCore.terrainTexture;
	}
}
