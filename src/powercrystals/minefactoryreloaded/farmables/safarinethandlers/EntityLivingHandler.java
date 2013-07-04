package powercrystals.minefactoryreloaded.farmables.safarinethandlers;

import java.util.List;

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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack safariNetStack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		NBTTagCompound tag = safariNetStack.getTagCompound();
		if (tag.hasKey("CustomName"))
		{
			infoList.add("Name: " + tag.getString("CustomName"));
		}
		infoList.add("Health: " + (tag.getShort("Health")));
	}
}
