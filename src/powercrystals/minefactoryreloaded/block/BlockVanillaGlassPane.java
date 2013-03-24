package powercrystals.minefactoryreloaded.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

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
	public Icon getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		return _iconPane;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void func_94332_a(IconRegister ir)
	{
		_iconPane = ir.func_94245_a("glass");
    	_iconSide = ir.func_94245_a("thinglass_top");
	}
}
