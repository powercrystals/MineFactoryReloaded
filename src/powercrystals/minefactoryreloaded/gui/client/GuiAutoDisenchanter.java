package powercrystals.minefactoryreloaded.gui.client;

import cpw.mods.fml.common.network.PacketDispatcher;
import powercrystals.core.net.PacketWrapper;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;
import powercrystals.minefactoryreloaded.net.Packets;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoDisenchanter;
import net.minecraft.client.gui.GuiButton;

public class GuiAutoDisenchanter extends GuiFactoryPowered
{
	private TileEntityAutoDisenchanter _disenchanter;
	
	private GuiButton _repeatToggle;

	public GuiAutoDisenchanter(ContainerFactoryPowered container, TileEntityAutoDisenchanter te)
	{
		super(container, te);
		_disenchanter = te;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		super.initGui();
		_repeatToggle = new GuiButton(1, (this.width - this.xSize) / 2 + 63, (this.height - this.ySize) / 2 + 23, 70, 20, "Repeat: No");
		buttonList.add(_repeatToggle);
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		_repeatToggle.displayString = _disenchanter.getRepeatDisenchant() ? "Repeat: Yes" : "Repeat: No";
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button.id == 1)
		{
			_disenchanter.setRepeatDisenchant(!_disenchanter.getRepeatDisenchant());
			PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modNetworkChannel, Packets.EnchanterButton,
					new Object[] { _disenchanter.xCoord, _disenchanter.yCoord, _disenchanter.zCoord, _disenchanter.getRepeatDisenchant() ? 1 : 0 }));
		}
	}
}
