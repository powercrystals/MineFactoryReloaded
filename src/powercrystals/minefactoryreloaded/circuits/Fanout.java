package powercrystals.minefactoryreloaded.circuits;

import powercrystals.minefactoryreloaded.api.rednet.IRedNetLogicCircuit;

public class Fanout implements IRedNetLogicCircuit
{
	@Override
	public int getInputCount()
	{
		return 1;
	}

	@Override
	public int getOutputCount()
	{
		return 8;
	}

	@Override
	public int[] recalculateOutputValues(long worldTime, int[] inputValues)
	{
		return new int[] { inputValues[0], inputValues[0], inputValues[0], inputValues[0], inputValues[0], inputValues[0], inputValues[0], inputValues[0] };
	}

	@Override
	public String getUnlocalizedName()
	{
		return "circuit.mfr.fanout";
	}

	@Override
	public String getInputPinLabel(int pin)
	{
		return "I" + pin;
	}

	@Override
	public String getOutputPinLabel(int pin)
	{
		return "O" + pin;
	}
}
