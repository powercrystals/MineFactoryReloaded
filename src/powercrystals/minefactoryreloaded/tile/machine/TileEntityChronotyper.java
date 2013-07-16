package powercrystals.minefactoryreloaded.tile.machine;

import java.util.List;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.IHarvestAreaContainer;
import powercrystals.minefactoryreloaded.gui.client.GuiChronotyper;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerChronotyper;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityChronotyper extends TileEntityFactoryPowered implements IHarvestAreaContainer
{
	private HarvestAreaManager _areaManager;
	private boolean _moveOld;
	
	public TileEntityChronotyper()
	{
		super(Machine.Chronotyper);
		_areaManager = new HarvestAreaManager(this, 2, 2, 1);
	}
	
	@Override
	public String getGuiBackground()
	{
		return "chronotyper.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiChronotyper(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerChronotyper getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerChronotyper(this, inventoryPlayer);
	}
	
	@Override
	public int getSizeInventory()
	{
		return 0;
	}
	
	@Override
	public HarvestAreaManager getHAM()
	{
		return _areaManager;
	}
	
	@Override
	protected boolean activateMachine()
	{
		List<?> entities = worldObj.getEntitiesWithinAABB(EntityAgeable.class, _areaManager.getHarvestArea().toAxisAlignedBB());
		
		for(Object o : entities)
		{
			if(!(o instanceof EntityAgeable))
			{
				continue;
			}
			EntityAgeable a = (EntityAgeable)o;
			if((a.getGrowingAge() < 0 && !_moveOld) || (a.getGrowingAge() >= 0 && _moveOld))
			{
				BlockPosition bp = BlockPosition.fromFactoryTile(this);
				bp.moveBackwards(1);
				a.setPosition(bp.x + 0.5, bp.y + 0.5, bp.z + 0.5);
				
				return true;
			}
		}
		setIdleTicks(getIdleTicksMax());
		return false;
	}
	
	public boolean getMoveOld()
	{
		return _moveOld;
	}
	
	public void setMoveOld(boolean moveOld)
	{
		_moveOld = moveOld;
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
		return 200;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		
		nbttagcompound.setByte("moveOld", (byte)(_moveOld ? 1 : 0));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		_moveOld = nbttagcompound.getByte("moveOld") == 0 ? false : true;
	}
	
	@Override
	public boolean canRotate()
	{
		return true;
	}
}
