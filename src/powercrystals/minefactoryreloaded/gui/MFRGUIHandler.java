package powercrystals.minefactoryreloaded.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.gui.client.GuiRedNetLogic;
import powercrystals.minefactoryreloaded.gui.container.ContainerRedNetLogic;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactory;
import powercrystals.minefactoryreloaded.tile.rednet.TileEntityRedNetLogic;
import cpw.mods.fml.common.network.IGuiHandler;

public class MFRGUIHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
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
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
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
		return null;
	}
}
