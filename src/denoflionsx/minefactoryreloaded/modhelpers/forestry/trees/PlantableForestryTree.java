package denoflionsx.minefactoryreloaded.modhelpers.forestry.trees;

import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.core.ItemInterface;
import forestry.api.genetics.AlleleManager;
import java.lang.reflect.Method;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;

public class PlantableForestryTree implements IFactoryPlantable {

    private ItemStack sapling;
    public static Block sapling_;
    private ITreeRoot root;
    public static Class TileTreeContainer;
    public static Method getTree_;
    private Method setTree;

    static {
        try {
            sapling_ = (Block) Class.forName("forestry.core.config.ForestryBlock").getField("saplingGE").get(null);
            TileTreeContainer = Class.forName("forestry.arboriculture.gadgets.TileTreeContainer");
            getTree_ = TileTreeContainer.getDeclaredMethod("getTree", new Class[0]);
        } catch (Throwable s) {
        }
    }

    public static ITree getTree(TileEntity t) {
        try {
            return (ITree) getTree_.invoke(t, new Object[0]);
        } catch (Throwable ex) {
        }
        return null;
    }

    public PlantableForestryTree() {
        try {
            sapling = ItemInterface.getItem("sapling");
            root = (ITreeRoot) AlleleManager.alleleRegistry.getSpeciesRoot("rootTrees");
            // Need to reference this class later.
            setTree = TileTreeContainer.getDeclaredMethod("setTree", new Class[]{ITree.class});
        } catch (Throwable stuff) {
        }
    }

    @Override
    public int getSeedId() {
        return sapling.itemID;
    }

    @Override
    public int getPlantedBlockId(World world, int x, int y, int z, ItemStack stack) {
        if (stack.isItemEqual(sapling)) {
            return sapling_.blockID;
        }
        return -1;
    }

    @Override
    public int getPlantedBlockMetadata(World world, int x, int y, int z, ItemStack stack) {
        if (stack.isItemEqual(sapling)) {
            return sapling.getItemDamage();
        }
        return 0;
    }

    @Override
    public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack) {
        if (stack.isItemEqual(sapling)) {
            int groundId = world.getBlockId(x, y - 1, z);
            if (!world.isAirBlock(x, y, z)) {
                return false;
            }
            return groundId == Block.dirt.blockID || groundId == Block.grass.blockID;
        }
        return false;
    }

    @Override
    public void prePlant(World world, int x, int y, int z, ItemStack stack) {
    }

    @Override
    public void postPlant(World world, int x, int y, int z, ItemStack stack) {
        if (stack.isItemEqual(sapling)) {
            TileEntity t = world.getBlockTileEntity(x, y, z);
            if (TileTreeContainer.isInstance(t)) {
                ITree tree = this.root.getMember(stack);
                try {
                    setTree.invoke(t, new Object[]{tree});
                } catch (Throwable TREE) {
                }
            }
        }
    }
}
