package powercrystals.minefactoryreloaded.block;

import powercrystals.minefactoryreloaded.render.IconOverlay;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockVanillaGlassPane extends BlockFactoryGlassPane
{
	private Icon _iconPane;
	
	public BlockVanillaGlassPane()
	{
		super(102);
		setHardness(0.3F);
		setStepSound(soundGlassFootstep);
		setUnlocalizedName("thinGlass");
	}
	
	@Override
	public Icon getBlockOverlayTexture()
	{
		return new IconOverlay(BlockFactoryGlass._texture, 8, 8, 0, 7);
	}
	
	@Override
	public Icon getBlockOverlayTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		return getBlockOverlayTexture();
	}
	
	@Override
	public Icon getIcon(int side, int meta)
	{
		return _iconPane;
	}
	
	@Override
	public Icon getSideTextureIndex()
	{
		return _iconSide;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister ir)
	{
		_iconPane = ir.registerIcon("glass");
		_iconSide = ir.registerIcon("thinglass_top");
	}
}
