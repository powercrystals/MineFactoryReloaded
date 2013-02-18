package powercrystals.minefactoryreloaded.farmables.safarinethandlers;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;

public class ZombieHandler implements ISafariNetHandler
{
	@Override
	public Class<?> validFor()
	{
		return EntityZombie.class;
	}

	@Override
	public void onCapture(NBTTagCompound safariNetTag, Entity capturedEntity)
	{
		safariNetTag.setBoolean("isVillager", ((EntityZombie)capturedEntity).isVillager());
	}

	@Override
	public void onRelease(NBTTagCompound safariNetTag, Entity spawnedEntity)
	{
		((EntityZombie)spawnedEntity).setVillager(safariNetTag.getBoolean("isVillager"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack safariNetStack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		if(safariNetStack.getTagCompound().getBoolean("isVillager"))
		{
			infoList.add("Villager");
		}
	}
}
