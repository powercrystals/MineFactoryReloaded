package powercrystals.minefactoryreloaded.render.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.util.Icon;

public class IconOverlay implements Icon {

	private Icon overlayIcon;
	private int xSegments, ySegments;
	private int selectedSegmentX, selectedSegmentY;

	public IconOverlay(Icon overlayIcon, int subX, int subY, boolean ...sides) {
		this.overlayIcon = overlayIcon;
		xSegments = subX;
		ySegments = subY;
		int value = toInt(sides);
		selectedSegmentX = value % subX;
		selectedSegmentY = value / subX;
	}
	
	private static int toInt(boolean ...flags)
	{
		int ret = 0;
		for (int i = flags.length; i --> 0;)
			ret |= (flags[i] ? 1 : 0) << i;
		return ret; 
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getOriginX() {
		return overlayIcon.getOriginX();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getOriginY() {
		return overlayIcon.getOriginY();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMinU() {
		return overlayIcon.getMinU() + (selectedSegmentX / xSegments);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMaxU() {
		return getMinU() + (1 / xSegments);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getInterpolatedU(double d0) {
		float minU = this.getMinU();
		float f = this.getMaxU() - minU;
		return minU + f * ((float)d0 / 16.0F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMinV() {
		return overlayIcon.getMinV() + (selectedSegmentY / ySegments);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMaxV() {
		return getMinV() + (1 / ySegments);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getInterpolatedV(double d0) {
		float minV = this.getMinV();
		float f = this.getMaxV() - minV;
		return minV + f * ((float)d0 / 16.0F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getIconName() {
		return overlayIcon.getIconName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSheetWidth() {
		return overlayIcon.getSheetWidth();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSheetHeight() {
		return overlayIcon.getSheetHeight();
	}

}
