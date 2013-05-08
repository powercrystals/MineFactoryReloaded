package powercrystals.minefactoryreloaded.tile.machine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryPowered;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

public class TileEntityLaserDrillPrecharger extends TileEntityFactoryPowered
{
	public TileEntityLaserDrillPrecharger()
	{
		super(Machine.LaserDrillPrecharger);
	}

	@Override
	public String getGuiBackground()
	{
		return "laserdrillprecharger.png";
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
	public boolean canRotate()
	{
		return true;
	}
	
	@Override
	public int getSizeInventory()
	{
		return 0;
	}

	@Override
	public String getInvName()
	{
		return "Laser Drill Precharger";
	}

	@Override
	protected boolean activateMachine()
	{
		BlockPosition bp = new BlockPosition(this);
		bp.orientation = getDirectionFacing();
		bp.moveForwards(1);
		
		if(!worldObj.isAirBlock(bp.x, bp.y, bp.z))
		{
			setIdleTicks(getIdleTicksMax());
			return false;
		}
		
		bp.moveForwards(1);
		
		TileEntity te = worldObj.getBlockTileEntity(bp.x, bp.y, bp.z);
		if(te instanceof TileEntityLaserDrill)
		{
			((TileEntityLaserDrill)te).addEnergy(_energyActivation);
			return true;
		}
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
		return 200;
	}
	
	public boolean shouldDrawBeam()
	{
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536;
	}
}
