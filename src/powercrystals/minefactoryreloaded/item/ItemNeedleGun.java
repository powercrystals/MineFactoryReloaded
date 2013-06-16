package powercrystals.minefactoryreloaded.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.entity.EntityNeedle;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemNeedleGun extends ItemFactory
{
	public ItemNeedleGun(int id)
	{
		super(id);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		boolean needsAmmo = stack.getTagCompound().getCompoundTag("ammo") == null || stack.getTagCompound().getCompoundTag("ammo").hasNoTags();
		
		if(!needsAmmo)
		{
			ItemStack ammo = new ItemStack(0, 0, 0);
			ammo.readFromNBT(stack.getTagCompound().getCompoundTag("ammo"));
			
			if(!player.worldObj.isRemote)
			{
				EntityNeedle needle = new EntityNeedle(world, player, ammo, 1.0F);
				world.spawnEntityInWorld(needle);
				world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 2.0F);
			}

			NBTTagCompound t = new NBTTagCompound();
			ammo.setItemDamage(ammo.getItemDamage() + 1);
			if(ammo.getItemDamage() <= ammo.getMaxDamage())
			{
				ammo.writeToNBT(t);
			}
			else
			{
				for(int i = 0; i < 36; i++)
				{
					if(player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].itemID == ammo.itemID)
					{
						player.inventory.decrStackSize(i, 1);
						ammo.setItemDamage(0);
						ammo.writeToNBT(t);
						break;
					}
				}
				
				if(!world.isRemote)
				{
					player.dropPlayerItem(new ItemStack(MineFactoryReloadedCore.needlegunAmmoEmptyItem));
				}
			}
			stack.getTagCompound().setCompoundTag("ammo", t);
		}
		else if(!world.isRemote)
		{
			player.openGui(MineFactoryReloadedCore.instance(), 1, world, 0, 0, 0);
		}
		return stack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
	}
}
