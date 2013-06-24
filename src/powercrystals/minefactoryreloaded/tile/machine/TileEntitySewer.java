package powercrystals.minefactoryreloaded.tile.machine;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import powercrystals.core.position.Area;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.ITankContainerBucketable;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiSewer;
import powercrystals.minefactoryreloaded.gui.container.ContainerSewer;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntitySewer extends TileEntityFactoryInventory implements ITankContainerBucketable
{
	private LiquidTank _tank;
	
	private HarvestAreaManager _areaManager;
	
	private int _tick;
	
	private long _nextSewerCheckTick;
	
	private boolean _jammed;
	
	public TileEntitySewer()
	{
		super(Machine.Sewer);
		_tank = new LiquidTank(1 * LiquidContainerRegistry.BUCKET_VOLUME);
		_areaManager = new HarvestAreaManager(this, 0, 1, 0);
		_areaManager.setOverrideDirection(ForgeDirection.UP);
	}
	
	@Override
	public String getGuiBackground()
	{
		return "sewagecollector.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiSewer(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerSewer getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerSewer(this, inventoryPlayer);
	}
	
	@Override
	public ILiquidTank getTank()
	{
		return _tank;
	}
	
	@Override
	protected boolean shouldPumpLiquid()
	{
		return true;
	}
	
	@Override
	protected void onFactoryInventoryChanged()
	{
		_areaManager.updateUpgradeLevel(_inventory[0]);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(worldObj.isRemote)
		{
			return;
		}
		_tick++;
		
		if(_nextSewerCheckTick <= worldObj.getTotalWorldTime())
		{
			Area a = new Area(BlockPosition.fromFactoryTile(this), _areaManager.getRadius(), 0, 0);
			_jammed = false;
			for(BlockPosition bp : a.getPositionsBottomFirst())
			{
				if(worldObj.getBlockId(bp.x, bp.y, bp.z) == MineFactoryReloadedCore.machineBlocks.get(0).blockID &&
						worldObj.getBlockMetadata(bp.x, bp.y, bp.z) == Machine.Sewer.getMeta() &&
						!(bp.x == xCoord && bp.y == yCoord && bp.z == zCoord))
				{
					_jammed = true;
					break;
				}
			}
			
			_nextSewerCheckTick = worldObj.getTotalWorldTime() + 800 + worldObj.rand.nextInt(800);
		}
		
		if(_tick >= 31 && !_jammed)
		{
			_tick = 0;
			List<?> entities = worldObj.getEntitiesWithinAABB(EntityLiving.class, _areaManager.getHarvestArea().toAxisAlignedBB());
			double massFound = 0;
			for(Object o : entities)
			{
				if(o instanceof EntityAnimal || o instanceof EntityVillager)
				{
					massFound += Math.pow(((EntityLiving)o).boundingBox.getAverageEdgeLength(), 2);
				}
				else if(o instanceof EntityPlayer && ((EntityPlayer)o).isSneaking())
				{
					massFound += Math.pow(((EntityLiving)o).boundingBox.getAverageEdgeLength(), 2);
				}
			}
			_tank.fill(LiquidDictionary.getLiquid("sewage", (int)(25 * massFound)), true);
		}
	}
	
	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
	{
		return 0;
	}
	
	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill)
	{
		return 0;
	}
	
	@Override
	public boolean allowBucketDrain()
	{
		return true;
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
		return _tank;
	}
	
	@Override
	public int getSizeInventory()
	{
		return 1;
	}
	
	@Override
	public String getInvName()
	{
		return "Sewer";
	}
}
