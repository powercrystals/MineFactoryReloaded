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
import net.minecraftforge.common.ISidedInventory;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;

public class BlockRailCargoPickup extends BlockRail
{
	public BlockRailCargoPickup(int i, int j)
	{
		super(i, j, true);
		setBlockName("cargoPickupRail");
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
                setCreativeTab(MFRCreativeTab.tab);
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
		
		for(Entry<ForgeDirection, IInventory> chest : UtilInventory.findChests(world, x, y, z).entrySet())
		{
			IInventory inv = chest.getValue();
			int invStart = 0;
			int invEnd = inv.getSizeInventory();
			
			if(inv instanceof ISidedInventory)
			{
				invStart = ((ISidedInventory)inv).getStartInventorySide(chest.getKey().getOpposite());
				invEnd = invStart + ((ISidedInventory)inv).getSizeInventorySide(chest.getKey().getOpposite());
			}
			
			for(int slotIndex = invStart; slotIndex < invEnd; slotIndex++)
			{
				ItemStack sourceStack = inv.getStackInSlot(slotIndex);
				if(sourceStack == null)
				{
					continue;
				}
				ItemStack stackToAdd = sourceStack.copy();
				int amountRemaining = UtilInventory.addToInventory(minecart, ForgeDirection.UNKNOWN, stackToAdd);
				if(amountRemaining == 0)
				{
					inv.setInventorySlotContents(slotIndex, null);
				}
				else
				{
					sourceStack.stackSize = amountRemaining;
					break;
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
