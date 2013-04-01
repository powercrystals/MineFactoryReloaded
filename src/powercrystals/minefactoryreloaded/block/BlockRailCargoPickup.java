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
import powercrystals.core.inventory.IInventoryManager;
import powercrystals.core.inventory.InventoryManager;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;

public class BlockRailCargoPickup extends BlockRailBase
{
	public BlockRailCargoPickup(int id)
	{
		super(id, true);
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
		
		IInventoryManager minecart = InventoryManager.create((EntityMinecartContainer)entity, ForgeDirection.UNKNOWN);
		
		for(Entry<ForgeDirection, IInventory> inventory : UtilInventory.findChests(world, x, y, z).entrySet())
		{
			IInventoryManager chest = InventoryManager.create(inventory.getValue(), inventory.getKey().getOpposite()); 
			for(Entry<Integer, ItemStack> contents : chest.getContents().entrySet())
			{
				if(contents.getValue() == null)
				{
					continue;
				}
				ItemStack stackToAdd = contents.getValue().copy();
				
				ItemStack remaining = minecart.addItem(stackToAdd);
				
				if(remaining != null)
				{
					stackToAdd.stackSize -= remaining.stackSize;
					chest.removeItem(stackToAdd.stackSize, stackToAdd);
				}
				else
				{
					chest.removeItem(stackToAdd.stackSize, stackToAdd);
					break;
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName());
	}
}
