package powercrystals.minefactoryreloaded.net;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

import powercrystals.core.net.PacketWrapper;
import powercrystals.minefactoryreloaded.core.TileEntityFactory;
import powercrystals.minefactoryreloaded.transport.TileEntityConveyor;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler implements IPacketHandler
{
	@SuppressWarnings("rawtypes")
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		int packetType = PacketWrapper.readPacketID(data);
		
		if (packetType == Packets.PacketIdTileDescription) // server -> client; server propagating machine rotation; args X Y Z rotation isActive
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, Integer.class, Boolean.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if (te instanceof TileEntityFactory)
			{
				TileEntityFactory tef = (TileEntityFactory) te;
				tef.rotateDirectlyTo((Integer)packetReadout[3]);
				tef.setIsActive((Boolean)packetReadout[4]);
			}
		}
		else if (packetType == Packets.PacketIdConveyorDescription) // server -> client; server propagating conveyor color
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, Integer.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if (te instanceof TileEntityConveyor)
			{
				TileEntityConveyor tec = (TileEntityConveyor) te;
				tec.setDyeColor((Integer)packetReadout[3]);
			}
		}
	}
}
