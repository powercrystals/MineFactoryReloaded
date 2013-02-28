package powercrystals.minefactoryreloaded.gui;

import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;

import powercrystals.core.net.PacketWrapper;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.net.Packets;
import powercrystals.minefactoryreloaded.processing.TileEntityAutoEnchanter;

public class GuiAutoEnchanter extends GuiFactoryInventory
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
		controlList.add(_inc);
		controlList.add(_dec);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		fontRenderer.drawString(new Integer(_enchanter.getTargetLevel()).toString(), 68, 44, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		int texture = mc.renderEngine.getTexture(MineFactoryReloadedCore.guiFolder + "autoenchanter.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
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
				PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modId, Packets.EnchanterButton,
						new Object[] { _enchanter.xCoord, _enchanter.yCoord, _enchanter.zCoord, 1 }));
			}
		}
		else
		{
			if(_enchanter.getTargetLevel() > 1)
			{
				_enchanter.setTargetLevel(_enchanter.getTargetLevel() - 1);
				PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modId, Packets.EnchanterButton,
						new Object[] { _enchanter.xCoord, _enchanter.yCoord, _enchanter.zCoord, -1 }));
			}
		}
	}
}
