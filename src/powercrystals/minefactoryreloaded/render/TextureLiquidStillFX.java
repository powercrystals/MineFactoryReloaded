package powercrystals.minefactoryreloaded.render;

import net.minecraft.client.renderer.RenderEngine;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLTextureFX;

public class TextureLiquidStillFX extends FMLTextureFX
{	
	protected float red[];
	protected float green[];
	protected float blue[];
	protected float alpha[];
	
	private int rMin;
	private int rMax;
	private int gMin;
	private int gMax;
	private int bMin;
	private int bMax;
	
	private String _texture;
	
	public TextureLiquidStillFX(int textureIndex, String texture, int rMin, int rMax, int gMin, int gMax, int bMin, int bMax)
	{
		super(textureIndex);
		
		_texture = texture;
		
		this.rMin = rMin;
		this.rMax = rMax;
		this.gMin = gMin;
		this.gMax = gMax;
		this.bMin = bMin;
		this.bMax = bMax;
	}

	@Override
	public void setup()
	{
		this.red = new float[tileSizeSquare];
		this.green = new float[tileSizeSquare];
		this.blue = new float[tileSizeSquare];
		this.alpha = new float[tileSizeSquare];
	}
	
	@Override
	public void bindImage(RenderEngine renderengine)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderengine.getTexture(_texture));
	}
	
	@Override
	public void onTick()
	{
		for (int x = 0; x < tileSizeBase; x++)
		{
			for (int y = 0; y < tileSizeBase; y++)
			{
				float f = 0.0F;
				for (int j1 = x - 1; j1 <= 1 + 1; j1++)
				{
					int k1 = j1 & tileSizeMask;
					int i2 = y & tileSizeMask;
					f += red[k1 + i2 * tileSizeBase];
				}
				
				green[x + y * tileSizeBase] = f / 3.3F + blue[x + y * tileSizeBase] * 0.8F;
			}
		}
		
		for (int j = 0; j < tileSizeBase; j++)
		{
			for (int l = 0; l < tileSizeBase; l++)
			{
				blue[j + l * tileSizeBase] += alpha[j + l * tileSizeBase] * 0.05F;
				if (blue[j + l * tileSizeBase] < 0.0F)
				{
					blue[j + l * tileSizeBase] = 0.0F;
				}
				alpha[j + l * tileSizeBase] -= 0.1F;
				if (Math.random() < 0.05D)
				{
					alpha[j + l * tileSizeBase] = 0.5F;
				}
			}
		}
			
		float af[] = green;
		green = red;
		red = af;
		for (int i1 = 0; i1 < tileSizeSquare; i1++)
		{
			float f1 = red[i1];
			if (f1 > 1.0F)
			{
				f1 = 1.0F;
			}
			if (f1 < 0.0F)
			{
				f1 = 0.0F;
			}
			float f2 = f1 * f1;
			int r = (int)(rMin + f2 * (rMax - rMin));
			int g = (int)(gMin + f2 * (gMax - rMin));
			int b = (int)(bMin + f2 * (bMax - rMin));
			if (anaglyphEnabled)
			{
				int i3 = (r * 30 + g * 59 + b * 11) / 100;
				int j3 = (r * 30 + g * 70) / 100;
				int k3 = (r * 30 + b * 70) / 100;
				r = i3;
				g = j3;
				b = k3;
			}
		
			imageData[i1 * 4 + 0] = (byte) r;
			imageData[i1 * 4 + 1] = (byte) g;
			imageData[i1 * 4 + 2] = (byte) b;
			imageData[i1 * 4 + 3] = (byte) 255;
		}
	}
}
