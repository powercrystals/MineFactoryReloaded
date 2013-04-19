package powercrystals.minefactoryreloaded.circuits.analog;

import java.util.Random;

import powercrystals.minefactoryreloaded.api.rednet.IRedNetLogicCircuit;
import powercrystals.minefactoryreloaded.circuits.base.StatelessCircuit;

public class RandomizerAnalog extends StatelessCircuit implements IRedNetLogicCircuit
{
	private Random _rand;
	
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
		if(_rand == null)
		{
			_rand = new Random(worldTime);
		}

		return new int[] { _rand.nextInt(16) };
	}

	@Override
	public String getUnlocalizedName()
	{
		return "circuit.mfr.randomizer.analog";
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