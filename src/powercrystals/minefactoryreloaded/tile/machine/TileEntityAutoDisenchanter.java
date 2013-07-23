package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.minefactoryreloaded.gui.client.GuiAutoDisenchanter;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerAutoDisenchanter;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityAutoDisenchanter extends TileEntityFactoryPowered
{
	private boolean _repeatDisenchant;
	
	public TileEntityAutoDisenchanter()
	{
		super(Machine.AutoDisenchanter);
	}
	
	@Override
	public String getGuiBackground()
	{
		return "autodisenchanter.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiAutoDisenchanter(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerAutoDisenchanter getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerAutoDisenchanter(this, inventoryPlayer);
	}
	
	public boolean getRepeatDisenchant()
	{
		return _repeatDisenchant;
	}
	
	public void setRepeatDisenchant(boolean repeatDisenchant)
	{
		_repeatDisenchant = repeatDisenchant;
	}
	
	@Override
	public int getSizeInventory()
	{
		return 4;
	}
	
	@Override
	public int getSizeInventorySide(ForgeDirection side)
	{
		return 4;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int sideordinal)
	{
		if(slot == 0)
		{
			return stack.getEnchantmentTagList() != null;
		}
		else if(slot == 1)
		{
			return stack.itemID == Item.book.itemID;
		}
		return false;
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int sideordinal)
	{
		if(slot == 2 || slot == 3) return true;
		return false;
	}
	
	@Override
	protected boolean activateMachine()
	{
		if(_inventory[0] != null && _inventory[0].getEnchantmentTagList() == null && _inventory[2] == null)
		{
			_inventory[2] = _inventory[0];
			_inventory[0] = null;
		}
		else if(_inventory[0] != null && _inventory[0].getEnchantmentTagList() != null && _inventory[1] != null &&
				_inventory[1].itemID == Item.book.itemID && _inventory[2] == null && _inventory[3] == null)
		{
			if(getWorkDone() >= getWorkMax())
			{
				decrStackSize(1, 1);
				
				NBTTagCompound enchTag;
				if(_inventory[0].itemID == Item.enchantedBook.itemID)
				{
					if((NBTTagList)_inventory[0].getTagCompound().getTag("ench") != null)
					{
						enchTag = (NBTTagCompound)((NBTTagList)_inventory[0].getTagCompound().getTag("ench")).tagAt(0);
						_inventory[0] = new ItemStack(Item.book);
					}
					else
					{
						enchTag = (NBTTagCompound)((NBTTagList)_inventory[0].getTagCompound().getTag("StoredEnchantments")).tagAt(0);
					}
				}
				else
				{
					int enchIndex = worldObj.rand.nextInt(((NBTTagList)_inventory[0].getTagCompound().getTag("ench")).tagCount());
					enchTag = (NBTTagCompound)((NBTTagList)_inventory[0].getTagCompound().getTag("ench")).tagAt(enchIndex);
					
					((NBTTagList)_inventory[0].getTagCompound().getTag("ench")).removeTag(enchIndex);
					if(((NBTTagList)_inventory[0].getTagCompound().getTag("ench")).tagCount() == 0)
					{
						_inventory[0].getTagCompound().removeTag("ench");
						if(_inventory[0].getTagCompound().hasNoTags())
						{
							_inventory[0].setTagCompound(null);
						}
					}
					
					
					if(_inventory[0].isItemStackDamageable())
					{
						int damage = worldObj.rand.nextInt((int)(_inventory[0].getMaxDamage() * 0.25)) + (int)(_inventory[0].getMaxDamage() * 0.1);
						_inventory[0].setItemDamage(_inventory[0].getItemDamage() + damage);
						if(_inventory[0].getItemDamage() >= _inventory[0].getMaxDamage())
						{
							_inventory[0] = null;
						}
					}
				}
				
				if(_inventory[0].getTagCompound() == null || _inventory[0].getTagCompound().getTag("ench") == null || !_repeatDisenchant)
				{
					_inventory[2] = _inventory[0];
					_inventory[0] = null;
				}
				
				_inventory[3] = new ItemStack(Item.enchantedBook, 1);
				
				NBTTagCompound baseTag = new NBTTagCompound();
				NBTTagList enchList = new NBTTagList();
				enchList.appendTag(enchTag);
				baseTag.setTag("StoredEnchantments", enchList);
				_inventory[3].setTagCompound(baseTag);
				
				setWorkDone(0);
			}
			else
			{
				setWorkDone(getWorkDone() + 1);
			}
			
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isStackValidForSlot(int slot, ItemStack itemstack)
	{
		if(slot == 0)
		{
			return itemstack.getEnchantmentTagList() != null;
		}
		else if(slot == 1)
		{
			return itemstack != null && itemstack.itemID == Item.book.itemID;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public int getEnergyStoredMax()
	{
		return 16000;
	}
	
	@Override
	public int getWorkMax()
	{
		return 600;
	}
	
	@Override
	public int getIdleTicksMax()
	{
		return 1;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setBoolean("repeatDisenchant", _repeatDisenchant);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		_repeatDisenchant = tag.getBoolean("repeatDisenchant");
	}
}
