package powercrystals.minefactoryreloaded.circuits;

import powercrystals.minefactoryreloaded.api.rednet.IRedNetLogicCircuit;

public class FlipFlopJK implements IRedNetLogicCircuit
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
		if(inputValues[0] > 0 && inputValues[1] == 0)
		{
			_value = true;
		}
		else if(inputValues[0] == 0 && inputValues[1] > 0)
		{
			_value = false;
		}
		else if(inputValues[0] > 0 && inputValues[1] > 0)
		{
			_value = !_value;
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
		return "circuit.mfr.flipflop.jk";
	}

	@Override
	public String getInputPinLabel(int pin)
	{
		return pin == 0 ? "J" : "K";
	}

	@Override
	public String getOutputPinLabel(int pin)
	{
		return pin == 0 ? "Q" : "Q#";
	}
}
