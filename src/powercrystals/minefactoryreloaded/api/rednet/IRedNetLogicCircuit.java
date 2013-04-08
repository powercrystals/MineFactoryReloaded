package powercrystals.minefactoryreloaded.api.rednet;

public interface IRedNetLogicCircuit
{
	public int getInputCount();
	
	public int getOutputCount();
	
	public int[] recalculateOutputValues(long worldTime, int[] inputValues);
	
	public String getUnlocalizedName();
	public String getInputPinLabel(int pin);
	public String getOutputPinLabel(int pin);
}
