package denoflionsx.minefactoryreloaded.modhelpers.forestry.utils;

import forestry.api.arboriculture.ITreeRoot;
import forestry.api.core.ItemInterface;
import forestry.api.genetics.AlleleManager;
import net.minecraft.item.ItemStack;

public class ForestryUtils {

    public static ITreeRoot root;

    public static void setTreeRoot() {
        root = (ITreeRoot) AlleleManager.alleleRegistry.getSpeciesRoot("rootTrees");
    }
    
    public static ItemStack getItem(String name){
        try{
            return ItemInterface.getItem(name);
        }catch(Throwable ex){
            return null;
        }
    }
}
