package powercrystals.minefactoryreloaded.circuits;

import powercrystals.minefactoryreloaded.api.rednet.IRedNetLogicCircuit;
import powercrystals.minefactoryreloaded.circuits.base.StatelessCircuit;

public class Mux4 extends StatelessCircuit implements IRedNetLogicCircuit
{
	@Override
	public int getInputCount()
	{
		return 3;
	}

	@Override
	public int getOutputCount()
	{
		return 4;
	}

	@Override
	public int[] recalculateOutputValues(long worldTime, int[] inputValues)
	{
		int channel = 0;
		if(inputValues[1] > 0 && inputValues[2] == 0) channel = 1;
		else if(inputValues[1] > 0 && inputValues[2] == 0) channel = 2;
		else if(inputValues[1] > 0 && inputValues[2] > 0) channel = 3;
		int[] output = new int[4];
		output[channel] = inputValues[0];
		
		return output;
	}

	@Override
	public String getUnlocalizedName()
	{
		return "circuit.mfr.mux.4";
	}

	@Override
	public String getInputPinLabel(int pin)
	{
		return pin == 0 ? "Z" : pin == 1 ? "S0" : "S1";
	}

	@Override
	public String getOutputPinLabel(int pin)
	{
		return "O" + pin;
	}

}
