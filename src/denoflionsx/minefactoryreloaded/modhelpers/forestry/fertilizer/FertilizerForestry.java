package denoflionsx.minefactoryreloaded.modhelpers.forestry.fertilizer;

import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizer;

public class FertilizerForestry implements IFactoryFertilizer {

    private ItemStack fert;

    public FertilizerForestry(ItemStack fert) {
        this.fert = fert;
    }

    @Override
    public int getFertilizerId() {
        return this.fert.itemID;
    }

    @Override
    public int getFertilizerMeta() {
        return this.fert.getItemDamage();
    }

    @Override
    public FertilizerType getFertilizerType() {
        return FertilizerType.GrowPlant;
    }

    @Override
    public void consume(ItemStack fertilizer) {
        fertilizer.stackSize--;
    }
}
