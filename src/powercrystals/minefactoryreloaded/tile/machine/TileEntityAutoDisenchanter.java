package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryPowered;
import powercrystals.minefactoryreloaded.gui.container.ContainerAutoDisenchanter;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityAutoDisenchanter extends TileEntityFactoryPowered
{
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
		return new GuiFactoryPowered(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerAutoDisenchanter getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerAutoDisenchanter(this, inventoryPlayer);
	}
	
	@Override
	public int getSizeInventory()
	{
		return 4;
	}
	
	@Override
	public int getStartInventorySide(ForgeDirection side)
	{
		if(side == ForgeDirection.UP || side == ForgeDirection.DOWN)
		{
			return 0;
		}
		else
		{
			return 2;
		}
	}
	
	@Override
	public int getSizeInventorySide(ForgeDirection side)
	{
		return 2;
	}
	
	@Override
	public String getInvName()
	{
		return "Auto-Disenchanter";
	}
	
	@Override
	protected boolean activateMachine()
	{
		if(_inventory[0] != null && _inventory[0].getEnchantmentTagList() == null && _inventory[2] == null)
		{
			_inventory[2] = _inventory[0];
			_inventory[0] = null;
		}
		else if(_inventory[0] != null && _inventory[0].getEnchantmentTagList() != null &&  _inventory[0].itemID != Item.enchantedBook.itemID && _inventory[1] != null &&
				_inventory[1].itemID == Item.book.itemID && _inventory[2] == null && _inventory[3] == null)
		{
			if(getWorkDone() >= getWorkMax())
			{
				_inventory[2] = _inventory[0];
				_inventory[0] = null;
				_inventory[3] = new ItemStack(Item.enchantedBook, 1);
				decrStackSize(1, 1);
				
				int enchIndex = worldObj.rand.nextInt(((NBTTagList)_inventory[2].getTagCompound().getTag("ench")).tagCount());
				NBTTagCompound enchTag = (NBTTagCompound)((NBTTagList)_inventory[2].getTagCompound().getTag("ench")).tagAt(enchIndex);
				
				NBTTagCompound baseTag = new NBTTagCompound();
				NBTTagList enchList = new NBTTagList();
				enchList.appendTag(enchTag);
				baseTag.setTag("StoredEnchantments", enchList);
				_inventory[3].setTagCompound(baseTag);
				
				((NBTTagList)_inventory[2].getTagCompound().getTag("ench")).removeTag(enchIndex);
				if(((NBTTagList)_inventory[2].getTagCompound().getTag("ench")).tagCount() == 0)
				{
					_inventory[2].getTagCompound().removeTag("ench");
					if(_inventory[2].getTagCompound().hasNoTags())
					{
						_inventory[2].setTagCompound(null);
					}
				}
				
				int damage = worldObj.rand.nextInt((int)(_inventory[2].getMaxDamage() * 0.25)) + (int)(_inventory[2].getMaxDamage() * 0.1);
				if(_inventory[2].isItemStackDamageable())
				{
					_inventory[2].setItemDamage(_inventory[2].getItemDamage() + damage);
					if(_inventory[2].getItemDamage() >= _inventory[2].getMaxDamage())
					{
						_inventory[2] = null;
					}
				}
				
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
	
}
