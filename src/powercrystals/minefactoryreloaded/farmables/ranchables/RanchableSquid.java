package powercrystals.minefactoryreloaded.farmables.ranchables;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IFactoryRanchable;

public class RanchableSquid implements IFactoryRanchable
{
	@Override
	public Class<?> getRanchableEntity()
	{
		return EntitySquid.class;
	}

	@Override
	public List<ItemStack> ranch(World world, EntityLiving entity, IInventory rancher)
	{
		List<ItemStack> drops = new ArrayList<ItemStack>();
		drops.add(new ItemStack(Item.dyePowder, 1, 0));
		return drops;
	}

}
