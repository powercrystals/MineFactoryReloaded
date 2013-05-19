package powercrystals.minefactoryreloaded.gui.client;

import net.minecraft.client.gui.GuiButton;
import powercrystals.core.net.PacketWrapper;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryInventory;
import powercrystals.minefactoryreloaded.net.Packets;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityEnchantmentRouter;
import cpw.mods.fml.common.network.PacketDispatcher;

public class GuiEnchantmentRouter extends GuiFactoryInventory
{
	private TileEntityEnchantmentRouter _router;
	
	private GuiButton _matchLevels;
	
	public GuiEnchantmentRouter(ContainerFactoryInventory container, TileEntityEnchantmentRouter router)
	{
		super(container, router);
		_router = router;
		ySize = 225;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		super.initGui();
		
		int xOffset = (this.width - this.xSize) / 2;
		int yOffset = (this.height - this.ySize) / 2;
		
		_matchLevels =  new GuiButton(1, xOffset + 7,  yOffset + 15, 100, 20, "Match Levels: NO");
		
		buttonList.add(_matchLevels);
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		_matchLevels.displayString  = _router.getMatchLevels() ? "Match Levels: YES" : "Match Levels: NO";
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button.id == 1)
		{
			PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modNetworkChannel, Packets.RouterButton,
					new Object[] { _router.xCoord, _router.yCoord, _router.zCoord }));
		}
	}
}
