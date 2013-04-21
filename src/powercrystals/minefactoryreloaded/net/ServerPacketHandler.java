package powercrystals.minefactoryreloaded.net;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

import powercrystals.core.net.PacketWrapper;
import powercrystals.minefactoryreloaded.tile.TileEntityRedNetLogic;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoEnchanter;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoJukebox;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoSpawner;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityChronotyper;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityDeepStorageUnit;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityHarvester;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ServerPacketHandler implements IPacketHandler
{
	@SuppressWarnings("rawtypes")
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		int packetType = PacketWrapper.readPacketID(data);
		
		if (packetType == Packets.EnchanterButton) // client -> server: autoenchanter GUI buttons
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, Integer.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if (te instanceof TileEntityAutoEnchanter)
			{
				((TileEntityAutoEnchanter)te).setTargetLevel(((TileEntityAutoEnchanter)te).getTargetLevel() + (Integer)packetReadout[3]);
			}
		}
		else if(packetType == Packets.HarvesterButton) // client -> server: harvester setting
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, String.class, Boolean.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if(te instanceof TileEntityHarvester)
			{
				((TileEntityHarvester)te).getSettings().put((String)packetReadout[3], (Boolean)packetReadout[4]);
			}
		}
		else if(packetType == Packets.ChronotyperButton) // client -> server: toggle chronotyper
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if(te instanceof TileEntityChronotyper)
			{
				((TileEntityChronotyper)te).setMoveOld(!((TileEntityChronotyper)te).getMoveOld());
			}
		}
		else if(packetType == Packets.DSUButton) // client -> server: toggle DSU output side
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, Integer.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if(te instanceof TileEntityDeepStorageUnit)
			{
				int side = (Integer)packetReadout[3];
				((TileEntityDeepStorageUnit)te).setSideIsOutput(side, (!((TileEntityDeepStorageUnit)te).getIsSideOutput(side)));
			}
		}
		else if(packetType == Packets.AutoJukeboxButton) // client -> server: copy record
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, Integer.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if(te instanceof TileEntityAutoJukebox)
			{
				TileEntityAutoJukebox j = ((TileEntityAutoJukebox)te);
				int button = (Integer)packetReadout[3];
				if(button == 1) j.playRecord();
				else if(button == 2) j.stopRecord();
				else if(button == 3) j.copyRecord();
			}
		}
		else if(packetType == Packets.AutoSpawnerButton) // client -> server: toggle autospawner
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if(te instanceof TileEntityAutoSpawner)
			{
				((TileEntityAutoSpawner)te).setSpawnExact(!((TileEntityAutoSpawner)te).getSpawnExact());
			}
		}
		else if(packetType == Packets.LogicRequestCircuitDefinition) // client -> server: request circuit from server
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, Integer.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if(te instanceof TileEntityRedNetLogic)
			{
				((TileEntityRedNetLogic)te).sendCircuitDefinition((Integer)packetReadout[3]);
			}
		}
		else if(packetType == Packets.LogicSetCircuit) // client -> server: set circuit
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, Integer.class, String.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if(te instanceof TileEntityRedNetLogic)
			{
				((TileEntityRedNetLogic)te).initCircuit((Integer)packetReadout[3], (String)packetReadout[4]);
				((TileEntityRedNetLogic)te).sendCircuitDefinition((Integer)packetReadout[3]);
			}
		}
		else if(packetType == Packets.LogicSetPin) // client -> server: set pin
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if(te instanceof TileEntityRedNetLogic)
			{
				if((Integer)packetReadout[3] == 0)
				{
					((TileEntityRedNetLogic)te).setInputPinMapping((Integer)packetReadout[4], (Integer)packetReadout[5], (Integer)packetReadout[6], (Integer)packetReadout[7]);
				}
				else if((Integer)packetReadout[3] == 1)
				{
					((TileEntityRedNetLogic)te).setOutputPinMapping((Integer)packetReadout[4], (Integer)packetReadout[5], (Integer)packetReadout[6], (Integer)packetReadout[7]);
				}
				((TileEntityRedNetLogic)te).sendCircuitDefinition((Integer)packetReadout[4]);
			}
		}
		else if(packetType == Packets.LogicReinitialize) // client -> server: set circuit
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if(te instanceof TileEntityRedNetLogic)
			{
				((TileEntityRedNetLogic)te).reinitialize((EntityPlayer)player);
			}
		}
	}
}
