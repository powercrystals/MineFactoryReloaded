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
		safariNetTag.setInteger("growingAge", ((EntityAgeable)capturedEntity).getGrowingAge());
	}

	@Override
	public void onRelease(NBTTagCompound safariNetTag, Entity spawnedEntity)
	{
		((EntityAgeable)spawnedEntity).setGrowingAge(safariNetTag.getInteger("growingAge"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack safariNetStack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		if(safariNetStack.getTagCompound().getInteger("growingAge") != 0)
		{
			infoList.add("Baby");
		}
	}
}
