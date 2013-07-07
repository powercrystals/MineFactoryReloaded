package powercrystals.minefactoryreloaded.item;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import powercrystals.core.net.PacketWrapper;
import powercrystals.minefactoryreloaded.MineFactoryReloadedClient;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.net.Packets;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRocketLauncher extends ItemFactory
{
	public ItemRocketLauncher(int id)
	{
		super(id);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(player.inventory.hasItem(MineFactoryReloadedCore.rocketItem.itemID))
		{
			player.inventory.consumeInventoryItem(MineFactoryReloadedCore.rocketItem.itemID);
			
			if(world.isRemote)
			{
				PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modNetworkChannel, Packets.RocketLaunchWithLock, new Object[]
						{ player.entityId, MineFactoryReloadedClient.instance.getLockedEntity()	}));
			}
		}
		return stack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
	}
}
