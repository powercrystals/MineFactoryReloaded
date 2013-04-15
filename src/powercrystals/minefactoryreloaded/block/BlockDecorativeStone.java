package powercrystals.minefactoryreloaded.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDecorativeStone extends Block
{
	private String[] _names = new String [] { "black.smooth", "white.smooth", "black.cobble", "white.cobble", "black.brick.large", "white.brick.large",
			"black.brick.small", "white.brick.small", "black.gravel", "white.gravel", "black.paved", "white.paved" };
	private Icon[] _icons = new Icon[_names.length];
	
	public BlockDecorativeStone(int blockId)
	{
		super(blockId, Material.rock);
		setHardness(2.0F);
		setResistance(10.0F);
		setStepSound(soundStoneFootstep);
		setUnlocalizedName("mfr.decorativestone");
		setCreativeTab(MFRCreativeTab.tab);
	}
	
	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister ir)
	{
		for(int i = 0; i < _icons.length; i++)
		{
			_icons[i] = ir.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName() + "." + _names[i]);
		}
	}
	
	@Override
	public Icon getIcon(int side, int meta)
	{
		return _icons[Math.min(meta, _icons.length)];
	}
}
