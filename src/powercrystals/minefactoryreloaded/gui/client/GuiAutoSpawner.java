package powercrystals.minefactoryreloaded.gui.client;

import net.minecraft.client.gui.GuiButton;
import powercrystals.core.net.PacketWrapper;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.container.ContainerAutoSpawner;
import powercrystals.minefactoryreloaded.net.Packets;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoSpawner;
import cpw.mods.fml.common.network.PacketDispatcher;

public class GuiAutoSpawner extends GuiFactoryPowered
{
	private GuiButton _toggle;
	
	public GuiAutoSpawner(ContainerAutoSpawner container, TileEntityAutoSpawner te)
	{
		super(container, te);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		super.initGui();
		
		int xOffset = (this.width - this.xSize) / 2;
		int yOffset = (this.height - this.ySize) / 2;
		
		_toggle = new GuiButton(1, xOffset + 7, yOffset + 44, 115, 20, "Spawn Exact Copy: ");
		
		buttonList.add(_toggle);
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		_toggle.displayString = "Spawn Exact Copy: " + (((TileEntityAutoSpawner)_tileEntity).getSpawnExact() ? "Yes" : "No");
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button.id == 1)
		{
			PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modNetworkChannel, Packets.AutoSpawnerButton,
					new Object[] { _tileEntity.xCoord, _tileEntity.yCoord, _tileEntity.zCoord }));
		}
	}
}
