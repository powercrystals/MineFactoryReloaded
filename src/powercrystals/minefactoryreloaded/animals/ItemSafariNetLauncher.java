package powercrystals.minefactoryreloaded.animals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.ItemFactory;
import powercrystals.minefactoryreloaded.entity.EntitySafariNet;

public class ItemSafariNetLauncher extends ItemFactory
{
	public ItemSafariNetLauncher(int id)
	{
		super(id);
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		
		for(int i = 0; i < player.inventory.getSizeInventory(); i++)
		{
			ItemStack ammo = player.inventory.getStackInSlot(i);
			if(ammo != null && ammo.getItem() instanceof ItemSafariNet)
			{
				if(!world.isRemote)
				{
					EntitySafariNet esn = new EntitySafariNet(world, player, (ammo.itemID == MineFactoryReloadedCore.safariNetSingleItem.itemID));
					if(!ItemSafariNet.isEmpty(ammo))
					{
						esn.setStoredEntity(ammo);
					}
					world.spawnEntityInWorld(esn);
				}
				player.inventory.setInventorySlotContents(i, null);
				break;
			}
		}
		return stack;
	}
}
