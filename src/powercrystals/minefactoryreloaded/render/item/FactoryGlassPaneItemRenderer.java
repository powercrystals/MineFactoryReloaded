package powercrystals.minefactoryreloaded.render.item;

import powercrystals.minefactoryreloaded.block.BlockFactoryGlassPane;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;

public class FactoryGlassPaneItemRenderer implements IItemRenderer
{
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return type.ordinal() < ItemRenderType.FIRST_PERSON_MAP.ordinal();
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return helper.ordinal() < ItemRendererHelper.EQUIPPED_BLOCK.ordinal();
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		RenderEngine renderEngine = Minecraft.getMinecraft().renderEngine;
		RenderBlocks renderer = (RenderBlocks)data[0];

		BlockFactoryGlassPane pane = (BlockFactoryGlassPane)Block.blocksList[((ItemBlock)item.getItem()).getBlockID()];

		Icon iconFront, iconOverlay;

		iconFront = renderer.getBlockIconFromSideAndMetadata(pane, 0, item.getItemDamage());
		iconOverlay = pane.getBlockOverlayTexture();

		GL11.glPushMatrix();

		if (item.getItemSpriteNumber() == 0)
			renderEngine.bindTexture("/terrain.png");
		else
			renderEngine.bindTexture("/gui/items.png");

		Tessellator tessellator = Tessellator.instance;

		double minXGlass = iconFront.getMinU();
		double maxXGlass = iconFront.getMaxU();
		double minYGlass = iconFront.getMinV();
		double maxYGlass = iconFront.getMaxV();

		double minXOverlay = iconOverlay.getMinU();
		double maxXOverlay = iconOverlay.getMaxU();
		double minYOverlay = iconOverlay.getMinV();
		double maxYOverlay = iconOverlay.getMaxV();

		double xMin = 0;
		double xMax = 16;
		double yMin = 0;
		double yMax = 16;
		double zMin = 0;
		double zMax = 0;

		if(type == ItemRenderType.INVENTORY)
		{
			GL11.glDisable(GL11.GL_LIGHTING);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(xMin, yMin, zMin, minXGlass, minYGlass);
			tessellator.addVertexWithUV(xMin, yMax, zMin, minXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMax, yMax, zMax, maxXGlass, maxYGlass);
			tessellator.addVertexWithUV(xMax, yMin, zMax, maxXGlass, minYGlass);

			tessellator.addVertexWithUV(xMin, yMin, zMin, minXOverlay, minYOverlay);
			tessellator.addVertexWithUV(xMin, yMax, zMin, minXOverlay, maxYOverlay);
			tessellator.addVertexWithUV(xMax, yMax, zMax, maxXOverlay, maxYOverlay);
			tessellator.addVertexWithUV(xMax, yMin, zMax, maxXOverlay, minYOverlay);
			tessellator.draw();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_CULL_FACE);

			if (item.hasEffect())
			{
				GL11.glDepthFunc(GL11.GL_GREATER);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDepthMask(false);
				renderEngine.bindTexture("%blur%/misc/glint.png");
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
				GL11.glColor4f(0.5F, 0.25F, 0.8F, 1.0F);
				GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
				float f = 0.00390625F;
				float f1 = 0.00390625F;
				float f2 = Minecraft.getSystemTime() % (3000 + 0 * 1873) / (3000.0F + 0 * 1873) * 256.0F;
				float f3 = 0.0F;
				float f4 = 4.0F;
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(0,  16, -50, (f2 + 16 * f4) * f, (f3 + 16) * f1);
				tessellator.addVertexWithUV(16, 16, -50, (f2 + 16 + 16 * f4) * f, (f3 + 16) * f1);
				tessellator.addVertexWithUV(16, 0,  -50, (f2 + 16) * f, (f3 + 0.0F) * f1);
				tessellator.addVertexWithUV(0,  0,  -50, (f2 + 0.0F) * f, (f3 + 0.0F) * f1);
				tessellator.draw();
				f2 = Minecraft.getSystemTime() % (3000 + 1 * 1873) / (3000.0F + 1 * 1873) * 256.0F;
				f4 = -1.0F;
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(0,  16, -50, (f2 + 16 * f4) * f, (f3 + 16) * f1);
				tessellator.addVertexWithUV(16, 16, -50, (f2 + 16 + 16 * f4) * f, (f3 + 16) * f1);
				tessellator.addVertexWithUV(16, 0,  -50, (f2 + 16) * f, (f3 + 0.0F) * f1);
				tessellator.addVertexWithUV(0,  0,  -50, (f2 + 0.0F) * f, (f3 + 0.0F) * f1);
				tessellator.draw();
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glDepthMask(true);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDepthFunc(GL11.GL_LEQUAL);
			}
		}
		else
		{
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			if (type == ItemRenderType.ENTITY)
			{
				GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
			}
			renderItemIn2D(tessellator, maxXGlass, maxYGlass, minXGlass, minYGlass, maxXOverlay, maxYOverlay, minXOverlay, minYOverlay, iconFront.getSheetWidth(), iconFront.getSheetHeight(), 0.0625D);

			if (item.hasEffect())
			{
				GL11.glDepthFunc(GL11.GL_EQUAL);
				GL11.glDisable(GL11.GL_LIGHTING);
				renderEngine.bindTexture("%blur%/misc/glint.png");
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
				float f7 = 0.76F;
				GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glPushMatrix();
				float f8 = 0.125F;
				GL11.glScalef(f8, f8, f8);
				float f9 = Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F;
				GL11.glTranslatef(f9, 0.0F, 0.0F);
				GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
				ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glScalef(f8, f8, f8);
				f9 = Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F;
				GL11.glTranslatef(-f9, 0.0F, 0.0F);
				GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
				ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
				GL11.glPopMatrix();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDepthFunc(GL11.GL_LEQUAL);
			}

			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
		GL11.glPopMatrix();
	}

	private static void renderItemIn2D(Tessellator tessellator, double maxXIcon, double maxYIcon, double minXIcon, double minYIcon, double maxXOverlay, double maxYOverlay, double minXOverlay, double minYOverlay, int sheetWidth, int sheetHeight, double thickness)
	{
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		tessellator.addVertexWithUV(0, 0, 0, minXIcon, minYIcon);
		tessellator.addVertexWithUV(1, 0, 0, maxXIcon, minYIcon);
		tessellator.addVertexWithUV(1, 1, 0, maxXIcon, maxYIcon);
		tessellator.addVertexWithUV(0, 1, 0, minXIcon, maxYIcon);

		tessellator.addVertexWithUV(0, 0, 0, minXOverlay, minYOverlay);
		tessellator.addVertexWithUV(1, 0, 0, maxXOverlay, minYOverlay);
		tessellator.addVertexWithUV(1, 1, 0, maxXOverlay, maxYOverlay);
		tessellator.addVertexWithUV(0, 1, 0, minXOverlay, maxYOverlay);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		tessellator.addVertexWithUV(0, 1, -thickness, minXIcon, maxYIcon);
		tessellator.addVertexWithUV(1, 1, -thickness, maxXIcon, maxYIcon);
		tessellator.addVertexWithUV(1, 0, -thickness, maxXIcon, minYIcon);
		tessellator.addVertexWithUV(0, 0, -thickness, minXIcon, minYIcon);

		tessellator.addVertexWithUV(0, 1, -thickness, minXOverlay, maxYOverlay);
		tessellator.addVertexWithUV(1, 1, -thickness, maxXOverlay, maxYOverlay);
		tessellator.addVertexWithUV(1, 0, -thickness, maxXOverlay, minYOverlay);
		tessellator.addVertexWithUV(0, 0, -thickness, minXOverlay, minYOverlay);
		tessellator.draw();

		double iconWidth = sheetWidth * (maxXIcon - minXIcon);
		double iconHeight = sheetHeight * (minYIcon - maxYIcon);

		double overlayWidth = sheetWidth * (maxXOverlay - minXOverlay);
		double overlayHeight = sheetHeight * (minYOverlay - maxYOverlay);

		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);

		int k;
		double f7;
		double f8;

		for (k = 0; k < iconWidth; ++k)
		{
			f7 = k / iconWidth;
			f8 = maxXIcon + (minXIcon - maxXIcon) * f7 - 0.5D / sheetWidth;
			tessellator.addVertexWithUV(f7, 0, -thickness, f8, minYIcon);
			tessellator.addVertexWithUV(f7, 0,          0, f8, minYIcon);
			tessellator.addVertexWithUV(f7, 1,          0, f8, maxYIcon);
			tessellator.addVertexWithUV(f7, 1, -thickness, f8, maxYIcon);

			f7 = k / overlayWidth;
			f8 = maxXOverlay + (minXOverlay - maxXOverlay) * f7 - 0.5D / sheetWidth;
			tessellator.addVertexWithUV(f7, 0, -thickness, f8, minYOverlay);
			tessellator.addVertexWithUV(f7, 0,          0, f8, minYOverlay);
			tessellator.addVertexWithUV(f7, 1,          0, f8, maxYOverlay);
			tessellator.addVertexWithUV(f7, 1, -thickness, f8, maxYOverlay);
		}

		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		double f9;

		for (k = 0; k < iconWidth; ++k)
		{
			f7 = k / iconWidth;
			f9 = f7 + 1 / iconWidth;
			f8 = maxXIcon + (minXIcon - maxXIcon) * f7 - 0.5D / sheetWidth;
			tessellator.addVertexWithUV(f9, 1, -thickness, f8, maxYIcon);
			tessellator.addVertexWithUV(f9, 1,          0, f8, maxYIcon);
			tessellator.addVertexWithUV(f9, 0,          0, f8, minYIcon);
			tessellator.addVertexWithUV(f9, 0, -thickness, f8, minYIcon);

			f7 = k / overlayWidth;
			f9 = f7 + 1 / overlayWidth;
			f8 = maxXOverlay + (minXOverlay - maxXOverlay) * f7 - 0.5D / sheetWidth;
			tessellator.addVertexWithUV(f9, 1, -thickness, f8, maxYOverlay);
			tessellator.addVertexWithUV(f9, 1,          0, f8, maxYOverlay);
			tessellator.addVertexWithUV(f9, 0,          0, f8, minYOverlay);
			tessellator.addVertexWithUV(f9, 0, -thickness, f8, minYOverlay);
		}

		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);

		for (k = 0; k < iconHeight; ++k)
		{
			f7 = k / overlayHeight;
			f9 = f7 + 1 / overlayHeight;
			f8 = minYIcon + (maxYIcon - minYIcon) * f7 - 0.5D / sheetHeight;
			tessellator.addVertexWithUV(0, f9,          0, minXIcon, f8);
			tessellator.addVertexWithUV(1, f9,          0, maxXIcon, f8);
			tessellator.addVertexWithUV(1, f9, -thickness, maxXIcon, f8);
			tessellator.addVertexWithUV(0, f9, -thickness, minXIcon, f8);

			f7 = k / overlayHeight;
			f9 = f7 + 1 / overlayHeight;
			f8 = minYOverlay + (maxYOverlay - minYOverlay) * f7 - 0.5D / sheetHeight;
			tessellator.addVertexWithUV(0, f9,          0, minXOverlay, f8);
			tessellator.addVertexWithUV(1, f9,          0, maxXOverlay, f8);
			tessellator.addVertexWithUV(1, f9, -thickness, maxXOverlay, f8);
			tessellator.addVertexWithUV(0, f9, -thickness, minXOverlay, f8);
		}

		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);

		for (k = 0; k < iconHeight; ++k)
		{
			f7 = k / iconHeight;
			f8 = minYIcon + (maxYIcon - minYIcon) * f7 - 0.5D / sheetHeight;
			tessellator.addVertexWithUV(1, f7,          0, maxXIcon, f8);
			tessellator.addVertexWithUV(0, f7,          0, minXIcon, f8);
			tessellator.addVertexWithUV(0, f7, -thickness, minXIcon, f8);
			tessellator.addVertexWithUV(1, f7, -thickness, maxXIcon, f8);

			f7 = k / overlayHeight;
			f8 = minYOverlay + (maxYOverlay - minYOverlay) * f7 - 0.5D / sheetHeight;
			tessellator.addVertexWithUV(1, f7,          0, maxXOverlay, f8);
			tessellator.addVertexWithUV(0, f7,          0, minXOverlay, f8);
			tessellator.addVertexWithUV(0, f7, -thickness, minXOverlay, f8);
			tessellator.addVertexWithUV(1, f7, -thickness, maxXOverlay, f8);
		}

		tessellator.draw();
	}
}
