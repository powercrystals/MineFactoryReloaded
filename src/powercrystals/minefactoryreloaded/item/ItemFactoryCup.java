package powercrystals.minefactoryreloaded.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidDictionary.LiquidRegisterEvent;
import net.minecraftforge.liquids.LiquidStack;

import powercrystals.minefactoryreloaded.MFRRegistry;

public class ItemFactoryCup extends ItemFactory
{

	private ItemFactoryCup _full, _empty;
	private int _maxUses = 0;
	
	public static abstract class LiquidManager
	{
		private static class Tuple
		{
			public Integer id;
			public Integer meta;
			public Tuple(Integer first, Integer second)
			{
				this.id = first;
				this.meta = second;
			}
			public int hashCode()
			{
				return (id.hashCode() << 16) ^ meta.hashCode();
			}
		}
		public static HashMap<String, Tuple> liquids = new HashMap<String, Tuple>();
		public static ArrayList<String> liquidIDs = new ArrayList<String>();
		private static ArrayList<ItemFactoryCup> containers = new ArrayList<ItemFactoryCup>();
		public static void registerAsContainers(ItemFactoryCup item) 
		{
			if (containers.contains(item._empty) || containers.contains(item._full))
				return;
			containers.add(item);
			for (int i = 1, e = liquidIDs.size(); i < e; ++i)
				LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getCanonicalLiquid(liquidIDs.get(i)), new ItemStack(item._full, 1, i), new ItemStack(item._empty, 1, 0)));
		}
		@ForgeSubscribe
		public static void registerLiquid(LiquidRegisterEvent evt)
		{
			String name = evt.Name;
			if (name == null)
				return;
			liquids.put(name, new Tuple(evt.Liquid.itemID, evt.Liquid.itemMeta));
			liquidIDs.add(name);
			for (ItemFactoryCup item : containers)
				LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getCanonicalLiquid(name), new ItemStack(item._full, 1, liquidIDs.size() - 1), new ItemStack(item._empty, 1, 0)));
		}
		static
		{
			liquidIDs.add(null);
			liquids.put(null, new Tuple(-1, -1));
			for (Map.Entry<String, LiquidStack> e : LiquidDictionary.getLiquids().entrySet())
			{
				liquids.put(e.getKey(), new Tuple(e.getValue().itemID, e.getValue().itemMeta));
				liquidIDs.add(e.getKey());
			}
			MinecraftForge.EVENT_BUS.register(LiquidManager.class);
		}
	}

	public ItemFactoryCup(int id, int stackSize, int maxUses)
	{
		super(id);
		this.setMaxStackSize(stackSize);
		this._maxUses = maxUses;
		this.setHasSubtypes(true);
	}

	public ItemFactory setContainers(ItemFactoryCup full, ItemFactoryCup empty)
	{
		this._full = full;
		this._empty = empty;
		full.setContainerItem(empty);
		LiquidManager.registerAsContainers(this);
		return this;
	}
	
	@Override
	public ItemStack getContainerItemStack(ItemStack stack)
	{
		ItemFactoryCup item = (ItemFactoryCup)stack.getItem();
		int damage = stack.getItemDamageForDisplay() + 1;
		if (item == null || !item._full.hasContainerItem() || damage >= item._empty._maxUses)
			return null;
		stack = new ItemStack(item._empty, 1, 0);
		stack.setItemDamage(damage);
		return stack;
	}

	public boolean hasDrinkableLiquid(ItemStack stack)
	{
		return MFRRegistry.getLiquidDrinkHandlers().containsKey(getLiquidID(stack));
	}

	public int getLiquidID(ItemStack stack)
	{
		return LiquidManager.liquids.get(LiquidManager.liquidIDs.get(stack.getItemDamage())).id;
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		ItemFactoryCup item = (ItemFactoryCup)stack.getItem();
		if (item == null)
			return null; // sanity check

		if (hasDrinkableLiquid(stack))
			MFRRegistry.getLiquidDrinkHandlers().get(getLiquidID(stack)).onDrink(player);

		if(!player.capabilities.isCreativeMode)
		{
			ItemStack drop = item.getContainerItemStack(stack);
			if (drop != null)
			{
				if (stack.stackSize-- > 1)
				{
					if (!player.inventory.addItemStackToInventory(drop))
						player.dropPlayerItem(drop);
				}
				else if (stack.stackSize == 0)
					return drop;
			}
			else
			{
				--stack.stackSize;
				player.renderBrokenItemStack(stack);
				player.addStat(StatList.objectBreakStats[item.itemID], 1);
			}
		}

		if (stack.stackSize <= 0)
			stack.stackSize = 0;
		return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return hasDrinkableLiquid(stack) ? EnumAction.drink : EnumAction.none;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (hasDrinkableLiquid(stack))
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		return stack;
	}

	@Override
    public int getItemDamageFromStackForDisplay(ItemStack stack)
    {
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null)
			return 0;
		return tag.getInteger("usedCount");
    }
	
    public boolean isItemStackDamaged(ItemStack stack)
    {
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null)
			return false;
        return tag.getInteger("usedCount") > 0;
    }

    public void setItemDamageForStack(ItemStack stack, int damage)
    {
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null)
			stack.setTagCompound((tag = new NBTTagCompound()));

        if (damage < 0)
            damage = 0;
            
        tag.setInteger("usedCount", damage);
    }
}
