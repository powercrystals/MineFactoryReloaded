package powercrystals.minefactoryreloaded.tile.machine;

import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraft.enchantment.EnchantmentHelper;

public class TileEntityEnchantmentRouter extends TileEntityItemRouter
{
	public TileEntityEnchantmentRouter()
	{
		super();
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected boolean isSideValidForItem(ItemStack stack, ForgeDirection side)
	{
		if(side == ForgeDirection.UNKNOWN || side == ForgeDirection.UP)
		{
			return false;
		}
		
		Map stackEnchants = EnchantmentHelper.getEnchantments(stack);
		// return false if the item is unenchanted 
		if(stackEnchants == null || stackEnchants.isEmpty())
		{
			return false;
		}
		
		int sideStart = _invOffsets[side.ordinal()];
		
		for(int i = sideStart; i < sideStart + 9; i++)
		{
			if(_inventory[i] != null && _inventory[i].hasTagCompound())
			{
				Map inventoryEnchants = EnchantmentHelper.getEnchantments(_inventory[i]);
				if(inventoryEnchants.isEmpty())
				{
					continue;
				}
				for(Object stackEnchantId : stackEnchants.keySet())
				{
					if(inventoryEnchants.containsKey(stackEnchantId))
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	@Override
	public String getInvName()
	{
		return "Enchantment Router";
	}
}
