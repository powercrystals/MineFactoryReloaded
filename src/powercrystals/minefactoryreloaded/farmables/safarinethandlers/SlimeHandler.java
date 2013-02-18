package powercrystals.minefactoryreloaded.farmables.safarinethandlers;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;

public class SlimeHandler implements ISafariNetHandler
{
	@Override
	public Class<?> validFor()
	{
		return EntitySlime.class;
	}

	@Override
	public void onCapture(NBTTagCompound safariNetTag, Entity capturedEntity)
	{
		safariNetTag.setInteger("slimeSize", ((EntitySlime)capturedEntity).getSlimeSize());
	}

	@Override
	public void onRelease(NBTTagCompound safariNetTag, Entity spawnedEntity)
	{
		((EntitySlime)spawnedEntity).setSlimeSize(safariNetTag.getInteger("slimeSize"));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack safariNetStack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
	}
}
