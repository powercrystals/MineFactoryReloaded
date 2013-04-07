package powercrystals.minefactoryreloaded.api.rednet;

public interface IRedNetLogicCircuit
{
	public int getInputCount();
	
	public int getOutputCount();
	
	public int[] recalculateOutputValues(long worldTime, int[] inputValues);
}
