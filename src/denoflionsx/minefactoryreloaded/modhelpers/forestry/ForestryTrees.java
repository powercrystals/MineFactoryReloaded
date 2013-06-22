package denoflionsx.minefactoryreloaded.modhelpers.forestry;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import denoflionsx.minefactoryreloaded.modhelpers.forestry.fertilizer.FertilizerForestry;
import denoflionsx.minefactoryreloaded.modhelpers.forestry.leaves.FertilizableForestryLeaves;
import denoflionsx.minefactoryreloaded.modhelpers.forestry.trees.FertilizableForestryTree;
import denoflionsx.minefactoryreloaded.modhelpers.forestry.leaves.FruitForestry;
import denoflionsx.minefactoryreloaded.modhelpers.forestry.trees.HarvestableForestryTree;
import denoflionsx.minefactoryreloaded.modhelpers.forestry.trees.PlantableForestryTree;
import denoflionsx.minefactoryreloaded.modhelpers.forestry.utils.ForestryUtils;
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
                ForestryUtils.setTreeRoot();
                FarmingRegistry.registerPlantable(new PlantableForestryTree());
                FarmingRegistry.registerFertilizable(new FertilizableForestryTree());
                for (Field f : Class.forName("forestry.core.config.ForestryBlock").getDeclaredFields()) {
                    if (f.getName().contains("log")) {
                        Block log = ((Block) f.get(null));
                        if (log != null) {
                            FarmingRegistry.registerHarvestable(new HarvestableForestryTree(log.blockID));
                            FarmingRegistry.registerFruitLogBlockId(log.blockID);
                        }
                    } else if (f.getName().contains("leaves")) {
                        Block leaves = ((Block) f.get(null));
                        if (leaves != null) {
                            FarmingRegistry.registerFruit(new FruitForestry(leaves.blockID));
                            FarmingRegistry.registerFertilizable(new FertilizableForestryLeaves(leaves.blockID));
                        }
                    }
                }
                FarmingRegistry.registerFertilizer(new FertilizerForestry(ForestryUtils.getItem("fertilizerBio")));
            } catch (Throwable damnit) {
                damnit.printStackTrace();
            }
        }
    }
}
