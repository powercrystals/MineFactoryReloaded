package powercrystals.minefactoryreloaded.block;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFactoryGlass extends BlockGlass implements IConnectableRedNet
{
	private String[] _names = new String []
			{ "white", "orange", "magenta", "lightblue", "yellow", "lime", "pink", "gray", "lightgray", "cyan", "purple", "blue", "brown", "green", "red", "black" };
	private Icon[] _icons = new Icon[_names.length];
	
	public BlockFactoryGlass(int blockId)
	{
		super(blockId, Material.glass, false);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		setUnlocalizedName("mfr.stainedglass.block");
		setHardness(0.3F);
		setStepSound(soundGlassFootstep);
		setCreativeTab(MFRCreativeTab.tab);
	}
	
	@Override
	public int getRenderBlockPass()
	{
		return 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
		for(int i = 0; i < _names.length; i++)
		{
			_icons[i] = ir.registerIcon("powercrystals/minefactoryreloaded/tile.mfr.stainedglass." + _names[i]);
		}
	}
	
	@Override
	public Icon getIcon(int side, int meta)
	{
		return _icons[Math.min(meta, _icons.length - 1)];
	}
	
	public Icon getBlockOverlayTexture()
	{
		return new IconOverlay(BlockFactoryGlassPane._overlay, 8, 8, false, false, false, false, false, false, false, false);
	}

	public Icon getBlockOverlayTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		if (side <= 1)
		{
			BlockPosition bp = new BlockPosition(x, y, z, ForgeDirection.NORTH);
			boolean[] sides = new boolean[8];
			bp.moveRight(1);
			sides[0] = world.getBlockId(bp.x,bp.y,bp.z) == blockID;
			bp.moveBackwards(1);
			sides[4] = world.getBlockId(bp.x,bp.y,bp.z) == blockID;
			bp.moveLeft(1);
			sides[1] = world.getBlockId(bp.x,bp.y,bp.z) == blockID;
			bp.moveLeft(1);
			sides[5] = world.getBlockId(bp.x,bp.y,bp.z) == blockID;
			bp.moveForwards(1);
			sides[3] = world.getBlockId(bp.x,bp.y,bp.z) == blockID;
			bp.moveForwards(1);
			sides[6] = world.getBlockId(bp.x,bp.y,bp.z) == blockID;
			bp.moveRight(1);
			sides[2] = world.getBlockId(bp.x,bp.y,bp.z) == blockID;
			bp.moveRight(1);
			sides[7] = world.getBlockId(bp.x,bp.y,bp.z) == blockID;
			return new IconOverlay(BlockFactoryGlassPane._overlay, 8, 8, sides);
		}
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
		return new IconOverlay(BlockFactoryGlassPane._overlay, 8, 8, sides);
	}
	
	@Override
	public int getRenderType()
	{
		return MineFactoryReloadedCore.renderIdFactoryGlass;
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
