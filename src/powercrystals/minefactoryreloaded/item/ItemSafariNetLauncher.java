package powercrystals.minefactoryreloaded.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.entity.EntitySafariNet;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;

public class ItemSafariNetLauncher extends ItemFactory
{
	public ItemSafariNetLauncher(int id)
	{
		super(id);
		setCreativeTab(MFRCreativeTab.tab);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		super.addInformation(stack, player, infoList, advancedTooltips);
		infoList.add("Sneak-click to change mode");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(player.isSneaking())
		{
			stack.setItemDamage(stack.getItemDamage() == 0 ? 1 : 0);
			if(world.isRemote)
			{
				if(isCaptureMode(stack))
				{
					player.sendChatToPlayer("Changed to capture mode");
				}
				else
				{
					player.sendChatToPlayer("Changed to release mode");
				}
			}
			return stack;
		}
		
		for(int i = 0; i < player.inventory.getSizeInventory(); i++)
		{
			ItemStack ammo = player.inventory.getStackInSlot(i);
			if(ammo != null && ammo.getItem() instanceof ItemSafariNet)
			{
				if((ItemSafariNet.isEmpty(ammo) && isCaptureMode(stack)) || (!ItemSafariNet.isEmpty(ammo) && !isCaptureMode(stack)))
				{
					if(!world.isRemote)
					{
						EntitySafariNet esn = new EntitySafariNet(world, player, ammo);
						world.spawnEntityInWorld(esn);
						
						world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
					}
					player.inventory.setInventorySlotContents(i, null);
					break;
				}
			}
		}
		return stack;
	}
	
	private boolean isCaptureMode(ItemStack stack)
	{
		return stack != null && stack.getItemDamage() == 1;
	}
}
