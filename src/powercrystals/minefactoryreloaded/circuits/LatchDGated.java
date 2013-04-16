package powercrystals.minefactoryreloaded.circuits;

import powercrystals.minefactoryreloaded.api.rednet.IRedNetLogicCircuit;

public class LatchDGated implements IRedNetLogicCircuit
{
	private boolean _value;
	
	@Override
	public int getInputCount()
	{
		return 2;
	}

	@Override
	public int getOutputCount()
	{
		return 2;
	}

	@Override
	public int[] recalculateOutputValues(long worldTime, int[] inputValues)
	{
		if(inputValues[1] > 0)
		{
			_value = (inputValues[0] > 0);
		}
		
		if(_value)
		{
			return new int[] { 15, 0 };
		}
		else
		{
			return new int[] { 0, 15 };
		}
	}

	@Override
	public String getUnlocalizedName()
	{
		return "circuit.mfr.latch.d.gated";
	}

	@Override
	public String getInputPinLabel(int pin)
	{
		return pin == 0 ? "D" : "E";
	}

	@Override
	public String getOutputPinLabel(int pin)
	{
		return pin == 0 ? "Q" : "Q#";
	}
}
