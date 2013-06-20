package denoflionsx.minefactoryreloaded.modhelpers.forestry;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import denoflionsx.minefactoryreloaded.modhelpers.forestry.trees.FertilizableForestryTree;
import denoflionsx.minefactoryreloaded.modhelpers.forestry.trees.HarvestableForestryTree;
import denoflionsx.minefactoryreloaded.modhelpers.forestry.trees.PlantableForestryTree;
import java.lang.reflect.Field;
import net.minecraft.block.Block;
import powercrystals.minefactoryreloaded.api.FarmingRegistry;

@Mod(modid = "MFR Compat Forestry Trees", name = "MFR Compat Forestry Trees", version = "1.0", dependencies = "before:Forestry")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class ForestryTrees {

    @Mod.PostInit
    public void modsLoaded(FMLPostInitializationEvent evt) {
        if (Loader.isModLoaded("Forestry")) {
            try {
                FarmingRegistry.registerPlantable(new PlantableForestryTree());
                FarmingRegistry.registerFertilizable(new FertilizableForestryTree());
                for (Field f : Class.forName("forestry.core.config.ForestryBlock").getDeclaredFields()) {
                    if (f.getName().contains("log")) {
                        FarmingRegistry.registerHarvestable(new HarvestableForestryTree(((Block) f.get(null)).blockID));
                    }
                }
            } catch (Throwable damnit) {
            }
        }
    }
}
