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
		safariNetTag.setInteger("woolColor", ((EntitySheep)capturedEntity).getFleeceColor());
		safariNetTag.setBoolean("shorn", ((EntitySheep)capturedEntity).getSheared());
	}

	@Override
	public void onRelease(NBTTagCompound safariNetTag, Entity spawnedEntity)
	{
		((EntitySheep)spawnedEntity).setFleeceColor(safariNetTag.getInteger("woolColor"));
		((EntitySheep)spawnedEntity).setSheared(safariNetTag.getBoolean("shorn"));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack safariNetStack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		infoList.add("Wool: " + ItemDye.dyeColorNames[BlockCloth.getBlockFromDye(safariNetStack.getTagCompound().getInteger("woolColor"))]);
	}
}
