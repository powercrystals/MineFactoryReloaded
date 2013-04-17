package powercrystals.minefactoryreloaded.modhelpers.chococraft.Chococraft;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableCropPlant;

@Mod(modid="MineFactoryReloaded|CompatChococraft", name = "MFR Compat: Chococraft",
		version = MineFactoryReloadedCore.version,
		dependencies = "required-after:MineFactoryReloaded;after:chococraft")
@NetworkMod(clientSideRequired=false, serverSideRequired=false)
public class Chococraft
{
	@Mod.Init
	public void load(FMLInitializationEvent event)
	{
		if (!Loader.isModLoaded("chococraft"))
		{
			FMLLog.info("Chococraft is not available; MFR Chococraft Compat not loaded");
			return;
		}

		try
		{
			Class<?> mod = Class.forName("chococraft.common.ModChocoCraft");

			FMLLog.info("Registering Gysahls for Planter/Harvester/Fertilizer");
			int blockId = ((Block)(mod.getField("gysahlStemBlock").get(null))).blockID;
			int seedId = ((Item)(mod.getField("gysahlSeedsItem").get(null))).itemID;

			MFRRegistry.registerPlantable(new PlantableCropPlant(seedId, blockId));
			MFRRegistry.registerHarvestable(new HarvestableChococraft(blockId));
			MFRRegistry.registerFertilizable(new FertilizableChococraft(blockId));

			// Chocobo List
			FMLLog.info("Registering Chocobos for Grinder...");
			final String[] chocobos = { "Black", "Blue", "Gold", "Green", "Pink", "Purple", "Red", "White", "Yellow" };
			int legItem = ((Item)(mod.getField("chocoboLegRawItem").get(null))).itemID;
			int featherItem = ((Item)(mod.getField("chocoboFeatherItem").get(null))).itemID;

			for (String name : chocobos)
			{
				try
				{
					Class<?> chocoboClass = Class.forName(String.format("chococraft.common.entities.colours.EntityChocobo%s", name));
					MFRRegistry.registerGrindable(new GrindableChocobo(chocoboClass, legItem, featherItem));
				}
				catch (ClassNotFoundException e)
				{
					FMLLog.warning(String.format("Entity class for the %s Chocobo could not be found", name));
				}
			}
		}
		catch (ClassNotFoundException e)
		{
			FMLLog.warning("Unable to load support for Chococraft");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
