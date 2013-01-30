package powercrystals.minefactoryreloaded.core;

import java.util.List;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore.Machine;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockFactoryMachine1 extends ItemBlock
{
	public ItemBlockFactoryMachine1(int i)
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getIconFromDamage(int i)
	{
		return Math.min(i, 6);
	}
	
	@Override
	public int getMetadata(int i)
	{
		return i;
	}

	@Override
	public String getItemNameIS(ItemStack itemstack)
	{
		int md = itemstack.getItemDamage();

		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.Ejector)) return "factoryEjector";
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.ItemRouter)) return "factoryItemRouter";
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.LiquidRouter)) return "factoryLiquidRouter";
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.DeepStorageUnit)) return "factoryDeepStorageUnit";
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.LiquiCrafter)) return "factoryLiquiCrafter";
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.LavaFabricator)) return "factoryLavaFabricator";
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.OilFabricator)) return "factoryOilFabricator";
		return "Invalid";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < 7; i++)
		{
			par3List.add(new ItemStack(MineFactoryReloadedCore.machineBlock1.blockID, 1, i));
		}
	}
}
