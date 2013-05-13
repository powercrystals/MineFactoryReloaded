package powercrystals.minefactoryreloaded.setup.village;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.ComponentVillage;
import net.minecraft.world.gen.structure.ComponentVillageStartPiece;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieceWeight;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.setup.MFRConfig;

public class ComponentZoologistHouse extends ComponentVillage
{
	private int averageGroundLevel = -1;
	
	public ComponentZoologistHouse(ComponentVillageStartPiece startPiece, int componentType, Random rand, StructureBoundingBox sbb, int coordBaseMode)
	{
		super(startPiece, componentType);
		this.coordBaseMode = coordBaseMode;
		boundingBox = sbb;
	}
	
	@SuppressWarnings("rawtypes")
	public static ComponentZoologistHouse buildComponent(StructureVillagePieceWeight villagePiece, ComponentVillageStartPiece startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	{
		StructureBoundingBox sbb = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, 9, 9, 6, p4);
		return (!canVillageGoDeeper(sbb)) || (StructureComponent.findIntersecting(pieces, sbb) != null) ? null : new ComponentZoologistHouse(startPiece, p5, random, sbb, p4);
	}
	
	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox sbb)
	{
		if(averageGroundLevel < 0)
		{
			averageGroundLevel = getAverageGroundLevel(world, sbb);
			
			if(averageGroundLevel < 0)
			{
				return true;
			}
			
			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 9 - 1, 0);
		}
		
		int brickId = MineFactoryReloadedCore.factoryDecorativeBrickBlock.blockID;
		int brickMeta = 4;
		
		fillWithBlocks(world, sbb, 1, 1, 1, 7, 5, 4, 0, 0, false);
		fillWithMetadataBlocks(world, sbb, 0, 0, 0, 8, 0, 5, brickId, brickMeta, brickId, brickMeta, false);
		fillWithMetadataBlocks(world, sbb, 0, 5, 0, 8, 5, 5, brickId, brickMeta, brickId, brickMeta, false);
		fillWithMetadataBlocks(world, sbb, 0, 6, 1, 8, 6, 4, brickId, brickMeta, brickId, brickMeta, false);
		fillWithMetadataBlocks(world, sbb, 0, 7, 2, 8, 7, 3, brickId, brickMeta, brickId, brickMeta, false);
		int i = getMetadataWithOffset(Block.stairsWoodOak.blockID, 3);
		int j = getMetadataWithOffset(Block.stairsWoodOak.blockID, 2);
		int k;
		int l;
		
		for(k = -1; k <= 2; ++k)
		{
			for(l = 0; l <= 8; ++l)
			{
				placeBlockAtCurrentPosition(world, Block.stairsWoodOak.blockID, i, l, 6 + k, k, sbb);
				placeBlockAtCurrentPosition(world, Block.stairsWoodOak.blockID, j, l, 6 + k, 5 - k, sbb);
			}
		}
		
		fillWithMetadataBlocks(world, sbb, 0, 1, 0, 0, 1, 5, brickId, brickMeta, brickId, brickMeta, false);
		fillWithMetadataBlocks(world, sbb, 1, 1, 5, 8, 1, 5, brickId, brickMeta, brickId, brickMeta, false);
		fillWithMetadataBlocks(world, sbb, 8, 1, 0, 8, 1, 4, brickId, brickMeta, brickId, brickMeta, false);
		fillWithMetadataBlocks(world, sbb, 2, 1, 0, 7, 1, 0, brickId, brickMeta, brickId, brickMeta, false);
		fillWithMetadataBlocks(world, sbb, 0, 2, 0, 0, 4, 0, brickId, brickMeta, brickId, brickMeta, false);
		fillWithMetadataBlocks(world, sbb, 0, 2, 5, 0, 4, 5, brickId, brickMeta, brickId, brickMeta, false);
		fillWithMetadataBlocks(world, sbb, 8, 2, 5, 8, 4, 5, brickId, brickMeta, brickId, brickMeta, false);
		fillWithMetadataBlocks(world, sbb, 8, 2, 0, 8, 4, 0, brickId, brickMeta, brickId, brickMeta, false);
		fillWithBlocks(world, sbb, 0, 2, 1, 0, 4, 4, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(world, sbb, 1, 2, 5, 7, 4, 5, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(world, sbb, 8, 2, 1, 8, 4, 4, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(world, sbb, 1, 2, 0, 7, 4, 0, Block.planks.blockID, Block.planks.blockID, false);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 4, 2, 0, sbb);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 5, 2, 0, sbb);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 6, 2, 0, sbb);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 4, 3, 0, sbb);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 5, 3, 0, sbb);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 6, 3, 0, sbb);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 0, 2, 2, sbb);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 0, 2, 3, sbb);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 0, 3, 2, sbb);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 0, 3, 3, sbb);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 8, 2, 2, sbb);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 8, 2, 3, sbb);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 8, 3, 2, sbb);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 8, 3, 3, sbb);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 2, 2, 5, sbb);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 3, 2, 5, sbb);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 5, 2, 5, sbb);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 6, 2, 5, sbb);
		fillWithBlocks(world, sbb, 1, 4, 1, 7, 4, 1, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(world, sbb, 1, 4, 4, 7, 4, 4, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(world, sbb, 1, 3, 4, 7, 3, 4, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 0, 7, 1, 4, sbb);
		placeBlockAtCurrentPosition(world, Block.stairsWoodOak.blockID, getMetadataWithOffset(Block.stairsWoodOak.blockID, 0), 7, 1, 3, sbb);
		k = getMetadataWithOffset(Block.stairsWoodOak.blockID, 3);
		placeBlockAtCurrentPosition(world, Block.stairsWoodOak.blockID, k, 6, 1, 4, sbb);
		placeBlockAtCurrentPosition(world, Block.stairsWoodOak.blockID, k, 5, 1, 4, sbb);
		placeBlockAtCurrentPosition(world, Block.stairsWoodOak.blockID, k, 4, 1, 4, sbb);
		placeBlockAtCurrentPosition(world, Block.stairsWoodOak.blockID, k, 3, 1, 4, sbb);
		placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 6, 1, 3, sbb);
		placeBlockAtCurrentPosition(world, Block.pressurePlatePlanks.blockID, 0, 6, 2, 3, sbb);
		placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 4, 1, 3, sbb);
		placeBlockAtCurrentPosition(world, Block.pressurePlatePlanks.blockID, 0, 4, 2, 3, sbb);
		placeBlockAtCurrentPosition(world, Block.workbench.blockID, 0, 7, 1, 1, sbb);
		placeBlockAtCurrentPosition(world, 0, 0, 1, 1, 0, sbb);
		placeBlockAtCurrentPosition(world, 0, 0, 1, 2, 0, sbb);
		placeDoorAtCurrentPosition(world, sbb, random, 1, 1, 0, getMetadataWithOffset(Block.doorWood.blockID, 1));
		
		if(getBlockIdAtCurrentPosition(world, 1, 0, -1, sbb) == 0 && getBlockIdAtCurrentPosition(world, 1, -1, -1, sbb) != 0)
		{
			placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 1, 0, -1, sbb);
		}
		
		for(l = 0; l < 6; ++l)
		{
			for(int i1 = 0; i1 < 9; ++i1)
			{
				clearCurrentPositionBlocksUpwards(world, i1, 9, l, sbb);
				fillCurrentPositionBlocksDownwards(world, brickId, brickMeta, i1, -1, l, sbb);
			}
		}
		
		spawnVillagers(world, sbb, 2, 1, 2, 1);
		return true;
	}
	
	@Override
	protected int getVillagerType(int par1)
	{
		return MFRConfig.zoolologistEntityId.getInt();
	}
}
