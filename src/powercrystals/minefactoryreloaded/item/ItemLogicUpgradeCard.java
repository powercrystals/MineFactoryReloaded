package powercrystals.minefactoryreloaded.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
		infoList.add("Circuits: " + getCircuitsForLevel(stack.getItemDamage() + 1));
		infoList.add("Variables: " + getVariablesForLevel(stack.getItemDamage() + 1));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack s)
	{
		int md = Math.min(s.getItemDamage(), _upgradeNames.length);
		return getUnlocalizedName() + "." + _upgradeNames[md];
	}
	
	public static int getCircuitsForLevel(int level)
	{
		return level == 0 ? 0 : 1 + 2 * (level - 1);
	}
	
	public static int getVariablesForLevel(int level)
	{
		return level == 0 ? 0 : 8 * level;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
	}
}
