package powercrystals.minefactoryreloaded.circuits.digital;

import net.minecraft.nbt.NBTTagCompound;
import powercrystals.minefactoryreloaded.api.rednet.IRedNetLogicCircuit;

public class Counter implements IRedNetLogicCircuit
{
	private int _count;
	private int _preset;
	
	private boolean _lastIncrementState;
	private boolean _lastDecrementState;
	
	@Override
	public int getInputCount()
	{
		return 3;
	}
	
	@Override
	public int getOutputCount()
	{
		return 2;
	}
	
	@Override
	public int[] recalculateOutputValues(long worldTime, int[] inputValues)
	{
		_preset = inputValues[2];
		
		if(inputValues[0] > 0 && !_lastIncrementState)
		{
			_count++;
		}
		else if(inputValues[1] > 0 && !_lastDecrementState)
		{
			_count--;
		}
		
		_lastIncrementState = inputValues[0] > 0;
		_lastDecrementState = inputValues[1] > 0;
		
		
		if(_count >= _preset)
		{
			_count = 0;
			return new int[] { 15, _count };
		}
		else if(_count < 0)
		{
			_count = _preset - 1;
			return new int[] { 15, _count };
		}
		return new int[] { 0, _count };
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return "circuit.mfr.counter";
	}
	
	@Override
	public String getInputPinLabel(int pin)
	{
		return pin == 0 ? "INC" : pin == 1 ? "DEC" : "PRE";
	}
	
	@Override
	public String getOutputPinLabel(int pin)
	{
		return pin == 0 ? "Q" : "V";
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		_count = tag.getInteger("count");
		_lastIncrementState = tag.getBoolean("lastIncrementState");
		_lastDecrementState = tag.getBoolean("lastDecrementState");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setInteger("count", _count);
		tag.setBoolean("lastIncrementState", _lastIncrementState);
		tag.setBoolean("lastDecrementState", _lastDecrementState);
	}
}
