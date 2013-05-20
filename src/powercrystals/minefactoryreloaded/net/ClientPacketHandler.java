package powercrystals.minefactoryreloaded.net;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.net.PacketWrapper;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactory;
import powercrystals.minefactoryreloaded.tile.conveyor.TileEntityConveyor;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoJukebox;
import powercrystals.minefactoryreloaded.tile.rednet.TileEntityRedNetHistorian;
import powercrystals.minefactoryreloaded.tile.rednet.TileEntityRedNetLogic;
import powercrystals.minefactoryreloaded.tile.rednet.TileEntityRedNetCable;
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
		
		if(packetType == Packets.TileDescription) // server -> client; server propagating machine rotation; args X Y Z rotation isActive
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, Integer.class, Boolean.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if(te instanceof TileEntityFactory)
			{
				TileEntityFactory tef = (TileEntityFactory) te;
				tef.rotateDirectlyTo((Integer)packetReadout[3]);
				tef.setIsActive((Boolean)packetReadout[4]);
			}
		}
		else if (packetType == Packets.ConveyorDescription) // server -> client; server propagating conveyor color, activity state
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, Integer.class, Boolean.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if (te instanceof TileEntityConveyor)
			{
				TileEntityConveyor tec = (TileEntityConveyor) te;
				tec.setDyeColor((Integer)packetReadout[3]);
				tec.setConveyorActive((Boolean)packetReadout[4]);
			}
		}
		else if (packetType == Packets.AutoJukeboxPlay) // server -> client; server playing a record
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, Integer.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if(te instanceof TileEntityAutoJukebox)
			{
				Minecraft.getMinecraft().ingameGUI.setRecordPlayingMessage(((ItemRecord)Item.itemsList[(Integer)packetReadout[3]]).recordName);
			}
		}
		else if (packetType == Packets.RoadBlockUpdate) // server -> client; road block light changed
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, Integer.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			((EntityPlayer)player).worldObj.setBlock((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2], MineFactoryReloadedCore.factoryRoadBlock.blockID, (Integer)packetReadout[3], 6);
			((EntityPlayer)player).worldObj.markBlockForRenderUpdate((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
		}
		else if (packetType == Packets.CableDescription) // server -> client; cable side colors
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Byte.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if(te instanceof TileEntityRedNetCable)
			{
				TileEntityRedNetCable tec = (TileEntityRedNetCable) te;
				tec.setSideColor(ForgeDirection.DOWN, (Integer)packetReadout[3]);
				tec.setSideColor(ForgeDirection.UP, (Integer)packetReadout[4]);
				tec.setSideColor(ForgeDirection.NORTH, (Integer)packetReadout[5]);
				tec.setSideColor(ForgeDirection.SOUTH, (Integer)packetReadout[6]);
				tec.setSideColor(ForgeDirection.WEST, (Integer)packetReadout[7]);
				tec.setSideColor(ForgeDirection.EAST, (Integer)packetReadout[8]);
				tec.setMode((Byte)packetReadout[9]);
			}
		}
		else if(packetType == Packets.LogicCircuitDefinition) // server -> client: logic circuit (class and pins)
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if(te instanceof TileEntityRedNetLogic)
			{
				((TileEntityRedNetLogic)te).setCircuitFromPacket(data);
			}
		}
		else if(packetType == Packets.HistorianValueChanged)
		{
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, Integer.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if(te instanceof TileEntityRedNetHistorian)
			{
				((TileEntityRedNetHistorian)te).setClientValue((Integer)packetReadout[3]);
			}
		}
	}
}
