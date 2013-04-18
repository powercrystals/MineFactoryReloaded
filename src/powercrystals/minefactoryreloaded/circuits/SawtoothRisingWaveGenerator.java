package powercrystals.minefactoryreloaded.circuits;

import powercrystals.minefactoryreloaded.api.rednet.IRedNetLogicCircuit;
import powercrystals.minefactoryreloaded.circuits.base.StatelessCircuit;

public class SawtoothRisingWaveGenerator extends StatelessCircuit implements IRedNetLogicCircuit
{
	@Override
	public int getInputCount()
	{
		return 0;
	}

	@Override
	public int getOutputCount()
	{
		return 1;
	}

	@Override
	public int[] recalculateOutputValues(long worldTime, int[] inputValues)
	{
		return new int[] { (int)(worldTime % 16) };
	}

	@Override
	public String getUnlocalizedName()
	{
		return "circuit.mfr.wavegenerator.sawtooth.rising";
	}

	@Override
	public String getInputPinLabel(int pin)
	{
		return "";
	}

	@Override
	public String getOutputPinLabel(int pin)
	{
		return "Q";
	}
}
