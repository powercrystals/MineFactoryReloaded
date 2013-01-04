package powercrystals.minefactoryreloaded.animals;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.ItemFactory;

public class ItemSafariNet extends ItemFactory
{
	public ItemSafariNet()
	{
		super(MineFactoryReloadedCore.safariNetItemId.getInt());
		maxStackSize = 1;
	}

	@Override
	public String getItemDisplayName(ItemStack par1ItemStack)
	{
		String var2 = ("" + StatCollector.translateToLocal(this.getItemName() + ".name")).trim();
		String var3 = EntityList.getStringFromID(par1ItemStack.getItemDamage());

		if (var3 != null)
		{
			var2 = var2 + " (" + StatCollector.translateToLocal("entity." + var3 + ".name") + ")";
		}

		return var2;
	}

	@SideOnly(Side.CLIENT)
	public int getIconFromDamageForRenderPass(int damage, int pass)
	{
		if(damage == 0) return iconIndex + 3;
		else if(pass == 0) return iconIndex;
		else if(pass == 1) return iconIndex + 1;
		else if(pass == 2) return iconIndex + 2;
		else return 255;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
	public int getRenderPasses(int metadata)
	{
		return 3;
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		if(stack.getItemDamage() == 0)
		{
			return 16777215;
		}
		EntityEggInfo egg = (EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(stack.getItemDamage()));
		
		if(egg == null)
		{
			return 16777215;
		}
		else if(pass == 2)
		{
			return egg.secondaryColor;
		}
		else if(pass == 1)
		{
			return egg.primaryColor;
		}
		else
		{
			return 16777215;
		}
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float xOffset, float yOffset, float zOffset)
	{
		if(world.isRemote)
		{
			return true;
		}
		else if(itemstack.getItemDamage() == 0)
		{
			return true;
		}
		else
		{
			int blockId = world.getBlockId(x, y, z);
			x += Facing.offsetsXForSide[side];
			y += Facing.offsetsYForSide[side];
			z += Facing.offsetsZForSide[side];
			double var12 = 0.0D;

			if (side == 1 && Block.blocksList[blockId] != null && Block.blocksList[blockId].getRenderType() == 11)
			{
				var12 = 0.5D;
			}

			if(spawnCreature(world, itemstack.getItemDamage(), (double)x + 0.5D, (double)y + var12, (double)z + 0.5D) != null)
			{
				--itemstack.stackSize;
			}

			return true;
		}
	}

	private static Entity spawnCreature(World world, int mobId, double x, double y, double z)
	{
		if (!EntityList.entityEggs.containsKey(Integer.valueOf(mobId)))
		{
			return null;
		}
		else
		{
			Entity e = EntityList.createEntityByID(mobId, world);

			if (e != null)
			{
					e.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
					((EntityLiving)e).initCreature();
					world.spawnEntityInWorld(e);
					((EntityLiving)e).playLivingSound();
				}

			return e;
		}
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityLiving entity)
	{
		if(itemstack.getItemDamage() > 0)
		{
			return true;
		}
		if(entity instanceof EntityLiving)
		{
			itemstack.setItemDamage(EntityList.getEntityID(entity));
			entity.setDead();
			return true;
		}
		return true;
	}
}
