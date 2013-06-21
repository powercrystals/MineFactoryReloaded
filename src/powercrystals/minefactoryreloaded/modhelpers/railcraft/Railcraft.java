package powercrystals.minefactoryreloaded.modhelpers.railcraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Method;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.network.NetworkMod;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

@Mod(modid = "MineFactoryReloaded|CompatRailcraft", name = "MFR Compat: Railcraft", version = MineFactoryReloadedCore.version, dependencies = "after:MineFactoryReloaded;after:Railcraft")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class Railcraft {

	@Init
	public void load(FMLInitializationEvent evt) {
		if (!Loader.isModLoaded("Railcraft"))
		{
			FMLLog.warning("Railcraft missing - MFR Railcraft Compat not loading");
			// why warn?
			return;
		}
		try
		{
			int id = MineFactoryReloadedCore.factoryDecorativeStoneBlock.blockID;
			FMLInterModComms.sendMessage("Railcraft", "balast", String.format("%s@%s", id, 8));
			FMLInterModComms.sendMessage("Railcraft", "balast", String.format("%s@%s", id, 9));
			// white sand? black sand?

			Object rockCrusher = Class.forName("mods.railcraft.api.crafting.RailcraftCraftingManager").getField("rockCrusher").get(null);
			Method createNewRecipe = Class.forName("mods.railcraft.api.crafting.IRockCrusherCraftingManager").getMethod("createNewRecipe", ItemStack.class, boolean.class, boolean.class);
			Method addOutput = Class.forName("mods.railcraft.api.crafting.IRockCrusherRecipe").getMethod("addOutput", ItemStack.class, float.class);

			Object recipe = createNewRecipe.invoke(rockCrusher, new ItemStack(MineFactoryReloadedCore.factoryDecorativeStoneBlock, 1, 10), true, false);
			addOutput.invoke(recipe, new ItemStack(MineFactoryReloadedCore.factoryDecorativeStoneBlock, 1, 2), 1.0f); // Paved Blackstone -> Cobble 
			
			recipe = createNewRecipe.invoke(rockCrusher, new ItemStack(MineFactoryReloadedCore.factoryDecorativeStoneBlock, 1, 11), true, false);
			addOutput.invoke(recipe, new ItemStack(MineFactoryReloadedCore.factoryDecorativeStoneBlock, 1, 3), 1.0f); // Paved Whitestone -> Cobble
			
			recipe = createNewRecipe.invoke(rockCrusher, new ItemStack(MineFactoryReloadedCore.factoryDecorativeStoneBlock, 1, 0), true, false);
			addOutput.invoke(recipe, new ItemStack(MineFactoryReloadedCore.factoryDecorativeStoneBlock, 1, 2), 1.0f); // Smooth Blackstone -> Cobble 
			
			recipe = createNewRecipe.invoke(rockCrusher, new ItemStack(MineFactoryReloadedCore.factoryDecorativeStoneBlock, 1, 1), true, false);
			addOutput.invoke(recipe, new ItemStack(MineFactoryReloadedCore.factoryDecorativeStoneBlock, 1, 3), 1.0f); // Smooth Whitestone -> Cobble
			
			recipe = createNewRecipe.invoke(rockCrusher, new ItemStack(MineFactoryReloadedCore.factoryDecorativeStoneBlock, 1, 2), true, false);
			addOutput.invoke(recipe, new ItemStack(MineFactoryReloadedCore.factoryDecorativeStoneBlock, 1, 8), 1.0f); // Cobble Blackstone -> Gravel + flint
			addOutput.invoke(recipe, new ItemStack(Item.flint, 1, 0), 0.05f);
			
			recipe = createNewRecipe.invoke(rockCrusher, new ItemStack(MineFactoryReloadedCore.factoryDecorativeStoneBlock, 1, 3), true, false);
			addOutput.invoke(recipe, new ItemStack(MineFactoryReloadedCore.factoryDecorativeStoneBlock, 1, 9), 1.0f); // Cobble Whitestone -> Gravel + flint
			addOutput.invoke(recipe, new ItemStack(Item.flint, 1, 0), 0.05f);
		}
		catch (Throwable _)
		{
			ModContainer This = FMLCommonHandler.instance().findContainerFor(this);
			FMLLog.log(This.getModId(), Level.SEVERE, "There was a problem loading %s.", This.getName());
			_.printStackTrace();
		}
	}

}
