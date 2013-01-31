package powercrystals.minefactoryreloaded.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.processing.TileEntityLiquiCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;

public class ContainerLiquiCrafter extends Container
{
	private TileEntityLiquiCrafter _crafter;
	
	private int _tempTankIndex;
	private LiquidStack _tempLiquidStack;

	public ContainerLiquiCrafter(TileEntityLiquiCrafter crafter, InventoryPlayer inventoryPlayer)
	{
		super();
		_crafter = crafter;
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				addSlotToContainer(new Slot(_crafter, j + i * 3, 8 + j * 18, 20 + i * 18));
			}
		}

		addSlotToContainer(new SlotViewOnly(_crafter, 9, 80, 38));
		addSlotToContainer(new SlotRemoveOnly(_crafter, 10, 134, 38));
		
		for(int i = 0; i < 2; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(_crafter, 11 + j + i * 9, 8 + j * 18, 79 + i * 18));
			}
		}
		
		bindPlayerInventory(inventoryPlayer);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		int tankIndex = (int)(_crafter.worldObj.getWorldTime() % 9);
		ILiquidTank tank = _crafter.getTanks(ForgeDirection.UNKNOWN)[tankIndex];
		LiquidStack l = tank.getLiquid();
		
		for(int i = 0; i < crafters.size(); i++)
		{
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 0, tankIndex);
			if(l != null)
			{
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 1, l.itemID);
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 2, l.itemMeta);
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 3, l.amount);
			}
			else
			{
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 1, 0);
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 2, 0);
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 3, 0);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int var, int value)
	{
		super.updateProgressBar(var, value);
		if(var == 0) _tempTankIndex = value;
		else if(var == 1) _tempLiquidStack = new LiquidStack(value, 0);
		else if(var == 2) _tempLiquidStack.itemMeta = value;
		else if(var == 3)
		{
			_tempLiquidStack.amount = value;
			((LiquidTank)_crafter.getTanks(ForgeDirection.UNKNOWN)[_tempTankIndex]).setLiquid(_tempLiquidStack);
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return _crafter.isUseableByPlayer(entityplayer);
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
					addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 133 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 191));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		return null;
	}
}
