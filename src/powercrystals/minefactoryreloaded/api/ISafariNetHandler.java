package powercrystals.minefactoryreloaded.api;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ISafariNetHandler
{
	public Class<?> validFor();

	@SuppressWarnings("rawtypes")
	public void addInformation(ItemStack safariNetStack, EntityPlayer player, List infoList, boolean advancedTooltips);
}
