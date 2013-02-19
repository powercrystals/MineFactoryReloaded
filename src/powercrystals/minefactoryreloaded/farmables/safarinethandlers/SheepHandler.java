package powercrystals.minefactoryreloaded.farmables.safarinethandlers;

import java.util.List;

import net.minecraft.block.BlockCloth;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;

public class SheepHandler implements ISafariNetHandler
{
	@Override
	public Class<?> validFor()
	{
		return EntitySheep.class;
	}

	@Override
	public void onCapture(NBTTagCompound safariNetTag, Entity capturedEntity)
	{
	}

	@Override
	public void onRelease(NBTTagCompound safariNetTag, Entity spawnedEntity)
	{
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack safariNetStack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		infoList.add("Wool: " + ItemDye.dyeColorNames[BlockCloth.getBlockFromDye(((NBTTagCompound)safariNetStack.getTagCompound().getTag("mobData")).getByte("Color"))]);
	}
}
