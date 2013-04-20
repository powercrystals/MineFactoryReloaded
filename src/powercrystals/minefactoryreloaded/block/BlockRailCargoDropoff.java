package powercrystals.minefactoryreloaded.block;

import java.util.Map.Entry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.inventory.IInventoryManager;
import powercrystals.core.inventory.InventoryManager;
import powercrystals.core.position.BlockPosition;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;

public class BlockRailCargoDropoff extends BlockRailBase
{
	public BlockRailCargoDropoff(int id)
	{
		super(id, true);
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
		
		IInventoryManager minecart = InventoryManager.create((EntityMinecartContainer)entity, ForgeDirection.UNKNOWN);
		
		for(Entry<Integer, ItemStack> contents : minecart.getContents().entrySet())
		{
			if(contents.getValue() == null)
			{
				continue;
			}
			
			ItemStack stackToAdd = contents.getValue().copy();
			ItemStack remaining = UtilInventory.dropStack(world, new BlockPosition(x, y, z), contents.getValue(), ForgeDirection.VALID_DIRECTIONS, ForgeDirection.UNKNOWN);
			
			if(remaining != null)
			{
				stackToAdd.stackSize -= remaining.stackSize;
			}
			
			minecart.removeItem(stackToAdd.stackSize, stackToAdd);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName());
	}
}
