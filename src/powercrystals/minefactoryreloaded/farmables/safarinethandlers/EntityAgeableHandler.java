package powercrystals.minefactoryreloaded.farmables.safarinethandlers;

import java.util.List;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;

public class EntityAgeableHandler implements ISafariNetHandler
{
	@Override
	public Class<?> validFor()
	{
		return EntityAgeable.class;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack safariNetStack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		if(safariNetStack.getTagCompound().getInteger("Age") < 0)
		{
			infoList.add("Baby");
		}
	}
}
