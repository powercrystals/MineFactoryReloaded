package powercrystals.minefactoryreloaded.gui.client;

import net.minecraft.client.gui.GuiButton;
import powercrystals.core.net.PacketWrapper;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.container.ContainerAutoEnchanter;
import powercrystals.minefactoryreloaded.net.Packets;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoEnchanter;
import cpw.mods.fml.common.network.PacketDispatcher;

public class GuiAutoEnchanter extends GuiFactoryPowered
{
	private TileEntityAutoEnchanter _enchanter;
	private GuiButton _inc;
	private GuiButton _dec;
	
	public GuiAutoEnchanter(ContainerAutoEnchanter container, TileEntityAutoEnchanter tileentity)
	{
		super(container, tileentity);
		_enchanter = tileentity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		super.initGui();
		_inc = new GuiButton(1, (this.width - this.xSize) / 2 + 63, (this.height - this.ySize) / 2 + 23, 20, 20, "+");
		_dec = new GuiButton(2, (this.width - this.xSize) / 2 + 63, (this.height - this.ySize) / 2 + 53, 20, 20, "-");
		_inc.enabled = (_enchanter.getTargetLevel() < 30);
		_dec.enabled = (_enchanter.getTargetLevel() > 1);
		buttonList.add(_inc);
		buttonList.add(_dec);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		fontRenderer.drawString(new Integer(_enchanter.getTargetLevel()).toString(), 68, 44, 4210752);
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		_inc.enabled = (_enchanter.getTargetLevel() < 30);
		_dec.enabled = (_enchanter.getTargetLevel() > 1);
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button.id == 1)
		{
			if(_enchanter.getTargetLevel() < 30)
			{
				_enchanter.setTargetLevel(_enchanter.getTargetLevel() + 1);
				PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modNetworkChannel, Packets.EnchanterButton,
						new Object[] { _enchanter.xCoord, _enchanter.yCoord, _enchanter.zCoord, 1 }));
			}
		}
		else
		{
			if(_enchanter.getTargetLevel() > 1)
			{
				_enchanter.setTargetLevel(_enchanter.getTargetLevel() - 1);
				PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modNetworkChannel, Packets.EnchanterButton,
						new Object[] { _enchanter.xCoord, _enchanter.yCoord, _enchanter.zCoord, -1 }));
			}
		}
	}
}
