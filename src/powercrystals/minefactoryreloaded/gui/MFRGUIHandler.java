package powercrystals.minefactoryreloaded.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.animals.TileEntityChronotyper;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryInventory;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;
import powercrystals.minefactoryreloaded.plants.TileEntityHarvester;
import powercrystals.minefactoryreloaded.processing.TileEntityAutoEnchanter;
import powercrystals.minefactoryreloaded.processing.TileEntityDeepStorageUnit;
import powercrystals.minefactoryreloaded.processing.TileEntityLiquiCrafter;
import powercrystals.minefactoryreloaded.transport.TileEntityItemRouter;
import powercrystals.minefactoryreloaded.transport.TileEntityLiquidRouter;
import cpw.mods.fml.common.network.IGuiHandler;

public class MFRGUIHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te instanceof TileEntityAutoEnchanter)
		{
			return new ContainerAutoEnchanter(((TileEntityAutoEnchanter)te), player.inventory);
		}
		else if(te instanceof TileEntityHarvester)
		{
			return new ContainerHarvester(((TileEntityHarvester)te), player.inventory);
		}
		else if(te instanceof TileEntityChronotyper)
		{
			return new ContainerChronotyper(((TileEntityChronotyper)te), player.inventory);
		}
		else if(te instanceof TileEntityFactoryInventory)
		{
			return new ContainerFactoryInventory((TileEntityFactoryInventory)te, player.inventory);
		}
		else if(te instanceof TileEntityFactoryPowered)
		{
			return new ContainerFactoryPowered((TileEntityFactoryPowered)te, player.inventory);
		}
		else if(te instanceof TileEntityItemRouter)
		{
			return new ContainerItemRouter((TileEntityItemRouter)te, player.inventory);
		}
		else if(te instanceof TileEntityLiquidRouter)
		{
			return new ContainerLiquidRouter((TileEntityLiquidRouter)te, player.inventory);
		}
		else if(te instanceof TileEntityDeepStorageUnit)
		{
			return new ContainerDeepStorageUnit((TileEntityDeepStorageUnit)te, player.inventory);
		}
		else if(te instanceof TileEntityLiquiCrafter)
		{
			return new ContainerLiquiCrafter((TileEntityLiquiCrafter)te, player.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te instanceof TileEntityAutoEnchanter)
		{
			return new GuiAutoEnchanter(new ContainerAutoEnchanter((TileEntityAutoEnchanter)te, player.inventory), (TileEntityAutoEnchanter)te);
		}
		else if(te instanceof TileEntityChronotyper)
		{
			return new GuiChronotyper(new ContainerChronotyper((TileEntityChronotyper)te, player.inventory), (TileEntityChronotyper)te);
		}
		else if(te instanceof TileEntityHarvester)
		{
			return new GuiHarvester(new ContainerHarvester((TileEntityHarvester)te, player.inventory), (TileEntityHarvester)te);
		}
		else if(te instanceof TileEntityFactoryInventory)
		{
			return new GuiFactoryInventory(new ContainerFactoryInventory((TileEntityFactoryInventory)te, player.inventory), (TileEntityFactoryInventory)te);
		}
		else if(te instanceof TileEntityFactoryPowered)
		{
			return new GuiFactoryPowered(new ContainerFactoryPowered((TileEntityFactoryPowered)te, player.inventory), (TileEntityFactoryPowered)te);
		}
		else if(te instanceof TileEntityItemRouter)
		{
			return new GuiItemRouter(new ContainerItemRouter((TileEntityItemRouter)te, player.inventory), (TileEntityItemRouter)te);
		}
		else if(te instanceof TileEntityLiquidRouter)
		{
			return new GuiLiquidRouter(new ContainerLiquidRouter((TileEntityLiquidRouter)te, player.inventory), (TileEntityLiquidRouter)te);
		}
		else if(te instanceof TileEntityDeepStorageUnit)
		{
			return new GuiDeepStorageUnit(new ContainerDeepStorageUnit((TileEntityDeepStorageUnit)te, player.inventory), (TileEntityDeepStorageUnit)te);
		}
		else if(te instanceof TileEntityLiquiCrafter)
		{
			return new GuiLiquiCrafter(new ContainerLiquiCrafter((TileEntityLiquiCrafter)te, player.inventory), (TileEntityLiquiCrafter)te);
		}
		return null;
	}
}
