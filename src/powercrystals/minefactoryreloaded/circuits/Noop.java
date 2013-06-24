package powercrystals.minefactoryreloaded.circuits;

import powercrystals.minefactoryreloaded.circuits.base.StatelessCircuit;

public class Noop extends StatelessCircuit
{
	@Override
	public int getInputCount()
	{
		return 0;
	}
	
	@Override
	public int getOutputCount()
	{
		return 0;
	}
	
	@Override
	public int[] recalculateOutputValues(long worldTime, int[] inputValues)
	{
		return new int[0];
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return "circuit.mfr.noop";
	}
	
	@Override
	public String getInputPinLabel(int pin)
	{
		return ((Integer)pin).toString();
	}
	
	@Override
	public String getOutputPinLabel(int pin)
	{
		return ((Integer)pin).toString();
	}
}
