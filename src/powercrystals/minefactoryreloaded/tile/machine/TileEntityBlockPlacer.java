package powercrystals.minefactoryreloaded.tile.machine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryPowered;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

public class TileEntityBlockPlacer extends TileEntityFactoryPowered
{
	public TileEntityBlockPlacer()
	{
		super(Machine.BlockPlacer);
	}
	
	@Override
	public String getGuiBackground()
	{
		return "blockplacer.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiFactoryPowered(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerFactoryPowered getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerFactoryPowered(this, inventoryPlayer);
	}

	@Override
	public int getSizeInventory()
	{
		return 9;
	}
	
	@Override
	protected boolean activateMachine()
	{
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(_inventory[i] == null || !(_inventory[i].getItem() instanceof ItemBlock))
			{
				continue;
			}
			
			BlockPosition bp = BlockPosition.fromFactoryTile(this);
			bp.moveForwards(1);
			if(worldObj.isAirBlock(bp.x, bp.y, bp.z))
			{
				worldObj.setBlock(bp.x, bp.y, bp.z, _inventory[i].itemID, _inventory[i].getItemDamage(), 3);
				Block block = Block.blocksList[_inventory[i].itemID];
				worldObj.playSoundEffect(bp.x + 0.5, bp.y + 0.5, bp.z + 0.5,
						block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
				decrStackSize(i, 1);
				return true;
			}
		}
		setIdleTicks(getIdleTicksMax());
		return false;
	}
	
	@Override
	public int getEnergyStoredMax()
	{
		return 16000;
	}
	
	@Override
	public int getWorkMax()
	{
		return 1;
	}
	
	@Override
	public int getIdleTicksMax()
	{
		return 20;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side)
	{
		return itemstack != null && itemstack.getItem() instanceof ItemBlock;
	}
	
	@Override
	public boolean canRotate()
	{
		return true;
	}
}
