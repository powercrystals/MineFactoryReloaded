package powercrystals.minefactoryreloaded.setup.village;

import java.util.Random;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

public class VillageTradeHandler implements IVillageTradeHandler
{
	@SuppressWarnings("unchecked")
	@Override
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random)
	{
		//recipeList.add(new MerchantRecipe(new ItemStack(Item.emerald, 1), new ItemStack(MineFactoryReloadedCore.safariNetSingleItem)));
		//recipeList.add(new MerchantRecipe(new ItemStack(Item.emerald, 3), new ItemStack(MineFactoryReloadedCore.safariNetItem)));
		
		recipeList.add(new MerchantRecipe(new ItemStack(Item.emerald), new ItemStack(MineFactoryReloadedCore.safariNetSingleItem), getStackForEntity(new EntityMooshroom(villager.worldObj))));
	}
	
	private ItemStack getStackForEntity(EntityLiving mob)
	{
		ItemStack s = new ItemStack(MineFactoryReloadedCore.safariNetSingleItem);
		NBTTagCompound c = new NBTTagCompound();
		mob.writeToNBT(c);
		c.setString("id", (String)EntityList.classToStringMapping.get(mob.getClass()));
		c.setBoolean("hide", true);
		s.setTagCompound(c);
		return s;
	}
}
