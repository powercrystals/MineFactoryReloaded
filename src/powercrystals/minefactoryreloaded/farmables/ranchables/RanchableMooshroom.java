package powercrystals.minefactoryreloaded.farmables.ranchables;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;

import powercrystals.core.inventory.IInventoryManager;
import powercrystals.core.inventory.InventoryManager;
import powercrystals.minefactoryreloaded.api.IFactoryRanchable;

public class RanchableMooshroom implements IFactoryRanchable {

	@Override
	public Class<?> getRanchableEntity()
	{
		return EntityMooshroom.class;
	}

	@Override
	public List<ItemStack> ranch(World world, EntityLiving entity, IInventory rancher)
	{
		List<ItemStack> drops = new LinkedList<ItemStack>();

		IInventoryManager manager = InventoryManager.create(rancher, ForgeDirection.UP);
		int bowlIndex = manager.findItem(new ItemStack(Item.bowlEmpty));
		if(bowlIndex >= 0)
		{
			drops.add(new ItemStack(Item.bowlSoup));
			rancher.decrStackSize(bowlIndex, 1);
		}

		int bucketIndex = manager.findItem(new ItemStack(Item.bucketEmpty));
		if(bucketIndex >= 0)
		{
			drops.add(new ItemStack(Item.bucketMilk));
			rancher.setInventorySlotContents(bucketIndex, null);
		}
		else
		{
			LiquidStack milk = LiquidDictionary.getLiquid("milk", 1000);
			drops.add(new ItemStack(milk.itemID, 1, milk.itemMeta));
		}
		
		return drops;
	}
}
