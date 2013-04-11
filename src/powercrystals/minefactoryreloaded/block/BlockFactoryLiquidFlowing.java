package powercrystals.minefactoryreloaded.block;

import java.util.Random;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowing;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFactoryLiquidFlowing extends BlockFlowing
{
	protected int numAdjacentSources = 0;
	protected boolean[] isOptimalFlowDirection = new boolean[4];
	protected int[] flowCost = new int[4];
	
	private Icon _icon;
	
	private int _stillId;
	
	public BlockFactoryLiquidFlowing(int flowingId, int stillId, String liquidName)
	{
		super(flowingId, Material.water);
		setUnlocalizedName("mfr.liquid." + liquidName + ".flowing");
		setHardness(100.0F);
		setLightOpacity(3);
		_stillId = stillId;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if(entity instanceof EntityPlayer || entity instanceof EntityLiving && !((EntityLiving)entity).isEntityUndead())
		{
			if(blockID == MineFactoryReloadedCore.sludgeFlowing.blockID)
			{
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.poison.id, 12 * 20, 0));
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.weakness.id, 12 * 20, 0));
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 12 * 20, 0));
			}
			else if(blockID == MineFactoryReloadedCore.sewageFlowing.blockID)
			{
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.hunger.id, 12 * 20, 0));
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.poison.id, 12 * 20, 0));
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 12 * 20, 0));
			}
			else if(blockID == MineFactoryReloadedCore.essenceFlowing.blockID)
			{
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.nightVision.id, 60 * 20, 0));
			}
			else if(blockID == MineFactoryReloadedCore.milkFlowing.blockID)
			{
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.digSpeed.id, 6 * 20, 0));
			}
			else if(blockID == MineFactoryReloadedCore.biofuelFlowing.blockID)
			{
				((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 12 * 20, 0));
			}
		}
		super.onEntityCollidedWithBlock(world, x, y, z, entity);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister ir)
	{
		_icon = ir.registerIcon("powercrystals/minefactoryreloaded/" + getUnlocalizedName());
	}

	@Override
	public Icon getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		return _icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z)
	{
		return 16777215;
	}
	
	@Override
	public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random par5Random)
	{
		int l = this.getFlowDecay(world, x, y, z);
		byte b0 = 1;

		if (this.blockMaterial == Material.lava && !world.provider.isHellWorld)
		{
			b0 = 2;
		}

		boolean flag = true;
		int i1;

		if (l > 0)
		{
			byte b1 = -100;
			this.numAdjacentSources = 0;
			int j1 = this.getSmallestFlowDecay(world, x - 1, y, z, b1);
			j1 = this.getSmallestFlowDecay(world, x + 1, y, z, j1);
			j1 = this.getSmallestFlowDecay(world, x, y, z - 1, j1);
			j1 = this.getSmallestFlowDecay(world, x, y, z + 1, j1);
			i1 = j1 + b0;

			if (i1 >= 8 || j1 < 0)
			{
				i1 = -1;
			}

			if(this.getFlowDecay(world, x, y + 1, z) >= 0)
			{
				int k1 = this.getFlowDecay(world, x, y + 1, z);

				if (k1 >= 8)
				{
					i1 = k1;
				}
				else
				{
					i1 = k1 + 8;
				}
			}

			if(this.numAdjacentSources >= 2 && this.blockMaterial == Material.water)
			{
				if (world.getBlockMaterial(x, y - 1, z).isSolid())
				{
					i1 = 0;
				}
				else if (world.getBlockMaterial(x, y - 1, z) == this.blockMaterial && world.getBlockMetadata(x, y - 1, z) == 0)
				{
					i1 = 0;
				}
			}

			if(this.blockMaterial == Material.lava && l < 8 && i1 < 8 && i1 > l && par5Random.nextInt(4) != 0)
			{
				i1 = l;
				flag = false;
			}

			if(i1 == l)
			{
				if (flag)
				{
					this.updateFlow(world, x, y, z);
				}
			}
			else
			{
				l = i1;

				if(i1 < 0)
				{
					world.setBlockToAir(x, y, z);
				}
				else
				{
					world.setBlockMetadataWithNotify(x, y, z, i1, 2);
					world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate(world));
					world.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
				}
			}
		}
		else
		{
			this.updateFlow(world, x, y, z);
		}

		if(this.liquidCanDisplaceBlock(world, x, y - 1, z))
		{
			if(this.blockMaterial == Material.lava && world.getBlockMaterial(x, y - 1, z) == Material.water)
			{
				world.setBlock(x, y - 1, z, Block.stone.blockID);
				this.triggerLavaMixEffects(world, x, y - 1, z);
				return;
			}

			if (l >= 8)
			{
				this.flowIntoBlock(world, x, y - 1, z, l);
			}
			else
			{
				this.flowIntoBlock(world, x, y - 1, z, l + 8);
			}
		}
		else if(l >= 0 && (l == 0 || this.blockBlocksFlow(world, x, y - 1, z)))
		{
			boolean[] aboolean = this.getOptimalFlowDirections(world, x, y, z);
			i1 = l + b0;

			if (l >= 8)
			{
				i1 = 1;
			}

			if (i1 >= 8)
			{
				return;
			}

			if (aboolean[0])
			{
				this.flowIntoBlock(world, x - 1, y, z, i1);
			}

			if (aboolean[1])
			{
				this.flowIntoBlock(world, x + 1, y, z, i1);
			}

			if (aboolean[2])
			{
				this.flowIntoBlock(world, x, y, z - 1, i1);
			}

			if (aboolean[3])
			{
				this.flowIntoBlock(world, x, y, z + 1, i1);
			}
		}
	}
	
	private void updateFlow(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		world.setBlock(x, y, z, _stillId, meta, 2);
	}
	
	private boolean liquidCanDisplaceBlock(World world, int x, int y, int z)
	{
		Material material = world.getBlockMaterial(x, y, z);
		return material == this.blockMaterial ? false : (material == Material.lava ? false : !this.blockBlocksFlow(world, x, y, z));
	}
	
	private void flowIntoBlock(World world, int x, int y, int z, int meta)
	{
		if(this.liquidCanDisplaceBlock(world, x, y, z))
		{
			int blockId = world.getBlockId(x, y, z);

			if(blockId > 0)
			{
				if(this.blockMaterial == Material.lava)
				{
					this.triggerLavaMixEffects(world, x, y, z);
				}
				else
				{
					Block.blocksList[blockId].dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
				}
			}

			world.setBlock(x, y, z, this.blockID, meta, 3);
		}
	}
	
	private boolean blockBlocksFlow(World world, int x, int y, int z)
	{
		int blockId = world.getBlockId(x, y, z);

		if(blockId != Block.doorWood.blockID && blockId != Block.doorSteel.blockID && blockId != Block.signPost.blockID && blockId != Block.ladder.blockID && blockId != Block.reed.blockID)
		{
			if(blockId == 0)
			{
				return false;
			}
			else
			{
				Material material = Block.blocksList[blockId].blockMaterial;
				return material == Material.portal ? true : material.blocksMovement();
			}
		}
		else
		{
			return true;
		}
	}
	
	private boolean[] getOptimalFlowDirections(World world, int x, int y, int z)
	{
		int side;
		int destX;

		for(side = 0; side < 4; ++side)
		{
			this.flowCost[side] = 1000;
			destX = x;
			int destZ = z;

			if(side == 0)
			{
				destX = x - 1;
			}

			if(side == 1)
			{
				++destX;
			}

			if(side == 2)
			{
				destZ = z - 1;
			}

			if(side == 3)
			{
				++destZ;
			}

			if(!this.blockBlocksFlow(world, destX, y, destZ) && (world.getBlockMaterial(destX, y, destZ) != this.blockMaterial || world.getBlockMetadata(destX, y, destZ) != 0))
			{
				if(this.blockBlocksFlow(world, destX, y - 1, destZ))
				{
					this.flowCost[side] = this.calculateFlowCost(world, destX, y, destZ, 1, side);
				}
				else
				{
					this.flowCost[side] = 0;
				}
			}
		}

		side = this.flowCost[0];

		for(destX = 1; destX < 4; ++destX)
		{
			if(this.flowCost[destX] < side)
			{
				side = this.flowCost[destX];
			}
		}

		for(destX = 0; destX < 4; ++destX)
		{
			this.isOptimalFlowDirection[destX] = this.flowCost[destX] == side;
		}

		return this.isOptimalFlowDirection;
	}
	
	private int calculateFlowCost(World world, int x, int y, int z, int recurseDepth, int side)
	{
		int j1 = 1000;

		for(int destSide = 0; destSide < 4; ++destSide)
		{
			if((destSide != 0 || side != 1) && (destSide != 1 || side != 0) && (destSide != 2 || side != 3) && (destSide != 3 || side != 2))
			{
				int destX = x;
				int destZ = z;

				if(destSide == 0)
				{
					destX = x - 1;
				}

				if(destSide == 1)
				{
					++destX;
				}

				if(destSide == 2)
				{
					destZ = z - 1;
				}

				if(destSide == 3)
				{
					++destZ;
				}

				if(!this.blockBlocksFlow(world, destX, y, destZ) && (world.getBlockMaterial(destX, y, destZ) != this.blockMaterial || world.getBlockMetadata(destX, y, destZ) != 0))
				{
					if(!this.blockBlocksFlow(world, destX, y - 1, destZ))
					{
						return recurseDepth;
					}

					if (recurseDepth < 4)
					{
						int j2 = this.calculateFlowCost(world, destX, y, destZ, recurseDepth + 1, destSide);

						if (j2 < j1)
						{
							j1 = j2;
						}
					}
				}
			}
		}

		return j1;
	}
}
