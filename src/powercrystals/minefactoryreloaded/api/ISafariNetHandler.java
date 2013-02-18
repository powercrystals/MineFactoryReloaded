package powercrystals.minefactoryreloaded.api;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface ISafariNetHandler
{
	public Class<?> validFor();
	
	public void onCapture(NBTTagCompound safariNetTag, Entity capturedEntity);
	
	public void onRelease(NBTTagCompound safariNetTag, Entity capturedEntity);

	public void addInformation(ItemStack safariNetStack, EntityPlayer player, List infoList, boolean advancedTooltips);
}
