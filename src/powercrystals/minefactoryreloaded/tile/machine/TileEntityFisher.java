package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.position.Area;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryPowered;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityFisher extends TileEntityFactoryPowered
{
	public TileEntityFisher()
	{
		super(Machine.Fisher);
	}
	
	@Override
	public String getGuiBackground()
	{
		return "fisher.png";
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
	public boolean activateMachine()
	{
		BlockPosition fishCenter = BlockPosition.fromFactoryTile(this);
		fishCenter.moveDown(1);
		Area fishingHole = new Area(fishCenter, 1, 0, 0);
		for(BlockPosition bp: fishingHole.getPositionsBottomFirst())
		{
			if(worldObj.getBlockId(bp.x, bp.y, bp.z) != Block.waterStill.blockID)
			{
				setIdleTicks(getIdleTicksMax());
				return false;
			}
		}
		
		setWorkDone(getWorkDone() + 1);
		
		if(getWorkDone() > getWorkMax())
		{
			doDrop(new ItemStack(Item.fishRaw));
			setWorkDone(0);
		}
		return true;
	}
	
	@Override
	public ForgeDirection getDropDirection()
	{
		return ForgeDirection.UP;
	}
	
	@Override
	public int getEnergyStoredMax()
	{
		return 16000;
	}
	
	@Override
	public int getWorkMax()
	{
		return 900;
	}
	
	@Override
	public int getIdleTicksMax()
	{
		return 200;
	}
	
	@Override
	public String getInvName()
	{
		return "Fisher";
	}
	
	@Override
	public int getSizeInventory()
	{
		return 0;
	}
	
	@Override
	public boolean manageSolids()
	{
		return true;
	}
}
