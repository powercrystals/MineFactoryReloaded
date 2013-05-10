package powercrystals.minefactoryreloaded.block;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockVanillaGlassPane extends BlockFactoryGlassPane
{
	private Icon _iconPane;
	private Icon _iconSide;
	
	public BlockVanillaGlassPane()
	{
		super(102);
		setHardness(0.3F);
		setStepSound(soundGlassFootstep);
		setUnlocalizedName("thinGlass");
	}
	
	@Override
	public Icon getBlockSideTextureFromMetadata(int meta)
	{
		return _iconSide;
	}
	
	@Override
	public Icon getIcon(int side, int meta)
	{
		return _iconPane;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister ir)
	{
		_iconPane = ir.registerIcon("glass");
		_iconSide = ir.registerIcon("thinglass_top");
	}
}
