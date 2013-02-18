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
		EntityLiving l = (EntityLiving)capturedEntity;
		safariNetTag.setInteger("health", l.getHealth());
	}

	@Override
	public void onRelease(NBTTagCompound safariNetTag, Entity spawnedEntity)
	{
		((EntityLiving)spawnedEntity).setEntityHealth(safariNetTag.getInteger("health"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack safariNetStack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		infoList.add("Health: " + safariNetStack.getTagCompound().getInteger("health"));
	}
}
