package powercrystals.minefactoryreloaded.block;

import java.util.Map.Entry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;

public class BlockRailCargoPickup extends BlockRailBase
{
	public BlockRailCargoPickup(int id)
	{
		super(id, false);
		setUnlocalizedName("mfr.rail.cargo.pickup");
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
		setCreativeTab(MFRCreativeTab.tab);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if(world.isRemote || !(entity instanceof EntityMinecartContainer))
		{
			return;
		}
		EntityMinecartContainer minecart = (EntityMinecartContainer)entity;
		
		for(Entry<ForgeDirection, IInventory> chest : UtilInventory.findChests(world, x, y, z).entrySet())
		{
			IInventory inv = chest.getValue();
			int invStart = 0;
			int invEnd = inv.getSizeInventory();
			
			if(inv instanceof ISidedInventory)
			{
				invStart = ((ISidedInventory)inv).func_94127_c(chest.getKey().getOpposite().ordinal());
				invEnd = invStart + ((ISidedInventory)inv).func_94128_d(chest.getKey().getOpposite().ordinal());
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
	@SideOnly(Side.CLIENT)
	public void func_94332_a(IconRegister par1IconRegister)
	{
		field_94336_cN = par1IconRegister.func_94245_a("powercrystals/minefactoryreloaded/" + getUnlocalizedName());
	}
}
