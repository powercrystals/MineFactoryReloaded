package powercrystals.minefactoryreloaded.block;

import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

public class BlockFactoryDecorativeBricks extends Block
{
	private String[] _names = new String [] { "glowstone", "ice", "lapis", "obsidian", "pavedstone", "snow", "glowstone_large", "ice_large", "lapis_large", "obsidian_large", "snow_large" };
	private Icon[] _icons = new Icon[_names.length];
	
	public BlockFactoryDecorativeBricks(int blockId)
	{
		super(blockId, Material.rock);
		setHardness(2.0F);
		setResistance(10.0F);
		setStepSound(soundStoneFootstep);
		setUnlocalizedName("mfr.decorativebrick");
		setCreativeTab(MFRCreativeTab.tab);
	}
	
	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		return (meta == 0 || meta == 6) ? 15 : 0;
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
	public Icon getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		return _icons[Math.min(meta, _icons.length)];
	}
}
