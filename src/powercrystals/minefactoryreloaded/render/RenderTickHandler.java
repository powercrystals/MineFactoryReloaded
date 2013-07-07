package powercrystals.minefactoryreloaded.render;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

import powercrystals.minefactoryreloaded.MineFactoryReloadedClient;
import powercrystals.minefactoryreloaded.item.ItemRocketLauncher;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class RenderTickHandler implements ITickHandler
{
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		renderHUD((Float)tickData[0]);
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel()
	{
		return null;
	}
	
	private void renderHUD(float partialTicks)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if(!mc.isGamePaused && mc.currentScreen == null && mc.thePlayer != null && mc.thePlayer.inventory.getCurrentItem() != null
				&& mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemRocketLauncher)
		{
			ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			Point center = new Point(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2);
			
			if(MineFactoryReloadedClient.instance.getLockedEntity() != Integer.MIN_VALUE)
			{
				mc.renderEngine.bindTexture("/powercrystals/minefactoryreloaded/textures/hud/lockon_blue.png");
			}
			else
			{
				mc.renderEngine.bindTexture("/powercrystals/minefactoryreloaded/textures/hud/lockon_red.png");
			}
			
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef(center.getX(), center.getY(), 0);
			GL11.glRotatef((mc.theWorld.getWorldTime() * 4) % 360 + partialTicks, 0, 0, 1);
			
			float distance = MineFactoryReloadedClient.instance.getLockTimeRemaining();
			
			drawLockonPart(center, distance, 0);
			drawLockonPart(center, distance, 90);
			drawLockonPart(center, distance, 180);
			drawLockonPart(center, distance, 270);
			
			GL11.glPopMatrix();
		}
	}
	
	private void drawLockonPart(Point center, float distanceFromCenter, int rotation)
	{
		GL11.glPushMatrix();
		
		GL11.glRotatef(rotation, 0, 0, 1);
		GL11.glTranslatef(-8, -13, 0);
		GL11.glTranslatef(0, -distanceFromCenter, 0);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2i(0, 0);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2i(0, 16);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2i(16, 16);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2i(16, 0);
		GL11.glEnd();
		
		GL11.glPopMatrix();
	}
}
