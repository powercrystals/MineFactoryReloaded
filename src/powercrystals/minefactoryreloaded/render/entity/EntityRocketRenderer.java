package powercrystals.minefactoryreloaded.render.entity;

import org.lwjgl.opengl.GL11;

import powercrystals.minefactoryreloaded.entity.EntityRocket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityRocketRenderer extends Render
{
	private IModelCustom _model;
	
	public EntityRocketRenderer()
	{
		try
		{
			_model = AdvancedModelLoader.loadModel("/powercrystals/minefactoryreloaded/models/Rocket.obj");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
    public void renderRocket(EntityRocket rocket, double x, double y, double z, float yaw, float partialTicks)
    {
		RenderEngine renderengine = Minecraft.getMinecraft().renderEngine;
		
		if(renderengine != null)
		{
			renderengine.bindTexture("/textures/itemmodels/powercrystals/minefactoryreloaded/Rocket.png");
		}
		
		GL11.glPushMatrix();
		
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glRotatef(rocket.prevRotationYaw + (rocket.rotationYaw - rocket.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(90, 0, 0, 1);
		GL11.glRotatef(rocket.prevRotationPitch + (rocket.rotationPitch - rocket.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
		GL11.glScalef(0.01F, 0.01F, 0.01F);
		
		_model.renderAll();
		
		GL11.glPopMatrix();
    }

    @Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks)
    {
        this.renderRocket((EntityRocket)entity, x, y, z, yaw, partialTicks);
    }
}