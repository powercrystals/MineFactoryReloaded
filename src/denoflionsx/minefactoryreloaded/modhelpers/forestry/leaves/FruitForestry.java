package denoflionsx.minefactoryreloaded.modhelpers.forestry.leaves;

import forestry.api.core.ItemInterface;
import forestry.api.genetics.IFruitBearer;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IFactoryFruit;

public class FruitForestry implements IFactoryFruit {

    private int id;
    private Method isPoll;

    public FruitForestry(int id) {
        this.id = id;
        try {
            // This isn't done yet. Need to figure out pollination.
            isPoll = Class.forName("forestry.arboriculture.gadgets.TileLeaves").getDeclaredMethod("isPollinated", new Class[0]);
        } catch (Exception ex) {
        }
    }

    @Override
    public int getSourceBlockId() {
        return this.id;
    }

    @Override
    public boolean canBePicked(World world, int x, int y, int z) {
        try {
            TileEntity t = world.getBlockTileEntity(x, y, z);
            if (t instanceof IFruitBearer) {
                IFruitBearer f = (IFruitBearer) t;
                if (f.hasFruit() && f.getRipeness() >= 1.0f) {
                    return true;
//                }else if ((Boolean) isPoll.invoke(t, new Object[0])){
//                    return true;
                }
            }
        } catch (Throwable ex) {
        }
        return false;
    }

    @Override
    public ItemStack getReplacementBlock(World world, int x, int y, int z) {
        return new ItemStack(this.getSourceBlockId(), 1, 0);
    }

    @Override
    public void prePick(World world, int x, int y, int z) {
    }

    @Override
    public List<ItemStack> getDrops(World world, Random rand, int x, int y, int z) {
        TileEntity t = world.getBlockTileEntity(x, y, z);
        if (t instanceof IFruitBearer) {
            IFruitBearer f = (IFruitBearer) t;
            return ((List) f.pickFruit(ItemInterface.getItem("grafter").copy()));
        }
        return null;
    }

    @Override
    public void postPick(World world, int x, int y, int z) {
    }
}
