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
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;

public class BlockRailCargoDropoff extends BlockRailBase
{
	public BlockRailCargoDropoff(int id)
	{
		super(id, false);
		setUnlocalizedName("mfr.rail.cargo.dropoff");
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
	@SideOnly(Side.CLIENT)
	public void func_94332_a(IconRegister par1IconRegister)
	{
		field_94336_cN = par1IconRegister.func_94245_a("powercrystals/minefactoryreloaded/" + getUnlocalizedName());
	}
}
