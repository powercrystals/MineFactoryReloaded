package powercrystals.minefactoryreloaded.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.tile.TileEntityRedNetLogic;

public class ItemRedNetMemoryCard extends ItemFactory
{
	public ItemRedNetMemoryCard(int id)
	{
		super(id);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		if(stack.getTagCompound() != null)
		{
			infoList.add("Programmed, " + stack.getTagCompound().getTagList("circuits").tagCount() + " circuits");
		}
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float xOffset, float yOffset, float zOffset)
	{
		if(world.isRemote)
		{
			return true;
		}
		
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te instanceof TileEntityRedNetLogic)
		{
			if(itemstack.getTagCompound() == null)
			{
				NBTTagCompound tag = new NBTTagCompound();
				te.writeToNBT(tag);
				itemstack.setTagCompound(tag);
				player.sendChatToPlayer("PRC program uploaded to memory card from PRC");
			}
			else
			{
				int circuitCount = itemstack.getTagCompound().getTagList("circuits").tagCount();
				if(circuitCount > ((TileEntityRedNetLogic)te).getCircuitCount())
				{
					player.sendChatToPlayer("PRC contains insufficient circuits to hold this program");
				}
				else
				{
					((TileEntityRedNetLogic)te).readCircuitsOnly(itemstack.getTagCompound());
					player.sendChatToPlayer("PRC program downloaded from memory card to PRC");
				}
			}
			
			return true;
		}
		return false;
	}
}
