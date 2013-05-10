package powercrystals.minefactoryreloaded.modhelpers.twilightforest;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.api.IMobEggHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.EntityRegistry.EntityRegistration;

public class TwilightForestEggHandler implements IMobEggHandler
{
	@SuppressWarnings("unchecked")
	@Override
	public EntityEggInfo getEgg(ItemStack safariNet)
	{
		Class<? extends Entity> entityClass = (Class<? extends Entity>)EntityList.stringToClassMapping.get(safariNet.getTagCompound().getString("id"));
		if(entityClass == null)
		{
			return null;
		}
		
		EntityRegistration er = EntityRegistry.instance().lookupModSpawn(entityClass, true);
		if(er != null && er.getContainer() == TwilightForest.twilightForestContainer)
		{
			return (EntityEggInfo)TwilightForest.entityEggs.get(er.getModEntityId());
		}
		return null;
	}
}
