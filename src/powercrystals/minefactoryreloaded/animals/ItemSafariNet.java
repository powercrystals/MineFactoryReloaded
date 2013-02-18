package powercrystals.minefactoryreloaded.animals;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Facing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;
import powercrystals.minefactoryreloaded.core.ItemFactory;

public class ItemSafariNet extends ItemFactory
{
	public ItemSafariNet()
	{
		super(MineFactoryReloadedCore.safariNetItemId.getInt());
		maxStackSize = 1;
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
		
		infoList.add(stack.getTagCompound().getString("mobName"));
		for(ISafariNetHandler handler : MFRRegistry.getSafariNetHandlers())
		{
			if(handler.validFor().isAssignableFrom((Class<?>)EntityList.stringToClassMapping.get(stack.getTagCompound().getString("mobName"))))
			{
				handler.addInformation(stack, player, infoList, advancedTooltips);
			}
		}
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
		if(pass == 0) return iconIndex;
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
		if(stack.getItemDamage() == 0 && stack.getTagCompound() == null)
		{
			return 16777215;
		}
		EntityEggInfo egg;
		
		if(stack.getItemDamage() != 0)
		{
			egg = (EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(stack.getItemDamage()));
		}
		else
		{
			egg = (EntityEggInfo)EntityList.entityEggs.get((Integer)EntityList.stringToIDMapping.get(stack.getTagCompound().getString("mobName")));
		}
		
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
		else if(itemstack.getItemDamage() == 0 || itemstack.getTagCompound() == null)
		{
			System.out.println("Called use with no entity stored");
			return true;
		}
		else
		{
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
				System.out.println("Attempting to spawn from damage");
				if(spawnCreature(world, itemstack.getItemDamage(), (double)x + 0.5D, (double)y + spawnOffsetY, (double)z + 0.5D) != null)
				{
					System.out.println("Spawn successful");
					itemstack.setItemDamage(0);
				}
			}
			else
			{
				System.out.println("Attempting to spawn from NBT");
				if(spawnCreature(world, itemstack.getTagCompound(), (double)x + 0.5D, (double)y + spawnOffsetY, (double)z + 0.5D) != null)
				{
					System.out.println("Spawn successful");
					itemstack.setTagCompound(null);
				}
			}

			return true;
		}
	}

	private static Entity spawnCreature(World world, NBTTagCompound mobTag, double x, double y, double z)
	{
		System.out.println("Spawning entity with name " + mobTag.getString("mobName"));
		Entity e = EntityList.createEntityByName(mobTag.getString("mobName"), world);
		System.out.println("Entity null: " + (e == null));

		if (e != null)
		{
			e.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
			((EntityLiving)e).initCreature();
			
			for(ISafariNetHandler handler : MFRRegistry.getSafariNetHandlers())
			{
				if(handler.validFor().isAssignableFrom(e.getClass()))
				{
					handler.onRelease(mobTag, e);
				}
			}
			
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
		if(itemstack.getItemDamage() > 0 || itemstack.getTagCompound() != null)
		{
			return false;
		}
		if(entity instanceof EntityLiving && !(entity instanceof EntityPlayer))
		{
			NBTTagCompound c = new NBTTagCompound();
			
			for(ISafariNetHandler handler : MFRRegistry.getSafariNetHandlers())
			{
				if(handler.validFor().isAssignableFrom(entity.getClass()))
				{
					handler.onCapture(c, entity);
				}
			}

			c.setString("mobName", (String)EntityList.classToStringMapping.get(entity.getClass()));
			itemstack.setTagCompound(c);
			entity.setDead();
			return true;
		}
		return true;
	}
}
