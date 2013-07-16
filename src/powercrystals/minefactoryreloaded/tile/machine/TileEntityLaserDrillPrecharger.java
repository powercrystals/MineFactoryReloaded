package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryPowered;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
	protected boolean activateMachine()
	{
		TileEntityLaserDrill drill = getDrill();
		if(drill == null)
		{
			setIdleTicks(getIdleTicksMax());
		}
		else
		{
			if(drill.addEnergy(_energyActivation) == 0)
			{
				return true;
			}
			return false;
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
		return getDrill() != null;
	}
	
	private TileEntityLaserDrill getDrill()
	{
		BlockPosition bp = new BlockPosition(this);
		bp.orientation = getDirectionFacing();
		bp.moveForwards(1);
		
		if(!worldObj.isAirBlock(bp.x, bp.y, bp.z))
		{
			return null;
		}
		
		bp.moveForwards(1);
		
		TileEntity te = worldObj.getBlockTileEntity(bp.x, bp.y, bp.z);
		if(te instanceof TileEntityLaserDrill)
		{
			return ((TileEntityLaserDrill)te);
		}
		
		return null;
	}
	

	@Override
	public int getMaxSafeInput()
	{
		return 2048;
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
