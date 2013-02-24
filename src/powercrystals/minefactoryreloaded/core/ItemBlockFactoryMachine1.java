package powercrystals.minefactoryreloaded.core;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore.Machine;
import powercrystals.minefactoryreloaded.processing.TileEntityDeepStorageUnit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean par4)
	{
		NBTTagCompound c = stack.getTagCompound();
		if(c != null && stack.getItemDamage() == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.DeepStorageUnit))
		{
			int storedId = c.getInteger("storedId");
			int storedMeta = c.getInteger("storedMeta");
			ItemStack storedItem = new ItemStack(storedId, 1, storedMeta);
			info.add("Contains " + c.getInteger("storedQuantity") + " " + Item.itemsList[storedId].getItemDisplayName(storedItem) + " (" + storedId + ":" + storedMeta + ")");
		}
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
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.AutoJukebox)) return "factoryAutoJukebox";
		if(md == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.Unifier)) return "factoryUnifier";
		return "Invalid";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < 9; i++)
		{
			par3List.add(new ItemStack(MineFactoryReloadedCore.machineBlock1.blockID, 1, i));
		}
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{
		if(super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata))
		{
			if(metadata == MineFactoryReloadedCore.machine1MetadataMappings.get(Machine.DeepStorageUnit) && stack.getTagCompound() != null)
			{
				((TileEntityDeepStorageUnit)world.getBlockTileEntity(x, y, z)).setId(stack.getTagCompound().getInteger("storedId"));
				((TileEntityDeepStorageUnit)world.getBlockTileEntity(x, y, z)).setMeta(stack.getTagCompound().getInteger("storedMeta"));
				((TileEntityDeepStorageUnit)world.getBlockTileEntity(x, y, z)).setQuantity(stack.getTagCompound().getInteger("storedQuantity"));
				((TileEntityDeepStorageUnit)world.getBlockTileEntity(x, y, z)).setSideIsOutput(0, stack.getTagCompound().getBoolean("side0output"));
				((TileEntityDeepStorageUnit)world.getBlockTileEntity(x, y, z)).setSideIsOutput(1, stack.getTagCompound().getBoolean("side1output"));
				((TileEntityDeepStorageUnit)world.getBlockTileEntity(x, y, z)).setSideIsOutput(2, stack.getTagCompound().getBoolean("side2output"));
				((TileEntityDeepStorageUnit)world.getBlockTileEntity(x, y, z)).setSideIsOutput(3, stack.getTagCompound().getBoolean("side3output"));
				((TileEntityDeepStorageUnit)world.getBlockTileEntity(x, y, z)).setSideIsOutput(4, stack.getTagCompound().getBoolean("side4output"));
				((TileEntityDeepStorageUnit)world.getBlockTileEntity(x, y, z)).setSideIsOutput(5, stack.getTagCompound().getBoolean("side5output"));
			}
			return true;	
		}
		return false;
	}
}
