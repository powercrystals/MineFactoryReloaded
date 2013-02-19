package powercrystals.minefactoryreloaded.farmables.safarinethandlers;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;

public class EntityLivingHandler implements ISafariNetHandler
{
	@Override
	public Class<?> validFor()
	{
		return EntityLiving.class;
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
		infoList.add("Health: " + ((NBTTagCompound)safariNetStack.getTagCompound().getTag("mobData")).getShort("Health"));
	}
}
