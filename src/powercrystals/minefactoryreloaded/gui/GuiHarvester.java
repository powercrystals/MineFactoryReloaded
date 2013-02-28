package powercrystals.minefactoryreloaded.gui;

import net.minecraft.client.gui.GuiButton;
import cpw.mods.fml.common.network.PacketDispatcher;
import powercrystals.core.net.PacketWrapper;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.net.Packets;
import powercrystals.minefactoryreloaded.plants.TileEntityHarvester;

public class GuiHarvester extends GuiFactoryPowered
{
	private TileEntityHarvester _harvester;
	
	private GuiButton _settingSilkTouch;
	private GuiButton _settingSmallShrooms;
	private GuiButton _settingJungleWood;
	
	private static final String _silkTouchText = "Shear Leaves: ";
	private static final String _smallShroomsText = "Small Shrooms: ";
	private static final String _jungleWoodText = "Jungle Wood: ";
	
	public GuiHarvester(ContainerHarvester container, TileEntityHarvester te)
	{
		super(container, te);
		_harvester = te;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		super.initGui();
		
		int xOffset = (this.width - this.xSize) / 2;
		int yOffset = (this.height - this.ySize) / 2;
		
		_settingSilkTouch = new GuiButton(1, xOffset + 7, yOffset + 14, 110, 20, _silkTouchText);
		_settingSmallShrooms = new GuiButton(2, xOffset + 7, yOffset + 34, 110, 20, _smallShroomsText);
		_settingJungleWood = new GuiButton(3, xOffset + 7, yOffset + 54, 110, 20, _jungleWoodText);
		
		controlList.add(_settingSilkTouch);
		controlList.add(_settingSmallShrooms);
		controlList.add(_settingJungleWood);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		fontRenderer.drawString(_tePowered.getInvName(), 8, 6, 4210752);
		
		drawBars();
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		_settingSilkTouch.displayString = _silkTouchText + getSettingText("silkTouch");
		_settingSmallShrooms.displayString = _smallShroomsText + getSettingText("harvestSmallMushrooms");
		_settingJungleWood.displayString = _jungleWoodText + getSettingText("harvestJungleWood");
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button.id == 1)
		{
			PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modId, Packets.HarvesterButton,
					new Object[] { _harvester.xCoord, _harvester.yCoord, _harvester.zCoord, "silkTouch", getNewSettingValue("silkTouch") }));
		}
		else if(button.id == 2)
		{
			PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modId, Packets.HarvesterButton,
					new Object[] { _harvester.xCoord, _harvester.yCoord, _harvester.zCoord, "harvestSmallMushrooms", getNewSettingValue("harvestSmallMushrooms") }));
		}
		else if(button.id == 3)
		{
			PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modId, Packets.HarvesterButton,
					new Object[] { _harvester.xCoord, _harvester.yCoord, _harvester.zCoord, "harvestJungleWood", getNewSettingValue("harvestJungleWood") }));
		}
	}
	
	private String getSettingText(String setting)
	{
		if(_harvester.getSettings().get(setting) == null) return "No";
		return _harvester.getSettings().get(setting) ? "Yes" : "No";
	}
	
	private Boolean getNewSettingValue(String setting)
	{
		if(_harvester.getSettings().get(setting) == null) return true;
		return _harvester.getSettings().get(setting) ? false : true;
	}
}
