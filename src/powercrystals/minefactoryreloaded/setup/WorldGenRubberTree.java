package powercrystals.minefactoryreloaded.setup;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class WorldGenRubberTree extends WorldGenerator
{
	public WorldGenRubberTree()
	{
		super();
	}
	
	public WorldGenRubberTree(boolean doNotify)
	{
		super(doNotify);
	}
	
	@Override
	public boolean generate(World world, Random rand, int x, int retries, int z)
	{
		for(int c = 0; c < retries; c++)
		{
			int y = world.getActualHeight() - 1;
			while(world.getBlockId(x, y, z) == 0 && y > 0)
			{
				y--;
			}
			
			if(!growTree(world, rand, x, y + 1, z))
			{
				retries--;
			}
			
			x += rand.nextInt(16) - 8;
			z += rand.nextInt(16) - 8;
		}
		
		return true;
	}
	
	public boolean growTree(World world, Random rand, int x, int y, int z)
	{
		int treeHeight = rand.nextInt(3) + 5,
			worldHeight = world.getHeight();
		boolean canGrow = true;
		
		if(y >= 1 && y + treeHeight + 1 <= worldHeight)
		{
			int var8;
			int var10;
			int var11;
			int var12;
			
			for(var8 = y; var8 <= y + 1 + treeHeight; ++var8)
			{
				byte var9 = 1;
				
				if(var8 == y)
				{
					var9 = 0;
				}
				
				if(var8 >= y + 1 + treeHeight - 2)
				{
					var9 = 2;
				}
				
				for(var10 = x - var9; var10 <= x + var9 && canGrow; ++var10)
				{
					for(var11 = z - var9; var11 <= z + var9 && canGrow; ++var11)
					{
						if(var8 >= 0 && var8 < worldHeight)
						{
							var12 = world.getBlockId(var10, var8, var11);
							
							Block block = Block.blocksList[var12];
							
							if((block != null &&
									(!block.isAirBlock(world, var10, var8, var11) ||
											!block.isLeaves(world, var10, var8, var11))))
							{
								canGrow = false;
							}
						}
						else
						{
							canGrow = false;
						}
					}
				}
			}
			
			if(!canGrow)
			{
				return false;
			}
			else
			{
				var8 = world.getBlockId(x, y - 1, z);
				
				if((Block.blocksList[var8] != null && (var8 == Block.grass.blockID ||
						var8 == Block.dirt.blockID ||
						Block.blocksList[var8].canSustainPlant(world, x, y - 1, z, ForgeDirection.UP,
								((BlockSapling)MineFactoryReloadedCore.rubberSaplingBlock)))) &&
						y < worldHeight - treeHeight - 1)
				{
					if (var8 == Block.grass.blockID)
					{
						this.setBlock(world, x, y - 1, z, Block.dirt.blockID);
					}
					int yOffset;
					
					for(yOffset = y - 3 + treeHeight; yOffset <= y + treeHeight; ++yOffset)
					{
						var10 = yOffset - (y + treeHeight);
						var11 = 1 - var10 / 2;
						
						for(var12 = x - var11; var12 <= x + var11; ++var12)
						{
							int var13 = var12 - x;
							
							for(int var14 = z - var11; var14 <= z + var11; ++var14)
							{
								int var15 = var14 - z;
								
								Block block = Block.blocksList[world.getBlockId(var12, yOffset, var14)];
								
								if((Math.abs(var13) != var11 || Math.abs(var15) != var11 ||
										rand.nextInt(2) != 0 && var10 != 0) &&
										(block == null || block.isAirBlock(world, var12, yOffset, var14) ||
											block.canBeReplacedByLeaves(world, var12, yOffset, var14)))
								{
									this.setBlockAndMetadata(world, var12, yOffset, var14, MineFactoryReloadedCore.rubberLeavesBlock.blockID, 0);
								}
							}
						}
					}
					
					for(yOffset = 0; yOffset < treeHeight; ++yOffset)
					{
						var10 = world.getBlockId(x, y + yOffset, z);
						
						Block block = Block.blocksList[var10];
						
						if(block == null || block.isAirBlock(world, x, y + yOffset, z)  ||
								block.isLeaves(world, x, y + yOffset, z))
						{
							this.setBlockAndMetadata(world, x, y + yOffset, z, MineFactoryReloadedCore.rubberWoodBlock.blockID, 1);
						}
					}
					
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		else
		{
			return false;
		}
	}
}
