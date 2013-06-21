package powercrystals.minefactoryreloaded.render.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.util.Icon;

public class IconOverlay implements Icon {

	private Icon overlayIcon;
	private float xSegments, ySegments;
	private float selectedSegmentX, selectedSegmentY;

	public IconOverlay(Icon overlayIcon, int subX, int subY, boolean ...sides) {
		this.overlayIcon = overlayIcon;
		xSegments = subX;
		ySegments = subY;
		int parts = toInt(sides) & 255;
		int value = (parts & 15) + (parts > 15 ? 16 : 0);
		parts = parts >> 4;
		int w;
		switch (value) {
		case 23: // left empty
			w = parts & 9;
			if ((w == 1) | w == 8) // bottom right, top right
				value = 32 | (w >> 3);
			break;
		case 27: // top empty
			w = parts & 3;
			if ((w == 1) | w == 2) // bottom right, bottom left
				value = 34 | (w >> 1);
			break;
		case 29: // bottom empty
			w = parts & 12;
			if ((w == 4) | w == 8) // top left, top right
				value = 36 | (w >> 3);
			break;
		case 30: // right empty
			w = parts & 6;
			if ((w == 2) | w == 4) // bottom left, top left
				value = 38 | (w >> 2);
			break;
		case 31: // all sides
			value = 40 + parts;
		default:
		}
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
		return (int)(this.getMinU() * overlayIcon.getSheetWidth());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getOriginY() {
		return (int)(this.getMinV() * overlayIcon.getSheetHeight());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMinU() {
		return overlayIcon.getInterpolatedU((selectedSegmentX / xSegments) * 16f);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMaxU() {
		return overlayIcon.getInterpolatedU(((selectedSegmentX + 1) / xSegments) * 16f);
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
		return overlayIcon.getInterpolatedV((selectedSegmentY / ySegments) * 16f);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMaxV() {
		return overlayIcon.getInterpolatedV(((selectedSegmentY + 1) / ySegments) * 16f);
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
