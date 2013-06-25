package powercrystals.minefactoryreloaded.tile.machine;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.position.Area;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.IFactoryFruit;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.TreeHarvestManager;
import powercrystals.minefactoryreloaded.core.TreeHarvestMode;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFruitPicker;
import powercrystals.minefactoryreloaded.gui.container.ContainerFruitPicker;
import powercrystals.minefactoryreloaded.setup.MFRConfig;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

public class TileEntityFruitPicker extends TileEntityFactoryPowered
{
	private HarvestAreaManager _areaManager;
	private TreeHarvestManager _treeManager;
	private BlockPosition _lastTree;
	
	private Random _rand;
	
	public TileEntityFruitPicker()
	{
		super(Machine.FruitPicker);
		_areaManager = new HarvestAreaManager(this, 1, 0, 0);
		_rand = new Random();
	}

	@Override
	public int getSizeInventory()
	{
		return 1;
	}
	
	@Override
	public String getGuiBackground()
	{
		return "fruitpicker.png";
	}
	
	@Override
	public ContainerFruitPicker getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerFruitPicker(this, inventoryPlayer);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiFruitPicker(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public String getInvName()
	{
		return "Fruit Picker";
	}
	
	@Override
	protected boolean activateMachine()
	{
		int harvestedBlockId = 0;
		int harvestedBlockMetadata = 0;
		
		BlockPosition targetCoords = getNextTree();
		if(targetCoords == null)
		{
			setIdleTicks(getIdleTicksMax());
			return false;
		}

		harvestedBlockId = worldObj.getBlockId(targetCoords.x, targetCoords.y, targetCoords.z);
		harvestedBlockMetadata = worldObj.getBlockMetadata(targetCoords.x, targetCoords.y, targetCoords.z);
		
		IFactoryFruit harvestable = MFRRegistry.getFruits().get(new Integer(harvestedBlockId));
		
		List<ItemStack> drops = harvestable.getDrops(worldObj, _rand, targetCoords.x, targetCoords.y, targetCoords.z);
		
		ItemStack replacement = harvestable.getReplacementBlock(worldObj, targetCoords.x, targetCoords.y, targetCoords.z);
		
		harvestable.prePick(worldObj, targetCoords.x, targetCoords.y, targetCoords.z);
		
		doDrop(drops);
		
		if(replacement == null)
		{
			if(MFRConfig.playSounds.getBoolean(true))
			{
				worldObj.playAuxSFXAtEntity(null, 2001, targetCoords.x, targetCoords.y, targetCoords.z, harvestedBlockId + (harvestedBlockMetadata << 12));
			}
			worldObj.setBlockToAir(targetCoords.x, targetCoords.y, targetCoords.z);
		}
		else
		{
			worldObj.setBlock(targetCoords.x, targetCoords.y, targetCoords.z, replacement.itemID, replacement.getItemDamage(), 3);
		}
		
		harvestable.postPick(worldObj, targetCoords.x, targetCoords.y, targetCoords.z);
		
		return true;
	}
	
	private BlockPosition getNextTree()
	{
		BlockPosition bp = _areaManager.getNextBlock();
		
		int searchId = worldObj.getBlockId(bp.x, bp.y, bp.z);
		
		if(!MFRRegistry.getFruitLogBlockIds().contains(searchId))
		{
			_lastTree = null;
			return null;
		}
		
		BlockPosition temp = getNextTreeSegment(bp.x, bp.y, bp.z, false);
		if(temp != null)
		{
			_areaManager.rewindBlock();
		}
		return temp;
	}
	
	private BlockPosition getNextTreeSegment(int x, int y, int z, boolean treeFlipped)
	{
		int blockId;
		
		if(_lastTree == null || _lastTree.x != x || _lastTree.y != y || _lastTree.z != z)
		{
			int yTreeAreaLowerBound = (treeFlipped ? y - MFRConfig.fruitTreeSearchMaxVertical.getInt() : y);
			int yTreeAreaUpperBound = (treeFlipped ? y : y + MFRConfig.fruitTreeSearchMaxVertical.getInt());
			Area a = new Area(x - MFRConfig.fruitTreeSearchMaxHorizontal.getInt(), x + MFRConfig.fruitTreeSearchMaxHorizontal.getInt(),
					yTreeAreaLowerBound, yTreeAreaUpperBound,
					z - MFRConfig.fruitTreeSearchMaxHorizontal.getInt(), z + MFRConfig.fruitTreeSearchMaxHorizontal.getInt());
			
			_treeManager = new TreeHarvestManager(a, treeFlipped ? TreeHarvestMode.HarvestInverted : TreeHarvestMode.Harvest);
			_lastTree = new BlockPosition(x, y, z);
		}
		else if(_treeManager.getIsDone())
		{
			_treeManager.reset();
		}
		
		while(true)
		{
			if(_treeManager.getIsDone())
			{
				return null;
			}
			
			BlockPosition bp = _treeManager.getNextBlock();
			blockId = worldObj.getBlockId(bp.x, bp.y, bp.z);

			if(MFRRegistry.getFruits().containsKey(new Integer(blockId)) && MFRRegistry.getFruits().get(new Integer(blockId)).canBePicked(worldObj, bp.x, bp.y, bp.z))
			{
				return bp;
			}
			_treeManager.moveNext();
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
		return 1;
	}
	
	@Override
	public int getIdleTicksMax()
	{
		return 5;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side)
	{
		return false;
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side)
	{
		return false;
	}
	
	@Override
	public boolean canRotate()
	{
		return true;
	}
	
	@Override
	protected void onFactoryInventoryChanged()
	{
		_areaManager.updateUpgradeLevel(_inventory[0]);
	}
	
	@Override
	public ForgeDirection getDropDirection()
	{
		return getDirectionFacing().getOpposite();
	}
}
