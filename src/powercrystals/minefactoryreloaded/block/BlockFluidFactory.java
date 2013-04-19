package powercrystals.minefactoryreloaded.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquid;
import powercrystals.core.block.BlockFluidClassic;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.rednet.IConnectableRedNet;
import powercrystals.minefactoryreloaded.api.rednet.RedNetConnectionType;

public class BlockFluidFactory extends BlockFluidClassic implements ILiquid, IConnectableRedNet
{
	private Icon _iconFlowing;
	private Icon _iconStill;
	
	public BlockFluidFactory(int id, String liquidName)
	{
		super(id, Material.water);
		setUnlocalizedName("mfr.liquid." + liquidName + ".flowing");
		setHardness(100.0F);
		setLightOpacity(3);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if(entity instanceof EntityPlayer || entity instanceof EntityLiving && !((EntityLiving)entity).isEntityUndead())
		{
			if(blockID == MineFactoryReloadedCore.sludgeLiquid.blockID)
			{
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.poison.id, 12 * 20, 0));
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.weakness.id, 12 * 20, 0));
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 12 * 20, 0));
			}
			else if(blockID == MineFactoryReloadedCore.sewageLiquid.blockID)
			{
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.hunger.id, 12 * 20, 0));
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.poison.id, 12 * 20, 0));
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 12 * 20, 0));
			}
			else if(blockID == MineFactoryReloadedCore.essenceLiquid.blockID)
			{
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.nightVision.id, 60 * 20, 0));
			}
			else if(blockID == MineFactoryReloadedCore.milkLiquid.blockID)
			{
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.digSpeed.id, 6 * 20, 0));
			}
			else if(blockID == MineFactoryReloadedCore.biofuelLiquid.blockID)
			{
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 12 * 20, 0));
			}
		}
		super.onEntityCollidedWithBlock(world, x, y, z, entity);
	}

	@Override
	public int getRenderType()
	{
		return MineFactoryReloadedCore.renderIdFluidClassic;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		return 1;
	}

	@Override
	public int stillLiquidId()
	{
		return blockID;
	}

	@Override
	public boolean isMetaSensitive()
	{
		return false;
	}

	@Override
	public int stillLiquidMeta()
	{
		return 0;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister ir)
	{
		_iconStill = ir.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName());
		_iconFlowing = ir.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName().replace(".still", ".flowing"));
	}

	@Override
	public Icon getIcon(int side, int meta)
	{
		return side <= 1 ? _iconStill : _iconFlowing;
	}

	@Override
	public RedNetConnectionType getConnectionType(World world, int x, int y, int z, ForgeDirection side)
	{
		return RedNetConnectionType.None;
	}

	@Override
	public int[] getOutputValues(World world, int x, int y, int z, ForgeDirection side)
	{
		return null;
	}

	@Override
	public int getOutputValue(World world, int x, int y, int z, ForgeDirection side, int subnet)
	{
		return 0;
	}

	@Override
	public void onInputsChanged(World world, int x, int y, int z, ForgeDirection side, int[] inputValues)
	{
	}

	@Override
	public void onInputChanged(World world, int x, int y, int z, ForgeDirection side, int inputValue)
	{
	}
}
