package powercrystals.minefactoryreloaded.api.rednet;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Defines a Block that can connect to RedNet wires. This must be implemented on your Block class.
 *
 */
public interface IConnectableRedNet
{
	/**
	 * Returns the connection type of this Block. "All" types will cause getOutputValues() and onInputChanged() to be used,
	 * whereas "Single" types will cause block updates for input changes and the normal redstone power output methods
	 * to be called. If this value must be changed while the block is alive, it must perform a block update on any adjacent RedNet wires.
	 * @param world The world this block is in.
	 * @param x This block's X coordinate.
	 * @param y This block's Y coordinate.
	 * @param z This block's Z coordinate.
	 * @param side The side that connection information is required for.
	 * @return The connection type.
	 */
	public RedNetConnectionType getConnectionType(World world, int x, int y, int z, ForgeDirection side);
	
	/**
	 * Returns the output values of this RedNet node. This array must be 16 elements long. Only called if your block is connected in "All" mode.
	 * @param world The world this block is in.
	 * @param x This block's X coordinate.
	 * @param y This block's Y coordinate.
	 * @param z This block's Z coordinate.
	 * @param side The side the output values are required for.
	 * @return The output values.
	 */
	public int[] getOutputValues(World world, int x, int y, int z, ForgeDirection side);
	
	/**
	 * Returns the output value of this RedNet node for a given subnet. Only called if your block is connected in "All" mode.
	 * @param world The world this block is in.
	 * @param x This block's X coordinate.
	 * @param y This block's Y coordinate.
	 * @param z This block's Z coordinate.
	 * @param side The side the output value is required for.
	 * @param subnet The subnet to get the output value for (0-15).
	 * @return The output value.
	 */
	public int getOutputValue(World world, int x, int y, int z, ForgeDirection side, int subnet);
	
	/**
	 * Called when the input values to this block change. Only called if your block is connected in "All" mode.
	 * @param world The world this block is in.
	 * @param x This block's X coordinate.
	 * @param y This block's Y coordinate.
	 * @param z This block's Z coordinate.
	 * @param side The side the input values are being changed on.
	 * @param inputValues The new set of input values. This array will be 16 elements long.
	 */
	public void onInputsChanged(World world, int x, int y, int z, ForgeDirection side, int[] inputValues);
	
	/**
	 * Called when the input value to this block changes. Only called if your block is connected in "Single" mode.
	 * @param world The world this block is in.
	 * @param x This block's X coordinate.
	 * @param y This block's Y coordinate.
	 * @param z This block's Z coordinate.
	 * @param side The side the input values are being changed on.
	 * @param inputValues The new set of input values. This array will be 16 elements long.
	 */
	public void onInputChanged(World world, int x, int y, int z, ForgeDirection side, int inputValue);
}
