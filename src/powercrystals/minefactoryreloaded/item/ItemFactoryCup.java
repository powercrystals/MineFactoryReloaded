package powercrystals.minefactoryreloaded.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;

import powercrystals.core.asm.relauncher.Implementable;
import powercrystals.minefactoryreloaded.MFRRegistry;

@Implementable("net.minecraftforge.fluids.IFluidContainerItem")
public class ItemFactoryCup extends ItemFactory
{
	private int _maxUses = 0;
	private boolean _prefix = false;

	public ItemFactoryCup(int id, int stackSize, int maxUses)
	{
		super(id);
		this.setMaxStackSize(stackSize);
		this._maxUses = maxUses;
		this.setMaxDamage(maxUses);
		this.setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		if (getFluid(stack) != null)
			return getUnlocalizedName() + (_prefix ? ".prefix" : ".suffix");
		return getUnlocalizedName();
	}

	public String getLocalizedName(String str)
	{
		return StatCollector.translateToLocal(getUnlocalizedName() + "." + str);
	}

	@Override
	public String getItemDisplayName(ItemStack item)
	{
		int id = item.getItemDamage();
		if (id != 0)
		{
			String ret = getFluidName(item), t = getLocalizedName(ret);
			if (t != null)
				return "\u00a7r"+t+"\u00a7r";
			if (ret == null)
			{
				item.setItemDamage(0);
				return super.getItemDisplayName(item);
			}
			LiquidStack liquid = LiquidDictionary.getLiquid(ret, 0);
			if (liquid != null)
			{
				ItemStack q = liquid.asItemStack();
				Item temp = Item.itemsList[q.itemID];
				if (temp != null) ret = temp.getItemDisplayName(q);
			}
			_prefix = true;
			t = super.getItemDisplayName(item);
			_prefix = false;
			t = t != null ? t.trim() : "";
			ret = (t.isEmpty() ? "" : t + " ") + ret;
			t = super.getItemDisplayName(item);
			t = t != null ? t.trim() : "";
			ret += t.isEmpty() ? " Cup" : " " + t;
			return ret;
		}
		return super.getItemDisplayName(item);
	}

	@Override
	public ItemStack getContainerItemStack(ItemStack stack)
	{
		ItemFactoryCup item = (ItemFactoryCup)stack.getItem();
		int damage = stack.getItemDamage() + 1;
		if (item == null || damage >= item._maxUses)
			return null;
		stack = new ItemStack(item, 1, 0);
		stack.setItemDamage(damage);
		return stack;
	}

	public String getFluidName(ItemStack stack)
	{
		NBTTagCompound tag = stack.stackTagCompound;
		return tag == null || !tag.hasKey("fluid") ? null : tag.getCompoundTag("fluid").getString("FluidName");
	}

	// shim
	public LiquidStack getFluid(ItemStack stack)
	{
		return null;
	}

	/*{TODO: migrate to FluidStack/IFluidContainerItem in 1.6
	@Override
	public FluidStack getFluid(ItemStack stack)
	{
		NBTTagCompound tag = stack.stackTagCompound;
		FluidStack fluid = null;
		if (tag != null && tag.hasKey("fluid"))
		{
			fluid = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("fluid"));
			if (fluid == null)
				tag.removeTag("fluid");
		}
		return fluid;
	}

	@Override
	public int getCapacity(ItemStack container)
	{
		return net.minecraftforge.fluids.FluidContainerRegistry.BUCKET_VOLUME;
	}

	@Override
	public int fill(ItemStack stack, FluidStack resource, boolean doFill)
	{
		if (resource == null)
			return 0;
		int fillAmount = 0, capacity = getCapacity(stack);
		NBTTagCompound tag = stack.stackTagCompound, fluidTag = null;
		FluidStack fluid = null;
		if (tag == null || !tag.hasKey("fluid") ||
			(fluidTag = tag.getCompoundTag("fluid")) == null ||
			(fluid = FluidStack.loadFluidStackFromNBT(fluidTag)) == null)
			fillAmount = Math.min(capacity, resource.amount);
		if (fluid == null)
			if (doFill)
				fluid = resource.copy();
		else if (!fluid.isFluidEqual(resource))
			return 0;
		else
			fillAmount = Math.min(capacity - fluid.amount, resource.amount);
		fillAmount = Math.max(fillAmount, 0);
		if (doFill)
		{
			if (tag == null)
				tag = stack.stackTagCompound = new NBTTagCompound();
			fluid.amount = fillAmount;
			tag.setTag("fluid", fluid.writeToNBT(fluidTag == null ? new NBTTagCompound() : fluidTag));
		}
		return fillAmount;
	}

	@Override
	public FluidStack drain(ItemStack stack, int maxDrain, boolean doDrain)
	{
		NBTTagCompound tag = stack.stackTagCompound, fluidTag = null;
		FluidStack fluid = null;
		if (tag == null || !tag.hasKey("fluid") ||
			(fluidTag = tag.getCompoundTag("fluid")) == null ||
			(fluid = FluidStack.loadFluidStackFromNBT(fluidTag)) == null)
			return null;
		int drainAmount = Math.min(maxDrain, fluid.amount) * (Math.max(Math.random() - 0.25, 0) + 0.25);
		if (doDrain)
		{
			if (tag.hasKey('toDrain'))
			{
				drainAmount = tag.getInteger('toDrain');
				tag.removeTag('toDrain');
			}
			tag.removeTag('fluid');
		}
		else
			tag.setInteger('toDrain', drainAmount);
		fluid.amount = drainAmount;
		return fluid;
	}
	//}*/

	public boolean hasDrinkableLiquid(ItemStack stack)
	{
		return MFRRegistry.getLiquidDrinkHandlers().containsKey(getFluidName(stack));
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		ItemFactoryCup item = (ItemFactoryCup)stack.getItem();
		if (item == null)
			return null; // sanity check

		if (hasDrinkableLiquid(stack))
			MFRRegistry.getLiquidDrinkHandlers().get(getFluidName(stack)).onDrink(player);

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
}
