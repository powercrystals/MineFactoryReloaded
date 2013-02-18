package powercrystals.minefactoryreloaded.farmables.safarinethandlers;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;

public class SkeletonHandler implements ISafariNetHandler
{
	@Override
	public Class<?> validFor()
	{
		return EntitySkeleton.class;
	}

	@Override
	public void onCapture(NBTTagCompound safariNetTag, Entity capturedEntity)
	{
		safariNetTag.setInteger("skeletonType", ((EntitySkeleton)capturedEntity).getSkeletonType());
	}

	@Override
	public void onRelease(NBTTagCompound safariNetTag, Entity spawnedEntity)
	{
		((EntitySkeleton)spawnedEntity).setSkeletonType(safariNetTag.getInteger("skeletonType"));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack safariNetStack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
	}
}
