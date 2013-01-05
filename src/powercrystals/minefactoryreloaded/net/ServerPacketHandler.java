package powercrystals.minefactoryreloaded.net;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

import powercrystals.core.net.PacketWrapper;
import powercrystals.minefactoryreloaded.animals.TileEntityChronotyper;
import powercrystals.minefactoryreloaded.plants.TileEntityHarvester;
import powercrystals.minefactoryreloaded.processing.TileEntityAutoEnchanter;
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
		
		if (packetType == PacketWrapper.PacketIdEnchanterButton) // client -> server: autoenchanter GUI buttons
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, Integer.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if (te instanceof TileEntityAutoEnchanter)
			{
				((TileEntityAutoEnchanter)te).setTargetLevel(((TileEntityAutoEnchanter)te).getTargetLevel() + (Integer)packetReadout[3]);
			}
		}
		else if(packetType == PacketWrapper.PacketIdHarvesterButton) // client -> server: harvester setting
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, String.class, Boolean.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if(te instanceof TileEntityHarvester)
			{
				((TileEntityHarvester)te).getSettings().put((String)packetReadout[3], (Boolean)packetReadout[4]);
			}
		}
		else if(packetType == PacketWrapper.PacketIdChronotyperButton) // client -> server: toggle chronotyper
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if(te instanceof TileEntityChronotyper)
			{
				((TileEntityChronotyper)te).setMoveOld(!((TileEntityChronotyper)te).getMoveOld());
			}
		}
	}
}
