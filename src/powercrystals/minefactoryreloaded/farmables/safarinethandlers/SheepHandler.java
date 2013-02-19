package powercrystals.minefactoryreloaded.farmables.safarinethandlers;

import java.util.List;

import net.minecraft.block.BlockCloth;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;

public class SheepHandler implements ISafariNetHandler
{
	@Override
	public Class<?> validFor()
	{
		return EntitySheep.class;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack safariNetStack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		infoList.add("Wool: " + ItemDye.dyeColorNames[BlockCloth.getBlockFromDye((safariNetStack.getTagCompound().getByte("Color")))]);
	}
}
