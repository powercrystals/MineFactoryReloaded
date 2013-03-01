package powercrystals.minefactoryreloaded.modhelpers.twilightforest;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.EntityRegistry.EntityRegistration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.api.IMobEggHandler;

public class TwilightForestEggHandler implements IMobEggHandler
{
	@SuppressWarnings("unchecked")
	@Override
	public EntityEggInfo getEgg(ItemStack safariNet)
	{
		try
		{
			Class<? extends Entity> entityClass = (Class<? extends Entity>)Class.forName(safariNet.getTagCompound().getString("_class"));
			EntityRegistration er = EntityRegistry.instance().lookupModSpawn(entityClass, true);
			if(er.getContainer() != null && er.getContainer() == TwilightForest.twilightForestContainer)
			{
				return (EntityEggInfo)TwilightForest.entityEggs.get(er.getModEntityId());
			}
			return null;
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			
			return null;
		}
	}
}
