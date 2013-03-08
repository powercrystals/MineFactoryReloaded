package powercrystals.minefactoryreloaded.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.animals.TileEntityAutoSpawner;
import powercrystals.minefactoryreloaded.animals.TileEntityChronotyper;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryInventory;
import powercrystals.minefactoryreloaded.core.TileEntityFactoryPowered;
import powercrystals.minefactoryreloaded.decorative.TileEntityAutoJukebox;
import powercrystals.minefactoryreloaded.gui.client.GuiAutoEnchanter;
import powercrystals.minefactoryreloaded.gui.client.GuiAutoJukebox;
import powercrystals.minefactoryreloaded.gui.client.GuiAutoSpawner;
import powercrystals.minefactoryreloaded.gui.client.GuiBioFuelGenerator;
import powercrystals.minefactoryreloaded.gui.client.GuiBioReactor;
import powercrystals.minefactoryreloaded.gui.client.GuiChronotyper;
import powercrystals.minefactoryreloaded.gui.client.GuiDeepStorageUnit;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryPowered;
import powercrystals.minefactoryreloaded.gui.client.GuiHarvester;
import powercrystals.minefactoryreloaded.gui.client.GuiItemRouter;
import powercrystals.minefactoryreloaded.gui.client.GuiLiquiCrafter;
import powercrystals.minefactoryreloaded.gui.client.GuiLiquidRouter;
import powercrystals.minefactoryreloaded.gui.client.GuiUpgradable;
import powercrystals.minefactoryreloaded.gui.container.ContainerAutoEnchanter;
import powercrystals.minefactoryreloaded.gui.container.ContainerAutoJukebox;
import powercrystals.minefactoryreloaded.gui.container.ContainerAutoSpawner;
import powercrystals.minefactoryreloaded.gui.container.ContainerBioFuelGenerator;
import powercrystals.minefactoryreloaded.gui.container.ContainerBioReactor;
import powercrystals.minefactoryreloaded.gui.container.ContainerChronotyper;
import powercrystals.minefactoryreloaded.gui.container.ContainerDeepStorageUnit;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;
import powercrystals.minefactoryreloaded.gui.container.ContainerHarvester;
import powercrystals.minefactoryreloaded.gui.container.ContainerItemRouter;
import powercrystals.minefactoryreloaded.gui.container.ContainerLiquiCrafter;
import powercrystals.minefactoryreloaded.gui.container.ContainerLiquidRouter;
import powercrystals.minefactoryreloaded.gui.container.ContainerUnifier;
import powercrystals.minefactoryreloaded.gui.container.ContainerUpgradable;
import powercrystals.minefactoryreloaded.plants.TileEntityFertilizer;
import powercrystals.minefactoryreloaded.plants.TileEntityHarvester;
import powercrystals.minefactoryreloaded.plants.TileEntityPlanter;
import powercrystals.minefactoryreloaded.power.TileEntityBioFuelGenerator;
import powercrystals.minefactoryreloaded.processing.TileEntityAutoEnchanter;
import powercrystals.minefactoryreloaded.processing.TileEntityBioReactor;
import powercrystals.minefactoryreloaded.processing.TileEntityDeepStorageUnit;
import powercrystals.minefactoryreloaded.processing.TileEntityLiquiCrafter;
import powercrystals.minefactoryreloaded.processing.TileEntityUnifier;
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
		else if(te instanceof TileEntityPlanter)
		{
			return new ContainerUpgradable(((TileEntityPlanter)te), player.inventory);
		}
		else if(te instanceof TileEntityFertilizer)
		{
			return new ContainerUpgradable(((TileEntityFertilizer)te), player.inventory);
		}
		else if(te instanceof TileEntityChronotyper)
		{
			return new ContainerChronotyper(((TileEntityChronotyper)te), player.inventory);
		}
		else if(te instanceof TileEntityAutoSpawner)
		{
			return new ContainerAutoSpawner(((TileEntityAutoSpawner)te), player.inventory);
		}
		else if(te instanceof TileEntityBioReactor)
		{
			return new ContainerBioReactor(((TileEntityBioReactor)te), player.inventory);
		}
		else if(te instanceof TileEntityBioFuelGenerator)
		{
			return new ContainerBioFuelGenerator(((TileEntityBioFuelGenerator)te), player.inventory);
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
		else if(te instanceof TileEntityAutoJukebox)
		{
			return new ContainerAutoJukebox((TileEntityAutoJukebox)te, player.inventory);
		}
		else if(te instanceof TileEntityUnifier)
		{
			return new ContainerUnifier((TileEntityUnifier)te, player.inventory);
		}
		else if(te instanceof TileEntityFactoryPowered)
		{
			return new ContainerFactoryPowered((TileEntityFactoryPowered)te, player.inventory);
		}
		else if(te instanceof TileEntityFactoryInventory)
		{
			return new ContainerFactoryInventory((TileEntityFactoryInventory)te, player.inventory);
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
		else if(te instanceof TileEntityPlanter)
		{
			return new GuiUpgradable(new ContainerUpgradable((TileEntityPlanter)te, player.inventory), (TileEntityPlanter)te);
		}
		else if(te instanceof TileEntityFertilizer)
		{
			return new GuiUpgradable(new ContainerUpgradable((TileEntityFertilizer)te, player.inventory), (TileEntityFertilizer)te);
		}
		else if(te instanceof TileEntityAutoSpawner)
		{
			return new GuiAutoSpawner(new ContainerAutoSpawner((TileEntityAutoSpawner)te, player.inventory), (TileEntityAutoSpawner)te);
		}
		else if(te instanceof TileEntityBioReactor)
		{
			return new GuiBioReactor(new ContainerBioReactor((TileEntityBioReactor)te, player.inventory), (TileEntityBioReactor)te);
		}
		else if(te instanceof TileEntityBioFuelGenerator)
		{
			return new GuiBioFuelGenerator(new ContainerBioFuelGenerator((TileEntityBioFuelGenerator)te, player.inventory), (TileEntityBioFuelGenerator)te);
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
		else if(te instanceof TileEntityAutoJukebox)
		{
			return new GuiAutoJukebox(new ContainerAutoJukebox((TileEntityAutoJukebox)te, player.inventory), (TileEntityAutoJukebox)te);
		}
		else if(te instanceof TileEntityUnifier)
		{
			return new GuiFactoryInventory(new ContainerUnifier((TileEntityUnifier)te, player.inventory), (TileEntityUnifier)te);
		}
		else if(te instanceof TileEntityFactoryPowered)
		{
			return new GuiFactoryPowered(new ContainerFactoryPowered((TileEntityFactoryPowered)te, player.inventory), (TileEntityFactoryPowered)te);
		}
		else if(te instanceof TileEntityFactoryInventory)
		{
			return new GuiFactoryInventory(new ContainerFactoryInventory((TileEntityFactoryInventory)te, player.inventory), (TileEntityFactoryInventory)te);
		}
		return null;
	}
}
