package powercrystals.minefactoryreloaded.farmables.egghandlers;

import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.api.IMobEggHandler;

public class VanillaEggHandler implements IMobEggHandler
{
	@Override
	public EntityEggInfo getEgg(ItemStack safariNet)
	{
		return (EntityEggInfo)EntityList.entityEggs.get(EntityList.stringToIDMapping.get(safariNet.getTagCompound().getString("id")));
	}
}
