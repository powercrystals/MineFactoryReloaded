package powercrystals.minefactoryreloaded.tile.machine;

import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import powercrystals.minefactoryreloaded.gui.client.GuiEnchantmentRouter;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerEnchantmentRouter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.InventoryPlayer;

public class TileEntityEnchantmentRouter extends TileEntityItemRouter
{
	private boolean _matchLevels = false;
	
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
						if(!_matchLevels || inventoryEnchants.get(stackEnchantId).equals(stackEnchants.get(stackEnchantId)))
						{
							return true;
						}
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
	
	public boolean getMatchLevels()
	{
		return _matchLevels;
	}
	
	@Override
	public String getGuiBackground()
	{
		return "enchantmentrouter.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiEnchantmentRouter(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerEnchantmentRouter getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerEnchantmentRouter(this, inventoryPlayer);
	}
	
	public void setMatchLevels(boolean newMatchLevelsSetting)
	{
		_matchLevels = newMatchLevelsSetting;
	}
}
