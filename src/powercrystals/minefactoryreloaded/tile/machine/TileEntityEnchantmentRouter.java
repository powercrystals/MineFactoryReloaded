package powercrystals.minefactoryreloaded.tile.machine;

import java.util.Map;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import powercrystals.minefactoryreloaded.gui.client.GuiEnchantmentRouter;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerEnchantmentRouter;
import powercrystals.minefactoryreloaded.setup.Machine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityEnchantmentRouter extends TileEntityItemRouter
{
	private boolean _matchLevels = false;
	
	public TileEntityEnchantmentRouter()
	{
		super(Machine.EnchantmentRouter);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	protected int[] getRoutesForItem(ItemStack stack)
	{
		int[] routeWeights = new int[_outputDirections.length];
		
		Map stackEnchants = EnchantmentHelper.getEnchantments(stack);
		// return false if the item is unenchanted 
		if(stackEnchants == null || stackEnchants.isEmpty())
		{
			for(int i = 0; i < routeWeights.length; i++)
			{
				routeWeights[i] = 0;
			}
			return routeWeights;
		}
		
		for(int i = 0; i < _outputDirections.length; i++)
		{
			int sideStart = _invOffsets[_outputDirections[i].ordinal()];
			routeWeights[i] = 0;
			
			for(int j = sideStart; j < sideStart + 9; j++)
			{
				if(_inventory[j] != null && _inventory[j].hasTagCompound())
				{
					Map inventoryEnchants = EnchantmentHelper.getEnchantments(_inventory[j]);
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
								routeWeights[i] += _inventory[j].stackSize;
							}
						}
					}
				}
			}
		}
		return routeWeights;
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
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		_matchLevels = nbttagcompound.getBoolean("matchLevels");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("matchLevels", _matchLevels);
	}
}
