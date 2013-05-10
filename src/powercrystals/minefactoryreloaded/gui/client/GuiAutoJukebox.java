package powercrystals.minefactoryreloaded.gui.client;

import net.minecraft.client.gui.GuiButton;
import powercrystals.core.net.PacketWrapper;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryInventory;
import powercrystals.minefactoryreloaded.net.Packets;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoJukebox;
import cpw.mods.fml.common.network.PacketDispatcher;

public class GuiAutoJukebox extends GuiFactoryInventory
{
	private TileEntityAutoJukebox _jukebox;
	private GuiButton _play;
	private GuiButton _stop;
	private GuiButton _copy;
	
	public GuiAutoJukebox(ContainerFactoryInventory container, TileEntityAutoJukebox jukebox)
	{
		super(container, jukebox);
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
		buttonList.add(_play);
		buttonList.add(_stop);
		buttonList.add(_copy);
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		_copy.enabled = _jukebox.getCanCopy();
		_play.enabled = _jukebox.getCanPlay();
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modNetworkChannel, Packets.AutoJukeboxButton,
				new Object[] { _jukebox.xCoord, _jukebox.yCoord, _jukebox.zCoord, button.id }));
	}
}
