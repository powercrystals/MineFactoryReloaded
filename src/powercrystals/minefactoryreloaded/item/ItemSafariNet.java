package powercrystals.minefactoryreloaded.item;

import java.util.List;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.IMobEggHandler;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;

public class ItemSafariNet extends ItemFactory
{
	private Icon _iconEmpty;
	private Icon _iconBack;
	private Icon _iconMid;
	private Icon _iconFront;
	
	public ItemSafariNet(int id)
	{
		super(id);
		maxStackSize = 1;
		setCreativeTab(MFRCreativeTab.tab);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack stack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		if(stack.getTagCompound() == null)
		{
			return;
		}
		
		infoList.add(stack.getTagCompound().getString("id"));
		for(ISafariNetHandler handler : MFRRegistry.getSafariNetHandlers())
		{
			try
			{
				if(handler.validFor().isAssignableFrom(Class.forName(stack.getTagCompound().getString("_class"))))
				{
					handler.addInformation(stack, player, infoList, advancedTooltips);
				}
			}
			catch(ClassNotFoundException e)
			{
				FMLLog.warning("MFR Safari Net tried to look up data for a nonexistent mob class %s!", stack.getTagCompound().getString("id"));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(ItemStack stack, int pass)
	{
		if(stack.getTagCompound() == null) return _iconEmpty;
		if(pass == 0) return _iconBack;
		else if(pass == 1) return _iconMid;
		else if(pass == 2) return _iconFront;
		return null;
	}

	@Override
	public void updateIcons(IconRegister ir)
	{
		_iconEmpty = ir.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName() + ".empty");
		_iconBack = ir.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName() + ".back");
		_iconMid = ir.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName() + ".mid");
		_iconFront = ir.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName() + ".front");
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
	@Override
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		if(stack.getItemDamage() == 0 && stack.getTagCompound() == null)
		{
			return 16777215;
		}
		EntityEggInfo egg = getEgg(stack);
		
		if(egg == null)
		{
			return 16777215;
		}
		else if(pass == 2)
		{
			return egg.primaryColor;
		}
		else if(pass == 1)
		{
			return egg.secondaryColor;
		}
		else
		{
			return 16777215;
		}
	}
	
	private EntityEggInfo getEgg(ItemStack safariStack)
	{
		if(safariStack.getTagCompound() == null)
		{
			return null;
		}
		
		for(IMobEggHandler handler : MFRRegistry.getModMobEggHandlers())
		{
			EntityEggInfo egg = handler.getEgg(safariStack);
			if(egg != null)
			{
				return egg;
			}
		}
		
		return null;
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float xOffset, float yOffset, float zOffset)
	{
		if(world.isRemote)
		{
			return true;
		}
		else if(isEmpty(itemstack))
		{
			return true;
		}
		else
		{
			return releaseEntity(itemstack, world, x, y, z, side);
		}
	}
	
	public static boolean releaseEntity(ItemStack itemstack, World world, int x, int y, int z, int side)
	{
		if(world.isRemote)
		{
			return false;
		}
		
		int blockId = world.getBlockId(x, y, z);
		x += Facing.offsetsXForSide[side];
		y += Facing.offsetsYForSide[side];
		z += Facing.offsetsZForSide[side];
		double spawnOffsetY = 0.0D;

		if (side == 1 && Block.blocksList[blockId] != null && Block.blocksList[blockId].getRenderType() == 11)
		{
			spawnOffsetY = 0.5D;
		}

		if(itemstack.getItemDamage() != 0)
		{
			if(spawnCreature(world, itemstack.getItemDamage(), (double)x + 0.5D, (double)y + spawnOffsetY, (double)z + 0.5D) != null)
			{
				if(itemstack.itemID == MineFactoryReloadedCore.safariNetSingleItem.itemID)
				{
					itemstack.stackSize--;
				}
				else
				{
					itemstack.setItemDamage(0);
				}
			}
		}
		else
		{
			if(spawnCreature(world, itemstack.getTagCompound(), (double)x + 0.5D, (double)y + spawnOffsetY, (double)z + 0.5D) != null)
			{
				if(itemstack.itemID == MineFactoryReloadedCore.safariNetSingleItem.itemID)
				{
					itemstack.stackSize--;
				}
				else
				{
					itemstack.setTagCompound(null);
				}
			}
		}

		return true;
	}

	private static Entity spawnCreature(World world, NBTTagCompound mobTag, double x, double y, double z)
	{
		NBTTagList pos = mobTag.getTagList("Pos");
		((NBTTagDouble)pos.tagAt(0)).data = x;
		((NBTTagDouble)pos.tagAt(1)).data = y;
		((NBTTagDouble)pos.tagAt(2)).data = z;
		
		Entity e = EntityList.createEntityFromNBT(mobTag, world);

		if (e != null)
		{
			e.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
			((EntityLiving)e).initCreature();
			
			e.readFromNBT(mobTag);
			
			world.spawnEntityInWorld(e);
			((EntityLiving)e).playLivingSound();
		}

		return e;
	}

	private static Entity spawnCreature(World world, int mobId, double x, double y, double z)
	{
		if(!EntityList.entityEggs.containsKey(Integer.valueOf(mobId)))
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
		return captureEntity(itemstack, entity);
	}
	
	public static boolean captureEntity(ItemStack itemstack, EntityLiving entity)
	{
		if(entity.worldObj.isRemote)
		{
			return false;
		}
		if(!isEmpty(itemstack))
		{
			return false;
		}
		else if(MFRRegistry.getSafariNetBlacklist().contains(entity.getClass()))
		{
			return false;
		}
		else if(entity instanceof EntityLiving && !(entity instanceof EntityPlayer))
		{
			NBTTagCompound c = new NBTTagCompound();
			
			entity.writeToNBT(c);

			c.setString("id", (String)EntityList.classToStringMapping.get(entity.getClass()));
			c.setString("_class", entity.getClass().getName());
			
			entity.setDead();
			if(entity.isDead)
			{
				itemstack.setTagCompound(c);
				return true;
			}
			else
			{
				return false;
			}
		}
		return true;
	}
	
	public static boolean isEmpty(ItemStack s)
	{
		return s == null || (s.getItemDamage() == 0 && s.getTagCompound() == null);
	}
	
	public static boolean isSingleUse(ItemStack s)
	{
		return s != null && s.itemID == MineFactoryReloadedCore.safariNetSingleItem.itemID;
	}
}
