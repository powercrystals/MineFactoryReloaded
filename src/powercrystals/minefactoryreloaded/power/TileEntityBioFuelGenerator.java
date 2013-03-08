package powercrystals.minefactoryreloaded.power;

import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;

public class TileEntityBioFuelGenerator extends TileEntityLiquidGenerator
{
	public TileEntityBioFuelGenerator()
	{
		super(12, 16, 0);
	}
	
	@Override
	protected LiquidStack getLiquidType()
	{
		return LiquidDictionary.getLiquid("biofuel", 1);
	}

	@Override
	public int getSizeInventory()
	{
		return 0;
	}

	@Override
	public String getInvName()
	{
		return "BioFuel Generator";
	}
	
	@Override
	public String getGuiBackground()
	{
		return "biofuelgenerator.png";
	}
}
