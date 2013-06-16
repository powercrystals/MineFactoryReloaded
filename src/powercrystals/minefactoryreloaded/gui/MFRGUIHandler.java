package powercrystals.minefactoryreloaded.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.gui.client.GuiNeedlegun;
import powercrystals.minefactoryreloaded.gui.client.GuiRedNetLogic;
import powercrystals.minefactoryreloaded.gui.container.ContainerNeedlegun;
import powercrystals.minefactoryreloaded.gui.container.ContainerRedNetLogic;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactory;
import powercrystals.minefactoryreloaded.tile.rednet.TileEntityRedNetLogic;
import cpw.mods.fml.common.network.IGuiHandler;

public class MFRGUIHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == 0)
		{
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if(te instanceof TileEntityFactory)
			{
				return ((TileEntityFactory)te).getContainer(player.inventory);
			}
			else if(te instanceof TileEntityRedNetLogic)
			{
				return new ContainerRedNetLogic((TileEntityRedNetLogic)te);
			}
		}
		else if(ID == 1)
		{
			if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().itemID == MineFactoryReloadedCore.needlegunItem.itemID)
			{
				return new ContainerNeedlegun(new NeedlegunContainerWrapper(player.getCurrentEquippedItem()), player.inventory);
			}
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if(ID == 0)
		{
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if(te instanceof TileEntityFactory)
			{
				return ((TileEntityFactory)te).getGui(player.inventory);
			}
			else if(te instanceof TileEntityRedNetLogic)
			{
				return new GuiRedNetLogic(new ContainerRedNetLogic((TileEntityRedNetLogic)te), (TileEntityRedNetLogic)te);
			}
		}
		else if(ID == 1)
		{
			if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().itemID == MineFactoryReloadedCore.needlegunItem.itemID)
			{
				return new GuiNeedlegun(new ContainerNeedlegun(new NeedlegunContainerWrapper(player.getCurrentEquippedItem()), player.inventory));
			}
		}
		return null;
	}
}
