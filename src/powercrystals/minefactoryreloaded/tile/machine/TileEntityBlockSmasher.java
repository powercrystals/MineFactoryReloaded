package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import powercrystals.core.inventory.InventoryManager;
import powercrystals.minefactoryreloaded.core.ITankContainerBucketable;
import powercrystals.minefactoryreloaded.gui.client.GuiBlockSmasher;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerBlockSmasher;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityBlockSmasher extends TileEntityFactoryPowered implements ITankContainerBucketable
{
	private LiquidTank _tank;
	
	private int _fortune = 0;
	
	private ItemStack _lastInput;
	private ItemStack _lastOutput;
	
	public TileEntityBlockSmasher()
	{
		super(Machine.BlockSmasher);
		_tank = new LiquidTank(LiquidContainerRegistry.BUCKET_VOLUME * 4);
	}
	
	@Override
	public int getSizeInventory()
	{
		return 2;
	}
	
	@Override
	public String getInvName()
	{
		return "Block Smasher";
	}
	
	@Override
	public String getGuiBackground()
	{
		return "blocksmasher.png";
	}
	
	@Override
	public ContainerBlockSmasher getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerBlockSmasher(this, inventoryPlayer);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiBlockSmasher(getContainer(inventoryPlayer), this);
	}
	
	@Override
	protected boolean activateMachine()
	{
		if(_inventory[0] == null)
		{
			setWorkDone(0);
			return false;
		}
		if(_lastInput == null || !InventoryManager.stacksEqual(_lastInput, _inventory[0]))
		{
			_lastInput = _inventory[0];
			_lastOutput = getOutput(_lastInput);
		}
		if(_lastOutput == null)
		{
			setWorkDone(0);
			return false;
		}
		if(_fortune > 0 && (_tank.getLiquid() == null || _tank.getLiquid().amount < _fortune))
		{
			return false;
		}
		if(_inventory[1] != null && !InventoryManager.stacksEqual(_lastOutput, _inventory[1]))
		{
			setWorkDone(0);
			return false;
		}
		if(_inventory[1] != null && _inventory[1].getMaxStackSize() - _inventory[1].stackSize < _lastOutput.stackSize)
		{
			return false;
		}
		
		setWorkDone(getWorkDone() + 1);
		_tank.drain(_fortune, true);
		
		if(getWorkDone() >= getWorkMax())
		{
			setWorkDone(0);
			_lastInput = null;
			if(_inventory[1] == null)
			{
				_inventory[1] = _lastOutput.copy();
			}
			else
			{
				_inventory[1].stackSize += _lastOutput.stackSize;
			}
			
			_inventory[0].stackSize--;
			if(_inventory[0].stackSize == 0)
			{
				_inventory[0] = null;
			}
		}
		return true;
	}
	
	private ItemStack getOutput(ItemStack input)
	{
		if(!(input.getItem() instanceof ItemBlock))
		{
			return null;
		}
		Block b = Block.blocksList[input.itemID];
		if(b == null)
		{
			return null;
		}
		
		int id = b.idDropped(input.itemID, worldObj.rand, _fortune);
		int meta = b.damageDropped(input.getItemDamage());
		int quantity = b.quantityDropped(meta, _fortune, worldObj.rand);
		if(quantity > 0 && id > 0)
		{
			return new ItemStack(id, quantity, meta);
		}
		return null;
	}
	
	public int getFortune()
	{
		return _fortune;
	}
	
	public void setFortune(int fortune)
	{
		if(fortune >= 0 && fortune <= 3)
		{
			if(_fortune < fortune)
			{
				setWorkDone(0);
			}
			_fortune = fortune;
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
		return 60;
	}
	
	@Override
	public int getIdleTicksMax()
	{
		return 1;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int sideordinal)
	{
		if(slot == 0) return true;
		return false;
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int sideordinal)
	{
		if(slot == 1) return true;
		return false;
	}
	
	@Override
	public boolean allowBucketFill()
	{
		return true;
	}
	
	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
	{
		if(resource == null || (resource.itemID != LiquidDictionary.getCanonicalLiquid("mobEssence").itemID))
		{
			return 0;
		}
		
		return _tank.fill(resource, doFill);
	}
	
	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill)
	{
		return fill(ForgeDirection.UNKNOWN, resource, doFill);
	}
	
	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		return null;
	}
	
	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain)
	{
		return null;
	}
	
	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction)
	{
		return new ILiquidTank[] { _tank };
	}
	
	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type)
	{
		if(type != null && type.itemID == LiquidDictionary.getCanonicalLiquid("mobEssence").itemID)
		{
			return _tank;
		}
		return null;
	}
	
	@Override
	public ILiquidTank getTank()
	{
		return _tank;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("fortune", _fortune);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		_fortune = nbttagcompound.getInteger("fortune");
	}
}
