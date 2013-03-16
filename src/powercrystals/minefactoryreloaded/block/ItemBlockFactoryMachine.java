package powercrystals.minefactoryreloaded.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import powercrystals.minefactoryreloaded.setup.Machine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBlockFactoryMachine extends ItemBlockFactory
{
	public ItemBlockFactoryMachine(int blockId)
	{
		super(blockId);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean par4)
	{
		NBTTagCompound c = stack.getTagCompound();
		if(getBlockID() == Machine.DeepStorageUnit.getBlockId() && stack.getItemDamage() == Machine.DeepStorageUnit.getMeta() && c != null)
		{
			int storedId = c.getInteger("storedId");
			int storedMeta = c.getInteger("storedMeta");
			ItemStack storedItem = new ItemStack(storedId, 1, storedMeta);
			info.add("Contains " + c.getInteger("storedQuantity") + " " + Item.itemsList[storedId].getItemDisplayName(storedItem) + " (" + storedId + ":" + storedMeta + ")");
		}
	}
}
