package powercrystals.minefactoryreloaded.circuits;

import powercrystals.minefactoryreloaded.api.rednet.IRedNetLogicCircuit;

public class Greater implements IRedNetLogicCircuit
{
	@Override
	public int getInputCount()
	{
		return 2;
	}

	@Override
	public int getOutputCount()
	{
		return 1;
	}

	@Override
	public int[] recalculateOutputValues(long worldTime, int[] inputValues)
	{
		return new int[] { inputValues[0] > inputValues[1] ? 15 : 0 };
	}

	@Override
	public String getUnlocalizedName()
	{
		return "circuit.mfr.boolean.greater";
	}

	@Override
	public String getInputPinLabel(int pin)
	{
		return pin == 0 ? "A" : "B";
	}

	@Override
	public String getOutputPinLabel(int pin)
	{
		return "Q";
	}
}
