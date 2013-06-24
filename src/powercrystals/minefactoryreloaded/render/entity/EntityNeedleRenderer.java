package powercrystals.minefactoryreloaded.render.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import powercrystals.minefactoryreloaded.entity.EntityNeedle;

@SideOnly(Side.CLIENT)
public class EntityNeedleRenderer extends Render
{
    public void renderNeedle(EntityNeedle needle, double par2, double par4, double par6, float par8, float par9)
    {
        this.loadTexture("/item/arrows.png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glRotatef(needle.prevRotationYaw + (needle.rotationYaw - needle.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(needle.prevRotationPitch + (needle.rotationPitch - needle.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.instance;
        byte b0 = 0;
        
        double f2 = 0.0D;
        double f3 = 0.5D;
        double f4 = (0 + b0 * 10) / 32.0D;
        double f5 = (5 + b0 * 10) / 32.0D;
        
        double f6 = 0.0D;
        double f7 = 0.15625D;
        double f8 = (5 + b0 * 10) / 32.0D;
        double f9 = (10 + b0 * 10) / 32.0D;
        float f10 = 0.01F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(f10, f10, f10);
        GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
        GL11.glNormal3f(f10, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, f6, f8);
        tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, f7, f8);
        tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, f7, f9);
        tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, f6, f9);
        tessellator.draw();
        GL11.glNormal3f(-f10, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, f6, f8);
        tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, f7, f8);
        tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, f7, f9);
        tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, f6, f9);
        tessellator.draw();

        for (int i = 0; i < 4; ++i)
        {
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, f10);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-8.0D, -2.0D, 0.0D, f2, f4);
            tessellator.addVertexWithUV(8.0D, -2.0D, 0.0D, f3, f4);
            tessellator.addVertexWithUV(8.0D, 2.0D, 0.0D, f3, f5);
            tessellator.addVertexWithUV(-8.0D, 2.0D, 0.0D, f2, f5);
            tessellator.draw();
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    @Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderNeedle((EntityNeedle)par1Entity, par2, par4, par6, par8, par9);
    }
}