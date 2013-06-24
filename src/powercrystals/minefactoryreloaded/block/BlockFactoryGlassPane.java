package powercrystals.minefactoryreloaded.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.position.BlockPosition;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.rednet.IConnectableRedNet;
import powercrystals.minefactoryreloaded.api.rednet.RedNetConnectionType;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import powercrystals.minefactoryreloaded.render.IconOverlay;
import powercrystals.minefactoryreloaded.setup.MFRConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFactoryGlassPane extends BlockPane implements IConnectableRedNet
{
	private String[] _names = new String []
			{ "white", "orange", "magenta", "lightblue", "yellow", "lime", "pink", "gray", "lightgray", "cyan", "purple", "blue", "brown", "green", "red", "black" };
	private Icon[] _icons = new Icon[_names.length];
	protected Icon _iconSide;
	static Icon _overlay;
	
	public BlockFactoryGlassPane(int blockId)
	{
		super(blockId, "", "", Material.glass, false);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		setUnlocalizedName("mfr.stainedglass.pane");
		setHardness(0.3F);
		setStepSound(soundGlassFootstep);
		if(blockId != Block.thinGlass.blockID)
		{
			setCreativeTab(MFRCreativeTab.tab);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
		for(int i = 0; i < _names.length; i++)
		{
			_icons[i] = ir.registerIcon("powercrystals/minefactoryreloaded/tile.mfr.stainedglass." + _names[i]);
		}
		_iconSide = ir.registerIcon("powercrystals/minefactoryreloaded/tile.mfr.stainedglass.pane.side");
		_overlay = ir.registerIcon("powercrystals/minefactoryreloaded/tile.mfr.stainedglass.border");
	}
	
	@Override
	public Icon getIcon(int side, int meta)
	{
		return _icons[Math.min(meta, _icons.length - 1)];
	}
	
	@Override
	public int getRenderBlockPass()
	{
		return 1;
	}
	
	public Icon getBlockOverlayTexture()
	{
		return new IconOverlay(_overlay, 8, 8, false, false, false, false, false, false, false, false);
	}

	public Icon getBlockOverlayTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		BlockPosition bp = new BlockPosition(x, y, z, ForgeDirection.VALID_DIRECTIONS[side]);
		boolean[] sides = new boolean[8];
		bp.moveRight(1);
		sides[0] = world.getBlockId(bp.x,bp.y,bp.z) == blockID;
		bp.moveDown(1);
		sides[4] = world.getBlockId(bp.x,bp.y,bp.z) == blockID;
		bp.moveLeft(1);
		sides[1] = world.getBlockId(bp.x,bp.y,bp.z) == blockID;
		bp.moveLeft(1);
		sides[5] = world.getBlockId(bp.x,bp.y,bp.z) == blockID;
		bp.moveUp(1);
		sides[3] = world.getBlockId(bp.x,bp.y,bp.z) == blockID;
		bp.moveUp(1);
		sides[6] = world.getBlockId(bp.x,bp.y,bp.z) == blockID;
		bp.moveRight(1);
		sides[2] = world.getBlockId(bp.x,bp.y,bp.z) == blockID;
		bp.moveRight(1);
		sides[7] = world.getBlockId(bp.x,bp.y,bp.z) == blockID;
		return new IconOverlay(_overlay, 8, 8, sides);
	}
	
	@Override
	public Icon getSideTextureIndex()
	{
		return _iconSide;
	}
	
	public boolean canThisFactoryPaneConnectToThisBlockID(int blockId)
	{
		return Block.opaqueCubeLookup[blockId] ||
				blockId == Block.glass.blockID ||
				blockId == MineFactoryReloadedCore.factoryGlassPaneBlock.blockID ||
				blockId == MineFactoryReloadedCore.factoryGlassBlock.blockID ||
				(blockId == Block.thinGlass.blockID && MFRConfig.vanillaOverrideGlassPane.getBoolean(true));
	}
	
	@Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        int blockId = world.getBlockId(x, y, z);
        return !((blockId == Block.glass.blockID ||
				blockId == MineFactoryReloadedCore.factoryGlassPaneBlock.blockID ||
				blockId == MineFactoryReloadedCore.factoryGlassBlock.blockID ||
				(blockId == Block.thinGlass.blockID &&
					MFRConfig.vanillaOverrideGlassPane.getBoolean(true))) ||
					!super.shouldSideBeRendered(world, x, y, z, side));
    }
	
	@Override
	public boolean canPaneConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
	{
		return canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x, y, z));
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		float xStart = 0.4375F;
		float zStart = 0.5625F;
		float xStop = 0.4375F;
		float zStop = 0.5625F;
		boolean connectedNorth = this.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x, y, z - 1));
		boolean connectedSouth = this.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x, y, z + 1));
		boolean connectedWest = this.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x - 1, y, z));
		boolean connectedEast = this.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x + 1, y, z));
		
		if ((!connectedWest || !connectedEast) && (connectedWest || connectedEast || connectedNorth || connectedSouth))
		{
			if (connectedWest && !connectedEast)
			{
				xStart = 0.0F;
			}
			else if (!connectedWest && connectedEast)
			{
				zStart = 1.0F;
			}
		}
		else
		{
			xStart = 0.0F;
			zStart = 1.0F;
		}
		
		if ((!connectedNorth || !connectedSouth) && (connectedWest || connectedEast || connectedNorth || connectedSouth))
		{
			if (connectedNorth && !connectedSouth)
			{
				xStop = 0.0F;
			}
			else if (!connectedNorth && connectedSouth)
			{
				zStop = 1.0F;
			}
		}
		else
		{
			xStop = 0.0F;
			zStop = 1.0F;
		}
		
		this.setBlockBounds(xStart, 0.0F, xStop, zStart, 1.0F, zStop);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List blockList, Entity e)
	{
		boolean connectedNorth = this.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x, y, z - 1));
		boolean connectedSouth = this.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x, y, z + 1));
		boolean connectedWest = this.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x - 1, y, z));
		boolean connectedEast = this.canThisFactoryPaneConnectToThisBlockID(world.getBlockId(x + 1, y, z));
		
		if ((!connectedWest || !connectedEast) && (connectedWest || connectedEast || connectedNorth || connectedSouth))
		{
			if (connectedWest && !connectedEast)
			{
				this.setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
				addCollidingBlockToList_do(world, x, y, z, aabb, blockList, e);
			}
			else if (!connectedWest && connectedEast)
			{
				this.setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
				addCollidingBlockToList_do(world, x, y, z, aabb, blockList, e);
			}
		}
		else
		{
			this.setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
			addCollidingBlockToList_do(world, x, y, z, aabb, blockList, e);
		}
		
		if ((!connectedNorth || !connectedSouth) && (connectedWest || connectedEast || connectedNorth || connectedSouth))
		{
			if (connectedNorth && !connectedSouth)
			{
				this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
				addCollidingBlockToList_do(world, x, y, z, aabb, blockList, e);
			}
			else if (!connectedNorth && connectedSouth)
			{
				this.setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
				addCollidingBlockToList_do(world, x, y, z, aabb, blockList, e);
			}
		}
		else
		{
			this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
			addCollidingBlockToList_do(world, x, y, z, aabb, blockList, e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void addCollidingBlockToList_do(World world, int x, int y, int z, AxisAlignedBB aabb, List blockList, Entity e)
	{
		AxisAlignedBB newAABB = this.getCollisionBoundingBoxFromPool(world, x, y, z);
		
		if (newAABB != null && aabb.intersectsWith(newAABB))
		{
			blockList.add(newAABB);
		}
	}
	
	@Override
	public int getRenderType()
	{
		return MineFactoryReloadedCore.renderIdFactoryGlassPane;
	}
	
	@Override
	public RedNetConnectionType getConnectionType(World world, int x, int y, int z, ForgeDirection side)
	{
		return RedNetConnectionType.None;
	}
	
	@Override
	public int[] getOutputValues(World world, int x, int y, int z, ForgeDirection side)
	{
		return null;
	}
	
	@Override
	public int getOutputValue(World world, int x, int y, int z, ForgeDirection side, int subnet)
	{
		return 0;
	}
	
	@Override
	public void onInputsChanged(World world, int x, int y, int z, ForgeDirection side, int[] inputValues)
	{
	}
	
	@Override
	public void onInputChanged(World world, int x, int y, int z, ForgeDirection side, int inputValue)
	{
	}
}
