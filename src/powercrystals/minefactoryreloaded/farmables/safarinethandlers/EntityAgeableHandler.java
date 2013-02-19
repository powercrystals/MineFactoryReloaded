package powercrystals.minefactoryreloaded.farmables.safarinethandlers;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;

public class EntityAgeableHandler implements ISafariNetHandler
{
	@Override
	public Class<?> validFor()
	{
		return EntityAgeable.class;
	}

	@Override
	public void onCapture(NBTTagCompound safariNetTag, Entity capturedEntity)
	{
	}

	@Override
	public void onRelease(NBTTagCompound safariNetTag, Entity spawnedEntity)
	{
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack safariNetStack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		if(((NBTTagCompound)safariNetStack.getTagCompound().getTag("mobData")).getInteger("Age") != 0)
		{
			infoList.add("Baby");
		}
	}
}
