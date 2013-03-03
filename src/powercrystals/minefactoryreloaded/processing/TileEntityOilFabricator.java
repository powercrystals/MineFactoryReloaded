package powercrystals.minefactoryreloaded.processing;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class TileEntityOilFabricator extends TileEntityLiquidFabricator
{
	public TileEntityOilFabricator()
	{
		super(MineFactoryReloadedCore.oilLiquidId, 5880, 1);
	}
	
	@Override
	public String getGuiBackground()
	{
		return "oilfab.png";
	}

	@Override
	public String getInvName()
	{
		return "Oil Fabricator";
	}

}
