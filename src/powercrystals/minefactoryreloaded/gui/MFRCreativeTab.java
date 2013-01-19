package powercrystals.minefactoryreloaded.gui;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class MFRCreativeTab extends CreativeTabs {

    public static final MFRCreativeTab tab = new MFRCreativeTab("Minefactory Reloaded", new ItemStack(MineFactoryReloadedCore.conveyorBlock));
    //-------------------
    private String label;
    private ItemStack icon;

    public MFRCreativeTab(String label, ItemStack icon) {
        super(label);
        this.label = label;
        this.icon = icon;
    }

    @Override
    public ItemStack getIconItemStack() {
        return icon;
    }

    @Override
    public String getTabLabel() {
        return this.label;
    }

    @Override
    public String getTranslatedTabLabel() {
        return this.getTabLabel();
    }
}
