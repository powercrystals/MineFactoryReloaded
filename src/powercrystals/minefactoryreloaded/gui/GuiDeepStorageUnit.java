package powercrystals.minefactoryreloaded.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;

import powercrystals.core.net.PacketWrapper;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.net.Packets;
import powercrystals.minefactoryreloaded.processing.TileEntityDeepStorageUnit;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.StatCollector;

public class GuiDeepStorageUnit extends GuiContainer
{
	private TileEntityDeepStorageUnit _dsu;

	private GuiButton _dirDown;
	private GuiButton _dirUp;
	private GuiButton _dirNorth;
	private GuiButton _dirSouth;
	private GuiButton _dirWest;
	private GuiButton _dirEast;
	
	public GuiDeepStorageUnit(Container container, TileEntityDeepStorageUnit dsu)
	{
		super(container);
		_dsu = dsu;
		ySize = 205;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		super.initGui();

		int xOffset = (this.width - this.xSize) / 2;
		int yOffset = (this.height - this.ySize) / 2;
		
		_dirDown =  new GuiButton(1, xOffset + 7,  yOffset + 80, 30, 20, "OUT");
		_dirUp =    new GuiButton(2, xOffset + 7,  yOffset + 40, 30, 20, "OUT");
		_dirNorth = new GuiButton(3, xOffset + 37, yOffset + 40, 30, 20, "OUT");
		_dirSouth = new GuiButton(4, xOffset + 37, yOffset + 80, 30, 20, "OUT");
		_dirWest =  new GuiButton(5, xOffset + 67, yOffset + 80, 30, 20, "OUT");
		_dirEast =  new GuiButton(6, xOffset + 67, yOffset + 40, 30, 20, "OUT");
		
		controlList.add(_dirDown);
		controlList.add(_dirUp);
		controlList.add(_dirNorth);
		controlList.add(_dirSouth);
		controlList.add(_dirWest);
		controlList.add(_dirEast);
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		_dirDown.displayString  = _dsu.getIsSideOutput(0) ? "OUT" : "IN";
		_dirUp.displayString    = _dsu.getIsSideOutput(1) ? "OUT" : "IN";
		_dirNorth.displayString = _dsu.getIsSideOutput(2) ? "OUT" : "IN";
		_dirSouth.displayString = _dsu.getIsSideOutput(3) ? "OUT" : "IN";
		_dirWest.displayString  = _dsu.getIsSideOutput(4) ? "OUT" : "IN";
		_dirEast.displayString  = _dsu.getIsSideOutput(5) ? "OUT" : "IN";
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		fontRenderer.drawString(_dsu.getInvName(), 8, 6, 4210752);
		fontRenderer.drawString("IN", 151, 6, 4210752);
		fontRenderer.drawString("OUT", 151, 36, 4210752);
		
		fontRenderer.drawString("Up", 8, 30, 4210752);
		fontRenderer.drawString("North", 38, 30, 4210752);
		fontRenderer.drawString("East", 68, 30, 4210752);
		
		fontRenderer.drawString("Down", 8, 70, 4210752);
		fontRenderer.drawString("South", 38, 70, 4210752);
		fontRenderer.drawString("West", 68, 70, 4210752);
		
		fontRenderer.drawString("Stored:", 110, 70, 4210752);
		fontRenderer.drawString(((Integer)_dsu.getQuantity()).toString(), 110, 80, 4210752);
		
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);	
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		int texture = mc.renderEngine.getTexture(MineFactoryReloadedCore.guiFolder + "dsu.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button.id > 0 && button.id <= 6)
		{
			PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modId, Packets.DSUButton,
					new Object[] { _dsu.xCoord, _dsu.yCoord, _dsu.zCoord, button.id - 1 }));
		}
	}
}
