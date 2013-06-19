package denoflionsx.minefactoryreloaded.modhelpers.forestry.trees;

import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

public class HarvestableForestryTree implements IFactoryHarvestable {

    private int id;

    public HarvestableForestryTree(int id) {
        this.id = id;
    }

    @Override
    public int getPlantId() {
        return id;
    }

    @Override
    public HarvestType getHarvestType() {
        return HarvestType.Tree;
    }

    @Override
    public boolean breakBlock() {
        return true;
    }

    @Override
    public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, int x, int y, int z) {
        return world.getBlockId(x, y, z) == id;
    }

    @Override
    public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z) {
        return Block.blocksList[world.getBlockId(x, y, z)].getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
    }

    @Override
    public void preHarvest(World world, int x, int y, int z) {
    }

    @Override
    public void postHarvest(World world, int x, int y, int z) {
        // just in case.
        world.setBlockTileEntity(x, y, z, null);
    }
}
