package powercrystals.minefactoryreloaded.farmables.plantables;

import powercrystals.minefactoryreloaded.api.IFactoryPlantable;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PlantableCropPlant extends PlantableStandard implements IFactoryPlantable
{
	public PlantableCropPlant(int seedItemID, int plantBlockID)
	{
		super(seedItemID, plantBlockID);
	}
	
	@Override
	public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack)
	{
		int groundId = world.getBlockId(x, y - 1, z);
		int ourId = world.getBlockId(x, y, z);
		return (groundId == Block.dirt.blockID || groundId == Block.grass.blockID || groundId == Block.tilledField.blockID)
			&& (Block.blocksList[ourId] == null || Block.blocksList[ourId].isAirBlock(world, x, y, z));
	}

	@Override
	public void prePlant(World world, int x, int y, int z, ItemStack stack)
	{
		int groundId = world.getBlockId(x, y - 1, z);
		if(groundId != Block.tilledField.blockID)
		{
			world.func_94575_c(x, y - 1, z, Block.tilledField.blockID);
		}
	}
}
