package powercrystals.minefactoryreloaded.gui;

import net.minecraft.client.gui.GuiButton;
import cpw.mods.fml.common.network.PacketDispatcher;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.animals.TileEntityChronotyper;
import powercrystals.minefactoryreloaded.net.PacketWrapper;

public class GuiChronotyper extends GuiFactoryPowered
{
	private GuiButton _ageToggle;
	private TileEntityChronotyper _chronotyper;
	
	public GuiChronotyper(ContainerFactoryPowered container, TileEntityChronotyper te)
	{
		super(container, te);
		_chronotyper = te;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		super.initGui();

		int xOffset = (this.width - this.xSize) / 2;
		int yOffset = (this.height - this.ySize) / 2;
		
		_ageToggle = new GuiButton(1, xOffset + 7, yOffset + 14, 110, 20, "Moving: ");
		
		controlList.add(_ageToggle);
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		_ageToggle.displayString = "Moving: " + (((TileEntityChronotyper)_chronotyper).getMoveOld() ? "Adults" : "Babies");
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button.id == 1)
		{
			PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modId, PacketWrapper.PacketIdChronotyperButton,
					new Object[] { _chronotyper.xCoord, _chronotyper.yCoord, _chronotyper.zCoord }));
		}
	}
}
