package powercrystals.minefactoryreloaded.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;

public class BlockFactoryGlass extends BlockGlass
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
	public Icon getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		return _icons[Math.min(meta, _icons.length - 1)];
	}
}
