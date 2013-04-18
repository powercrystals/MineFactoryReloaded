package powercrystals.minefactoryreloaded.circuits;

import net.minecraft.nbt.NBTTagCompound;
import powercrystals.minefactoryreloaded.api.rednet.IRedNetLogicCircuit;

public class SquareWaveGenerator implements IRedNetLogicCircuit
{
	private boolean _value;
	private int _period = 4;
	
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
		if(worldTime % _period == 0)
		{
			_value = !_value;
		}
		return new int[] { _value ? 15 : 0 };
	}

	@Override
	public String getUnlocalizedName()
	{
		return "circuit.mfr.wavegenerator.square";
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

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		_value = tag.getBoolean("state");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setBoolean("state", _value);
	}
}
