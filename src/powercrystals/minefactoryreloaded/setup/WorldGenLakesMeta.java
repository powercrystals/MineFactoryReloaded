package powercrystals.minefactoryreloaded.setup;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenLakesMeta extends WorldGenerator
{
	private int _blockId;
	private int _blockMeta;
	
	public WorldGenLakesMeta(int blockId, int blockMeta)
	{
		_blockId = blockId;
		_blockMeta = blockMeta;
	}
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		x -= 8;
		
		for(z -= 8; y > 5 && world.isAirBlock(x, y, z); --y)
		{
			;
		}
		
		if(y <= 4)
		{
			return false;
		}
		else
		{
			y -= 4;
			boolean[] aboolean = new boolean[2048];
			int l = random.nextInt(4) + 4;
			int i1;
			
			for(i1 = 0; i1 < l; ++i1)
			{
				double d0 = random.nextDouble() * 6.0D + 3.0D;
				double d1 = random.nextDouble() * 4.0D + 2.0D;
				double d2 = random.nextDouble() * 6.0D + 3.0D;
				double d3 = random.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
				double d4 = random.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
				double d5 = random.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;
				
				for(int j1 = 1; j1 < 15; ++j1)
				{
					for(int k1 = 1; k1 < 15; ++k1)
					{
						for(int l1 = 1; l1 < 7; ++l1)
						{
							double d6 = (j1 - d3) / (d0 / 2.0D);
							double d7 = (l1 - d4) / (d1 / 2.0D);
							double d8 = (k1 - d5) / (d2 / 2.0D);
							double d9 = d6 * d6 + d7 * d7 + d8 * d8;
							
							if(d9 < 1.0D)
							{
								aboolean[(j1 * 16 + k1) * 8 + l1] = true;
							}
						}
					}
				}
			}
			
			int i2;
			int j2;
			boolean flag;
			
			for(i1 = 0; i1 < 16; ++i1)
			{
				for(j2 = 0; j2 < 16; ++j2)
				{
					for(i2 = 0; i2 < 8; ++i2)
					{
						flag = !aboolean[(i1 * 16 + j2) * 8 + i2]
								&& (i1 < 15 && aboolean[((i1 + 1) * 16 + j2) * 8 + i2] || i1 > 0 && aboolean[((i1 - 1) * 16 + j2) * 8 + i2] || j2 < 15
										&& aboolean[(i1 * 16 + j2 + 1) * 8 + i2] || j2 > 0 && aboolean[(i1 * 16 + (j2 - 1)) * 8 + i2] || i2 < 7
										&& aboolean[(i1 * 16 + j2) * 8 + i2 + 1] || i2 > 0 && aboolean[(i1 * 16 + j2) * 8 + (i2 - 1)]);
						
						if(flag)
						{
							Material material = world.getBlockMaterial(x + i1, y + i2, z + j2);
							
							if(i2 >= 4 && material.isLiquid())
							{
								return false;
							}
							
							if(i2 < 4 && !material.isSolid() && world.getBlockId(x + i1, y + i2, z + j2) != _blockId)
							{
								return false;
							}
						}
					}
				}
			}
			
			for(i1 = 0; i1 < 16; ++i1)
			{
				for(j2 = 0; j2 < 16; ++j2)
				{
					for(i2 = 0; i2 < 8; ++i2)
					{
						if(aboolean[(i1 * 16 + j2) * 8 + i2])
						{
							world.setBlock(x + i1, y + i2, z + j2, i2 >= 4 ? 0 : _blockId, _blockMeta, 2);
						}
					}
				}
			}
			
			for(i1 = 0; i1 < 16; ++i1)
			{
				for(j2 = 0; j2 < 16; ++j2)
				{
					for(i2 = 4; i2 < 8; ++i2)
					{
						if(aboolean[(i1 * 16 + j2) * 8 + i2] && world.getBlockId(x + i1, y + i2 - 1, z + j2) == Block.dirt.blockID
								&& world.getSavedLightValue(EnumSkyBlock.Sky, x + i1, y + i2, z + j2) > 0)
						{
							BiomeGenBase biomegenbase = world.getBiomeGenForCoords(x + i1, z + j2);
							
							if(biomegenbase.topBlock == Block.mycelium.blockID)
							{
								world.setBlock(x + i1, y + i2 - 1, z + j2, Block.mycelium.blockID, 0, 2);
							}
							else
							{
								world.setBlock(x + i1, y + i2 - 1, z + j2, Block.grass.blockID, 0, 2);
							}
						}
					}
				}
			}
			
			return true;
		}
	}
}