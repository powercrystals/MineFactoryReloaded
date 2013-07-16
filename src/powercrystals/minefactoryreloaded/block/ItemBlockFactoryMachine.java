package powercrystals.minefactoryreloaded.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import powercrystals.minefactoryreloaded.setup.Machine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockFactoryMachine extends ItemBlockFactory
{
	private int _machineBlockIndex;
	
	public ItemBlockFactoryMachine(int blockId)
	{
		super(blockId);
		setMaxDamage(0);
		setHasSubtypes(true);
		
		_machineBlockIndex = ((BlockFactoryMachine)Block.blocksList[getBlockID()]).getBlockIndex();
		int highestMeta = Machine.getHighestMetadata(_machineBlockIndex);
		String[] names = new String[highestMeta + 1];
		for(int i = 0; i <= highestMeta; i++)
		{
			names[i] = Machine.getMachineFromIndex(_machineBlockIndex, i).getInternalName();
		}
		setNames(names);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return _names[Math.min(stack.getItemDamage(), _names.length - 1)];
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
			int storedQuantity = c.getInteger("storedQuantity");
			if (storedId > 0 & storedQuantity > 0)
			{
				ItemStack storedItem = new ItemStack(storedId, 1, storedMeta);
				info.add("Contains " + storedQuantity + " " + Item.itemsList[storedId].getItemDisplayName(storedItem) + " (" + storedId + ":" + storedMeta + ")");
			}
			else
			{
				// info.add("Empty");
			}
		}
		else if(getBlockID() == Machine.BioFuelGenerator.getBlockId() && stack.getItemDamage() == Machine.BioFuelGenerator.getMeta())
		{
			info.add("Produces MJ only.");
		}
	}
}
