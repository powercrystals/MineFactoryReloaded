package powercrystals.minefactoryreloaded.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;

public class ItemLogicUpgradeCard extends ItemFactory
{
	private static String[] _upgradeNames = { "100", "300", "500" };
	
	public ItemLogicUpgradeCard(int id)
	{
		super(id);
		setCreativeTab(MFRCreativeTab.tab);
		setHasSubtypes(true);
		setMetaMax(2);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		super.addInformation(stack, player, infoList, advancedTooltips);
		infoList.add("Circuits: " + (1 + 2 * stack.getItemDamage()));
		infoList.add("Variables: " + (8 * (1 + stack.getItemDamage())));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack s)
	{
		int md = Math.min(s.getItemDamage(), _upgradeNames.length);
		return getUnlocalizedName() + "." + _upgradeNames[md];
	}
}
