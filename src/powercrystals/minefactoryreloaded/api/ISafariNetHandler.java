package powercrystals.minefactoryreloaded.api;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public interface ISafariNetHandler
{
	public Class<?> validFor();
	
	public void onCapture(ItemStack safariNetStack, Entity capturedEntity);
	
	public void onRelease(ItemStack safariNetStack, Entity capturedEntity);

	public void addInformation(ItemStack safariNetStack);
}
