package powercrystals.minefactoryreloaded.circuits;

import powercrystals.minefactoryreloaded.api.rednet.IRedNetLogicCircuit;

public class AdderDigitalHalf implements IRedNetLogicCircuit
{
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
		int c = (inputValues[0] > 0 && inputValues[1] > 0) ? 15 : 0;
		int s = (inputValues[0] > 0 ^ inputValues[1] > 0) ? 15 : 0;
		return new int[] { s, c };
	}

	@Override
	public String getUnlocalizedName()
	{
		return "circuit.mfr.adder.digital.half";
	}

	@Override
	public String getInputPinLabel(int pin)
	{
		return pin == 0 ? "A" : "B";
	}

	@Override
	public String getOutputPinLabel(int pin)
	{
		return pin == 0 ? "S" : "C";
	}
}
