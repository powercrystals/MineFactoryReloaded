package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.liquids.LiquidDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryPowered;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;
import powercrystals.minefactoryreloaded.tile.base.TileEntityLiquidFabricator;

public class TileEntityOilFabricator extends TileEntityLiquidFabricator
{
	public TileEntityOilFabricator()
	{
		super((Integer)LiquidDictionary.getCanonicalLiquid("oil").itemID == null ? Block.waterStill.blockID : LiquidDictionary.getCanonicalLiquid("oil").itemID, 5880, 1);
	}
	
	@Override
	public String getGuiBackground()
	{
		return "oilfab.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiFactoryPowered(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerFactoryPowered getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerFactoryPowered(this, inventoryPlayer);
	}

	@Override
	public String getInvName()
	{
		return "Oil Fabricator";
	}

}
