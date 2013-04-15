package powercrystals.minefactoryreloaded.tile;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import org.bouncycastle.util.Arrays;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import powercrystals.core.net.PacketWrapper;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.rednet.IRedNetLogicCircuit;
import powercrystals.minefactoryreloaded.circuits.Noop;
import powercrystals.minefactoryreloaded.net.Packets;

public class TileEntityRedNetLogic extends TileEntity
{
	public class PinMapping
	{
		public PinMapping(int pin, int buffer)
		{
			this.pin = pin;
			this.buffer = buffer;
		}
		
		public int pin;
		public int buffer;
	}
	
	private IRedNetLogicCircuit[] _circuits;
	
	// 0-5 in, 6-11 out, 12 const, 13 var, 14 null
	private int[][] _buffers = new int[15][];
	
	private PinMapping[][] _pinMappingInputs = new PinMapping[_circuits.length][];
	private PinMapping[][] _pinMappingOutputs = new PinMapping[_circuits.length][];
	
	public TileEntityRedNetLogic()
	{
		// init I/O and constant buffers
		for(int i = 0; i < 13; i++)
		{
			_buffers[i] = new int[16];
		}
		// init variable buffer
		_buffers[13] = new int[getVariableBufferSize()];
		// init null buffer
		_buffers[14] = new int[1];
		
		_circuits = new IRedNetLogicCircuit[getCircuitCount()];
		
		for(int i = 0; i < _circuits.length; i++)
		{
			initCircuit(i, new Noop());
		}
	}
	
	private int getVariableBufferSize()
	{
		return 16;
	}
	
	private int getCircuitCount()
	{
		return 4;
	}
	
	public IRedNetLogicCircuit getCircuit(int index)
	{
		return _circuits[index];
	}
	
	public PinMapping getInputPinMapping(int circuitIndex, int pinIndex)
	{
		return _pinMappingInputs[circuitIndex][pinIndex];
	}
	
	public void setInputPinMapping(int circuitIndex, int pinIndex, int buffer, int pin)
	{
		_pinMappingInputs[circuitIndex][pinIndex] = new PinMapping(pin, buffer);
	}
	
	public PinMapping getOutputPinMapping(int circuitIndex, int pinIndex)
	{
		return _pinMappingOutputs[circuitIndex][pinIndex];
	}
	
	public void setOutputPinMapping(int circuitIndex, int pinIndex, int buffer, int pin)
	{
		_pinMappingOutputs[circuitIndex][pinIndex] = new PinMapping(pin, buffer);
	}
	
	private IRedNetLogicCircuit getNewCircuit(String className)
	{
		try
		{
			return (IRedNetLogicCircuit) Class.forName(className).newInstance();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return new Noop();
		}
	}
	
	public void initCircuit(int index, String circuitClassName)
	{
		initCircuit(index, getNewCircuit(circuitClassName));
	}
	
	private void initCircuit(int index, IRedNetLogicCircuit circuit)
	{
		_circuits[index] = circuit;
		_pinMappingInputs[index] = new PinMapping[_circuits[index].getInputCount()];
		_pinMappingOutputs[index] = new PinMapping[_circuits[index].getOutputCount()];
		for(int i = 0; i < _pinMappingInputs[index].length; i++)
		{
			_pinMappingInputs[index][i] = new PinMapping(0, 0);
		}
		for(int i = 0; i < _pinMappingOutputs[index].length; i++)
		{
			_pinMappingOutputs[index][i] = new PinMapping(6, 0);
		}
	}
	
	public void setCircuitFromPacket(DataInputStream packet)
	{
		try
		{
			int circuitIndex = packet.readInt();
			String circuitName = packet.readUTF();
			
			initCircuit(circuitIndex, circuitName);
			
			int inputCount = packet.readInt();
			for(int p = 0; p < inputCount; p++)
			{
				int buffer = packet.readInt();
				int pin = packet.readInt();
				_pinMappingInputs[circuitIndex][p] = new PinMapping(pin, buffer);
			}
			
			int outputCount = packet.readInt();
			for(int p = 0; p < outputCount; p++)
			{
				int buffer = packet.readInt();
				int pin = packet.readInt();
				_pinMappingOutputs[circuitIndex][p] = new PinMapping(pin, buffer);
			}
		}
		catch(IOException x)
		{
			x.printStackTrace();
		}
	}
	
	public void sendCircuitToPlayer(int circuit, EntityPlayer player)
	{
		List<Object> data = new ArrayList<Object>();
		
		data.add(xCoord);
		data.add(yCoord);
		data.add(zCoord);
		
		data.add(circuit);

		data.add(_circuits[circuit].getClass().getName());
		data.add(_circuits[circuit].getInputCount());
		for(int p = 0; p < _pinMappingInputs[circuit].length; p++)
		{
			data.add(_pinMappingInputs[circuit][p].buffer);
			data.add(_pinMappingInputs[circuit][p].pin);
		}
		data.add(_circuits[circuit].getOutputCount());
		for(int p = 0; p < _pinMappingOutputs[circuit].length; p++)
		{
			data.add(_pinMappingOutputs[circuit][p].buffer);
			data.add(_pinMappingOutputs[circuit][p].pin);
		}
		
		PacketDispatcher.sendPacketToPlayer(PacketWrapper.createPacket(MineFactoryReloadedCore.modNetworkChannel, Packets.LogicCircuitDefinition, data.toArray()), (Player)player);
	}
	
	@Override
	public void updateEntity()
	{
		if(worldObj.isRemote)
		{
			return;
		}
		
		int[] lastOuput = Arrays.clone(_buffers[2]);
		
		for(int circuitNum = 0; circuitNum < _circuits.length; circuitNum++)
		{	
			int[] input = new int[_circuits[circuitNum].getInputCount()];
			for(int pinNum = 0; pinNum < input.length; pinNum++)
			{
				input[pinNum] = _buffers[_pinMappingInputs[circuitNum][pinNum].buffer][_pinMappingInputs[circuitNum][pinNum].pin];
			}
			
			int[] output = _circuits[circuitNum].recalculateOutputValues(worldObj.getTotalWorldTime(), input);

			for(int pinNum = 0; pinNum < output.length; pinNum++)
			{
				_buffers[_pinMappingOutputs[circuitNum][pinNum].buffer][_pinMappingOutputs[circuitNum][pinNum].pin] = output[pinNum];
			}
		}
		
		if(!Arrays.areEqual(lastOuput, _buffers[2]))
		{
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, MineFactoryReloadedCore.rednetLogicBlock.blockID);
		}
	}
	
	public int getOutputValue(int subnet)
	{
		return _buffers[2][subnet];
	}
	
	public int[] getOutputValues()
	{
		return _buffers[2];
	}
	
	public void onInputsChanged(int[] values)
	{
		_buffers[0] = values;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		
		NBTTagList circuits = new NBTTagList();
		for(int c = 0; c < _circuits.length; c++)
		{
			NBTTagCompound circuit = new NBTTagCompound();
			circuit.setString("circuit", _circuits[c].getClass().getName());
			
			NBTTagList inputPins = new NBTTagList();
			for(int p = 0; p < _pinMappingInputs[c].length; p++)
			{
				NBTTagCompound pin = new NBTTagCompound();
				pin.setInteger("buffer", _pinMappingInputs[c][p].buffer);
				pin.setInteger("pin", _pinMappingInputs[c][p].pin);
				
				inputPins.appendTag(pin);
			}
			circuit.setTag("inputPins", inputPins);
			
			NBTTagList outputPins = new NBTTagList();
			for(int p = 0; p < _pinMappingOutputs[c].length; p++)
			{
				NBTTagCompound pin = new NBTTagCompound();
				pin.setInteger("buffer", _pinMappingOutputs[c][p].buffer);
				pin.setInteger("pin", _pinMappingOutputs[c][p].pin);
				
				outputPins.appendTag(pin);
			}
			circuit.setTag("outputPins", outputPins);
			
			circuits.appendTag(circuit);
		}
		
		nbttagcompound.setTag("circuits", circuits);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		
		NBTTagList circuits = nbttagcompound.getTagList("circuits");
		if(circuits != null)
		{
			for(int c = 0; c < circuits.tagCount(); c++)
			{
				initCircuit(c, ((NBTTagCompound)circuits.tagAt(c)).getString("circuit"));
				
				NBTTagList inputPins = ((NBTTagCompound)circuits.tagAt(c)).getTagList("inputPins");
				if(inputPins != null)
				{
					for(int i = 0; i < inputPins.tagCount() && i < _pinMappingInputs[c].length; i++)
					{
						int pin = ((NBTTagCompound)inputPins.tagAt(i)).getInteger("pin");
						int buffer = ((NBTTagCompound)inputPins.tagAt(i)).getInteger("buffer");
						_pinMappingInputs[c][i] = new PinMapping(pin, buffer);
					}
				}
				
				NBTTagList outputPins = ((NBTTagCompound)circuits.tagAt(c)).getTagList("outputPins");
				if(outputPins != null)
				{
					for(int i = 0; i < outputPins.tagCount() && i < _pinMappingOutputs[c].length; i++)
					{
						int pin = ((NBTTagCompound)outputPins.tagAt(i)).getInteger("pin");
						int buffer = ((NBTTagCompound)outputPins.tagAt(i)).getInteger("buffer");
						_pinMappingOutputs[c][i] = new PinMapping(pin, buffer);
					}
				}
			}
		}
	}
	
	public int getLevelForSlot(int slot)
	{
		return Math.max(zCoord % 16, 2);
	}
	
	public int getRotation()
	{
		return 0;
	}
}
