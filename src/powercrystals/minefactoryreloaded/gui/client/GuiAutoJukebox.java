package powercrystals.minefactoryreloaded.gui.client;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;

import powercrystals.core.net.PacketWrapper;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.decorative.TileEntityAutoJukebox;
import powercrystals.minefactoryreloaded.net.Packets;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.StatCollector;

public class GuiAutoJukebox extends GuiContainer
{
	private TileEntityAutoJukebox _jukebox;
	private GuiButton _play;
	private GuiButton _stop;
	private GuiButton _copy;

	public GuiAutoJukebox(Container container, TileEntityAutoJukebox jukebox)
	{
		super(container);
		_jukebox = jukebox;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		super.initGui();
		_play = new GuiButton(1, (this.width - this.xSize) / 2 +  63, (this.height - this.ySize) / 2 + 23, 20, 20, "\u25B6");
		_stop = new GuiButton(2, (this.width - this.xSize) / 2 +  83, (this.height - this.ySize) / 2 + 23, 20, 20, "\u25A0");
		_copy = new GuiButton(3, (this.width - this.xSize) / 2 + 103, (this.height - this.ySize) / 2 + 23, 20, 20, "\u25CF");
		controlList.add(_play);
		controlList.add(_stop);
		controlList.add(_copy);
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		_copy.enabled = _jukebox.getCanCopy();
		_play.enabled = _jukebox.getCanPlay();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		fontRenderer.drawString(_jukebox.getInvName(), 8, 6, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);	
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		int texture = mc.renderEngine.getTexture(MineFactoryReloadedCore.guiFolder + "autojukebox.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modId, Packets.AutoJukeboxButton,
				new Object[] { _jukebox.xCoord, _jukebox.yCoord, _jukebox.zCoord, button.id }));
	}
}
